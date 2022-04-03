package com.example.quanlygiatla_nhom1.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.example.quanlygiatla_nhom1.Class.UserClass;
import com.example.quanlygiatla_nhom1.R;
import com.example.quanlygiatla_nhom1.SQLite.DAO.UserDAO;
import com.example.quanlygiatla_nhom1.Utilities.Utilities;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;

public class LoginActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private TextInputLayout edlUserName, edlPassword;
    private TextInputEditText edUserName, edPassword;
    private AppCompatCheckBox cbSaveAccount;
    private Button btnLogin, btnSignIn;
    private Intent intent;
    private UserDAO userDAO;
    private Utilities utilities;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.actionBar();

        this.initViewById();

        this.init();

        this.removeErrorTextChage();

        this.rememberLogin();

        this.initClickListener();


    }

    private void init() {
        userDAO = new UserDAO(LoginActivity.this);
        utilities = new Utilities();
        this.sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        if (this.userDAO.GetAllNotDeleteUser().size() == 0) {
            UserClass userClass = new UserClass(
                    "Nhóm 1",
                    "0878571999",
                    "admin",
                    "admin",
                    "Admin",
                    "Chưa xóa"
            );
            this.userDAO.AddOneUser(userClass);
        }
    }

    private void actionBar() {
        this.actionBar = getSupportActionBar();
        this.actionBar.setTitle("Đăng nhập");


    }

    private void rememberLogin() {
        String login = sharedPreferences.getString("login", "");
        if (login.equalsIgnoreCase("remember")) {
            edUserName.setText(sharedPreferences.getString("Username", ""));
            edPassword.setText(sharedPreferences.getString("Password", ""));
            cbSaveAccount.setChecked(true);
        } else {
            edUserName.setText(sharedPreferences.getString("Username", ""));
        }
    }

    private void initClickListener() {
        this.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateLogin()) {
                    String username = edUserName.getText().toString();
                    String password = edPassword.getText().toString();
                    if (userDAO.Login(username, password).equalsIgnoreCase("User")) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("Username", username);
                        editor.putString("Role", "User");
                        if (cbSaveAccount.isChecked()) {
                            editor.putString("login", "remember");
                            editor.putString("Password", password);
                        } else {
                            editor.putString("login", "No");
                            editor.putString("Password", "");
                        }
                        editor.commit();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else if (userDAO.Login(username, password).equalsIgnoreCase("Admin")) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("Username", username);
                        editor.putString("Role", "Admin");
                        if (cbSaveAccount.isChecked()) {
                            editor.putString("login", "remember");
                            editor.putString("Password", password);
                        } else {
                            editor.putString("login", "No");
                            editor.putString("Password", "");
                        }
                        editor.commit();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                    }
                }
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

    private Boolean validateLogin() {
        boolean success = true;
        Matcher UsernameMatcher = this.utilities.NSCPattern.matcher(edUserName.getText().toString().trim());
        Matcher PasswordMatcher = this.utilities.NSCPattern.matcher(edPassword.getText().toString().trim());

        if (!UsernameMatcher.matches()) {
            this.edlUserName.setError(this.utilities.NotSpecialCharacter);
            success = false;
        }
        if (this.edUserName.getText().toString().equalsIgnoreCase("")) {
            this.edlUserName.setError(this.utilities.UserNameRequire);
            success = false;
        }
        if (!PasswordMatcher.matches()) {
            this.edlPassword.setError(this.utilities.NotSpecialCharacter);
            success = false;
        }
        if (this.edPassword.getText().toString().equalsIgnoreCase("")) {
            this.edlPassword.setError(this.utilities.PasswordRequire);
            success = false;
        }
        return success;
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

    private void removeErrorTextChage() {
        this.utilities.removeErrorText(this.edlUserName, this.edUserName);
        this.utilities.removeErrorText(this.edlPassword, this.edPassword);
    }
}