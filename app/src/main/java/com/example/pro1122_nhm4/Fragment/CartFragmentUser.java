package com.example.pro1122_nhm4.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pro1122_nhm4.Activity.ConfirmationOrderActivity;
import com.example.pro1122_nhm4.Adapter.CartAdapter;
import com.example.pro1122_nhm4.DAO.CartDAO;
import com.example.pro1122_nhm4.DAO.DishDAO;
import com.example.pro1122_nhm4.Model.Cart;
import com.example.pro1122_nhm4.R;

import java.text.DecimalFormat;
import java.util.List;


public class CartFragmentUser extends Fragment {
    private RecyclerView recyclerView;
    private CartDAO cartDAO;
    private CartAdapter cartAdapter;
    private List<Cart> cartList;
    private DishDAO dishDAO;
    private TextView totalTextView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Button btn_orderCart;

    public CartFragmentUser() {

    }


    public static CartFragmentUser newInstance() {
        CartFragmentUser fragment = new CartFragmentUser();
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
        return inflater.inflate(R.layout.fragment_cart_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recy_fragment_cart_listFood);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayotCart);
        totalTextView = view.findViewById(R.id.tv_fragment_cart_total);
        btn_orderCart = view.findViewById(R.id.btn_fragment_cart_confirm);
        cartDAO = new CartDAO(getContext());
        dishDAO = new DishDAO(getContext());
        cartDAO.open();
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        Integer userID = sharedPreferences.getInt("userId", -1);
        Log.d("CartFragmentUser", "userID:"+userID);
        Log.d("CartDAO", "Querying carts for userID: " + userID);
        cartList = cartDAO.getCartByUserID(userID);
        Log.d("CartDAO", "Cart list size: " + (cartList != null ? cartList.size() : 0));
        cartAdapter = new CartAdapter(getContext(),dishDAO,this);
        if (cartList == null || cartList.isEmpty()) {
            Log.w("CartFragmentUser", "Cart list is empty for user ID: " + userID);
            // Hiển thị một TextView thông báo danh sách trống
            recyclerView.setVisibility(View.GONE);
            view.findViewById(R.id.empty_view).setVisibility(View.VISIBLE);
            cartAdapter.notifyDataSetChanged();// Một TextView trong layout
        } else {
            Log.d("CartFragmentUser", "Cart list size: " + cartList.size());
            recyclerView.setVisibility(View.VISIBLE);
            view.findViewById(R.id.empty_view).setVisibility(View.GONE);
            cartAdapter.setData(cartList, cartDAO);
            recyclerView.setAdapter(cartAdapter);
            cartAdapter.notifyDataSetChanged();
        }
        cartAdapter.setData(cartList,cartDAO);
        cartAdapter.notifyDataSetChanged();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(cartAdapter);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            // Mô phỏng tải dữ liệu mới
            new Handler().postDelayed(() -> {
                cartList = cartDAO.getCartByUserID(userID);
                cartAdapter.setData(cartList,cartDAO);
                recyclerView.setAdapter(cartAdapter);
                if(recyclerView.getVisibility() == View.GONE){
                    recyclerView.setVisibility(View.VISIBLE);
                    view.findViewById(R.id.empty_view).setVisibility(View.GONE);
                }
                // Tắt hiệu ứng làm mới
                swipeRefreshLayout.setRefreshing(false);
            }, 500); // Mô phỏng thời gian tải 2 giây
        });
        btn_orderCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cartList == null || cartList.isEmpty()) {
                    Toast.makeText(CartFragmentUser.this.getContext(), "Giỏ hàng trống", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(getContext(), ConfirmationOrderActivity.class);
                    startActivity(intent);
                }


            }
        });

    }
    public void updateTotalPrice(double total) {
        if (totalTextView != null) {
            DecimalFormat formatter = new DecimalFormat("#,###,###,###");
            totalTextView.setText("" + formatter.format(total) + "đ");
        }
    }
}