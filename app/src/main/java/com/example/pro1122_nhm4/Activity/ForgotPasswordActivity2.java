package com.example.pro1122_nhm4.Activity;

import android.content.Intent;
import android.os.Bundle;
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

public class ForgotPasswordActivity2 extends AppCompatActivity {
    private Button btn_ForgotPassword2;
    private TextInputEditText edit_PassForgotPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btn_ForgotPassword2 = findViewById(R.id.btn_ForgotPassword2);
        edit_PassForgotPass = findViewById(R.id.edt_PassForgotPass);
        UserDAO userDAO = new UserDAO(this);
        userDAO.open();
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        btn_ForgotPassword2.setOnClickListener(v -> {
            if(edit_PassForgotPass.getText().toString().isEmpty()){
                Toast.makeText(this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();

            }else{
                userDAO.editForgotPass(email,edit_PassForgotPass.getText().toString());
                Toast.makeText(this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(ForgotPasswordActivity2.this, SignInActivity.class);
                startActivity(intent1);
            }




        });

    }
}