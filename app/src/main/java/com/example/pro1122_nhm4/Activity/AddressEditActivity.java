package com.example.pro1122_nhm4.Activity;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.List;

public class AddressEditActivity extends AppCompatActivity {
    private TextView tv_AddressEditAddress, tv_TenUserEditAddress, tv_SDTUserEditAddress;
    private Button btn_editAddress;
    private List<User> userList;
    private User user;
    private UserDAO userDAO;
    private ImageView img_backEditAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_address_edit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        img_backEditAddress = findViewById(R.id.img_backEditAddress);
        img_backEditAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tv_AddressEditAddress = findViewById(R.id.tv_DiaChiEditAddress);
        tv_TenUserEditAddress = findViewById(R.id.tv_TenUserEditAddress);
        tv_SDTUserEditAddress = findViewById(R.id.tv_SDTUserEditAddress);
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String diaChi = sharedPreferences.getString("diachi", null);
        String ten = sharedPreferences.getString("hoten", null);
        String sdt = sharedPreferences.getString("sdt", null);
        tv_AddressEditAddress.setText(diaChi);
        tv_TenUserEditAddress.setText(ten);
        tv_SDTUserEditAddress.setText(sdt);
        btn_editAddress = findViewById(R.id.btn_editAddress);
        btn_editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditAddressDialog();
            }
        });
    }

    private void EditAddressDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_edit_addrress, null);

        // Khởi tạo dialog
        Dialog dialog = new Dialog(this, androidx.appcompat.R.style.Base_V21_Theme_AppCompat_Dialog);
        dialog.setContentView(view);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(params);
        Button button = view.findViewById(R.id.btn_dialog_edit_address);
        Button button1 = view.findViewById(R.id.btn_dialog_cancel_edit_address);
        EditText edt_DiaChi = view.findViewById(R.id.ed_dialog_change_address);
        userList = new ArrayList<User>();
        user = new User();
        userDAO = new UserDAO(this);
        userDAO.open();
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        Integer user_id = sharedPreferences.getInt("userId", 0);
        String diaChi = sharedPreferences.getString("diachi", null);
        edt_DiaChi.setText(diaChi);
        user = userDAO.getUserById(user_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edt_DiaChi.getText().toString().isEmpty()){
                    user.setDiachi(edt_DiaChi.getText().toString());
                    long res = userDAO.editUser(user);
                    if (res > 0) {
                        Toast.makeText(AddressEditActivity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        tv_AddressEditAddress.setText(edt_DiaChi.getText().toString());
                        dialog.dismiss();
                    }else {
                        Toast.makeText(AddressEditActivity.this, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                    }else {
                    Toast.makeText(AddressEditActivity.this, "Hãy nhập địa chỉ", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                    }
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
}