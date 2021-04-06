package Mancala;



import com.example.mancala.game.GameFramework.players.GamePlayer;
import com.example.mancala.game.GameFramework.actionMessage.GameAction;

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

    public int getRow() {
        return row;
    }

    public int getCol(){
        return col;
    }
}
