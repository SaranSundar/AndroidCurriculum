package com.sszg.recy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class FakeFragment extends DialogFragment {
    private EditText title, year;
    private Button save, cancel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_movie, container, false);
        title = view.findViewById(R.id.movie_title);
        year = view.findViewById(R.id.movie_year);
        save = view.findViewById(R.id.save);
        cancel = view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMovie();
            }
        });
        return view;
    }

    public void addMovie(){
        String title = this.title.getText().toString();
        String year = this.year.getText().toString();
        String genre = "";

    }
}
