package com.url.msi.lingvist;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.king.view.circleprogressview.CircleProgressView;


public class HomeFragment extends Fragment {

    TextView textView;
    private CircleProgressView cpv;
    private int[] mShaderColors = new int[]{0xFF4FEAAC,0xFFA8DD51,0xFFE8D30F,0xFFA8DD51,0xFF4FEAAC};



    public HomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment
        textView = view.findViewById(R.id.t1);
        textView.setText("测试");
        cpv = view.findViewById(R.id.cpv);
        cpv.setShowTick(false);

        cpv.setProgressColor(mShaderColors);
        cpv.showAnimation(70,1500);

        return view;
    }

}