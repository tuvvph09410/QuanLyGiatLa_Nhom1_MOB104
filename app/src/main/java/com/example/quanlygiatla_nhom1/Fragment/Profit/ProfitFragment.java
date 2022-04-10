package com.example.quanlygiatla_nhom1.Fragment.Profit;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.quanlygiatla_nhom1.Adapter.ProfitApdater;
import com.example.quanlygiatla_nhom1.Class.ProfitClass;
import com.example.quanlygiatla_nhom1.R;
import com.example.quanlygiatla_nhom1.SQLite.DAO.BillDetailDAO;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ProfitFragment extends Fragment {
    BarChart barChart;
    BillDetailDAO billDetailDAO;
    List<BarEntry> profit;
    ConstraintLayout l_chart;
    NumberPicker month_picker;
    Button btn_date,btn_done;
    ConstraintLayout l_month_picker;
    ListView lv_month_profit;
    TextView tv_day_profit,tv_total_profit;
    List<ProfitClass> profits;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profit, container, false);

        barChart = view.findViewById(R.id.barChart);
        l_chart = view.findViewById(R.id.l_chart);
        month_picker = view.findViewById(R.id.month_picker);
        btn_date = view.findViewById(R.id.btn_date);
        btn_done = view.findViewById(R.id.btn_done);
        l_month_picker = view.findViewById(R.id.l_month_picker);
        lv_month_profit = view.findViewById(R.id.lv_month_profit);
        tv_day_profit = view.findViewById(R.id.tv_day_profit);
        tv_total_profit = view.findViewById(R.id.tv_total_profit);

        Init();
        DateOnClick();
        DoneOnClick();

        return view;
}

    private void Init() {
        billDetailDAO = new BillDetailDAO(getContext());

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date now = Calendar.getInstance().getTime();
        Integer day_profit = billDetailDAO.GetOneDayProfit(""+simpleDateFormat.format(now));
        tv_day_profit.setText(""+day_profit);
        Integer total_profit = billDetailDAO.GetAllDayProfit();
        tv_total_profit.setText(""+total_profit);

        month_picker.setMaxValue(12);
        month_picker.setMinValue(1);
        month_picker.setValue(1);
        profit= new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            int p=0;
            if(i<10){
                p=billDetailDAO.GetProfitByMonth("0"+Integer.toString(i));
            } else {
                p=billDetailDAO.GetProfitByMonth(Integer.toString(i));
            }
            profit.add(new BarEntry(i, p));
        }
        BarDataSet barDataSet = new BarDataSet(profit, "Tháng");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
        BarData barData = new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("");
//        barChart.animateY(2000);
        String[] month = new String[]{"1","2","3","4","5","6","7","8","9","10","11","12"};

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(month));
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);
        barChart.setVisibleXRangeMaximum(13);
        l_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    private void DateOnClick() {
        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l_month_picker.setVisibility(View.VISIBLE);
            }
        });
    }

    private void DoneOnClick() {
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l_month_picker.setVisibility(View.GONE);
                lv_month_profit.setVisibility(View.VISIBLE);
                profits = new ArrayList<>();
                Toast.makeText(getActivity(), ""+month_picker.getValue(), Toast.LENGTH_SHORT).show();
                String month;
                if(month_picker.getValue()<10){
                    month="0"+ month_picker.getValue();
                } else {
                    month= ""+month_picker.getValue();
                }
                profits = billDetailDAO.GetOneMonthProfit(month);
                ProfitApdater adapter = new ProfitApdater(getContext(),profits);
                lv_month_profit.setAdapter(adapter);

                ViewGroup vg = lv_month_profit;
                int totalHeight = 0;
                for (int i = 0; i < adapter.getCount(); i++) {
                    View listItem = adapter.getView(i, null, vg);
                    listItem.measure(0, 0);
                    totalHeight += 150;
                }
                ViewGroup.LayoutParams par = lv_month_profit.getLayoutParams();
                par.height = totalHeight + (lv_month_profit.getDividerHeight() * (adapter.getCount() - 1));
                lv_month_profit.setLayoutParams(par);
                lv_month_profit.requestLayout();
            }
        });
    }
}
