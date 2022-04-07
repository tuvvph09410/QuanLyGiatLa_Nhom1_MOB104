package com.example.quanlygiatla_nhom1.Fragment.ChangePassword;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.quanlygiatla_nhom1.R;
import com.example.quanlygiatla_nhom1.SQLite.DAO.UserDAO;
import com.example.quanlygiatla_nhom1.Utilities.Utilities;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;

public class ChangePasswordFragment extends Fragment {
    TextView tv_user_name;
    TextInputEditText ed_old_password,ed_new_password,ed_confirm_password;
    TextInputLayout edl_old_password,edl_new_password,edl_confirm_password;
    Button btn_confirm;
    UserDAO userDAO;
    String UserName;
    SharedPreferences sharedPreferences;
    Utilities utils;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        ed_old_password = view.findViewById(R.id.ed_old_password);
        ed_new_password = view.findViewById(R.id.ed_new_password);
        ed_confirm_password = view.findViewById(R.id.ed_confirm_password);
        edl_old_password = view.findViewById(R.id.edl_old_password);
        edl_new_password = view.findViewById(R.id.edl_new_password);
        edl_confirm_password = view.findViewById(R.id.edl_confirm_password);
        tv_user_name = view.findViewById(R.id.tv_user_name);
        btn_confirm = view.findViewById(R.id.btn_confirm);

        Init();
        ConfirmOnClick();
        RemoveErrorTextOnChange();

        return view;
    }

    private void Init() {
        utils = new Utilities();
        userDAO = new UserDAO(getContext());
        sharedPreferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        UserName = sharedPreferences.getString("Username", "");
        tv_user_name.setText(UserName);
    }

    private void ConfirmOnClick() {
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ValidateEdit()){
                    String update = userDAO.ChangePassword(
                            UserName,
                            ed_old_password.getText().toString(),
                            ed_new_password.getText().toString()
                    );
                    Toast.makeText(getActivity(), update, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean ValidateEdit(){
        Matcher OldPasswordMatcher = utils.NSCPattern.matcher(ed_old_password.getText().toString().trim());
        Matcher NewPasswordMatcher = utils.NSCPattern.matcher(ed_new_password.getText().toString().trim());
        boolean success = true;
        if(!OldPasswordMatcher.matches()){
            edl_old_password.setError(utils.NotSpecialCharacter);
            success = false;
        }
        if(ed_old_password.getText().toString().trim().equalsIgnoreCase("")){
            edl_old_password.setError(utils.PasswordRequire);
            success = false;
        }
        if(!NewPasswordMatcher.matches()){
            edl_new_password.setError(utils.NotSpecialCharacter);
            success = false;
        }
        if(ed_new_password.getText().toString().trim().length() > 20 || ed_new_password.getText().toString().trim().length() < 4 ){
            edl_new_password.setError(utils.PasswordLength);
            success = false;
        }
        if(ed_new_password.getText().toString().trim().equalsIgnoreCase("")){
            edl_new_password.setError(utils.PasswordRequire);
            success = false;
        }
        if(ed_confirm_password.getText().toString().trim().equalsIgnoreCase("")){
            edl_confirm_password.setError(utils.ConfirmPasswordRequire);
            success = false;
        }
        if(!ed_confirm_password.getText().toString().trim().equalsIgnoreCase(ed_new_password.getText().toString().trim())){
            edl_confirm_password.setError(utils.PasswordCompare);
            success = false;
        }
        return success;
    }

    private void RemoveErrorTextOnChange() {
        utils.removeErrorText(edl_old_password,ed_old_password);
        utils.removeErrorText(edl_new_password,ed_new_password);
        utils.removeErrorText(edl_confirm_password,ed_confirm_password);
    }
}
