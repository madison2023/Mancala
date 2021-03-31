package Mancala.players;

import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.mancala.R;
import com.example.mancala.game.GameFramework.GameMainActivity;
import com.example.mancala.game.GameFramework.infoMessage.GameInfo;
import com.example.mancala.game.GameFramework.players.GameHumanPlayer;
import com.example.mancala.game.GameFramework.utilities.Logger;

import Mancala.BoardView;

public class MancalaHumanPlayer extends GameHumanPlayer implements View.OnTouchListener {
    private int layoutId;
    private BoardView boardView;
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
        return myActivity.findViewById(R.id.top_gui_layout);
    }

    @Override
    public void receiveInfo(GameInfo info) {

    }

    @Override
    public void setAsGui(GameMainActivity activity) {
        //like oncreate
        // Load the layout resource for the new configuration
        activity.setContentView(R.layout.board_main);

        /*// set the surfaceView instance variable
        surfaceView = (TTTSurfaceView)myActivity.findViewById(R.id.surfaceView);
        Logger.log("set listener","OnTouch");
        surfaceView.setOnTouchListener(this);*/

        /*Spinner spinner = myActivity.findViewById(R.id.difficulty);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.difficulty, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);*/

        /*boardView = (BoardView)myActivity.findViewById(R.id.GameView);
        boardView.setOnTouchListener(this);*/
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

   /* @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }*/
}
