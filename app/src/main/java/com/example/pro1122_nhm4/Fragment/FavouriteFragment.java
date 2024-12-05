package com.example.pro1122_nhm4.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pro1122_nhm4.Adapter.FavouriteAdapter;
import com.example.pro1122_nhm4.DAO.DishDAO;
import com.example.pro1122_nhm4.DAO.FavouriteDAO;
import com.example.pro1122_nhm4.R;

public class FavouriteFragment extends Fragment {
    private RecyclerView recyclerView;
    private DishDAO dishDAO;
    private FavouriteAdapter favouriteAdapter;
    private FavouriteDAO favouriteDAO;
    public FavouriteFragment() {

    }


    public static FavouriteFragment newInstance() {
        FavouriteFragment fragment = new FavouriteFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fravourite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dishDAO = new DishDAO(getContext());
        favouriteDAO = new FavouriteDAO(getContext());
        dishDAO.open();
        favouriteDAO.open();
        recyclerView = view.findViewById(R.id.recyclerViewFavourite);
        favouriteAdapter = new FavouriteAdapter(getContext(), dishDAO);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        int userID = sharedPreferences.getInt("userId", -1);
        favouriteAdapter.setData(favouriteDAO.getAllFavouriteByUserId(userID), favouriteDAO);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(favouriteAdapter);

    }
}