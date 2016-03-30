package com.minh_vuong.test;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;


public class Compass extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mRotation;
    private float mPrevDegree = 0f;
    private TextView tvHeading, direction;
    private ImageView mCompass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvHeading = (TextView) findViewById(R.id.headingText);
        direction = (TextView) findViewById(R.id.direction);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mRotation = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        mCompass = (ImageView) findViewById(R.id.compassImg);
    }



    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mRotation, SensorManager.SENSOR_DELAY_UI);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this, mRotation);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //----TYPE_ROTATION_VECTOR--------------------------------------------
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            float[] r = new float[9];
            float[] mOrientation2 = new float[3];
            SensorManager.getRotationMatrixFromVector(r, event.values);
            SensorManager.getOrientation(r, mOrientation2);
            float azimuthInDegress = (float) (Math.toDegrees(mOrientation2[0]) + 360) % 360; //idea: (int)
            int nbr = Math.round(azimuthInDegress);
            String str = Integer.toString(nbr);
            direction.setText(getDirection(nbr));
            tvHeading.setText(str + "°");

            //----ANIMATION FOR ImageView-----
            RotateAnimation ra = new RotateAnimation(mPrevDegree, -azimuthInDegress, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            ra.setDuration(100);
            ra.setFillAfter(true);
            mCompass.startAnimation(ra);
            mPrevDegree = -azimuthInDegress;
            //String location = lm.getLastKnownLocation();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub
    }

    //SOURCE: http://stackoverflow.com/questions/153724/how-to-round-a-number-to-n-decimal-places-in-java
    public String round(float nbr) {
        String nbr2 = Float.toString(nbr);
        Double nbr3 = Double.valueOf(nbr2);
        return Double.toString(Math.round(nbr3 * 100.0) / 100.0);
    }

    public String getDirection(int nbr){
        if((nbr<30) || (nbr>=330)){
            return ("North");
        }
        else if((nbr>=30) && (nbr<60)){
            return ("Northeast");
        }
        else if((nbr>=60) && (nbr<120)){
            return ("East");
        }
        else if((nbr>=120) && (nbr<150)){
            return ("Southeast");
        }
        else if((nbr>=150) && (nbr<210)){
            return ("South");
        }
        else if((nbr>=210) && (nbr<240)){
            return ("Southwest");
        }
        else if((nbr>=240) && (nbr<300)){
            return ("West");
        }
        else if((nbr>=300) && (nbr<330)){
            return ("Northwest");
        }
        return "";
    }
}