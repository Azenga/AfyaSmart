package com.mysasse.afyasmart;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mysasse.afyasmart.ui.LoginActivity;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";

    private AppBarConfiguration mAppBarConfiguration;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Check whether the necessary permissions are available before proceeding
        if (
                (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                        || (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        ) {
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }

        //Initialize fire-base instances
        mAuth = FirebaseAuth.getInstance();

        //Get toolbar and set it as the support action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Register the drawer layout
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        //Register the navigation view
        NavigationView navigationView = findViewById(R.id.nav_view);

        //Setup the AppBarConfigurations
        mAppBarConfiguration = new AppBarConfiguration
                .Builder(R.id.homeFragment, R.id.doctorsFragment, R.id.diseasesFragment, R.id.messagesFragment, R.id.postsFragment, R.id.profileFragment)
                .setOpenableLayout(drawerLayout)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.logout) {
            AlertDialog.Builder logoutDialog = new AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_account_circle_black_48dp)
                    .setMessage("Are sure you want to logout ?")
                    .setPositiveButton("Sure", (dialog, which) -> {
                        mAuth.signOut();
                        sendToLogin();
                    })
                    .setNegativeButton("Cancel", ((dialog, which) -> {

                    }));

            logoutDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();


        if (currentUser == null) {
            sendToLogin();
            return;
        }

        updateUI(currentUser);
    }

    private void sendToLogin() {

        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
    }

    private void updateUI(FirebaseUser currentUser) {
        Log.d(TAG, "updateUI: Authenticated User UID :" + currentUser.getUid());
    }
}
