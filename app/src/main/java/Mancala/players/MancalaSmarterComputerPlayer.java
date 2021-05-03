package Mancala.players;

import android.util.Log;

import com.example.mancala.game.GameFramework.infoMessage.GameInfo;
import com.example.mancala.game.GameFramework.players.GameComputerPlayer;

import java.util.Random;

import Mancala.MancalaGameState;
import Mancala.MancalaMoveAction;

/**
 * Smart AI player. Will choose a pit based on whether the last marble will land in its own store.
 * If it cannot find that pit, it will choose a pit based on whether the last marble will land in an
 * empty pit across from its opponent's pit containing marbles.
 * If it still cannot find a pit, it will then choose the pit with the largest marbles.
 *
 * @author Henry Lee
 */
public class MancalaSmarterComputerPlayer extends GameComputerPlayer {

    // random number generator
    protected Random gen = new Random();

    /**
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */
    public MancalaSmarterComputerPlayer(String name) {
        super(name);
    }

    /**
     * receive GameState and process information
     * @param info
     */
    @Override
    protected void receiveInfo(GameInfo info) {

        // return if the info is not an instance of MancalaGameState
        if (!(info instanceof MancalaGameState)) {
            return;
        }

        // create new state by copy
        MancalaGameState smartCompstate = new MancalaGameState((MancalaGameState) info);

        // return if the player is not the SmarterComputerPlayer
        if((smartCompstate.getWhoseTurn() != playerNum) || (this.playerNum == smartCompstate.getPlayerBottom())){
            return;
        }

        // assign computer player array
        int[] player0 = smartCompstate.getPlayer0();
        int[] player1 = smartCompstate.getPlayer1();
        int pitNum;

        // check if the player is elligible for another turn
        int getAnotherTurnPitNum = findGetAnotherTurn(player1);
        if (getAnotherTurnPitNum != -1) {
            //send game for another turn
            sendGame(getAnotherTurnPitNum);
        } else {
            // find capture number
            int getCaptureMove = findCapture(player1,player0);
            if (getCaptureMove != -1) {
                // if not found then send the game
                sendGame(getCaptureMove);
            } else {
                // variable to record the largest pit and number of marbles in the pit
                int largePit = 0;
                int num = 0;

                //find pit with most marbles
                for (int i=0; i < player1.length-1; i++){
                    if (player1[i] > num){
                        num = player1[i];
                        largePit = i;
                    }
                }
                //send to the largePit
                sendGame(largePit);

            }
        }

    }


    /**
     * constructor to send the game action
     *
     */
    private void sendGame(int pitNum) {
        Log.d("MancalaSmarterComputerPlayer", "sendGame - " + this.playerNum + " " + pitNum);
        MancalaMoveAction action = new MancalaMoveAction(this, 1, pitNum);
        sleep(3);
        game.sendAction(action);
    }

    /**
     * check if the player is eligible for another turn
     * @param player1
     * @return
     */
    private int findGetAnotherTurn(int[] player1) {

        for (int i = 5; i >= 0; i--) {
            int numMarbles = player1[i];

            // caculate endIndex by adding the current pit number and number of marbles of pit
            int endIndex = i + numMarbles;
            if (endIndex == 6) {
                return i;
            }
        }
        return -1;
    }

    /**
     * method to capture marbles of the pit on the opposite row
     * @param player1
     * @param player0
     * @return
     */
    private int findCapture(int[] player1, int[] player0) {
        for(int i = 0; i < 6; i++) {
            int numMarbles = player1[i];
            if(numMarbles != 0) {
                int newIndex = numMarbles + i;
                if (newIndex < 6 && player1[newIndex] == 0 && player0[Math.abs(newIndex - 5)] != 0) {
                    return i;
                }
            }
        }
        return -1;
    }

}
