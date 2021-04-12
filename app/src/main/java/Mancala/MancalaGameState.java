package Mancala;

import com.example.mancala.game.GameFramework.infoMessage.GameState;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Has the state of a Mancala game, contains information about where the marbles are,
 * the player's score, where the last marble landed, etc
 * @author Rachel Madison, Henry Lee, Jordan Nakamura
 */
public class MancalaGameState extends GameState implements Serializable  {

    //arrays for each player will store number of marbles in the corresponding pocket
    //ex: if player0 has 4 marbles in their first pocket player0[0] = 4
    private int[] player0;
    private int[] player1;

    private int whoseTurn;
    private int lastRow;
    private int lastCol;

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


        whoseTurn = original.whoseTurn;
        lastCol = original.lastCol;
        lastRow = original.lastRow;


    }

    @Override
    public String toString(){
        return "\nComputer Player's Pockets: " + Arrays.toString(player1) + "\nHuman Player's Pockets: "
                + Arrays.toString(player0) + "Whose turn: " + whoseTurn + "\nLast Column: "
                    + lastCol + "\nLast Row: " + lastRow;
    }






    /**
     * the player selects a pit/pocket and then the pieces are moved according to the rules of the game
     * @param row   the bottom is row 0 and top is row 1
     * @param col   columns labeled 0-6, where 0 is the first pocket and 6 is the store
     * @return  true if successfully select a pit and false otherwise
     */
    public boolean selectPit(int row, int col) {
        if(whoseTurn == 0) {
                //set selected pit to zero
                int numMarbles = player0[col];
                player0[col] = 0;
                //add one marble to each pit while there are still marbles going into other array if necessary
                addMarblesToPlayer0(col+1, numMarbles);
                return true;
        }
        else if (whoseTurn == 1){
                //set selected pit to zero
                int numMarbles = player1[col];
                player1[col] = 0;
                //add one marble to each pit while there are still marbles going into other array if necessary
                addMarblesToPlayer1(col+1, numMarbles);
                return true;
        }
        else {
            return false;
        }
    }


    /**
     * adds one marble to each pit in player0's array until they run out of marbles or need to go
     * into the other player's array
     * @param col the column we start adding marbles at
     */
    public void addMarblesToPlayer0(int col, int numMarbles){
        while(numMarbles > 0) {
            //making sure we don't add a marble to the wrong players store or go out of bounds
            if(col != player0.length && !(col == 6 && whoseTurn == 1)) {
                //getting the row and col of the last place we land, important for special cases
                //like capturing and getting another turn
                if(numMarbles == 1) {
                    lastRow = 0;
                    lastCol = col;
                }

                player0[col] += 1;
                col++;
            }
            else {
                addMarblesToPlayer1( 0, numMarbles);//would start at the beginning of the marbles array
                return;
            }
            numMarbles--;
        }
    }

    /**
     * adds one marble to each pit in player1's array until they run out of marbles or need to go
     * into the other player's array
     *
     * @param col the column we start adding marbles at
     */
    public void addMarblesToPlayer1 (int col, int numMarbles) {
        while(numMarbles > 0) {
            if(col != player1.length && !(col == 6 && whoseTurn == 0)) {
                if(numMarbles == 1) {
                    lastRow = 1;
                    lastCol = col;
                }
                player1[col] += 1;
                col++;
            }
            else {
                addMarblesToPlayer0(0, numMarbles); //would start at the beginning of the human array
                return;
            }
            numMarbles--;
        }
    }

    /**
     * takes the (row,col) where the player's last marble landed and sends that last marble
     * and the ones in the opponents pocket across from it to their store
     *
     * @param row the row of the player who is trying to capture
     * @param col the column the player's last marble landed at
     *
     */
    public void capture(int row, int col) {
        int oppRow = 1 - row; //opponents row that we are capturing from
        int oppCol = Math.abs(col - 5); //opponents column
        if (oppRow == 0) {
            int marbles = player0[oppCol];
            player0[oppCol] = 0;
            player1[col] = 0;
            player1[6] += marbles + 1;
        } else if (oppRow == 1) {
            int marbles = player1[oppCol];
            player1[oppCol] = 0;
            player0[col] = 0;
            player0[6] += marbles + 1;
        }

    }

    /**
     * used for testing to make sure the GameState is functioning correctly
     * @param object
     * @return true if the object is the same as the current MancalaGameState
     */
    public boolean equals(Object object) {
        if(! (object instanceof MancalaGameState)) return false;
        MancalaGameState state = (MancalaGameState) object;


        for(int i = 0; i< this.player0.length; i++){ //player0.length and player1.length will always be the same
            if (this.player0[i] != state.player0[i] || this.player1[i] != state.player1[i]){
                return false;
            }
        }


        if (this.whoseTurn != state.whoseTurn || this.lastRow != state.lastRow || this.lastCol != state.lastCol) {
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

    public int getLastRow() {
        return lastRow;
    }

    public int getLastCol() {
        return lastCol;
    }

}
