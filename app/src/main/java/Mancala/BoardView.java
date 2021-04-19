package Mancala;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;

import com.example.mancala.game.GameFramework.utilities.FlashSurfaceView;

import java.util.ArrayList;

/**
 * A surface view that has the board and marbles drawn on it
 * @author Jordan Nakamura
 * @author Henry Lee
 * @author Rachel Madison
 */
public class BoardView extends FlashSurfaceView {

    private final static float BORDER_PERCENT = 5;
    private final static float POCKET_SIZE_PERCENT = 1.5f;
    private final static float MARBLE_SIZE_PERCENT = 0.05f;

    //centers of Pits
    //Player A is the bottom row Player B is top row
    //Position A1 is the left bottom, position A6 is bottom right
    //Position B1 is top right, position B6 is top left
    float cxA1;
    float cxA2;
    float cxA3;
    float cxA4;
    float cxA5;
    float cxA6;

    // y for all the bottom row pockets
    float cyA;

    float cxB1;
    float cxB2;
    float cxB3;
    float cxB4;
    float cxB5;
    float cxB6;
    //y for all of the B pockets
    float cyB;

    //colors
    Paint black = new Paint();
    Paint brown = new Paint();
    Paint lightBrown = new Paint();
    Paint lightBlue = new Paint();
    Paint number = new Paint();
    Paint text = new Paint();

    private MancalaGameState state;

    private ArrayList <PointF> pointArrayList;
    private int player0Score;
    private int player1Score;

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        setBackgroundColor(Color.WHITE);

        black.setColor(Color.BLACK);
        brown.setColor(Color.rgb(117,65,45));
        lightBrown.setColor(Color.rgb(205,133,63));
        lightBlue.setColor(Color.rgb(74,164,176));
        number.setColor(Color.BLACK);
        number.setTextAlign(Paint.Align.CENTER);
        number.setTextSize(28);
        text.setColor(Color.BLACK);
        text.setTextAlign(Paint.Align.CENTER);
        text.setTextSize(50);

