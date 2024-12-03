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

import com.example.pro1122_nhm4.Adapter.CommingOrderAdapter;
import com.example.pro1122_nhm4.DAO.OrderDAO;
import com.example.pro1122_nhm4.Model.Order;
import com.example.pro1122_nhm4.R;

import java.util.List;


public class CommingFragment extends Fragment {
    private RecyclerView recyclerView;
    private Order order;
    private OrderDAO orderDAO;
    private CommingOrderAdapter commingOrderAdapter;
    private List<Order> orderList;

    public CommingFragment() {

    }


    public static CommingFragment newInstance() {
        CommingFragment fragment = new CommingFragment();
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
        return inflater.inflate(R.layout.fragment_comming, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.reacyclerViewCommingOrder);
        orderDAO = new OrderDAO(getContext());
        orderDAO.open();
        commingOrderAdapter = new CommingOrderAdapter(getActivity());
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        Integer userID = sharedPreferences.getInt("userId", -1);
        orderList = orderDAO.getAllOrdersByUserIDAndStatus(userID);
        commingOrderAdapter.setData(orderList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(commingOrderAdapter);

    }
}