package com.shingetsu.datntruong.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shingetsu.datntruong.Interface.ItemClickListner;
import com.shingetsu.datntruong.R;

import org.jetbrains.annotations.NotNull;


public class AllCarAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtNameCar, txtRateCar, txtVersion, txtSeat, txtGate, txtShift, txtPriceCar, txtMaterial;
    public ImageView imgCar;
    public ItemClickListner itemClickListner;

    public AllCarAdapter(@NonNull @NotNull View itemView) {
        super(itemView);
        imgCar = itemView.findViewById(R.id.img_car);
        txtNameCar = itemView.findViewById(R.id.tv_name_car);
        txtRateCar = itemView.findViewById(R.id.tv_rate_car);
        txtVersion = itemView.findViewById(R.id.tv_status);
        txtSeat = itemView.findViewById(R.id.seat_car);
        txtGate = itemView.findViewById(R.id.gate_car);
        txtShift = itemView.findViewById(R.id.shift);
        txtPriceCar = itemView.findViewById(R.id.price_car);


    }
    public void setItemClickListner(ItemClickListner listner){
        this.itemClickListner = listner;
    }

    @Override
    public void onClick(View view) {
        itemClickListner.onClick(view, getAdapterPosition(), false);


    }
}
