package com.sszg.doordash;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private PagerAdapter pagerAdapter;
    private ViewPager container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar("Door Dash");
        setupFragments();
    }


    public void setupFragments() {
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), 1);
        container = findViewById(R.id.container);
        container.setAdapter(pagerAdapter);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.food_page:
                                switchPages(0);
                                break;

                            case R.id.drink_page:
                                switchPages(1);
                                break;

                            case R.id.orders_page:
                                switchPages(2);
                                break;

                        }
                        return true;
                    }
                });
    }

    public void switchPages(int i) {
        container.setCurrentItem(i);
    }

    public void setupToolbar(String title) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
    }
}
