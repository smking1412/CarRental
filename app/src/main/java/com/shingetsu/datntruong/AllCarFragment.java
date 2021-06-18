package com.shingetsu.datntruong;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shingetsu.datntruong.Adapter.AllCarAdapter;
import com.shingetsu.datntruong.Adapter.MiniCarAdapter;
import com.shingetsu.datntruong.Models.Car;

import org.jetbrains.annotations.NotNull;

import static com.shingetsu.datntruong.HomeActivity.listCar1;


public class AllCarFragment extends Fragment {
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


        view = inflater.inflate(R.layout.fragment_all_car, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_class);
        // lấy dữ liệu từ listcar1== xe con đổ vào adapter
        miniCarAdapter = new MiniCarAdapter(getActivity(), listCar1);

//        dataRef = FirebaseDatabase.getInstance().getReference().child("products");
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 2);
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(miniCarAdapter);
        miniCarAdapter.setOnItemClickListener(new MiniCarAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, View v) {
                Intent intent = new Intent(getActivity(), CarInformation.class);
                intent.putExtra("pid", listCar1.get(pos).getPid());
                startActivity(intent);
            }
        });
        return view;
    }
 /*   @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Car> options = new FirebaseRecyclerOptions.Builder<Car>()
                .setQuery(dataRef, Car.class)
                .build();

        FirebaseRecyclerAdapter<Car, AllCarAdapter> adapter = new FirebaseRecyclerAdapter<Car, AllCarAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull @NotNull AllCarAdapter holder, int position, @NonNull @NotNull Car model) {
                holder.txtNameCar.setText(model.getName());
                holder.txtRateCar.setText(model.getRate());
                holder.txtVersion.setText("Phiên bản: "+ model.getVersion());
                holder.txtSeat.setText(model.getSeat());
                holder.txtGate.setText(model.getGate());
                holder.txtShift.setText(model.getShift());
                Glide.with(getContext()).load(model.getImage()).into(holder.imgCar);
                holder.txtPriceCar.setText(model.getPice()+" VND");

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), CarInformation.class);
                        intent.putExtra("pid", model.getPid());
                        startActivity(intent);
                    }
                });
            }


            @NonNull
            @NotNull
            @Override
            public AllCarAdapter onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car, parent, false);
                AllCarAdapter holder = new AllCarAdapter(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }*/


}