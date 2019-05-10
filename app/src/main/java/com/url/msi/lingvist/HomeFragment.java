package com.url.msi.lingvist;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.king.view.circleprogressview.CircleProgressView;
/*首页*/

public class HomeFragment extends Fragment {

    TextView textView;
    SQLiteDatabase db;
    AssetsDatabaseManager mg;


    private CircleProgressView cpv;



    public HomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment

        final Intent intent = new Intent(getActivity().getApplicationContext(),World.class);
        mg = AssetsDatabaseManager.getManager();
        // 通过管理对象获取数据库
        db = mg.getDatabase("Lingvist.db");
        int cc = findAll();




        cpv = view.findViewById(R.id.cpv);
        cpv.setShowTick(false);

        cpv.setProgressColorResource(R.color.colorPrimary);


        cpv.setLabelTextSize(48);
        cpv.setLabelTextColorResource(R.color.colorPrimary);
        cpv.showAnimation(cc,1500);//显示进度
        cpv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.cpv:
                        startActivity(intent);//开新界面
                }
            }
        });

        return view;
    }

    public int findAll()
    {
        int cc = 0;
        Cursor cursor = db.query("counts",null,null,null,null,null,null);
        if (cursor.moveToFirst())
        {
            cursor.move(0);
            cc = cursor.getInt(1);
        }


        return cc;
    }


}