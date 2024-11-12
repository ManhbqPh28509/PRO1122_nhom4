package com.example.pro1122_nhm4.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pro1122_nhm4.DAO.UserDAO;
import com.example.pro1122_nhm4.Model.User;
import com.example.pro1122_nhm4.R;

import org.apache.commons.validator.routines.EmailValidator;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;

public class SignUpActivity extends AppCompatActivity {
    private EditText etSignUpDate,etSignUpEmail,etSignUpHoTen,etSignUpPass,etSignUpDC,etSignUpPhone;
    private Button btnSignUp;
    private TextView tv_LogIn;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

            etSignUpDate = findViewById(R.id.ed_SignUpDate);
            etSignUpEmail = findViewById(R.id.ed_SignUpEmail);
            etSignUpHoTen = findViewById(R.id.ed_SignUpHoTen);
            etSignUpPass = findViewById(R.id.ed_SignUpPassword);
            etSignUpDC = findViewById(R.id.ed_SignUpDC);
            etSignUpPhone = findViewById(R.id.ed_SignUpSDT);
            btnSignUp = findViewById(R.id.btn_SignUp);
            tv_LogIn = findViewById(R.id.tv_logIn);
            userDAO = new UserDAO(SignUpActivity.this);
            userDAO.open();
            EmailValidator validator = EmailValidator.getInstance(true, false);
            String namePattern = "^([\\p{L}']+(\\s[\\p{L}']+)*)+$";
            String phonePattern = "^(0[2|3|5|7|8|9])+([0-9]{8})$";

            etSignUpDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDatePickerDialog();
                }
            });
            btnSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(etSignUpDate.getText().toString().isEmpty() || etSignUpEmail.getText().toString().isEmpty() || etSignUpHoTen.getText().toString().isEmpty() || etSignUpPass.getText().toString().isEmpty() || etSignUpDC.getText().toString().isEmpty() || etSignUpPhone.getText().toString().isEmpty()){
                        Toast.makeText(SignUpActivity.this, "Hãy điền đầy đủ thông tin cần thiết", Toast.LENGTH_SHORT).show();
                    } else if (!validator.isValid(etSignUpEmail.getText().toString().trim())) {
                        Toast.makeText(SignUpActivity.this, "Email không đúng định dạng", Toast.LENGTH_SHORT).show();
                    } else if (!etSignUpHoTen.getText().toString().trim().matches(namePattern))  {
                        Toast.makeText(SignUpActivity.this, "Họ và tên không đúng định dạng(Viết hoa những chữ cái đầu)", Toast.LENGTH_SHORT).show();
                    } else if (!etSignUpPhone.getText().toString().trim().matches(phonePattern)) {
                        Toast.makeText(SignUpActivity.this, "Số điện thoại không đúng định dạng", Toast.LENGTH_SHORT).show();
                    }else if (userDAO.checkEmail(etSignUpEmail.getText().toString().trim())){
                        Toast.makeText(SignUpActivity.this, "Tài khoản email này đã được đăng ký ", Toast.LENGTH_SHORT).show();
                    } else {
                        User user = new User();
                        user.setEmail(etSignUpEmail.getText().toString().trim());
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            user.setNgaysinh(LocalDate.parse(etSignUpDate.getText().toString().trim()));
                        }
                        user.setSdt((etSignUpPhone.getText().toString().trim()));
                        user.setMatkhau(etSignUpPass.getText().toString().trim());
                        user.setDiachi(etSignUpDC.getText().toString().trim());
                        user.setHoten(etSignUpHoTen.getText().toString().trim());
                        long res = userDAO.insertNew(user);
                        if (res != 0 ){
                            Toast.makeText(SignUpActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUpActivity.this,SignInActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SignUpActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                        }


                    }
                }
            });
            tv_LogIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        }
        private void showDatePickerDialog() {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    SignUpActivity.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(Calendar.YEAR, year);
                            calendar.set(Calendar.MONTH, month);
                            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            String formattedDate = dateFormat.format(calendar.getTime());
                            etSignUpDate.setText(formattedDate);
                        }
                    }, year, month, dayOfMonth);
            datePickerDialog.show();
        }
}
