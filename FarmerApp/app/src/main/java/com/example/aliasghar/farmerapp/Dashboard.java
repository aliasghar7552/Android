package com.example.aliasghar.farmerapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBar;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.support.v4.view.GravityCompat;

public class Dashboard extends AppCompatActivity {

    SessionManagement session;

    private Toolbar toolbar;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        session = new SessionManagement(this);

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigationView);

        session.checkLogin();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_postcrops:
                        item.setChecked(true);
                       //displayMessage("Post Crops were clicked");
                        Intent intent = new Intent(getApplicationContext(), PostCrops.class);
                        startActivity(intent);
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_opentender:
                        item.setChecked(true);
                        //displayMessage("Open Tendors was clicked");
                        Intent intent1 = new Intent(getApplicationContext(), OpenTendors.class);
                        startActivity(intent1);
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_closetender:
                        item.setChecked(true);
                        //displayMessage("Close tender was clicked");
                        Intent intent2 = new Intent(getApplicationContext(), ClosedTendors.class);
                        startActivity(intent2);
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_viewpost:
                        item.setChecked(true);
                        //displayMessage("View Post was clicked");
                        Intent intent3 = new Intent(getApplicationContext(), UserPosts.class);
                        startActivity(intent3);
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_viewbids:
                        item.setChecked(true);
                        //displayMessage(" View Bids was clicked");
                        Intent intent4 = new Intent(getApplicationContext(), ViewUserBids.class);
                        startActivity(intent4);
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.nav_logout:
                        item.setChecked(true);
                        //displayMessage(" View Bids was clicked");
                        session.logout();
                        drawerLayout.closeDrawers();
                        return true;
                }
                return false;
            }

        });
    }


    public void displayMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
