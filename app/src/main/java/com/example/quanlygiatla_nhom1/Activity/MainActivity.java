package com.example.quanlygiatla_nhom1.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.quanlygiatla_nhom1.Class.UserClass;
import com.example.quanlygiatla_nhom1.Fragment.Account.AccountFragment;
import com.example.quanlygiatla_nhom1.Fragment.Bill.BillFragment;
import com.example.quanlygiatla_nhom1.Fragment.ChangePassword.ChangePasswordFragment;
import com.example.quanlygiatla_nhom1.Fragment.Home.HomeFragment;
import com.example.quanlygiatla_nhom1.Fragment.Personal.PersonalFragment;
import com.example.quanlygiatla_nhom1.Fragment.Profit.ProfitFragment;
import com.example.quanlygiatla_nhom1.Fragment.ResetPassword.ResetPasswordFragment;
import com.example.quanlygiatla_nhom1.Fragment.Service.ServiceFragment;
import com.example.quanlygiatla_nhom1.Fragment.Warehouse.WarehouseFragment;
import com.example.quanlygiatla_nhom1.R;
import com.example.quanlygiatla_nhom1.SQLite.DAO.UserDAO;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    MaterialToolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    UserDAO userDAO;
    List<UserClass> users;
    ArrayList<String> userSpinner;
    SharedPreferences sharedPreferences;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        this.actionBar();
        Init();
        ToolbarOnClick();
        NavigationSelect();
    }

    private void actionBar() {
        this.actionBar = getSupportActionBar();
        if (this.actionBar != null) {
            this.actionBar.hide();
        }
    }

    //    *************************
    //    *    End Create View    *
    //    *************************

    public void replaceFragment(Fragment fragment, String name) {
        toolbar.setTitle(name);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    private void Init() {
        userDAO = new UserDAO(this);
        users = userDAO.GetAllUser();
        userSpinner = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            userSpinner.add(users.get(i).getUserName());
        }
        replaceFragment(new HomeFragment(), "Trang ch???");
    }

    private void ToolbarOnClick() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void NavigationSelect() {
        sharedPreferences = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        String role = sharedPreferences.getString("Role", "");
        Log.e("role", role);

        if (role.equalsIgnoreCase("User")) {
            navigationView.getMenu().findItem(R.id.nav_profit).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_account).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_reset_password).setVisible(false);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        replaceFragment(new HomeFragment(), "Trang ch???");
                        break;

                    case R.id.nav_bill:
                        replaceFragment(new BillFragment(), "H??a ????n");
                        break;

                    case R.id.nav_service:
                        replaceFragment(new ServiceFragment(), "D???ch v???");
                        break;

                    case R.id.nav_warehouse:
                        replaceFragment(new WarehouseFragment(), "Kho");
                        break;

                    case R.id.nav_profit:
                        replaceFragment(new ProfitFragment(),"Doanh thu");break;

                    case R.id.nav_account:
                        replaceFragment(new AccountFragment(), "Qu???n l?? t??i kho???n");
                        break;

                    case R.id.nav_personal:
                        replaceFragment(new PersonalFragment(), "Th??ng tin c?? nh??n");
                        break;

                    case R.id.nav_change_password:
                        replaceFragment(new ChangePasswordFragment(),"?????i m???t kh???u");
                        break;

                    case R.id.nav_reset_password:
                        replaceFragment(new ResetPasswordFragment(userSpinner),"?????t l???i m???t kh???u");break;

                    case R.id.nav_logout: {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        MainActivity.this.finish();
                    }
                    break;

                    default:
                        return true;
                }
                return false;
            }
        });
    }

}