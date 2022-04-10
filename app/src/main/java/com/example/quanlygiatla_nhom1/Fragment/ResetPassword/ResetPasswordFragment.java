package com.example.quanlygiatla_nhom1.Fragment.ResetPassword;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.quanlygiatla_nhom1.Class.UserClass;
import com.example.quanlygiatla_nhom1.R;
import com.example.quanlygiatla_nhom1.SQLite.DAO.UserDAO;
import com.example.quanlygiatla_nhom1.Utilities.Utilities;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class ResetPasswordFragment extends Fragment {
    TextInputEditText ed_new_password,ed_confirm_password;
    TextInputLayout edl_new_password,edl_confirm_password;
    Spinner sp_user_name;
    Button btn_confirm;
    UserDAO userDAO;
    List<UserClass> users;
    ArrayList<String> arraySpinner;
    Utilities utils;

    public ResetPasswordFragment(ArrayList<String> arraySpinner) {
        this.arraySpinner = arraySpinner;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        ed_new_password = view.findViewById(R.id.ed_new_password);
        ed_confirm_password = view.findViewById(R.id.ed_confirm_password);
        edl_new_password = view.findViewById(R.id.edl_new_password);
        edl_confirm_password = view.findViewById(R.id.edl_confirm_password);
        sp_user_name = view.findViewById(R.id.sp_user_name);
        btn_confirm = view.findViewById(R.id.btn_confirm);

        Init();
        ConfirmOnClick();
        RemoveErrorTextOnChange();

        return view;
    }

    private void Init() {
        utils = new Utilities();
        userDAO = new UserDAO(getContext());
        users = userDAO.GetAllUser();
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,arraySpinner);
        sp_user_name.setAdapter(spinnerAdapter);
    }

    private void ConfirmOnClick() {
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ValidateEdit()){
                    String update = userDAO.ResetPassword(
                            sp_user_name.getSelectedItem().toString(),
                            ed_new_password.getText().toString()
                    );
                    Toast.makeText(getActivity(), ""+update, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean ValidateEdit(){
        Matcher PasswordMatcher = utils.NSCPattern.matcher(ed_new_password.getText().toString().trim());
        boolean success = true;
        if(!PasswordMatcher.matches()){
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
        utils.removeErrorText(edl_new_password,ed_new_password);
        utils.removeErrorText(edl_confirm_password,ed_confirm_password);
    }
}
