package com.example.testone;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class AllCategoriesDialog extends DialogFragment {
    public interface GetCategory {
        void onGetCategoryResult(String category);
    }

    private GetCategory getCategory;

    public static final String ALL_CATEGORIES = "categories";
    public static final String CALLING_ACTIVITY = "activity";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View mView = getActivity().getLayoutInflater().inflate(R.layout.dialog_all_categories, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(mView);
        Bundle bundle = getArguments();
        if (null != bundle) {
            String callingActivity = bundle.getString(CALLING_ACTIVITY);
            ArrayList<String> categories = bundle.getStringArrayList(ALL_CATEGORIES);
            if (null != categories) {
                ListView listView = mView.findViewById(R.id.lView);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_list_item_1,
                        categories);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        switch (callingActivity) {
                            case "main_activity":
                                Intent intent = new Intent(getActivity(), SearchActivity.class);
                                intent.putExtra("category", categories.get(i));
                                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_NEW_TASK);
                                getActivity().startActivity(intent);
                                dismiss();
                                break;
                            case "search_activity":
                                try {
                                    getCategory = (GetCategory) getActivity();
                                    getCategory.onGetCategoryResult(categories.get(i));
                                    dismiss();
                                } catch (ClassCastException e) {
                                    e.printStackTrace();
                                }
                                break;
                        }

                    }
                });
            }
        }
        return builder.create();
    }
}