package com.example.testone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CartActivity extends AppCompatActivity {
    private MaterialToolbar mtTool;
    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        initView();
        initButtonNav();
        setSupportActionBar(mtTool);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flLayout, new FirstCartFragment());
        transaction.commit();

    }

    private void initView() {
        mtTool = findViewById(R.id.mtToolbar);
        navigationView = findViewById(R.id.bnvView);
    }

    private void initButtonNav() {
        navigationView.setSelectedItemId(R.id.menuCart);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menuHome:
                        Intent intentHome = new Intent(CartActivity.this,MainActivity.class);
                        intentHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intentHome);
                        break;
                    case R.id.menuSearch:
                        Intent intent = new Intent(CartActivity.this, SearchActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case R.id.menuCart:
                        break;
                    default:
                        break;
                }

                return false;
            }
        });
    }
}