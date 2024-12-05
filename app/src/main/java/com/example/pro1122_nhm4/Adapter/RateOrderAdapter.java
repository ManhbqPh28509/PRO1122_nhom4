package com.example.pro1122_nhm4.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pro1122_nhm4.DAO.RateOrderDAO;
import com.example.pro1122_nhm4.DAO.UserDAO;
import com.example.pro1122_nhm4.Model.RateOrder;
import com.example.pro1122_nhm4.R;

import java.util.List;

public class RateOrderAdapter extends RecyclerView.Adapter<RateOrderAdapter.ViewHolder> {
    private List<RateOrder> rateOrderList;
    private RateOrderDAO rateOrderDAO;
    private Context context;
    private UserDAO userDAO;
    public RateOrderAdapter(Context context) {
        this.context = context;
        this.userDAO = new UserDAO(context);
    }
    public void setData(List<RateOrder> rateOrderList, RateOrderDAO rateOrderDAO) {
        this.rateOrderList = rateOrderList;
        this.rateOrderDAO = rateOrderDAO;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RateOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rate_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RateOrderAdapter.ViewHolder holder, int position) {
        RateOrder rateOrder = rateOrderList.get(position);
        userDAO.open();
        holder.tv_user_rate.setText(userDAO.getUserNameById(rateOrder.getUser_id()));
        holder.tv_content_rate.setText(rateOrder.getComment());
        holder.library_tinted_wide_ratingbar_rateOrder.setRating(rateOrder.getRating());
    }

    @Override
    public int getItemCount() {
        return rateOrderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_user_rate, tv_content_rate;
        private me.zhanghai.android.materialratingbar.MaterialRatingBar library_tinted_wide_ratingbar_rateOrder;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_user_rate = itemView.findViewById(R.id.tv_user_rate_order);
            tv_content_rate = itemView.findViewById(R.id.tv_content_rateOrder);
            library_tinted_wide_ratingbar_rateOrder = itemView.findViewById(R.id.library_tinted_wide_ratingbar_rateOrder);
        }
    }
}
