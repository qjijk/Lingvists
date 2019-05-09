package com.url.msi.lingvist;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class SQLite {

    SQLiteDatabase db;
    AssetsDatabaseManager mg;
    ArrayList<Word> words;
    static ArrayList<Sent> sents;

    public SQLite()
    {

        // 获取管理对象，因为数据库需要通过管理对象才能够获取
        mg = AssetsDatabaseManager.getManager();
        // 通过管理对象获取数据库
        db = mg.getDatabase("Lingvist.db");

        words = new ArrayList<Word>();
        sents = new ArrayList<Sent>();
    }

    public  void setWordDemande()
    {
        int j = 0;
        Cursor cursor = db.rawQuery("select * from s123 order by random()", null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(2);//获取SQLite中的id列
            String letter = cursor.getString(0);
            int cp = cursor.getInt(1);
            Word word = new Word(id, letter, cp);
            words.add(word);

            if (cp == 1 && j < 34)
            {
                JS(letter, cp, j);
                j++;
            }


            /*Log.i("word",letter);//调试用
            Log.i("cp", String.valueOf(cp));*/
        }

    }

    public static ArrayList<Sent> getSentsa()
    {
        return sents;
    }

    public void JS(final String key, final int cp, final int id)
    {
        new Thread(new Runnable() {
            String d1 = null;//句子
            String d2 = null;//翻译
            String d3 = null;//意思
            @Override
            public void run() {
                Log.i("w", key);
                try {
                    //URL url = new URL("http://dict-co.iciba.com/api/dictionary.php?w=go&key=6810AFA1CA6CAA1DC7C1CFA32F41DD4A");
                    Document document = Jsoup.connect("http://dict-co.iciba.com/api/dictionary.php?w=" + key + "&key=6810AFA1CA6CAA1DC7C1CFA32F41DD4A").get();//获取xml,key为所查询的单词
                    Elements elements = document.select("dict");
                    Elements e2 = elements.select("sent");
                    //每个词只获取第一个例句
                    for (int i = 1; i < e2.size() && i < 2; i++) {
                        d1 = e2.get(i).select("orig").text();
                        //d1 = d1.replace(key,"______");
                        int dd = d1.indexOf(key);
                        int ff = key.length();

                        d2 = e2.get(i).select("trans").text();
                        Log.i("E", d1);
                        Log.i("C", d2);
                    }
                    Elements e3 = elements.select("pos");//pos为词性
                    Elements e4 = elements.select("acceptation");//acceptation为意思
                    for (int i = 0; i < e3.size() && i < e4.size() && i < 2; i++)//最多只给出两个意思
                    {
                        if (d3 != null) {
                            d3 = d3 + "\n" + e3.get(i).select("pos").text() + e4.get(i).select("acceptation").text();//使其分行
                        } else {
                            d3 = e3.get(i).select("pos").text() + e4.get(i).select("acceptation").text();//如果只有一个单词则不分行
                        }
                    }

                    Sent sentss = new Sent(id, key, cp, d1, d2, d3,0);
                    sents.add(sentss);


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }).start();

    }










}