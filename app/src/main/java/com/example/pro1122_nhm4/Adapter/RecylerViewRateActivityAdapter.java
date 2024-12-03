package com.example.pro1122_nhm4.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
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

public class RecylerViewRateActivityAdapter extends RecyclerView.Adapter<RecylerViewRateActivityAdapter.ViewHolder> {
    private List<OrderItem> orderItemList;
    private Context context;
    private OrderItem orderItem;
    private OrderItemDAO orderItemDAO;
    private DishDAO dishDAO;
    public RecylerViewRateActivityAdapter(Context context, DishDAO dishDAO) {
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
    public RecylerViewRateActivityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rating_dish_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecylerViewRateActivityAdapter.ViewHolder holder, int position) {
        dishDAO.open();
        Dish dish = dishDAO.getDishByID(orderItemList.get(position).getDish_id());
        Glide.with(context).load(dish.getImg()).into(holder.imageView);
        holder.tv_nameDish.setText(dish.getName());
        holder.ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if(rating == 5){
                holder.edt_comment.setText("Rất tốt");
            } else if (rating == 4){
                holder.edt_comment.setText("Tốt");
            } else if (rating == 3) {
                holder.edt_comment.setText("Tạm được");
            } else if (rating == 2) {
                holder.edt_comment.setText("Không tốt");
            } else if (rating == 1) {
                holder.edt_comment.setText("Rất không tốt");
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView tv_nameDish;
        private RatingBar ratingBar;
        private EditText edt_comment;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_ratingDish);
            tv_nameDish = itemView.findViewById(R.id.tv_ratingDish);
            ratingBar = itemView.findViewById(R.id.library_tinted_wide_ratingbar_ratingDish);
            edt_comment = itemView.findViewById(R.id.edt_ratingDish);
        }
    }
}
