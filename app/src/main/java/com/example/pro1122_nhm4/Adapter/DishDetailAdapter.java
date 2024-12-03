package com.example.pro1122_nhm4.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pro1122_nhm4.DAO.RateDishDAO;
import com.example.pro1122_nhm4.DAO.UserDAO;
import com.example.pro1122_nhm4.Model.RateDish;
import com.example.pro1122_nhm4.R;

import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class DishDetailAdapter extends RecyclerView.Adapter<DishDetailAdapter.ViewHolder> {
    private RateDish rateDish;
    private RateDishDAO rateDishDAO;
    private Context context;
    private List<RateDish> rateDishList;
    private UserDAO userDAO;
    public DishDetailAdapter(Context context) {
        this.context = context;
        this.userDAO = new UserDAO(context);
    }
    public void setData(List<RateDish> rateDishList, RateDishDAO rateDishDAO) {
        this.rateDishList = rateDishList;
        this.rateDishDAO = rateDishDAO;
    }

    @NonNull
    @Override
    public DishDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rate_dish, parent, false);
        return new DishDetailAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishDetailAdapter.ViewHolder holder, int position) {
        rateDish = rateDishList.get(position);
        userDAO.open();
        holder.tv_NameUser.setText(userDAO.getUserNameById(rateDish.getUser_id()));
        holder.tv_Content.setText(rateDish.getComment());
        holder.ratingBar.setRating(rateDish.getRating());
    }
    public float getAverageRating() {
        float sum = 0;
        for (RateDish rateDish : rateDishList) {
            sum += rateDish.getRating();
        }
        return sum / rateDishList.size();
    }
    @Override
    public int getItemCount() {
        return rateDishList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_NameUser,tv_Content;
        private MaterialRatingBar ratingBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_NameUser = itemView.findViewById(R.id.tv_user_rate);
            tv_Content = itemView.findViewById(R.id.tv_content_rate);
            ratingBar = itemView.findViewById(R.id.library_tinted_wide_ratingbar_rateDish);
        }
    }
}
