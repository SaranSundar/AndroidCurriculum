package com.sszg.recy;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.Callable;

public class AddMovieFragment extends DialogFragment {

    private EditText movieTitle, movieYear;
    private ConstraintLayout genreLayout;
    private Button addMovieBtn, cancel;
    private CallbackToActivity callbackToActivity;

    public interface CallbackToActivity {
        void addMovieToList(String title, String year, String genre);
    }

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
        cancel = view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return view;
    }

    public void addMovie(View view) {
        Toast.makeText(getContext(), "HELLO", Toast.LENGTH_SHORT).show();
        String title = movieTitle.getText().toString();
        String year = movieYear.getText().toString();
        StringBuilder genre = new StringBuilder();
        for (int i = 0; i < genreLayout.getChildCount(); i++) {
            if (genreLayout.getChildAt(i) instanceof CheckBox) {
                CheckBox checkBox = ((CheckBox) genreLayout.getChildAt(i));
                if (checkBox.isChecked()) {
                    genre.append(checkBox.getText().toString());
                    genre.append(" & ");
                }
            }
        }
        if (genre.length() >= 2 && genre.charAt(genre.length() - 2) == '&'){
            genre.delete(genre.length() - 2, genre.length());
        }
        if (title.isEmpty() || year.isEmpty() || genre.toString().isEmpty() || year.length() != 4) {
            alertView("Error", "Please fill in all fields", getContext(), null);
        } else {
            callbackToActivity = (CallbackToActivity) getActivity();
            callbackToActivity.addMovieToList(title, year, genre.toString());
            dismiss();
        }
    }

    private void alertView(String title, String message, Context context, final Callable<Void> resetGame) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(title)
                .setIcon(R.drawable.ic_launcher_foreground)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Try Again!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        dialoginterface.cancel();
                        if (resetGame != null) {
                            try {
                                resetGame.call();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).show();
    }
}
