package com.url.msi.lingvist;

import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.king.view.circleprogressview.CircleProgressView;
/*不用管*/


public class MainActivity extends AppCompatActivity {
    private CircleProgressView cpv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.main_navigation_bar);
        bottomNav.setOnNavigationItemSelectedListener(navListener);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new HomeFragment()).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment setFragment = null;

            switch (menuItem.getItemId()){
                case R.id.home_nav:
                    setFragment = new HomeFragment();
                    break;
                case R.id.account_nav:
                    setFragment = new AccountFragment();
                    break;
                case R.id.notification_nav:
                    setFragment = new NotificationFragment();

                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, setFragment).commit();

            return true;
        }
    };
}