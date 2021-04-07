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



}
