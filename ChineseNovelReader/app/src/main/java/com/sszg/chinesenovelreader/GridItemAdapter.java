package com.sszg.chinesenovelreader;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GridItemAdapter {
    private ArrayList<Book> books;


    public class GridViewHolder extends RecyclerView.ViewHolder {
        private ImageView coverPicture;
        private CheckBox checkBox;
        private TextView title, chaptersUnread;

        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
