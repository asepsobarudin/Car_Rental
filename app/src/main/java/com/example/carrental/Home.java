package com.example.carrental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        bottomNavigationView = findViewById(R.id.bottomNavigate);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        String fragmentName = getIntent().getStringExtra("fragment");

        new Handler().postDelayed(() -> {
            if (fragmentName != null) {
                if (fragmentName.equals("page_home")) {
                    loadFragment(new PageHome());
                } else if (fragmentName.equals("page_order")) {
                    loadFragment(new PageOrder());
                } else if (fragmentName.equals("page_user")) {
                    loadFragment(new PageUser());
                }
            } else {
                loadFragment(new PageHome());
            }
        }, 1000);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.home:
                fragment = new PageHome();
                break;
            case R.id.order:
                fragment = new PageOrder();
                break;
            case R.id.user:
                fragment = new PageUser();
                break;
            default:
                fragment = new BlankFragment();
                break;
        }
        if (fragment != null) {
            loadFragment(fragment);
            return true;
        }
        return false;
    }

    void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.contentView, fragment).commit();
    }
}
