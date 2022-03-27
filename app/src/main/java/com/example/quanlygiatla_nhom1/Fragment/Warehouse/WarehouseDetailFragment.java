package com.example.quanlygiatla_nhom1.Fragment.Warehouse;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.quanlygiatla_nhom1.Activity.MainActivity;
import com.example.quanlygiatla_nhom1.Class.WarehouseClass;
import com.example.quanlygiatla_nhom1.R;
import com.example.quanlygiatla_nhom1.SQLite.DAO.WarehouseDAO;
import com.example.quanlygiatla_nhom1.Utilities.Utilities;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class WarehouseDetailFragment extends Fragment {
    TextView tv_id;
    TextInputEditText ed_warehouse_name;
    TextInputLayout edl_warehouse_name;
    Button btn_edit,btn_cancel;
    WarehouseClass warehouse;
    Spinner sp_status;
    WarehouseDAO warehouseDAO;
    Utilities utils;
    SharedPreferences sharedPreferences;
    public WarehouseDetailFragment(WarehouseClass warehouse) {
        this.warehouse = warehouse;
    }
    @Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
    View view=inflater.inflate(R.layout.fragment_warehouse_detail,container,false);
        tv_id = view.findViewById(R.id.tv_id);
        ed_warehouse_name = view.findViewById(R.id.ed_warehouse_name);
        edl_warehouse_name = view.findViewById(R.id.edl_warehouse_name);
        btn_edit = view.findViewById(R.id.btn_edit);
        btn_cancel = view.findViewById(R.id.btn_cancel);
        sp_status = view.findViewById(R.id.sp_status);

        Init();
        EditOnClick();
        CancelOnClick();
        RemoveErrorTextOnChange();

        return view;
    }
    //    *************************
    //    *    End Create View    *
    //    *************************

    private void Init() {
        utils = new Utilities();
        tv_id.setText(warehouse.getId());
        ed_warehouse_name.setText(warehouse.getName());
        ArrayList<String> arraySpinner = new ArrayList<>();
        Boolean empty = warehouse.getStatus().equalsIgnoreCase("Trống");
        arraySpinner.add("Trống");
        arraySpinner.add("Đã sử dụng");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,arraySpinner);
        sp_status.setAdapter(spinnerAdapter);

        if(empty){
            sp_status.setSelection(0);
        }else{
            sp_status.setSelection(1);
        }

        sharedPreferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        String role = sharedPreferences.getString("Role","");
        if(role.equalsIgnoreCase("Admin")){
            edl_warehouse_name.setEnabled(true);
        } else{
            edl_warehouse_name.setEnabled(false);
        }
    }

    private void EditOnClick() {
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ValidateEdit()){
                    warehouseDAO = new WarehouseDAO(getContext());
                    WarehouseClass w = new WarehouseClass(warehouse.getId(),ed_warehouse_name.getText().toString(),sp_status.getSelectedItem().toString());
                    if(warehouseDAO.UpdateOneWarehouse(w)){
                        Toast.makeText(getContext(), "Sửa kho thành công", Toast.LENGTH_SHORT).show();
                        ((MainActivity)getContext()).replaceFragment(new WarehouseFragment(),"Kho");
                    }else{
                        Toast.makeText(getContext(), "Sửa kho thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void CancelOnClick() {
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getContext()).replaceFragment(new WarehouseFragment(),"Kho");
            }
        });
    }
    private boolean ValidateEdit(){
        Matcher NameMatcher = utils.NSCPattern.matcher(ed_warehouse_name.getText().toString().trim());
        boolean success = true;
        if(!NameMatcher.matches()){
            edl_warehouse_name.setError(utils.NotSpecialCharacter);
            success = false;
        }
        if(ed_warehouse_name.getText().toString().trim().length() >20){
            edl_warehouse_name.setError(utils.WarehouseNameLength);
            success = false;
        }
        if(ed_warehouse_name.getText().toString().trim().equalsIgnoreCase("")){
            edl_warehouse_name.setError(utils.WarehouseNameRequire);
            success = false;
        }
        return success;
    }

    private void RemoveErrorTextOnChange() {
        utils.removeErrorText(edl_warehouse_name,ed_warehouse_name);
    }
}
