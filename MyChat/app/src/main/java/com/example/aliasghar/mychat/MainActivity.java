package com.example.aliasghar.mychat;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.aliasghar.mychat.Adapters.TabsPageAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    Toolbar toolbar;

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabsPageAdapter tabsPageAdapter;

    FirebaseUser currentUser;
    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        currentUser = auth.getCurrentUser();

        if(currentUser != null) {
            String onlineUserId = auth.getCurrentUser().getUid();

            usersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(onlineUserId);
        }

        //main page toolbar
        toolbar = (Toolbar) findViewById(R.id.mainPageToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("MyChat");

        //Main page tabs
        viewPager = (ViewPager) findViewById(R.id.mainTabsPager);
        tabsPageAdapter = new TabsPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabsPageAdapter);
        tabLayout = (TabLayout) findViewById(R.id.mainTabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser == null) {
            logoutUser();
        }
        else if(currentUser != null) {
            usersRef.child("online").setValue("true");
        }
    }

    private void logoutUser() {
        Intent intent = new Intent(getBaseContext(), StartPage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(currentUser != null) {
            usersRef.child("online").setValue(ServerValue.TIMESTAMP);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    // Main page Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.mainMenuLogout) {

            if(currentUser != null) {
                usersRef.child("online").setValue(ServerValue.TIMESTAMP);
            }
            auth.signOut();
            logoutUser();
        }
        if (item.getItemId() == R.id.mainMenuSettings) {
            Intent intent = new Intent(getBaseContext(), Settings.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.mainMenuAllUsers) {
            Intent intent = new Intent(getBaseContext(), AllUsers.class);
            startActivity(intent);
        }
        return true;
    }

}
