package com.shingetsu.datntruong;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.util.Pair;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.shingetsu.datntruong.Models.Car;
import com.shingetsu.datntruong.Models.Trip;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BookingCarActivity extends AppCompatActivity implements OnMapReadyCallback {
    private TextView tvPickDate;
    private RadioGroup rgPickDriver;
    private TextView tvPriceCar;
    private TextView tvServicePrice;
    private TextView tvTotalPrice;
    private TextView tvDepositPrice;
    private AppCompatButton btnConfirm;
    private TextView tvTimeRent;
    private TextView tvPhiBaoHiem;
    private TextView tvAddressCar;
    public boolean hasDriver = false;
    private Button btnbackBooking;
    private String productID = "";
    private LatLng latLng;
    Car carSelect;
    Date currentTime;
    Trip trip = new Trip();
    private int selectedID;
    private long dailyPrice = 0;
    private int dayRent;
    private long driverPrice = 0;
    private long baohiemPrice = 54800;
    private long tempTotalPrice = 0;
    private long totalPrice = 0;
    private float deposit = 0;

    private GoogleMap mMap;
    Car car = new Car();
    private String addressCar = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_car);
        productID = getIntent().getStringExtra("pid");
        carSelect = (Car) getIntent().getSerializableExtra("Car");
        tvPickDate = findViewById(R.id.tv_pickup_Date);
        rgPickDriver = findViewById(R.id.title);
        tvPriceCar = findViewById(R.id.tv_unit_price_rental);
        tvServicePrice = findViewById(R.id.tv_service_price);
        tvTotalPrice = findViewById(R.id.tv_total);
        tvDepositPrice = findViewById(R.id.tv_deposit);
        btnConfirm = findViewById(R.id.btn_confirm);
        tvTimeRent = findViewById(R.id.tv_time_rental_car);
        tvPhiBaoHiem = findViewById(R.id.tv_insurrance_price);
        tvAddressCar = findViewById(R.id.tv_address_car);
        btnbackBooking = findViewById(R.id.btn_back_booking_complete);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("chọn ngày khởi hành và kết thúc");
        final MaterialDatePicker materialDatePicker = builder.build();

        Car car = getIntent().getParcelableExtra("book car");
        dailyPrice = Long.parseLong(car.getPice());
        // nhận tt car

        tvPriceCar.setText(car.getPice() + "VNĐ");
        tvAddressCar.setText(car.getAddress());
        getLocationAddress(this, car.getAddress());



        //get phi bao hiem tu fb
        tvPhiBaoHiem.setText(String.valueOf(baohiemPrice) + "VNĐ");


        deposit = (float) 0.3;//get tu firebase
        tvPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
                    @Override
                    public void onPositiveButtonClick(Pair<Long, Long> selection) {
                        Long startDate = selection.first;
                        Long endDate = selection.second;

                        long msDiff = endDate - startDate;
                        dayRent = (int) TimeUnit.MILLISECONDS.toDays(msDiff);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
                        tvTimeRent.setText(String.valueOf(dayRent));
                        trip.setEndDateTrip(simpleDateFormat.format(endDate));
                        trip.setStartDateTrip(simpleDateFormat.format(startDate));
                        tvPickDate.setText(simpleDateFormat.format(startDate) + " - " + simpleDateFormat.format(endDate));
                    }

                });
                ShowInvoice();

            }
        });
        rgPickDriver.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                selectedID = radioGroup.getCheckedRadioButtonId();
                if (selectedID == -1) {
                    Toast.makeText(BookingCarActivity.this, "Vui lòng chọn hình thức thuê!!", Toast.LENGTH_SHORT).show();
                } else {
                    switch (selectedID) {
                        case R.id.not_driver:
                            hasDriver = false;
                            driverPrice = 0;
                            ShowInvoice();
                            break;
                        case R.id.yes_driver:
                            hasDriver = true;
                            driverPrice = 300000;//get từ Firebase
                            ShowInvoice();
                            break;
                        default:
                    }
                }
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //gán data trip vào đây


                trip.setHasDriverTrip(hasDriver);
                trip.setPriceCartrip(totalPrice);
                trip.setCarIdTrip(car.getPid());
                trip.setStatusTrip("Đang chờ");
                Intent intent = new Intent(BookingCarActivity.this, PayActivity.class);
                intent.putExtra("trip", trip);
                startActivity(intent);

            }
        });

        btnbackBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
    }

    private long TempTotalPrice(long dailyPrice, int timeRent, long driverPrice, long baohiemPrice) {
        return tempTotalPrice = dailyPrice * timeRent + driverPrice * timeRent + baohiemPrice;
    }

    private long ToTalPrice(long tempTotalPrice, float tienCoc) {
        return totalPrice = tempTotalPrice;
    }


    private void ShowInvoice() {
        tvTimeRent.setText(String.valueOf(dayRent));
        tvServicePrice.setText(String.valueOf(driverPrice * dayRent) + " VNĐ");

        Log.d("showinvoicessss", "ShowInvoice: " + dailyPrice + "zzz" + dayRent + "///" + driverPrice + "  sss" + baohiemPrice);

        TempTotalPrice(dailyPrice, dayRent, driverPrice, baohiemPrice);

        ToTalPrice(tempTotalPrice, deposit);
        tvDepositPrice.setText(String.valueOf(totalPrice * deposit) + " VNĐ");
        tvTotalPrice.setText(String.valueOf(totalPrice) + " VNĐ");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        mMap.addMarker(new MarkerOptions().position(latLng).title("xe thuê")).setIcon(BitmapDescriptorFactory.defaultMarker());
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(14f).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    public LatLng getLocationAddress(Context context, String strAddress) {
        Geocoder coder = new Geocoder(context);
        List<Address> address;

        try {
            address = coder.getFromLocationName(strAddress, 1);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            latLng = new LatLng(location.getLatitude(), location.getLongitude());
        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return latLng;
    }
}
