package com.example.quanlygiatla_nhom1.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.quanlygiatla_nhom1.Class.ServiceClass;
import com.example.quanlygiatla_nhom1.R;
import com.example.quanlygiatla_nhom1.SQLite.DAO.ServiceDAO;

import java.util.List;

public class ServiceAdapter extends BaseAdapter {

    public Context context;
    public List<ServiceClass> services;
    ServiceDAO serviceDAO;
//    ServiceFragment serviceFragment;
    SharedPreferences sharedPreferences;

    public void ServiceApdater(Context context, List<ServiceClass> services) {
        this.context =context;
        this.services = services;
//        this.serviceFragment = serviceFragment;
    }

    @Override
    public int getCount() {
        return services.size();
    }

    @Override
    public Object getItem(int i) {
        return services.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if (view==null){
            viewHolder= new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.adapter_service,viewGroup,false);
            viewHolder.id = view.findViewById(R.id.tv_id);
            viewHolder.product_type = view.findViewById(R.id.tv_product_type);
            viewHolder.service_type = view.findViewById(R.id.tv_service_type);
            viewHolder.unit = view.findViewById(R.id.tv_unit);
            viewHolder.price = view.findViewById(R.id.tv_price);
            viewHolder.btn_status = view.findViewById(R.id.btn_status);
            viewHolder.l_service = view.findViewById(R.id.l_service);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.id.setText(services.get(i).getId());
        viewHolder.product_type.setText(services.get(i).getProduct_type());
        viewHolder.service_type.setText(services.get(i).getService_type());
        viewHolder.unit.setText(services.get(i).getUnit());
        viewHolder.price.setText(""+services.get(i).getPrice());
        Boolean active = services.get(i).getStatus().equalsIgnoreCase("Hoạt động");

        sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        String role = sharedPreferences.getString("Role","");
        if(role.equalsIgnoreCase("Admin")){
            if(active){
                viewHolder.btn_status.setText("Bật");
                viewHolder.btn_status.setBackgroundColor(Color.parseColor("#1A73E9"));
            }
            else{
                viewHolder.btn_status.setText("Tắt");
                viewHolder.btn_status.setBackgroundColor(Color.parseColor("#FE5758"));
            }

            viewHolder.btn_status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    serviceDAO = new ServiceDAO(context);
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setCancelable(true);
                    builder.setTitle("Đổi trạng thái");
                    builder.setMessage("Bạn xác định rằng sẽ " + (services.get(i).getStatus().equalsIgnoreCase("Hoạt động")?"Tắt":"Bật")+" dịch vụ");
                    builder.setPositiveButton("Xác nhận",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int j) {
//                                    if(serviceDAO.UpdateStatusOneService(services.get(i))){
//                                        Toast.makeText(context, "Thay đổi trạng thái thành công", Toast.LENGTH_SHORT).show();
//                                        serviceFragment.ReloadListView();
//                                    }else{
//                                        Toast.makeText(context, "Thay đổi trạng thái thất bại", Toast.LENGTH_SHORT).show();
//                                    }
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
        } else{
            viewHolder.btn_status.setVisibility(View.GONE);
        }


        viewHolder.l_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ((MainActivity)context).replaceFragment(new ServiceDetailFragment(services.get(i)),"Dịch vụ chi tiết");
            }
        });

        return view;
    }

    private class ViewHolder{
        TextView id;
        TextView product_type ;
        TextView service_type;
        TextView unit;
        TextView price;
        Button btn_status;
        ConstraintLayout l_service;
    }
}
