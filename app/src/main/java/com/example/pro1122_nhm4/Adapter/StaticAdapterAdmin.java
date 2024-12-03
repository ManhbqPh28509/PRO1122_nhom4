package com.example.pro1122_nhm4.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pro1122_nhm4.DAO.OrderDAO;
import com.example.pro1122_nhm4.Model.Order;
import com.example.pro1122_nhm4.R;

import java.util.List;
import java.util.Locale;

public class StaticAdapterAdmin extends RecyclerView.Adapter<StaticAdapterAdmin.ViewHolder> {
    private Order order;
    private OrderDAO orderDAO;
    private List<Order> orderList;
    public StaticAdapterAdmin(List<Order> orderList) {
        this.orderList = orderList;
    }
    public void setData(List<Order> orderList) {
        this.orderList = orderList;
        notifyDataSetChanged();
    }
    public long getItemId(int position) {
        return super.getItemId(position);  // Loại bỏ việc trả về ID thủ công
    }
    @NonNull
    @Override
    public StaticAdapterAdmin.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_static, parent, false);
        return new StaticAdapterAdmin.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StaticAdapterAdmin.ViewHolder holder, int position) {
        order = orderList.get(position);
        holder.tv_DateStatic.setText(String.valueOf(order.getOrder_date()));
        holder.tv_TotalPriceStatic.setText(String.format(Locale.getDefault(), "%,d VNĐ",order.getTotal_amount()));

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_DateStatic, tv_TotalPriceStatic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_DateStatic = itemView.findViewById(R.id.tv_DateStatic);
            tv_TotalPriceStatic = itemView.findViewById(R.id.tv_TotalPriceStatic);
        }
    }
}
