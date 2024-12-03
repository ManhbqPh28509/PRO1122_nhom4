package com.example.pro1122_nhm4.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pro1122_nhm4.DAO.DishDAO;
import com.example.pro1122_nhm4.DAO.OrderDAO;
import com.example.pro1122_nhm4.DAO.OrderItemDAO;
import com.example.pro1122_nhm4.Model.Order;
import com.example.pro1122_nhm4.Model.OrderItem;
import com.example.pro1122_nhm4.R;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CommingOrderAdapter extends RecyclerView.Adapter<CommingOrderAdapter.ViewHolder> {
    private static final Log log = LogFactory.getLog(CommingOrderAdapter.class);
    private DishDAO dishDAO;
    private Context context;
    private Order order;
    private OrderDAO orderDAO;
    private OrderItem orderItem;
    private OrderItemDAO orderItemDAO;
    private List<OrderItem> orderItemList;
    private List<Order> orderList;
    private DecimalFormat formatter = new DecimalFormat("#,###,###,###");

    public  CommingOrderAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<Order> orderList){
        this.orderList = orderList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommingOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        order = orderList.get(position);
        if(order.getStatus().equals("Đã hoàn thành")){
            return;
        } else if (order.getStatus().equals("Đã giao hàng")) {
            holder.tv_orderStatus.setVisibility(View.GONE);
            holder.btn_DaNhanHang.setVisibility(View.VISIBLE);
        }
        holder.tv_ordertime.setText(String.valueOf(order.getOrder_date()));
        holder.tv_orderStatus.setText(order.getStatus());
        holder.tv_totalPriceOrder.setText(formatter.format(order.getTotal_amount()) + "đ");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 30);
        Date shipDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        holder.tv_ShipTimeOrder.setText("  Dự kiến giao hàng lúc "+timeFormat.format(shipDate)+" Hôm nay");
        LinearLayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        holder.recyclerView_DishesOrder.setLayoutManager(layoutManager);
        dishDAO = new DishDAO(context);
        dishDAO.open();
        orderItemDAO = new OrderItemDAO(context);
        orderItemDAO.open();
        orderItemList = orderItemDAO.getAllOrderItemsByOrderId(order.getOrder_id());
        CommingOrderItemAdapter adapter = new CommingOrderItemAdapter(context,dishDAO);
        adapter.setData(orderItemList,orderItemDAO);
        holder.recyclerView_DishesOrder.setAdapter(adapter);
        holder.tv_quantityOrder.setText(adapter.getTotalQuantity()+" món");
        holder.tv_totalPriceOrder.setText(formatter.format(adapter.getTotalPrice()+20000) + "đ");
        holder.btn_DaNhanHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Order order = orderList.get(adapterPosition);
                    order.setStatus("Đã hoàn thành");
                    orderDAO = new OrderDAO(context);
                    orderDAO.open();
                    long res = orderDAO.update(order);
                    if (res != 0) {
                        android.util.Log.d("OrderList", "Position: " + adapterPosition + ", OrderList size: " + orderList.size());
                        if (adapterPosition >= 0 && adapterPosition < orderList.size()) {
                            orderList.remove(adapterPosition);
                            notifyItemRemoved(adapterPosition);

                            // Thông báo rằng danh sách đã thay đổi để đảm bảo không xảy ra lỗi
                            if (adapterPosition < orderList.size()) {
                                notifyItemRangeChanged(adapterPosition, orderList.size() - adapterPosition);
                            }
                        } else {
                            android.util.Log.d("OrderList", "Invalid position: " + adapterPosition);
                        }
                    }
                }
            }
        });
        dishDAO.close();
        orderItemDAO.close();

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_ordertime, tv_orderStatus, tv_totalPriceOrder, tv_quantityOrder,tv_ShipTimeOrder;
        private RecyclerView recyclerView_DishesOrder;
        private Button btn_DaNhanHang;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_ordertime = itemView.findViewById(R.id.tv_ordertime);
            tv_orderStatus = itemView.findViewById(R.id.tv_orderStatus);
            tv_totalPriceOrder = itemView.findViewById(R.id.tv_totalPriceOrder);
            tv_quantityOrder = itemView.findViewById(R.id.tv_quantityOrder);
            tv_ShipTimeOrder = itemView.findViewById(R.id.tv_ShipTimeOrder);
            recyclerView_DishesOrder = itemView.findViewById(R.id.recyclerView_DishesOrder);
            btn_DaNhanHang = itemView.findViewById(R.id.btn_DaGiaoHang);
        }
    }
}
