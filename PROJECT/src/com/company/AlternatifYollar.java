package com.company;

import java.util.ArrayList;

public class AlternatifYollar{
    public int uzaklik; //toplatilan mesafe
    public ArrayList<String> rota = new ArrayList<>();


    public AlternatifYollar(int uzaklik, ArrayList<String> rota) {
        this.uzaklik = uzaklik;
        this.rota = rota;
    }

    public int getUzaklik() {
        return uzaklik;
    }

    public void setUzaklik(int uzaklik) {
        this.uzaklik = uzaklik;
    }

    public ArrayList<String> getRota() {
        return rota;
    }

    public void setRota(ArrayList<String> rota) {
        this.rota = rota;
    }


}
