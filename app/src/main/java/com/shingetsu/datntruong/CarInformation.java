package com.shingetsu.datntruong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shingetsu.datntruong.Models.Car;

import org.jetbrains.annotations.NotNull;

public class CarInformation extends AppCompatActivity {
    private TextView namecar;
    private TextView available;
    private ImageView imgStatus;
    private TextView rateCar;
    private TextView priceCar;
    private TextView shiftcar;
    private TextView seatCar;
    private TextView gasCar;
    private TextView describe;
    private ImageView imgCarInfo;
    private Button btnbackBooking;
    private Button btnBookCar;
    private String productID = "";
    private Context context;
    private Car car= new Car();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_information);
        productID = getIntent().getStringExtra("pid");
        namecar = findViewById(R.id.infor_car);
        imgStatus = findViewById(R.id.imageView_notavailable);
        available = findViewById(R.id.notAvailableText);
        rateCar = findViewById(R.id.tv_rate_car_booking);
        priceCar = findViewById(R.id.tv_price_car);
        shiftcar = findViewById(R.id.tv_shift_car_booking);
        seatCar = findViewById(R.id.tv_seat_car_booking);
        describe = findViewById(R.id.tv_describe_car);
        imgCarInfo = findViewById(R.id.Image_car_booking);
        btnbackBooking = findViewById(R.id.btn_back_booking);
        btnBookCar = findViewById(R.id.book);
        getProductDetails(productID);
        btnbackBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnBookCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( car.getStatus() == true){
                    AlertDialog.Builder builder = new AlertDialog.Builder(CarInformation.this);
                    builder.setTitle("Thông báo").setMessage("Xe hiện đã được đặt")
                            .setNegativeButton("Xác nhận", ((dialog, which) -> dialog.dismiss()))
                            .setCancelable(false);
                    AlertDialog dialog = builder.create();
                    dialog.setOnShowListener(dialog1 -> {
                        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorAccent));
                    });
                    dialog.show();

                } else {
                    Intent intent = new Intent(CarInformation.this, BookingCarActivity.class);
                    intent.putExtra("book car",car);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }

    private void getProductDetails(String productID) {
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("products");
        if (productID != null) {
            productRef.child(productID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        car = snapshot.getValue(Car.class);
                        namecar.setText(car.getName());
                        // Glide.with(context)
                        //    .load(car.getImage())
                        //    .into(imgStatus);
//                        available.setText(car.isRenting());
                        if (car.getStatus() == true) {
                            available.setText("Xe Đang bận");
                            imgStatus.setImageResource(R.drawable.ic_x_icon);
                        } else {
                            available.setText("Sẵn sàng");
                            available.setTextColor(Color.parseColor("#7CFC00"));
                            imgStatus.setImageResource(R.drawable.ic_baseline_check_24);
                        }
                        rateCar.setText(car.getRate());
                        priceCar.setText(car.getPice() + " " +
                                "VNĐ");
                        shiftcar.setText(car.getShift());
                        seatCar.setText(car.getSeat());
                        describe.setText(car.getMaterial());
                        Glide.with(getApplicationContext())
                                .load(car.getImage())
                                .into(imgCarInfo);

                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }
    }
}