package com.example.mancala;

import android.view.View;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import static org.junit.Assert.*;

import Mancala.MancalaGameState;
import Mancala.MancalaLocalGame;
import Mancala.MancalaMainActivity;

@RunWith(RobolectricTestRunner.class)
public class MancalaTests {
    public MancalaMainActivity activity;

    @Before
    public void setup() throws Exception {
        activity = Robolectric.buildActivity(MancalaMainActivity.class).create().resume().get();
    }


    @Test
    public void test_CopyConstructorOfState_Empty(){
        MancalaGameState MancalaState = new MancalaGameState();
        MancalaGameState copyState = new MancalaGameState(MancalaState);
        assertTrue("Copy Constructor did not produce equal States", MancalaState.equals(copyState));
    }

    /*
    * Tests that the copy constructor works when given a full MancalaGameState
    * @author Rachel Madison
    * */
    @Test
    public void copyConstructorOfState_full() {
        MancalaGameState mancalaGameState = new MancalaGameState();
        int[] player0 = {0,0,0,0,1,5,9};
        int[] player1 = {1,1,0,1,4,3,10};
        mancalaGameState.setPlayer0(player0);
        mancalaGameState.setPlayer1(player1);
        mancalaGameState.setWhoseTurn(0);
        mancalaGameState.setPlayerTop(1);
        mancalaGameState.setPlayerBottom(0);
        MancalaGameState copyState = new MancalaGameState(mancalaGameState);
        assertTrue(mancalaGameState.equals(copyState));
    }

    /**
     * Tests the capture method in MancalaGameState
     * @author Rachel Madison
     */
    @Test
    public void capture(){
        MancalaGameState MancalaState = new MancalaGameState();
        int[] player0 = MancalaState.getPlayer0();
        int[] player1 = MancalaState.getPlayer1();
        player0[3]=1;
        MancalaState.capture(0,3);
        assertEquals(player0[6],5);
        assertEquals(player0[3],0);
        assertEquals(player1[2],0);
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
        MancalaState.setWhoseTurn(0);
        MancalaState.setPlayerBottom(0);
        MancalaState.setPlayerTop(1);
        MancalaState.selectPit(0,3);
        int[] player0 = MancalaState.getPlayer0();
        assertEquals(1,player0[6]);
    }

    @Test
    public void addMarblesToPlayer1() {
        MancalaGameState MancalaState = new MancalaGameState();
        MancalaState.setWhoseTurn(1);
        MancalaState.setPlayerTop(1);
        MancalaState.setPlayerBottom(0);
        MancalaState.selectPit(0,3);
        int[] player1 = MancalaState.getPlayer1();
        assertEquals(1,player1[6]);
    }

}
