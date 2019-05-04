package com.url.msi.lingvist;

import android.annotation.SuppressLint;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.brioal.selectabletextview.OnWordClickListener;
import com.brioal.selectabletextview.SelectableTextView;

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

    TextView sentText, traText, senttraText, leandText;
    EditText inputText;
    ProgressBar progressBar;
    SelectableTextView sentTexts;
    SQLiteDatabase db;
    AssetsDatabaseManager mg;

    String a = null;

    private SpansManager spansManager;

    int cont = 0;

    Button button, button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sentsArrayList = MainActivity.getSentsa();

        // 初始化，只需要调用一次
        AssetsDatabaseManager.initManager(getApplication());
        // 获取管理对象，因为数据库需要通过管理对象才能够获取
        mg = AssetsDatabaseManager.getManager();
        // 通过管理对象获取数据库
        db = mg.getDatabase("Lingvist.db");

        a = changeWord();


    }
   /* private void Changestyle1()//转换风格
    {
        View view = getLayoutInflater().inflate(R.layout.fragment_world_next,null);
        LinearLayout layout = findViewById(R.id.line1);
        layout.addView(view);
    }*/

    private String changeWord()
    {

        String key = null;
        int size = sentsArrayList.size();
        Log.i("size", String.valueOf(size));
        final int position = (int) Math.round(random() * (size - 1));
        Log.i("int", String.valueOf(position));
        Sent sent = sentsArrayList.get(position);
        String sents = sent.getSent();
        String senttra = sent.getSenttra();
        String tra = sent.getTra();
        final String wo = sent.getWord();
        if(sent.getN1() == 1 || sent.getN1() == 2)
        {
            setContentView(R.layout.activity_world);
            sentText = findViewById(R.id.sentence);
            init();
            button = findViewById(R.id.submit1);
            inputText = findViewById(R.id.et_input);
            spansManager = new SpansManager(this,sentText,inputText);
            key = sent.getWord();
            int cc = key.length() * 12;
            ReplaceSpan.textWidth = cc + 9;
            Log.i("cc", String.valueOf(ReplaceSpan.textWidth));


            String qq = "(?i)"+key;
            String ss = sents.replaceAll(qq, "____");
            Log.i("sent",sents);
            Log.i("key",key);
            spansManager.doFillBlank(ss);

            traText.setText(tra);
            senttraText.setText(senttra);
            leandText.setText(String.valueOf(cont));
            sent.setN1(1);
            progressBar.setProgress(cont);
            butt1();

        }
        else if (sent.getN1() == 0)
        {
            setContentView(R.layout.activivy_select);
            init();
            sentTexts = findViewById(R.id.sentenceSelect);
            button2 = findViewById(R.id.submit2);
            traText.setText(tra);
            senttraText.setText(senttra);
            sentTexts.setText(sents);
            leandText.setText(String.valueOf(cont));
            progressBar.setProgress(cont);
            Log.i("wo", wo);
           // Log.i("a", a);

            sentTexts.setOnWordClickListener(new OnWordClickListener() {
                @Override
                protected void onNoDoubleClick(String s) {
                    Log.i("lis", s);

                    if(s.equals(wo))
                    {
                        Toast.makeText(getApplicationContext(),wo,Toast.LENGTH_LONG);
                        cont++;
                        String cc = String.valueOf(cont);
                        leandText.setText(cc);
                        a = changeWord();
                    }
                    else
                    {
                        Toast toast = Toast.makeText(getApplicationContext(),wo,Toast.LENGTH_LONG);
                        toast.show();
                    }

                }
            });
            butt2();
        }
        else
        {

        }
        return key;

    }

    private void init()
    {
        senttraText = findViewById(R.id.wordTranslation);
        traText = findViewById(R.id.sentenceTranslation);
        leandText = findViewById(R.id.learnedNum);
        progressBar = findViewById(R.id.progress_bar_healthy);



    }

    private void butt1()
    {
        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                inputText.setHint(null);

                Log.i("keykey",inputText.getText().toString());
                if (inputText.getText().toString().equals(a))
                {
                    cont++;
                    String cc = String.valueOf(cont);
                    leandText.setText(cc);
                    inputText.setText(null);
                    a = changeWord();
                }else {
                    inputText.setText(null);
                    inputText.setHintTextColor(R.color.red);
                    inputText.setHint(a);
                }


            }
        });
    }

    public void butt2()
    {
        button2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                cont++;
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



}