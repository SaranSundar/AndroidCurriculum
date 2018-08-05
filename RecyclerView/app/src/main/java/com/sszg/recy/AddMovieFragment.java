package com.sszg.recy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddMovieFragment extends DialogFragment {

    private EditText movieTitle, movieYear;
    private ConstraintLayout genreLayout;
    private Button addMovieBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_movie, container, false);
        movieTitle = view.findViewById(R.id.movie_title_edit);
        movieYear = view.findViewById(R.id.movie_year_edit);
        genreLayout = view.findViewById(R.id.movie_genre_layout);
        addMovieBtn = view.findViewById(R.id.add_movie_btn);
        addMovieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMovie(view);
            }
        });
        return view;
    }

    public void addMovie(View view) {
        Toast.makeText(getContext(), "HELLO", Toast.LENGTH_SHORT).show();
        dismiss();
    }
}
