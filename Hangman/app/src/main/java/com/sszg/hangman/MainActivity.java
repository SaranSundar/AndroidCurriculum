package com.sszg.hangman;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Callable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.hangman)
    TextView hangman;
    @BindView(R.id.guess_letters)
    TextView lettersGuessed;
    @BindView(R.id.guess_word)
    TextView guessWord;
    @BindView(R.id.guess_edit)
    EditText guessLetter;
    @BindView(R.id.guess_button)
    Button guessButton;
    // ***CUSTOM LOGIC***
    private int lives = 6;
    private String answer;
    private ArrayList<String> wrongLetters;
    private ArrayList<String> rightLetters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        resetGame();
    }

    public void resetGame() {
        lives = 6;
        answer = readFoods(getAssets());
        wrongLetters = new ArrayList<>();
        rightLetters = new ArrayList<>();
        drawWord();
        System.out.println("ANSWER IS: " + answer);
    }

    @OnClick({R.id.guess_button})
    public void onClick(View v) {
        String guess = guessLetter.getText().toString();
        guessLetter.setText("");
        guess = guess.trim();
        guess = guess.toLowerCase();
        if (guess.length() > 1 || guess.isEmpty() || guess.equals(" ")) {
            alertView("Invalid", "Please enter only 1 letter a-z", this, null);
        } else {
            if (rightLetters.contains(guess) || wrongLetters.contains(guess)) {
                alertView("Invalid", "You already guessed " + guess, this, null);
            } else {
                if (answer.contains(guess)) {
                    rightLetters.add(guess);
                } else {
                    wrongLetters.add(guess);
                    lives--;
                }
                drawWord();
            }
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

    public void drawWord() {
        setPicture(lives);
        lettersGuessed.setText("");
        for (int i = 0; i < wrongLetters.size(); i++) {
            String s = wrongLetters.get(i);
            lettersGuessed.append(s);
            if (i != wrongLetters.size() - 1) {
                lettersGuessed.append(",");
            }
        }
        guessWord.setText("");
        for (int i = 0; i < answer.length(); i++) {
            String character = answer.substring(i, i + 1);
            if (rightLetters.contains(character)) {
                guessWord.append(character);
            } else {
                guessWord.append("_");
            }
            if (i != answer.length() - 1) {
                guessWord.append(" ");
            }
        }
        if (rightLetters.size() == answer.length()) {
            alertView("Game Over", "You Won!", this, new Callable<Void>() {
                @Override
                public Void call() {
                    resetGame();
                    return null;
                }
            });
        } else if (lives == 0) {
            alertView("Game Over", "You Lost!", this, new Callable<Void>() {
                @Override
                public Void call() {
                    resetGame();
                    return null;
                }
            });
        }
    }

    public void setPicture(int lives) {
        Resources res = getResources();
        String[] planets = res.getStringArray(R.array.hangman_array);
        hangman.setText(planets[lives]);
    }

    public String readFoods(AssetManager assetManager) {
        ArrayList<String> foods = new ArrayList<>();
        InputStream inputStream;
        try {
            inputStream = assetManager.open("HangmanWords.txt");
            Scanner kb = new Scanner(inputStream);
            while (kb.hasNext()) {
                foods.add(kb.nextLine());
            }
            inputStream.close();
            kb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return foods.get((new Random(new Date().getTime())).nextInt(foods.size()));
    }
}
