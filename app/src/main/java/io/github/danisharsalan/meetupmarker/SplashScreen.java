package io.github.danisharsalan.meetupmarker;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (AccessToken.getCurrentAccessToken() != null) {
//            setContentView(R.layout.activity_events);
//        } else {
            setContentView(R.layout.activity_login);
//        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent startActivity = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(startActivity);
                finish();
            }
        }, 2000);

    }
}
