package com.example.pro1122_nhm4.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pro1122_nhm4.Adapter.CartAdapter;
import com.example.pro1122_nhm4.Adapter.ConfirmOrderAdapter;
import com.example.pro1122_nhm4.DAO.CartDAO;
import com.example.pro1122_nhm4.DAO.DishDAO;
import com.example.pro1122_nhm4.DAO.OrderDAO;
import com.example.pro1122_nhm4.DAO.OrderItemDAO;
import com.example.pro1122_nhm4.Fragment.CartFragmentUser;
import com.example.pro1122_nhm4.Model.Cart;
import com.example.pro1122_nhm4.Model.Order;
import com.example.pro1122_nhm4.Model.OrderItem;
import com.example.pro1122_nhm4.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ConfirmationOrderActivity extends AppCompatActivity {
    private TextView tv_TenUser, tv_SDTUser,tv_DiachiUser,tv_TimeOrder,tv_qualityDish,tv_priceDish,tv_totalPriceDish;
    private Button btn_confirmOrder;
    private RecyclerView recyclerView_DishConfirmOrder;
    private CartAdapter cartAdapter;
    private List<Cart> cartList;
    private CartDAO cartDAO;
    private CartFragmentUser cartFragmentUser;
    private DishDAO dishDAO;
    private ConfirmOrderAdapter confirmOrderAdapter;
    private OrderDAO orderDAO;
    private OrderItemDAO orderItemDAO;
    private Order order;
    private OrderItem orderItem;
    private LinearLayout linearLayout_DiaChiUserConfirmOder;
    private ImageView iv_confirm_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_confirmation_order3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        }
        tv_TenUser = findViewById(R.id.tv_TenUserComfrimOder);
        tv_SDTUser = findViewById(R.id.tv_SDTUserConfirmOrder);
        tv_DiachiUser = findViewById(R.id.tv_DiaChiUserConfirmOder);
        tv_TimeOrder = findViewById(R.id.tv_TimeOrder);
        tv_qualityDish = findViewById(R.id.tv_qualityDish);
        tv_priceDish = findViewById(R.id.tv_priceDish);
        tv_totalPriceDish = findViewById(R.id.tv_totalPriceDish);
        btn_confirmOrder = findViewById(R.id.btn_confirmOrder);
        recyclerView_DishConfirmOrder = findViewById(R.id.recyclerView_DishConfirmOrder);
        SharedPreferences sharedPreferences = this.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String ten = sharedPreferences.getString("hoten", "");
        String sdt = sharedPreferences.getString("sdt", "");
        String diachi = sharedPreferences.getString("diachi", "");
        Integer user_id = sharedPreferences.getInt("userId", 0);
        tv_TenUser.setText(ten);
        tv_DiachiUser.setText(diachi);
        tv_SDTUser.setText(sdt);
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        tv_TimeOrder.setText("Đặt hàng lúc: " + timeFormat.format(currentDate) + " - " + dateFormat.format(currentDate));
        cartDAO = new CartDAO(this);
        dishDAO = new DishDAO(this);
        cartFragmentUser = new CartFragmentUser();
        cartDAO.open();
        cartList = cartDAO.getCartByUserID(user_id);
        cartAdapter = new CartAdapter(cartFragmentUser , this);
        cartAdapter.setData(cartList, cartDAO);
        confirmOrderAdapter = new ConfirmOrderAdapter(this,dishDAO);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView_DishConfirmOrder.setLayoutManager(layoutManager);
        recyclerView_DishConfirmOrder.setAdapter(confirmOrderAdapter);
        confirmOrderAdapter.setData(cartList,cartDAO);
        confirmOrderAdapter .notifyDataSetChanged();
        Integer totalQuantity = cartAdapter.getTotalQuantity();
        tv_qualityDish.setText("Tổng cộng ("+totalQuantity+" món)");
        Integer totalPrice = (int) cartAdapter.getTotalPrice();
        DecimalFormat formatter = new DecimalFormat("#,###,###,###");
        tv_priceDish.setText(formatter.format(totalPrice) + "đ");
        double totalPriceWithShip = totalPrice + 20000;
        tv_totalPriceDish.setText(formatter.format(totalPriceWithShip) + "đ");
        orderDAO = new OrderDAO(this);
        orderItemDAO = new OrderItemDAO(this);
        linearLayout_DiaChiUserConfirmOder = findViewById(R.id.linearLayout_DiaChiUserConfirmOder);
        linearLayout_DiaChiUserConfirmOder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmationOrderActivity.this, AddressEditActivity.class);
                startActivity(intent);
            }
        });
        iv_confirm_back = findViewById(R.id.img_backComfirmOrder);
        iv_confirm_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long orderId = 0;
                DateTimeFormatter formatter = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                }
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    orderId = orderDAO.createOrder(user_id, totalPrice+20000, "Chưa xác nhận", LocalDate.parse(dateFormat.format(currentDate),formatter));
                }
                for (Cart cart : cartList) {
                    OrderItem orderItem = new OrderItem();
                    if (orderId <= Integer.MAX_VALUE) {
                        orderItem.setOrder_id((int) orderId);
                    } else {
                        Log.d("ConfirmationOrderActivity", "Order ID is too large");
                    }
                    orderItem.setDish_id(cart.getDish_id());
                    orderItem.setQuantity(cart.getQuantity());
                    orderItem.setPrice(cart.getSum()/cart.getQuantity());
                    orderItemDAO.insert(orderItem); // Thêm OrderItem vào database
                }
                cartDAO.deleteCartByUserID(user_id);
                cartAdapter.notifyDataSetChanged();
                Intent intent = new Intent(ConfirmationOrderActivity.this, ConfirmOrderActivity2.class);
                startActivity(intent);
            }
        });



    }

}