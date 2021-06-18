package com.shingetsu.datntruong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shingetsu.datntruong.Models.Trip;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class PayActivity extends AppCompatActivity {
    private EditText edtCardNumber, edtMonth, edtYear, edtCvv, edtNameCard;
    private Button btnPay;
    FirebaseDatabase fb;
    FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private String saveCurrentDate;
    private String saveCurrentTime;
    private String tripRandomkey;
    String uid;
    Trip trip;
    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("AllTrip");
        fb = FirebaseDatabase.getInstance();

        edtCardNumber = findViewById(R.id.card_number);
        edtMonth = findViewById(R.id.month);
        edtYear = findViewById(R.id.year);
        edtCvv = findViewById(R.id.cvv);
        edtNameCard = findViewById(R.id.name);
        btnPay = findViewById(R.id.btn_pay);

        loadingbar = new ProgressDialog(this);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                trip = (Trip) getIntent().getSerializableExtra("trip");
                Log.d("bbssss", trip.getStartDateTrip());
                createTrip(trip);
                finish();


            }
        });

        edtCardNumber.addTextChangedListener(new TextWatcher() {
            private static final int TOTAL_SYMBOLS = 19; // size of pattern 0000-0000-0000-0000
            private static final int TOTAL_DIGITS = 16; // max numbers of digits in pattern: 0000 x 4
            private static final int DIVIDER_MODULO = 5; // means divider position is every 5th symbol beginning with 1
            private static final int DIVIDER_POSITION = DIVIDER_MODULO - 1; // means divider position is every 4th symbol beginning with 0
            private static final char DIVIDER = '-';
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isInputCorrect(s, TOTAL_SYMBOLS, DIVIDER_MODULO, DIVIDER)) {
                    s.replace(0, s.length(), buildCorrectString(getDigitArray(s, TOTAL_DIGITS), DIVIDER_POSITION, DIVIDER));
                }

            }
            private boolean isInputCorrect(Editable s, int totalSymbols, int dividerModulo, char divider) {
                boolean isCorrect = s.length() <= totalSymbols; // check size of entered string
                for (int i = 0; i < s.length(); i++) { // check that every element is right
                    if (i > 0 && (i + 1) % dividerModulo == 0) {
                        isCorrect &= divider == s.charAt(i);
                    } else {
                        isCorrect &= Character.isDigit(s.charAt(i));
                    }
                }
                return isCorrect;
            }

            private String buildCorrectString(char[] digits, int dividerPosition, char divider) {
                final StringBuilder formatted = new StringBuilder();

                for (int i = 0; i < digits.length; i++) {
                    if (digits[i] != 0) {
                        formatted.append(digits[i]);
                        if ((i > 0) && (i < (digits.length - 1)) && (((i + 1) % dividerPosition) == 0)) {
                            formatted.append(divider);
                        }
                    }
                }

                return formatted.toString();
            }

            private char[] getDigitArray(final Editable s, final int size) {
                char[] digits = new char[size];
                int index = 0;
                for (int i = 0; i < s.length() && index < size; i++) {
                    char current = s.charAt(i);
                    if (Character.isDigit(current)) {
                        digits[index] = current;
                        index++;
                    }
                }
                return digits;
            }
        });
    }

    private void createTrip(final Trip trip) {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        tripRandomkey = saveCurrentDate + saveCurrentTime;
        HashMap<String, Object> tripMap = new HashMap<>();
        tripMap.put("tripId", tripRandomkey);
        tripMap.put("date", saveCurrentDate);
        tripMap.put("time", saveCurrentTime);
        tripMap.put("carIdTrip", trip.getCarIdTrip());
        tripMap.put("uid", mAuth.getUid());
        tripMap.put("priceCartrip", trip.getPriceCartrip());
        tripMap.put("startDateTrip", trip.getStartDateTrip());
        tripMap.put("endDateTrip", trip.getEndDateTrip());
        tripMap.put("hasDriverTrip", trip.getHasDriverTrip());
        tripMap.put("statusTrip", trip.getStatusTrip());
        databaseReference.child(mAuth.getUid())
                .child(tripRandomkey)
                .setValue(tripMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(PayActivity.this, HomeActivity.class);
                            startActivity(intent);
                            loadingbar.dismiss();
                            Toast.makeText(PayActivity.this, "Đã thanh toán...", Toast.LENGTH_SHORT).show();
                        } else {
                            loadingbar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(PayActivity.this, "lỗi!!!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}