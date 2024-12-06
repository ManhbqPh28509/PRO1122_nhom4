package com.example.pro1122_nhm4.Activity;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pro1122_nhm4.Adapter.AccountAdapter;
import com.example.pro1122_nhm4.DAO.UserDAO;
import com.example.pro1122_nhm4.Model.User;
import com.example.pro1122_nhm4.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AccountActivity extends AppCompatActivity {
    private ImageView img_backEditAccount;
    private RecyclerView recyclerViewAccout;
    private UserDAO userDAO;
    private AccountAdapter accountAdapter;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        img_backEditAccount = findViewById(R.id.img_backEditAccount);
        recyclerViewAccout = findViewById(R.id.reacyclerViewAccout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewAccout.setLayoutManager(layoutManager);
        userDAO = new UserDAO(this);
        userDAO.open();
        userList = userDAO.selectAll();
        accountAdapter = new AccountAdapter(this);
        accountAdapter.setData(userList,userDAO);
        recyclerViewAccout.setAdapter(accountAdapter);
        img_backEditAccount.setOnClickListener(v -> {
            onBackPressed();
        });

    }
}