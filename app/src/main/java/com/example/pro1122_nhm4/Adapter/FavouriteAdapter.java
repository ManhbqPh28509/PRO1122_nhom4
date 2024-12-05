package com.example.pro1122_nhm4.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pro1122_nhm4.Activity.DetailDishActivity;
import com.example.pro1122_nhm4.DAO.DishDAO;
import com.example.pro1122_nhm4.DAO.FavouriteDAO;
import com.example.pro1122_nhm4.Model.Dish;
import com.example.pro1122_nhm4.Model.Favourite;
import com.example.pro1122_nhm4.R;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {
    private Favourite favourite;
    private FavouriteDAO favouriteDAO;
    private Context context;
    private List<Favourite> favouriteList;
    private DishDAO dishDAO;
    public FavouriteAdapter(Context context, DishDAO dishDAO) {
        this.context = context;
        this.dishDAO = dishDAO;
        this.favouriteDAO = new FavouriteDAO(context);
        this.dishDAO.open();
    }
    public void setData(List<Favourite> favouriteList, FavouriteDAO favouriteDAO){
        this.favouriteList = favouriteList;
        this.favouriteDAO = favouriteDAO;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public FavouriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new FavouriteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteAdapter.ViewHolder holder, int position) {
        favourite = favouriteList.get(position);
        dishDAO.open();
        Dish dish = dishDAO.getDishByID(favourite.getDish_id());
        holder.tv_nameDishFavourite.setText(String.valueOf(dish.getName()));
        Glide.with(context).load(dish.getImg()).into(holder.img_DishFavourite);
        holder.linearLayoutItemFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailDishActivity.class);
                intent.putExtra("dish_id", dish.getDish_id());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return favouriteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearLayoutItemFavourite;
        private ImageView img_DishFavourite;
        private TextView tv_nameDishFavourite;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayoutItemFavourite = itemView.findViewById(R.id.linearLayoutItemFavourite);
            img_DishFavourite = itemView.findViewById(R.id.img_DishFavourite);
            tv_nameDishFavourite = itemView.findViewById(R.id.tv_nameDishFavourite);
        }
    }
}
