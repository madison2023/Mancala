package Mancala;

import com.example.mancala.game.GameFramework.infoMessage.GameState;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author Rachel Madison, Henry Lee, Jordan Nakamura
 */
public class MancalaGameState extends GameState implements Serializable  {
    //arrays for each player will store number of marbles in the corresponding pocket
    //ex: if humanPlayer has 4 marbles in their first pocket humanPlayer[0] = 4
    private int[] humanPlayer;
    private int[] computerPlayer;

    //stores who's turn it is
    private boolean isHumansTurn;


    private boolean rowIsEmpty; //if true game is over

    private int numMarbles;

    //constructor for objects of class MancalaGameState
    public MancalaGameState() {
        //initializing the number of marbles in each pocket
        humanPlayer = new int[7];
        computerPlayer = new int[7];
        //every pocket has 4 marbles at the beginning except the store which has 1
        for(int i = 0; i< humanPlayer.length - 1; i++){ //humanPlayer.length and computerPlayer.length will always be the same
            humanPlayer[i] = 4;
            computerPlayer[i] = 4;
        }
        humanPlayer[humanPlayer.length - 1] = 0;
        computerPlayer[computerPlayer.length - 1] = 0;

        //initializing booleans
        isHumansTurn = true;
        rowIsEmpty = false;
    }

    //copy Constructor
    public MancalaGameState(MancalaGameState original)
    {
        humanPlayer = new int[7];
        computerPlayer = new int[7];
        for(int i = 0; i< humanPlayer.length; i++){ //humanPlayer.length and computerPlayer.length will always be the same
            humanPlayer[i] = original.humanPlayer[i];
            computerPlayer[i] = original.computerPlayer[i];
        }

        //initializing booleans
        isHumansTurn = original.isHumansTurn;
        rowIsEmpty = original.rowIsEmpty;

    }

    @Override
    public String toString(){
        return "\nComputer Player's Pockets: " + Arrays.toString(computerPlayer) + "\nHuman Player's Pockets: "
                + Arrays.toString(humanPlayer) + "\nisHumansTurn = " + isHumansTurn + "\nrowIsEmpty = " + rowIsEmpty + "\nNumMarbles: " + numMarbles + "\n";
    }





    public boolean selectPit(int row, int col) { //columns labeled 0-6, where 0 is the first pocket and 6 is the store
        if(isHumansTurn) {
            if(row == 1 && humanPlayer[col] != 0 && col != 6) { //cant make a move from an empty pit, one that isn't yours, or your store
                //set selected pit to zero
                numMarbles = humanPlayer[col];
                humanPlayer[col] = 0;
                //add one marble to each pit while there are still marbles going into other array if necessary
                addMarblesToHuman(col+1);
                isHumansTurn = !isHumansTurn; //next player's turn
                return true;
            }
            else {
                return false;
            }
        }
        else {
            if (row == 1 && computerPlayer[col] != 0 && col != 6) { //cant make a move from an empty pit, one that isn't yours, or your store
                //set selected pit to zero
                numMarbles = computerPlayer[col];
                computerPlayer[col] = 0;
                //add one marble to each pit while there are still marbles going into other array if necessary
                addMarblesToComputer(col+1);
                isHumansTurn = !isHumansTurn;
                return true;
            } else {
                return false;
            }
        }
    }

    public void addMarblesToHuman(int col){
        while(numMarbles > 0) {
            //second half of this if statement is making sure we don't add a marble to the wrong players store
            if(col != humanPlayer.length && !(col == 6 && !isHumansTurn)) {
                humanPlayer[col] += 1;
                col++;
            }
            else {
                addMarblesToComputer( 0);//would start at the beginning of the marbles array
                return;
            }
            numMarbles--;
        }
    }

    public void addMarblesToComputer(int col) {
        while(numMarbles > 0) {
            if(col != computerPlayer.length && !(col == 6 && isHumansTurn)) {
                computerPlayer[col] += 1;
                col++;
            }
            else {
                addMarblesToHuman(0); //would start at the beginning of the human array
                return;

            }
            numMarbles--;
        }
    }

    public boolean equals(Object object) {
        if(! (object instanceof MancalaGameState)) return false;
        MancalaGameState state = (MancalaGameState) object;


        for(int i = 0; i< this.humanPlayer.length; i++){ //humanPlayer.length and computerPlayer.length will always be the same
            if (this.humanPlayer[i] != state.humanPlayer[i] || this.computerPlayer[i] != state.computerPlayer[i]){
                return false;
            }
        }

        //initializing booleans
        if (this.isHumansTurn != state.isHumansTurn || this.rowIsEmpty != state.rowIsEmpty || this.numMarbles != state.numMarbles) {
            return false;
        }
        else {
            return true;
        }

    }

    public int[] getHumanPlayer() {
        return humanPlayer;
    }

    public int[] getComputerPlayer() {
        return computerPlayer;
    }
}
