package com.shingetsu.datntruong;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Phạm Minh Tân - Shin on 5/13/2021.
 */
public class AllCarFragment extends Fragment {
    RecyclerView recyclerView;
    AllCarAdapter listCarAdapter;
    private View view;
    private String[] itemCars = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_all_car, container, false);
        itemCars = getResources().getStringArray(R.array.carlist);
        init();
        return  view;
    }

    private void init() {
        recyclerView = view.findViewById(R.id.recycle_class);

        listCarAdapter = new AllCarAdapter(getActivity(),itemCars);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(),2);
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(listCarAdapter);

    }


}