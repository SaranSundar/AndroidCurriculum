package com.sszg.doordash;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {
    private int pages;

    public PagerAdapter(FragmentManager fm, int pages) {
        super(fm);
        this.pages = pages;
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            return FoodFragment.newInstance(i);
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return pages;
    }
}
