package Mancala.actions;

import com.example.mancala.game.GameFramework.actionMessage.GameAction;
import com.example.mancala.game.GameFramework.players.GamePlayer;

public class MancalaMoveAction extends GameAction {
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public MancalaMoveAction(GamePlayer player) {
        super(player);
    }
}
