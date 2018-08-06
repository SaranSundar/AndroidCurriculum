package com.sszg.recy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class FakeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieAdapter2 moviesAdapter;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // SETUP TOOLBAR
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Movie List");
        setSupportActionBar(toolbar);
        setupRecyclerView();
        populateList();
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                DialogFragment dialogFragment = new AddMovieFragment();
                dialogFragment.setCancelable(true);
                dialogFragment.show(ft, "dialog");
            }
        });
    }

    public void populateList() {
        for (int i = 0; i < 25; i++) {
            moviesAdapter.addMovie("Superman", "Action", "2013");
            moviesAdapter.addMovie("Iron Man", "Adventure & Action", "2011");
        }
        moviesAdapter.notifyDataSetChanged();
    }

    public void setupRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        moviesAdapter = new MovieAdapter2();
        recyclerView.setAdapter(moviesAdapter);
        // **** SETUP DISPLAY FOR RECYCLER VIEW ****
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fab.getVisibility() == View.VISIBLE) {
                    fab.hide();
                } else if (dy < 0 && fab.getVisibility() != View.VISIBLE) {
                    fab.show();
                }
            }
        });
    }
}
