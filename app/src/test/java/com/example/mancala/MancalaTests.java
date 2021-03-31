package com.example.mancala;

import org.junit.Before;
import org.junit.runner.RunWith;

import Mancala.MancalaMainActivity;

@RunWith(RoboelectricTestRunner.class);
public class MancalaTests {
    public MancalaMainActivity activity;

    @Before
    public void setup() throws Exception {
        activity = Roboelectric.buildActivity(MancalaMainActivity.class).create().resume().get();
    }
}
