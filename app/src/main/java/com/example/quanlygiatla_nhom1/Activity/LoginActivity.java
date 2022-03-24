package com.example.quanlygiatla_nhom1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.example.quanlygiatla_nhom1.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private TextInputLayout edlUserName, edlPassword;
    private TextInputEditText edUserName, edPassword;
    private AppCompatCheckBox cbSaveAccount;
    private Button btnLogin, btnSignIn;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.actionBar();

        this.initViewById();

        this.initClickListener();


    }

    private void actionBar() {
        this.actionBar = getSupportActionBar();
        this.actionBar.setTitle("Đăng nhập");

    }

    private void initClickListener() {
        this.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        this.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(LoginActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViewById() {
        this.edUserName = findViewById(R.id.ed_userNameLogin);
        this.edPassword = findViewById(R.id.ed_passwordLogin);
        this.edlUserName = findViewById(R.id.edl_userNameLogin);
        this.edlPassword = findViewById(R.id.edl_passwordLogin);
        this.cbSaveAccount = findViewById(R.id.cb_saveAccount);
        this.btnLogin = findViewById(R.id.btn_login);
        this.btnSignIn = findViewById(R.id.btn_signIn);
    }
}