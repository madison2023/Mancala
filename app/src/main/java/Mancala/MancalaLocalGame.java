package Mancala;

import com.example.mancala.game.GameFramework.LocalGame;
import com.example.mancala.game.GameFramework.actionMessage.GameAction;
import com.example.mancala.game.GameFramework.players.GamePlayer;

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
     * @return
     */
    @Override
    protected boolean canMove(int playerIdx) {
        return playerIdx == ((MancalaGameState)state).getWhoseTurn();
    }

    @Override
    protected String checkIfGameOver() {

        MancalaGameState state = (MancalaGameState) super.state;

        int computerRowValue = 0; // number of marbles in computer pockets
        int humanRowValue = 0;

        int[] temp = state.getPlayer0();
        int computerStoreValue = temp[6]; // set computer score
        for (int i = 0; i < temp.length - 1; i++) {
            computerRowValue = temp[i] + computerRowValue;
        }

        temp = state.getPlayer1();
        int humanStoreValue = temp[6]; // set human score
        for (int i = 0; i < temp.length - 1; i++) {
            humanRowValue = temp[i] + humanRowValue;
        }

        if(computerRowValue == 0 || humanRowValue == 0){
            if(humanStoreValue > computerStoreValue){
                return playerNames[0] + " won!";
            } else {
                return playerNames[1] + " won!";
            }
        } else {
            return null; // neither row is empty, game continues
        }
    }

    @Override
    protected boolean makeMove(GameAction action) {
        MancalaMoveAction mancalaMoveAction = (MancalaMoveAction) action;
        MancalaGameState state = (MancalaGameState) super.state;

        int row = mancalaMoveAction.getRow();
        int col = mancalaMoveAction.getCol();


        //int playerId = getPlayerIdx(mancalaMoveAction.getPlayer());

        int whoseTurn = state.getWhoseTurn();
        int[] player0 = state.getPlayer0();
        int[] player1 = state.getPlayer1();

        if (whoseTurn == 0 && (row == 1 || player0[col] == 0 || col == 6)) {
            return false;
        }
        else if (whoseTurn == 1 && (row == 0 || player1[col] == 0 || col == 6)) {
            return false;
        }


        state.selectPit(row,col);
        //figure out a way to give the player another turn if necessary
        state.setWhoseTurn(1- whoseTurn);


        return true;
    }
}
