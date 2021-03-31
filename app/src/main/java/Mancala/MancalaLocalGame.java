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

    @Override
    protected boolean canMove(int playerIdx) {
        return false;
    }

    @Override
    protected String checkIfGameOver() {
        return null;
    }

    @Override
    protected boolean makeMove(GameAction action) {
        MancalaMoveAction mancalaMoveAction = (MancalaMoveAction) action;
        MancalaGameState state = (MancalaGameState) super.state;

        int row = mancalaMoveAction.getRow();
        int col = mancalaMoveAction.getCol();


        //int playerId = getPlayerIdx(mancalaMoveAction.getPlayer());

        int whoseTurn = state.getWhoseTurn();

        if(whoseTurn == 0 && col == 1){
            return false;
        }
        else if (whoseTurn == 1 && col == 0) {
            return false;
        }

        state.selectPit(row,col);
        state.setWhoseTurn(1- whoseTurn);


        return true;
    }
}
