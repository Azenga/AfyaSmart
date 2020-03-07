package com.mysasse.afyasmart;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
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
import com.mysasse.afyasmart.ui.RequestPermissionActivity;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";

    private FirebaseAuth mAuth;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Check whether the necessary permission and grant them if necessary
        if (
                (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                        || (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        ) {
            startActivity(new Intent(this, RequestPermissionActivity.class));
            return;
        }

        //Initialize fire-base instances
        mAuth = FirebaseAuth.getInstance();

        //Register the toolbar and set it as the actionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Register the drawer layout
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        //Register Navigation View
        NavigationView navigationView = findViewById(R.id.nav_view);

        //Setup appBarConfiguration with the drawer layout and the top destinations
        appBarConfiguration = new AppBarConfiguration
                .Builder(R.id.homeFragment, R.id.doctorsFragment, R.id.diseasesFragment, R.id.messagesFragment, R.id.postsFragment)
                .setOpenableLayout(drawerLayout)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.fragment);

        /*
         * Setup Navigation UI components
         */

        //Setup the action bar
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        //Setup the sidebar navigation
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
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
            updateUI(user);
        }
    }

    private void updateUI(FirebaseUser user) {
        Log.d(TAG, "updateUI: User => " + user);
    }
}
