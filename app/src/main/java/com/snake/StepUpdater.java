package com.snake;

import java.util.TimerTask;

public class StepUpdater extends TimerTask {

    GameActivity gameActivity;

    StepUpdater(GameActivity gameActivity){
        this.gameActivity = gameActivity;
    }

    @Override
    public void run() {
        gameActivity.Step();
    }

}