package com.example.quanlygiatla_nhom1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlygiatla_nhom1.Class.UserClass;
import com.example.quanlygiatla_nhom1.R;
import com.example.quanlygiatla_nhom1.SQLite.DAO.UserDAO;
import com.example.quanlygiatla_nhom1.Utilities.Utilities;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;

public class SignInActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private TextInputLayout edlNameSignIn, edlUserNameSignIn, edlPasswordSignIn, edlRePasswordSignIn, edlPhoneSignIn;
    private TextInputEditText edNameSignIn, edUserNameSignIn, edPasswordSignIn, edRePasswordSignIn, edPhoneSignIn;
    private Button btnSaveSignIn, btnCancelSignIn;
    private UserDAO userDAO;
    private Utilities utilities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        this.initActionBar();

        this.initViewByID();

        this.init();

        this.removeErrorTextOnChage();

        this.initClickListener();
    }

    private void removeErrorTextOnChage() {
        this.utilities.removeErrorText(edlUserNameSignIn, edUserNameSignIn);
        this.utilities.removeErrorText(edlNameSignIn, edNameSignIn);
        this.utilities.removeErrorText(edlPhoneSignIn, edPhoneSignIn);
        this.utilities.removeErrorText(edlPasswordSignIn, edPasswordSignIn);
        this.utilities.removeErrorText(edlRePasswordSignIn, edRePasswordSignIn);
    }

    private Boolean validateSignin() {
        Matcher nameMatcher = this.utilities.NSCPattern.matcher(edNameSignIn.getText().toString().trim());
        Matcher userNameMatcher = this.utilities.NSCPattern.matcher(edUserNameSignIn.getText().toString().trim());
        Matcher passwordMatcher = this.utilities.NSCPattern.matcher(edPasswordSignIn.getText().toString().trim());
        boolean success = true;
        if (!nameMatcher.matches()) {
            this.edlNameSignIn.setError(this.utilities.NotSpecialCharacter);
            success = false;
        }
        if (edNameSignIn.getText().toString().trim().length() < 2 || edNameSignIn.getText().toString().trim().length() > 20) {
            this.edlNameSignIn.setError(this.utilities.FullNameLength);
            success = false;
        }
        if (edNameSignIn.getText().toString().equalsIgnoreCase("")) {
            this.edlNameSignIn.setError(this.utilities.FullNameRequire);
            success = false;
        }
        if (!userNameMatcher.matches()) {
            this.edlUserNameSignIn.setError(this.utilities.NotSpecialCharacter);
            success = false;
        }
        if (edUserNameSignIn.getText().toString().length() < 4 || edUserNameSignIn.getText().toString().length() > 20) {
            this.edlUserNameSignIn.setError(this.utilities.UserNameLength);
            success = false;
        }
        if (edUserNameSignIn.getText().toString().equalsIgnoreCase("")) {
            this.edlUserNameSignIn.setError(this.utilities.UserNameRequire);
            success = false;
        }
        try {
            Integer.parseInt(edPhoneSignIn.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            this.edlPhoneSignIn.setError(this.utilities.PhoneInvalid);
            success = false;
        }
        if (edPhoneSignIn.getText().toString().length() != 10) {
            this.edlPhoneSignIn.setError(this.utilities.PhoneLength);
            success = false;
        }
        if (edPhoneSignIn.getText().toString().equalsIgnoreCase("")) {
            this.edlPhoneSignIn.setError(this.utilities.PhoneRequire);
            success = false;
        }
        if (!passwordMatcher.matches()) {
            edlPasswordSignIn.setError(this.utilities.NotSpecialCharacter);
            success = false;
        }
        if (edPasswordSignIn.getText().toString().length() < 4 || edPasswordSignIn.getText().toString().length() > 20) {
            edlPasswordSignIn.setError(this.utilities.PasswordLength);
            success = false;
        }
        if (!edPasswordSignIn.getText().toString().equalsIgnoreCase(edRePasswordSignIn.getText().toString())) {
            edlRePasswordSignIn.setError(this.utilities.PasswordCompare);
            success = false;
        }
        if (edPasswordSignIn.getText().toString().equalsIgnoreCase("")) {
            edlPasswordSignIn.setError(this.utilities.PasswordRequire);
            success = false;
        }
        if (edRePasswordSignIn.getText().toString().equalsIgnoreCase("")) {
            edlRePasswordSignIn.setError(this.utilities.ConfirmPasswordRequire);
            success = false;
        }
        return success;
    }

    private void init() {
        this.userDAO = new UserDAO(SignInActivity.this);
        this.utilities = new Utilities();

    }

    private void initClickListener() {
        this.btnCancelSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        this.btnSaveSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateSignin()) {
                    String name = edNameSignIn.getText().toString();
                    String username = edUserNameSignIn.getText().toString();
                    String numberPhone = edPhoneSignIn.getText().toString();
                    String password = edPasswordSignIn.getText().toString();
                    UserClass userClass = new UserClass(name, numberPhone, username, password, "User", "Chưa xóa");
                    if (userDAO.AddOneUser(userClass)) {
                        Toast.makeText(getApplicationContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignInActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void initViewByID() {
        this.edlNameSignIn = findViewById(R.id.edl_nameSignIn);
        this.edlUserNameSignIn = findViewById(R.id.edl_userNameSignIn);
        this.edlPasswordSignIn = findViewById(R.id.edl_passwordSignIn);
        this.edlRePasswordSignIn = findViewById(R.id.edl_rePasswordSignIn);

        this.edNameSignIn = findViewById(R.id.ed_nameSignIn);
        this.edUserNameSignIn = findViewById(R.id.ed_userNameSignIn);
        this.edPasswordSignIn = findViewById(R.id.ed_passwordSignIn);
        this.edRePasswordSignIn = findViewById(R.id.ed_rePasswordSignIn);

        this.edlPhoneSignIn = findViewById(R.id.edl_phone);
        this.edPhoneSignIn = findViewById(R.id.ed_phone);
        this.btnSaveSignIn = findViewById(R.id.btn_saveSignIn);
        this.btnCancelSignIn = findViewById(R.id.btn_cancelSignIn);
    }

    private void initActionBar() {
        this.actionBar = getSupportActionBar();
        this.actionBar.setTitle("Đăng ký");
        this.actionBar.setDisplayHomeAsUpEnabled(true);
    }

}