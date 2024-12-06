package com.example.pro1122_nhm4.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pro1122_nhm4.Activity.AddressEditActivity;
import com.example.pro1122_nhm4.Adapter.CategoryAdapter;
import com.example.pro1122_nhm4.Adapter.DishAdapter;
import com.example.pro1122_nhm4.Adapter.SlideAdapter;
import com.example.pro1122_nhm4.DAO.CategoryDAO;
import com.example.pro1122_nhm4.DAO.DishDAO;
import com.example.pro1122_nhm4.Interface.OnCategoryClickListener;
import com.example.pro1122_nhm4.Model.Dish;
import com.example.pro1122_nhm4.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;


public class HomeFragmentUser extends Fragment implements OnCategoryClickListener {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private SlideAdapter sildeAdapter;
    private List<Integer> images;
    private SharedPreferences sharedPreferences;
    private TextView tv_DiaChi;
    private CategoryAdapter categoryAdapter;
    private CategoryDAO categoryDAO;
    private RecyclerView recyclerViewCategory;
    private DishAdapter dishAdapter;
    private RecyclerView recyclerViewDish;
    private List<Dish> dishList;
    private DishDAO dishDAO;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EditText edt_Search;
    public HomeFragmentUser() {

    }


    public static HomeFragmentUser newInstance() {
        HomeFragmentUser fragment = new HomeFragmentUser();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayoutHome);
        edt_Search = view.findViewById(R.id.ed_fragment_home_search);
        categoryDAO = new CategoryDAO(getActivity());
        categoryDAO.open();
        dishDAO = new DishDAO(getActivity());
        dishDAO.open();
        dishList = dishDAO.getAll();
        dishAdapter = new DishAdapter(getContext());
        dishAdapter.setData(dishDAO.getAll(),dishDAO);
        recyclerViewDish = view.findViewById(R.id.recycler_view_dish);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerViewDish.setLayoutManager(layoutManager);
        recyclerViewDish.setAdapter(dishAdapter);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String userDiaChi = sharedPreferences.getString("diachi", null);
        tv_DiaChi = view.findViewById(R.id.tv_DiaChiUser);
        tv_DiaChi.setText(userDiaChi);
        tv_DiaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddressEditActivity.class);
                startActivity(intent);
            }
        });
        recyclerViewCategory = view.findViewById(R.id.recycler_view_category);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.HORIZONTAL, false);
        recyclerViewCategory.setLayoutManager(gridLayoutManager);
        Log.d("MainActivity", "Number of items: " + categoryDAO.getAll().size());
        CategoryAdapter categoryAdapter = new CategoryAdapter(categoryDAO.getAll(),getContext(),this);
        recyclerViewCategory.setAdapter(categoryAdapter);
        edt_Search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String query = edt_Search.getText().toString().trim();
                if (!query.isEmpty()) {
                    Toast.makeText(HomeFragmentUser.this.getContext(), query, Toast.LENGTH_SHORT).show();
                    List<Dish> results = dishDAO.getDishesByName(query);
                    dishList.clear();
                    dishList.addAll(results);
                    dishAdapter.setData(dishList,dishDAO);
                } else {
                    Toast.makeText(HomeFragmentUser.this.getContext(), "Vui lòng nhập từ khóa!", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        images = Arrays.asList(
                R.drawable.banner1,
                R.drawable.banner2,
                R.drawable.banner3,
                R.drawable.banner4,
                R.drawable.banner5,
                R.drawable.banner6
        );


        SlideAdapter adapter = new SlideAdapter(images);
        viewPager.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            // Mô phỏng tải dữ liệu mới
            new Handler().postDelayed(() -> {
                edt_Search.setText("");
                dishList = dishDAO.getAll(); 
                dishAdapter.setData(dishList,dishDAO);
                recyclerViewDish.setAdapter(dishAdapter);
                // Tắt hiệu ứng làm mới
                swipeRefreshLayout.setRefreshing(false);
            }, 1000); // Mô phỏng thời gian tải 2 giây
        });
        // Liên kết TabLayout với ViewPager2
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            // Không cần thiết lập gì thêm, chấm sẽ tự động tạo
        }).attach();
        autoScroll();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        categoryDAO.close();
        dishDAO.close();
    }
    @Override
    public void onCategoryClick(int categoryId) {
        dishList = dishDAO.getDishesByCategotyID(categoryId); // Sử dụng lại DishDAO
        dishAdapter.setData(dishList,dishDAO);
    }

    private void autoScroll() {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int nextItem = (viewPager.getCurrentItem() + 1) % images.size();
                viewPager.setCurrentItem(nextItem, true); // true cho phép cuộn mượt mà
                Log.d("AutoScroll", "Next Item: " + nextItem); // In ra log để kiểm tra
                handler.postDelayed(this, 3000); // Mỗi 3 giây chuyển ảnh
            }
        };
        handler.postDelayed(runnable, 3000);
    }
}