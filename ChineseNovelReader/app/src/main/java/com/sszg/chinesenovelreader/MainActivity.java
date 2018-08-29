package com.sszg.chinesenovelreader;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements GridItemAdapter.ItemClickListener {

    private RecyclerView recyclerView;
    private GridItemAdapter gridAdapter;
    private static ProgressDialog progressDialog;
    private static String wuxiaWorldURL = "https://www.wuxiaworld.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupGrid();
        populateGrid();
        wuxiaWorldURL += "novel/a-will-eternal/awe-chapter-1";
    }

    private class WuxiaWorld extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Wuxia World");
            progressDialog.setMessage("Loading Book Catalog...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document page = Jsoup.connect(wuxiaWorldURL).get();
//                Elements table = page.select("table[class=table table-novels]");
//                Elements titles = table.select("span[class=title]");
//                for (Element e : titles) {
//                    System.out.println(e.text());
//                }
                Elements story = page.select("div[class=fr-view]");
                String chp1 = story.text();
                System.out.println(chp1);
                System.out.println(story.text());

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
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
        new WuxiaWorld().execute();
    }
}
