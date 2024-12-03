package com.example.pro1122_nhm4.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pro1122_nhm4.DAO.CartDAO;
import com.example.pro1122_nhm4.DAO.DishDAO;
import com.example.pro1122_nhm4.Fragment.CartFragmentUser;
import com.example.pro1122_nhm4.Model.Cart;
import com.example.pro1122_nhm4.Model.Dish;
import com.example.pro1122_nhm4.R;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private final CartFragmentUser cartFragmentUser;
    private List<Cart> cartList;
    private Context context;
    private CartDAO cartDAO;
    public DishDAO dishDAO;
    private DecimalFormat formatter = new DecimalFormat("#,###,###,###");
    public CartAdapter(Context context, DishDAO dishDAO, CartFragmentUser cartFragmentUser) {
        this.context = context;
        this.dishDAO = dishDAO;
        this.cartFragmentUser = cartFragmentUser;
        this.cartDAO = new CartDAO(context);
        this.dishDAO.open();
    }
    public CartAdapter(CartFragmentUser cartFragmentUser, Context context){
        this.cartFragmentUser = cartFragmentUser;
        this.context = context;
    }


    public void setData(List<Cart> cartList, CartDAO cartDAO) {
        this.cartList = cartList;
        this.cartDAO = cartDAO;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        dishDAO.open();
        Dish dish = dishDAO.getDishByID(cartList.get(position).getDish_id());
        if (dish != null) {
            holder.name.setText(dish.getName());
            holder.price.setText(formatter.format(dish.getPrice()) + "đ");
            holder.quantity.setText(String.valueOf(cartList.get(position).getQuantity()));
            Glide.with(context).load(dish.getImg()).into(holder.img);

            holder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int newQuantity = cartList.get(position).getQuantity() + 1;
                    int newSum = cartList.get(position).getSum() + dish.getPrice();
                    cartList.get(position).setQuantity(newQuantity);
                    cartList.get(position).setSum(newSum);
                    cartDAO.updateCart(cartList.get(position));
                    notifyDataSetChanged();
                    updateTotalPrice();
                }
            });

            holder.sub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int newQuantity = cartList.get(position).getQuantity() - 1;
                    int newSum = cartList.get(position).getSum() - dish.getPrice();

                    if (newQuantity <= 0) {
                        cartDAO.deleteCart(cartList.get(position).getCart_id());
                        cartList.remove(position);
                        notifyDataSetChanged();
//                        if (cartList.isEmpty()) {
//                            if (holder.recyclerView != null) {
//                                holder.recyclerView.setVisibility(View.GONE);
//                                notifyDataSetChanged();
//                            }
//                            if (holder.emptyView != null) {
//                                holder.emptyView.setVisibility(View.VISIBLE);
//                                notifyDataSetChanged();
//                            }
//                        } else {
//                            notifyDataSetChanged();
//                        }
                    } else {
                        cartList.get(position).setQuantity(newQuantity);
                        cartList.get(position).setSum(newSum);
                        cartDAO.updateCart(cartList.get(position));
                        notifyItemChanged(position);
                    }
                    updateTotalPrice();
                }
            });

            updateTotalPrice();
        }

        dishDAO.close();
    }

    public int getTotalQuantity() {
        int totalQuantity = 0;
        for (Cart cart : cartList) {
            totalQuantity += cart.getQuantity();
        }
        return totalQuantity;
    }

    public double getTotalPrice() {
        double totalPrice = 0;
        for (Cart cart : cartList) {
            totalPrice += cart.getSum();
        }
        return totalPrice;
    }

    private void updateTotalPrice() {
        double total = 0;
        for (Cart cart : cartList) {
            total += cart.getSum();
        }
        if (cartFragmentUser != null) {
            cartFragmentUser.updateTotalPrice(total);
        }
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img, add, sub;
        private TextView name, price, quantity, emptyView, total;
        private RecyclerView recyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.iv_item_cart_foodImg);
            name = itemView.findViewById(R.id.tv_item_cart_foodName);
            price = itemView.findViewById(R.id.tv_item_cart_foodPrice);
            quantity = itemView.findViewById(R.id.tv_item_cart_quantity);
            add = itemView.findViewById(R.id.btn_item_cart_quantity_up);
            sub = itemView.findViewById(R.id.btn_item_cart_quantity_down);
            recyclerView = itemView.findViewById(R.id.recy_fragment_cart_listFood);
            emptyView = itemView.findViewById(R.id.empty_view);
            total = itemView.findViewById(R.id.tv_fragment_cart_total);
        }
    }
}
