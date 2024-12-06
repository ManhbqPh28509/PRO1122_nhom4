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

import com.example.pro1122_nhm4.Fragment.FavouriteFragment;
import com.example.pro1122_nhm4.Fragment.HomeFragmentUser;
import com.example.pro1122_nhm4.Fragment.NotificationFragment;
import com.example.pro1122_nhm4.Fragment.OrderFragmentUser;
import com.example.pro1122_nhm4.Fragment.ProfileFragmentUser;
import com.example.pro1122_nhm4.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity2 extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FrameLayout fragmentContainer;
    private HomeFragmentUser homeFragmentUser;
    private OrderFragmentUser orderFragmentUser;
    private FavouriteFragment favouriteFragment;
    private ProfileFragmentUser profileFragmentUser;
    private NotificationFragment notificationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.xanh));
        }
        bottomNavigationView = findViewById(R.id.bottom_navi_user);
        fragmentContainer = findViewById(R.id.frameLayoutUser);
        if(homeFragmentUser == null){
            homeFragmentUser = new HomeFragmentUser();
        }
        if(orderFragmentUser == null){
            orderFragmentUser = new OrderFragmentUser();
        }
        if(favouriteFragment == null){
            favouriteFragment = new FavouriteFragment();
        }
        if(profileFragmentUser == null){
            profileFragmentUser = new ProfileFragmentUser();
        }if(notificationFragment == null){
            notificationFragment = new NotificationFragment();
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayoutUser, homeFragmentUser)
                .commit();
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.menu_item_1) {
                selectedFragment =  new HomeFragmentUser();
            } else if (item.getItemId() == R.id.menu_item_2) {
                selectedFragment =  new OrderFragmentUser();
            } else if (item.getItemId() == R.id.menu_item_3) {
                selectedFragment =  new FavouriteFragment();
            } else if (item.getItemId() == R.id.menu_item_4) {
                selectedFragment =  new NotificationFragment();
            } else if (item.getItemId() == R.id.menu_item_5) {
                selectedFragment =  new ProfileFragmentUser();
            }

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayoutUser, selectedFragment)
                    .commit();

            return true;
        });
    }
}