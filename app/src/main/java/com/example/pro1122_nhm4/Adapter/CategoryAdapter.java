package com.example.pro1122_nhm4.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pro1122_nhm4.DAO.DishDAO;
import com.example.pro1122_nhm4.Interface.OnCategoryClickListener;
import com.example.pro1122_nhm4.Model.Category;
import com.example.pro1122_nhm4.Model.Dish;
import com.example.pro1122_nhm4.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<Category> categoryList;
    private Context context;
    private Dish dish;
    private DishDAO dishDAO;
    private DishAdapter dishAdapter;
    private OnCategoryClickListener listener;
    public CategoryAdapter(List<Category> categoryList, Context context,OnCategoryClickListener listener) {
        this.categoryList = categoryList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(categoryList.get(position).getName());
        Glide.with(context).load(categoryList.get(position).getImg()).into(holder.img);
        holder.linearLayout.setOnClickListener(view -> {
            int categoryId = categoryList.get(position).getCategory_id();
            Log.d("CategoryAdapter", "Listener: " + listener);
            listener.onCategoryClick(categoryId);
        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        private ImageView img;
        private LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_text);
            img = itemView.findViewById(R.id.item_image);
            linearLayout = itemView.findViewById(R.id.linearLayoutCategory);
        }
    }
}
