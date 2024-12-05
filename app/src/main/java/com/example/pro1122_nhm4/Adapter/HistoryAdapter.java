package com.example.pro1122_nhm4.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pro1122_nhm4.Activity.DetailOrderActivity;
import com.example.pro1122_nhm4.Activity.RateActivityUser;
import com.example.pro1122_nhm4.DAO.DishDAO;
import com.example.pro1122_nhm4.DAO.OrderDAO;
import com.example.pro1122_nhm4.DAO.OrderItemDAO;
import com.example.pro1122_nhm4.DAO.RateOrderDAO;
import com.example.pro1122_nhm4.Model.Order;
import com.example.pro1122_nhm4.Model.OrderItem;
import com.example.pro1122_nhm4.R;

import java.text.DecimalFormat;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private DishDAO dishDAO;
    private Context context;
    private Order order;
    private OrderDAO orderDAO;
    private OrderItem orderItem;
    private OrderItemDAO orderItemDAO;
    private List<OrderItem> orderItemList;
    private List<Order> orderList;
    private RateOrderDAO rateOrderDAO;
    private DecimalFormat formatter = new DecimalFormat("#,###,###,###");
    public HistoryAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<Order> orderList) {
        this.orderList = orderList;
        for (Order order : orderList) {
            Log.d("HistoryAdapter", "setData: order_id = " + order.getOrder_id());
        }
    }
    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_history, parent, false);
        return new HistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        order = orderList.get(position);
        holder.tv_ordertimeHistory.setText(String.valueOf(order.getOrder_date()));
        holder.tv_totalPriceOrderHistory.setText(formatter.format(order.getTotal_amount()) + "đ");
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.MINUTE, 30);
//        Date shipDate = calendar.getTime();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM", Locale.getDefault());
//        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
//        holder.tv_ShipTimeOrder.setText("  Dự kiến giao hàng lúc "+timeFormat.format(shipDate)+" Hôm nay");
        LinearLayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        holder.recyclerView_DishesOrderHistory.setLayoutManager(layoutManager);
        dishDAO = new DishDAO(context);
        dishDAO.open();
        orderItemDAO = new OrderItemDAO(context);
        orderItemDAO.open();
        orderItemList = orderItemDAO.getAllOrderItemsByOrderId(order.getOrder_id());
        CommingOrderItemAdapter adapter = new CommingOrderItemAdapter(context,dishDAO);
        adapter.setData(orderItemList,orderItemDAO);
        holder.recyclerView_DishesOrderHistory.setAdapter(adapter);
        holder.tv_quantityOrderHistory.setText(adapter.getTotalQuantity()+" món");
        holder.tv_totalPriceOrderHistory.setText(formatter.format(adapter.getTotalPrice()+20000) + "đ");
        dishDAO.close();
        orderItemDAO.close();
        rateOrderDAO = new RateOrderDAO(context);
        rateOrderDAO.open();
        if (rateOrderDAO.isOrderRated(order.getOrder_id())) {
            holder.btn_DanhGia.setVisibility(View.GONE);
            holder.tv_DaDanhGia.setVisibility(View.VISIBLE);
        }
        holder.btn_DanhGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition(); // Lấy vị trí chính xác
                Order clickedOrder = orderList.get(adapterPosition); // Lấy Order từ vị trí
                Log.d("HistoryAdapter", "onClick: position = " + adapterPosition + ", order_id = " + clickedOrder.getOrder_id());
                Intent intent = new Intent(context, RateActivityUser.class);
                intent.putExtra("order_id", clickedOrder.getOrder_id());
                context.startActivity(intent);
                }
        });
        holder.linearLayoutItemOrderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition(); // Lấy vị trí chính xác
                Order clickedOrder = orderList.get(adapterPosition); // Lấy Order từ vị trí
                Log.d("HistoryAdapter", "onClick: position = " + adapterPosition + ", order_id = " + clickedOrder.getOrder_id());
                Intent intent = new Intent(context, DetailOrderActivity.class);
                intent.putExtra("order_id", clickedOrder.getOrder_id());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_ordertimeHistory, tv_totalPriceOrderHistory, tv_quantityOrderHistory,tv_DaDanhGia;
        private Button btn_DanhGia;
        private RecyclerView recyclerView_DishesOrderHistory;
        private LinearLayout linearLayoutItemOrderHistory;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_ordertimeHistory = itemView.findViewById(R.id.tv_ordertimeHistory);
            tv_totalPriceOrderHistory = itemView.findViewById(R.id.tv_totalPriceOrderHistory);
            tv_quantityOrderHistory = itemView.findViewById(R.id.tv_quantityOrderHistory);
            btn_DanhGia = itemView.findViewById(R.id.btn_DanhGia);
            recyclerView_DishesOrderHistory = itemView.findViewById(R.id.recyclerView_DishesOrderHistory);
            tv_DaDanhGia = itemView.findViewById(R.id.tv_DaDanhGia);
            linearLayoutItemOrderHistory = itemView.findViewById(R.id.linearLayoutItemOrderHistory);

        }
    }
}
