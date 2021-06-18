package com.shingetsu.datntruong.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shingetsu.datntruong.AllCarFragment;
import com.shingetsu.datntruong.CoachFragment;
import com.shingetsu.datntruong.HomeActivity;
import com.shingetsu.datntruong.Models.Car;
import com.shingetsu.datntruong.MyTripActivity;
import com.shingetsu.datntruong.R;
import com.shingetsu.datntruong.TruckFragment;
import com.shingetsu.datntruong.TypeOfCarFragment;
import com.shingetsu.datntruong.Utils.Common;
import com.shingetsu.datntruong.Adapter.ViewPagerAdapter;
import com.shingetsu.datntruong.databinding.FragmentHomeBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    ViewPagerAdapter viewPagerAdapter;
    RecyclerView listCar;
    String[] itemcar = null;
    ImageView imgAllTrip;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        itemcar = getResources().getStringArray(R.array.carlist);
        imgAllTrip = root.findViewById(R.id.btn_all_trip);
        imgAllTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext() , MyTripActivity.class);
                startActivity(intent);
            }
        });
        init();



//        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);



                // initViewPager();


            }
        });
        return root;
    }

    private void init() {
        viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), 0);

        binding.viewpager.setAdapter(viewPagerAdapter);
        binding.tablayout1.setupWithViewPager(binding.viewpager);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public Fragment[] fragments = new Fragment[3];

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new AllCarFragment();
                    break;

                case 1:
                    fragment = new CoachFragment();
                    break;

                case 2:
                    fragment = new TypeOfCarFragment();
                    break;
                case 3:
                    fragment = new TruckFragment();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }

        //set title cho tablayout
        @Override
        public CharSequence getPageTitle(int position)
        {
            return itemcar[position];
        }
    }

}

