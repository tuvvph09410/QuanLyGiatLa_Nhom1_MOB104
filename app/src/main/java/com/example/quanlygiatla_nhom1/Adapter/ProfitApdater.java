package com.example.quanlygiatla_nhom1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quanlygiatla_nhom1.Class.ProfitClass;
import com.example.quanlygiatla_nhom1.R;
import com.example.quanlygiatla_nhom1.Utilities.Utilities;

import java.util.List;

public class ProfitApdater extends BaseAdapter {
    public Context context;
    public List<ProfitClass> profits;
    Utilities utils = new Utilities();

    public ProfitApdater(Context context, List<ProfitClass> profits) {
        this.context =context;
        this.profits = profits;
    }
    private class ViewHolder{
        TextView tv_date;
        TextView tv_total ;
    }
    @Override
    public int getCount() {
        return profits.size();
    }

    @Override
    public Object getItem(int i) {
        return profits.get(i);
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
            view = LayoutInflater.from(context).inflate(R.layout.adapter_profit,viewGroup,false);
            viewHolder.tv_date = view.findViewById(R.id.tv_date);
            viewHolder.tv_total = view.findViewById(R.id.tv_total);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tv_date.setText(utils.ChangeDate(profits.get(i).getDate()));
        viewHolder.tv_total.setText(""+profits.get(i).getPrice());
        return view;
    }

}
