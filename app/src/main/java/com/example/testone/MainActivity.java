package com.example.testone;

import static com.example.testone.AllCategoriesDialog.ALL_CATEGORIES;
import static com.example.testone.AllCategoriesDialog.CALLING_ACTIVITY;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initWidgets();
        Utils.initSharedPreference(this);


        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction;
        transaction = manager.beginTransaction();
        transaction.replace(R.id.frameContainer, new MainFragment());
        transaction.commit();
    }


    private void initView() {
        drawer = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.mtToolbar);
    }

    private void initWidgets() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, (R.string.drawer_open), (R.string.drawer_close));
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuCart:
                        Intent cartIntent = new Intent(MainActivity.this, CartActivity.class);
                        cartIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(cartIntent);
                        break;
                    case R.id.menuCategories:
                        AllCategoriesDialog dialog = new AllCategoriesDialog();
                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList(ALL_CATEGORIES, Utils.getCategories(MainActivity.this));
                        bundle.putString(CALLING_ACTIVITY, "main_activity");
                        dialog.setArguments(bundle);
                        dialog.show(getSupportFragmentManager(), "all category dialog");
                        break;
                    case R.id.menuAbout:
                        showDialog("About Us", "Designed and Developed by Srinath", "Visit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent website = new Intent(MainActivity.this, WebsiteActivity.class);
                                startActivity(website);
                            }
                        });
                        break;
                    case R.id.menuTerms:
                        showDialog("Terms", "There are no terms, Enjoy using the application :)", "Dismiss", null);
                        break;
                    case R.id.menuLicences:
                        LicencesDialog licencesDialog = new LicencesDialog();
                        licencesDialog.show(getSupportFragmentManager(), "licences");
                    default:
                        break;
                }
                return false;
            }
        });
    }

    private void showDialog(String title, String message, String positiveButtonText, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButtonText, listener).create().show();
    }
}
