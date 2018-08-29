package com.sszg.chinesenovelreader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GridItemAdapter extends RecyclerView.Adapter<GridItemAdapter.GridViewHolder> {
    private ArrayList<Book> books;
    private Context context;
    private ItemClickListener mClickListener;

    public GridItemAdapter() {
        books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public Book getBook(int i) {
        return books.get(i);
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View rowView = LayoutInflater.from(context).inflate(R.layout.grid_item, viewGroup, false);
        return new GridViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder gridViewHolder, int i) {
        if (i % 2 == 0) {
            gridViewHolder.coverPicture.setImageResource(R.drawable.hidden_marriage);
        } else {
            gridViewHolder.coverPicture.setImageResource(R.drawable.ghost_emperor);
        }
        gridViewHolder.title.setText(books.get(i).getTitle());
        String chpUn = i + " unread";
        gridViewHolder.chaptersUnread.setText(chpUn);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }


    public class GridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView coverPicture;
        private CheckBox checkBox;
        private TextView title, chaptersUnread;

        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            coverPicture = itemView.findViewById(R.id.cover_picture);
            checkBox = itemView.findViewById(R.id.check_box);
            title = itemView.findViewById(R.id.title);
            chaptersUnread = itemView.findViewById(R.id.chapters_unread);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }
}
