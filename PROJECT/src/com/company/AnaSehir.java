package com.company;

import java.util.ArrayList;

public class AnaSehir {

    public int ana_plaka;
    public String ana_sehir;
    public ArrayList<KomsuSehir> komsu_sehirler = new ArrayList<KomsuSehir>();
    public int komsu_sayisi;
    public int sehir_say=81;

    public AnaSehir() {
    }

    public AnaSehir(int ana_plaka, String ana_sehir, int komsu_sayisi) {
        this.ana_plaka = ana_plaka;
        this.ana_sehir = ana_sehir;
        this.komsu_sayisi = komsu_sayisi;
    }
    public int getAna_plaka() {
        return ana_plaka;
    }

    public void setAna_plaka(int ana_plaka) {
        this.ana_plaka = ana_plaka;
    }

    public String getAna_sehir() {
        return ana_sehir;
    }

    public void setAna_sehir(String ana_sehir) {
        this.ana_sehir = ana_sehir;
    }

    public ArrayList<KomsuSehir> getKomsu_sehirler() {
        return komsu_sehirler;
    }

    public void setKomsu_sehirler(ArrayList<KomsuSehir> komsu_sehirler) {
        this.komsu_sehirler = komsu_sehirler;
    }

    public int getKomsu_sayisi() {
        return komsu_sayisi;
    }

    public void setKomsu_sayisi(int komsu_sayisi) {
        this.komsu_sayisi = komsu_sayisi;
    }
}
