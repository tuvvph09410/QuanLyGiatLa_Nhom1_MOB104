package com.example.quanlygiatla_nhom1.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.quanlygiatla_nhom1.Activity.MainActivity;
import com.example.quanlygiatla_nhom1.Class.BillClass;
import com.example.quanlygiatla_nhom1.Fragment.Bill.BillDetailFragment;
import com.example.quanlygiatla_nhom1.Fragment.Bill.BillFragment;
import com.example.quanlygiatla_nhom1.R;
import com.example.quanlygiatla_nhom1.SQLite.DAO.BillDAO;
import com.example.quanlygiatla_nhom1.SQLite.DAO.BillDetailDAO;
import com.example.quanlygiatla_nhom1.Utilities.Utilities;

import java.util.ArrayList;
import java.util.List;

public class BillAdapter extends BaseAdapter {
    public Context context;
    public List<BillClass> bills;

    BillDAO billDAO;
    BillDetailDAO billDetailDAO;
    BillFragment billFragment;
    Utilities utils = new Utilities();

    public BillAdapter(Context context, List<BillClass> bills, BillFragment billFragment) {
        this.context = context;
        this.bills = bills;
        this.billFragment = billFragment;
        billDAO = new BillDAO(context);
        billDetailDAO = new BillDetailDAO(context);
    }

    @Override
    public int getCount() {
        if (this.bills == null) {
            return 0;
        }
        return this.bills.size();
    }

    @Override
    public Object getItem(int i) {
        if (this.bills == null) {
            return null;
        }
        return this.bills.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.adapter_bill, viewGroup, false);
            viewHolder.tv_id = view.findViewById(R.id.tv_id);
            viewHolder.tv_date = view.findViewById(R.id.tv_date);
            viewHolder.tv_user_name = view.findViewById(R.id.tv_user_name);
            viewHolder.tv_customer_name = view.findViewById(R.id.tv_customer_name);
            viewHolder.tv_phone = view.findViewById(R.id.tv_phone);
            viewHolder.tv_status = view.findViewById(R.id.tv_status);
            viewHolder.tv_service_id = view.findViewById(R.id.tv_service_id);
            viewHolder.tv_warehouse_id = view.findViewById(R.id.tv_warehouse_id);
            viewHolder.tv_delete = view.findViewById(R.id.tv_delete);
            viewHolder.btn_status = view.findViewById(R.id.btn_status);
            viewHolder.l_bill = view.findViewById(R.id.l_bill);
            viewHolder.tv_total = view.findViewById(R.id.tv_total);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        List<String> warehouseID = billDetailDAO.GetAllWarehouseBill(bills.get(i).getId().trim());
        List<String> serviceID = billDetailDAO.GetAllServiceBill(bills.get(i).getId().trim());
        List<String> uniqueWarehouseID = new ArrayList<>();
        List<String> uniqueServiceID = new ArrayList<>();
        for (String w : warehouseID) {
            if (!uniqueWarehouseID.contains(w)) {
                uniqueWarehouseID.add(w);
            }
        }
        for (String s : serviceID) {
            if (!uniqueServiceID.contains(s)) {
                uniqueServiceID.add(s);
            }
        }

        String total = "" + billDetailDAO.GetBillTotal(bills.get(i).getId());

        viewHolder.tv_id.setText(bills.get(i).getId());
        viewHolder.tv_date.setText(utils.ChangeDate(bills.get(i).getDate()));
        viewHolder.tv_user_name.setText(bills.get(i).getUser_name());
        viewHolder.tv_customer_name.setText(bills.get(i).getCustomer_name());
        viewHolder.tv_phone.setText(bills.get(i).getCustomer_phone());
        viewHolder.tv_service_id.setText(uniqueServiceID.toString());
        viewHolder.tv_warehouse_id.setText(uniqueWarehouseID.toString());
        viewHolder.tv_status.setText(bills.get(i).getStatus());
        viewHolder.tv_delete.setText(bills.get(i).getDelete().equalsIgnoreCase("Đã xóa") ? "Đã xóa" : "");
        viewHolder.tv_total.setText(total);

        viewHolder.btn_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                builder.setTitle("Đổi trạng thái");
                builder.setMessage("Bạn xác định rằng sẽ đổi từ trạng thái " + bills.get(i).getStatus() + " sang trạng thái tiếp theo?");
                builder.setPositiveButton("Xác nhận",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int j) {
                                if (billDAO.UpdateStatusOneBill(bills.get(i))) {
                                    Toast.makeText(context, "Đổi trạng thái thành công", Toast.LENGTH_SHORT).show();
                                    billFragment.ReloadListView();
                                } else {
                                    Toast.makeText(context, "Đổi trạng thái thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        viewHolder.l_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) context).replaceFragment(new BillDetailFragment(bills.get(i), total), "Chi tiết hóa đơn");
            }
        });

        return view;


    }

    private class ViewHolder {
        TextView tv_id, tv_date, tv_user_name, tv_customer_name, tv_phone, tv_status, tv_service_id, tv_warehouse_id, tv_delete, tv_total;
        Button btn_status;
        ConstraintLayout l_bill;
    }


}
