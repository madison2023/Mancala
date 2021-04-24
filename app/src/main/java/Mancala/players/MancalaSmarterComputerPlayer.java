package Mancala.players;

import android.util.Log;

import com.example.mancala.game.GameFramework.infoMessage.GameInfo;
import com.example.mancala.game.GameFramework.players.GameComputerPlayer;

import java.util.Random;

import Mancala.MancalaGameState;
import Mancala.MancalaMoveAction;

/**
 * @author Henry Lee
 */
public class MancalaSmarterComputerPlayer extends GameComputerPlayer {

    protected Random gen = new Random();

    /**
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */
    public MancalaSmarterComputerPlayer(String name) {
        super(name);
    }

    @Override
    protected void receiveInfo(GameInfo info) {
        if (!(info instanceof MancalaGameState)) {
            return;
        }


        Log.d("MancalaSmarterComputerPlayer", "receiveInfo - " + this.playerNum);

        MancalaGameState smartCompstate = new MancalaGameState((MancalaGameState) info);
        if((smartCompstate.getWhoseTurn() != playerNum) || (this.playerNum == smartCompstate.getPlayerBottom())){
            return;
        }


        int[] player0 = smartCompstate.getPlayer0();
        int[] player1 = smartCompstate.getPlayer1();
        int pitNum;


        int getAnotherTurnPitNum = findGetAnotherTurn(player1); //problem with findGetAnotherTurn method
        if (getAnotherTurnPitNum != -1) {
            MancalaMoveAction action = new MancalaMoveAction(this, 1, getAnotherTurnPitNum);
            sleep(3);
            game.sendAction(action);
            Log.d("Smarter Comp", "getAnotherTurnPit found");

        } else {
            int lastPit = smartCompstate.getLastCol();
            //player 1 - Computer
            int marbleCount = player1[lastPit];
            if (marbleCount == 0 && player0[lastPit] != 0){
                // pit is empty, and pit across is not empty
                // generate new pit number
                int pitNumber = gen.nextInt(6);
                sendGame(pitNumber);
            } else {
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
     *
     */
    private void sendGame(int pitNum) {
        Log.d("MancalaSmarterComputerPlayer", "sendGame - " + this.playerNum + " " + pitNum);
        MancalaMoveAction action = new MancalaMoveAction(this, 1, pitNum);
        sleep(1);
        game.sendAction(action);
    }


    private int findGetAnotherTurn(int[] player1) {

        for (int i = 5; i >= 0; i--) {
            int numMarbles = player1[i];
            int endIndex = i + numMarbles;
            if (endIndex == 6) {
                return i;
            }
        }
        return -1;
    }

}
