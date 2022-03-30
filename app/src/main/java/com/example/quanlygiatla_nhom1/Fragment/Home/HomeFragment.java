package com.example.quanlygiatla_nhom1.Fragment.Home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.quanlygiatla_nhom1.Activity.MainActivity;
import com.example.quanlygiatla_nhom1.Class.UserClass;
import com.example.quanlygiatla_nhom1.Fragment.Bill.BillFragment;
import com.example.quanlygiatla_nhom1.Fragment.Service.ServiceFragment;
import com.example.quanlygiatla_nhom1.Fragment.Warehouse.WarehouseFragment;
import com.example.quanlygiatla_nhom1.R;
import com.example.quanlygiatla_nhom1.SQLite.DAO.BillDetailDAO;
import com.example.quanlygiatla_nhom1.SQLite.DAO.UserDAO;
import com.example.quanlygiatla_nhom1.SQLite.SQLite;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    ConstraintLayout l_bill,l_service,l_warehouse, l_account,l_chart;
    BarChart barChart;
    Button btn_reset;
    BillDetailDAO billDetailDAO;
    List<BarEntry> profit;
    SQLite sqLite;
    SharedPreferences sharedPreferences;
    FrameLayout l_bar_chart;
    UserDAO userDAO;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        l_bill = view.findViewById(R.id.l_bill);
        l_service = view.findViewById(R.id.l_service);
        l_warehouse = view.findViewById(R.id.l_warehouse);
        l_account = view.findViewById(R.id.l_account);
        barChart = view.findViewById(R.id.barChart);
        btn_reset = view.findViewById(R.id.btn_reset);
        l_chart = view.findViewById(R.id.l_chart);
        l_bar_chart = view.findViewById(R.id.l_bar_chart);

        LayoutBill();
        LayoutService();
        LayoutWarehouse();
        LayoutAccount();
        LayoutChart();
        Init();

        return view;
    }

    private void LayoutBill() {
        l_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getContext()).replaceFragment(new BillFragment(),"Hóa đơn");
            }
        });
    }

    private void LayoutService() {
        l_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getContext()).replaceFragment(new ServiceFragment(),"Dịch vụ");
            }
        });
    }

    private void LayoutWarehouse() {
        l_warehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getContext()).replaceFragment(new WarehouseFragment(),"Kho");
            }
        });
    }

    private void LayoutAccount() {
//        l_account.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ((MainActivity)getContext()).replaceFragment(new PersonalFragment(),"Thông tin cá nhân");
//            }
//        });
    }

    private void LayoutChart() {
        l_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    private void Init() {
        sharedPreferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        String role = sharedPreferences.getString("Role","");
        if(role.equalsIgnoreCase("Admin")){
            ButtonReset();
            BarChartProfit();
        } else{
            l_bar_chart.setVisibility(View.GONE);
            btn_reset.setVisibility(View.GONE);
        }
    }



    private void ButtonReset(){
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setTitle("Đặt lại và sao lưu toàn bộ dữ liệu");
                builder.setMessage("Bạn chắc chắn rằng sẽ đặt lại và sao lưu toàn bộ dữ liệu trong app?");
                builder.setPositiveButton("Xác nhận",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                sqLite = new SQLite(getContext());
                                sqLite.BackupAndReset();
                                Toast.makeText(getActivity(), "Sao lưu và đặt lại thành công", Toast.LENGTH_SHORT).show();
                                userDAO = new UserDAO(getContext());
                                if(userDAO.GetAllNotDeleteUser().size()==0){
                                    UserClass user = new UserClass(
                                            "Kien",
                                            "0961187214",
                                            "admin",
                                            "admin",
                                            "Admin","Chưa xóa");
                                    userDAO.AddOneUser(user);
                                }
                                ((MainActivity)getActivity()).replaceFragment(new HomeFragment(),"Trang chủ");
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
    }
    private void BarChartProfit(){
        billDetailDAO = new BillDetailDAO(getContext());
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
        String[] month = new String[]{"1","2","3","4","5","6","7","8","9","10","11","12"};
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(month));
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);
        barChart.setVisibleXRangeMaximum(13);
    }

}
