package com.snake;

import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements OnClickListener{

    // 0 - первый запуск
    // 1 - запуск после проигрыша
    public static int GAME_MODE = 0;
    public static int GAME_SCORE = 0;

    TextView textView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {
        //textView = (TextView) findViewById(R.id.button);
        //textView.setText("Android it's easy!");
    }
}
