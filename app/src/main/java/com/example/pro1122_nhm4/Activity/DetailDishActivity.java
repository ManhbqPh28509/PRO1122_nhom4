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

import com.bumptech.glide.Glide;
import com.example.pro1122_nhm4.Adapter.DishDetailAdapter;
import com.example.pro1122_nhm4.DAO.DishDAO;
import com.example.pro1122_nhm4.DAO.RateDishDAO;
import com.example.pro1122_nhm4.Model.Dish;
import com.example.pro1122_nhm4.R;

import java.text.DecimalFormat;

public class DetailDishActivity extends AppCompatActivity {
    private Dish dish;
    private DishDAO dishDAO;
    private ImageView imgDish,imgBack;
    private TextView tvNameDish,tvPriceDish, tvDescriptionDish,tv_count_comment,AVG_rating;
    private RecyclerView recyclerView;
    private DecimalFormat formatter = new DecimalFormat("#,###,###,###");
    private RateDishDAO rateDishDAO;
    private DishDetailAdapter dishDetailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_dish);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Integer id = getIntent().getIntExtra("dish_id", 0);
        Log.d("id", String.valueOf(id));
        dishDAO = new DishDAO(this);
        dishDAO.open();
        dish = dishDAO.getDishByID(id);
        imgDish = findViewById(R.id.img_infor_food_img);
        tvNameDish = findViewById(R.id.tv_infor_food_name);
        tvPriceDish = findViewById(R.id.tv_infor_food_price);
        tvDescriptionDish = findViewById(R.id.tv_infor_food_description);
        recyclerView = findViewById(R.id.recylerView_rate);
        tv_count_comment = findViewById(R.id.tv_count_comment);
        AVG_rating = findViewById(R.id.AVG_rating);
        imgBack = findViewById(R.id.img_backDishDetail);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        rateDishDAO = new RateDishDAO(this);
        rateDishDAO.open();
        dishDetailAdapter = new DishDetailAdapter(this);
        dishDetailAdapter.setData(rateDishDAO.getRateDishByDishID(id),rateDishDAO);
        recyclerView.setAdapter(dishDetailAdapter);
        dishDetailAdapter.notifyDataSetChanged();
        Glide.with(this).load(dish.getImg()).into(imgDish);
        tvNameDish.setText(dish.getName());
        tvPriceDish.setText(formatter.format(dish.getPrice())+" VND");
        tvDescriptionDish.setText(dish.getDescription());
        tv_count_comment.setText(String.valueOf("("+rateDishDAO.getRateDishByDishID(id).size())+")");
        AVG_rating.setText(String.valueOf(dishDetailAdapter.getAverageRating())+"/5");
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }
}