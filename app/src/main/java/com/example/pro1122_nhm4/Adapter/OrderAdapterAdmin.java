package com.example.pro1122_nhm4.Adapter;

import android.annotation.SuppressLint;
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
import com.example.pro1122_nhm4.DAO.DishDAO;
import com.example.pro1122_nhm4.DAO.OrderDAO;
import com.example.pro1122_nhm4.DAO.OrderItemDAO;
import com.example.pro1122_nhm4.Model.Order;
import com.example.pro1122_nhm4.Model.OrderItem;
import com.example.pro1122_nhm4.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderAdapterAdmin extends RecyclerView.Adapter<OrderAdapterAdmin.ViewHolder> {
    private DishDAO dishDAO;
    private Context context;
    private Order order;
    private OrderDAO orderDAO;
    private OrderItem orderItem;
    private OrderItemDAO orderItemDAO;
    private List<OrderItem> orderItemList;
    private List<Order> orderList;
    private DecimalFormat formatter = new DecimalFormat("#,###,###,###");
    public OrderAdapterAdmin(Context context) {
        this.context = context;

    }
    public void setData(List<Order> orderList) {
        this.orderList = orderList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public OrderAdapterAdmin.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_admin, parent, false);
        return new OrderAdapterAdmin.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapterAdmin.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        order = orderList.get(position);
        if(order.getStatus().equals("Chưa xác nhận")){
            holder.btn_XacNhapDon.setVisibility(View.VISIBLE);
            holder.btn_GiaoHang.setVisibility(View.GONE);
            holder.btn_DaGiaoHang.setVisibility(View.GONE);
            holder.tv_orderStatusAdmin.setVisibility(View.GONE);
        } else if (order.getStatus().equals("Đã xác nhận")){
            holder.btn_XacNhapDon.setVisibility(View.GONE);
            holder.btn_GiaoHang.setVisibility(View.VISIBLE);
            holder.btn_DaGiaoHang.setVisibility(View.GONE);
            holder.tv_orderStatusAdmin.setVisibility(View.GONE);
        } else if (order.getStatus().equals("Đang giao hàng")){
            holder.btn_XacNhapDon.setVisibility(View.GONE);
            holder.btn_GiaoHang.setVisibility(View.GONE);
            holder.btn_DaGiaoHang.setVisibility(View.VISIBLE);
            holder.tv_orderStatusAdmin.setVisibility(View.GONE);
        } else if (order.getStatus().equals("Đã giao hàng")){
            holder.btn_XacNhapDon.setVisibility(View.GONE);
            holder.btn_GiaoHang.setVisibility(View.GONE);
            holder.btn_DaGiaoHang.setVisibility(View.GONE);
            holder.tv_orderStatusAdmin.setVisibility(View.VISIBLE);
        } else if (order.getStatus().equals("Đã hoàn thành")) {
            holder.btn_XacNhapDon.setVisibility(View.GONE);
            holder.btn_GiaoHang.setVisibility(View.GONE);
            holder.btn_DaGiaoHang.setVisibility(View.GONE);
            holder.tv_orderStatusAdmin.setVisibility(View.VISIBLE);
        } else if(order.getStatus().equals("Đã hủy")){
            holder.btn_XacNhapDon.setVisibility(View.GONE);
            holder.btn_GiaoHang.setVisibility(View.GONE);
            holder.btn_DaGiaoHang.setVisibility(View.GONE);
            holder.tv_orderStatusAdmin.setVisibility(View.VISIBLE);
        }
        holder.tv_ordertimeAdmin.setText(String.valueOf(order.getOrder_date()));
        holder.tv_orderStatusAdmin.setText(order.getStatus());
        holder.tv_totalPriceOrderAdmin.setText(formatter.format(order.getTotal_amount()) + "đ");
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.MINUTE, 30);
//        Date shipDate = calendar.getTime();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM", Locale.getDefault());
//        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
//        holder.tv_ShipTimeOrder.setText("  Dự kiến giao hàng lúc "+timeFormat.format(shipDate)+" Hôm nay");
        LinearLayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        holder.recyclerView_DishesOrderAdmin.setLayoutManager(layoutManager);
        dishDAO = new DishDAO(context);
        dishDAO.open();
        orderItemDAO = new OrderItemDAO(context);
        orderItemDAO.open();
        orderItemList = orderItemDAO.getAllOrderItemsByOrderId(order.getOrder_id());
        CommingOrderItemAdapter adapter = new CommingOrderItemAdapter(context,dishDAO);
        adapter.setData(orderItemList,orderItemDAO);
        holder.recyclerView_DishesOrderAdmin.setAdapter(adapter);
        holder.tv_quantityOrderAdmin.setText(adapter.getTotalQuantity()+" món");
        holder.tv_totalPriceOrderAdmin.setText(formatter.format(adapter.getTotalPrice()+20000) + "đ");
        dishDAO.close();
        orderItemDAO.close();
        holder.linearLayoutItemOrderAdmin.setOnClickListener(new View.OnClickListener() {
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
        holder.btn_XacNhapDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Order order = orderList.get(adapterPosition); // Lấy đúng phần tử từ danh sách
                    order.setStatus("Đã xác nhận");

                    // Cập nhật vào cơ sở dữ liệu
                    orderDAO = new OrderDAO(context);
                    orderDAO.open();
                    long res = orderDAO.update(order);
                    orderDAO.close();

                    if (res != 0) {
                        orderList.set(adapterPosition, order); // Cập nhật danh sách
                        notifyItemChanged(adapterPosition);   // Chỉ cập nhật phần tử thay đổi
                    }
                }
            }
        });

        holder.btn_GiaoHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Order order = orderList.get(adapterPosition);

                    order.setStatus("Đang giao hàng");
                    orderDAO = new OrderDAO(context);
                    orderDAO.open();
                    long res = orderDAO.update(order);
                    if (res != 0) {
                        orderList.set(adapterPosition,order);
                        notifyItemChanged(adapterPosition);
                    }
                    orderDAO.close();
                }
            }
        });

        holder.btn_DaGiaoHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Order order = orderList.get(adapterPosition);
                    order.setStatus("Đã giao hàng");
                    orderDAO = new OrderDAO(context);
                    orderDAO.open();
                    long res = orderDAO.update(order);
                    if (res != 0) {
                        orderList.set(adapterPosition,order);
                        notifyItemChanged(adapterPosition);
                    }
                    orderDAO.close();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_ordertimeAdmin, tv_totalPriceOrderAdmin, tv_quantityOrderAdmin, tv_orderStatusAdmin;
        private RecyclerView recyclerView_DishesOrderAdmin;
        private Button btn_XacNhapDon, btn_GiaoHang, btn_DaGiaoHang;
        private LinearLayout linearLayoutItemOrderAdmin;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_ordertimeAdmin = itemView.findViewById(R.id.tv_ordertimeAdmin);
            tv_totalPriceOrderAdmin = itemView.findViewById(R.id.tv_totalPriceOrderAdmin);
            tv_quantityOrderAdmin = itemView.findViewById(R.id.tv_quantityOrderAdmin);
            tv_orderStatusAdmin = itemView.findViewById(R.id.tv_orderStatusAdmin);
            recyclerView_DishesOrderAdmin = itemView.findViewById(R.id.recyclerView_DishesOrderAdmin);
            btn_XacNhapDon = itemView.findViewById(R.id.btn_XacNhapDon);
            btn_GiaoHang = itemView.findViewById(R.id.btn_GiaoHang);
            btn_DaGiaoHang = itemView.findViewById(R.id.btn_DaGiaoHang);
            linearLayoutItemOrderAdmin = itemView.findViewById(R.id.linearLayoutItemOrderAdmin);
        }
    }
}
