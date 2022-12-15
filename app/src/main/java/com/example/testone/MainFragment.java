package com.example.testone;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class MainFragment extends Fragment {
    private BottomNavigationView navigationView;
    private RecyclerView rvNewItem, rvPopularItem, rvSuggestedItem;
    private GroceryItemAdapter newItemAdapter, popularItemAdapter, suggestedItemAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_main, container, false);
        initView(mView);
        initButtonNav();
        //Utils.clearSharedPreference(getActivity());
        return mView;

    }

    @Override
    public void onResume() {
        super.onResume();
        initRecViews();
    }

    private void initView(View mView) {
        navigationView = mView.findViewById(R.id.mbMenu);
        rvNewItem = mView.findViewById(R.id.rvNewItem);
        rvPopularItem = mView.findViewById(R.id.rvPopularItem);
        rvSuggestedItem = mView.findViewById(R.id.rvSuggestedItem);
    }

    private void initRecViews() {
        newItemAdapter = new GroceryItemAdapter(getActivity());
        rvNewItem.setAdapter(newItemAdapter);
        rvNewItem.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        popularItemAdapter = new GroceryItemAdapter(getActivity());
        rvPopularItem.setAdapter(popularItemAdapter);
        rvPopularItem.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        suggestedItemAdapter = new GroceryItemAdapter(getActivity());
        rvSuggestedItem.setAdapter(suggestedItemAdapter);
        rvSuggestedItem.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        ArrayList<GroceryItem> newItems = Utils.getAllItems(getActivity());
        if (null != newItems) {
            Comparator<GroceryItem> newItemComparator = new Comparator<GroceryItem>() {
                @Override
                public int compare(GroceryItem o1, GroceryItem o2) {
                    return o1.getId() - o2.getId();
                }
            };

            Comparator<GroceryItem> reverseComparator = Collections.reverseOrder(newItemComparator);
            Collections.sort(newItems, reverseComparator);
            newItemAdapter.setItems(newItems);
        }

        ArrayList<GroceryItem> popularItem = Utils.getAllItems(getActivity());
        if (null != popularItem) {
            Comparator<GroceryItem> popularItemComparator = new Comparator<GroceryItem>() {
                @Override
                public int compare(GroceryItem groceryItem, GroceryItem t1) {
                    return groceryItem.getPopularPoint() - t1.getPopularPoint();
                }
            };
            Collections.sort(popularItem, Collections.reverseOrder(popularItemComparator));
            popularItemAdapter.setItems(popularItem);
        }

        ArrayList<GroceryItem> suggestedItem = Utils.getAllItems(getActivity());
        if (null != suggestedItem) {
            Comparator<GroceryItem> suggestedItemComparator = new Comparator<GroceryItem>() {
                @Override
                public int compare(GroceryItem groceryItem, GroceryItem t1) {
                    return groceryItem.getUserPoint() - t1.getUserPoint();
                }
            };
            Collections.sort(suggestedItem, Collections.reverseOrder(suggestedItemComparator));
            suggestedItemAdapter.setItems(suggestedItem);
        }
    }

    private void initButtonNav() {
        navigationView.setSelectedItemId(R.id.menuHome);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menuHome:
                        break;
                    case R.id.menuSearch:
                        Intent intent = new Intent(getActivity(), SearchActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case R.id.menuCart:
                        Intent intentCart = new Intent(getActivity(), CartActivity.class);
                        intentCart.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intentCart);
                        break;
                    default:
                        break;
                }

                return false;
            }
        });
    }
}