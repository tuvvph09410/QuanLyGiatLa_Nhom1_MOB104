package com.example.quanlygiatla_nhom1.Fragment.Account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.quanlygiatla_nhom1.Activity.MainActivity;
import com.example.quanlygiatla_nhom1.Class.UserClass;
import com.example.quanlygiatla_nhom1.R;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_detail, container, false);
        tv_user_name = view.findViewById(R.id.tv_user_name);
        ed_user_name = view.findViewById(R.id.ed_user_name);
        ed_name = view.findViewById(R.id.ed_name);
        ed_phone = view.findViewById(R.id.ed_phone);
        edl_user_name = view.findViewById(R.id.edl_user_name);
        edl_name = view.findViewById(R.id.edl_name);
        edl_phone = view.findViewById(R.id.edl_phone);
        btn_delete = view.findViewById(R.id.btn_delete);
        btn_cancel = view.findViewById(R.id.btn_cancel);

        Init();
        DeleteOnClick();
        CancelOnClick();

        return view;
    }

    private void CancelOnClick() {
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getContext()).replaceFragment(new AccountFragment(),"Tài khoản");
            }
        });
    }

    private void DeleteOnClick() {
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userDAO = new UserDAO(getContext());
                if(userDAO.DeleteUser(user.getUserName())){
                    Toast.makeText(getActivity(), "Xóa tài khoản thành công", Toast.LENGTH_SHORT).show();
                    ((MainActivity)getContext()).replaceFragment(new AccountFragment(),"Tài khoản");
                }
                else{
                    Toast.makeText(getActivity(), "Xóa tài khoản thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void Init() {
        tv_user_name.setText(user.getUserName());
        ed_user_name.setText(user.getUserName());
        ed_name.setText(user.getName());
        ed_phone.setText(user.getPhone());
    }
}
