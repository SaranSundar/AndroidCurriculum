package com.sszg.chinesenovelreader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

public class BookReaderActivity extends AppCompatActivity {

    private RelativeLayout bookSettings;
    private LinearLayout chapterBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_reader);
        chapterBody = findViewById(R.id.chapter_body);
        bookSettings = findViewById(R.id.book_settings);
    }

    public void clickScreen(View v) {
        Toast.makeText(this, "WORKING", Toast.LENGTH_SHORT).show();
        if (bookSettings.getVisibility() == View.INVISIBLE) {
            bookSettings.setVisibility(View.VISIBLE);
        } else {
            bookSettings.setVisibility(View.INVISIBLE);
        }
    }
}
