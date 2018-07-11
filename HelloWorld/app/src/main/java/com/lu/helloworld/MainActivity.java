package com.lu.helloworld;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ImageView clickMe;
    private TextView counterTextView;
    private int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Log.i("STARTING", "Launched app");

        clickMe = findViewById(R.id.cookieImage);
        counterTextView = findViewById(R.id.counter);

        clickMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ObjectAnimator animY = ObjectAnimator.ofFloat(view, "translationY", 100f, 0f);
                animY.setDuration(700);//1sec
                animY.setInterpolator(new BounceInterpolator());
                animY.start();

                counter++;
                Log.i("BUTTON", "clicked the button " + counter);
                String text = "Cookie count is: " + counter;
                counterTextView.setText(text);
            }
        });


    }
}
