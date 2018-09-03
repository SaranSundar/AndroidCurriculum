package com.sszg.chinesenovelreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements GridItemAdapter.ItemClickListener {

    private RecyclerView recyclerView;
    private GridItemAdapter gridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupGrid();
        populateGrid();
        //WuxiaHelpers wuxiaHelpers = new WuxiaHelpers();
        //wuxiaHelpers.getLatestChapters(this);
    }


    public void populateGrid() {
        for (int i = 0; i < 10; i++) {
            Book book1 = new Book("", "Hidden Marriage");
            Book book2 = new Book("", "Paradise Land");
            Book book3 = new Book("", "Tree Farms & Fun");
            gridAdapter.addBook(book1);
            gridAdapter.addBook(book2);
            gridAdapter.addBook(book3);
        }
        gridAdapter.notifyDataSetChanged();
    }

    public void setupGrid() {
        recyclerView = findViewById(R.id.recycler_view);
        int numOfColumns = 3;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numOfColumns));
        gridAdapter = new GridItemAdapter();
        gridAdapter.setClickListener(this);
        recyclerView.setAdapter(gridAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Book book = gridAdapter.getBook(position);
        Toast.makeText(this, "Title: " + book.getTitle(), Toast.LENGTH_SHORT).show();
        Intent bookReader = new Intent(this, BookReaderActivity.class);
        startActivity(bookReader);

    }
}
