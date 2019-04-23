package com.url.msi.lingvist;

public class Sent extends Word {


    private String sent;//句子
    private String tra;//意思
    private String senttra;//句子翻译





    public Sent(int id, String word, int cp, String sent, String tra, String senttra)
    {
        super(id, word, cp);
        this.sent = sent;
        this.senttra = senttra;
        this.tra = tra;
    }

    public void setSent(String sent) {
        this.sent = sent;
    }

    public void setSenttra(String senttra) {
        this.senttra = senttra;
    }

    public void setTra(String tra) {
        this.tra = tra;
    }

    public String getSenttra() {
        return senttra;
    }

    public String getSent() {
        return sent;
    }

    public String getTra() {
        return tra;
    }
}
