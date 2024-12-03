package com.example.pro1122_nhm4.Fragment;

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
import com.example.pro1122_nhm4.Adapter.OrderAdapterAdmin;
import com.example.pro1122_nhm4.DAO.OrderDAO;
import com.example.pro1122_nhm4.Model.Order;
import com.example.pro1122_nhm4.R;

import java.util.List;


public class OrderFragmentAdmin extends Fragment {
    private RecyclerView recyclerView;
    private Order order;
    private OrderDAO orderDAO;
    private OrderAdapterAdmin orderAdapterAdmin;
    private List<Order> orderList;


    public OrderFragmentAdmin() {

    }


    public static OrderFragmentAdmin newInstance() {
        OrderFragmentAdmin fragment = new OrderFragmentAdmin();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerViewOrderAdmin);
        orderDAO = new OrderDAO(getContext());
        orderDAO.open();
        orderAdapterAdmin = new OrderAdapterAdmin(getActivity());
        orderList = orderDAO.getAllOrders();
        orderAdapterAdmin.setData(orderList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(orderAdapterAdmin);
        orderDAO.close();
    }
}