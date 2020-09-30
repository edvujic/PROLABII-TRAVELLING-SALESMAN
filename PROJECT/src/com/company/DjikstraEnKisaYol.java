package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
// asagidaki kod, https://java2blog.com/dijkstra-java/ uyarlanmasindan yazilmistir.
public class DjikstraEnKisaYol {

        public void enKisaYolHesapla(Dugum kaynakDugum){

            kaynakDugum.setUzaklik(0);
            PriorityQueue<Dugum> oncelikKuyrugu = new PriorityQueue<>();
            oncelikKuyrugu.add(kaynakDugum);
            kaynakDugum.setZiyaretEdilmis(true);

            while( !oncelikKuyrugu.isEmpty() ){
                Dugum suankiDugum = oncelikKuyrugu.poll();

                for(Kenar kenar: suankiDugum.getKomsulukList()){

                    Dugum v = kenar.getHedefDugum();
                    if(!v.isZiyaretEdilmis())
                    {
                        double yeniUzaklik = suankiDugum.getUzaklik() + kenar.getAgirlik();

                        if( yeniUzaklik < v.getUzaklik() ){
                            oncelikKuyrugu.remove(v);
                            v.setUzaklik(yeniUzaklik);
                            v.setOnceki(suankiDugum);
                            oncelikKuyrugu.add(v);
                        }
                    }
                }
                suankiDugum.setZiyaretEdilmis(true);

            }
            oncelikKuyrugu.removeAll(oncelikKuyrugu);
        }

    public ArrayList<String> getEnKisaYol(Dugum hedefVertex){
        ArrayList<String> yol = new ArrayList<>();

        for(Dugum dugum =hedefVertex;dugum!=null;dugum=dugum.getOnceki()){
            yol.add(dugum.getIsim());
        }
        Collections.reverse(yol);

        return yol;

    }


}




