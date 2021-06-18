package com.shingetsu.datntruong;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shingetsu.datntruong.Adapter.MyTripAdapter;
import com.shingetsu.datntruong.Models.Trip;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.shingetsu.datntruong.R.layout.*;

public class MyTripActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    Toolbar toolbar;
    private RecyclerView recyclerView;
    private DatabaseReference mTrip;
    public View view;
    private ArrayList<Trip> listTrip = new ArrayList<>();






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_my_trip);
        toolbar = findViewById(R.id.toonbar_my_trip);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(MyTripActivity.this, HomeActivity.class));
            }
        });

        recyclerView = findViewById(R.id.recycle_my_trip);


        mAuth = FirebaseAuth.getInstance();
        mTrip = FirebaseDatabase.getInstance().getReference("AllTrip").child(mAuth.getUid());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference mCar = FirebaseDatabase.getInstance().getReference().child("products");
        FirebaseRecyclerOptions<Trip> options = new FirebaseRecyclerOptions.Builder<Trip>()
                .setQuery(mTrip, Trip.class)
                .build();

        FirebaseRecyclerAdapter<Trip, MyTripAdapter> adapter = new FirebaseRecyclerAdapter<Trip, MyTripAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull @NotNull MyTripAdapter holder, int position, @NonNull @NotNull Trip model) {

                listTrip.add(model);

                mCar.child(model.getCarIdTrip()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        String img = snapshot.child("image").getValue(String.class);
                        String nameCar = snapshot.child("name").getValue(String.class);
                        holder.tvNameCarTrip.setText(nameCar);
                        Glide.with(getApplicationContext()).load(img).into(holder.imgCarTrip);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });

                holder.tvPriceCarTrip.setText(model.getPriceCartrip() + " VNĐ");
                holder.tvStartDateTrip.setText(model.getStartDateTrip() + " - " + model.getEndDateTrip());
                if (model.getHasDriverTrip() == true) {
                    holder.tvDriverTrip.setText("Có tài xế");
                    holder.tvDriverTrip.setTextColor(Color.parseColor("#00ff00"));
                } else {
                    holder.tvDriverTrip.setText("Không có tài xế");
                    holder.tvDriverTrip.setTextColor(Color.parseColor("#ff0000"));
                }
                if (model.getStatusTrip().equals("Đang chờ")) {
                    holder.btnStatusTrip.setVisibility(View.VISIBLE);
                    holder.tvStatusTrip.setText("Đang chờ");
                    holder.tvStatusTrip.setTextColor(Color.parseColor("#ff7f50"));

                } else if (model.getStatusTrip().equals("Đang chạy")) {
                    holder.btnStatusTrip.setVisibility(View.GONE);
                    holder.tvStatusTrip.setText("Đang chạy");
                    holder.tvStatusTrip.setTextColor(Color.parseColor("#00ff00"));

                } else if (model.getStatusTrip().equals("Đã kết thúc")) {
                    holder.btnStatusTrip.setVisibility(View.GONE);
                    holder.tvStatusTrip.setText("kết thúc");

                }

                holder.btnStatusTrip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialogConfirm(position);

                    }
                });
            }


            @NonNull
            @NotNull
            @Override
            public MyTripAdapter onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_trip, parent, false);
                MyTripAdapter holder = new MyTripAdapter(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private void showDialogConfirm(int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MyTripActivity.this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference dataRef =database.getReference("AllTrip");
        builder.setTitle("Hủy chuyến").setMessage("Bạn có chắc muốn hủy chuyến")
                .setNegativeButton("Không", ((dialog, which) -> dialog.dismiss() ))
                .setPositiveButton("Đồng ý", ((dialog, which) ->
                        dataRef.child(listTrip.get(pos).getUid()).child(listTrip.get(pos).getTripId()).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable @org.jetbrains.annotations.Nullable DatabaseError error, @NonNull @NotNull DatabaseReference ref) {
                                Toast.makeText(MyTripActivity.this, "Bạn đã hủy chuyến xe!!!", Toast.LENGTH_SHORT).show();
                            }
                        })

                )).setCancelable(false);
                AlertDialog dialog = builder.create();
                dialog.setOnShowListener(dialog1 -> {
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorAccent));
                });
                dialog.show();
            }



    }


