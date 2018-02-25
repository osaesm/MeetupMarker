package io.github.danisharsalan.meetupmarker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class Navigation extends AppCompatActivity {

    String firstName, lastName, email, profile_picture_url, id;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_profile:
                    loadFragment(new ProfileFragment());
                    return true;
                case R.id.navigation_map:
                    loadFragment(new MapActivityFragment());
                    return true;
                case R.id.navigation_add_event:
                    loadFragment(new AddEvent());
                    return true;
            }
            return false;
        }
    };

    public void loadFragment(Fragment fragment) {
        if(fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.rootLayout, fragment)
                    .commit();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Intent i = getIntent();
        firstName = i.getStringExtra("first name");
        lastName = i.getStringExtra("last name");
        email = i.getStringExtra("email");
        id = i.getStringExtra("id");
        profile_picture_url = i.getStringExtra("photo url");
        boolean toMap = i.getBooleanExtra("to map", false);
        if(toMap) {
            navigation.setSelectedItemId(R.id.navigation_profile);
        } else {
            navigation.setSelectedItemId(R.id.navigation_add_event);
        }
    }

}
