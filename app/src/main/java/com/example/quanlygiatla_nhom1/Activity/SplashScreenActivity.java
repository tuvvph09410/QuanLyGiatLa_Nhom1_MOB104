package com.example.quanlygiatla_nhom1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlygiatla_nhom1.R;
import com.google.android.material.button.MaterialButton;

public class SplashScreenActivity extends AppCompatActivity {
    private MaterialButton mBtnGetStart;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        this.initViewById();
        this.initListener();
    }

    private void initListener() {
        this.mBtnGetStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViewById() {
        this.mBtnGetStart = findViewById(R.id.mbtn_getStart);
    }
}