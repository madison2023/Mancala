package Mancala.players;

import com.example.mancala.game.GameFramework.infoMessage.GameInfo;
import com.example.mancala.game.GameFramework.players.GameComputerPlayer;

import Mancala.MancalaGameState;
import Mancala.MancalaMoveAction;

/**
 * @author Henry Lee
 */
public class MancalaSmarterComputerPlayer extends GameComputerPlayer {
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

        MancalaGameState smartCompstate = new MancalaGameState((MancalaGameState)info);
        int pitNum;
        if (smartCompstate.getLastRow() == this.playerNum){
            //last marble on its the player's own store
            pitNum = smartCompstate.getLastCol();
            sendGame(pitNum);
        } else {
            int lastPit = smartCompstate.getLastCol();
            int[] player0 = smartCompstate.getPlayer0();
            int[] player1 = smartCompstate.getPlayer1();
            if (this.playerNum == 0){
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
            }

        }

    }

    /**
     * constructor to send the game action
     * @param pitNum
     */
    private void sendGame(int pitNum) {
        MancalaMoveAction action = new MancalaMoveAction(this, this.playerNum, pitNum);
        sleep(3.0);
        game.sendAction(action);
    }
}
