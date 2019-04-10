package com.url.msi.lingvist;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;

import static java.lang.Math.random;
/*不用管，还没用*/

public class World extends AppCompatActivity {

    ArrayList<Word> wordArrayList;
    String key, sent, tra, senttra;
    TextView sentText, traText, senttraText;
    EditText inputText;
    Handler handler = null;

    SQLiteDatabase db;
    AssetsDatabaseManager mg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world);

        wordArrayList = new ArrayList<Word>();

        AssetsDatabaseManager.initManager(getApplication());

        mg = AssetsDatabaseManager.getManager();
        //通过管理对象获取数据库
        db = mg.getDatabase("Lingvist.db");

        setWordArrayList();



        senttraText = (TextView) findViewById(R.id.wordTranslation);
        sentText = (TextView) findViewById(R.id.sentence);
        traText = (TextView) findViewById(R.id.sentenceTranslation);
        JS();

        /*handler = new Handler(){

            public void handleMassage(Message msg){
                Log.i("wsssss","qssssq");
                super.handleMessage(msg);
                String str = String.valueOf(msg.obj);
                sentText.setText(str);
                Log.i("wqwqwq","qweweq");
            }
        };*/
    }




    private void setWordArrayList() {
        Cursor cursor = db.rawQuery("select * from s123", null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(2);//获取SQLite中的id列
            String letter = cursor.getString(0);
            int cp = cursor.getInt(1);
            Word w = new Word(id, letter, cp);
            wordArrayList.add(w);

        }

    }


    // URL url = new URL(http://dict-co.iciba.com/api/dictionary.php?w=go&key=6810AFA1CA6CAA1DC7C1CFA32F41DD4A);
    //金山API获取xml

    public void JS() {

        int size = wordArrayList.size();
        int position = (int) Math.round(random() * (size - 1));
        Word word = wordArrayList.get(position);
        key = word.getWord();//获取单词
        int a = word.getCp();//获取词频

        new Thread(new Runnable() {
            String d1;
            String d2;
            String d3 = null;
            @Override
            public void run() {
                Log.i("w", key);
                try {
                    //URL url = new URL("http://dict-co.iciba.com/api/dictionary.php?w=go&key=6810AFA1CA6CAA1DC7C1CFA32F41DD4A");
                    Document document = Jsoup.connect("http://dict-co.iciba.com/api/dictionary.php?w="+key+"&key=6810AFA1CA6CAA1DC7C1CFA32F41DD4A").get();
                    Elements elements = document.select("dict");
                    Elements e2 = elements.select("sent");
                    //每个词只获取第一个例句
                    for (int i = 0; i < e2.size() && i < 1; i++){
                        d1 = e2.get(i).select("orig").text();
                        d2 = e2.get(i).select("trans").text();
                        Log.i("E",d1);
                        Log.i("C",d2);
                    }
                    Elements e3 = elements.select("pos");
                    Elements e4 = elements.select("acceptation");
                    for (int i = 0; i < e3.size() && i < e4.size() && i < 2; i++)//最多只给出两个意思
                    {
                        if(d3 != null)
                        {
                            d3 = d3 +"\n"+ e3.get(i).select("pos").text() + e4.get(i).select("acceptation").text();
                        }else{
                            d3 = e3.get(i).select("pos").text() + e4.get(i).select("acceptation").text();

                        }

                    }
                    Log.i("d3",d3);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            sentText.setText(d1);
                            traText.setText(d2);
                            senttraText.setText(d3);
                        }
                    });


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }).start();

    }

}