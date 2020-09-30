package com.company;

public class KomsuSehir {

    public int plaka_no;
    public String sehir_isim;
    public int mesafe;

    public int getPlaka_no() {
        return plaka_no;
    }

    public KomsuSehir(int plaka_no, String sehir_isim, int mesafe) {
        this.plaka_no = plaka_no;
        this.sehir_isim = sehir_isim;
        this.mesafe = mesafe;
    }

    public void setPlaka_no(int plaka_no) {
        this.plaka_no = plaka_no;
    }

    public String getSehir_isim() {
        return sehir_isim;
    }

    public void setSehir_isim(String sehir_isim) {
        this.sehir_isim = sehir_isim;
    }

    public int getMesafe() {
        return mesafe;
    }

    public void setMesafe(int mesafe) {
        this.mesafe = mesafe;
    }
}
