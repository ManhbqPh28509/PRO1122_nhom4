package com.example.pro1122_nhm4.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pro1122_nhm4.Activity.DetailDishActivity;
import com.example.pro1122_nhm4.DAO.CartDAO;
import com.example.pro1122_nhm4.DAO.DishDAO;
import com.example.pro1122_nhm4.Fragment.CartFragmentUser;
import com.example.pro1122_nhm4.Model.Cart;
import com.example.pro1122_nhm4.Model.Category;
import com.example.pro1122_nhm4.Model.Dish;
import com.example.pro1122_nhm4.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.ViewHolder> {
    private List<Dish> dishList;
    private Context context;
    private DishDAO dishDAO;
    private CartDAO cartDAO;
    private CartAdapter cartAdapter;
    private List<Cart> cartList;
    public DishAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<Dish> dishList,DishDAO dishDAO) {
        this.dishList = dishList;
        this.dishDAO = dishDAO;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DishAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dish, parent, false);
        return new DishAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Dish dish = dishList.get(position);
        holder.name.setText(dishList.get(position).getName());
        holder.description.setText(dishList.get(position).getDescription());
        DecimalFormat formatter = new DecimalFormat("#,###,###,###");
        holder.price.setText(formatter.format(dishList.get(position).getPrice())+"đ");
        Glide.with(context).load(dishList.get(position).getImg()).into(holder.img);
        if (dish.getAvailability().equals("Còn hàng")) {
            holder.button.setVisibility(View.VISIBLE);
            holder.text_het_hang.setVisibility(View.GONE);
        } else if(dish.getAvailability().equals("Hết hàng")){
            holder.button.setVisibility(View.GONE);
            holder.text_het_hang.setVisibility(View.VISIBLE);
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailDishActivity.class);
                intent.putExtra("dish_id", dish.getDish_id());
                context.startActivity(intent);
            }
        });
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCart(dish,position);
            }
        });
    }

    private void AddCart(Dish dish, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_add_cart_home, null);

        // Khởi tạo dialog
        Dialog dialog = new Dialog(context, androidx.appcompat.R.style.Base_V21_Theme_AppCompat_Dialog);
        dialog.setContentView(view);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(params);
        Button button = view.findViewById(R.id.btn_dialog_add_cart);
        Button button1 = view.findViewById(R.id.btn_dialog_cancel);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart cart = new Cart();
                CartDAO cartDAO = new CartDAO(context);
                cartDAO.open();
                cartAdapter = new CartAdapter(context,dishDAO, CartFragmentUser.newInstance());
                cart.setDish_id(dish.getDish_id());
                cart.setQuantity(1);
                cart.setSum(dish.getPrice());
                SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                int userId = sharedPreferences.getInt("userId", -1);
                cart.setUser_id(userId);
                if (cartList == null) {
                    cartList = new ArrayList<>();
                    Log.d("AddCart", "cartList is initialized");
                }
                if (!cartDAO.isDishExists(cart.getDish_id(), cart.getUser_id())) {
                    if (cartDAO.insertNew(cart) > 0) {
                        Log.d("AddCart", "Item added successfully: " + cart.getDish_id());
                        Toast.makeText(context, "Đã Thêm Vào Giỏ Hàng", Toast.LENGTH_SHORT).show();
                        cartList.add(cart);
                        cartAdapter.setData(cartList, cartDAO);
                        cartAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(context, "Thêm Vào Giỏ Hàng Thất Bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Món ăn đã được chọn", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
                cartDAO.close();
                dialog.dismiss();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return dishList.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name,price,description,text_het_hang;
        private ImageView img;
        private FloatingActionButton button;
        private CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.text_dish_name);
            price = itemView.findViewById(R.id.text_dish_price);
            img = itemView.findViewById(R.id.image_dish);
            button = itemView.findViewById(R.id.button_add_to_cart);
            description = itemView.findViewById(R.id.text_dish_description);
            text_het_hang = itemView.findViewById(R.id.text_het_hang);
            cardView = itemView.findViewById(R.id.cardViewItemDish);
        }
    }
}
