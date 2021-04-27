package Mancala.players;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mancala.R;
import com.example.mancala.game.GameFramework.GameMainActivity;
import com.example.mancala.game.GameFramework.infoMessage.GameInfo;
import com.example.mancala.game.GameFramework.infoMessage.IllegalMoveInfo;
import com.example.mancala.game.GameFramework.infoMessage.NotYourTurnInfo;
import com.example.mancala.game.GameFramework.players.GameHumanPlayer;
import com.example.mancala.game.GameFramework.utilities.Logger;

import Mancala.BoardView;
import Mancala.MancalaGameState;
import Mancala.MancalaMoveAction;

import static java.lang.Thread.sleep;

/**
 * Enables interaction with the GUI
 * @author Rachel Madison
 */
public class MancalaHumanPlayer extends GameHumanPlayer implements View.OnTouchListener {

    private BoardView boardView;
    private TextView whoseTurnTextView;
    private MediaPlayer mp;

    /**
     * constructor
     * @param name the name of the player
     */
    public MancalaHumanPlayer(String name) {
        super(name);

    }

    @Override
    public View getTopView() {
        return myActivity.findViewById(R.id.top_gui_layout);
    }

    /**
     External Citation
     Date:     3 April 2020
     Problem:  Could not figure out how to set up receive info
     Resource: TicTacToe example code
     Solution: used the structure from tictactoe's receive info
     */
    @Override
    public void receiveInfo(GameInfo info) {
        if (boardView == null) return;

        if (info instanceof IllegalMoveInfo || info instanceof NotYourTurnInfo) {
            // flashing screen for an illegal move
            boardView.flash(Color.RED, 50);
            //Using sound to indicate an invalid move
            mp = MediaPlayer.create(myActivity.getApplicationContext(), R.raw.error);
            mp.start();
            Log.d("Sound", "Error Sound");


        }
        if (!(info instanceof MancalaGameState)) {
            return;
        }
        else {
            MancalaGameState mancalaGameState = (MancalaGameState) info;
            boardView.setState(mancalaGameState);
            whoseTurnTextView.setText(allPlayerNames[mancalaGameState.getWhoseTurn()] + "'s Turn");
            boardView.invalidate();
            //Logger.log(TAG, "receiving");
        }
    }

    @Override
    public void setAsGui(GameMainActivity activity) {
        //like oncreate
        activity.setContentView(R.layout.board_main);
        boardView = myActivity.findViewById(R.id.GameView);
        Logger.log("set listener","OnTouch");
        boardView.setOnTouchListener(this);
        this.whoseTurnTextView     = myActivity.findViewById(R.id.whoseTurnTextView);
        whoseTurnTextView.setText(""); //keeps it from saying "TextView" at the beginning
    }

    /**
     External Citation
     Date:     3 April 2020
     Problem:  Didn't know how to handle touch action
     Resource: TicTacToe example code
     Solution: used the structure from tictactoe's onTouch since both games are
                coordinate based for the actions
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //ignoring if action is not up
        if (event.getAction() != MotionEvent.ACTION_UP) return true;

        float x = event.getX();
        float y = event.getY();
        Point point = boardView.mapPixelToPit(x,y);

        if (point != null) {

            MancalaMoveAction action = new MancalaMoveAction(this, point.x, point.y);
            game.sendAction(action);
            //Clicking sound
            v.playSoundEffect(SoundEffectConstants.CLICK);
            Log.d("Sound", "Click Sound");
            boardView.invalidate();
        }

        return true;
    }

    @Override
    protected void initAfterReady() {
        super.initAfterReady();

        MancalaGameState gameState = (MancalaGameState) game.getGameState();
        gameState.setPlayerBottom(playerNum);
        gameState.setWhoseTurn(playerNum);
        gameState.setPlayerTop(1-playerNum);
    }


    /* @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }*/
}
