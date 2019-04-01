package com.url.msi.lingvist;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.king.view.circleprogressview.CircleProgressView;
//import com.android.support:appcompat-v7:28.0.0;

/*不用管，还没用*/


public class NotificationFragment extends Fragment {

    private CircleProgressView cpv;



    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_notification, container, false);





        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_notification, container, false);
        return view;

        //circleProgressView.showAnimation(80,3000);
    }

}