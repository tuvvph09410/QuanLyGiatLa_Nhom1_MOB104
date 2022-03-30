package com.example.quanlygiatla_nhom1.Fragment.Account;

import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.quanlygiatla_nhom1.Class.UserClass;
import com.example.quanlygiatla_nhom1.SQLite.DAO.UserDAO;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AccountDetailFragment extends Fragment {
    UserClass user;
    TextView tv_user_name;
    TextInputEditText ed_user_name,ed_name,ed_phone;
    TextInputLayout edl_user_name,edl_name,edl_phone;
    Button btn_delete,btn_cancel;
    UserDAO userDAO;
    public AccountDetailFragment(UserClass user) {
        this.user = user;
    }
}
