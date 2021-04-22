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
    int[] player0;
    int[] player1;

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

        MancalaGameState smartCompstate = new MancalaGameState((MancalaGameState)info);
        int pitNum;

        //use math to find the last marble
        /*
        if (smartCompstate.getLastRow() == this.playerNum){
            //last marble on its the player's own store
            pitNum = smartCompstate.getLastCol();
            sendGame(pitNum);
        } */

        if(smartCompstate.getWhoseTurn() != playerNum){
            return;
        }

        player0 = smartCompstate.getPlayer0();
        player1 = smartCompstate.getPlayer1();
        int getAnotherTurnPitNum = findGetAnotherTurn();
        if (getAnotherTurnPitNum != -1){
            sendGame(getAnotherTurnPitNum);
        } else {
            int lastPit = smartCompstate.getLastCol();
            //int[] player0 = smartCompstate.getPlayer0();
            //int[] player1 = smartCompstate.getPlayer1();
            /*if (this.playerNum == 0){
                //player 0
                int marbleCount = player0[lastPit];
                if (marbleCount == 0 && player1[lastPit] != 0){
                    // pit is empty, and pit across is not empty
                    sendGame(lastPit);
                } else {
                    int largePit = 0;
                    int num = 0;

                    // find pit with the most marbles
                    for (int i=0; i < player0.length; i++){
                        if (player0[i] > num){
                            num = player0[i];
                            largePit = i;
                        }
                    }
                    //send to the largePit
                    sendGame(largePit);
                }
            } else {
                //player 1
                int marbleCount = player1[lastPit];
                if (marbleCount == 0 && player0[lastPit] != 0){
                    // pit is empty, and pit across is not empty
                    sendGame(lastPit);
                } else {
                    int largePit = 0;
                    int num = 0;

                    //find pit with most marbles
                    for (int i=0; i < player1.length; i++){
                        if (player1[i] > num){
                            num = player1[i];
                            largePit = i;
                        }
                    }
                    //send to the largePit
                    sendGame(largePit);
                }
            } */


                // generate random number
                int pitNumber = gen.nextInt(6);

                // check if pit empty
                int marbleNum = 0;
                if (playerNum == smartCompstate.getPlayerBottom()) {
                    marbleNum = ((MancalaGameState) info).getPlayer0()[pitNumber];
                } else {
                    marbleNum = ((MancalaGameState) info).getPlayer1()[pitNumber];
                }

                // continue checking until not empty pit
                while (marbleNum == 0) {
                    pitNumber = gen.nextInt(6);
                    //marbleNum = 0;
                    if (playerNum == smartCompstate.getPlayerBottom()) {
                        marbleNum = ((MancalaGameState) info).getPlayer0()[pitNumber];
                    } else {
                        marbleNum = ((MancalaGameState) info).getPlayer1()[pitNumber];
                    }
                }
               sendGame(pitNumber);

            }

    }


    /**
     * constructor to send the game action
     * @param pitNum
     */
    private void sendGame(int pitNum) {
        Log.d("MancalaSmarterComputerPlayer", "sendGame - " + this.playerNum + " " + pitNum);
        MancalaMoveAction action = new MancalaMoveAction(this, this.playerNum, pitNum);
        sleep(3);
        game.sendAction(action);
    }

    private int findGetAnotherTurn(){
        for (int i = 5; i >= 0; i--){
            int numMarbles = player1[i];
            Log.d("numMarbles", String.valueOf(numMarbles));
            int newIndex = numMarbles + i;
            if (numMarbles + i == 6){
                return i;
            } else if (newIndex < 6){
                if (player1[newIndex] == 0 && player0[Math.abs(newIndex - 5)] != 0){
                    return i;
                }
            }
        }
        return -1;
    }

    private int findCapture(){
        for(int i = 0; i < 6; i++) {
            int numMarbles = player1[i];

            int newIndex = numMarbles + i;
            if (player1[newIndex] == 0 && player0[Math.abs(newIndex - 5)] != 0){
                return i;
            }
        }
        return -1;
    }
}
