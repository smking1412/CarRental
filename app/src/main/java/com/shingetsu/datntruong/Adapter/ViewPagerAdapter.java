package com.shingetsu.datntruong.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> arrFragment = new ArrayList<>();
    ArrayList<String> arrTitle = new ArrayList<>();

    public ViewPagerAdapter(@NonNull @NotNull FragmentManager fm) {
        super(fm);
    }




    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {

        return arrFragment.get(position);
    }

    @Override
    public int getCount() {
        return arrTitle.size();
    }



    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return arrTitle.get(position);
    }
    public  void addFragment(Fragment fragment, String title){
        arrFragment.add(fragment);
        arrTitle.add(title);
    }
}
