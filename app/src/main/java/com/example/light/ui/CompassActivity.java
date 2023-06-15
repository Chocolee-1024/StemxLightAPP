package com.example.light.ui;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.light.R;
import com.example.light.base.BaseActivity;

public class CompassActivity extends BaseActivity implements SensorEventListener {

    //使用方向传感器编写指南针
    private ImageView compass_pointer;
    private Toolbar compass_toolbar;
    private TextView position_textView;
    private TextView degree_textView;
    private float currentDegree = 0f;
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        init();

    }
    @Override
    public void init() {
        setupToolbar();
        setupImageView();
        setupTextView();
        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);  //获取传感器服务

    }

    private void setupTextView() {
        degree_textView = (TextView) findViewById(R.id.compass_degree_textView);
        position_textView = (TextView) findViewById(R.id.compass_position_textView);
    }

    private void setupImageView() {
        compass_pointer = (ImageView) findViewById(R.id.compass_background);
    }

    /**設定Toolbar*/
    private void setupToolbar() {
        compass_toolbar = (Toolbar) findViewById(R.id.compass_toolbar);
        compass_toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        compass_toolbar.setNavigationOnClickListener(this::backIconClicked);
    }

    @Override
    protected void onResume() {
        //为方向传感器注册监听器
        super.onResume();
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),sensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        //取消监听器
        super.onPause();
        sensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        float degree = Math.round(event.values[0]);
        RotateAnimation rotateAnimation = new RotateAnimation(
                currentDegree, -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        rotateAnimation.setDuration(200);
        rotateAnimation.setFillAfter(true);
        degree_textView.setText((int)degree+"°");
        if(degree == 0)
            position_textView.setText("北");
        else if(degree>0 && degree<90)
            position_textView.setText("東北");
        else if(degree == 90)
            position_textView.setText("東");
        else if(degree>90 && degree<180)
            position_textView.setText("東南");
        else if(degree == 180)
            position_textView.setText("南");
        else if(degree>180 && degree<270)
            position_textView.setText("西南");
        else if(degree == 270)
            position_textView.setText("西");
        else if(degree>270 && degree<360)
            position_textView.setText("西北");
        Log.e("TAG", "onSensorChanged: " +degree );
        compass_pointer.startAnimation(rotateAnimation);
        currentDegree = -degree;
    }

    /**back點擊*/
    private void backIconClicked(View view) {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}