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
import com.example.pro1122_nhm4.DAO.DishDAO;
import com.example.pro1122_nhm4.DAO.OrderItemDAO;
import com.example.pro1122_nhm4.Model.Dish;
import com.example.pro1122_nhm4.Model.OrderItem;
import com.example.pro1122_nhm4.R;

import java.util.List;

public class CommingOrderItemAdapter extends RecyclerView.Adapter<CommingOrderItemAdapter.ViewHolder> {
    private List<OrderItem> orderItemList;
    private Context context;
    private OrderItem orderItem;
    private OrderItemDAO orderItemDAO;
    private DishDAO dishDAO;
    public CommingOrderItemAdapter(Context context, DishDAO dishDAO) {
        this.context = context;
        this.dishDAO = dishDAO;
        this.orderItemDAO = new OrderItemDAO(context);
        this.dishDAO.open();
    }
    public void setData(List<OrderItem> orderItemList, OrderItemDAO orderItemDAO) {
        this.orderItemList = orderItemList;
        this.orderItemDAO = orderItemDAO;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CommingOrderItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_item_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommingOrderItemAdapter.ViewHolder holder, int position) {
        dishDAO.open();
        Dish dish = dishDAO.getDishByID(orderItemList.get(position).getDish_id());
        Glide.with(context).load(dish.getImg()).into(holder.imageView);
        holder.tv_nameDish.setText(dish.getName());

    }
    public int getTotalQuantity() {
        int totalQuantity = 0;
        for (OrderItem orderItem : orderItemList) {
            totalQuantity += orderItem.getQuantity();
        }
        return totalQuantity;
    }
    public double getTotalPrice() {
        double totalPrice = 0;
        for (OrderItem orderItem : orderItemList) {
            totalPrice += orderItem.getPrice()* orderItem.getQuantity();
        }
        return totalPrice;
    }

    @Override
    public int getItemCount() {
        return orderItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView tv_nameDish;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image_item_order);
            tv_nameDish = itemView.findViewById(R.id.item_name_dish_item_order);
        }
    }
}
