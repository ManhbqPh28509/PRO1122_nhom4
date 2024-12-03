package com.example.pro1122_nhm4.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pro1122_nhm4.Model.Category;
import com.example.pro1122_nhm4.R;

import java.util.ArrayList;
import java.util.List;

public class CategorySpinnerAdapter extends ArrayAdapter<Category> {
    private List<Category> categoryList;
    private Context context;
    public CategorySpinnerAdapter (@NonNull Context context, @NonNull List<Category> categoryList) {
        super(context, 0, categoryList);
        this.context = context;
        this.categoryList = categoryList;
    }

    public CategorySpinnerAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_spinner_edit_dish, parent, false);
        }

        Category category = categoryList.get(position);
        TextView textView = convertView.findViewById(R.id.tv_spinnerMaDanhMuc);
        TextView textView2 = convertView.findViewById(R.id.tv_spinnerTenDanhMuc);
        if (category != null) {
            textView.setText(category.getCategory_id().toString());
            textView2.setText(category.getName().toString());
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_spinner_edit_dish, parent, false);
        }

        Category category = categoryList.get(position);
        TextView textView = convertView.findViewById(R.id.tv_spinnerMaDanhMuc);
        TextView textView2 = convertView.findViewById(R.id.tv_spinnerTenDanhMuc);
        if (category != null) {
            textView.setText(category.getCategory_id().toString());
            textView2.setText(category.getName().toString());
        }

        return convertView;
    }
}
