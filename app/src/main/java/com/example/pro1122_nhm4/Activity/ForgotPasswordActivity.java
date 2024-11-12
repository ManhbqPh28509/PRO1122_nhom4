package com.example.pro1122_nhm4.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pro1122_nhm4.DAO.UserDAO;
import com.example.pro1122_nhm4.R;
import com.google.android.material.textfield.TextInputEditText;

public class ForgotPasswordActivity extends AppCompatActivity {
    private Button btn_next;
    private TextInputEditText edt_EmailPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btn_next = findViewById(R.id.btn_ForgotPassword);
        edt_EmailPassword = findViewById(R.id.edt_EmailForgotPass);
        UserDAO userDAO = new UserDAO(this);
        userDAO.open();
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edt_EmailPassword.getText().toString().isEmpty()){
                    Toast.makeText(ForgotPasswordActivity.this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
                }else{
                    if (userDAO.checkEmail(edt_EmailPassword.getText().toString())){
                        Intent intent = new Intent(ForgotPasswordActivity.this, ForgotPasswordActivity2.class);
                        intent.putExtra("email",edt_EmailPassword.getText().toString());
                        startActivity(intent);
                    }else {
                        Toast.makeText(ForgotPasswordActivity.this, "Email not found", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }
}