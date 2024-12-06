package com.example.pro1122_nhm4.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pro1122_nhm4.Activity.SignUpActivity;
import com.example.pro1122_nhm4.DAO.UserDAO;
import com.example.pro1122_nhm4.Model.User;
import com.example.pro1122_nhm4.R;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> {
    private List<User> userList;
    private UserDAO userDAO;
    private Context context;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public AccountAdapter(Context context) {
       this.context = context;
    }
    public void setData(List<User> userList, UserDAO userDAO) {
        this.userList = userList;
        this.userDAO = userDAO;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AccountAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account, parent, false);
        return new AccountAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        User user = userList.get(position);
        holder.tv_NameUserAccount.setText(user.getHoten());
        holder.tv_EmailUserAccount.setText(user.getEmail());
        holder.tv_PhoneUserAccount.setText(user.getSdt());
        holder.img_DeleteUserAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete");
                builder.setMessage("Do you want to delete");
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        long res = userDAO.deleteTV(user);
                        if (res != 0 ){
                            Toast.makeText(context, "Xóa Thành công", Toast.LENGTH_SHORT).show();
                            userList.remove(user);
                            userList.clear();
                            userList.addAll(userDAO.selectAll());
                            notifyDataSetChanged();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.show();
            }
        });
        holder.img_EditUserAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditAccount(user,position);
            }
        });

    }

    private void EditAccount(User user, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_edit_account, null);

        // Khởi tạo dialog
        Dialog dialog = new Dialog(context, androidx.appcompat.R.style.Base_V21_Theme_AppCompat_Dialog);
        dialog.setContentView(view);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(params);
        EditText ed_EditNameAccount = view.findViewById(R.id.ed_EditNameAccount);
        EditText ed_EditEmailAccount = view.findViewById(R.id.ed_EditEmailAccount);
        EditText ed_EditPhoneAccount = view.findViewById(R.id.ed_EditNumbersAccount);
        EditText ed_EditAddressAccount = view.findViewById(R.id.ed_EditAddressAccount);
        EditText ed_EditDateAccount = view.findViewById(R.id.ed_EditDateAccount);
        EditText ed_EditPasswordAccount = view.findViewById(R.id.ed_EditPasswordAccount);
        RadioGroup radioGroupEditAccount = view.findViewById(R.id.radioGroupEditAccount);
        ed_EditNameAccount.setText(user.getHoten());
        ed_EditEmailAccount.setText(user.getEmail());
        ed_EditPhoneAccount.setText(user.getSdt());
        ed_EditAddressAccount.setText(user.getDiachi());
        ed_EditPasswordAccount.setText(user.getMatkhau());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ed_EditDateAccount.setText(user.getNgaysinh().format(dateFormatter));
        }
        ed_EditDateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(ed_EditDateAccount);
            }
        });
        if(user.getUser_role().equals("admin")){
            radioGroupEditAccount.check(R.id.radiobtn_Admin);
        }else {
            radioGroupEditAccount.check(R.id.radiobtn_User);
        }
        Button btn_EditDiaLogEditAccount = view.findViewById(R.id.btn_EditDiaLogAccount);
        btn_EditDiaLogEditAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ed_EditEmailAccount.getText().toString().equals("")){
                    Toast.makeText(context, "Hãy nhập Email", Toast.LENGTH_SHORT).show();
                } else if (ed_EditNameAccount.getText().toString().equals("")) {
                    Toast.makeText(context, "Hãy nhập Họ và tên", Toast.LENGTH_SHORT).show();
                } else if (ed_EditPhoneAccount.getText().toString().equals("")) {
                    Toast.makeText(context, "Hãy nhập Số điện thoại", Toast.LENGTH_SHORT).show();
                } else if (ed_EditAddressAccount.getText().toString().equals("")) {
                    Toast.makeText(context, "Hãy nhập địa chỉ", Toast.LENGTH_SHORT).show();
                } else if (ed_EditPasswordAccount.getText().toString().equals("")) {
                    Toast.makeText(context, "Hãy nhập mật khẩu", Toast.LENGTH_SHORT).show();
                } else {
                    user.setHoten(ed_EditNameAccount.getText().toString());
                    user.setEmail(ed_EditEmailAccount.getText().toString());
                    user.setSdt(ed_EditPhoneAccount.getText().toString());
                    user.setDiachi(ed_EditAddressAccount.getText().toString());
                    user.setMatkhau(ed_EditPasswordAccount.getText().toString());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        user.setNgaysinh(LocalDate.parse(ed_EditDateAccount.getText().toString()));
                    }
                    if(radioGroupEditAccount.getCheckedRadioButtonId() == R.id.radiobtn_Admin){
                        user.setUser_role("admin");
                    }else {
                        user.setUser_role("user");
                    }
                    long res = userDAO.editUser(user);
                    if(res>0){
                        Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        userList.remove(position);
                        userList.clear();
                        userList.addAll(userDAO.selectAll());
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }else {
                        Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
            }
        });
        Button btn_CancelDialogEditAccount = view.findViewById(R.id.btn_CancelDialogAccount);
        btn_CancelDialogEditAccount.setOnClickListener(new View.OnClickListener() {
                                                           @Override
                                                           public void onClick(View v) {
                                                               dialog.dismiss();
                                                           }
                                                       });

        dialog.show();

    }
    private void showDatePickerDialog(EditText editText) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                R.style.CustomDatePickerDialog, // Áp dụng theme tùy chỉnh
                (view, selectedYear, selectedMonth, selectedDayOfMonth) -> {
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(Calendar.YEAR, selectedYear);
                    selectedDate.set(Calendar.MONTH, selectedMonth);
                    selectedDate.set(Calendar.DAY_OF_MONTH, selectedDayOfMonth);
                    String formattedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                            .format(selectedDate.getTime());
                    editText.setText(formattedDate);
                },
                year,
                month,
                dayOfMonth
        );

        datePickerDialog.show();
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_NameUserAccount,tv_EmailUserAccount,tv_PhoneUserAccount;
        private ImageView img_DeleteUserAccount,img_EditUserAccount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_NameUserAccount = itemView.findViewById(R.id.tv_NameUserItemAccount);
            tv_EmailUserAccount = itemView.findViewById(R.id.tv_EmailUserItemAccount);
            tv_PhoneUserAccount = itemView.findViewById(R.id.tv_SDTItemAccount);
            img_DeleteUserAccount = itemView.findViewById(R.id.imgBinAccount);
            img_EditUserAccount = itemView.findViewById(R.id.imgEditAccount);

        }
    }
}
