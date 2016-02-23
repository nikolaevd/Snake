package com.snake;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{

    // 0 - первый запуск
    // 1 - запуск при переходе на следующий уровень
    // 2 - запуск после проигрыша
    public static int GAME_MODE = 0;
    public static int GAME_LEVEL = 0;

    private TextView textView;
    private Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {

        super.onStart();

        if(GAME_MODE == 0){
            setContentView(R.layout.activity_main);
            button = (Button) this.findViewById(R.id.button0);
            button.setOnClickListener(this);
        }
        else if(GAME_MODE == 1){
            setContentView(R.layout.activity_lose);
            button = (Button) this.findViewById(R.id.button1);
            button.setText("Продолжить");
            textView = (TextView) this.findViewById(R.id.textView);
            textView.setText("Уровень " + (GAME_LEVEL - 1) + " пройден.");
            button.setOnClickListener(this);
        }
        else{
            setContentView(R.layout.activity_lose);
            button = (Button) this.findViewById(R.id.button1);
            button.setText("Заново");
            textView = (TextView) this.findViewById(R.id.textView);
            textView.setText("Вы набрали " + Game.SCORE + " очков.");
            button.setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View v){

        Intent intent = new Intent(this, com.snake.GameActivity.class);

        switch(GAME_MODE){
            case 0:
                Game.SCORE = 0;
                GAME_LEVEL = 1;
                break;
            case 1:
                // непонятно, почему не переходиит на GameActivity
                break;
            case 2:

                Game.SCORE = 0;
                GAME_LEVEL = 1;
                break;
        }

        this.startActivity(intent);

    }
}
