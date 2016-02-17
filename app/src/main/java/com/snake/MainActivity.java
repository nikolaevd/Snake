package com.snake;

import android.content.Intent;
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
    protected void onStart() {
        super.onStart();
        if(GAME_MODE == 0){
            setContentView(R.layout.activity_main);
            button = (Button) this.findViewById(R.id.button0);
            button.setOnClickListener(this);
        }
        else{
            setContentView(R.layout.activity_lose);
            button = (Button) this.findViewById(R.id.button1);
            textView = (TextView) this.findViewById(R.id.textView);
            button.setOnClickListener(this);
            textView.setText("Вы набрали " + GAME_SCORE + " очков.");
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, com.snake.GameActivity.class);
        GAME_MODE=0;
        GAME_SCORE=0;
        this.startActivity(intent);
    }
}
