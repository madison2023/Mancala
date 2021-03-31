package Mancala;

import com.example.mancala.game.GameFramework.infoMessage.GameState;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author Rachel Madison, Henry Lee, Jordan Nakamura
 */
public class MancalaGameState extends GameState implements Serializable  {
    //arrays for each player will store number of marbles in the corresponding pocket
    //ex: if player0 has 4 marbles in their first pocket player0[0] = 4
    private int[] player0;
    private int[] player1;

    //stores who's turn it is
    private int whoseTurn;


    //private boolean rowIsEmpty; //if true game is over

    private int numMarbles;

    //constructor for objects of class MancalaGameState
    public MancalaGameState() {
        //initializing the number of marbles in each pocket
        player0 = new int[7];
        player1 = new int[7];
        //every pocket has 4 marbles at the beginning except the store which has 1
        for(int i = 0; i< player0.length - 1; i++){ //player0.length and player1.length will always be the same
            player0[i] = 4;
            player1[i] = 4;
        }
        player0[player0.length - 1] = 0;
        player1[player1.length - 1] = 0;

        //initializing booleans
//        isHumansTurn = true;
//        rowIsEmpty = false;
        whoseTurn = 0;
    }

    //copy Constructor
    public MancalaGameState(MancalaGameState original)
    {
        player0 = new int[7];
        player1 = new int[7];
        for(int i = 0; i< player0.length; i++){ //player0.length and player1.length will always be the same
            player0[i] = original.player0[i];
            player1[i] = original.player1[i];
        }

        //initializing booleans
        /*isHumansTurn = original.isHumansTurn;
        rowIsEmpty = original.rowIsEmpty;*/
        whoseTurn = original.whoseTurn;

    }

    @Override
    public String toString(){
        return "\nComputer Player's Pockets: " + Arrays.toString(player1) + "\nHuman Player's Pockets: "
                + Arrays.toString(player0) + "Whose turn: " + whoseTurn + "\nNumMarbles: " + numMarbles + "\n";
    }


//"\nisHumansTurn = " + isHumansTurn + "\nrowIsEmpty = " + rowIsEmpty +


    public boolean selectPit(int row, int col) { //columns labeled 0-6, where 0 is the first pocket and 6 is the store
        if(whoseTurn == 0) {  //human
            if(row == 1 && player0[col] != 0 && col != 6) { //cant make a move from an empty pit, one that isn't yours, or your store
                //set selected pit to zero
                numMarbles = player0[col];
                player0[col] = 0;
                //add one marble to each pit while there are still marbles going into other array if necessary
                addMarblesToPlayer0(col+1);
                //isHumansTurn = !isHumansTurn; //next player's turn
                return true;
            }
            else {
                return false;
            }
        }
        else {
            if (row == 1 && player1[col] != 0 && col != 6) { //cant make a move from an empty pit, one that isn't yours, or your store
                //set selected pit to zero
                numMarbles = player1[col];
                player1[col] = 0;
                //add one marble to each pit while there are still marbles going into other array if necessary
                addMarblesToPlayer1(col+1);
                //isHumansTurn = !isHumansTurn;
                return true;
            } else {
                return false;
            }
        }
    }

    public void addMarblesToPlayer0(int col){
        while(numMarbles > 0) {
            //second half of this if statement is making sure we don't add a marble to the wrong players store
            if(col != player0.length && !(col == 6 && whoseTurn == 1)) {
                player0[col] += 1;
                col++;
            }
            else {
                addMarblesToPlayer1( 0);//would start at the beginning of the marbles array
                return;
            }
            numMarbles--;
        }
    }

    public void addMarblesToPlayer1 (int col) {
        while(numMarbles > 0) {
            if(col != player1.length && !(col == 6 && whoseTurn == 0)) {
                player1[col] += 1;
                col++;
            }
            else {
                addMarblesToPlayer0(0); //would start at the beginning of the human array
                return;

            }
            numMarbles--;
        }
    }

    public boolean equals(Object object) {
        if(! (object instanceof MancalaGameState)) return false;
        MancalaGameState state = (MancalaGameState) object;


        for(int i = 0; i< this.player0.length; i++){ //player0.length and player1.length will always be the same
            if (this.player0[i] != state.player0[i] || this.player1[i] != state.player1[i]){
                return false;
            }
        }

        //initializing booleans
        if (this.whoseTurn != state.whoseTurn || this.numMarbles != state.numMarbles) {
            return false;
        }
        else {
            return true;
        }

    }

    public int[] getPlayer0() {
        return player0;
    }

    public int[] getPlayer1() {
        return player1;
    }



    public int getWhoseTurn() {
        return whoseTurn;
    }

    public void setWhoseTurn(int whoseTurn) {
        this.whoseTurn = whoseTurn;
    }
}
