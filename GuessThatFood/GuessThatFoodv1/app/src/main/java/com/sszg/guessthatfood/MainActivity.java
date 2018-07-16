package com.sszg.guessthatfood;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> foods;
    private ArrayList<String> chosen;
    private TextView scoreTV;
    private int score;
    private int level;
    private ImageView picture;
    private static final int HTTP_REQUEST_TIMEOUT = 3 * 600000;
    private Button option1, option2, option3, option4, hint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scoreTV = findViewById(R.id.score);
        picture = findViewById(R.id.picture);
        resetGame();
        //getWebsite();
        runNetworkTask();
    }

    public void initializeButtons() {
        hint = findViewById(R.id.hint);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        setClickListener(option1, option2, option3, option4, hint);
    }

    public void setImageView(ImageView imageView, String imageName) {
        imageName = imageName.toLowerCase();
        int imageId = getResourseId(this, imageName, "drawable", getPackageName());
        imageView.setImageResource(imageId);
        imageView.setTag(imageName);
    }

    public void updateLevel() {
        String levelText = "" + level + "/" + foods.size();
        scoreTV.setText(levelText);
    }

    public void checkAnswer(String text, Button[] buttons) {
        if (picture.getTag().equals(text.toLowerCase())) {
            Toast.makeText(getApplicationContext(), "Good Job!", Toast.LENGTH_SHORT).show();
            score++;
        } else {
            Toast.makeText(getApplicationContext(), "Wrong!", Toast.LENGTH_SHORT).show();
        }
        level++;
        if (level == foods.size() + 1) {
            alertView("You score is " + score + "/" + (level - 1));
        } else {
            chooseRandomFood(buttons);
            updateLevel();
        }
    }

    private void alertView(String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Game Over")
                .setIcon(R.drawable.ic_launcher_foreground)
                .setMessage(message)
                .setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        dialoginterface.cancel();
                        resetGame();
                    }
                }).show();
    }

    public void resetGame() {
        score = 0;
        initializeFoods();
        initializeButtons();
        level = 1;
        updateLevel();
    }

    public static int getResourseId(Context context, String pVariableName, String pResourcename, String pPackageName) throws RuntimeException {
        try {
            return context.getResources().getIdentifier(pVariableName, pResourcename, pPackageName);
        } catch (Exception e) {
            throw new RuntimeException("Error getting Resource ID.", e);
        }
    }

    public void setClickListener(final Button... buttons) {
        chooseRandomFood(buttons);
        for (int i = 0; i < buttons.length; i++) {
            Button b = buttons[i];
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String text = ((Button) view).getText().toString();
                    if (!view.getTag().equals("5")) {
                        //Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                        checkAnswer(text, buttons);
                    } else {
                        Toast.makeText(getApplicationContext(), "Hint is...", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void chooseRandomFood(Button[] buttons) {
        // Remove previous chosen foods
        ArrayList<String> diff = new ArrayList<>(foods);
        diff.removeAll(chosen);
        // Select random food from remaining list
        Random random = new Random(new Date().getTime());
        int index = random.nextInt(diff.size());
        String name = diff.get(index);
        setImageView(picture, name);
        // Add food to chosen list
        chosen.add(name);

        // Choose new button food options
        index = random.nextInt(4);
        setButtonTexts(buttons, name, index, random);
    }

    public void setButtonTexts(Button[] buttons, String name, int index, Random random) {
        ArrayList<String> temp = new ArrayList<>(foods);
        temp.remove(name);
        for (int i = 0; i < buttons.length; i++) {
            if (!buttons[i].getTag().equals("5")) {
                if (i == index) {
                    buttons[i].setText(name);
                } else {
                    int ind = random.nextInt(temp.size());
                    buttons[i].setText(temp.get(ind));
                    temp.remove(ind);
                }
            }
        }
    }

    public void runNetworkTask() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (String f : foods) {
                    List<Result> results = new ArrayList<>();

                    try {
                        results = search(f);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (results != null) {
                        for (Result result : results) {
                            System.out.println(result.getLink());
                        }
                    }
                }
            }
        }).start();
    }

    public List<Result> search(String keyword) {
        Customsearch customsearch = null;


        try {
            customsearch = new Customsearch(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
                public void initialize(HttpRequest httpRequest) {
                    try {
                        // set connect and read timeouts
                        httpRequest.setConnectTimeout(HTTP_REQUEST_TIMEOUT);
                        httpRequest.setReadTimeout(HTTP_REQUEST_TIMEOUT);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Result> resultList = null;
        try {
            Customsearch.Cse.List list;
            if (customsearch != null) {
                list = customsearch.cse().list(keyword);
                list.setKey("AIzaSyB058366Q83_r2QtNskYAtmOhTbDyOTWt4");
                list.setCx("002740584417081531455:fnr_tspgkay");
                list.setSearchType("image");
                list.setNum(1L);
                Search results = list.execute();
                resultList = results.getItems();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public String getURL(String query) {
        return "https://www.google.com/search?q=" + query + "&source=lnms&tbm=isch";
    }

    private void getWebsite() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String searchURL = getURL(foods.get(0)) + "&num=" + 3;
                    //without proper User-Agent, we will get 403 error
                    Document doc = Jsoup.connect(searchURL).userAgent("Mozilla/5.0").get();

                    //Get all elements with img tag ,

                    Elements img = doc.getElementsByTag("img");

                    for (Element el : img) {

                        String src = el.absUrl("src");


                        System.out.println("Image Found!");

                        System.out.println("src attribute is : " + src);


                        //getImages(src);


                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //result.setText(builder.toString());
                        }
                    });
                } catch (Exception ignore) {
                }
            }
        }).start();
    }

    public void initializeFoods() {
        if (foods != null) {
            foods.clear();
            chosen.clear();
        } else {
            foods = new ArrayList<>();
            chosen = new ArrayList<>();
        }
        AssetManager assetManager = getAssets();
        InputStream inputStream;
        try {
            inputStream = assetManager.open("Foods.txt");
            Scanner kb = new Scanner(inputStream);
            while (kb.hasNext()) {
                foods.add(kb.nextLine());
            }
            inputStream.close();
            kb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
