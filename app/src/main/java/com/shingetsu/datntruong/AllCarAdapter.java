package com.shingetsu.datntruong;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AllCarAdapter extends RecyclerView.Adapter<AllCarAdapter.ViewHolder> {
    Context context;
    String[] itemCars;

    public AllCarAdapter(Context context, String[] itemCars) {
        this.context = context;
        this.itemCars = itemCars;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_car, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        ItemCar itemCar = itemCars.get(position);
//        holder.txtnamecar.setText(itemCar.getName());
//        holder.txtstatuscar.setText(itemCar.getStatus());
//        holder.txtseat.setText(itemCar.getSeat());
//        holder.txtgate.setText(itemCar.getGate());
//        holder.txtshift.setText(itemCar.getShift());
//        holder.txtpricecar.setText(itemCar.getPrice());
//        Picasso.with(context).load(itemCar.getImagecar()).into(holder.imgcar);

    }

    @Override
    public int getItemCount() {
        return itemCars.length;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtnamecar;
        TextView txtrateting;
        TextView txtstatuscar;
        TextView txtseat;
        TextView txtgate;
        TextView txtshift;
        ImageView imgcar;
        TextView txtpricecar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtnamecar = itemView.findViewById(R.id.tv_name_car);
            txtrateting = itemView.findViewById(R.id.tv_rate_car);
            txtstatuscar = itemView.findViewById(R.id.tv_status);
            txtseat = itemView.findViewById(R.id.seat_car);
            txtgate = itemView.findViewById(R.id.gate_car);
            txtshift = itemView.findViewById(R.id.shift);
            imgcar = itemView.findViewById(R.id.img_car);
            txtpricecar = itemView.findViewById(R.id.price_car);
        }
    }
}
