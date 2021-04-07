package Mancala.players;

import com.example.mancala.game.GameFramework.infoMessage.GameInfo;
import com.example.mancala.game.GameFramework.players.GameComputerPlayer;

import java.util.Random;

import Mancala.MancalaGameState;
import Mancala.MancalaMoveAction;

public class MancalaDumbComputerPlayer extends GameComputerPlayer {

    protected Random gen = new Random();
    /**
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */
    public MancalaDumbComputerPlayer(String name) {
        super(name);
    }

    @Override
    protected void receiveInfo(GameInfo info) {
        MancalaGameState dumbCompState = new MancalaGameState((MancalaGameState)info);

        if (dumbCompState.getWhoseTurn() != this.playerNum){
            return;
        } else {
            // generate random number
            int pitNumber = gen.nextInt(6);

            // check if pit empty
            int marbleNum = 0;
            if (playerNum == 0){
                marbleNum = ((MancalaGameState) info).getPlayer0()[pitNumber];
             } else {
                marbleNum = ((MancalaGameState) info).getPlayer1()[pitNumber];
            }

            // continue checking until not empty pit
            while (marbleNum == 0){
                pitNumber = gen.nextInt(6);
                marbleNum = 0;
                if (playerNum == 0){
                    marbleNum = ((MancalaGameState) info).getPlayer0()[pitNumber];
                } else {
                    marbleNum = ((MancalaGameState) info).getPlayer1()[pitNumber];
                }
            }

            // register action
            MancalaMoveAction action = new MancalaMoveAction(this, this.playerNum, pitNumber);
            //sleep(500);
            game.sendAction(action);

        }

    }
}
