package com.url.msi.lingvist;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;

import static java.lang.Math.floor;
import static java.lang.Math.random;


public class World extends AppCompatActivity {

    ArrayList<Sent> sentsArrayList;
    String key, sent, tra, senttra;
    TextView sentText, traText, senttraText, leandText;
    EditText inputText;
    Handler handler = null;
    int cont = 0;

    Button button ;

    SQLiteDatabase db;
    AssetsDatabaseManager mg;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world);
        sentsArrayList = MainActivity.getSentsa();




        senttraText = findViewById(R.id.wordTranslation);
        sentText = findViewById(R.id.sentence);
        traText = findViewById(R.id.sentenceTranslation);
        leandText = findViewById(R.id.learnedNum);
        button = findViewById(R.id.submit);

        changeWord();

        leandText.setText("0");//设置下面的进度
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cont++;
                String cc = String.valueOf(cont);
                leandText.setText(cc);
                changeWord();

            }
        });
    }
   /* private void Changestyle1()//转换风格
    {
        View view = getLayoutInflater().inflate(R.layout.fragment_world_next,null);
        LinearLayout layout = findViewById(R.id.line1);
        layout.addView(view);
    }*/

    private void changeWord()
    {

        int size = sentsArrayList.size();
        Log.i("size", String.valueOf(size));
        int position = (int) Math.round(random() * (size - 1));
        Sent sent = sentsArrayList.get(position);
        String key = sent.getWord();
        String sents = sent.getSent();
        String senttra = sent.getSenttra();
        String tra = sent.getTra();
        sentText.setText(sents);
        traText.setText(tra);
        senttraText.setText(senttra);



    }


}