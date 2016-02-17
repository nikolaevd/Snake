package com.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.SurfaceView;

public class GameSurface extends SurfaceView {

    Game field;
    Bitmap head, till, body, bg, fruite;
    String someText = "012345";
    float x, y;

    public void setXY(float x, float y){
        this.x = x;
        this.y = y;
    }

    public GameSurface(Context context){
        super(context);

        field = new Game();
        head = BitmapFactory.decodeResource(context.getResources(), R.drawable.head);
        till = BitmapFactory.decodeResource(context.getResources(), R.drawable.till);
        body = BitmapFactory.decodeResource(context.getResources(), R.drawable.body);
        bg = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg);
        fruite = BitmapFactory.decodeResource(context.getResources(), R.drawable.fruite);
    }
}
