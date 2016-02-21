package com.snake;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.util.List;
import java.util.Timer;

public class GameActivity extends Activity implements SensorEventListener{

    GameSurface gameSurface;
    Timer timer;
    int width, height;

    SensorManager sensorManager;
    Sensor sensor;

    float SSX = 0, SSY = 0;
    float SX = 0, SY = 0;
    boolean firstTime;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameSurface = new GameSurface(this);
        this.setContentView(gameSurface);
        timer = new Timer();
//        height = this.getWindowManager().getDefaultDisplay().getHeight();
//        width = this.getWindowManager().getDefaultDisplay().getWidth();

        DisplayMetrics displaymetrics = new DisplayMetrics();
        height = displaymetrics.widthPixels;
        width = displaymetrics.heightPixels;

        sensorManager = (SensorManager) getSystemService(Activity.SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        if (sensors.size() > 0) {
            for (Sensor s : sensors) {
                if (s.getType() == Sensor.TYPE_ACCELEROMETER) {
                    if (s == null)
                        this.sensor = s;
                }
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // таймер обновления картинки
        timer.scheduleAtFixedRate(new GraphUpdater(gameSurface), 0, 100);
        // таймер обновления змейки
        timer.scheduleAtFixedRate(new StepUpdater(this), 0, 100);
        // регистрируем нашу форму как объект слушающий изменения датчика - акселерометра
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
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
    public void onSensorChanged(SensorEvent event) {
        gameSurface.setSomeText("Your score is: " + MainActivity.GAME_SCORE);

        // получаем показания датчика
        SX = event.values[0];
        SY = event.values[1];

        // если игра уже идет, то
        if (!this.firstTime) {
            // получаем положение телефона в пространстве
            // с поправкой на его начальное положение
            float dirX = SX - SSX;
            float dirY = SY - SSY;
            // устанавливаем для змеи новое направление
            gameSurface.game.setCurDirection(this.getDirection(dirX, dirY));
            // передаем в нашу повержность координаты телефона в пространстве
            gameSurface.setXY(dirX, dirY);
        } else {
            // если игра только началась делаем поправку на начальное положение телефона
            this.firstTime = false;
            SSX = SX;
            SSY = SY;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private int getDirection(float x, float y) {
        if (Math.abs(x) > Math.abs(y)) {
            if (x > 0) {
                return Game.DIR_WEST;
            } else {
                return Game.DIR_EAST;
            }
        } else {
            if (y > 0) {
                return Game.DIR_SOUTH;
            } else {
                return Game.DIR_NORTH;
            }
        }
    }

    public void Step() {
        // если ход не удался то закрываем текущую активити
        if (!gameSurface.game.nextMove()) {
            MainActivity.GAME_MODE = 1;
            this.finish();
        }
        // если все впорядке то обновляем очки в стартовой активити
        else{
            MainActivity.GAME_SCORE = this.gameSurface.game.getScore();
        }
    }
}
