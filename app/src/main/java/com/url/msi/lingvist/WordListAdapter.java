package com.url.msi.lingvist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.ViewHolder> {

    private List<Sent> wordList;

    public WordListAdapter(List<Sent> wordList){
        this.wordList = wordList;
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_style,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView wordView;
        private TextView meanView;
        public ViewHolder(View itemView) { super(itemView);
        wordView = itemView.findViewById(R.id.wordShow);
        meanView = itemView.findViewById(R.id.meanShow);
        }
    }

    @Override

    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.wordView.setText(wordList.get(position).getWord());
        holder.meanView.setText(wordList.get(position).getTra());
    } //这个方法很简单了，返回List中的子项的个数

    @Override

    public int getItemCount() {
        return wordList.size();
    }


}
