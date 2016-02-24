package com.snake;

import java.util.List;
import java.util.Timer;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class GameActivity extends Activity implements SensorEventListener{

    private GameSurface gameSurface;
    private Timer timer;
    private int width, height;

    private static int SNAKE_SPEED = 500;

    private SensorManager sensorManager;
    private Sensor accelerometer;

    private float SSX = 0, SSY = 0;
    private float SX = 0, SY = 0;
    private boolean firstTime;

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        gameSurface = new GameSurface(this);
        this.setContentView(gameSurface);
        timer = new Timer();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        height = displaymetrics.widthPixels;
        width = displaymetrics.heightPixels;

        // инициализируем акселерометр
        sensorManager = (SensorManager) getSystemService(Activity.SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        if(sensors.size() > 0){
            for(Sensor sensor : sensors){
                if(sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                    if(accelerometer == null){
                        accelerometer = sensor;
                    }
                }
            }
        }

    }

    @Override
    public void onStart(){

        super.onStart();
        // таймер обновления картинки на экране
        timer.scheduleAtFixedRate(new GraphUpdater(gameSurface), 0, 100);
        // таймер обновления положения змейки
        if(SNAKE_SPEED >= 250){
            SNAKE_SPEED -= 50;
            timer.scheduleAtFixedRate(new StepUpdater(this), 0, SNAKE_SPEED);
        }
        // регистрируем текущую активити как объект, слушающий изменения датчика - акселерометра
        sensorManager.registerListener(this, accelerometer, sensorManager.SENSOR_DELAY_GAME);
        this.firstTime = true;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }

    @Override
    public void onStop() {

        super.onStop();
        // останавливаем таймеры
        timer.cancel();
        timer.purge();
        // отписываемся от получения сообщений об изменении от датчика
        sensorManager.unregisterListener(this);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }

    // метод, определяет по показаниям акселерометра передаваемым ему как параметры х и у
    // в каком направлении должна двигаться змея
    private int getDirection(float x, float y){

        if(Math.abs(x) > Math.abs(y)){
            if(x > 0){
                return Game.WEST;
            }
            else{
                return Game.EAST;
            }
        }
        else{
            if(y > 0){
                return Game.NORTH;
            }
            else{
                return Game.SOUTH;
            }
        }

    }

    // обработка изменений ориентации телефона в пространстве
    @Override
    public void onSensorChanged(SensorEvent event){

		gameSurface.setText("Набрано очков: " + Game.SCORE + " Уровень: " + Game.LEVEL);
        // получаем показания датчика
        SX = event.values[0];
        SY = event.values[1];

        // если игра уже идет, то
        if(!this.firstTime){
            // получаем положение телефона в пространстве
            // с поправкой на его начальное положение
            float dirX = SX - SSX;
            float dirY = SY - SSY;
            // устанавливаем для змеи новое направление
            gameSurface.getGame().setCurDirection(this.getDirection(dirX, dirY));
        }
        else{
            // если игра только началась делаем поправку на начальное
            // положение телефона
            this.firstTime = false;
            SSX = SX;
            SSY = SY;
        }

    }

    // в методе происходит движение змейки в зависимости от ее направления
    public void step(){

        // если ход не удался то закрываем текущую активити
        if(!gameSurface.getGame().nextMove()){
            SNAKE_SPEED = 500;
            Game.MODE = 2;
            this.finish();
        }
        // переключаем уровень
        else{
            if(Game.SCORE >= 60){
                Game.LEVEL++;
                Game.MODE = 1;
                this.finish();
            }
        }

    }

}
