package com.example.quanlygiatla_nhom1.Activity;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlygiatla_nhom1.R;

public class SignInActivity extends AppCompatActivity {
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        this.initActionBar();
    }

    private void initActionBar() {
        this.actionBar = getSupportActionBar();
        this.actionBar.setTitle("Đăng ký");
        this.actionBar.setDisplayHomeAsUpEnabled(true);
    }

}