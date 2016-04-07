package com.minh_vuong.test;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import java.math.BigDecimal;

public class Accelerometer extends AppCompatActivity implements SensorEventListener {

    private SensorManager sm;
    private Sensor accelerometer;
    private TextView xText, yText, zText;
    private double xRound, yRound, zRound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);
        setupToolbar();
        initView();
    }

    private void initView(){
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);

        xText = (TextView) findViewById(R.id.xText);
        yText = (TextView) findViewById(R.id.yText);
        zText = (TextView) findViewById(R.id.zText);
    }

    private void setupToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        /** float xAcc = sensorEvent.values[0];
        xRound = round(xAcc,2);
        int xRound = Math.round(xAcc);
        xText.setText(xRound); */
        xText.setText(String.valueOf(sensorEvent.values[0]));
        yText.setText(String.valueOf(sensorEvent.values[1]));
        zText.setText(String.valueOf(sensorEvent.values[2]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }

    protected void onResumse() {
        super.onResume();
        sm.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
}
