package com.example.pro1122_nhm4.Fragment;

import static android.app.Activity.RESULT_OK;
import static com.example.pro1122_nhm4.Adapter.DishAdapterAdmin.REQUEST_IMAGE_CAPTURE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pro1122_nhm4.Adapter.DishAdapter;
import com.example.pro1122_nhm4.Adapter.DishAdapterAdmin;
import com.example.pro1122_nhm4.DAO.DishDAO;
import com.example.pro1122_nhm4.Interface.OnImageSelectedListener;
import com.example.pro1122_nhm4.Model.Dish;
import com.example.pro1122_nhm4.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;


public class HomeFragmentAdmin extends Fragment implements OnImageSelectedListener {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private DishAdapterAdmin dishAdapterAdmin;
    private RecyclerView recyclerViewDish;
    private List<Dish> dishList;
    private DishDAO dishDAO;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EditText edt_Search;
    private Dish currentDish;
    private OnImageSelectedListener onImageSelectedListener;
    private FloatingActionButton floatingActionButton;

    public HomeFragmentAdmin() {

    }


    public static HomeFragmentAdmin newInstance() {
        HomeFragmentAdmin fragment = new HomeFragmentAdmin();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edt_Search = view.findViewById(R.id.ed_fragment_home_searchHomeAdmin);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefeshLayoutHomeAdmin);
        recyclerViewDish = view.findViewById(R.id.recycler_view_DishHomeAdmin);
        dishDAO = new DishDAO(getActivity());
        dishDAO.open();
        dishList = dishDAO.getAll();
        dishAdapterAdmin = new DishAdapterAdmin(this);
        dishAdapterAdmin.setData(dishDAO.getAll(),dishDAO);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerViewDish.setLayoutManager(layoutManager);
        recyclerViewDish.setAdapter(dishAdapterAdmin);
        floatingActionButton = view.findViewById(R.id.floatingButtonHomeAdmin);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dishAdapterAdmin.AddDish();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(() -> {
            // Mô phỏng tải dữ liệu mới
            new Handler().postDelayed(() -> {
                edt_Search.setText("");
                dishList = dishDAO.getAll();
                dishAdapterAdmin.setData(dishList,dishDAO);
                recyclerViewDish.setAdapter(dishAdapterAdmin);
                // Tắt hiệu ứng làm mới
                swipeRefreshLayout.setRefreshing(false);
            }, 1000); // Mô phỏng thời gian tải 2 giây
        });
        edt_Search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String query = edt_Search.getText().toString().trim();
                if (!query.isEmpty()) {
                    List<Dish> results = dishDAO.getDishesByName(query);
                    dishList.clear();
                    dishList.addAll(results);
                    dishAdapterAdmin.setData(dishList,dishDAO);
                } else {
                    Toast.makeText(HomeFragmentAdmin.this.getContext(), "Vui lòng nhập từ khóa!", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE && data != null) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            if (photo != null && currentDish != null) {
                String filePath = saveImageToInternalStorage(photo);
                // Lưu ảnh vào bộ nhớ và lấy đường dẫn
                if (filePath != null) {
                    // Gọi callback trong Adapter để cập nhật Dialog
                    if (dishAdapterAdmin != null) {
                        dishAdapterAdmin.onImageSelectedListener.onImageSelected(filePath);
                    }
                } else {
                    Toast.makeText(getContext(), "Lưu ảnh thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // Hàm lưu ảnh vào bộ nhớ trong
    private String saveImageToInternalStorage(Bitmap bitmap) {
        File directory = getContext().getDir("images", getContext().MODE_PRIVATE);
        File file = new File(directory, System.currentTimeMillis() + ".jpg");

        try (FileOutputStream fos = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return file.getAbsolutePath(); // Trả về đường dẫn file
    }

    // Hàm mở camera từ Adapter
    public void openCamera(Dish dish) {
        this.currentDish = dish; // Lưu món ăn hiện tại
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    @Override
    public void onImageSelected(String imagePath) {

    }
}