package com.example.pro1122_nhm4.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pro1122_nhm4.Activity.SignUpActivity;
import com.example.pro1122_nhm4.Adapter.StaticAdapterAdmin;
import com.example.pro1122_nhm4.DAO.OrderDAO;
import com.example.pro1122_nhm4.Model.Order;
import com.example.pro1122_nhm4.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;


public class StatisticalFragmentAdmin extends Fragment {

    private LinearLayout layout_fromDate, layout_toDate;
    private EditText edt_fromDateStatic, edt_toDateStatic;
    private TextView tv_DoanhThu;
    private RecyclerView recyclerView_Revenue;
    private StaticAdapterAdmin statisticalAdapter;
    private OrderDAO orderDAO;
    private List<Order> orderList;
    private Button btn_Statistical;

    // Định dạng hiển thị và kiểm tra ngày
    private final SimpleDateFormat displayFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private final SimpleDateFormat comparisonFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public StatisticalFragmentAdmin() {
    }

    public static StatisticalFragmentAdmin newInstance() {
        return new StatisticalFragmentAdmin();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_statistical_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Ánh xạ view
        layout_fromDate = view.findViewById(R.id.layout_fromDate);
        layout_toDate = view.findViewById(R.id.layout_toDate);
        edt_fromDateStatic = view.findViewById(R.id.edt_fromDateStatic);
        edt_toDateStatic = view.findViewById(R.id.edt_toDateStatic);
        tv_DoanhThu = view.findViewById(R.id.tv_DoanhThu);
        btn_Statistical = view.findViewById(R.id.btn_Statistical);
        recyclerView_Revenue = view.findViewById(R.id.recylerView_Revenue);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView_Revenue.setLayoutManager(layoutManager);
        // Cài đặt ngày mặc định: từ ngày 7 ngày trước đến hôm nay
        Calendar calendar = Calendar.getInstance();
        edt_toDateStatic.setText(displayFormat.format(calendar.getTime()));
        calendar.add(Calendar.DATE, -7);
        edt_fromDateStatic.setText(displayFormat.format(calendar.getTime()));
        // Xử lý click chọn ngày
        layout_fromDate.setOnClickListener(v -> showDatePickerDialog(true));
        edt_fromDateStatic.setOnClickListener(v -> showDatePickerDialog(true));
        layout_toDate.setOnClickListener(v -> showDatePickerDialog(false));
        edt_toDateStatic.setOnClickListener(v -> showDatePickerDialog(false));
        // Nút xem thống kê
        btn_Statistical.setOnClickListener(v -> handleStatistics());
    }

    // Hàm hiển thị DatePickerDialog
    private void showDatePickerDialog(boolean isFromDate) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog(requireActivity(), R.style.CustomDatePickerDialog,
                (view, selectedYear, selectedMonth, selectedDayOfMonth) -> {
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(selectedYear, selectedMonth, selectedDayOfMonth);

                    // Định dạng ngày cho hiển thị
                    String formattedDisplayDate = displayFormat.format(selectedDate.getTime());
                    String formattedComparisonDate = comparisonFormat.format(selectedDate.getTime());

                    if (isFromDate) {
                        edt_fromDateStatic.setText(formattedDisplayDate);
                    } else {
                        // Kiểm tra nếu "Đến ngày" nhỏ hơn "Từ ngày"
                        if (isDateRangeValid(edt_fromDateStatic.getText().toString(), formattedDisplayDate)) {
                            edt_toDateStatic.setText(formattedDisplayDate);
                        } else {
                            Toast.makeText(requireActivity(), "Đến ngày phải sau Từ ngày", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                year, month, dayOfMonth
        ).show();
    }

    // Kiểm tra ngày hợp lệ
    private boolean isDateRangeValid(String fromDate, String toDate) {
        try {
            Date from = displayFormat.parse(fromDate);
            Date to = displayFormat.parse(toDate);

            return to != null && from != null && !to.before(from); // "Đến ngày" không được trước "Từ ngày"
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xử lý thống kê
    private void handleStatistics() {
        String fromDateDisplay = edt_fromDateStatic.getText().toString();
        String toDateDisplay = edt_toDateStatic.getText().toString();

        try {
            // Chuyển đổi sang định dạng kiểm tra
            String fromDate = comparisonFormat.format(displayFormat.parse(fromDateDisplay));
            String toDate = comparisonFormat.format(displayFormat.parse(toDateDisplay));

            // Lấy dữ liệu từ database
            orderDAO = new OrderDAO(requireActivity());
            orderList = orderDAO.getRevenueByDateRange(fromDate, toDate);


            // Cập nhật RecyclerView
            if (statisticalAdapter == null) {
                statisticalAdapter = new StaticAdapterAdmin(orderList);
                recyclerView_Revenue.setAdapter(statisticalAdapter);

            } else {
                statisticalAdapter.setData(orderList);
                statisticalAdapter.notifyDataSetChanged();
            }

            // Tính tổng doanh thu
            int totalRevenue = 0;
            for (Order order : orderList) {
                totalRevenue += order.getTotal_amount();
            }
            tv_DoanhThu.setText(String.format(Locale.getDefault(), "%,d VNĐ", totalRevenue));

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(requireActivity(), "Lỗi xử lý ngày!", Toast.LENGTH_SHORT).show();
        }
    }
}