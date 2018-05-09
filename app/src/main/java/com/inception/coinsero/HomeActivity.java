package com.inception.coinsero;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {



    private FragmentManager fm ;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    open_home();
                    return true;
                case R.id.navigation_dashboard:

                    open_transactions();
                    return  true;
                case R.id.navigation_account:

                    open_account();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        navigation.setSelectedItemId(R.id.navigation_home);
    }

    private void open_account()
    {
        fm = getSupportFragmentManager();

        fm.beginTransaction().replace(R.id.main_frame , new AccountFragment()).commit();
    }

    private void open_home()
    {
        fm = getSupportFragmentManager();

        fm.beginTransaction().replace(R.id.main_frame , new HomeFragment()).commit();
    }

    private void open_transactions()
    {
        fm = getSupportFragmentManager();

        fm.beginTransaction().replace(R.id.main_frame , new TransactionFragment()).commit();
    }



}
