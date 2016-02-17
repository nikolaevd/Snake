package com.snake;

import android.graphics.Canvas;
import android.graphics.Color;

import java.util.TimerTask;

public class GraphUpdater extends TimerTask {

    GameSurface gameSurface;

    GraphUpdater(GameSurface gameSurface){
        this.gameSurface = gameSurface;
    }

    @Override
    public void run() {
        Canvas c = gameSurface.getHolder().lockCanvas();
        if (c!=null){
            c.drawColor(Color.BLACK);
            gameSurface.drawSnake(c);
            gameSurface.getHolder().unlockCanvasAndPost(c);
        }
    }
}