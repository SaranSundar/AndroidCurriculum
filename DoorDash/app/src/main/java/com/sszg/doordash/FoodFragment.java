package com.sszg.doordash;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import static android.widget.LinearLayout.VERTICAL;

public class FoodFragment extends Fragment implements RestaurantAdapter.RecyclerViewClickListener {
    private static String ARG_SECTION_NUMBER = "section_number";
    private RecyclerView recyclerView;
    private RestaurantAdapter restaurantAdapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.food_fragment, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        restaurantAdapter = new RestaurantAdapter(this);
        recyclerView.setAdapter(restaurantAdapter);
        //SETUP DISPLAY FOR RECYCLE VIEW
        initializeRecyclerView(recyclerView, container);
        initializeRestaurants(container);
        return view;
    }

    public void initializeRestaurants(ViewGroup container) {
        AssetManager assetManager = container.getContext().getAssets();
        InputStream inputStream;
        try {
            inputStream = assetManager.open("Restaurants");
            Scanner kb = new Scanner(inputStream);
            //Skip intro line
            kb.nextLine();
            while (kb.hasNext()) {
                Restaurant restaurant = new Restaurant(kb.nextLine(), kb.nextLine(), kb.nextLine(), kb.nextLine(), kb.nextLine(), kb.nextLine(), kb.nextLine());
                restaurantAdapter.addRestaurant(restaurant);
                kb.nextLine();
            }
            inputStream.close();
            kb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        restaurantAdapter.notifyDataSetChanged();
    }

    public void initializeRecyclerView(RecyclerView recyclerView, ViewGroup container) {
        //SETUP DISPLAY FOR RECYCLE VIEW
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(container.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(container.getContext(), VERTICAL));  //getActivity
    }

    public static FoodFragment newInstance(int sectionNumber) {
        FoodFragment foodFragment = new FoodFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        foodFragment.setArguments(args);
        return foodFragment;
    }

    @Override
    public void onClick(RestaurantAdapter.RestaurantViewHolder view, int position) {
        Toast.makeText(getContext(), "Position " + position + " Name is " + view.getName().getText().toString(), Toast.LENGTH_SHORT).show();
    }
}
