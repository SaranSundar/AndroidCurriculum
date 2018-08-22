package com.sszg.youtube;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EmptyFragment extends Fragment {
    private static String ARG_SECTION_NUMBER = "section_number";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.empty_layout, container, false);
        return view;
    }

    public static EmptyFragment newInstance(int sectionNumber) {
        EmptyFragment emptyFragment = new EmptyFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        emptyFragment.setArguments(args);
        return emptyFragment;
    }
}
