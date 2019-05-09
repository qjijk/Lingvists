package com.url.msi.lingvist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;


public class AccountFragment extends Fragment {

    ArrayList<Sent> sentsArrayList;
    private RecyclerView recyclerView;
    private List<Sent> wordList = new ArrayList<>();

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);


        initList();
        recyclerView = view.findViewById(R.id.recycleview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        WordListAdapter adapter = new WordListAdapter(wordList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void initList() {
        sentsArrayList = SQLite.getSentd();
        for (int i = 0;i<sentsArrayList.size();i++){
            //通过条用构造方法，赋值
            Sent word = sentsArrayList.get(i);
            wordList.add(word);
            Log.i("abcd", String.valueOf(word));

        }
    }



}