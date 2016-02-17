package com.snake;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import java.util.Timer;

public class GameActivity extends Activity implements SensorEventListener{

    GameSurface gameSurface;
    Timer timer;
    int width, height;

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
