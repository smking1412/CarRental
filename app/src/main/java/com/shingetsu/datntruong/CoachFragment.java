package com.shingetsu.datntruong;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shingetsu.datntruong.Adapter.AllCarAdapter;
import com.shingetsu.datntruong.Adapter.MiniCarAdapter;

import static com.shingetsu.datntruong.HomeActivity.listCar1;
import static com.shingetsu.datntruong.HomeActivity.listCar2;
import static com.shingetsu.datntruong.HomeActivity.listCar3;


public class CoachFragment extends Fragment {

    private RecyclerView recyclerView;
    AllCarAdapter listCarAdapter;
    private DatabaseReference dataRef;
    private MiniCarAdapter miniCarAdapter;
    private View view;

    RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        view = inflater.inflate(R.layout.fragment_coach, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_coach);

        miniCarAdapter = new MiniCarAdapter(getActivity(), listCar2);
        dataRef = FirebaseDatabase.getInstance().getReference().child("products");
        // cái dataref này gọi xong để đấy à

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 2);
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(miniCarAdapter);
        miniCarAdapter.setOnItemClickListener(new MiniCarAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, View v) {
                Intent intent = new Intent(getActivity(), CarInformation.class);
                intent.putExtra("pid", listCar2.get(pos).getPid());
                startActivity(intent);
            }
        });
        return view;
    }
}