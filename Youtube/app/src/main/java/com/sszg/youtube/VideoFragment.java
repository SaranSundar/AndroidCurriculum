package com.sszg.youtube;

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

import com.sszg.youtube.RecyclerView.RecyclerAdapter;
import com.sszg.youtube.RecyclerView.Video;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import static android.widget.LinearLayout.VERTICAL;

public class VideoFragment extends Fragment {
    private static String ARG_SECTION_NUMBER = "section_number";
    private RecyclerView recyclerView;
    private RecyclerAdapter videosAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_fragment, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        videosAdapter = new RecyclerAdapter();
        recyclerView.setAdapter(videosAdapter);
        //SETUP DISPLAY FOR RECYCLE VIEW
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(container.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(container.getContext(), VERTICAL));  //getActivity
        initializeVideos(container);
        return view;
    }


    public void initializeVideos(ViewGroup container) {
        AssetManager assetManager = container.getContext().getAssets();
        InputStream inputStream;
        try {
            inputStream = assetManager.open("urls.txt");
            Scanner kb = new Scanner(inputStream);
            kb.nextLine();
            while (kb.hasNext()) {
                String imageURL = kb.nextLine();
                String videoURL = kb.nextLine();
                String authorURL = kb.nextLine();
                String authorTitle = kb.nextLine();
                String videoTitle = kb.nextLine();
                Video video = new Video(imageURL, videoURL, authorURL, authorTitle, videoTitle);
                videosAdapter.addVideo(video);
                kb.nextLine();
            }
            inputStream.close();
            kb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        videosAdapter.notifyDataSetChanged();
    }

    // TO BE CALLED FROM ANOTHER CLASS
    public static VideoFragment newInstance(int sectionNumber) {
        VideoFragment videoFragment = new VideoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        videoFragment.setArguments(args);
        return videoFragment;
    }
}
