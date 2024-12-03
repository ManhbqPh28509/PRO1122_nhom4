package com.example.pro1122_nhm4.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pro1122_nhm4.Adapter.TabLayoutAdapter;
import com.example.pro1122_nhm4.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class OrderFragmentUser extends Fragment {
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private TabLayoutAdapter tabLayoutAdapter;

    public OrderFragmentUser() {
    }


    public static OrderFragmentUser newInstance() {
        OrderFragmentUser fragment = new OrderFragmentUser();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = view.findViewById(R.id.tab_layout_OrderUser);
        viewPager = view.findViewById(R.id.viewPageOrderUser);
        tabLayoutAdapter = new TabLayoutAdapter(getActivity());
        viewPager.setAdapter(tabLayoutAdapter);
        new TabLayoutMediator(tabLayout, viewPager, ((tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Giỏ hàng");
                    break;
                case 1:
                    tab.setText("Đang đến");
                    break;
                case 2:
                    tab.setText("Lịch sử");
                    break;
                case 3:
                    tab.setText("Đánh giá");

                    break;
            }
        })).attach();


    }
}