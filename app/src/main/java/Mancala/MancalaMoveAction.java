package Mancala;

import com.example.mancala.game.GameFramework.players.GamePlayer;
import com.example.mancala.game.GameFramework.actionMessage.GameAction;

/**
 * Creates the action of moving marbles
 *
 * @author Henry Lee
 */
public class MancalaMoveAction extends GameAction{

    private int row;
    private int col;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public MancalaMoveAction(GamePlayer player, int row, int col) {
        super(player);

        this.row = row;
        this.col = col;
    }

    // gets the board row
    public int getRow() {
        return row;
    }

    // gets the board column
    public int getCol(){
        return col;
    }
}
