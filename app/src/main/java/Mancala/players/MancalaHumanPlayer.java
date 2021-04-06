package Mancala.players;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Point;
import android.view.MotionEvent;
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

public class MancalaHumanPlayer extends GameHumanPlayer implements View.OnTouchListener {

    private BoardView boardView;
    private TextView whoseTurnTextView;
    /**
     * constructor
     *
     * @param name the name of the player
     */
    public MancalaHumanPlayer(String name) {
        super(name);

    }

    @Override
    protected void initAfterReady() {
        super.initAfterReady();
        whoseTurnTextView.setText(allPlayerNames[0]);
    }

    @Override
    public View getTopView() {
        return myActivity.findViewById(R.id.top_gui_layout);
    }

    @Override
    public void receiveInfo(GameInfo info) {
        if (boardView == null) return;

        if (info instanceof IllegalMoveInfo || info instanceof NotYourTurnInfo) {
            // flashing screen for an illegal move
            boardView.flash(Color.RED, 50);
        }
        if (!(info instanceof MancalaGameState)) {
            return;
        }
        else {
            MancalaGameState mancalaGameState = (MancalaGameState)info;
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



        /*Spinner spinner = myActivity.findViewById(R.id.difficulty);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.difficulty, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);*/

        boardView = myActivity.findViewById(R.id.GameView);
        Logger.log("set listener","OnTouch");
        boardView.setOnTouchListener(this);
        this.whoseTurnTextView     = (TextView)myActivity.findViewById(R.id.whoseTurnTextView);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //ignoring if action is not up
        if (event.getAction() != MotionEvent.ACTION_UP) return true;

        float x = event.getX();
        float y = event.getY();
        //map to pixel here
        Point p = boardView.mapPixelToPit(x,y);
        if (p != null) {
            MancalaMoveAction action = new MancalaMoveAction(this, p.x, p.y);
            game.sendAction(action);
            boardView.invalidate();
        }
        return true;
    }

   /* @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }*/
}
