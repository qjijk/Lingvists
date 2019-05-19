package com.url.msi.lingvist;

import android.annotation.SuppressLint;
import android.content.ContentValues;
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
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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
    int c = 0;

    Button button, button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        sentsArrayList = SQLite.getSentsa();

        // 初始化，只需要调用一次
        /*AssetsDatabaseManager.initManager(getApplication());*/
        // 获取管理对象，因为数据库需要通过管理对象才能够获取
        mg = AssetsDatabaseManager.getManager();
        // 通过管理对象获取数据库
        db = mg.getDatabase("Lingvist.db");
        cont = findAll();

        a = changeWord();




    }
    private int findAll()
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
        //Log.i("size", String.valueOf(size));
        final int position = (int) Math.round(random() * (size - 1));
        Log.i("int", String.valueOf(position));
        Sent sent = sentsArrayList.get(position);
        String sents = sent.getSent();
        int id = sent.getId();
        final String senttra = sent.getSenttra();
        final String tra = sent.getTra();
        final String wo = sent.getWord();
        if(sent.getN1() == 1 || sent.getN1() == 2)
        {
            setContentView(R.layout.activity_world);
            sentText = findViewById(R.id.sentence);
            init();
            button = findViewById(R.id.submit1);
            inputText = findViewById(R.id.et_input);
            inputText.setHorizontallyScrolling(false);
            inputText.setMaxLines(1);
            inputText.setOnKeyListener(onKeyListener);
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
            sent.setN1(sent.getN1()+1);
            progressBar.setProgress(cont);
            updateDb(sent.getN1(),key);
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
            sent.setN1(sent.getN1()+1);
           // Log.i("a", a);

            sentTexts.setOnWordClickListener(new OnWordClickListener() {
                @Override
                protected void onNoDoubleClick(String s) {
                    Log.i("lis", s);

                    if(s.equalsIgnoreCase(wo))
                    {
                        Toast.makeText(getApplicationContext(),wo,Toast.LENGTH_LONG);
                        cont++;
                        saveCount(cont);
                        saveDb(wo, senttra);
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
        else if(sent.getN1() == 5)
        {
            String del = "delete from s123 where C2 = " + id;
            db.execSQL(del);
        }
        if (cont == 100)
        {
            SQLite sqL = new SQLite();

            sqL.setWordDemande(NotificationFragment.newws);
            sentsArrayList = SQLite.getSentsa();
        }
        return key;

    }

    View.OnKeyListener onKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            Log.d("i", String.valueOf(i));
            if (i == KeyEvent.KEYCODE_ENTER)
            {
                Log.d("成功","成功");
                return true;
            }

            return false;
        }
    };


    private void updateDb(int n1, String word)
    {
        String sql = "update worded set C3 = "+(n1-1)+" where C1 = '"+ word+"'";
        db.execSQL(sql);

    }

    private void saveCount(int conts)
    {
        String sql = "update counts set C2 = "+conts+" where C1 = 0";
        db.execSQL(sql);


    }

    private void saveDb(String wo, String tra)
    {
        ContentValues cValue = new ContentValues();
        cValue.put("C1", wo);
        cValue.put("C4", tra);
        db.replace("worded", null, cValue);
        c++;
        Log.d("c", String.valueOf(c));
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
                    saveCount(cont);
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