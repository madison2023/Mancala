package Mancala;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;

public class BoardView extends SurfaceView {
    // pocket circle radius
    float radius = 90f;



    //measurements of board
    float boardWidth = 1780.0f;
    float boardHeight = 480.0f;
    float boardOutterLeft = 100.0f; //spelled wrong?
    float boardInnerLeft = 110.0f;
    float boardOutterRight = 1900.0f;
    float boardInnerRight = 1890.0f;
    float boardOutterTop = 200.0f;
    float boardInnerTop = 210.0f;
    float boardOutterBottom = 685.0f;
    float boardInnerBottom = 675.0f;
    float ovalTop = 248f;
    float ovalBottom = 637f;

    //centers of Stores
    float cxLeftStore = ((boardOutterLeft+(boardWidth/45))+(boardOutterLeft+(boardWidth/7)))/2;
    float cyLeftStore = ((boardOutterTop+(boardHeight/10))+boardOutterBottom-(boardHeight/10))/2;
    float cxRightStore = ((boardOutterRight-(boardWidth/45))+(boardOutterRight-boardWidth/7))/2;
    float cyRightStore = ((boardOutterTop+(boardHeight/10))+boardOutterBottom-(boardHeight/10))/2;

    //centers of Pits
    //Player A is the bottom row Player B is top row
    //Position A1 is the left bottom, position A6 is bottom right
    //Position B1 is top right, position B6 is top left
    float cxA1 = 363.78f + radius + (0*(1487.1f/7f));
    float cxA2 = 363.78f + radius + (1*(1487.1f/7f));
    float cxA3 = 363.78f + radius + (2*(1487.1f/7f));
    float cxA4 = 363.78f + radius + (3*(1487.1f/7f));
    float cxA5 = 363.78f + radius + (4*(1487.1f/7f));
    float cxA6 = 363.78f + radius + (5*(1487.1f/7f));
    //y for all of the A pockets
    float cyA = ovalBottom - radius;

    float cxB1 = 363.78f + radius + (5 * (1487.1f/7f));
    float cxB2 = 363.78f + radius + (4 * (1487.1f/7f));
    float cxB3 = 363.78f + radius + (3 * (1487.1f/7f));
    float cxB4 = 363.78f + radius + (2 * (1487.1f/7f));
    float cxB5 = 363.78f + radius + (1 * (1487.1f/7f));
    float cxB6 = 363.78f + radius + (0 * (1487.1f/7f));
    //y for all of the B pockets
    float cyB = ovalTop + radius;

    //coordinates for text
    float textCxLeftStore = (boardInnerLeft + boardOutterLeft+(boardWidth/45))/2;
    float textCxRightStore = (boardInnerRight + boardOutterRight-(boardWidth/45))/2;
    float textCyTopRow = boardInnerTop + (3*(ovalTop-boardInnerTop)/4);
    float textCyBottomRow = boardInnerBottom - ((boardInnerBottom-ovalBottom)/4);


