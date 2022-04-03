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
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlygiatla_nhom1.Activity.MainActivity;
import com.example.quanlygiatla_nhom1.Class.WarehouseClass;
import com.example.quanlygiatla_nhom1.Fragment.Warehouse.WarehouseDetailFragment;
import com.example.quanlygiatla_nhom1.Fragment.Warehouse.WarehouseFragment;
import com.example.quanlygiatla_nhom1.R;
import com.example.quanlygiatla_nhom1.SQLite.DAO.WarehouseDAO;

import java.util.List;

public class WarehouseApdater extends BaseAdapter {
public Context context;
public List<WarehouseClass> warehouses;
WarehouseDAO warehouseDAO;
WarehouseFragment warehouseFragment;
    public WarehouseApdater(Context context, List<WarehouseClass> warehouses, WarehouseFragment warehouse) {
        this.context =context;
        this.warehouses = warehouses;
        this.warehouseFragment = warehouse;
    }

    @Override
    public int getCount() {
        if (this.warehouses == null){
            return 0;
        }

        return this.warehouses.size();
    }

    @Override
    public Object getItem(int i) {
        if (this.warehouses == null){
            return null;
        }
        return this.warehouses.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       ViewHolder viewHolder=null;
if (view==null){
    viewHolder=new ViewHolder();
    view= LayoutInflater.from(context).inflate(R.layout.adapter_warehouse,viewGroup,false);
    viewHolder.tv_id = view.findViewById(R.id.tv_id);
    viewHolder.tv_name = view.findViewById(R.id.tv_name);
    viewHolder.tv_status = view.findViewById(R.id.tv_status);
    viewHolder.btn_status = view.findViewById(R.id.btn_status);
    viewHolder.l_warehouse = view.findViewById(R.id.l_warehouse);
    view.setTag(viewHolder);
}else{
    viewHolder = (ViewHolder) view.getTag();
}
        viewHolder.tv_id.setText(warehouses.get(i).getId());
        viewHolder.tv_name.setText(warehouses.get(i).getName());
        viewHolder.tv_status.setText(warehouses.get(i).getStatus());

        viewHolder.btn_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                warehouseDAO = new WarehouseDAO(context);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                builder.setTitle("Đổi trạng thái");
                builder.setMessage("Bạn xác định rằng sẽ đổi trạng thái từ "+warehouses.get(i).getStatus()+" sang "+(warehouses.get(i).getStatus().equalsIgnoreCase("Trống")?"Đã đầy":"Trống") );
                builder.setPositiveButton("Xác nhận",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int j) {
                                if(warehouseDAO.UpdateOneWarehouseStatus(warehouses.get(i))){
                                    Toast.makeText(context, "Sửa kho thành công", Toast.LENGTH_SHORT).show();
                                    warehouseFragment.ReloadListView();
                                }else{
                                    Toast.makeText(context, "Sửa kho thất bại", Toast.LENGTH_SHORT).show();
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
        viewHolder.l_warehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)context).replaceFragment(new WarehouseDetailFragment(warehouses.get(i)),"Kho chi tiết");
            }
        });

        return view;
    }
    private class ViewHolder{
        TextView tv_id;
        TextView tv_name ;
        TextView tv_status;
        Button btn_status;
        ConstraintLayout l_warehouse;
    }
}
