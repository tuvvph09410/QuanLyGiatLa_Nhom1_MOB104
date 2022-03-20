package com.example.quanlygiatla_nhom1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlygiatla_nhom1.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignInActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private TextInputLayout edlNameSignIn, edlUserNameSignIn, edlPasswordSignIn, edlRePasswordSignIn;
    private TextInputEditText edNameSignIn, edUserNameSignIn, edPasswordSignIn, edRePasswordSignIn;
    private Button btnSaveSignIn, btnCancelSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        this.initActionBar();

        this.initViewByID();

        this.initClickListener();
    }

    private void initClickListener() {
        this.btnCancelSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViewByID() {
        this.edlNameSignIn = findViewById(R.id.edl_nameSignIn);
        this.edlUserNameSignIn = findViewById(R.id.edl_userNameSignIn);
        this.edlPasswordSignIn = findViewById(R.id.edl_passwordSignIn);
        this.edlRePasswordSignIn = findViewById(R.id.edl_rePasswordSignIn);
        this.edUserNameSignIn = findViewById(R.id.ed_nameSignIn);
        this.edPasswordSignIn = findViewById(R.id.ed_userNameSignIn);
        this.edRePasswordSignIn = findViewById(R.id.ed_passwordSignIn);
        this.btnSaveSignIn = findViewById(R.id.btn_saveSignIn);
        this.btnCancelSignIn = findViewById(R.id.btn_cancelSignIn);
    }

    private void initActionBar() {
        this.actionBar = getSupportActionBar();
        this.actionBar.setTitle("Đăng ký");
        this.actionBar.setDisplayHomeAsUpEnabled(true);
    }

}