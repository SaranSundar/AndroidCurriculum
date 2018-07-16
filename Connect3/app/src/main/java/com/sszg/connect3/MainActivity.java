package com.sszg.connect3;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private GridLayout board;
    private int playerR = 1;
    private int size = 9;
    private int winC = 3;
    private float width;
    private float height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        width = displayMetrics.widthPixels / displayMetrics.density;
        height = displayMetrics.heightPixels / displayMetrics.density;
        board = findViewById(R.id.grid);
        //board.setBackgroundColor(Color.BLACK);
        for (int i = 0; i < size; i++) {
            fillGrid(i);
        }
    }

    public void fillGrid(int index) {
        final ImageView icon = new ImageView(this);
        icon.setImageResource(R.drawable.red);
        int imageDimension = (int) (width - 90) / 3;//where 3 is the column count
        final float scale = this.getResources().getDisplayMetrics().density;
        int pixels = (int) (imageDimension * scale + 0.5f);
        icon.setTag(-1);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(pixels, pixels);
        int mDPR = (int) (imageDimension / 18 * scale + 0.5f);
        int div = (int) (16 * scale) - 42;
        int mDPD = (int) (imageDimension / div * scale + 0.5f); // Should be / 6 on smaller screen
        lp.setMargins(mDPR, mDPD, mDPR, mDPD);
        icon.setLayoutParams(lp);
        icon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        //setClick(icon);
        board.addView(icon, index);
    }

    public int checkWin(int val) {
        int[][] arr = new int[size / 3][size / 3];
        for (int i = 0; i < size; i++) {
            int r = i / 3;
            int c = i % 3;
            arr[r][c] = (int) board.getChildAt(i).getTag();
        }
        int fill = 0;
        for (int i = 0; i < size; i++) {
            int r = i / 3;
            int c = i % 3;
            if (checkRow(arr, r, c, val) || checkCol(arr, r, c, val) ||
                    checkDiagonalLR(arr, r, c, val) || checkDiagonalRL(arr, r, c, val)) {
                return 1;
            }
            if (arr[r][c] != -1) {
                fill++;
            }
        }
        if (fill >= size) {
            return 2;
        }
        return 0;
    }

    public boolean checkDiagonalRL(int[][] arr, int row, int col, int val) {
        boolean found = false;
        int count = 0;
        int c = arr[0].length - 1;
        for (int r = 0; r < arr.length; r++) {
            if (arr[r][c] == val) {
                found = true;
                count++;
            } else if (found) {
                break;
            }
            c--;
        }
        return count >= winC;
    }

    public boolean checkDiagonalLR(int[][] arr, int row, int col, int val) {
        boolean found = false;
        int count = 0;
        int c = 0;
        for (int r = 0; r < arr.length; r++) {
            if (arr[r][c] == val) {
                found = true;
                count++;
            } else if (found) {
                break;
            }
            c++;
        }
        return count >= winC;
    }

    public boolean checkCol(int[][] arr, int row, int col, int val) {
        boolean found = false;
        int count = 0;
        for (int r = 0; r < arr.length; r++) {
            if (arr[r][col] == val) {
                found = true;
                count++;
            } else if (found) {
                break;
            }
        }
        return count >= winC;
    }

    public boolean checkRow(int[][] arr, int row, int col, int val) {
        boolean found = false;
        int count = 0;
        for (int c = 0; c < arr[0].length; c++) {
            if (arr[row][c] == val) {
                found = true;
                count++;
            } else if (found) {
                break;
            }
        }
        return count >= winC;
    }

    public void clearGrid() {
        playerR = 1;
        for (int i = 0; i < size; i++) {
            ((ImageView) board.getChildAt(i)).setImageDrawable(null);
            board.getChildAt(i).setTag(-1);
            board.getChildAt(i).setEnabled(true);
        }
    }

    public void setClick(final ImageView icon) {
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View o_view) {
                ImageView view = (ImageView) o_view;
                view.setTranslationY(-1000f);
                int prevR = playerR;
                if (playerR == 1) {
                    view.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.red, null));
                    view.setTag(playerR);
                    playerR = 0;
                } else {
                    view.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.yellow, null));
                    view.setTag(playerR);
                    playerR = 1;
                }
                view.animate().translationYBy(1000f).setDuration(300);
                icon.setEnabled(false);
                int win = checkWin(prevR);
                if (win == 1) {
                    displayDialog("GAME OVER", "PLAYER " + prevR + " WON!", "PLAY AGAIN");
                } else if (win == 2) {
                    displayDialog("GAME OVER", "DRAW", "PLAY AGAIN");
                }
            }
        });
    }

    public void displayDialog(String title, String message, String buttonM) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, buttonM,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        clearGrid();
                    }
                });
        alertDialog.show();
    }
}
