package com.shingetsu.datntruong;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.shingetsu.datntruong.Utils.Common;

public class SignInActivity extends AppCompatActivity {
    private static final String TAG = "ERROR";
    TextInputLayout username;
    TextInputLayout password;
    Button btnlogin;
    private TextView btnSignUp;
    FirebaseAuth mAuth;

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
            GoToHome(userName, password);

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

                    startActivity(new Intent(SignInActivity.this, HomeActivity.class));
                    finish();
                    Toast.makeText(getApplicationContext(), "Welcome " + UserName, Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(e -> {
                pd.dismiss();
                Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            });

        }
        editor.apply();


    }

    private void openSignUp() {
        startActivity(new Intent(SignInActivity.this, SignUpActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }

    private boolean validatePassword() {
        String val = password.getEditText().getText().toString();
        if (val.isEmpty()) {
            password.setError("Không được để trống");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }


    private boolean validateUsername() {

        String val = username.getEditText().getText().toString();
        if (val.isEmpty()) {
            username.setError("Không được để trống");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Common.REQUEST_CODE_SIGN_UP && resultCode == RESULT_OK) {

        }
    }
}