    //colors
    Paint black = new Paint();
    Paint brown = new Paint();
    Paint lightBrown = new Paint();
    Paint lightBlue = new Paint();
    Paint number = new Paint();
    Paint text = new Paint();



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

    }

    //draws blue marble outlined in black
    public void drawMarble(float cx, float cy, Canvas canvas){
        canvas.drawCircle(cx,cy,22.0f,black);
        canvas.drawCircle(cx,cy,20.0f,lightBlue);
    }

    //draws marbles for each pit
    //all same distance from center of pit
    public void drawPitMarbles(float cx, float cy, int numMarbles, Canvas canvas, boolean isTopRow) {
        float radius = 50.0f;
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
        float radius = 80.0f;
        double angle = 0.0;
        for(int i = 0; i < numMarbles; i++) {
            float centerx = (float) (cx + radius*Math.random()*Math.cos(angle));
            float centery = (float) (cy + radius*Math.random()*Math.sin(angle));
            drawMarble(centerx,centery,canvas);
            angle += 2*Math.PI/(Math.random()*15);
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
        canvas.drawText(numberStr, cx, cy, number);
    }

    /** draws pockets at coordinates cx and cy with specified radius */
    public void drawPocket(Canvas canvas, float cx, float cy, float radius){
        // Outter circle
        black.setColor(Color.BLACK);
        canvas.drawCircle(cx ,cy, radius, black);

        // Inner circle
        lightBrown.setColor(Color.rgb(205,133,63));
        canvas.drawCircle(cx ,cy, radius - 5, lightBrown);
    }

    public void drawTurnName(Canvas canvas, boolean isComputersTurn) {
        if (isComputersTurn) {
            canvas.drawText("Computer's Turn", boardOutterLeft + boardWidth/2, boardOutterBottom + 100, text);
        }
        else {
            canvas.drawText("Your Turn", boardOutterLeft + boardWidth/2, boardOutterBottom + 100, text);
        }
    }

    /** onDraw method draws the game board and pockets. */
    public void onDraw(Canvas canvas){

        float width = canvas.getWidth();
        float height = canvas.getHeight();


        // Draws board
        canvas.drawRect(boardOutterLeft,boardOutterTop,boardOutterRight, boardOutterBottom, black);
        canvas.drawRect(boardInnerLeft,boardInnerTop, boardInnerRight, boardInnerBottom, brown);

        // Draws left oval
        canvas.drawOval(boardOutterLeft+(boardWidth/45), boardOutterTop+(boardHeight/10), boardOutterLeft+(boardWidth/7), boardOutterBottom-(boardHeight/10), black);
        canvas.drawOval(boardOutterLeft+(boardWidth/40f), boardOutterTop+(boardHeight/9), boardOutterLeft+(boardWidth/7.18f), boardOutterBottom-(boardHeight/9), lightBrown);

        // Draws right oval
        canvas.drawOval(boardOutterRight-(boardWidth/45), boardOutterTop+(boardHeight/10), boardOutterRight-(boardWidth/7), boardOutterBottom-(boardHeight/10), black);
        canvas.drawOval(boardOutterRight-(boardWidth/40f), boardOutterTop+(boardHeight/9), boardOutterRight-(boardWidth/7.18f), boardOutterBottom-(boardHeight/9), lightBrown);



        for(int i = 0; i < 6; i++){
            // Draws top row of pockets
            float cxTop = 363.78f + radius + (i * (1487.1f/7f));
            float cyTop = ovalTop + radius;
            drawPocket(canvas, cxTop, cyTop, radius);

            // Draws bottom row of pockets
            float cxBottom = 363.78f + radius + (i * (1487.1f/7f));
            float cyBottom = ovalBottom - radius;
            drawPocket(canvas, cxBottom, cyBottom, radius);
        }
        //draw pit marbles for player A
        drawPitMarbles(cxA1, cyA, 6, canvas, false);
        drawPitMarbles(cxA2, cyA, 0, canvas, false);
        drawPitMarbles(cxA3, cyA, 3, canvas, false);
        drawPitMarbles(cxA4, cyA, 7, canvas, false);
        drawPitMarbles(cxA5, cyA, 7, canvas, false);
        drawPitMarbles(cxA6, cyA, 0, canvas, false);

        //draw pit marbles for player B
        drawPitMarbles(cxB6, cyB, 0, canvas, true);
        drawPitMarbles(cxB5, cyB, 7, canvas, true);
        drawPitMarbles(cxB4, cyB, 3, canvas,true);
        drawPitMarbles(cxB3, cyB, 0, canvas,true);
        drawPitMarbles(cxB2, cyB, 0, canvas,true);
        drawPitMarbles(cxB1, cyB, 0, canvas,true);

        //store marbles for player A
        drawStoreMarbles(cxRightStore, cyRightStore, 3, canvas, false);

        //store marbles for player B
        drawStoreMarbles(cxLeftStore, cyLeftStore, 6, canvas, true);

        //draw Text that says whose turn it is
        drawTurnName(canvas, false);


    }


}


