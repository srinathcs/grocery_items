package com.example.testone;

import static com.example.testone.FragmentCartSecond.ORDER_KEY;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentCartThird extends Fragment {
    private AppCompatTextView tvItems, tvAddress, tvPhoneNumber, tvTotalPrice;
    private AppCompatButton btnBack, btnCheckOut;
    private RadioGroup rgPayment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_cart_third, container, false);

        initView(mView);

        Bundle bundle = getArguments();
        if (null != bundle) {
            String jsonOrder = bundle.getString(ORDER_KEY);
            if (null != jsonOrder) {
                Gson gson = new Gson();
                Type type = new TypeToken<OrderModel>() {
                }.getType();
                OrderModel model = gson.fromJson(jsonOrder, type);
                if (null != model) {
                    String items = " ";
                    for (GroceryItem i : model.getItems()) {
                        items += "\n\t" + i.getName();
                    }
                    tvItems.setText(items);
                    tvAddress.setText(model.getAddress());
                    tvPhoneNumber.setText(model.getPhoneNumber());
                    tvTotalPrice.setText(String.valueOf(model.getTotalPrice()));

                    btnBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Bundle backBundle = new Bundle();
                            backBundle.putString(ORDER_KEY, jsonOrder);
                            FragmentCartSecond fragmentCartSecond = new FragmentCartSecond();
                            fragmentCartSecond.setArguments(backBundle);
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.flLayout, fragmentCartSecond);
                            transaction.commit();
                        }
                    });
                    btnCheckOut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            switch (rgPayment.getCheckedRadioButtonId()) {
                                case R.id.rbPayPal:
                                    model.setPaymentMethod("Paypal");
                                    break;
                                case R.id.rbCreditCard:
                                    model.setPaymentMethod("Credit Card");
                                    break;
                                default:
                                    model.setPaymentMethod("Unknown");
                                    break;
                            }
                            model.setSuccess(true);

                            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
                                    .setLevel(HttpLoggingInterceptor.Level.BODY);

                            OkHttpClient client = new OkHttpClient.Builder()
                                    .addInterceptor(interceptor)
                                    .build();

                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl("http://jsonplaceholder.typicode.com/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .client(client)
                                    .build();
                            OrderEndPoint endPoint = retrofit.create(OrderEndPoint.class);
                            Call<OrderModel> call = endPoint.newOrder(model);
                            call.enqueue(new Callback<OrderModel>() {
                                @Override
                                public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {
                                    Log.d("TAG", "onResponse: " + response.code());
                                    if (response.isSuccessful()) {
                                        Bundle resultBundle = new Bundle();
                                        resultBundle.putString(ORDER_KEY, gson.toJson(response.body()));
                                        PaymentResultFragment paymentResultFragment = new PaymentResultFragment();
                                        paymentResultFragment.setArguments(resultBundle);
                                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                        transaction.replace(R.id.flLayout, paymentResultFragment);
                                        transaction.commit();
                                    }
                                }

                                @Override
                                public void onFailure(Call<OrderModel> call, Throwable t) {
                                    t.printStackTrace();
                                }
                            });
                        }
                    });

                }
            }
        }

        return mView;
    }

    private void initView(View mView) {
        rgPayment = mView.findViewById(R.id.rgPaymentMethod);
        tvItems = mView.findViewById(R.id.tvItems);
        tvAddress = mView.findViewById(R.id.tvAddress);
        tvPhoneNumber = mView.findViewById(R.id.tvPhoneNum);
        tvTotalPrice = mView.findViewById(R.id.tvPrice);
        btnBack = mView.findViewById(R.id.btnBack);
        btnCheckOut = mView.findViewById(R.id.btnCheckOut);

    }
}
