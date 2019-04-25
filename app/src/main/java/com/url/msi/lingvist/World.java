package com.url.msi.lingvist;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
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


public class World extends AppCompatActivity implements ReplaceSpan.OnClickListener{

    ArrayList<Sent> sentsArrayList;
    String key, sent, tra, senttra;
    TextView sentText, traText, senttraText, leandText;
    EditText inputText;
    Handler handler = null;
    String a = null;

    private SpansManager spansManager;



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

        inputText = findViewById(R.id.et_input);
        spansManager = new SpansManager(this,sentText,inputText);

        leandText.setText("0");//设置下面的进度
        a = changeWord();
        butt();

    }
   /* private void Changestyle1()//转换风格
    {
        View view = getLayoutInflater().inflate(R.layout.fragment_world_next,null);
        LinearLayout layout = findViewById(R.id.line1);
        layout.addView(view);
    }*/

    private String changeWord()
    {

        int size = sentsArrayList.size();
        Log.i("size", String.valueOf(size));
        int position = (int) Math.round(random() * (size - 1));
        Sent sent = sentsArrayList.get(position);
        String key = sent.getWord();
        String sents = sent.getSent();
        String senttra = sent.getSenttra();
        String tra = sent.getTra();
        String qq = "(?i)"+key;
        String ss = sents.replaceAll(qq, "____");
        Log.i("sent",sents);
        Log.i("key",key);
        spansManager.doFillBlank(ss);

        traText.setText(tra);
        senttraText.setText(senttra);
        return key;

    }

    private void butt()
    {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String b = "["+a+"]";
                Log.i("b",b);

                Log.i("keykey",inputText.getText().toString());
                if (inputText.getText().toString().equals(a))
                {
                    cont++;
                    String cc = String.valueOf(cont);
                    leandText.setText(cc);
                    inputText.setText(null);
                    a = changeWord();
                }else {
                    inputText.setText(a);
                }


            }
        });
    }

    public void OnClick(TextView v, int id, ReplaceSpan span)
    {
        spansManager.setData(inputText.getText().toString(), null, spansManager.mOldSpan);
        spansManager.mOldSpan = id;
        inputText.setText(TextUtils.isEmpty(span.mText)?"":span.mText);
        inputText.setSelection(span.mText.length());
        span.mText = "";
        RectF rf = spansManager.drawSpanRect(span);
        spansManager.setEtXY(rf);
        spansManager.setSpanChecked(id);
    }

    private String getMyAns()
    {
        spansManager.setLastCheckedSpanText(inputText.getText().toString());
        return spansManager.getMyAnswer().toString();
    }

}