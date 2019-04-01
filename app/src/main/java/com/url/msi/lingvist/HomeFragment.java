package com.url.msi.lingvist;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.king.view.circleprogressview.CircleProgressView;
/*看名字就知道是干什么的了*/

public class HomeFragment extends Fragment {

    TextView textView;
    private CircleProgressView cpv;
    private int[] mShaderColors = new int[]{0xFF4FEAAC,0xFFA8DD51,0x00000000,0xFFA8DD51,0xFF4FEAAC};



    public HomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment
        textView = view.findViewById(R.id.t1);
        final Intent intent = new Intent(getActivity().getApplicationContext(),World.class);


        cpv = view.findViewById(R.id.cpv);
        cpv.setShowTick(false);

        cpv.setProgressColor(mShaderColors);
        cpv.showAnimation(70,1500);
        cpv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.cpv:
                        textView.setText("666666");
                        startActivity(intent);
                }
            }
        });

        return view;
    }


}