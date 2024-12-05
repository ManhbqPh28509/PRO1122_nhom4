package com.example.pro1122_nhm4.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pro1122_nhm4.Adapter.RateOrderAdapter;
import com.example.pro1122_nhm4.DAO.RateOrderDAO;
import com.example.pro1122_nhm4.Model.RateOrder;
import com.example.pro1122_nhm4.R;

import java.util.List;


public class RateFragmentUser extends Fragment {
    private RecyclerView recyclerView;
    private RateOrderAdapter rateOrderAdapter;
    private RateOrderDAO rateOrderDAO;
    private List<RateOrder> rateOrderList;
    public RateFragmentUser() {

    }


    public static RateFragmentUser newInstance() {
        RateFragmentUser fragment = new RateFragmentUser();
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
        return inflater.inflate(R.layout.fragment_rate_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recylerView_RateOrderUser);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        rateOrderDAO = new RateOrderDAO(getContext());
        rateOrderDAO.open();
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", 0);
        String user_role = sharedPreferences.getString("user_role", "");
        if(user_role.equals("user")){
            rateOrderList = rateOrderDAO.getRateOrdersByUserId(userId);
        }else{
            rateOrderList = rateOrderDAO.getAllRateOrders();
        }
        for(RateOrder rateOrder : rateOrderList){
            Log.d("rateOrder", rateOrder.toString());
        }
        rateOrderAdapter = new RateOrderAdapter(getActivity());
        rateOrderAdapter.setData(rateOrderList,rateOrderDAO);
        recyclerView.setAdapter(rateOrderAdapter);
    }
}