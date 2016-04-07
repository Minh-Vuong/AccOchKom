package com.minh_vuong.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ButtonAcc(View view) {
        Intent intent = new Intent(this, Accelerometer.class);
        startActivity(intent);
    }

    public void ButtonCom(View view) {
        Intent intent = new Intent(this, CompassActivity.class);
        startActivity(intent);
    }
}

