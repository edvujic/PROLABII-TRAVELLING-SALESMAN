package com.company;

import java.util.ArrayList;
import java.util.List;
// asagidaki kod, https://java2blog.com/dijkstra-java/ uyarlanmasindan yazilmistir.
public class Dugum implements Comparable<Dugum> {

    private String isim;
    private List<Kenar> komsulukList;
    private boolean ziyaretEdilmis;
    private Dugum onceki;
    private double uzaklik = Double.MAX_VALUE;

    public Dugum(String isim) {
        this.isim = isim;
        this.komsulukList= new ArrayList<>();
    }

    public void komsuEkle(Kenar kenar) {
        this.komsulukList.add(kenar);
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public List<Kenar> getKomsulukList() {
        return komsulukList;
    }

    public void setKomsulukList(List<Kenar> komsulukList) {
        this.komsulukList = komsulukList;
    }

    public boolean isZiyaretEdilmis() {
        return ziyaretEdilmis;
    }

    public void setZiyaretEdilmis(boolean ziyaretEdilmis) {
        this.ziyaretEdilmis = ziyaretEdilmis;
    }

    public Dugum getOnceki() {
        return onceki;
    }

    public void setOnceki(Dugum onceki) {
        this.onceki = onceki;
    }

    public double getUzaklik() {
        return uzaklik;
    }

    public void setUzaklik(double uzaklik) {
        this.uzaklik = uzaklik;
    }

    @Override
    public String toString() {
        return this.isim;
    }

    @Override
    public int compareTo(Dugum digerDugum) {
        return Double.compare(this.uzaklik, digerDugum.getUzaklik());
    }
}