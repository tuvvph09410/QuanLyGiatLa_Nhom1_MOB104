package com.example.quanlygiatla_nhom1.Fragment.Account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.quanlygiatla_nhom1.Adapter.UserAdapter;
import com.example.quanlygiatla_nhom1.Class.UserClass;
import com.example.quanlygiatla_nhom1.R;
import com.example.quanlygiatla_nhom1.SQLite.DAO.UserDAO;

import java.util.List;

public class AccountFragment  extends Fragment {
    ListView listView;
    List<UserClass> users;
    UserDAO userDAO;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        listView = view.findViewById(R.id.list_view);

        Init();
        MapListView();

        return view;
    }

    private void MapListView() {
        users = userDAO.GetAllUser();
        UserAdapter apdater = new UserAdapter(getContext(),users);
        listView.setAdapter(apdater);
    }

    private void Init() {
        userDAO = new UserDAO(getContext());
    }
}
