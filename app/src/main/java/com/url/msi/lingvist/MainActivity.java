package com.url.msi.lingvist;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.king.view.circleprogressview.CircleProgressView;

import java.util.ArrayList;
/*不用管*/


public class MainActivity extends AppCompatActivity {
    private CircleProgressView cpv;

    SQLiteDatabase db;
    AssetsDatabaseManager mg;
    ArrayList<Word> words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.main_navigation_bar);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        // 初始化，只需要调用一次
        AssetsDatabaseManager.initManager(getApplication());
        // 获取管理对象，因为数据库需要通过管理对象才能够获取
        mg = AssetsDatabaseManager.getManager();
        // 通过管理对象获取数据库
        db = mg.getDatabase("Lingvist.db");




        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new HomeFragment()).commit();
        }
        setWordDemande();

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

    //获取单词数据
    private void setWordDemande()
    {
        Cursor cursor = db.rawQuery("select * from s123", null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(2);//获取SQLite中的id列
            String letter = cursor.getString(0);
            int cp = cursor.getInt(1);
            Word word = new Word(id, letter, cp);
            //words.add(word);
            Log.i("word",letter);//调试用
            Log.i("cp", String.valueOf(cp));
        }

    }



}