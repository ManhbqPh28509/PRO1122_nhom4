package com.example.pro1122_nhm4.Adapter;

import android.content.Context;
import android.util.Log;
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

import java.text.DecimalFormat;
import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {
    private List<OrderItem> orderItemList;
    private Context context;
    private OrderItem orderItem;
    private OrderItemDAO orderItemDAO;
    private DishDAO dishDAO;
    private DecimalFormat formatter = new DecimalFormat("#,###,###,###");
    public OrderDetailAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<OrderItem> orderItemList, OrderItemDAO orderItemDAO) {
        this.orderItemList = orderItemList;
        this.orderItemDAO = orderItemDAO;
        Log.d("setData", "Size: " + orderItemList.size());
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public OrderDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_detail, parent, false);
        return new OrderDetailAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull OrderDetailAdapter.ViewHolder holder, int position) {
        Log.d("onBindViewHolder", "Binding item at position: " + position);
        orderItem = orderItemList.get(position);
        Log.d("onBindViewHolder", "OrderItem: " + orderItem.getDish_id());
        dishDAO = new DishDAO(context);
        dishDAO.open();
        Log.d("onBindViewHolder", "DishDAO opened");
        Dish dish = dishDAO.getDishByID(orderItem.getDish_id());
        Log.d("DishDAO", "Dish ID: " + orderItem.getDish_id() + ", Dish Name: " + (dish != null ? dish.getName() : "NULL"));
        Glide.with(context).load(dish.getImg()).into(holder.imageView);
        holder.tv_name.setText(dish.getName());
        holder.tv_quantity.setText(String.valueOf(orderItem.getQuantity()) + "x");
        holder.tv_price.setText(formatter.format(orderItem.getPrice()) + "đ");
        dishDAO.close();
        Log.d("onBindViewHolder", "DishDAO closed");

    }

    @Override
    public int getItemCount() {
        int size = orderItemList != null ? orderItemList.size() : 0;
        Log.d("OrderDetailAdapter", "getItemCount: " + size);
        return size;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView tv_name, tv_price, tv_quantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_DishOrderDetail);
            tv_name = itemView.findViewById(R.id.tv_nameDishOrderDetail);
            tv_price = itemView.findViewById(R.id.tv_priceDishOrderDetail);
            tv_quantity = itemView.findViewById(R.id.tv_quantityOrderDetail);
        }
    }
}
