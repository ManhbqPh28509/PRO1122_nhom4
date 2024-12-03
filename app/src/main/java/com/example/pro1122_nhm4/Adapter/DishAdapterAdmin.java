package com.example.pro1122_nhm4.Adapter;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pro1122_nhm4.Activity.DetailDishActivity;
import com.example.pro1122_nhm4.DAO.CategoryDAO;
import com.example.pro1122_nhm4.DAO.DishDAO;
import com.example.pro1122_nhm4.Fragment.HomeFragmentAdmin;
import com.example.pro1122_nhm4.Interface.OnImageSelectedListener;
import com.example.pro1122_nhm4.Model.Category;
import com.example.pro1122_nhm4.Model.Dish;
import com.example.pro1122_nhm4.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DishAdapterAdmin extends RecyclerView.Adapter<DishAdapterAdmin.ViewHolder> {
    private List<Dish> dishList;
    private Context context;
    private DishDAO dishDAO;
    private List<Category> categoryList;
    private Category category;
    private CategoryDAO categoryDAO;
    private HomeFragmentAdmin homeFragmentAdmin;
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public OnImageSelectedListener onImageSelectedListener;

    public DishAdapterAdmin(Context context) {
        this.context = context;
    }
    public DishAdapterAdmin(HomeFragmentAdmin homeFragmentAdmin) {
         this.homeFragmentAdmin = homeFragmentAdmin;
         this.context = homeFragmentAdmin.getContext();
    }
    public void setData(List<Dish> dishList, DishDAO dishDAO) {
        this.dishList = dishList;
        this.dishDAO = dishDAO;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public DishAdapterAdmin.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dish_admin, parent, false);
        return new DishAdapterAdmin.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishAdapterAdmin.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Dish dish = dishList.get(position);
        holder.name.setText(dish.getName());
        holder.description.setText(dish.getDescription());
        DecimalFormat formatter = new DecimalFormat("#,###,###,###");
        holder.price.setText(formatter.format(dish.getPrice())+"đ");
        Glide.with(context).load(dish.getImg()).into(holder.img);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailDishActivity.class);
                intent.putExtra("dish_id",dish.getDish_id());
                context.startActivity(intent);
            }
        });
        holder.bin.setOnClickListener(new View.OnClickListener() {
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
                        long res = dishDAO.deleteDish(dish);
                        if (res != 0 ){
                            Toast.makeText(context, "Xóa Thành công", Toast.LENGTH_SHORT).show();
                            dishList.remove(dish);
                            dishList.clear();
                            dishList.addAll(dishDAO.getAll());
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
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateDish(dish,position);
            }
        });

    }

    private void UpdateDish(Dish dish, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_edit_dish, null);

        // Khởi tạo dialog
        Dialog dialog = new Dialog(context, androidx.appcompat.R.style.Base_V21_Theme_AppCompat_Dialog);
        dialog.setContentView(view);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(params);
        EditText ed_EditNameDish = view.findViewById(R.id.ed_EditNameDish);
        EditText ed_EditPriceDish = view.findViewById(R.id.ed_EditPriceDish);
        EditText ed_EditDescriptonDish = view.findViewById(R.id.ed_EditDescriptionDish);
        EditText ed_EditImgDish = view.findViewById(R.id.ed_EditImgDish);
        RadioGroup radioGroupEditDish = view.findViewById(R.id.radioGroupEditDish);
        Spinner spinnerEditDish = view.findViewById(R.id.spinnerEditDish);
        categoryList = new ArrayList<Category>();
        categoryDAO = new CategoryDAO(context);
        categoryDAO.open();
        categoryList = categoryDAO.getAll();
        CategorySpinnerAdapter categorySpinnerAdapter = new CategorySpinnerAdapter(context, categoryList);
        spinnerEditDish.setAdapter(categorySpinnerAdapter);
        spinnerEditDish.setSelection(categorySpinnerAdapter.getPosition(category));
        final Category[] selectedCategory = {null};

        spinnerEditDish.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedCategory[0] = (Category) parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
        for (int i = 0; i < categoryList.size(); i++) {
            if ( categoryList.get(i).getCategory_id().equals(dish.getCategory_id())) {
                spinnerEditDish.setSelection(i);
                break;
            }
        }
        ed_EditNameDish.setText(dish.getName());
        ed_EditPriceDish.setText(String.valueOf(dish.getPrice()));
        ed_EditDescriptonDish.setText(dish.getDescription());
        ed_EditImgDish.setText(dish.getImg());
        if (dish.getAvailability().equals("Còn hàng")){
            radioGroupEditDish.check(R.id.radiobtn_ConHang);
        } else {
            radioGroupEditDish.check(R.id.radiobtn_HetHang);
        }
        Button btn_UpdateDish = view.findViewById(R.id.btn_EditDiaLogEditDish);
        btn_UpdateDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ed_EditNameDish.getText().toString().trim();
                String price = ed_EditPriceDish.getText().toString().trim();
                String description = ed_EditDescriptonDish.getText().toString().trim();
                String img = ed_EditImgDish.getText().toString().trim();
                if(name.equals("")){
                    Toast.makeText(context, "Vui lòng nhập tên món ăn", Toast.LENGTH_SHORT).show();
                }else if(price.equals("")){
                    Toast.makeText(context, "Vui lòng nhập giá món ăn", Toast.LENGTH_SHORT).show();
                }else if(description.equals("")){
                    Toast.makeText(context, "Vui lòng nhập mô tả món ăn", Toast.LENGTH_SHORT).show();
                }else if(img.equals("")){
                    Toast.makeText(context, "Vui lòng nhập ảnh món ăn", Toast.LENGTH_SHORT).show();
                }else if (radioGroupEditDish.getCheckedRadioButtonId() == -1){
                    Toast.makeText(context, "Vui lòng chọn trạng thái món ăn", Toast.LENGTH_SHORT).show();
                }else {
                    if (radioGroupEditDish.getCheckedRadioButtonId() == R.id.radiobtn_ConHang){
                        dish.setAvailability("Còn hàng");
                    } else {
                        dish.setAvailability("Hết hàng");
                    }
                    if (selectedCategory[0] != null){
                        dish.setCategory_id(selectedCategory[0].getCategory_id());
                    }
                    dish.setName(name);
                    dish.setPrice(Integer.parseInt(price));
                    dish.setDescription(description);
                    dish.setImg(img);
                    dishDAO.open();
                    long res = dishDAO.updateDish(dish);
                    if (res != 0){
                        Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        dishList.set(position,dish);
                        dishList.clear();
                        dishList.addAll(dishDAO.getAll());
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }else {
                        Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        Button btn_CancelDiaLogEditDish = view.findViewById(R.id.btn_CancelDialogEditDish);
        btn_CancelDiaLogEditDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button btn_EditImgDish = view.findViewById(R.id.btn_EditImgDish);
        btn_EditImgDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (homeFragmentAdmin != null) {

                    homeFragmentAdmin.openCamera(dish);// Gọi trực tiếp từ Fragment
                    setOnImageSelectedListener(new OnImageSelectedListener() {
                        @Override
                        public void onImageSelected(String imagePath) {
                            ed_EditImgDish.setText(imagePath);
                        }
                    });
                }
            }
        });
        dialog.show();
    }
    public void setOnImageSelectedListener(OnImageSelectedListener listener) {
        this.onImageSelectedListener = listener;
    }

    @Override
    public int getItemCount() {
        return dishList.size();
    }

    public void AddDish() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_add_dish, null);

        // Khởi tạo dialog
        Dialog dialog = new Dialog(context, androidx.appcompat.R.style.Base_V21_Theme_AppCompat_Dialog);
        dialog.setContentView(view);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(params);
        EditText ed_AddNameDish = view.findViewById(R.id.ed_AddNameDish);
        EditText ed_AddPriceDish = view.findViewById(R.id.ed_AddPriceDish);
        EditText ed_AddDescriptonDish = view.findViewById(R.id.ed_AddDescriptionDish);
        EditText ed_AddImgDish = view.findViewById(R.id.ed_AddImgDish);
        RadioGroup radioGroupAddDish = view.findViewById(R.id.radioGroupAddDish);
        Spinner spinnerAddDish = view.findViewById(R.id.spinnerAddDish);
        categoryList = new ArrayList<Category>();
        categoryDAO = new CategoryDAO(context);
        categoryDAO.open();
        categoryList = categoryDAO.getAll();
        CategorySpinnerAdapter categorySpinnerAdapter = new CategorySpinnerAdapter(context, categoryList);
        spinnerAddDish.setAdapter(categorySpinnerAdapter);
        final Category[] selectedCategory = {null};

        spinnerAddDish.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedCategory[0] = (Category) parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
        Button btn_AddDiaLogAddDish = view.findViewById(R.id.btn_AddDiaLogAddDish);
        btn_AddDiaLogAddDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dish dish = new Dish();
                String name = ed_AddNameDish.getText().toString().trim();
                String price = ed_AddPriceDish.getText().toString().trim();
                String description = ed_AddDescriptonDish.getText().toString().trim();
                String img = ed_AddImgDish.getText().toString().trim();
                if(name.equals("")){
                    Toast.makeText(context, "Vui lòng nhập tên món ăn", Toast.LENGTH_SHORT).show();
                }else if(price.equals("")){
                    Toast.makeText(context, "Vui lòng nhập giá món ăn", Toast.LENGTH_SHORT).show();
                }else if(description.equals("")){
                    Toast.makeText(context, "Vui lòng nhập mô tả món ăn", Toast.LENGTH_SHORT).show();
                }else if(img.equals("")){
                    Toast.makeText(context, "Vui lòng nhập ảnh món ăn", Toast.LENGTH_SHORT).show();
                }else if (radioGroupAddDish.getCheckedRadioButtonId() == -1){
                    Toast.makeText(context, "Vui lòng chọn trạng thái món ăn", Toast.LENGTH_SHORT).show();
                }else {
                    if (radioGroupAddDish.getCheckedRadioButtonId() == R.id.radiobtn_ConHang){
                        dish.setAvailability("Còn hàng");
                    } else {
                        dish.setAvailability("Hết hàng");
                    }
                    if(selectedCategory[0] != null){
                        dish.setCategory_id(selectedCategory[0].getCategory_id());
                    }
                    dish.setName(name);
                    dish.setPrice(Integer.parseInt(price));
                    dish.setDescription(description);
                    dish.setImg(img);
                    dishDAO.open();
                    long res = dishDAO.addDish(dish);
                    if (res != 0) {
                        Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        dishList.add(dish);
                        dishList.clear();
                        dishList.addAll(dishDAO.getAll());
                        notifyDataSetChanged();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }


            }
        });
        Button btn_CancelDialogAddDish = view.findViewById(R.id.btn_CancelDialogAddDish);
        btn_CancelDialogAddDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button btn_EditImgDish = view.findViewById(R.id.btn_AddImgDish);
        btn_EditImgDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (homeFragmentAdmin != null) {
                    Dish dish = new Dish();
                    homeFragmentAdmin.openCamera(dish);// Gọi trực tiếp từ Fragment
                    setOnImageSelectedListener(new OnImageSelectedListener() {
                        @Override
                        public void onImageSelected(String imagePath) {
                            ed_AddImgDish.setText(imagePath);
                        }
                    });
                }
            }
        });
        dialog.show();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name,price,description;
        private ImageView img,edit,bin;
        private CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.text_dish_nameHomeAdmin);
            price = itemView.findViewById(R.id.tv_dishPriceHomeAdmin);
            img = itemView.findViewById(R.id.image_dishHomeAdmin);
            description = itemView.findViewById(R.id.text_dish_descriptionHomeAdmin);
            edit = itemView.findViewById(R.id.imgEditDish);
            bin = itemView.findViewById(R.id.imgBinDish);
            cardView = itemView.findViewById(R.id.cardView_dishHomeAdmin);
        }
    }
}
