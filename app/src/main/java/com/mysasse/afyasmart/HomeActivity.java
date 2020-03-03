package com.mysasse.afyasmart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mysasse.afyasmart.ui.LoginActivity;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Initialize fire-base instances
        mAuth = FirebaseAuth.getInstance();

        if (getSupportActionBar() == null) {
            setSupportActionBar(findViewById(R.id.toolbar));
            getSupportActionBar().setTitle(R.string.app_name);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            mAuth.signOut();
            sendToLogin();
        }
        return true;
    }

    private void sendToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();

        if (user == null) {
            sendToLogin();
        } else {
            //@todo
            //Update UI
            updateUI(user);
        }
    }

    private void updateUI(FirebaseUser user) {
        Log.d(TAG, "updateUI: User => " + user);
    }
}
