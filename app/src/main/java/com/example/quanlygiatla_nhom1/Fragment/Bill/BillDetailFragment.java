package com.example.quanlygiatla_nhom1.Fragment.Bill;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlygiatla_nhom1.Activity.MainActivity;
import com.example.quanlygiatla_nhom1.Adapter.BillDetailAdapter;
import com.example.quanlygiatla_nhom1.Class.BillClass;
import com.example.quanlygiatla_nhom1.Class.BillDetailClass;
import com.example.quanlygiatla_nhom1.Fragment.Bill.BillFragment;
import com.example.quanlygiatla_nhom1.R;
import com.example.quanlygiatla_nhom1.SQLite.DAO.BillDAO;
import com.example.quanlygiatla_nhom1.SQLite.DAO.BillDetailDAO;
import com.example.quanlygiatla_nhom1.Utilities.Utilities;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill_detail, container, false);

        tv_id = view.findViewById(R.id.tv_id);
        edl_create_date = view.findViewById(R.id.edl_create_date);
        edl_user_name = view.findViewById(R.id.edl_user_name);
        edl_customer_name = view.findViewById(R.id.edl_customer_name);
        edl_customer_phone = view.findViewById(R.id.edl_customer_phone);
        edl_total = view.findViewById(R.id.edl_total);
        ed_user_name = view.findViewById(R.id.ed_user_name);
        ed_customer_name = view.findViewById(R.id.ed_customer_name);
        ed_create_date = view.findViewById(R.id.ed_create_date);
        ed_customer_phone = view.findViewById(R.id.ed_customer_phone);
        ed_total = view.findViewById(R.id.ed_total);
        sp_status = view.findViewById(R.id.sp_status);
        btn_cancel = view.findViewById(R.id.btn_cancel);
        btn_edit = view.findViewById(R.id.btn_edit);
        btn_delete = view.findViewById(R.id.btn_delete);
        lv_detail = view.findViewById(R.id.lv_detail);

        Init();
        MapToListView();
        DeleteOnClick();
        EditOnClick();
        CancelOnClick();
        RemoveErrorTextOnChange();

        return view;
    }

    //    *************************
    //    *    End Create View    *
    //    *************************

    private void Init(){
        utils = new Utilities();
        tv_id.setText(bill.getId());
        ed_create_date.setText(utils.ChangeDate(bill.getDate()));
        ed_user_name.setText(bill.getUser_name());
        ed_customer_name.setText(bill.getCustomer_name());
        ed_customer_phone.setText(bill.getCustomer_phone());
        ed_total.setText(total);
        billDetailDAO = new BillDetailDAO(getContext());
        billDAO = new BillDAO(getContext());
        billDetails = billDetailDAO.GetAllBillDetailInfo(bill.getId().trim());
        ArrayList<String> arraySpinner = new ArrayList<>();
        arraySpinner.add("Chưa giặt");
        arraySpinner.add("Đã giặt");
        arraySpinner.add("Đã giao");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,arraySpinner);
        sp_status.setAdapter(spinnerAdapter);
        if(bill.getStatus().equalsIgnoreCase("Chưa giặt")){
            sp_status.setSelection(0);
        }else if(bill.getStatus().equalsIgnoreCase("Đã giặt")){
            sp_status.setSelection(1);
        }else if(bill.getStatus().equalsIgnoreCase("Đã giao")){
            sp_status.setSelection(2);
        }
        sharedPreferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        String role = sharedPreferences.getString("Role","");
        if(role.equalsIgnoreCase("Admin")){
            DeleteOnClick();
            sp_status.setEnabled(true);
        } else{
            btn_delete.setVisibility(View.GONE);
            sp_status.setEnabled(false);
        }
    }

    private void MapToListView(){
        BillDetailAdapter adapter = new BillDetailAdapter(getContext(),billDetails);
        lv_detail.setAdapter(adapter);

        ViewGroup vg = lv_detail;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += 350;
        }
        ViewGroup.LayoutParams par = lv_detail.getLayoutParams();
        par.height = totalHeight + (lv_detail.getDividerHeight() * (adapter.getCount() - 1));
        lv_detail.setLayoutParams(par);
        lv_detail.requestLayout();
    }

    private void DeleteOnClick(){
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(billDAO.DeleteOneBill(bill)){
                    Toast.makeText(getActivity(), "Xóa hóa đơn thành công", Toast.LENGTH_SHORT).show();
                    ((MainActivity)getContext()).replaceFragment(new BillFragment(),"Hóa đơn");
                } else {
                    Toast.makeText(getActivity(), "Xóa hóa đơn thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void EditOnClick(){
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ValidateEdit()){
                    BillClass b = new BillClass(
                            bill.getId(),
                            ed_customer_name.getText().toString(),
                            ed_customer_phone.getText().toString(),
                            bill.getDate(),
                            bill.getUser_name(),
                            sp_status.getSelectedItem().toString(),
                            bill.getDelete()
                    );
                    if(billDAO.UpdateOneBill(b)){
                        Toast.makeText(getActivity(), "Cập nhật hóa đơn thành công", Toast.LENGTH_SHORT).show();
                        ((MainActivity)getContext()).replaceFragment(new BillFragment(),"Hóa đơn");
                    }else{
                        Toast.makeText(getActivity(), "Cập nhật hóa đơn thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void CancelOnClick(){
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getContext()).replaceFragment(new BillFragment(),"Hóa đơn");
            }
        });
    }

    private boolean ValidateEdit(){
        Matcher NameMatcher = utils.NSCPattern.matcher(ed_customer_name.getText().toString().trim());
        boolean success = true;
        try {
            Integer.parseInt(ed_customer_phone.getText().toString());
        }catch (NumberFormatException ne){
            edl_customer_phone.setError(utils.PhoneInvalid);
            success = false;
        }
        if(ed_customer_phone.getText().toString().trim().length()!=10 ){
            edl_customer_phone.setError(utils.PhoneLength);
            success = false;
        }
        if(ed_customer_phone.getText().toString().trim().equalsIgnoreCase("")){
            edl_customer_phone.setError(utils.PhoneRequire);
            success = false;
        }
        if(!NameMatcher.matches()){
            edl_customer_name.setError(utils.NotSpecialCharacter);
            success = false;
        }
        if(ed_customer_name.getText().toString().trim().length()<2 || ed_customer_name.getText().toString().trim().length()>20 ){
            edl_customer_name.setError(utils.CustomerNameLength);
            success = false;
        }
        if(ed_customer_name.getText().toString().trim().equalsIgnoreCase("")){
            edl_customer_name.setError(utils.CustomerNameRequire);
            success = false;
        }
        return success;
    }

    private void RemoveErrorTextOnChange(){
        utils.removeErrorText(edl_customer_name,ed_customer_name);
        utils.removeErrorText(edl_customer_phone,ed_customer_phone);
    }
}
