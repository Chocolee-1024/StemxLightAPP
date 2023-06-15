package com.example.light.ui.main;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.core.app.ActivityCompat;

import com.example.light.R;
import com.example.light.base.BaseActivity;
import com.example.light.ui.CompassActivity;
import com.example.light.ui.map.MapsActivity;
import com.example.light.ui.morseTransform.MorseTransformActivity;
import com.example.light.ui.mostPost.MorsePostActivity;

public class MainActivity extends BaseActivity {
    private Button transform_button;
    private Button post_button;
    private Button map_button;
    private Button compass_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }
    @Override
    public void init(){
        setupUI();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            return;
        }
    }

    private void setupUI(){
        transform_button = (Button) findViewById(R.id.main_transform_button);
        post_button = (Button) findViewById(R.id.main_post_button);
        map_button = (Button) findViewById(R.id.main_map_button);
        compass_button = (Button) findViewById(R.id.main_compass_button);

        transform_button.setText(R.string.morse_code_transform);
        post_button.setText(R.string.morse_code_post);
        transform_button.setOnClickListener(this::transformBtClicked);
        post_button.setOnClickListener(this::postBtClicked);
        map_button.setOnClickListener(this::mapBtClicked);
        compass_button.setOnClickListener(this::compassClicked);

    }

    /**
     * 跳到轉換
     * */
    private void transformBtClicked(View view){
            Intent transformIntent = new Intent(MainActivity.this, MorseTransformActivity.class);
            startActivity(transformIntent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    /**
     * 跳到發送
     * */
    private void postBtClicked(View view){

        Intent postIntent = new Intent(MainActivity.this, MorsePostActivity.class);
        startActivity(postIntent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


    }
    /**
     * 跳到指南針
     * */
    private void compassClicked(View view) {
        Intent compassIntent = new Intent(MainActivity.this, CompassActivity.class);
        startActivity(compassIntent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    /**
     * 跳到地圖
     * */
    private void mapBtClicked(View view){

        Intent mapIntent = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(mapIntent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}