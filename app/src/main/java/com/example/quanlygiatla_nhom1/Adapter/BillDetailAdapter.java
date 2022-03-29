package com.example.quanlygiatla_nhom1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quanlygiatla_nhom1.Class.BillDetailClass;
import com.example.quanlygiatla_nhom1.R;

import java.util.List;

public class BillDetailAdapter extends BaseAdapter {

    public Context context;
    public List<BillDetailClass> billDetails;

    public BillDetailAdapter(Context context, List<BillDetailClass> billDetails) {
        this.context =context;
        this.billDetails = billDetails;
    }

    @Override
    public int getCount() {
        return billDetails.size();
    }

    @Override
    public Object getItem(int i) {
        return billDetails.get(i);
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
            view = LayoutInflater.from(context).inflate(R.layout.adapter_bill_detail,viewGroup,false);
            viewHolder.tv_index = view.findViewById(R.id.tv_index);
            viewHolder.tv_service = view.findViewById(R.id.tv_service_type);
            viewHolder.tv_warehouse = view.findViewById(R.id.tv_warehouse);
            viewHolder.tv_quantity = view.findViewById(R.id.tv_quantity);
            viewHolder.tv_price = view.findViewById(R.id.tv_price);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tv_index.setText(""+(i+1));
        viewHolder.tv_service.setText(billDetails.get(i).getServiceID());
        viewHolder.tv_warehouse.setText(billDetails.get(i).getWarehouseID());
        viewHolder.tv_quantity.setText(""+billDetails.get(i).getQuantity());
        viewHolder.tv_price.setText(""+billDetails.get(i).getTotal());

        return view;
    }

    private class ViewHolder{
        TextView tv_index,tv_service,tv_warehouse,tv_quantity,tv_price;
    }
}
