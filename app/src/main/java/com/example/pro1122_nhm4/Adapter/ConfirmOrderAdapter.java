package com.example.pro1122_nhm4.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pro1122_nhm4.DAO.CartDAO;
import com.example.pro1122_nhm4.DAO.DishDAO;
import com.example.pro1122_nhm4.Model.Cart;
import com.example.pro1122_nhm4.Model.Dish;
import com.example.pro1122_nhm4.R;

import java.text.DecimalFormat;
import java.util.List;

public class ConfirmOrderAdapter extends RecyclerView.Adapter<ConfirmOrderAdapter.ViewHolder> {
    private List<Cart> cartList;
    private Context context;
    private CartDAO cartDAO;
    public DishDAO dishDAO;
    private DecimalFormat formatter = new DecimalFormat("#,###,###,###");
    public ConfirmOrderAdapter(Context context, DishDAO dishDAO) {
        this.context = context;
        this.dishDAO = dishDAO;
        this.cartDAO = new CartDAO(context);
        this.dishDAO.open();
    }
    public void setData(List<Cart> cartList, CartDAO cartDAO) {
        this.cartList = cartList;
        this.cartDAO = cartDAO;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ConfirmOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_confirm_order, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConfirmOrderAdapter.ViewHolder holder, int position) {
        dishDAO.open();
        Cart cart = cartList.get(position);
        Dish dish = dishDAO.getDishByID(cart.getDish_id());
        if (dish != null) {
            holder.name.setText(dish.getName());
            holder.price.setText(formatter.format(dish.getPrice()) + "đ");
            holder.quantity.setText(String.valueOf(cart.getQuantity()));
            Glide.with(context).load(dish.getImg()).into(holder.img);
        }

    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView name, price, quantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.iv_item_confirm_order);
            name = itemView.findViewById(R.id.tv_item_confirm_order_dish);
            price = itemView.findViewById(R.id.tv_item_confirm_order_price);
            quantity = itemView.findViewById(R.id.tv_item_confirm_order_quantity);

        }
    }
}
