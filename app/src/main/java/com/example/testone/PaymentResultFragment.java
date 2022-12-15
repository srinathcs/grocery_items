package com.example.testone;

import static com.example.testone.FragmentCartSecond.ORDER_KEY;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class PaymentResultFragment extends Fragment {

    private AppCompatTextView tvMessage;
    private AppCompatButton btnHome;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_payment_result, container, false);
        initView(mView);

        Bundle bundle = getArguments();
        if (null != bundle) {
            String jsonOrder = bundle.getString(ORDER_KEY);
            if (null != jsonOrder) {
                Gson gson = new Gson();
                Type type = new TypeToken<OrderModel>() {
                }.getType();
                OrderModel orderModel = gson.fromJson(jsonOrder, type);
                if (null != orderModel) {
                    if (orderModel.isSuccess()) {
                        tvMessage.setText("Payment was successful\nYour Order will arrive in 3 days");
                        Utils.clearCartItems(getActivity());
                        for (GroceryItem item:orderModel.getItems()){
                            Utils.increasePopularityPoint(getActivity(),item,1);
                            Utils.changeUserPoint(getActivity(),item,4);
                        }
                    } else {
                        tvMessage.setText("Payment failed,\nPlease try another payment method");
                    }
                }
            }else {

            }
        }

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        return mView;
    }

    private void initView(View mView) {
        tvMessage = mView.findViewById(R.id.tvPayMsg);
        btnHome = mView.findViewById(R.id.btnHome);
    }
}
