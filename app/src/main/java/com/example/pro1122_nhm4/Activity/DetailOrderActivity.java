package com.example.pro1122_nhm4.Activity;

import android.annotation.SuppressLint;
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
import com.example.pro1122_nhm4.DAO.OrderDAO;
import com.example.pro1122_nhm4.DAO.OrderItemDAO;
import com.example.pro1122_nhm4.DAO.UserDAO;
import com.example.pro1122_nhm4.Model.Order;
import com.example.pro1122_nhm4.Model.OrderItem;
import com.example.pro1122_nhm4.Model.User;
import com.example.pro1122_nhm4.R;

import java.text.DecimalFormat;
import java.util.List;

public class DetailOrderActivity extends AppCompatActivity {
    private TextView tv_OrderDetailStatus,tv_OrderDetailAddress,tv_OrderDetailUserNameAndNumbers,tv_qualityDishOrderDetail,tv_priceDishOrderDetail,tv_totalPriceDishOrderDetail;
    private ImageView img_backOrderDetail,img_Fravourite,img_Fravourite2;
    private RecyclerView recyclerView;
    private DecimalFormat formatter = new DecimalFormat("#,###,###,###");
    private OrderItemDAO orderItemDAO;
    private OrderDetailAdapter orderDetailAdapter;
    private OrderDAO orderDAO;
    private Order order;
    private UserDAO userDAO;
    private User user;

    @SuppressLint("MissingInflatedId")
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
        recyclerView.setLayoutManager(layoutManager);
        orderItemDAO = new OrderItemDAO(this);
        orderItemDAO.open();
        orderDAO = new OrderDAO(this);
        orderDAO.open();
        userDAO = new UserDAO(this);
        userDAO.open();
        order = orderDAO.getOrderById(getIntent().getIntExtra("order_id", 0));
        user = userDAO.getUserById(order.getUser_id());
        tv_OrderDetailStatus.setText(order.getStatus());
        tv_OrderDetailAddress.setText(user.getDiachi());
        tv_OrderDetailUserNameAndNumbers.setText(user.getHoten() + " - " + user.getSdt());
        int totalQuantity = 0;
        for (OrderItem orderItem : orderItemDAO.getAllOrderItemsByOrderId(order.getOrder_id())) {
            totalQuantity += orderItem.getQuantity();
        }
        tv_qualityDishOrderDetail.setText("Tổng cộng("+String.valueOf(totalQuantity)+" món)");
        tv_priceDishOrderDetail.setText(formatter.format(order.getTotal_amount()-20000));
        tv_totalPriceDishOrderDetail.setText(formatter.format(order.getTotal_amount()));

        orderDetailAdapter = new OrderDetailAdapter(this);
        Integer id = getIntent().getIntExtra("order_id", 0);
        Log.d("DetailOrderActivity", "Received order_id: " + id);
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