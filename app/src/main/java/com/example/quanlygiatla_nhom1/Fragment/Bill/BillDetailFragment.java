package com.example.quanlygiatla_nhom1.Fragment.Bill;

import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.quanlygiatla_nhom1.Class.BillClass;
import com.example.quanlygiatla_nhom1.Class.BillDetailClass;
import com.example.quanlygiatla_nhom1.SQLite.DAO.BillDAO;
import com.example.quanlygiatla_nhom1.SQLite.DAO.BillDetailDAO;
import com.example.quanlygiatla_nhom1.Utilities.Utilities;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class BillDetailFragment extends Fragment {
    BillClass bill;
    List<BillDetailClass> billDetails;
    BillDetailDAO billDetailDAO;
    BillDAO billDAO;
    Utilities utils;
    TextView tv_id;
    TextInputLayout edl_create_date, edl_user_name, edl_customer_name, edl_customer_phone,edl_total;
    TextInputEditText ed_create_date, ed_user_name, ed_customer_name, ed_customer_phone,ed_total;
    Button btn_cancel,btn_edit,btn_delete;
    Spinner sp_status;
    ListView lv_detail;
    String total;
    SharedPreferences sharedPreferences;

    public BillDetailFragment( BillClass bill, String total) {
        this.bill = bill;
        this.total=total;
    }
}
