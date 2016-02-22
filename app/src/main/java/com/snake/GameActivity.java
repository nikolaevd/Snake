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

    GameSurface gameSurface;
    Timer timer;
    int width, height;

    SensorManager SensorManager;
    Sensor accelerometer;
    
    float SSX = 0, SSY = 0;
    float SX = 0, SY = 0;
    boolean firstTime;

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
        SensorManager = (SensorManager) getSystemService(Activity.SENSOR_SERVICE);
        List<Sensor> sensors = SensorManager.getSensorList(Sensor.TYPE_ALL);
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
        timer.scheduleAtFixedRate(new StepUpdater(this), 0, 500);
        // регистрируем текущую активити как объект, слушающий изменения датчика - акселерометра
        SensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
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
        SensorManager.unregisterListener(this);
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
            gameSurface.game.setCurDirection(this.getDirection(dirX, dirY));
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
    public void Step(){

        // если ход не удался то закрываем текущую активити
        if(!gameSurface.game.nextMove()) {
            MainActivity.GAME_MODE = 1;
            this.finish();
        }
        // если все впорядке то обновляем очки в стартовой активити
        else{
            MainActivity.GAME_SCORE = this.gameSurface.game.getScore();
        }

    }

}
