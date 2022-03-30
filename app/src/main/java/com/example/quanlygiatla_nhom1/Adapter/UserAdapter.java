package com.example.quanlygiatla_nhom1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.quanlygiatla_nhom1.Activity.MainActivity;
import com.example.quanlygiatla_nhom1.Class.UserClass;
import com.example.quanlygiatla_nhom1.Fragment.Account.AccountDetailFragment;
import com.example.quanlygiatla_nhom1.R;

import java.util.List;

public class UserAdapter extends BaseAdapter {
    public Context context;
    public List<UserClass> users;

    public UserAdapter(Context context, List<UserClass> users) {
        this.context =context;
        this.users = users;
    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if (view==null){
            viewHolder= new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.adapter_account,viewGroup,false);
            viewHolder.tv_user_name = view.findViewById(R.id.tv_user_name);
            viewHolder.tv_name = view.findViewById(R.id.tv_name);
            viewHolder.tv_phone = view.findViewById(R.id.tv_phone);
            viewHolder.tv_delete = view.findViewById(R.id.tv_delete);
            viewHolder.l_user = view.findViewById(R.id.l_user);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tv_user_name.setText(users.get(i).getUserName());
        viewHolder.tv_name.setText(users.get(i).getName());
        viewHolder.tv_phone.setText(users.get(i).getPhone());
        viewHolder.tv_delete.setText(users.get(i).getDelete().equalsIgnoreCase("Chưa xóa")?"":"Đã xóa");

        viewHolder.l_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)context).replaceFragment(new AccountDetailFragment(users.get(i)),"Tài khoản chi tiết");
            }
        });

        return view;
    }

    private class ViewHolder{
        TextView tv_user_name;
        TextView tv_name;
        TextView tv_phone;
        TextView tv_delete;
        ConstraintLayout l_user;
    }
}
