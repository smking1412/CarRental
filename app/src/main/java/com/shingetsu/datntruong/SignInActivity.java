package com.shingetsu.datntruong;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.shingetsu.datntruong.Models.User;
import com.shingetsu.datntruong.Utils.Common;

import org.jetbrains.annotations.NotNull;

public class SignInActivity extends AppCompatActivity {
    private static final String TAG = "ERROR";
    TextInputLayout username;
    TextInputLayout password;
    Button btnlogin;
    private TextView btnSignUp;
    FirebaseAuth mAuth;
    DatabaseReference userRef;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth = FirebaseAuth.getInstance();

        username = findViewById(R.id.edt_user_name_login);
        password = findViewById(R.id.edt_password);
        btnlogin = findViewById(R.id.btn_login);
        btnSignUp = findViewById(R.id.register);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getEditText().getText().toString().trim();
                String pass = password.getEditText().getText().toString().trim();
                GoToHome(user, pass);
            }
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String email = bundle.getString("email");
            String pass = bundle.getString("pass");
            if (email != null && pass != null) {
                setFields(email, pass);
            }
        }
        password.getEditText().setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) {
                String user = username.getEditText().getText().toString().trim();
                String pass = password.getEditText().getText().toString().trim();
                GoToHome(user, pass);
                return true;
            }
            return false;
        });

    }

    private void setFields(String email, String pass) {
        username.getEditText().setText(email);
        password.getEditText().setText(pass);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);
        String userName = sharedPreferences.getString("UserName", "");
        String password = sharedPreferences.getString("Password", "");
        if (!userName.isEmpty() && !password.isEmpty()) {
            setFields(userName, password);
            //GoToHome(userName, password);

        }
    }

    private void GoToHome(String UserName, String Password) {
        ProgressDialog pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("Please Wait logging in..");
        pd.show();
        SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!UserName.isEmpty() && !Password.isEmpty()) {

            editor.putString("UserName", UserName);
            editor.putString("Password", Password);
            mAuth.signInWithEmailAndPassword(UserName, Password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    pd.dismiss();
                    checkUserFromFirebase();

                }
            }).addOnFailureListener(e -> {
                pd.dismiss();
                Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            });

        }
        editor.apply();
    }

    private void checkUserFromFirebase() {
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference(Common.USER_INFO_REFERENCE)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                Common.userModel = user;
                startActivity(new Intent(SignInActivity.this, HomeActivity.class));
                finish();
                Toast.makeText(getApplicationContext(), "Welcome " + user.getUsername(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}
