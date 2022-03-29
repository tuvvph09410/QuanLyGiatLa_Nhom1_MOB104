package com.example.quanlygiatla_nhom1.Fragment.Bill;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.quanlygiatla_nhom1.Adapter.BillAdapter;
import com.example.quanlygiatla_nhom1.Class.BillClass;
import com.example.quanlygiatla_nhom1.Class.BillDetailClass;
import com.example.quanlygiatla_nhom1.Class.ServiceClass;
import com.example.quanlygiatla_nhom1.Class.WarehouseClass;
import com.example.quanlygiatla_nhom1.R;
import com.example.quanlygiatla_nhom1.SQLite.DAO.BillDAO;
import com.example.quanlygiatla_nhom1.SQLite.DAO.BillDetailDAO;
import com.example.quanlygiatla_nhom1.SQLite.DAO.ServiceDAO;
import com.example.quanlygiatla_nhom1.SQLite.DAO.WarehouseDAO;
import com.example.quanlygiatla_nhom1.Utilities.Utilities;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class BillFragment extends Fragment {
    FloatingActionButton fab_add;
    ListView listView;
    BillDAO billDAO;
    BillDetailDAO billDetailDAO;
    ServiceDAO serviceDAO;
    List<BillClass> bills, billSearch;
    String date = "";
    SharedPreferences sharedPreferences;
    ArrayList<String> serviceSpinner;
    ArrayList<String> warehouseSpinner;
    ArrayList<String> countSpinner;
    WarehouseDAO warehouseDAO;
    List<ServiceClass> services;
    List<WarehouseClass> warehouses;
    Button btn_add, btn_cancel, btn_date, btn_done, btn_detail;
    TextInputEditText ed_id, ed_customer_name, ed_customer_phone;
    TextInputLayout edl_id, edl_customer_name, edl_customer_phone;
    ConstraintLayout l_date_picker;
    LinearLayout l_detail;
    DatePicker datePicker;
    Utilities utils;
    SearchView search;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill, container, false);
        listView = view.findViewById(R.id.list_view);
        fab_add = view.findViewById(R.id.fab_add);
        search = view.findViewById(R.id.search);

        Init();
        OnSearching();
        MapListView();
        FabAdd();

        return view;
    }
    //    *************************
    //    *    End Create View    *
    //    *************************

    private void Init() {
        utils = new Utilities();
        serviceDAO = new ServiceDAO(getContext());
        countSpinner = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            countSpinner.add("" + (i + 1));
        }
        warehouseDAO = new WarehouseDAO(getContext());
        billDetailDAO = new BillDetailDAO(getContext());
        billDAO = new BillDAO(getContext());

        services = serviceDAO.GetSpinnerService();
        warehouses = warehouseDAO.GetSpinnerWarehouse();
        serviceSpinner = new ArrayList<>();
        for (int i = 0; i < services.size(); i++) {
            serviceSpinner.add(services.get(i).getId() + " - " + services.get(i).getService_type() + " - " + services.get(i).getProduct_type());
        }
        warehouseSpinner = new ArrayList<>();
        for (int i = 0; i < warehouses.size(); i++) {
            warehouseSpinner.add(warehouses.get(i).getId() + " - " + warehouses.get(i).getName());
        }
        sharedPreferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        bills = billDAO.GetAllBill();
        billSearch = bills;
    }


    private void MapListView() {
        BillAdapter adapter = new BillAdapter(getContext(), billSearch, this);
        listView.setAdapter(adapter);
    }

    private void FabAdd() {
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (warehouses.size() == 0 || services.size() == 0) {
                    Toast.makeText(getActivity(), "Kho hoặc dịch vụ đang trống, vui lòng liên hệ admin", Toast.LENGTH_SHORT).show();
                } else {

                    String UserName = sharedPreferences.getString("UserName", "");
                    Dialog dialog = new Dialog(getContext());
                    dialog.setContentView(R.layout.dialog_add_bill);
                    dialog.show();

                    ed_id = dialog.findViewById(R.id.ed_id);
                    ed_customer_name = dialog.findViewById(R.id.ed_customer_name);
                    ed_customer_phone = dialog.findViewById(R.id.ed_customer_phone);
                    edl_id = dialog.findViewById(R.id.edl_id);
                    edl_customer_name = dialog.findViewById(R.id.edl_customer_name);
                    edl_customer_phone = dialog.findViewById(R.id.edl_customer_phone);
                    btn_date = dialog.findViewById(R.id.btn_date);
                    datePicker = dialog.findViewById(R.id.datePicker);
                    btn_add = dialog.findViewById(R.id.btn_add);
                    btn_cancel = dialog.findViewById(R.id.btn_cancel);
                    btn_done = dialog.findViewById(R.id.btn_done);
                    btn_detail = dialog.findViewById(R.id.btn_bill_detail);
                    l_date_picker = dialog.findViewById(R.id.l_date_picker);
                    l_detail = dialog.findViewById(R.id.l_detail);

                    RemoveErrorTextOnChange();

                    btn_date.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (l_date_picker.getVisibility() == View.VISIBLE) {
                                l_date_picker.setVisibility(View.GONE);
                            } else {
                                l_date_picker.setVisibility(View.VISIBLE);
                            }
                        }
                    });

                    btn_done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            date = "" + datePicker.getDayOfMonth() + "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getYear();
                            btn_date.setText(date);
                            l_date_picker.setVisibility(View.GONE);
                        }
                    });

                    AddBillDetailOnClick();

                    btn_add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (ValidateAdd()) {
                                if (l_detail.getChildCount() == 0) {
                                    Toast.makeText(getContext(), "Vui lòng nhập ít nhất một dịch vụ", Toast.LENGTH_SHORT).show();
                                } else {
                                    BillClass b = new BillClass(
                                            ed_id.getText().toString(),
                                            ed_customer_name.getText().toString(),
                                            ed_customer_phone.getText().toString(),
                                            date,
                                            UserName,
                                            "Chưa giặt",
                                            "Chưa xóa"
                                    );

                                    if (billDAO.AddOneBill(b)) {
                                        for (int i = 0; i < l_detail.getChildCount() / 6; i++) {
                                            Spinner sp_service = new Spinner(getContext());
                                            sp_service = (Spinner) l_detail.getChildAt(i * 6 + 1);
                                            Spinner sp_warehouse = new Spinner(getContext());
                                            sp_warehouse = (Spinner) l_detail.getChildAt(i * 6 + 3);
                                            Spinner ed_quantity = new Spinner(getContext());
                                            ed_quantity = (Spinner) l_detail.getChildAt(i * 6 + 5);

                                            String[] sp_sv = sp_service.getSelectedItem().toString().split("-", 2);
                                            String[] sp_wh = sp_warehouse.getSelectedItem().toString().split("-", 2);
                                            Integer quantity = Integer.parseInt(ed_quantity.getSelectedItem().toString());
                                            Integer Price = quantity * serviceDAO.GetPrice(sp_sv[0].trim());

                                            BillDetailClass bd = new BillDetailClass(
                                                    ed_id.getText().toString(),
                                                    sp_sv[0].trim(),
                                                    sp_wh[0].trim(),
                                                    quantity,
                                                    Price
                                            );
                                            if (!billDetailDAO.AddOneBillDetail(bd)) {
                                                Toast.makeText(getActivity(), "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        bills = billDAO.GetAllBill();
                                        billSearch = bills;
                                        MapListView();
                                        dialog.dismiss();
                                        Toast.makeText(getActivity(), "Thêm hóa đơn thành công", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getActivity(), "Mã hóa đơn đã tồn tại", Toast.LENGTH_SHORT).show();
                                    }
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
            }
        });
    }
    private boolean ValidateAdd() {
        Matcher IDMatcher = utils.NSCPattern.matcher(ed_id.getText().toString().trim());
        Matcher CustomerNameMatcher = utils.NSCPattern.matcher(ed_customer_name.getText().toString().trim());
        boolean success = true;
        if (!IDMatcher.matches()) {
            edl_id.setError(utils.NotSpecialCharacter);
            success = false;
        }
        if (!ed_id.getText().toString().trim().startsWith("HD")) {
            edl_id.setError(utils.BillFormat);
            success = false;
        }
        if (ed_id.getText().toString().trim().length() > 20) {
            edl_id.setError(utils.BillIdLength);
            success = false;
        }
        if (ed_id.getText().toString().trim().equalsIgnoreCase("")) {
            edl_id.setError(utils.BillRequire);
            success = false;
        }
        if (!CustomerNameMatcher.matches()) {
            edl_customer_name.setError(utils.NotSpecialCharacter);
            success = false;
        }
        if (ed_customer_name.getText().toString().trim().length() > 20 || ed_customer_name.getText().toString().trim().length() < 2) {
            edl_customer_name.setError(utils.CustomerNameLength);
            success = false;
        }
        if (ed_customer_name.getText().toString().trim().equalsIgnoreCase("")) {
            edl_customer_name.setError(utils.CustomerNameRequire);
            success = false;
        }
        try {
            Integer.parseInt(ed_customer_phone.getText().toString());
        } catch (NumberFormatException ne) {
            edl_customer_phone.setError(utils.PhoneInvalid);
            success = false;
        }
        if (ed_customer_phone.getText().toString().trim().length() != 10) {
            edl_customer_phone.setError(utils.PhoneLength);
            success = false;
        }
        if (ed_customer_phone.getText().toString().trim().equalsIgnoreCase("")) {
            edl_customer_phone.setError(utils.PasswordRequire);
            success = false;
        }
        if (date.equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), utils.DateRequire, Toast.LENGTH_SHORT).show();
        }


        return success;
    }
    private void RemoveErrorTextOnChange() {
        utils.removeErrorText(edl_id, ed_id);
        utils.removeErrorText(edl_customer_name, ed_customer_name);
        utils.removeErrorText(edl_customer_phone, ed_customer_phone);
    }
    private void AddBillDetailOnClick() {
        btn_detail.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"ResourceAsColor", "Range"})
            @Override
            public void onClick(View view) {
                l_detail.setVisibility(View.VISIBLE);

                Spinner serviceClone = new Spinner(getContext().getApplicationContext());
                serviceClone.setMinimumHeight(60);
                serviceClone.setPadding(20, 0, 0, 0);
                ArrayAdapter<String> serviceAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, serviceSpinner);
                serviceClone.setAdapter(serviceAdapter);
                TextView tv_service = new TextView(getContext().getApplicationContext());
                tv_service.setText("Dịch vụ");
