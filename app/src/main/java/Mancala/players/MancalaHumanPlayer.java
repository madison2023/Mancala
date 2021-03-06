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
import android.widget.CompoundButton;
import android.widget.MediaController;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.mancala.R;
import com.example.mancala.game.GameFramework.GameMainActivity;
import com.example.mancala.game.GameFramework.infoMessage.GameInfo;
import com.example.mancala.game.GameFramework.infoMessage.GameOverInfo;
import com.example.mancala.game.GameFramework.infoMessage.IllegalMoveInfo;
import com.example.mancala.game.GameFramework.infoMessage.NotYourTurnInfo;
import com.example.mancala.game.GameFramework.players.GameHumanPlayer;
import com.example.mancala.game.GameFramework.utilities.Logger;

import java.io.IOException;

import Mancala.BoardView;
import Mancala.MancalaGameState;
import Mancala.MancalaMoveAction;

import static java.lang.Thread.sleep;

/**
 * Enables interaction with the GUI
 *
 * @author Rachel Madison
 * @author Jordan Nakamura
 * @author Henry Lee
 */
public class MancalaHumanPlayer extends GameHumanPlayer implements View.OnTouchListener,
                CompoundButton.OnCheckedChangeListener {

    // MancalaHumanPlayer instance variables
    private BoardView boardView;
    private TextView whoseTurnTextView;
    private Switch toggleMusic;
    private MediaPlayer mp1;
    private MediaPlayer mp2;

    /**
     * constructor
     * @param name the name of the player
     */
    public MancalaHumanPlayer(String name) {
        super(name);
    }

    /**
     * method to get topView
     * @return
     */
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
            //Using sound to indicate an invalid move
            mp1 = MediaPlayer.create(myActivity.getApplicationContext(), R.raw.error);
            mp1.start();
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
        }
    }

    /**
     * method to initalize UI
     * @param activity
     */
    @Override
    public void setAsGui(GameMainActivity activity) {
        //like oncreate
        activity.setContentView(R.layout.board_main);
        boardView = myActivity.findViewById(R.id.GameView);
        Logger.log("set listener","OnTouch");
        boardView.setOnTouchListener(this);
        this.whoseTurnTextView     = myActivity.findViewById(R.id.whoseTurnTextView);
        whoseTurnTextView.setText(""); //keeps it from saying "TextView" at the beginning
        toggleMusic = myActivity.findViewById(R.id.musicSwitch);
        toggleMusic.setOnCheckedChangeListener(this);
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

    /**
     * method to perform any initialization that needs to be done after the player
     * knows what their game-position and opponents' names are.
     */
    @Override
    protected void initAfterReady() {
        // do any action in the super class first
        super.initAfterReady();

        // set player positions with Human Player on the bottom and Computer Player on top
        MancalaGameState gameState = (MancalaGameState) game.getGameState();
        gameState.setPlayerBottom(playerNum);
        gameState.setWhoseTurn(playerNum);
        gameState.setPlayerTop(1-playerNum);

        //Background Music
        mp2 = MediaPlayer.create(myActivity.getApplicationContext(), R.raw.music);
        mp2.start();
        mp2.setLooping(true);

    }

    /**
     * method to perform action after the Game is over
     */
    @Override
    protected void afterGameOver() {
        // stop music after game is over
        mp2.stop();
    }

    /**
     * switch listener on the change event
     * @param compoundButton
     * @param isChecked
     */
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked && !mp2.isPlaying()){
            mp2.start();
        } else {
            mp2.pause();
        }
    }


}
