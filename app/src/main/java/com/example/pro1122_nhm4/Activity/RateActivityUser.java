package com.example.pro1122_nhm4.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Telephony;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pro1122_nhm4.Adapter.RecylerViewRateActivityAdapter;
import com.example.pro1122_nhm4.DAO.DishDAO;
import com.example.pro1122_nhm4.DAO.OrderItemDAO;
import com.example.pro1122_nhm4.DAO.RateDishDAO;
import com.example.pro1122_nhm4.DAO.RateOrderDAO;
import com.example.pro1122_nhm4.Model.OrderItem;
import com.example.pro1122_nhm4.Model.RateDish;
import com.example.pro1122_nhm4.Model.RateOrder;
import com.example.pro1122_nhm4.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RateActivityUser extends AppCompatActivity {
    private RatingBar ratingBar;
    private int ratingValue;
    private EditText edt_Rate,edt_RateChatLuong;
    private RecyclerView recyclerView_Rate;
    private Button btn_Rate;
    private RateDishDAO rateDishDAO;
    private List<RateDish> rateDishList;
    private OrderItemDAO orderItemDAO;
    private List<OrderItem> orderItemList;
    private RateOrder rateOrder;
    private RateOrderDAO rateOrderDAO;
    private RecylerViewRateActivityAdapter recylerViewRateActivityAdapter;
    private DishDAO dishDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rate_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edt_Rate = findViewById(R.id.edt_Rate);
        recyclerView_Rate = findViewById(R.id.recyclerView_Rate);
        btn_Rate = findViewById(R.id.btn_Rate);
        ratingBar = findViewById(R.id.library_tinted_wide_ratingbar);
        ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            ratingValue = (int) rating;
        });

        // Khởi tạo DAO và dữ liệu
        rateDishDAO = new RateDishDAO(this);
        orderItemDAO = new OrderItemDAO(this);
        orderItemDAO.open();
        Integer orderid = getIntent().getIntExtra("order_id", 0);
        Log.d("OrderItemList", "OrderItemList size: " + orderid);
        orderItemList = orderItemDAO.getAllOrderItemsByOrderId(getIntent().getIntExtra("order_id", 0));// Lấy danh sách món ăn của đơn hàng
        Log.d("OrderItemList", "OrderItemList size: " + orderItemList.size());
        dishDAO = new DishDAO(this);
        dishDAO.open();
        rateOrderDAO = new RateOrderDAO(this);
        rateOrder = new RateOrder();
        rateOrderDAO.open();
        Calendar calendar = Calendar.getInstance();
        Date ratingDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());


        // Cài đặt RecyclerView
        recylerViewRateActivityAdapter = new RecylerViewRateActivityAdapter(this,dishDAO);
        recylerViewRateActivityAdapter.setData(orderItemList,orderItemDAO);
        recyclerView_Rate.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_Rate.setAdapter(recylerViewRateActivityAdapter);

        // Lấy user_id từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        int user_id = sharedPreferences.getInt("userId", 0);

        // Xử lý sự kiện khi nhấn nút Đánh giá
        btn_Rate.setOnClickListener(v -> {
            // Duyệt qua tất cả các item trong RecyclerView
            float rating = 0;
            for (int i = 0; i < recyclerView_Rate.getChildCount(); i++) {
                // Lấy view của item hiện tại
                View itemView = recyclerView_Rate.getChildAt(i);

                // Tìm các view bên trong item
                RatingBar ratingBar = itemView.findViewById(R.id.library_tinted_wide_ratingbar_ratingDish);
                EditText edtComment = itemView.findViewById(R.id.edt_ratingDish);

                // Lấy giá trị từ RatingBar và EditText
                rating = ratingBar.getRating();
                String comment = edtComment.getText().toString().trim();

                // Kiểm tra rating > 0 và comment không rỗng
                if (rating > 0) {
                    // Tạo đối tượng RateDish và lưu vào cơ sở dữ liệu
                    RateDish rateDish = new RateDish();
                    rateDish.setUser_id(user_id); // ID người dùng
                    rateDish.setDish_id(orderItemList.get(i).getDish_id()); // ID món ăn
                    rateDish.setRating((int) rating); // Chuyển rating sang số nguyên
                    rateDish.setComment(comment);
                    rateDish.setDate_rate(dateFormat.format(ratingDate));

                    rateDishDAO.insert(rateDish); // Lưu dữ liệu vào bảng RateDish
                } else {
                    Toast.makeText(this, "Món ăn tại vị trí " + i + " chưa được đánh giá!", Toast.LENGTH_SHORT).show();
                }
            }
            rateOrder.setOrder_id(getIntent().getIntExtra("order_id", 0));
            rateOrder.setUser_id(user_id);
            if (ratingValue == 0) {
                ratingValue = 5;
            }
            rateOrder.setRating(ratingValue);
            if(edt_Rate.getText().toString().equals("")&& ratingValue == 5){
                edt_Rate.setText("Rất Tốt");
            } else if (edt_Rate.getText().toString().equals("") && ratingValue == 4) {
                edt_Rate.setText("Tốt");
            } else if (edt_Rate.getText().toString().equals("") && ratingValue == 3) {
                edt_Rate.setText("Trung Bình");
            } else if (edt_Rate.getText().toString().equals("") && ratingValue == 2) {
                edt_Rate.setText("Tệ");
            } else if (edt_Rate.getText().toString().equals("") && ratingValue == 1) {
                edt_Rate.setText("Rất Tệ");
            }
            rateOrder.setComment(edt_Rate.getText().toString());
            rateOrder.setDate_rate(dateFormat.format(ratingDate));
            // Gọi insert chỉ một lần
            long result = rateOrderDAO.insert(rateOrder);
            if (result > 0) {
                Toast.makeText(this, "Đánh giá đã được lưu!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Đánh giá không được lưu!", Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(this, "Đánh giá đã được lưu!", Toast.LENGTH_SHORT).show();

            // Đóng màn hình
            Intent intent = new Intent(RateActivityUser.this, MainActivity2.class);
            startActivity(intent);
            finish();
        });
    }
}