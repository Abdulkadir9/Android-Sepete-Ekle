package com.example.sepeteekle.models;

public class sepet {
    private int s_Id;
    private int s_UrunId;
    private int s_KullaniciId;

    public sepet(int s_Id, int s_UrunId, int s_KullaniciId) {
        this.s_Id = s_Id;
        this.s_UrunId = s_UrunId;
        this.s_KullaniciId = s_KullaniciId;
    }

    public int getS_Id() {
        return s_Id;
    }

    public void setS_Id(int s_Id) {
        this.s_Id = s_Id;
    }

    public int getS_UrunId() {
        return s_UrunId;
    }

    public void setS_UrunId(int s_UrunId) {
        this.s_UrunId = s_UrunId;
    }

    public int getS_KullaniciId() {
        return s_KullaniciId;
    }

    public void setS_KullaniciId(int s_KullaniciId) {
        this.s_KullaniciId = s_KullaniciId;
    }
}
