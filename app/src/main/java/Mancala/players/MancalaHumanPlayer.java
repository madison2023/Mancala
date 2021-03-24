package Mancala.players;

import android.view.View;

import com.example.mancala.R;
import com.example.mancala.game.GameFramework.GameMainActivity;
import com.example.mancala.game.GameFramework.infoMessage.GameInfo;
import com.example.mancala.game.GameFramework.players.GameHumanPlayer;
import com.example.mancala.game.GameFramework.utilities.Logger;

public class MancalaHumanPlayer extends GameHumanPlayer {
    private int layoutId;
    /**
     * constructor
     *
     * @param name the name of the player
     */
    public MancalaHumanPlayer(String name, int layoutId) {
        super(name);
        this.layoutId = layoutId;
    }

    @Override
    public View getTopView() {
        return myActivity.findViewById(R.id.GameView);
    }

    @Override
    public void receiveInfo(GameInfo info) {

    }

    @Override
    public void setAsGui(GameMainActivity activity) {
        //like oncreate
        // Load the layout resource for the new configuration
        activity.setContentView(layoutId);

        /*// set the surfaceView instance variable
        surfaceView = (TTTSurfaceView)myActivity.findViewById(R.id.surfaceView);
        Logger.log("set listener","OnTouch");
        surfaceView.setOnTouchListener(this);*/
    }
}
