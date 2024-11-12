package com.example.pro1122_nhm4.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pro1122_nhm4.DAO.UserDAO;
import com.example.pro1122_nhm4.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignInActivity extends AppCompatActivity {
    private TextInputLayout textInputLayoutPass,textInputLayoutEmail;
    private TextInputEditText edt_Password,edt_Email;
    private TextView tv_SignUp,tv_ForgotPass;
    private Button btn_Login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPass = findViewById(R.id.textInputLayoutPass);
        edt_Password = findViewById(R.id.edt_Password);
        edt_Email = findViewById(R.id.edt_Email);
        tv_SignUp = findViewById(R.id.tv_SignUp);
        btn_Login = findViewById(R.id.btn_Login);
        tv_ForgotPass = findViewById(R.id.tv_ForgotPassword);
        UserDAO userDAO = new UserDAO(this);
        userDAO.open();
        tv_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edt_Email.getText().toString().isEmpty() || edt_Password.getText().toString().isEmpty()){
                    Toast.makeText(SignInActivity.this, "Please enter your email and password", Toast.LENGTH_SHORT).show();
                }else if(!userDAO.checkLoginThanhVien(edt_Email.getText().toString(),edt_Password.getText().toString())){
                    Toast.makeText(SignInActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }else{
                    if(userDAO.checkRoleUser(edt_Email.getText().toString(),edt_Password.getText().toString())){
                        Intent intent = new Intent(SignInActivity.this,MainActivity2.class);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(SignInActivity.this,MainActivity.class);
                        startActivity(intent);
                    }

                }
            }
        });
        tv_ForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this,ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

    }
}