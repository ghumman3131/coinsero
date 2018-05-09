package com.inception.coinsero;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences sp = getSharedPreferences("user_info" , MODE_PRIVATE);

                if(sp.getBoolean("second_time" , false))
                {
                    if(sp.getString("username","").equals("")) {
                        Intent i = new Intent(SplashActivity.this, LoginActivity.class);

                        startActivity(i);

                        finish();
                    }
                    else {
                        Intent i = new Intent(SplashActivity.this, HomeActivity.class);

                        startActivity(i);

                        finish();
                    }
                }
                else {

                    Intent i = new Intent(SplashActivity.this, WelcomeActivity.class);

                    startActivity(i);

                    finish();
                }
            }
        }, 2000);
    }
}
