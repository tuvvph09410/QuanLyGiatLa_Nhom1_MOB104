package com.example.quanlygiatla_nhom1.Fragment.Service;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.quanlygiatla_nhom1.Adapter.ServiceAdapter;
import com.example.quanlygiatla_nhom1.Class.ServiceClass;
import com.example.quanlygiatla_nhom1.R;
import com.example.quanlygiatla_nhom1.SQLite.DAO.ServiceDAO;
import com.example.quanlygiatla_nhom1.Utilities.Utilities;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class ServiceFragment extends Fragment {

    List<ServiceClass> services,serviceSearch;
    ServiceDAO serviceDAO;
    ListView listView;
    FloatingActionButton fab_add;
    Utilities utils;
    Button btn_add,btn_cancel;
    TextInputLayout edl_id,edl_product_type,edl_service_type,edl_unit,edl_price;
    TextInputEditText ed_id,ed_product_type,ed_service_type,ed_unit,ed_price;
    SharedPreferences sharedPreferences;
    SearchView search;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service, container, false);

        listView = view.findViewById(R.id.list_view);
        fab_add = view.findViewById(R.id.fab_add);
        search = view.findViewById(R.id.search);

        Init();
        MapListView();
        OnSearching();

        return view;
    }

    private void Init() {
        utils = new Utilities();
        serviceDAO = new ServiceDAO(getActivity());
        sharedPreferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        String role = sharedPreferences.getString("Role","");
        if(role.equalsIgnoreCase("Admin")){
            FabAddOnClick();
        }else{
            fab_add.setVisibility(View.GONE);
        }
        services = serviceDAO.GetAllService();
        serviceSearch = services;
    }

    private void FabAddOnClick() {
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_add_service);
                dialog.show();

                btn_add = dialog.findViewById(R.id.btn_add);
                btn_cancel = dialog.findViewById(R.id.btn_cancel);
                ed_id = dialog.findViewById(R.id.ed_id);
                ed_product_type = dialog.findViewById(R.id.ed_product_type);
                ed_service_type = dialog.findViewById(R.id.ed_service_type);
                ed_unit = dialog.findViewById(R.id.ed_unit);
                ed_price = dialog.findViewById(R.id.ed_price);
                edl_id = dialog.findViewById(R.id.edl_id);
                edl_product_type = dialog.findViewById(R.id.edl_product_type);
                edl_service_type = dialog.findViewById(R.id.edl_service_type);
                edl_unit = dialog.findViewById(R.id.edl_unit);
                edl_price = dialog.findViewById(R.id.edl_price);
                RemoveErrorTextOnChange();

                btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(ValidateAddService()) {
                            ServiceClass s = new ServiceClass(
                                    ed_id.getText().toString(),
                                    ed_service_type.getText().toString(),
                                    ed_product_type.getText().toString(),
                                    ed_unit.getText().toString(),
                                    Integer.parseInt(ed_price.getText().toString()),
                                    "Hoạt động"
                            );
                            if (serviceDAO.AddOneService(s)) {
                                Toast.makeText(getActivity(), "Thêm dịch vụ thành công", Toast.LENGTH_SHORT).show();
                                services = serviceDAO.GetAllService();
                                serviceSearch = services;
                                MapListView();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(getActivity(), "Mã dịch vụ đã tồn tại", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });

                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

            }
        });

    }
    private void RemoveErrorTextOnChange(){
        utils.removeErrorText(edl_id,ed_id);
        utils.removeErrorText(edl_price,ed_price);
        utils.removeErrorText(edl_service_type,ed_service_type);
        utils.removeErrorText(edl_unit,ed_unit);
        utils.removeErrorText(edl_product_type,ed_product_type);
    }
    private boolean ValidateAddService(){
        Matcher IDMatcher = utils.NSCPattern.matcher(ed_id.getText().toString().trim());
        Matcher ServiceTypeMatcher = utils.NSCPattern.matcher(ed_service_type.getText().toString().trim());
        Matcher ProductTypeMatcher = utils.NSCPattern.matcher(ed_product_type.getText().toString().trim());
        Matcher UnitMatcher = utils.NSCPattern.matcher(ed_unit.getText().toString().trim());
        boolean success = true;
        if(!IDMatcher.matches()){
            edl_id.setError(utils.NotSpecialCharacter);
            success = false;
        }
        if(!ed_id.getText().toString().trim().startsWith("DV")){
            edl_id.setError(utils.ServiceIdFormat);
            success = false;
        }
        if(ed_id.getText().toString().trim().equalsIgnoreCase("")){
            edl_id.setError(utils.ServiceIdRequire);
            success = false;
        }
        if(ed_id.getText().toString().trim().length() >20){
            edl_id.setError(utils.ServiceIdLength);
            success = false;
        }
        if(!ServiceTypeMatcher.matches()){
            edl_service_type.setError(utils.NotSpecialCharacter);
            success = false;
        }
        if(ed_service_type.getText().toString().trim().equalsIgnoreCase("")){
            edl_service_type.setError(utils.ServiceTypeRequire);
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

    private void MapListView() {
        ServiceAdapter apdater = new ServiceAdapter(getContext(),serviceSearch,this);
        listView.setAdapter(apdater);
    }

    private void OnSearching() {
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                serviceSearch = new ArrayList<>();
                for (int i = 0; i < services.size(); i++) {
                    if(services.get(i).getId().startsWith(search.getQuery().toString())){
                        serviceSearch.add(services.get(i));
                    }
                }
                MapListView();
                return false;
            }
        });

    }

    public void ReloadListView() {
        serviceSearch = serviceDAO.GetAllService();
       ServiceAdapter apdater = new ServiceAdapter(getContext(),serviceSearch,this);
        listView.setAdapter(apdater);
    }
}
