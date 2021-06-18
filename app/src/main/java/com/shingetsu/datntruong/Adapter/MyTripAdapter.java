package com.shingetsu.datntruong.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shingetsu.datntruong.R;

import org.jetbrains.annotations.NotNull;

public class MyTripAdapter extends RecyclerView.ViewHolder {
    public TextView tvNameCarTrip, tvPriceCarTrip, tvStartDateTrip, tvDriverTrip, tvStatusTrip;
    public ImageView imgCarTrip;
    public LinearLayout btnStatusTrip;
    public View mView;
    public MyTripAdapter(@NonNull @NotNull View itemView) {
        super(itemView);
        mView = itemView;

        btnStatusTrip = mView.findViewById(R.id.linear);
        tvPriceCarTrip = mView.findViewById(R.id.tv_price_car_trip);
        tvStartDateTrip = mView.findViewById(R.id.tv_start_date_trip);
        tvDriverTrip = mView.findViewById(R.id.tv_driver_trip);
        tvStatusTrip = mView.findViewById(R.id.tv_status_trip);
        tvNameCarTrip = mView.findViewById(R.id.tv_name_car_trip);
        imgCarTrip = mView.findViewById(R.id.img_car_trip);
    }

}
