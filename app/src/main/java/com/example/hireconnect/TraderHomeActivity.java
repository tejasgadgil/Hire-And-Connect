package com.example.hireconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class TraderHomeActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    Button historyBtn, updateProfileBtn, pendingRequestsBtn, contactBtn, logoutBtn;

    Intent intent;
    String usernameTXT;

    int updateProfileID = R.id.updateProfile;
    int contactID = R.id.contact;
    int logoutID = R.id.logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trader_home);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_open, R.string.navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        Log.d("tejas", "step1");


// Set up the menu item click listener
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            // Handle the click event for the menu item here
            int itemId = menuItem.getItemId();
            Log.d("tejas", "step2" + itemId);


            if(itemId == logoutID){
                Toast.makeText(TraderHomeActivity.this, "Logout clicked", Toast.LENGTH_SHORT).show();

            } else if (itemId == updateProfileID) {
                Log.d("tejas", "step3" + updateProfileID);
                Toast.makeText(TraderHomeActivity.this, "Update Profile clicked", Toast.LENGTH_SHORT).show();
                Log.d("tejas", "step4" + updateProfileID);

            }else if (itemId == contactID){
                Toast.makeText(TraderHomeActivity.this, "Contact clicked", Toast.LENGTH_SHORT).show();

            }


            // Close the navigation drawer if it's open
            // This assumes you have a DrawerLayout or similar setup
            // If you don't use a drawer, you can skip this part
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }

            return true; // Return true to indicate that the item was handled
        });


//        // Set a DrawerListener to prevent the drawer from closing when clicking inside the content area
//        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
//            @Override
//            public void onDrawerSlide(View drawerView, float slideOffset) {
//                super.onDrawerSlide(drawerView, slideOffset);
//
//                // Lock the drawer open when it's being slid open
//                if (slideOffset > 0) {
//                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
//                } else {
//                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//                }
//            }
//        });

        intent = getIntent();
        usernameTXT = intent.getStringExtra("usernameOfTrader");


    }


}



