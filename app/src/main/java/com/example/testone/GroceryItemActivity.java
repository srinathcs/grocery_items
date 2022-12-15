package com.example.testone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class GroceryItemActivity extends AppCompatActivity implements AddReviewDialog.AddReview {
    private RecyclerView rvReview;
    private AppCompatTextView tvName, tvPrice, tvDescription, tvReview, tvAddReview;
    private AppCompatImageView ivItem, ivFirstFilled, ivFirstEmpty, ivSecondFilled, ivSecondEmpty, ivThirdFilled, ivThirdEmpty;
    private AppCompatButton btnAddToCart;
    private RelativeLayout rlFirstStart, rlSecondStart, rlThirdStart;
    private GroceryItem incomingItem;
    public static final String GROCERY_ITEM_KEY = "incoming_item";
    private ReviewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_item);
        initView();
        adapter = new ReviewsAdapter();
        Intent intent = getIntent();
        if (null != intent) {
            incomingItem = intent.getParcelableExtra(GROCERY_ITEM_KEY);
            if (null != incomingItem) {
                Utils.changeUserPoint(this, incomingItem, 1);
                tvName.setText(incomingItem.getName());
                tvDescription.setText(incomingItem.getDescription());
                tvPrice.setText(String.valueOf(incomingItem.getPrice()) + "Rs");
                Glide.with(this)
                        .asBitmap()
                        .load(incomingItem.getImageUrl())
                        .into(ivItem);
                ArrayList<Review> reviews = Utils.getReviewById(this, incomingItem.getId());
                rvReview.setAdapter(adapter);
                rvReview.setLayoutManager(new LinearLayoutManager(this));
                if (null != reviews) {
                    if (reviews.size() > 0) {
                        adapter.setReviews(reviews);
                    }
                }
            }
        }
        tvAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddReviewDialog addReviewDialog = new AddReviewDialog();
                Bundle bundle = new Bundle();
                bundle.putParcelable(GROCERY_ITEM_KEY, incomingItem);
                addReviewDialog.setArguments(bundle);
                addReviewDialog.show(getSupportFragmentManager(), "add_review");
            }
        });
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.addItemToCard(GroceryItemActivity.this, incomingItem);
                Log.e("TAG", "onClick: card Item " + Utils.getCartItem(GroceryItemActivity.this));
                Intent intentCart = new Intent(GroceryItemActivity.this, CartActivity.class);
                intentCart.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentCart);
            }
        });
        handleRating();
    }

    private void handleRating() {
        switch (incomingItem.getRate()) {
            case 0:
                ivFirstEmpty.setVisibility(View.VISIBLE);
                ivFirstFilled.setVisibility(View.GONE);
                ivSecondEmpty.setVisibility(View.VISIBLE);
                ivSecondFilled.setVisibility(View.GONE);
                ivThirdEmpty.setVisibility(View.VISIBLE);
                ivThirdFilled.setVisibility(View.GONE);
                break;

            case 1:
                ivFirstEmpty.setVisibility(View.GONE);
                ivFirstFilled.setVisibility(View.VISIBLE);
                ivSecondEmpty.setVisibility(View.VISIBLE);
                ivSecondFilled.setVisibility(View.GONE);
                ivThirdEmpty.setVisibility(View.VISIBLE);
                ivThirdFilled.setVisibility(View.GONE);
                break;

            case 2:
                ivFirstEmpty.setVisibility(View.GONE);
                ivFirstFilled.setVisibility(View.VISIBLE);
                ivSecondEmpty.setVisibility(View.GONE);
                ivSecondFilled.setVisibility(View.VISIBLE);
                ivThirdEmpty.setVisibility(View.VISIBLE);
                ivThirdFilled.setVisibility(View.GONE);
                break;

            case 3:
                ivFirstEmpty.setVisibility(View.GONE);
                ivFirstFilled.setVisibility(View.VISIBLE);
                ivSecondEmpty.setVisibility(View.GONE);
                ivSecondFilled.setVisibility(View.VISIBLE);
                ivThirdEmpty.setVisibility(View.GONE);
                ivThirdFilled.setVisibility(View.VISIBLE);
                break;

            default:
                break;
        }

        rlFirstStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (incomingItem.getRate() != 1) {
                    Utils.changeRate(GroceryItemActivity.this, incomingItem.getId(), 1);
                    Utils.changeUserPoint(GroceryItemActivity.this, incomingItem, (1 - incomingItem.getRate()) * 2);
                    incomingItem.setRate(1);
                    handleRating();
                }
            }
        });
        rlSecondStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (incomingItem.getRate() != 2) {
                    Utils.changeRate(GroceryItemActivity.this, incomingItem.getId(), 2);
                    Utils.changeUserPoint(GroceryItemActivity.this, incomingItem, (2 - incomingItem.getRate()) * 2);
                    incomingItem.setRate(2);
                    handleRating();
                }
            }
        });
        rlThirdStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (incomingItem.getRate() != 3) {
                    Utils.changeRate(GroceryItemActivity.this, incomingItem.getId(), 3);
                    Utils.changeUserPoint(GroceryItemActivity.this, incomingItem, (2 - incomingItem.getRate()) * 2);
                    incomingItem.setRate(3);
                    handleRating();
                }
            }
        });
    }

    private void initView() {
        rvReview = findViewById(R.id.rvReview);
        tvName = findViewById(R.id.tvName);
        tvPrice = findViewById(R.id.tvPrice);
        tvDescription = findViewById(R.id.tvDescription);
        tvReview = findViewById(R.id.tvReview);
        tvAddReview = findViewById(R.id.tvAddReview);
        ivItem = findViewById(R.id.ivItem);
        ivFirstFilled = findViewById(R.id.ivFirstFilled);
        ivFirstEmpty = findViewById(R.id.ivFirstEmpty);
        ivSecondFilled = findViewById(R.id.ivSecondFilled);
        ivSecondEmpty = findViewById(R.id.ivSecondEmpty);
        ivThirdEmpty = findViewById(R.id.ivThirdEmpty);
        ivThirdFilled = findViewById(R.id.ivThirdFilled);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        rlFirstStart = findViewById(R.id.rlFirstStar);
        rlSecondStart = findViewById(R.id.rlSecondStar);
        rlThirdStart = findViewById(R.id.rlThirdStar);
    }

    @Override
    public void onAddReviewResult(Review review) {
        Log.e("TAG", "onAddReviewResult: review" + review);
        Utils.addReview(this, review);
        Utils.changeUserPoint(this,incomingItem,3);
        ArrayList<Review> reviews = Utils.getReviewById(this, review.getGroceryItemId());
        if (null != reviews) {
            adapter.setReviews(reviews);
        }

    }
}