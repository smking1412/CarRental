package com.shingetsu.datntruong.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shingetsu.datntruong.CarInformation;
import com.shingetsu.datntruong.Models.Car;
import com.shingetsu.datntruong.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MiniCarAdapter extends RecyclerView.Adapter<AllCarAdapter> {
    public OnItemClickListener onItemClickListener;
    private Activity activity;
    ArrayList<Car> minicarList;

    public MiniCarAdapter(Activity activity, ArrayList<Car> minicarList) {
        this.activity = activity;
        this.minicarList = minicarList;
        notifyDataSetChanged();
    }

    public MiniCarAdapter(ArrayList<Car> minicarList) {
        this.minicarList = minicarList;
    }
    //ko đk gọi nên màu xám.

    @NonNull
    @NotNull
    @Override
    public AllCarAdapter onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car, parent, false);
        AllCarAdapter allCarAdapter = new AllCarAdapter(view);
        return allCarAdapter;
    }

    //chỗ này là sao

    @Override
    public void onBindViewHolder(@NonNull @NotNull AllCarAdapter holder, int position) {
        final Car cars = minicarList.get(position);
        holder.txtNameCar.setText(cars.getName());
        holder.txtRateCar.setText(cars.getRate());
        holder.txtVersion.setText("Phiên bản: " + cars.getVersion());
        holder.txtSeat.setText(cars.getSeat());
        holder.txtGate.setText(cars.getGate());
        holder.txtShift.setText(cars.getShift());
        Glide.with(activity).load(cars.getImage()).into(holder.imgCar);
        holder.txtPriceCar.setText(cars.getPice() + " VND");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(holder.getAdapterPosition(), v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return minicarList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int pos, View v);
    }
}
