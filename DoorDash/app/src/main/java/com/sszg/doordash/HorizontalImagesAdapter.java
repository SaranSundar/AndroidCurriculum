package com.sszg.doordash;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class HorizontalImagesAdapter extends RecyclerView.Adapter<HorizontalImagesAdapter.ImagesViewHolder> {

    private ArrayList<String> imageNames;
    private Context context;

    HorizontalImagesAdapter() {
        imageNames = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            imageNames.add("breakfast" + i);
        }
    }

    @NonNull
    @Override
    public ImagesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View rowView = LayoutInflater.from(context).inflate(R.layout.horizontal_image_layout, viewGroup, false);
        return new ImagesViewHolder(rowView);
    }

    private static Drawable getImage(Context c, String ImageName) {
        return c.getResources().getDrawable(c.getResources().getIdentifier(ImageName, "drawable", c.getPackageName()));
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesViewHolder imagesViewHolder, int i) {
        imagesViewHolder.foodImage.setImageDrawable(getImage(context, imageNames.get(i)));
    }

    @Override
    public int getItemCount() {
        return imageNames.size();
    }

    class ImagesViewHolder extends RecyclerView.ViewHolder {
        private ImageView foodImage;

        ImagesViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.food_image);
        }
    }
}
