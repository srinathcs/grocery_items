package com.example.testone;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.DialogFragment;

public class LicencesDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View mView= getActivity().getLayoutInflater().inflate(R.layout.dialog_licences,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(mView);
        AppCompatTextView tvLicences =mView.findViewById(R.id.tvLicences);
        AppCompatButton btnDismiss = mView.findViewById(R.id.btnDismiss);

        tvLicences.setText(Utils.getLicenses());
        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return builder.create();
    }
}
