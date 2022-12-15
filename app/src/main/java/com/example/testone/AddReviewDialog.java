package com.example.testone;

import static com.example.testone.GroceryItemActivity.GROCERY_ITEM_KEY;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddReviewDialog extends DialogFragment {
    public interface AddReview {
        void onAddReviewResult(Review review);
    }

    private AddReview addReview;
    private AppCompatTextView tvName, tvWarning;
    private AppCompatEditText etName, etReview;
    private AppCompatButton btnAddReview;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View mView = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_review, null);
        initView(mView);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(mView);
        Bundle bundle = getArguments();
        if (null != bundle) {
            GroceryItem item = bundle.getParcelable(GROCERY_ITEM_KEY);
            if (null != item) {
                tvName.setText(item.getName());
                btnAddReview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String userName = etName.getText().toString();
                        String review = etReview.getText().toString();
                        String date = getCurrentDate();
                        if (userName.equals("") || review.equals("")) {
                            tvWarning.setText("Fill all the blanks");
                            tvWarning.setVisibility(View.VISIBLE);
                        } else {
                            tvWarning.setVisibility(View.GONE);
                            try {
                                addReview = (AddReview) getActivity();
                                addReview.onAddReviewResult(new Review(item.getId(), userName, review, date));
                                dismiss();
                            } catch (ClassCastException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        }
        return builder.create();
    }

    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-DD-YYYY");
        return sdf.format(calendar.getTime());
    }

    private void initView(View mView) {
        tvName = mView.findViewById(R.id.tvName);
        tvWarning = mView.findViewById(R.id.tvWarning);
        etName = mView.findViewById(R.id.etName);
        etReview = mView.findViewById(R.id.etReview);
        btnAddReview = mView.findViewById(R.id.btnAddReview);

    }


}