        pointArrayList = new ArrayList<>();
        player0Score = 0;
        player1Score = 0;
    }

    //draws blue marble outlined in black
    public void drawMarble(float cx, float cy, Canvas canvas){
        canvas.drawCircle(cx,cy,p(MARBLE_SIZE_PERCENT) + 2f,black);
        canvas.drawCircle(cx,cy,p(MARBLE_SIZE_PERCENT),lightBlue);
    }

    //draws marbles for each pit
    //all same distance from center of pit
    public void drawPitMarbles(float cx, float cy, int numMarbles, Canvas canvas, boolean isTopRow) {

        float textCyTopRow = ((v(BORDER_PERCENT*4)+((v(100 - (BORDER_PERCENT*4)) - v(BORDER_PERCENT*4))/10)) + v(BORDER_PERCENT*4)) / 2;
        float textCyBottomRow = ((v(100 - (BORDER_PERCENT*4))-((v(100 - (BORDER_PERCENT*4)) - v(BORDER_PERCENT*4))/10)) + (v(100 - (BORDER_PERCENT*4)))) / 2;

        float radius = p(POCKET_SIZE_PERCENT)/2; //= 50.0f;
        double angle = 0.0;

        for(int i = 0; i < numMarbles; i++) {
            drawMarble((float) (cx + radius*Math.cos(angle)), (float) (cy + radius*Math.sin(angle)), canvas);
            angle += 2*Math.PI/numMarbles;
        }

        //draw number of marbles for the pits
        if(isTopRow){
            drawMarblesNumber(cx, textCyTopRow, numMarbles, canvas);
        } else{
            drawMarblesNumber(cx,textCyBottomRow , numMarbles, canvas);
        }

    }

    //spreads marbles out more since more marbles will be in the 2 stores at either end
    //uses random arrangement instead of circular
    public void drawStoreMarbles(float cx, float cy, int numMarbles, Canvas canvas, boolean isLeft) {
        //coordinates for text
        float textCxLeftStore = (h(BORDER_PERCENT) + h(BORDER_PERCENT)+((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/45))/2;
        float textCxRightStore = (h(100 - BORDER_PERCENT) + h(100 - BORDER_PERCENT)-((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/45))/2;

        float radius = (h(BORDER_PERCENT)+((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/7.18f)
                - h(BORDER_PERCENT)+((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/40f))/2 - (p(MARBLE_SIZE_PERCENT) + 2f)*4; //4 times marble radius is making sure it doesnt get drawn on the edge
        double angle = 0.0;

        int numMarblesToAdd = 0;
        if(!isLeft && numMarbles > player0Score) {
            numMarblesToAdd = numMarbles - player0Score;
            player0Score = numMarbles;
        }
        else if (isLeft && numMarbles > player1Score){
            numMarblesToAdd = numMarbles - player1Score;
            player1Score = numMarbles;
        }

        for(int i = 0; i < numMarblesToAdd; i++) {
            angle += 2*Math.PI/(Math.random()*10);
            float centerx = (float) (cx + radius*Math.random()*Math.cos(angle));
            float centery = (float) (cy + radius*Math.random()*Math.sin(angle));
            pointArrayList.add(new PointF(centerx,centery));
            //drawMarble(centerx,centery,canvas);
            //angle = 2*Math.PI/(Math.random()*8);
        }

        for(PointF pointF : pointArrayList){
            drawMarble(pointF.x,pointF.y,canvas);
        }

        //draw the number of marbles for the store
        if(isLeft){
            drawMarblesNumber(textCxLeftStore,cy, numMarbles, canvas);
        } else{
            drawMarblesNumber(textCxRightStore,cy, numMarbles, canvas);
        }
    }

    //draw the number
    public void drawMarblesNumber(float cx, float cy, int numMarbles, Canvas canvas){
        String numberStr = new Integer(numMarbles).toString();
        canvas.drawText(numberStr, cx, cy + 5f, number);
    }

    /** draws pockets at coordinates cx and cy with specified radius */
    public void drawPocket(Canvas canvas, float cx, float cy, float radius){
        // Outer circle
        black.setColor(Color.BLACK);
        canvas.drawCircle(cx ,cy, radius, black);

        // Inner circle
        lightBrown.setColor(Color.rgb(205,133,63));
        canvas.drawCircle(cx ,cy, radius - 5, lightBrown);
    }

    public void drawTurnName(Canvas canvas, boolean isComputersTurn) {
        if (isComputersTurn) {
            canvas.drawText("Computer's Turn", h(BORDER_PERCENT) + (h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/2, v(100 - (BORDER_PERCENT*4)) + 100, text);
        }
        else {
            canvas.drawText("Your Turn", h(BORDER_PERCENT) + (h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/2, v(100 - (BORDER_PERCENT*4)) + 100, text);
        }
    }



    /** onDraw method draws the game board and pockets. */
    public void onDraw(Canvas canvas){

        setPoints();

        /*// pocket circle radius
        float radius = 90f;*/

        //centers of stores
        float cxLeftStore = ((h(BORDER_PERCENT) + ((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT)) / 45)) + (h(BORDER_PERCENT) + ((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT)) / 7))) / 2;
        float cxRightStore = ((h(100 - BORDER_PERCENT) - ((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT)) / 45)) + (h(100 - BORDER_PERCENT) - (h(100 - BORDER_PERCENT) - h(BORDER_PERCENT)) / 7)) / 2;
        float cyStore = (cyA + cyB) / 2;

        canvas.drawRect(h(BORDER_PERCENT) - 10,v(BORDER_PERCENT*4) - 10,h(100 - BORDER_PERCENT) + 10, v(100 - (BORDER_PERCENT*4)) + 10, black);
        canvas.drawRect(h(BORDER_PERCENT),v(BORDER_PERCENT*4),h(100 - BORDER_PERCENT), v(100 - (BORDER_PERCENT*4)), brown);

        // Draws left oval
        canvas.drawOval(h(BORDER_PERCENT)+((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/45), v(BORDER_PERCENT*4)+((v(100 - (BORDER_PERCENT*4)) - v(BORDER_PERCENT*4))/10), h(BORDER_PERCENT)+((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/7), v(100 - (BORDER_PERCENT*4))-((v(100 - (BORDER_PERCENT*4)) - v(BORDER_PERCENT*4))/10), black);
        canvas.drawOval(h(BORDER_PERCENT)+((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/40f), v(BORDER_PERCENT*4)+((v(100 - (BORDER_PERCENT*4)) - v(BORDER_PERCENT*4))/9), h(BORDER_PERCENT)+((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/7.18f), v(100 - (BORDER_PERCENT*4))-((v(100 - (BORDER_PERCENT*4)) - v(BORDER_PERCENT*4))/9), lightBrown);

        // Draws right oval
        canvas.drawOval(h(100 - BORDER_PERCENT)-((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/45), v(BORDER_PERCENT*4)+((v(100 - (BORDER_PERCENT*4)) - v(BORDER_PERCENT*4))/10), h(100 - BORDER_PERCENT)-((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/7), v(100 - (BORDER_PERCENT*4))-((v(100 - (BORDER_PERCENT*4)) - v(BORDER_PERCENT*4))/10), black);
        canvas.drawOval(h(100 - BORDER_PERCENT)-((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/40f), v(BORDER_PERCENT*4)+((v(100 - (BORDER_PERCENT*4)) - v(BORDER_PERCENT*4))/9), h(100 - BORDER_PERCENT)-((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/7.18f), v(100 - (BORDER_PERCENT*4))-((v(100 - (BORDER_PERCENT*4)) - v(BORDER_PERCENT*4))/9), lightBrown);

        for(int i = 1; i <= 6; i++){
            float a = (h(BORDER_PERCENT) + ((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/7));
            float b = (h(100 - BORDER_PERCENT)-((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/7));
            // Draws top row of pockets
            float cx = a / 1.55f + (i * 1.2f * (b - a)) / 7f;
            float cyTop = (v(BORDER_PERCENT*4)+((v(100 - (BORDER_PERCENT*4)) - v(BORDER_PERCENT*4))/10)) + p(POCKET_SIZE_PERCENT);
            drawPocket(canvas, cx, cyTop, p(POCKET_SIZE_PERCENT));

            // Draws bottom row of pockets
            float cyBottom = (v(100 - (BORDER_PERCENT*4))-((v(100 - (BORDER_PERCENT*4)) - v(BORDER_PERCENT*4))/10)) - p(POCKET_SIZE_PERCENT);
            drawPocket(canvas, cx, cyBottom, p(POCKET_SIZE_PERCENT));
        }

        if(state == null) {
            state = new MancalaGameState();
        }

        int[] player0 = state.getPlayer0();
        int[] player1 = state.getPlayer1();

        //draw pit marbles for player A
        drawPitMarbles(cxA1, cyA, player0[0], canvas, false);
        drawPitMarbles(cxA2, cyA, player0[1], canvas, false);
        drawPitMarbles(cxA3, cyA, player0[2], canvas, false);
        drawPitMarbles(cxA4, cyA, player0[3], canvas, false);
        drawPitMarbles(cxA5, cyA, player0[4], canvas, false);
        drawPitMarbles(cxA6, cyA, player0[5], canvas, false);

        //draw pit marbles for player B
        drawPitMarbles(cxB6, cyB, player1[5], canvas, true);
        drawPitMarbles(cxB5, cyB, player1[4], canvas, true);
        drawPitMarbles(cxB4, cyB, player1[3], canvas,true);
        drawPitMarbles(cxB3, cyB, player1[2], canvas,true);
        drawPitMarbles(cxB2, cyB, player1[1], canvas,true);
        drawPitMarbles(cxB1, cyB, player1[0], canvas,true);

        //store marbles for player A
        drawStoreMarbles(cxRightStore, cyStore, player0[6], canvas, false);

        //store marbles for player B
        drawStoreMarbles(cxLeftStore, cyStore, player1[6], canvas, true);

        //draw Text that says whose turn it is
        /*if (state.getWhoseTurn() == 0) {
            drawTurnName(canvas, false); //need to change this so that it doesn't rely on human always being player 0
        }
        else {
            drawTurnName(canvas, true);
        }*/
        //canvas.drawText(state.getPlayerName() +"'s Turn", boardOuterLeft + boardWidth/2, boardOuterBottom + 100, text);
    }


    public void setState(MancalaGameState state) {
        this.state = state;
    }

    private boolean checkPointInCircle(float x,float y, float cx, float cy) {
        //using equation (x-a)^2+(y-b)^2 == r^2 where (a,b) is the center of the circle

        return (x - cx) * (x - cx) + (y - cy) * (y - cy) <= p(POCKET_SIZE_PERCENT)*p(POCKET_SIZE_PERCENT);
    }

    public void setPoints(){
        cxA1 = (h(BORDER_PERCENT) + ((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/7)) / 1.55f + (1 * 1.2f * ((h(100 - BORDER_PERCENT)-((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/7)) - (h(BORDER_PERCENT) + ((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/7)))) / 7f;
        cxA2 = (h(BORDER_PERCENT) + ((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/7)) / 1.55f + (2 * 1.2f * ((h(100 - BORDER_PERCENT)-((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/7)) - (h(BORDER_PERCENT) + ((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/7)))) / 7f;
        cxA3 = (h(BORDER_PERCENT) + ((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/7)) / 1.55f + (3 * 1.2f * ((h(100 - BORDER_PERCENT)-((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/7)) - (h(BORDER_PERCENT) + ((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/7)))) / 7f;
        cxA4 = (h(BORDER_PERCENT) + ((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/7)) / 1.55f + (4 * 1.2f * ((h(100 - BORDER_PERCENT)-((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/7)) - (h(BORDER_PERCENT) + ((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/7)))) / 7f;
        cxA5 = (h(BORDER_PERCENT) + ((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/7)) / 1.55f + (5 * 1.2f * ((h(100 - BORDER_PERCENT)-((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/7)) - (h(BORDER_PERCENT) + ((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/7)))) / 7f;
        cxA6 = (h(BORDER_PERCENT) + ((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/7)) / 1.55f + (6 * 1.2f * ((h(100 - BORDER_PERCENT)-((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/7)) - (h(BORDER_PERCENT) + ((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/7)))) / 7f;
        //y for all of the A pockets
        cyA = (v(100 - (BORDER_PERCENT*4))-((v(100 - (BORDER_PERCENT*4)) - v(BORDER_PERCENT*4))/10)) - p(POCKET_SIZE_PERCENT);

        cxB1 = (h(BORDER_PERCENT) + ((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/7)) / 1.55f + (6 * 1.2f * ((h(100 - BORDER_PERCENT)-((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/7)) - (h(BORDER_PERCENT) + ((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/7)))) / 7f;
        cxB2 = (h(BORDER_PERCENT) + ((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/7)) / 1.55f + (5 * 1.2f * ((h(100 - BORDER_PERCENT)-((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/7)) - (h(BORDER_PERCENT) + ((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/7)))) / 7f;
        cxB3 = (h(BORDER_PERCENT) + ((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/7)) / 1.55f + (4 * 1.2f * ((h(100 - BORDER_PERCENT)-((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/7)) - (h(BORDER_PERCENT) + ((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/7)))) / 7f;
        cxB4 = (h(BORDER_PERCENT) + ((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/7)) / 1.55f + (3 * 1.2f * ((h(100 - BORDER_PERCENT)-((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/7)) - (h(BORDER_PERCENT) + ((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/7)))) / 7f;
        cxB5 = (h(BORDER_PERCENT) + ((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/7)) / 1.55f + (2 * 1.2f * ((h(100 - BORDER_PERCENT)-((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/7)) - (h(BORDER_PERCENT) + ((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/7)))) / 7f;
        cxB6 = (h(BORDER_PERCENT) + ((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/7)) / 1.55f + (1 * 1.2f * ((h(100 - BORDER_PERCENT)-((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/7)) - (h(BORDER_PERCENT) + ((h(100 - BORDER_PERCENT) - h(BORDER_PERCENT))/7)))) / 7f;
        // y for all the B pockets
        cyB = (v(BORDER_PERCENT*4)+((v(100 - (BORDER_PERCENT*4)) - v(BORDER_PERCENT*4))/10)) + p(POCKET_SIZE_PERCENT);
    }

    public Point mapPixelToPit(float x, float y) {
        //row 0 is bottom row
        if (checkPointInCircle(x,y,cxA1,cyA)) {
            return new Point(0,0);
        }
        else if (checkPointInCircle(x,y,cxA2,cyA)) {
            return new Point(0,1);
        }
        else if (checkPointInCircle(x,y,cxA3,cyA)) {
            return new Point(0,2);
        }
        else if (checkPointInCircle(x,y,cxA4,cyA)) {
            return new Point(0,3);
        }
        else if (checkPointInCircle(x,y,cxA5,cyA)) {
            return new Point(0,4);
        }
        else if (checkPointInCircle(x,y,cxA6,cyA)) {
            return new Point(0,5);
        }
        else if (checkPointInCircle(x,y,cxB1,cyB)) {
            return new Point(1,0);
        }
        else if (checkPointInCircle(x,y,cxB2,cyB)) {
            return new Point(1,1);
        }
        else if (checkPointInCircle(x,y,cxB3,cyB)) {
            return new Point(1,2);
        }
        else if (checkPointInCircle(x,y,cxB4,cyB)) {
            return new Point(1,3);
        }
        else if (checkPointInCircle(x,y,cxB5,cyB)) {
            return new Point(1,4);
        }
        else if (checkPointInCircle(x,y,cxB6,cyB)) {
            return new Point(1,5);
        }
        else {
            return null;
        }
    }
    private float h(float percent) {
        float width = getWidth();
        return percent / 100 * width;
    }
    private float v(float percent) {
        float height = getHeight();
        return percent / 100 * height;
    }

    private float p(float percent) {
        float width = getWidth();
        float height = getHeight();
        return (float) Math.sqrt(percent / 100 * (height * width) / (float)Math.PI);
    }
}


