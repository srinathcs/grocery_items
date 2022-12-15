package com.example.testone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FirstCartFragment extends Fragment implements CartAdapter.DeleteItem, CartAdapter.TotalPrice {


    private AppCompatTextView tvTotal, tvNoItem;
    private AppCompatButton btnNext;
    private RecyclerView rvFirstCart;
    private RelativeLayout rlItems;
    private CartAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_cart_first, container, false);
        initView(mView);
        adapter = new CartAdapter(getActivity(), this);
        rvFirstCart.setAdapter(adapter);
        rvFirstCart.setLayoutManager(new LinearLayoutManager(getActivity()));

        ArrayList<GroceryItem> cartItem = Utils.getCartItem(getActivity());
        if (null != cartItem) {
            if (cartItem.size() > 0) {
                tvNoItem.setVisibility(View.GONE);
                rlItems.setVisibility(View.VISIBLE);
                adapter.setItems(cartItem);
            } else {
                tvNoItem.setVisibility(View.VISIBLE);
                rlItems.setVisibility(View.GONE);
            }
        } else {
            tvNoItem.setVisibility(View.VISIBLE);
            rlItems.setVisibility(View.GONE);
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.flLayout, new FragmentCartSecond());
                transaction.commit();
            }
        });

        return mView;

    }

    @Override
    public void onDeleteResult(GroceryItem item) {
        Utils.deleteItemFormCart(getActivity(), item);
        ArrayList<GroceryItem> cartItem = Utils.getCartItem(getActivity());
        if (null != cartItem) {
            if (cartItem.size() > 0) {
                tvNoItem.setVisibility(View.GONE);
                rlItems.setVisibility(View.VISIBLE);
                adapter.setItems(cartItem);
            } else {
                tvNoItem.setVisibility(View.VISIBLE);
                rlItems.setVisibility(View.GONE);
            }
        } else {
            tvNoItem.setVisibility(View.VISIBLE);
            rlItems.setVisibility(View.GONE);
        }

    }


    @Override
    public void getTotalPrice(double price) {
        tvTotal.setText(String.valueOf(price) + "Rs");
    }


    private void initView(View mView) {
        tvTotal = mView.findViewById(R.id.tvTotal);
        tvNoItem = mView.findViewById(R.id.tvEmptyItem);
        btnNext = mView.findViewById(R.id.btnNext);
        rvFirstCart = mView.findViewById(R.id.rvFirstCart);
        rlItems = mView.findViewById(R.id.rlItems);

    }


}
