package com.example.cis2208;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        setContentView(R.layout.activity_main); //sets screen to view this XML layout

        //Find and set toolbar to the activity (top part of the screen)
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Find drawer from layout
        drawer = findViewById(R.id.drawer_layout);

        //Find navigation view (hamburger menu area which is initially hidden and set it so that its items can be tapped
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Make the hamburger menu functional
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        //Method to synchronize after the DrawerLayout's instance state has been restored, and any other time when the state may have diverged in such a way that the ActionBarDrawerToggle was not notified
        toggle.syncState();

        if(savedInstanceState == null) { //if starting activity for first item or if leaving by pressing back button and get back to it. if device rotated it will not be null
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new bFragment()).commit(); //Change fragment
            navigationView.setCheckedItem(R.id.nav_b);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) { //To identify which board was selected
        switch (menuItem.getItemId()){
            case R.id.nav_b:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new bFragment()).commit();
                break;
            case R.id.nav_pol:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new polFragment()).commit();
                break;
            case R.id.nav_g:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new gFragment()).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START); //Close drawer after selection
        return true;
    }

    @Override
    public void onBackPressed() { //Overrides back butt to close drawer if drawer is open
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
}
