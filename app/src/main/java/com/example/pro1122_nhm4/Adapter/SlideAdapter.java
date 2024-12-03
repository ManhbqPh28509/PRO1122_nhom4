package com.example.pro1122_nhm4.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pro1122_nhm4.Fragment.HomeFragmentUser;
import com.example.pro1122_nhm4.R;

import java.util.List;

public class SlideAdapter extends RecyclerView.Adapter<SlideAdapter.ViewHolder> {
    private List<Integer> imageList;
    private Context context;
    public SlideAdapter( List<Integer> imageList) {
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public SlideAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_silde, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SlideAdapter.ViewHolder holder, int position) {
        holder.imageView.setImageResource(imageList.get(position));
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_slide);
        }
    }
}
