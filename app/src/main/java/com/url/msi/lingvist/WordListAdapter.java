package com.url.msi.lingvist;

import android.support.v7.widget.RecyclerView;

import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.ViewHolder> {
    private List<Sent> wordList;
    public WordListAdapter(List<Sent> wordList){
        this.wordList = wordList;
    }
    @Override

}
