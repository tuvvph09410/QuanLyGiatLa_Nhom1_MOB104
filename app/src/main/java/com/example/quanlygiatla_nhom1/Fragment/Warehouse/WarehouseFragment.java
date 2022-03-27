package com.example.quanlygiatla_nhom1.Fragment.Warehouse;

import android.annotation.SuppressLint;
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

import com.example.quanlygiatla_nhom1.Adapter.WarehouseApdater;
import com.example.quanlygiatla_nhom1.Class.WarehouseClass;
import com.example.quanlygiatla_nhom1.R;
import com.example.quanlygiatla_nhom1.SQLite.DAO.WarehouseDAO;
import com.example.quanlygiatla_nhom1.Utilities.Utilities;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class WarehouseFragment extends Fragment {
    List<WarehouseClass> warehouses,warehouseSearch;
    WarehouseDAO warehouseDAO;
    ListView listView;
    FloatingActionButton fab_add;
    Utilities utils;
    Button btn_add,btn_cancel;
    TextInputEditText ed_id,ed_name;
    TextInputLayout edl_id,edl_name;
    SharedPreferences sharedPreferences;
    SearchView search;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
View view=inflater.inflate(R.layout.fragment_warehouse,container,false);
        listView = view.findViewById(R.id.list_view);
        fab_add = view.findViewById(R.id.fab_add);
        search = view.findViewById(R.id.search);
        Init();
        MapListView();
        OnSearching();

        return view;
    }

    private void OnSearching() {
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                warehouseSearch = new ArrayList<>();
                for (int i = 0; i < warehouses.size(); i++) {
                    if(warehouses.get(i).getId().startsWith(search.getQuery().toString())){
                        warehouseSearch.add(warehouses.get(i));
                    }
                }
                MapListView();
                return false;
            }
        });
    }

    //    *************************
    //    *    End Create View    *
    //    *************************

    private void Init(){
        utils = new Utilities();
        warehouseDAO = new WarehouseDAO(getContext());
        sharedPreferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        String role = sharedPreferences.getString("Role","");
        if(role.equalsIgnoreCase("Admin")){
            FabAddWarehouse();
        }else{
            fab_add.setVisibility(View.GONE);
        }
        warehouses = warehouseDAO.GetAllWarehouse();
        warehouseSearch = warehouses;
    }



    public void MapListView(){
        WarehouseApdater apdater = new WarehouseApdater(getContext(),warehouseSearch,this);
        listView.setAdapter(apdater);
    }
    public void ReloadListView(){
        warehouseSearch = warehouseDAO.GetAllWarehouse();
        WarehouseApdater apdater = new WarehouseApdater(getContext(),warehouseSearch,this);
        listView.setAdapter(apdater);
    }
    @SuppressLint("CutPasteId")
    private void FabAddWarehouse() {
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_add_warehouse);
                dialog.show();

                btn_add = dialog.findViewById(R.id.btn_add);
                btn_cancel = dialog.findViewById(R.id.btn_cancel);
                ed_id = dialog.findViewById(R.id.ed_id);
                ed_name = dialog.findViewById(R.id.ed_warehouse_name);
                edl_id = dialog.findViewById(R.id.edl_id);
                edl_name = dialog.findViewById(R.id.edl_warehouse_name);
                RemoveErrorTextOnChange();

                btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(ValidateAddWarehouse()){
                            WarehouseClass w = new WarehouseClass(
                                    ed_id.getText().toString(),
                                    ed_name.getText().toString(),
                                    "Trống"
                            );
                            if(warehouseDAO.AddOneWarehouse(w)){
                                Toast.makeText(getActivity(), "Thêm kho thành công", Toast.LENGTH_SHORT).show();
                                warehouses = warehouseDAO.GetAllWarehouse();
                                warehouseSearch = warehouses;
                                MapListView();
                                dialog.dismiss();
                            }else {
                                Toast.makeText(getActivity(), "Mã kho đã tồn tại", Toast.LENGTH_SHORT).show();
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
    private boolean ValidateAddWarehouse(){
        Matcher IDMatcher = utils.NSCPattern.matcher(ed_id.getText().toString().trim());
        Matcher NameMatcher = utils.NSCPattern.matcher(ed_name.getText().toString().trim());
        boolean success = true;
        if(!ed_id.getText().toString().trim().startsWith("K")){
            edl_id.setError(utils.WarehouseIdFormat);
            success = false;
        }
        if(!IDMatcher.matches()){
            edl_id.setError(utils.NotSpecialCharacter);
            success = false;
        }
        if(ed_id.getText().toString().trim().equalsIgnoreCase("")){
            edl_id.setError(utils.WarehouseIdRequire);
            success = false;
        }
        if(ed_id.getText().toString().trim().length() >20){
            edl_id.setError(utils.WarehouseIdLength);
            success = false;
        }
        if(!NameMatcher.matches()){
            edl_name.setError(utils.NotSpecialCharacter);
            success = false;
        }
        if(ed_name.getText().toString().trim().equalsIgnoreCase("")){
            edl_name.setError(utils.WarehouseNameRequire);
            success = false;
        }
        if(ed_name.getText().toString().trim().length() >20){
            edl_name.setError(utils.WarehouseNameLength);
            success = false;
        }
        return success;
    }
    private void RemoveErrorTextOnChange(){
        utils.removeErrorText(edl_id,ed_id);
        utils.removeErrorText(edl_name,ed_name);
    }
}
