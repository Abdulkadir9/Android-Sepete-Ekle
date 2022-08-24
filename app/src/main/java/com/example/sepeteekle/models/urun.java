package com.example.sepeteekle.models;

public class urun {
    private int u_Id;
    private String u_Ad;
    private String u_Aciklama;
    private double u_Fiyat;
    private int u_Sepette;

    public urun(int u_Id, String u_Ad, String u_Aciklama, double u_Fiyat, int u_Sepette) {
        this.u_Id = u_Id;
        this.u_Ad = u_Ad;
        this.u_Aciklama = u_Aciklama;
        this.u_Fiyat = u_Fiyat;
        this.u_Sepette = u_Sepette;
    }

    public int getU_Id() {
        return u_Id;
    }

    public void setU_Id(int u_Id) {
        this.u_Id = u_Id;
    }

    public String getU_Ad() {
        return u_Ad;
    }

    public void setU_Ad(String u_Ad) {
        this.u_Ad = u_Ad;
    }

    public String getU_Aciklama() {
        return u_Aciklama;
    }

    public void setU_Aciklama(String u_Aciklama) {
        this.u_Aciklama = u_Aciklama;
    }

    public double getU_Fiyat() {
        return u_Fiyat;
    }

    public void setU_Fiyat(double u_Fiyat) {
        this.u_Fiyat = u_Fiyat;
    }

    public int getU_Sepette() {
        return u_Sepette;
    }

    public void setU_Sepette(int u_Sepette) {
        this.u_Sepette = u_Sepette;
    }
}
