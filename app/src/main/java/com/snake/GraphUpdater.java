package com.snake;

import android.graphics.Canvas;
import android.graphics.Color;

import java.util.TimerTask;

public class GraphUpdater extends TimerTask{

    GameSurface gameSurface;

    GraphUpdater(GameSurface gameSurface){
        this.gameSurface = gameSurface;
    }

    @Override
    public void run(){

        Canvas canvas = gameSurface.getHolder().lockCanvas();
        if (canvas != null){
            canvas.drawColor(Color.BLACK);
            gameSurface.drawSnake(canvas);
            gameSurface.getHolder().unlockCanvasAndPost(canvas);
        }

    }

}