package com.example.pro1122_nhm4.Activity;

import android.os.Build;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.pro1122_nhm4.Adapter.OrderAdapterAdmin;
import com.example.pro1122_nhm4.Fragment.HomeFragmentAdmin;
import com.example.pro1122_nhm4.Fragment.HomeFragmentUser;
import com.example.pro1122_nhm4.Fragment.NotificationFragment;
import com.example.pro1122_nhm4.Fragment.OrderFragmentAdmin;
import com.example.pro1122_nhm4.Fragment.ProfileFragmentUser;
import com.example.pro1122_nhm4.Fragment.RateFragmentUser;
import com.example.pro1122_nhm4.Fragment.StatisticalFragmentAdmin;
import com.example.pro1122_nhm4.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FrameLayout fragmentContainer;
    private HomeFragmentAdmin homeFragmentAdmin;
    private OrderFragmentAdmin orderFragmentAdmin;
    private StatisticalFragmentAdmin statisticalFragmentAdmin;
    private RateFragmentUser rateFragmentUser;
    private NotificationFragment notificationFragment;
    private ProfileFragmentUser profileFragmentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.xanh));
        }
        bottomNavigationView = findViewById(R.id.bottom_navi_admin);
        fragmentContainer = findViewById(R.id.frameLayoutAdmin);
        if (homeFragmentAdmin == null){
            homeFragmentAdmin = new HomeFragmentAdmin();
        }
        if(orderFragmentAdmin == null){
            orderFragmentAdmin = new OrderFragmentAdmin();
        }
        if(statisticalFragmentAdmin == null){
            statisticalFragmentAdmin = new StatisticalFragmentAdmin();
        }
        if(rateFragmentUser == null){
            rateFragmentUser = new RateFragmentUser();
        }if(notificationFragment == null){
            notificationFragment = new NotificationFragment();
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayoutAdmin, homeFragmentAdmin)
                .commit();
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.menu_item_1) {
                selectedFragment =  new HomeFragmentAdmin();
            } else if (item.getItemId() == R.id.menu_item_2) {
                selectedFragment =  new OrderFragmentAdmin();
            } else if (item.getItemId() == R.id.menu_item_3) {
                selectedFragment =  new RateFragmentUser();
            } else if (item.getItemId() == R.id.menu_item_4) {
                selectedFragment =  new StatisticalFragmentAdmin();
            } else if (item.getItemId() == R.id.menu_item_5) {
                selectedFragment =  new ProfileFragmentUser();
            }

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayoutAdmin, selectedFragment)
                    .commit();

            return true;
        });
    }
}