//                tv_service.setPadding(60, 20, 0, 0);
                tv_service.setPadding(40, 20, 0, 0);
                tv_service.setTextColor(Color.parseColor("#000000"));

                Spinner warehouseClone = new Spinner(getContext().getApplicationContext());
                warehouseClone.setMinimumHeight(60);
                warehouseClone.setPadding(20, 0, 0, 0);
                ArrayAdapter<String> warehouseAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, warehouseSpinner);
                warehouseClone.setAdapter(warehouseAdapter);
                TextView tv_warehouse = new TextView(getContext().getApplicationContext());
                tv_warehouse.setText("Kho");
//                tv_warehouse.setPadding(60, 20, 0, 0);
                tv_warehouse.setPadding(40, 20, 0, 0);
                tv_warehouse.setTextColor(Color.parseColor("#000000"));

                Spinner countClone = new Spinner(getContext().getApplicationContext());
                countClone.setMinimumHeight(60);
                countClone.setPadding(20, 0, 0, 0);
                ArrayAdapter<String> countAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, countSpinner);
                countClone.setAdapter(countAdapter);
                TextView tv_count = new TextView(getContext().getApplicationContext());
                tv_count.setText("Số lượng");
//                tv_count.setPadding(60, 20, 0, 0);
                tv_count.setPadding(40, 20, 0, 0);
                tv_count.setTextColor(Color.parseColor("#000000"));

                l_detail.addView(tv_service);
                l_detail.addView(serviceClone);
                l_detail.addView(tv_warehouse);
                l_detail.addView(warehouseClone);
                l_detail.addView(tv_count);
                l_detail.addView(countClone);
            }
        });
    }

    private void OnSearching() {
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                billSearch = new ArrayList<>();
                for (int i = 0; i < bills.size(); i++) {
                    if (bills.get(i).getId().startsWith(search.getQuery().toString())) {
                        billSearch.add(bills.get(i));
                    }
                }
                MapListView();
                return false;
            }
        });
    }

    public void ReloadListView() {
        billSearch = billDAO.GetAllBill();
        BillAdapter adapter = new BillAdapter(getContext(), billSearch, this);
        listView.setAdapter(adapter);
    }
}
