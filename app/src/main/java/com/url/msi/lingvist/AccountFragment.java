package com.url.msi.lingvist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;


public class AccountFragment extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Sent> wordList = new ArrayList<>();

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_account);

        //initList（）方法用于给List填充数据

        initList();

        recyclerView = findViewById(R.id.recycleview);

        //创建LinearLayoutManager，用于决定RecyclerView的布局方式

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AccountFragment.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        //创建适配器

        WordListAdapter adapter = new WordListAdapter(wordList);
        recyclerView.setAdapter(adapter);

    }

    private void initList() {

        for (int i = 0;i<3;i++){

            //通过条用构造方法，赋值
            Sent word = new Sent();
            wordList.add(word);

        }
    }
}