package com.example.pro1122_nhm4.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pro1122_nhm4.Activity.AccountActivity;
import com.example.pro1122_nhm4.Activity.AddressEditActivity;
import com.example.pro1122_nhm4.Activity.SignInActivity;
import com.example.pro1122_nhm4.R;


public class ProfileFragmentUser extends Fragment {
    private Button btn_SignOut;
    private TextView tv_ProfileUserName;
    private LinearLayout linearLayout_AddressProfile,linearLayout_acconut;

    public ProfileFragmentUser() {
    }


    public static ProfileFragmentUser newInstance() {
        ProfileFragmentUser fragment = new ProfileFragmentUser();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_SignOut = view.findViewById(R.id.btn_SignOut);
        tv_ProfileUserName = view.findViewById(R.id.tv_ProfileUserName);
        linearLayout_acconut = view.findViewById(R.id.linearLayout_account);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String hoten = sharedPreferences.getString("hoten", "");
        String user_role = sharedPreferences.getString("user_role", "");
        if(user_role.equals("admin")){
            linearLayout_acconut.setVisibility(View.VISIBLE);
        }else {
            linearLayout_acconut.setVisibility(View.GONE);
        }
        linearLayout_acconut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AccountActivity.class);
                startActivity(intent);
            }
        });
        tv_ProfileUserName.setText(hoten);
        linearLayout_AddressProfile = view.findViewById(R.id.linearLayout_AddressProfile);
        linearLayout_AddressProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddressEditActivity.class);
                startActivity(intent);
            }
        });
        btn_SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SignInActivity.class);
                startActivity(intent);
            }
        });


    }
}