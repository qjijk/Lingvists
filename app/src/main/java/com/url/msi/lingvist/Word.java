package com.url.msi.lingvist;

public class Word {

    private int id;//序号
    private String word;//单词
    private int cp;//词频


    public Word(int id, String word, int cp)
    {
        this.cp = cp;
        this.word = word;
        this.id = id;

    }



    public void setCp(int cp) {
        this.cp = cp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getCp() {
        return cp;
    }

    public int getId() {
        return id;
    }

    public String getWord() {
        return word;
    }
}
