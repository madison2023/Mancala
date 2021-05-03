package Mancala.players;

import com.example.mancala.game.GameFramework.infoMessage.GameInfo;
import com.example.mancala.game.GameFramework.players.GameComputerPlayer;

import java.util.Random;

import Mancala.MancalaGameState;
import Mancala.MancalaMoveAction;

/**
 * Dumb AI for Mancala game. Randomly selects a pit with marbles on its row.
 * If pit is empty, will continue to randomly select pits until it finds a pit with marbles.
 *
 * @author Henry Lee
 */
public class MancalaDumbComputerPlayer extends GameComputerPlayer {

    // generate random number
    protected Random gen = new Random();

    /**
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */
    public MancalaDumbComputerPlayer(String name) {
        super(name);
    }

    /**
     * receive GameState and process information
     */
    @Override
    protected void receiveInfo(GameInfo info) {

        // create new state by copy
        MancalaGameState dumbCompState = new MancalaGameState((MancalaGameState) info);

        // test if current player is the dumb computer player. If not return
        if (dumbCompState.getWhoseTurn() != this.playerNum) {
            return;
        } else {
            // generate random number
            int pitNumber = gen.nextInt(6);

            // check if pit empty
            int marbleNum = 0;
            if (playerNum == dumbCompState.getPlayerBottom()) {
                marbleNum = ((MancalaGameState) info).getPlayer0()[pitNumber];
            } else {
                marbleNum = ((MancalaGameState) info).getPlayer1()[pitNumber];
            }

            // continue checking until not empty pit
            while (marbleNum == 0) {
                pitNumber = gen.nextInt(6);
                //marbleNum = 0;
                if (playerNum == dumbCompState.getPlayerBottom()) {
                    marbleNum = ((MancalaGameState) info).getPlayer0()[pitNumber];
                } else {
                    marbleNum = ((MancalaGameState) info).getPlayer1()[pitNumber];
                }
            }

            // register action
            MancalaMoveAction action = new MancalaMoveAction(this, 1, pitNumber); //this.playerNum
            sleep(3.0);
            game.sendAction(action);

        }

    }
}
