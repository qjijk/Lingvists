package com.url.msi.lingvist;

public class Sent extends Word {


    private String sent;//句子
    private String tra;//意思
    private String senttra;//句子翻译
    private int n1;





    public Sent(int id, String word, int cp, String sent, String tra, String senttra, int n1)
    {
        super(id, word, cp);
        this.sent = sent;
        this.senttra = senttra;
        this.tra = tra;
        this.n1 = n1;
    }

    public int getN1() {
        return n1;
    }

    public void setN1(int n1) {
        this.n1 = n1;
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
