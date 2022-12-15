package com.example.testone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class FragmentCartSecond extends Fragment {
    public static final String ORDER_KEY = "order";
    private AppCompatEditText etAddress, etZipCode, etPhone, etEmail;
    private AppCompatButton btnBack, btnNext;
    private AppCompatTextView tvWarning;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_cart_second, container, false);

        initView(mView);

        Bundle bundle = getArguments();
        if (null != bundle){
            String jsonOrder = bundle.getString(ORDER_KEY);
            if (null != jsonOrder){
                Gson gson = new Gson();
                Type type = new TypeToken<OrderModel>(){}.getType();
                OrderModel model = gson.fromJson(jsonOrder,type);
                if (null != model){
                    etAddress.setText(model.getAddress());
                    etPhone.setText(model.getPhoneNumber());
                    etZipCode.setText(model.getPhoneNumber());
                    etEmail.setText(model.getEmail());
                }
            }
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.flLayout, new FirstCartFragment());
                transaction.commit();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ValidateData()) {
                    tvWarning.setVisibility(View.GONE);

                    ArrayList<GroceryItem> cartItem = Utils.getCartItem(getActivity());
                    if (null != cartItem) {
                        OrderModel orderModel = new OrderModel();
                        orderModel.setItems(cartItem);
                        orderModel.setAddress(etAddress.getText().toString());
                        orderModel.setEmail(etEmail.getText().toString());
                        orderModel.setPhoneNumber(etPhone.getText().toString());
                        orderModel.setZipCode(etZipCode.getText().toString());
                        orderModel.setTotalPrice(calculateTotalPrice(cartItem));

                        Gson gson = new Gson();
                        String jsonOrder = gson.toJson(orderModel);
                        Bundle bundle = new Bundle();
                        bundle.putString(ORDER_KEY, jsonOrder);

                        FragmentCartThird fragmentCartThird = new FragmentCartThird();
                        fragmentCartThird.setArguments(bundle);
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.flLayout,fragmentCartThird);
                        transaction.commit();
                    }
                } else {
                    tvWarning.setVisibility(View.VISIBLE);
                    tvWarning.setText("Fill All The Details");
                }
            }
        });
        return mView;
    }

    private double calculateTotalPrice(ArrayList<GroceryItem> items) {
        double price = 0;
        for (GroceryItem item : items) {
            price += item.getPrice();
        }
        return price;
    }

    private boolean ValidateData() {
        if (etAddress.getText().toString().equals("") || etZipCode.getText().toString().equals("") ||
                etEmail.getText().toString().equals("") || etPhone.getText().toString().equals("")) {
            return false;
        }
        return true;
    }

    private void initView(View mView) {
        etAddress = mView.findViewById(R.id.etAddress);
        etZipCode = mView.findViewById(R.id.etZipCode);
        etPhone = mView.findViewById(R.id.etPhoneNum);
        etEmail = mView.findViewById(R.id.etEmail);
        btnBack = mView.findViewById(R.id.btnBack);
        btnNext = mView.findViewById(R.id.btnNext);
        tvWarning = mView.findViewById(R.id.tvWarning);
    }
}
