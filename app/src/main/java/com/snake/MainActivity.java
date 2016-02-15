package com.snake;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

public class MainActivity extends ActionBarActivity {

    // 0 - первый запуск
    // 1 - запуск после проигрыша
    public static int GAME_MODE = 0;
    public static int GAME_SCORE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
