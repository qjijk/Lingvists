package com.url.msi.lingvist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.king.view.circleprogressview.CircleProgressView;
//import com.android.support:appcompat-v7:28.0.0;

/*不用管，还没用*/


public class NotificationFragment extends Fragment {
    TextView learndView,countView;
    EditText editText;
    SQLiteDatabase db;
    AssetsDatabaseManager mg;
    private int sum, lea;
    public static int newws = 34;




    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        editText = view.findViewById(R.id.newWord);
        editText.setHint(String.valueOf(newws));
        editText.setOnKeyListener(onKeyListener);

        countView = view.findViewById(R.id.sum);
        learndView = view.findViewById(R.id.wordNum);
        sum = SQLite.getSum();
        countView.setText(String.valueOf(sum));
        lea = SQLite.getLea();
        learndView.setText(String.valueOf(lea));



        return view;


    }
    View.OnKeyListener onKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            Log.d("i", String.valueOf(i));
            if (i == KeyEvent.KEYCODE_ENTER)
            {
                newws = Integer.parseInt(editText.getText().toString());
                Log.d("成功", String.valueOf(newws));

                return true;
            }

            return false;
        }
    };

}