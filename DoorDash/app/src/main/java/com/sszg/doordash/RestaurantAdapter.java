package com.sszg.doordash;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import static android.widget.LinearLayout.HORIZONTAL;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private ArrayList<Restaurant> restaurants;
    private Context context;
    private HorizontalImagesAdapter horizontalImagesAdapter;
    private RecyclerViewClickListener mListener;

    public interface RecyclerViewClickListener {

        void onClick(RestaurantViewHolder view, int position);
    }

    RestaurantAdapter(RecyclerViewClickListener listener) {
        restaurants = new ArrayList<>();
        mListener = listener;
    }

    public void addRestaurant(Restaurant restaurant) {
        restaurants.add(restaurant);
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View rowView = LayoutInflater.from(context).inflate(R.layout.row_design, viewGroup, false);
        return new RestaurantViewHolder(rowView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder restaurantViewHolder, int i) {
        Restaurant restaurant = restaurants.get(i);
        restaurantViewHolder.name.setText(restaurant.getName());
        String genreField = restaurant.getGenre() + " - " + restaurant.getMoney();
        restaurantViewHolder.genre.setText(genreField);
        String distanceField = restaurant.getDistance() + " min";
        restaurantViewHolder.distance.setText(distanceField);
        String ratingField = restaurant.getStars() + " Stars - " + restaurant.getReviews() + " Reviews";
        restaurantViewHolder.rating.setText(ratingField);
        String deliveryFeeField = restaurant.getFee();
        restaurantViewHolder.fee.setText(deliveryFeeField);
        horizontalImagesAdapter = new HorizontalImagesAdapter();
        restaurantViewHolder.horizontalImages.setAdapter(horizontalImagesAdapter);
        initializeRecyclerView(restaurantViewHolder.horizontalImages, context);
    }

    private void initializeRecyclerView(RecyclerView recyclerView, Context context) {
        //SETUP DISPLAY FOR RECYCLE VIEW
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(context, HORIZONTAL));  //getActivity
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    class RestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RecyclerView horizontalImages;
        private TextView name, genre, distance, rating, fee;
        private RecyclerViewClickListener mListener;

        RestaurantViewHolder(@NonNull View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            mListener = listener;
            horizontalImages = itemView.findViewById(R.id.horizontal_images);
            name = itemView.findViewById(R.id.name);
            genre = itemView.findViewById(R.id.genre);
            distance = itemView.findViewById(R.id.distance);
            rating = itemView.findViewById(R.id.rating);
            fee = itemView.findViewById(R.id.fee);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                mListener.onClick(this, getAdapterPosition());
            }
        }


        public RecyclerView getHorizontalImages() {
            return horizontalImages;
        }

        public TextView getName() {
            return name;
        }

        public TextView getGenre() {
            return genre;
        }

        public TextView getDistance() {
            return distance;
        }

        public TextView getRating() {
            return rating;
        }

        public TextView getFee() {
            return fee;
        }
    }
}
