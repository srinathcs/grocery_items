package com.example.testone;

import static com.example.testone.AllCategoriesDialog.ALL_CATEGORIES;
import static com.example.testone.AllCategoriesDialog.CALLING_ACTIVITY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements AllCategoriesDialog.GetCategory {

    @Override
    public void onGetCategoryResult(String category) {
        ArrayList<GroceryItem> items = Utils.getItemByCategory(this, category);
        if (null != items) {
            adapter.setItems(items);
        }
    }

    private MaterialToolbar mtToolBar;
    private AppCompatTextView tvFirstCat, tvSecondCat, tvThirdCat, tvAllCat;
    private AppCompatEditText etSearch;
    private AppCompatImageView ivIcon;
    private RecyclerView recyclerView;
    private BottomNavigationView navigationView;
    private GroceryItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        initButtonNav();
        initWidget();
        setSupportActionBar(mtToolBar);
        adapter = new GroceryItemAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        Intent intent = getIntent();
        if (null != intent) {
            String category = intent.getStringExtra("category");
            if (null != category) {
                ArrayList<GroceryItem> items = Utils.getItemByCategory(this, category);
                if (null != items) {
                    adapter.setItems(items);
                    increaseUserPoint(items);
                }
            }
        }

    }

    private void initButtonNav() {
        navigationView.setSelectedItemId(R.id.menuSearch);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuHome:
                        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case R.id.menuSearch:

                        break;
                    case R.id.menuCart:
                        Intent intentCart = new Intent(SearchActivity.this, CartActivity.class);
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

    private void initWidget() {
        ivIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initSearch();
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                initSearch();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ArrayList<String> categories = Utils.getCategories(this);
        if (null != categories) {
            if (categories.size() > 0) {
                if (categories.size() == 1) {
                    showCategories(categories, 1);
                } else if (categories.size() == 2) {
                    showCategories(categories, 2);
                } else {
                    showCategories(categories, 3);
                }
            }
        }

        tvAllCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllCategoriesDialog dialog = new AllCategoriesDialog();
                Bundle bundle = new Bundle();
                bundle.putStringArrayList(ALL_CATEGORIES, Utils.getCategories(SearchActivity.this));
                bundle.putString(CALLING_ACTIVITY, "search_activity");
                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(), "all category dialog");
            }
        });

    }

    private void increaseUserPoint(ArrayList<GroceryItem> items){
        for (GroceryItem i: items){
            Utils.changeUserPoint(this,i,1);
        }
    }

    private void showCategories(ArrayList<String> categories, int i) {
        switch (i) {
            case 1:
                tvFirstCat.setVisibility(View.VISIBLE);
                tvFirstCat.setText(categories.get(0));
                tvSecondCat.setVisibility(View.GONE);
                tvThirdCat.setVisibility(View.GONE);
                tvFirstCat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<GroceryItem> items = Utils.getItemByCategory(SearchActivity.this, categories.get(0));
                        if (null != items) {
                            adapter.setItems(items);
                            increaseUserPoint(items);
                        }
                    }
                });
                break;
            case 2:
                tvFirstCat.setVisibility(View.VISIBLE);
                tvFirstCat.setText(categories.get(0));
                tvSecondCat.setVisibility(View.VISIBLE);
                tvSecondCat.setText(categories.get(1));
                tvThirdCat.setVisibility(View.GONE);
                tvFirstCat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<GroceryItem> items = Utils.getItemByCategory(SearchActivity.this, categories.get(0));
                        if (null != items) {
                            adapter.setItems(items);
                            increaseUserPoint(items);
                        }
                    }
                });
                tvSecondCat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<GroceryItem> items = Utils.getItemByCategory(SearchActivity.this, categories.get(1));
                        if (null != items) {
                            adapter.setItems(items);
                            increaseUserPoint(items);
                        }
                    }
                });
                break;
            case 3:
                tvFirstCat.setVisibility(View.VISIBLE);
                tvFirstCat.setText(categories.get(0));
                tvSecondCat.setVisibility(View.VISIBLE);
                tvSecondCat.setText(categories.get(1));
                tvThirdCat.setVisibility(View.VISIBLE);
                tvThirdCat.setText(categories.get(2));
                tvFirstCat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<GroceryItem> items = Utils.getItemByCategory(SearchActivity.this, categories.get(0));
                        if (null != items) {
                            adapter.setItems(items);
                            increaseUserPoint(items);
                        }
                    }
                });
                tvSecondCat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<GroceryItem> items = Utils.getItemByCategory(SearchActivity.this, categories.get(1));
                        if (null != items) {
                            adapter.setItems(items);
                        }
                    }
                });
                tvThirdCat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<GroceryItem> items = Utils.getItemByCategory(SearchActivity.this, categories.get(2));
                        if (null != items) {
                            adapter.setItems(items);
                        }
                    }
                });
                break;
            default:
                tvFirstCat.setVisibility(View.GONE);
                tvSecondCat.setVisibility(View.GONE);
                tvThirdCat.setVisibility(View.GONE);
                break;
        }
    }

    private void initView() {
        mtToolBar = findViewById(R.id.mtToolbar);
        tvFirstCat = findViewById(R.id.tvFirstCat);
        tvSecondCat = findViewById(R.id.tvSecondCat);
        tvThirdCat = findViewById(R.id.tvThirdCat);
        tvAllCat = findViewById(R.id.tvAllCat);
        etSearch = findViewById(R.id.etSearch);
        ivIcon = findViewById(R.id.ivSearch);
        recyclerView = findViewById(R.id.rvList);
        navigationView = findViewById(R.id.mbMenu);
    }

    private void initSearch() {
        if (!etSearch.getText().toString().equals("")) {
            String name = etSearch.getText().toString();
            ArrayList<GroceryItem> items = Utils.searchForItems(this, name);
            if (null != items) {
                adapter.setItems(items);
                increaseUserPoint(items);
            }
        }
    }
}