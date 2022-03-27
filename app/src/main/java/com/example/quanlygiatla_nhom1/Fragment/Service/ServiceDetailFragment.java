package com.example.quanlygiatla_nhom1.Fragment.Service;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.quanlygiatla_nhom1.Activity.MainActivity;
import com.example.quanlygiatla_nhom1.Class.ServiceClass;
import com.example.quanlygiatla_nhom1.R;
import com.example.quanlygiatla_nhom1.SQLite.DAO.ServiceDAO;
import com.example.quanlygiatla_nhom1.Utilities.Utilities;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;

public class ServiceDetailFragment extends Fragment {
    ServiceClass service;
    TextView tv_id;
    TextInputEditText ed_service_type,ed_product_type,ed_unit,ed_price;
    TextInputLayout edl_service_type,edl_product_type,edl_unit,edl_price;
    Button btn_edit,btn_status,btn_cancel;
    ServiceDAO serviceDAO;
    SharedPreferences sharedPreferences;
    Utilities utils;
    public ServiceDetailFragment(ServiceClass service) {
        this.service = service;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service_detail, container, false);

        tv_id = view.findViewById(R.id.tv_id);
        ed_service_type = view.findViewById(R.id.ed_service_type);
        ed_product_type = view.findViewById(R.id.ed_product_type);
        ed_unit = view.findViewById(R.id.ed_unit);
        ed_price = view.findViewById(R.id.ed_price);
        edl_service_type = view.findViewById(R.id.edl_service_type);
        edl_product_type = view.findViewById(R.id.edl_product_type);
        edl_unit = view.findViewById(R.id.edl_unit);
        edl_price = view.findViewById(R.id.edl_price);
        btn_edit = view.findViewById(R.id.btn_edit);
        btn_status = view.findViewById(R.id.btn_status);
        btn_cancel = view.findViewById(R.id.btn_cancel);

        Init();
        CancelOnClick();
        RemoveErrorTextOnChange();

        return view;
    }


    private void Init() {
        utils = new Utilities();
        tv_id.setText(service.getId());
        ed_service_type.setText(service.getService_type());
        ed_product_type.setText(service.getProduct_type());
        ed_unit.setText(service.getUnit());
        ed_price.setText(""+service.getPrice());

        sharedPreferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        String role = sharedPreferences.getString("Role","");
        if(role.equalsIgnoreCase("Admin")){
            Boolean active = service.getStatus().equalsIgnoreCase("Hoạt động");
            StatusOnClick();
            if(active){
                btn_status.setText("Tắt dịch vụ");
                btn_status.setBackgroundColor(Color.parseColor("#FE5758"));
            }
            else{
                btn_status.setText("Bật dịch vụ");
                btn_status.setBackgroundColor(Color.parseColor("#1A73E9"));
            }
            EditOnClick();

            edl_service_type.setEnabled(true);
            edl_product_type.setEnabled(true);
            edl_unit.setEnabled(true);
            edl_price.setEnabled(true);

        } else{
            btn_status.setVisibility(View.GONE);
            btn_edit.setVisibility(View.GONE);

            edl_service_type.setEnabled(false);
            edl_product_type.setEnabled(false);
            edl_unit.setEnabled(false);
            edl_price.setEnabled(false);
        }
    }
    private void StatusOnClick(){
        btn_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serviceDAO = new ServiceDAO(getContext());
                if(serviceDAO.UpdateStatusOneService(service)){
                    Toast.makeText(getContext(), "Sửa dịch vụ thành công", Toast.LENGTH_SHORT).show();
                    ((MainActivity)getContext()).replaceFragment(new ServiceFragment(),"Dịch vụ");
                }else{
                    Toast.makeText(getContext(), "Sửa dịch vụ thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void EditOnClick(){
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ValidateEdit()){
                    serviceDAO = new ServiceDAO(getContext());
                    ServiceClass s = new ServiceClass(
                            tv_id.getText().toString(),
                            ed_service_type.getText().toString(),
                            ed_product_type.getText().toString(),
                            ed_unit.getText().toString(),
                            Integer.parseInt(ed_price.getText().toString()),
                            service.getStatus()
                    );
                    if(serviceDAO.UpdateOneService(s)){
                        Toast.makeText(getContext(), "Sửa dịch vụ thành công", Toast.LENGTH_SHORT).show();
                        ((MainActivity)getContext()).replaceFragment(new ServiceFragment(),"Dịch vụ");
                    }else{
                        Toast.makeText(getContext(), "Sửa dịch vụ thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void CancelOnClick() {
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getContext()).replaceFragment(new ServiceFragment(),"Dịch vụ");
            }
        });
    }
    private boolean ValidateEdit(){
        Matcher ServiceTypeMatcher = utils.NSCPattern.matcher(ed_service_type.getText().toString().trim());
        Matcher ProductTypeMatcher = utils.NSCPattern.matcher(ed_product_type.getText().toString().trim());
        Matcher UnitMatcher = utils.NSCPattern.matcher(ed_unit.getText().toString().trim());
        boolean success = true;
        if(ed_service_type.getText().toString().trim().equalsIgnoreCase("")){
            edl_service_type.setError(utils.ServiceTypeRequire);
            success = false;
        }
        if(!ServiceTypeMatcher.matches()){
            edl_service_type.setError(utils.NotSpecialCharacter);
            success = false;
        }
        if(ed_service_type.getText().toString().trim().length() >20){
            edl_service_type.setError(utils.ServiceTypeLength);
            success = false;
        }
        if(!ProductTypeMatcher.matches()){
            edl_product_type.setError(utils.NotSpecialCharacter);
            success = false;
        }
        if(ed_product_type.getText().toString().trim().equalsIgnoreCase("")){
            edl_product_type.setError(utils.ProductTypeRequire);
            success = false;
        }
        if(ed_product_type.getText().toString().trim().length() >20){
            edl_product_type.setError(utils.ProductTypeLength);
            success = false;
        }
        if(!UnitMatcher.matches()){
            edl_unit.setError(utils.NotSpecialCharacter);
            success = false;
        }
        if(ed_unit.getText().toString().trim().equalsIgnoreCase("")){
            edl_unit.setError(utils.UnitRequire);
            success = false;
        }
        if(ed_unit.getText().toString().trim().length() >20){
            edl_unit.setError(utils.UnitLength);
            success = false;
        }
        try {
            Integer.parseInt(ed_price.getText().toString());
        }catch (NumberFormatException ne){
            edl_price.setError(utils.PriceInvalid);
            success = false;
        }
        if(ed_price.getText().toString().trim().equalsIgnoreCase("")){
            edl_price.setError(utils.PriceRequire);
            success = false;
        }
        if(ed_price.getText().toString().trim().length() >20){
            edl_price.setError(utils.PriceLength);
            success = false;
        }
        return success;
    }

    private void RemoveErrorTextOnChange() {
        utils.removeErrorText(edl_service_type,ed_service_type);
        utils.removeErrorText(edl_product_type,ed_product_type);
        utils.removeErrorText(edl_unit,ed_unit);
        utils.removeErrorText(edl_price,ed_price);
    }



}
