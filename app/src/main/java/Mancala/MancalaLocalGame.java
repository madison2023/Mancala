package Mancala;

import com.example.mancala.game.GameFramework.LocalGame;
import com.example.mancala.game.GameFramework.actionMessage.GameAction;
import com.example.mancala.game.GameFramework.players.GamePlayer;

/**
 * Local Game for Mancala--enforces the rules of the game
 * @author Henry Lee
 * @author Jordan Nakamura
 * @author Rachel Madison
 */
public class MancalaLocalGame extends LocalGame {

    public MancalaLocalGame() {
        super();
        super.state = new MancalaGameState();
    }

    public MancalaLocalGame(MancalaGameState gameState){
        super();
        super.state = new MancalaGameState(gameState);


    }

    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        p.sendInfo(new MancalaGameState(((MancalaGameState) state)));
    }

    /**
     *
     * @param playerIdx
     * 		the player's player-number (ID)
     * @return true if the player can move, false otherwise
     */
    @Override
    protected boolean canMove(int playerIdx) {
        return playerIdx == ((MancalaGameState)state).getWhoseTurn();
    }

    @Override
    protected String checkIfGameOver() {


        MancalaGameState state = (MancalaGameState) super.state;

        int player1TotalMarbles = 0; //number of marbles in all of their pockets (not including the store)
        int player0TotalMarbles = 0;

        int[] temp = state.getPlayer0();
        int player0StoreValue = temp[6]; // set player 0 score
        for (int i = 0; i < temp.length - 1; i++) {
            player0TotalMarbles = temp[i] + player0TotalMarbles;
        }

        temp = state.getPlayer1();
        int player1StoreValue = temp[6]; // set player 1 score
        for (int i = 0; i < temp.length - 1; i++) {
            player1TotalMarbles = temp[i] + player1TotalMarbles;
        }

        if(player0TotalMarbles == 0 || player1TotalMarbles == 0){
            if(player0StoreValue > player1StoreValue){
                return playerNames[state.getPlayerBottom()] + " won! "; //was playerNames[0]
            } else {
                return playerNames[state.getPlayerTop()] + " won! ";
            }
        } else {
            return null; // neither row is empty, game continues
        }
    }

    /**
     * makes move by selecting pit and then capture's/gives another turn according to
     * the rules of the game
     *
     * @param action The move that the player has sent to the game
     * @return true if able to make the move, false otherwise
     */
    @Override
    protected boolean makeMove(GameAction action) {
        MancalaMoveAction mancalaMoveAction = (MancalaMoveAction) action;
        MancalaGameState state = (MancalaGameState) super.state;

        int row = mancalaMoveAction.getRow();
        int col = mancalaMoveAction.getCol();


        int whoseTurn = state.getWhoseTurn();
        int[] player0 = state.getPlayer0();
        int[] player1 = state.getPlayer1();

        if (whoseTurn == state.getPlayerBottom() && (row == 1 || player0[col] == 0 || col == 6)) { //was whoseTurn == 0 /*&& (state.getPlayerBottom() == 0)*/
            return false;
        }
        else if (whoseTurn == state.getPlayerTop() && (row == 0 || player1[col] == 0 || col == 6)) { /*&& (state.getPlayerTop() == 1)*/
            return false;
        }

        state.selectPit(row,col);

        //captures marbles if you land in an empty pocket and the opponent has marbles in the opposite pocket
        if(whoseTurn == state.getPlayerBottom() && state.getLastRow() == 0 && player0[state.getLastCol()] == 1
                && player1[Math.abs(state.getLastCol()-5)] > 0 && state.getLastCol() != 6) {
            state.capture(state.getLastRow(),state.getLastCol());
        }
        else if(whoseTurn == state.getPlayerTop() && state.getLastRow() == 1 && player1[state.getLastCol()] == 1
                && player0[Math.abs(state.getLastCol()-5)] > 0 && state.getLastCol() != 6) {
            state.capture(state.getLastRow(),state.getLastCol());
        }

        //switch whose turn it is if we don't need to give the player another turn
        if(!(state.getLastCol() == 6 && ((state.getLastRow() == 1 && whoseTurn == state.getPlayerTop())
            || (state.getLastRow() == 0 && whoseTurn == state.getPlayerBottom())))) { //was && state.getLastRow() == whoseTurn
            state.setWhoseTurn(1 - whoseTurn);
        }

        return true;
    }
}
