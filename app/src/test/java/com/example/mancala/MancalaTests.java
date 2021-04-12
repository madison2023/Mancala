package com.example.mancala;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import static org.junit.Assert.*;

import Mancala.MancalaGameState;
import Mancala.MancalaMainActivity;

@RunWith(RobolectricTestRunner.class)
public class MancalaTests {
    public MancalaMainActivity activity;

    @Before
    public void setup() throws Exception {
        activity = Robolectric.buildActivity(MancalaMainActivity.class).create().resume().get();
    }

    //Tests focused on the state: copy constructors and equals
    //copy cons:  empty default state, in progress state, full board state
    //This tests the copy constructor when nothing is set
    @Test
    public void test_CopyConstructorOfState_Empty(){
        MancalaGameState MancalaState = new MancalaGameState();
        MancalaGameState copyState = new MancalaGameState(MancalaState);
        assertTrue("Copy Constructor did not produce equal States", MancalaState.equals(copyState));
    }

    @Test
    public void selectPit(){
        MancalaGameState MancalaState = new MancalaGameState();
        MancalaState.selectPit(0,0);
        int[] player0 = MancalaState.getPlayer0();
        assertEquals(0,player0[0]);
        assertEquals(5,player0[1]);
        assertEquals(5,player0[2]);
        assertEquals(5,player0[3]);
        assertEquals(5,player0[4]);
        assertEquals(4,player0[5]);
    }

    @Test
    public void addMarblesToPlayer0() {
        MancalaGameState MancalaState = new MancalaGameState();
        MancalaState.selectPit(0,3);
        int[] player0 = MancalaState.getPlayer0();
        assertEquals(1,player0[6]);
    }

    @Test
    public void addMarblesToPlayer1() {
        MancalaGameState MancalaState = new MancalaGameState();
        MancalaState.setWhoseTurn(1);
        MancalaState.selectPit(0,3);
        int[] player1 = MancalaState.getPlayer1();
        assertEquals(1,player1[6]);
    }

}
