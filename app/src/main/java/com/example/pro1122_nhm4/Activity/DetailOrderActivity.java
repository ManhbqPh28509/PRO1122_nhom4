package com.example.pro1122_nhm4.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pro1122_nhm4.Adapter.OrderDetailAdapter;
import com.example.pro1122_nhm4.DAO.OrderItemDAO;
import com.example.pro1122_nhm4.Model.OrderItem;
import com.example.pro1122_nhm4.R;

import java.text.DecimalFormat;
import java.util.List;

public class DetailOrderActivity extends AppCompatActivity {
    private TextView tv_OrderDetailStatus,tv_OrderDetailAddress,tv_OrderDetailUserNameAndNumbers,tv_qualityDishOrderDetail,tv_priceDishOrderDetail,tv_totalPriceDishOrderDetail;
    private ImageView img_backOrderDetail;
    private RecyclerView recyclerView;
    private DecimalFormat formatter = new DecimalFormat("#,###,###,###");
    private OrderItemDAO orderItemDAO;
    private OrderDetailAdapter orderDetailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_order);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tv_OrderDetailStatus = findViewById(R.id.tv_OrderDetailStatus);
        tv_OrderDetailAddress = findViewById(R.id.tv_OrderDetailAddress);
        tv_OrderDetailUserNameAndNumbers = findViewById(R.id.tv_OrderDetailUserNameAndNumbers);
        tv_qualityDishOrderDetail = findViewById(R.id.tv_qualityDishOrderDetail);
        tv_priceDishOrderDetail = findViewById(R.id.tv_priceDishOrderDetail);
        tv_totalPriceDishOrderDetail = findViewById(R.id.tv_totalPriceDishOrderDetail);
        img_backOrderDetail = findViewById(R.id.img_backOrderDetail);
        recyclerView = findViewById(R.id.recycler_view_order_detail);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        orderItemDAO = new OrderItemDAO(this);
        orderItemDAO.open();
        orderDetailAdapter = new OrderDetailAdapter(this);
        Integer id = getIntent().getIntExtra("order_id", 0);
        if (id == 0) {
            Log.e("DetailOrderActivity", "Invalid order_id: " + id);
            return;
        }

        // Fetch OrderItem list from database using orderId
        List<OrderItem> orderItems = orderItemDAO.getAllOrderItemsByOrderId(id);

        // Log the size of the list to check if data is fetched
        Log.d("DetailOrderActivity", "Number of order items: " + orderItems.size());

        // Set data to adapter
        if (orderItems != null && !orderItems.isEmpty()) {
            orderDetailAdapter = new OrderDetailAdapter(this);
            orderDetailAdapter.setData(orderItems, orderItemDAO);
            recyclerView.setAdapter(orderDetailAdapter);
        } else {
            Log.e("DetailOrderActivity", "No order items found for order_id: " + id);
        }

        // Set back button action
        img_backOrderDetail.setOnClickListener(v -> onBackPressed());

    }
}