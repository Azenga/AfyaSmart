package com.mysasse.afyasmart.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mysasse.afyasmart.HomeActivity;
import com.mysasse.afyasmart.R;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    private FirebaseAuth mAuth;

    //Global accessible views
    private TextInputEditText emailTxt, passwordTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Initialize fire-base variables
        mAuth = FirebaseAuth.getInstance();


        //Register View
        emailTxt = findViewById(R.id.email_txt);
        passwordTxt = findViewById(R.id.password_txt);
        ProgressBar registerProgressBar = findViewById(R.id.register_progress_bar);
        MaterialButton loginButton = findViewById(R.id.register_button);
        TextView signInTv = findViewById(R.id.sign_in_tv);

        signInTv.setOnClickListener(view -> {
            finish();
        });

        loginButton.setOnClickListener(view -> {
            String email = String.valueOf(emailTxt.getText());
            String password = String.valueOf(passwordTxt.getText());

            if (hasInvalidInputs(email, password)) return;

            registerProgressBar.setVisibility(View.VISIBLE);

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            sendHome();
                            return;
                        }

                        Log.e(TAG, "onCreate: createUserWithEmailAndPassword: error: ", task.getException());
                        Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    });


        });
    }

    private boolean hasInvalidInputs(String email, String password) {

        if (TextUtils.isEmpty(email)) {
            emailTxt.setError("Email Address is Required");
            emailTxt.requestFocus();
            return true;
        }

        if (TextUtils.isEmpty(password)) {
            passwordTxt.setError("Password is Required");
            passwordTxt.requestFocus();
            return true;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailTxt.setError("Invalid email format");
            emailTxt.requestFocus();
            return true;
        }

        if (password.length() < 6) {
            passwordTxt.setError("Minimum of 6 chars required");
            passwordTxt.requestFocus();
            return true;
        }

        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) finish();
    }

    public void sendHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
