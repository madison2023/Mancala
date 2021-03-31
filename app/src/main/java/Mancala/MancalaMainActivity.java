//package Mancala;
package Mancala;

import com.example.mancala.R;
import com.example.mancala.game.GameFramework.GameMainActivity;
import com.example.mancala.game.GameFramework.LocalGame;
import com.example.mancala.game.GameFramework.gameConfiguration.GameConfig;
import com.example.mancala.game.GameFramework.gameConfiguration.GamePlayerType;
import com.example.mancala.game.GameFramework.infoMessage.GameState;
import com.example.mancala.game.GameFramework.players.GamePlayer;

import java.util.ArrayList;

import Mancala.players.MancalaDumbComputerPlayer;
import Mancala.players.MancalaHumanPlayer;
import Mancala.players.MancalaSmarterComputerPlayer;

public class MancalaMainActivity extends GameMainActivity {

    public static final int PORT_NUMBER = 5213;
    @Override
    public GameConfig createDefaultConfig() {
        // Define the allowed player types
        ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();


        playerTypes.add(new GamePlayerType("Dumb Computer Player") {
            @Override
            public GamePlayer createPlayer(String name) {
                return new MancalaDumbComputerPlayer(name);
            }
        });

        playerTypes.add(new GamePlayerType("Local Human Player") {
            @Override
            public GamePlayer createPlayer(String name) {
                return new MancalaHumanPlayer(name, R.layout.activity_main); //this needs to be changed to the board_main.xml
            }
        });

        playerTypes.add(new GamePlayerType("Smart Computer Player") {
            @Override
            public GamePlayer createPlayer(String name) {
                return new MancalaSmarterComputerPlayer(name);
            }
        });

        GameConfig defaultConfig = new GameConfig(playerTypes,2,2, "Mancala", PORT_NUMBER);

        defaultConfig.addPlayer("Human",1);
        defaultConfig.addPlayer("Computer",0);

        return defaultConfig;

    }

    @Override
    public LocalGame createLocalGame(GameState gameState) {
        if(gameState == null) {
            return new MancalaLocalGame();
        }
        else {
            return new MancalaLocalGame((MancalaGameState) gameState);
        }

    }
}
