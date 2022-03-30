package com.example.quanlygiatla_nhom1.Fragment.Personal;

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

import com.example.quanlygiatla_nhom1.Class.UserClass;
import com.example.quanlygiatla_nhom1.R;
import com.example.quanlygiatla_nhom1.SQLite.DAO.UserDAO;
import com.example.quanlygiatla_nhom1.Utilities.Utilities;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;

public class PersonalFragment extends Fragment {
    TextView tv_user_name;
    TextInputEditText ed_user_name,ed_name,ed_phone;
    TextInputLayout edl_user_name,edl_name,edl_phone;
    UserClass user;
    UserDAO userDAO;
    SharedPreferences sharedPreferences;
    String UserName = "";
    Button btn_edit;
    Utilities utils;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);

        tv_user_name = view.findViewById(R.id.tv_user_name);
        ed_user_name = view.findViewById(R.id.ed_user_name);
        ed_name = view.findViewById(R.id.ed_name);
        ed_phone = view.findViewById(R.id.ed_phone);
        edl_user_name = view.findViewById(R.id.edl_user_name);
        edl_name = view.findViewById(R.id.edl_name);
        edl_phone = view.findViewById(R.id.edl_phone);
        btn_edit = view.findViewById(R.id.btn_edit);

        Init();
        EditOnClick();
        RemoveErrorTextOnChange();

        return view;
    }

    private void RemoveErrorTextOnChange() {
        utils.removeErrorText(edl_name,ed_name);
        utils.removeErrorText(edl_phone,ed_phone);
    }

    private void EditOnClick() {
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ValidateEdit()){
                    if(userDAO.UpdateUser(UserName,ed_name.getText().toString(),ed_phone.getText().toString())){
                        Toast.makeText(getActivity(), "Thay đổi thông tin thành công", Toast.LENGTH_SHORT).show();
                        MapUser();
                    }else{
                        Toast.makeText(getActivity(), "Thay đổi thông tin thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void Init() {
        utils = new Utilities();
        sharedPreferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        UserName = sharedPreferences.getString("UserName","");
        MapUser();
    }

    private void MapUser() {
        userDAO = new UserDAO(getContext());
        user = userDAO.FindOneUser(UserName);
        tv_user_name.setText(UserName);
        ed_user_name.setText(UserName);
        ed_name.setText(user.getName());
        ed_phone.setText(user.getPhone());
    }
    private boolean ValidateEdit(){
        Matcher NameMatcher = utils.NSCPattern.matcher(ed_name.getText().toString().trim());
        boolean success = true;
        try {
            Integer.parseInt(ed_phone.getText().toString());
        }catch (NumberFormatException ne){
            edl_phone.setError(utils.PhoneInvalid);
            success = false;
        }
        if(ed_phone.getText().toString().trim().length() != 10){
            edl_phone.setError(utils.PhoneLength);
            success = false;
        }
        if(ed_phone.getText().toString().trim().equalsIgnoreCase("")){
            edl_phone.setError(utils.PhoneRequire);
            success = false;
        }
        if(!NameMatcher.matches()){
            edl_name.setError(utils.NotSpecialCharacter);
            success = false;
        }
        if(ed_name.getText().toString().trim().length() < 2 || ed_name.getText().toString().trim().length() > 20 ){
            edl_name.setError(utils.FullNameLength);
            success = false;
        }
        if(ed_name.getText().toString().trim().equalsIgnoreCase("")){
            edl_name.setError(utils.FullNameRequire);
            success = false;
        }
        return success;
    }

}
