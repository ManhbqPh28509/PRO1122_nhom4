package com.example.pro1122_nhm4.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.pro1122_nhm4.Fragment.CartFragmentUser;
import com.example.pro1122_nhm4.Fragment.CommingFragment;
import com.example.pro1122_nhm4.Fragment.HistoryFragment;
import com.example.pro1122_nhm4.Fragment.RateFragmentUser;

public class TabLayoutAdapter extends FragmentStateAdapter {


    public TabLayoutAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 0:
                return new CartFragmentUser();
            case 1:
                return new CommingFragment();
            case 2:
                return new HistoryFragment();
            case 3:
                return new RateFragmentUser();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}