package com.company;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.swingViewer.DefaultView;
import org.graphstream.ui.swingViewer.LayerRenderer;
import org.graphstream.ui.view.Viewer;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class UcuncuEkran extends JFrame{
    public JPanel calisanekran;
    public JTextField textField1;

    public static ArrayList<AnaSehir> sehirler = new ArrayList<AnaSehir>();
    public static Dugum[] dugum = new Dugum[81];
    public static int a = 0 ;
    public static ArrayList<AlternatifYollar> tum_yol = new ArrayList<>();
    public static ArrayList<String> gezilecekler = new ArrayList<>();
    public static ArrayList<String> al_gezilecekler = new ArrayList<>();
    public static int s_say;
    public static ArrayList<String> rota = new ArrayList<>();

    public UcuncuEkran() throws IOException {
        Graph graph1 = new MultiGraph("Graph");

        Graph graph2= new MultiGraph("Graph");
        Graph graph3 = new MultiGraph("Graph");
        Graph graph4 = new MultiGraph("Graph");
        Graph graph5 = new MultiGraph("Graph");
        Dugum son_sehir = null;
        FileInputStream fis = new FileInputStream("sehir.txt");
        Scanner sc = new Scanner(fis);
        int i;
        int k;
        while (sc.hasNextLine()) {
            sehir_atama(sc.nextLine());
        }
        sc.close();
        Giris sehir_sayisi_giris = new Giris();
        İkinciEkran alinan_gezilecekler = new İkinciEkran();

        System.out.println("alınan sehir sayisi: " + sehir_sayisi_giris.sehir_sayisi);
        s_say = sehir_sayisi_giris.sehir_sayisi;

       for(k = 0 ; k < s_say ; k++) {
           gezilecekler.add(alinan_gezilecekler.TextAlanlari.get(k).getText());
        }
        for (int j = 0; j < gezilecekler.size(); j++) {
            System.out.println(gezilecekler.get(j));
        }
        int kopyala = gezilecekler.size();
        System.out.println(gezilecekler.size());
        al_gezilecekler = (ArrayList<String>) gezilecekler.clone();
        List<String> stringList = new ArrayList<String>();
        stringList.addAll(al_gezilecekler);
        List<List<String>> myLists = listPermutations(stringList);

        //vertex'lere atama baslangic
        for(i = 0; i<sehirler.size(); i++)
        {
            dugum[i] = new Dugum(sehirler.get(i).ana_sehir);
        }
        for(k = 0; k<sehirler.size(); k++)
        {
            for(int b = 0 ; b<sehirler.get(k).komsu_sayisi; b++)
            {
                dugum[k].komsuEkle(new Kenar(sehirler.get(k).komsu_sehirler.get(b).mesafe,
                        dugum[k],
                        sehir_don(String.valueOf(sehirler.get(k).komsu_sehirler.get(b).sehir_isim)))
                );
            }
        }
        //vertex'lere atama bitis


        //gezilecekleri gezmek baslangic
        Dugum start = dugum[40];
        DjikstraEnKisaYol kisaYol = new DjikstraEnKisaYol();
        ArrayList<Integer> uzakliklar = new ArrayList<>();
        int gidis = 0;
        kisaYol.enKisaYolHesapla(start);

        if(gezilecekler.size() == 1){
            int uzak_tek;
            ArrayList<String> rota_tek = new ArrayList<>();
            uzak_tek = (int) sehir_don(gezilecekler.get(0)).getUzaklik();
            System.out.println("Tek uzaklık: " + uzak_tek);
            System.out.println("Tek rota: " + kisaYol.getEnKisaYol(sehir_don(gezilecekler.get(0))));
            rota_tek.addAll(kisaYol.getEnKisaYol(sehir_don(gezilecekler.get(0))));

            for (i = 0; i < dugum.length; i++) {
                dugum[i].setUzaklik(Double.MAX_VALUE);
                dugum[i].setZiyaretEdilmis(false);
                dugum[i].setOnceki(null);
            }

            kisaYol.enKisaYolHesapla(sehir_don(gezilecekler.get(0)));
            System.out.println("Dönüş : ");
            System.out.println("Dönüş yolu mesafe : " + dugum[40].getUzaklik());
            System.out.println("Toplam mesafe: " + (uzak_tek + dugum[40].getUzaklik()));
            System.out.println("Dönüş Yolu rota: " + kisaYol.getEnKisaYol(dugum[40]));
            int j = 1;
            rota_tek.addAll(kisaYol.getEnKisaYol(dugum[40]));
            for (i = 0; i < rota_tek.size(); i++) {
                if (j != rota_tek.size()) {
                    if (rota_tek.get(i).equals(rota_tek.get(j))) {
                        rota_tek.remove(i);
                    }
                    j++;
                }
            }
            tum_yol.add(new AlternatifYollar(uzak_tek,rota_tek));
            System.out.println("En kisa yol: " + rota_tek);
            dosyaya_yazdir(tum_yol);
            graph_ciz(graph1, tum_yol.get(0).getRota(), "En kisa yol");
        }
        else {

            while (!gezilecekler.isEmpty()) {

                for (i = 0; i < gezilecekler.size(); i++) {
                    uzakliklar.add(i, (int) sehir_don(gezilecekler.get(i)).getUzaklik());
                    System.out.println(start.getIsim() + "dan " + sehir_don(gezilecekler.get(i)) + " isimli sehre olan mesafe: " + uzakliklar.get(i));
                    System.out.println("Izlenen yol: " + kisaYol.getEnKisaYol(sehir_don(gezilecekler.get(i))));
                }
                //boslatma islemi
                rota.addAll(kisaYol.getEnKisaYol(sehir_don(gezilecekler.get(yer_bul(uzakliklar)))));
                for (i = 0; i < dugum.length; i++) {
                    dugum[i].setUzaklik(Double.MAX_VALUE);
                    dugum[i].setZiyaretEdilmis(false);
                    dugum[i].setOnceki(null);

                }
                gidis += en_kucuk(uzakliklar);
                start = sehir_don(gezilecekler.get(yer_bul(uzakliklar)));
                System.out.println(yer_bul(uzakliklar));
                kisaYol.enKisaYolHesapla(start);
                gezilecekler.remove(yer_bul(uzakliklar));
                gezilecekler.trimToSize();
                uzakliklar.clear();
                System.out.println("xxxxxxxxxxxxxxxxxxxxxxxx");

                if (gezilecekler.size() == 1) {
                    son_sehir = sehir_don(gezilecekler.get(0));
                }

            }
            for (i = 0; i < dugum.length; i++) {
                dugum[i].setUzaklik(Double.MAX_VALUE);
                dugum[i].setZiyaretEdilmis(false);
                dugum[i].setOnceki(null);

            }
            kisaYol.enKisaYolHesapla(son_sehir);
            System.out.println(son_sehir.getIsim() + "dan " + dugum[40].getIsim() + " isimli sehre olan mesafe: " + (int) dugum[40].getUzaklik());
            System.out.println("Izlenen yol: " + kisaYol.getEnKisaYol(dugum[40]));
            int donus = (int) dugum[40].getUzaklik();
            System.out.println("Gidis mesafesi: " + gidis);
            System.out.println("Donus mesafesi: " + donus);
            System.out.println("Toplam mesafesi: " + (gidis + donus));
            rota.addAll(kisaYol.getEnKisaYol(dugum[40]));
            int j = 1;
            for (i = 0; i < rota.size(); i++) {
                if (j != rota.size()) {
                    if (rota.get(i).equals(rota.get(j))) {
                        rota.remove(i);
                    }
                    j++;
                }
            }
            System.out.println("En kisa yol: " + rota);
            for (i = 0; i < dugum.length; i++) {
                dugum[i].setUzaklik(Double.MAX_VALUE);
                dugum[i].setZiyaretEdilmis(false);
                dugum[i].setOnceki(null);
            }
            System.out.println("gezilecekler : " +  kopyala);
            int permutasyon_sayisi;
            if(kopyala > 1 && kopyala < 8){
                permutasyon_sayisi = myLists.size();
                for (i = 0; i < permutasyon_sayisi; i++) {
                    alternatif_yol(dugum[40], myLists.get(i));
                }
            }else{
                permutasyon_sayisi = factorial(7) ;
                for (i = 0; i < permutasyon_sayisi; i++) {
                    alternatif_yol(dugum[40], myLists.get(i));
                }
            }
            Collections.sort(tum_yol, Comparator.comparing(AlternatifYollar::getUzaklik));
            System.out.println("Siralanmis yollar");

            for (i = 0; i < tum_yol.size(); i++) {
                System.out.println(tum_yol.get(i).getUzaklik());
                System.out.println(tum_yol.get(i).getRota());
            }
            dosyaya_yazdir(tum_yol);
            if(al_gezilecekler.size()==2)
            {
                graph_ciz(graph1, tum_yol.get(0).getRota(), "En kisa yol");
                graph_ciz(graph2, tum_yol.get(1).getRota(), "2. En kisa yol");
            }
            else
            {
                graph_ciz(graph1, tum_yol.get(0).getRota(), "En kisa yol");
                graph_ciz(graph2, tum_yol.get(1).getRota(), "2. En kisa yol");
                graph_ciz(graph3, tum_yol.get(2).getRota(), "3. En kisa yol");
                graph_ciz(graph4, tum_yol.get(3).getRota(), "4. En kisa yol");
                graph_ciz(graph5, tum_yol.get(4).getRota(), "5. En kisa yol");
            }
        }
    }
    public static void graph_ciz(Graph graph1, ArrayList<String> rota, String s)
    {
        Viewer viewer;
        grafa_ekle(graph1);
        for(Node node : graph1)
        {
            node.addAttribute("ui.label", node.getId());
        }
        int b=0;
            for(int j = 0, k=1; k<rota.size(); j++, k++)
            {

                    System.out.println(rota.get(j) + rota.get(k)) ;
                    graph1.addEdge(String.valueOf(b), rota.get(j) , rota.get(k), true);
                b++;
            }

            graph1.addAttribute("ui.quality");
            graph1.addAttribute("ui.antialias");
            graph1.setStrict(true);
            viewer = graph1.display();
            viewer.disableAutoLayout();
        DefaultView view = (DefaultView) viewer.getDefaultView();
        view.setBackLayerRenderer(new LayerRenderer() {
            @Override
            public void render(Graphics2D graphics2D, GraphicGraph graphicGraph, double v, int i, int i1, double v1, double v2, double v3, double v4) {
                graphics2D.setColor(Color.red);
                graphics2D.drawString(s, 10, 30);
            }
        });
    }
    public static Graph grafa_ekle(Graph graph1)
    {
        graph1.addNode("Edirne");
        Node n = graph1.getNode("Edirne");
        n.addAttribute("xy", 0,6);
        graph1.addNode("Canakkale");
        Node n2 = graph1.getNode("Canakkale");
        n2.addAttribute("xy", 0,4);
        graph1.addNode("Izmir");
        Node n3= graph1.getNode("Izmir");
        n3.addAttribute("xy", 0,2);
        graph1.addNode("Aydin");
        Node n4= graph1.getNode("Aydin");
        n4.addAttribute("xy", 0,1);
        graph1.addNode("Kirklareli");
        Node n5= graph1.getNode("Kirklareli");
        n5.addAttribute("xy", 0.5,6);
        graph1.addNode("Tekirdag");
        Node n6= graph1.getNode("Tekirdag");
        n6.addAttribute("xy", 0.5,5);
        graph1.addNode("Balikesir");
        Node n7= graph1.getNode("Balikesir");
        n7.addAttribute("xy", 0.5,4);
        graph1.addNode("Manisa");
        Node n8= graph1.getNode("Manisa");
        n8.addAttribute("xy", 0.5,3);
        graph1.addNode("Istanbul");
        Node n9= graph1.getNode("Istanbul");
        n9.addAttribute("xy", 1,6);
        graph1.addNode("Yalova");
        Node n10= graph1.getNode("Yalova");
        n10.addAttribute("xy", 1,5);
        graph1.addNode("Bursa");
        Node n11= graph1.getNode("Bursa");
        n11.addAttribute("xy", 1,4);
        graph1.addNode("Kutahya");
        Node n12= graph1.getNode("Kutahya");
        n12.addAttribute("xy", 1,3);
        graph1.addNode("Usak");
        Node n13= graph1.getNode("Usak");
        n13.addAttribute("xy", 1,2);
        graph1.addNode("Denizli");
        Node n14= graph1.getNode("Denizli");
        n14.addAttribute("xy", 1,1);
        graph1.addNode("Mugla");
        Node n15= graph1.getNode("Mugla");
        n15.addAttribute("xy", 1,0);
        graph1.addNode("Kocaeli");
        Node n16= graph1.getNode("Kocaeli");
        n16.addAttribute("xy", 1.5,6);
        graph1.addNode("Bilecik");
        Node n17= graph1.getNode("Bilecik");
        n17.addAttribute("xy", 1.5,5);
        graph1.addNode("Eskisehir");
        Node n18= graph1.getNode("Eskisehir");
        n18.addAttribute("xy", 1.5,4);
        graph1.addNode("Afyonkarahisar");
        Node n19= graph1.getNode("Afyonkarahisar");
        n19.addAttribute("xy", 1.5,3);
        graph1.addNode("Isparta");
        Node n20= graph1.getNode("Isparta");
        n20.addAttribute("xy", 1.5,2);
        graph1.addNode("Burdur");
        Node n21= graph1.getNode("Burdur");
        n21.addAttribute("xy", 1.5,1);
        graph1.addNode("Antalya");
        Node n22= graph1.getNode("Antalya");
        n22.addAttribute("xy", 1.5,0);
        graph1.addNode("Sakarya");
        Node n23= graph1.getNode("Sakarya");
        n23.addAttribute("xy", 2,6);
        graph1.addNode("Bolu");
        Node n24= graph1.getNode("Bolu");
        n24.addAttribute("xy", 2,5);
        graph1.addNode("Ankara");
        Node n25= graph1.getNode("Ankara");
        n25.addAttribute("xy", 2,4);
        graph1.addNode("Konya");
        Node n26= graph1.getNode("Konya");
        n26.addAttribute("xy", 2,3);
        graph1.addNode("Karaman");
        Node n27= graph1.getNode("Karaman");
        n27.addAttribute("xy", 2,2);
        graph1.addNode("Mersin");
        Node n28= graph1.getNode("Mersin");
        n28.addAttribute("xy", 2,1);
        graph1.addNode("Duzce");
        Node n29= graph1.getNode("Duzce");
        n29.addAttribute("xy", 2.5,6);
        graph1.addNode("Zonguldak");
        Node n30= graph1.getNode("Zonguldak");
        n30.addAttribute("xy", 3,6);
        graph1.addNode("Bartin");
        Node n31= graph1.getNode("Bartin");
        n31.addAttribute("xy", 3.5,6);
        graph1.addNode("Karabuk");
        Node n32= graph1.getNode("Karabuk");
        n32.addAttribute("xy", 3.5,5);
        graph1.addNode("Aksaray");
        Node n33= graph1.getNode("Aksaray");
        n33.addAttribute("xy", 3.5,3);
        graph1.addNode("Kastamonu");
        Node n34= graph1.getNode("Kastamonu");
        n34.addAttribute("xy", 4,6);
        graph1.addNode("Cankiri");
        Node n35= graph1.getNode("Cankiri");
        n35.addAttribute("xy", 3.5,4);
        graph1.addNode("Kirikkale");
        Node n36= graph1.getNode("Kirikkale");
        n36.addAttribute("xy", 4,4);
        graph1.addNode("Kirsehir");
        Node n37= graph1.getNode("Kirsehir");
        n37.addAttribute("xy", 4,3);
        graph1.addNode("Nevsehir");
        Node n38= graph1.getNode("Nevsehir");
        n38.addAttribute("xy", 4,2);
        graph1.addNode("Nigde");
        Node n39= graph1.getNode("Nigde");
        n39.addAttribute("xy", 4,1);
        graph1.addNode("Sinop");
        Node n40= graph1.getNode("Sinop");
        n40.addAttribute("xy", 4.5,6);
        graph1.addNode("Corum");
        Node n41= graph1.getNode("Corum");
        n41.addAttribute("xy", 4.5,5);
        graph1.addNode("Yozgat");
        Node n42= graph1.getNode("Yozgat");
        n42.addAttribute("xy", 4.5,4);
        graph1.addNode("Kayseri");
        Node n43= graph1.getNode("Kayseri");
        n43.addAttribute("xy", 4.5,3);
        graph1.addNode("Adana");
        Node n44= graph1.getNode("Adana");
        n44.addAttribute("xy", 4.5,2);
        graph1.addNode("Samsun");
        Node n45= graph1.getNode("Samsun");
        n45.addAttribute("xy", 5,6);
        graph1.addNode("Amasya");
        Node n46= graph1.getNode("Amasya");
        n46.addAttribute("xy", 5,5);
        graph1.addNode("Osmaniye");
        Node n47= graph1.getNode("Osmaniye");
        n47.addAttribute("xy", 5,2);
        graph1.addNode("Hatay");
        Node n48= graph1.getNode("Hatay");
        n48.addAttribute("xy", 5,1);
        graph1.addNode("Ordu");
        Node n49= graph1.getNode("Ordu");
        n49.addAttribute("xy", 5.5,6);
        graph1.addNode("Tokat");
        Node n50= graph1.getNode("Tokat");
        n50.addAttribute("xy", 5.5,5);
        graph1.addNode("Sivas");
        Node n51= graph1.getNode("Sivas");
        n51.addAttribute("xy", 5.5,4);
        graph1.addNode("Malatya");
        Node n52= graph1.getNode("Malatya");
        n52.addAttribute("xy", 5.5,2);
        graph1.addNode("Kahramanmaras");
        Node n53= graph1.getNode("Kahramanmaras");
        n53.addAttribute("xy", 5.5,1);
        graph1.addNode("Giresun");
        Node n54= graph1.getNode("Giresun");
        n54.addAttribute("xy", 6,6);
        graph1.addNode("Gumushane");
        Node n55= graph1.getNode("Gumushane");
        n55.addAttribute("xy", 6,5);
        graph1.addNode("Erzincan");
        Node n56= graph1.getNode("Erzincan");
        n56.addAttribute("xy", 6,4);
        graph1.addNode("Tunceli");
        Node n57= graph1.getNode("Tunceli");
        n57.addAttribute("xy", 6,3);
        graph1.addNode("Elazig");
        Node n58= graph1.getNode("Elazig");
        n58.addAttribute("xy", 6,2);
        graph1.addNode("Adiyaman");
        Node n59= graph1.getNode("Adiyaman");
        n59.addAttribute("xy", 6,1);
        graph1.addNode("Sanliurfa");
        Node n60= graph1.getNode("Sanliurfa");
        n60.addAttribute("xy", 6,0);
        graph1.addNode("Trabzon");
        Node n61= graph1.getNode("Trabzon");
        n61.addAttribute("xy", 6.5,6);
        graph1.addNode("Bayburt");
        Node n62= graph1.getNode("Bayburt");
        n62.addAttribute("xy", 6.5,5);
        graph1.addNode("Bingol");
        Node n63= graph1.getNode("Bingol");
        n63.addAttribute("xy", 6.5,3);
        graph1.addNode("Diyarbakir");
        Node n64= graph1.getNode("Diyarbakir");
        n64.addAttribute("xy", 6.5,2);
        graph1.addNode("Mardin");
        Node n65= graph1.getNode("Mardin");
        n65.addAttribute("xy", 6.5,0);
        graph1.addNode("Rize");
        Node n66= graph1.getNode("Rize");
        n66.addAttribute("xy", 7,6);
        graph1.addNode("Erzurum");
        Node n81= graph1.getNode("Erzurum");
        n81.addAttribute("xy", 7,5);
        graph1.addNode("Mus");
        Node n67= graph1.getNode("Mus");
        n67.addAttribute("xy", 7,3);
        graph1.addNode("Batman");
        Node n68= graph1.getNode("Batman");
        n68.addAttribute("xy", 7,2);
        graph1.addNode("Artvin");
        Node n69= graph1.getNode("Artvin");
        n69.addAttribute("xy", 7.5,6);
        graph1.addNode("Bitlis");
        Node n70= graph1.getNode("Bitlis");
        n70.addAttribute("xy", 7.5,3);
        graph1.addNode("Siirt");
        Node n71= graph1.getNode("Siirt");
        n71.addAttribute("xy", 7.5,2);
        graph1.addNode("Sirnak");
        Node n72= graph1.getNode("Sirnak");
        n72.addAttribute("xy", 7.5,1);
        graph1.addNode("Ardahan");
        Node n73= graph1.getNode("Ardahan");
        n73.addAttribute("xy", 8,6);
        graph1.addNode("Kars");
        Node n74= graph1.getNode("Kars");
        n74.addAttribute("xy", 8,5);
        graph1.addNode("Igdir");
        Node n75= graph1.getNode("Igdir");
        n75.addAttribute("xy", 8,4);
        graph1.addNode("Agri");
        Node n76= graph1.getNode("Agri");
        n76.addAttribute("xy", 8,3);
        graph1.addNode("Van");
        Node n77= graph1.getNode("Van");
        n77.addAttribute("xy", 8,2);
        graph1.addNode("Hakkari");
        Node n78= graph1.getNode("Hakkari");
        n78.addAttribute("xy", 8,1);
        graph1.addNode("Gaziantep");
        Node n79= graph1.getNode("Gaziantep");
        n79.addAttribute("xy", 5.5,0);
        graph1.addNode("Kilis");
        Node n80= graph1.getNode("Kilis");
        n80.addAttribute("xy", 5,0);

        return graph1;

    }
    public static Dugum sehir_don(String isim)
    {
        int i;
        for(i=0;i<sehirler.size(); i++)
        {
            if(dugum[i].getIsim().equals(isim))
            {
                break;
            }
        }
        return dugum[i];
    }

    public static void dosyaya_yazdir(ArrayList <AlternatifYollar> tum_yol) throws IOException {

        File dosya = new File("tum_yollar.txt");
        if(!dosya.exists()){
            dosya.createNewFile();
        }
        FileWriter dosya_oku = new FileWriter(dosya,false);
        BufferedWriter bwriter = new BufferedWriter(dosya_oku);

        for(int i = 0 ; i < tum_yol.size() ; i++){
            bwriter.write((i+1) + ". yol : [");
            for(int j = 0 ; j < tum_yol.get(i).getRota().size() ; j++){
                bwriter.write(tum_yol.get(i).getRota().get(j));
                bwriter.write(", ");
            }
            bwriter.write("]");
            bwriter.write("\n");
            bwriter.write("Uzaklık : ");
            bwriter.write(String.valueOf(tum_yol.get(i).getUzaklik()));
            bwriter.write("\n");

        }
        bwriter.close();

    }
    public static void alternatif_yol(Dugum start, List<String> gezecek) {
        ArrayList<String> al_rota = new ArrayList<>();
        int gidis=0;
        DjikstraEnKisaYol alternatif = new DjikstraEnKisaYol();
        Dugum son = null;
        ArrayList<Integer> al_uzak = new ArrayList<>();

        System.out.println("Iterasyon sayisi: " + factorial(gezecek.size()));

        System.out.println("xxxxxxxxxxx - alternatif - xxxxxxxxx");
        while (!gezecek.isEmpty()) {
            alternatif.enKisaYolHesapla(start);
            System.out.println("Alternatif gezilecek rota: " + gezecek);
            System.out.println();
            for (int i = 0; i < gezecek.size(); i++) {

                al_uzak.add(i, (int) sehir_don(gezecek.get(i)).getUzaklik());
                System.out.println(start.getIsim() + "dan " + sehir_don(gezecek.get(i)) + " isimli sehre olan mesafe: " + al_uzak.get(i));
                System.out.println("Izlenen yol: " + alternatif.getEnKisaYol(sehir_don(gezecek.get(i))));
            }
            System.out.println();
            al_rota.addAll(alternatif.getEnKisaYol(sehir_don(gezecek.get(0))));
            for (int i = 0; i < dugum.length; i++) {
                dugum[i].setUzaklik(Double.MAX_VALUE);
                dugum[i].setZiyaretEdilmis(false);
                dugum[i].setOnceki(null);

            }
            gidis+=al_uzak.get(0);
            start = sehir_don(gezecek.get(0));


            gezecek.remove(0);
            if (gezecek.size()==1) {
                son = sehir_don(gezecek.get(0));
            }
        }
        System.out.println();
        for (int i = 0; i < dugum.length; i++) {
            dugum[i].setUzaklik(Double.MAX_VALUE);
            dugum[i].setZiyaretEdilmis(false);
            dugum[i].setOnceki(null);

        }
        System.out.println("DONUS: ");

        al_uzak.clear();
        alternatif.enKisaYolHesapla(son);
        System.out.println( son.getIsim()+ "dan " + dugum[40].getIsim() + " isimli sehre olan mesafe: " + (int) dugum[40].getUzaklik());
        System.out.println("Izlenen yol: "+ alternatif.getEnKisaYol(dugum[40]));
        int donus = (int) dugum[40].getUzaklik();
        System.out.println("gidis mesafesi: "+gidis);
        System.out.println("donus mesafesi: "+donus);
        System.out.println("toplam mesafe: "+(gidis+donus));

        al_rota.addAll(alternatif.getEnKisaYol(dugum[40]));
        System.out.println();

        for (int i = 0; i < dugum.length; i++) {
            dugum[i].setUzaklik(Double.MAX_VALUE);
            dugum[i].setZiyaretEdilmis(false);
            dugum[i].setOnceki(null);

        }
        int k=1;
        for(int i = 0; i<al_rota.size(); i++)
        {
            if(k!=al_rota.size())
            {
                if(al_rota.get(i).equals(al_rota.get(k)))
                {
                    al_rota.remove(i);
                }
                k++;
            }
        }
        tum_yol.add(new AlternatifYollar((gidis+donus), al_rota));
        System.out.println();

        System.out.println("Obje "+tum_yol.get(a).getUzaklik());
        System.out.println("Rota "+tum_yol.get(a).getRota());
        a++;

    }
    public static int factorial(int size)
    {
        int result =1;
        for(int i = 1; i<=size; i++)
            result*=i;

        return result;
    }
    public static int en_kucuk(ArrayList uzakliklar)
    {
        int minimum_sayi= (int) uzakliklar.get(0);
        for(int i=1; i<uzakliklar.size(); i++)
        {
            if(minimum_sayi>(int)uzakliklar.get(i))
            {
                minimum_sayi=(int) uzakliklar.get(i);
            }
        }
        return minimum_sayi;
    }
    public static int yer_bul(ArrayList uzakliklar)
    {
        int minimum_sayi=(int) uzakliklar.get(0);
        int minimum_yer = 0;
        for(int i=1; i<uzakliklar.size(); i++)
        {
            if(minimum_sayi>(int) uzakliklar.get(i))
            {
                minimum_sayi= (int) uzakliklar.get(i);
                minimum_yer=i;
            }
        }
        return minimum_yer;
    }

    public static void sehir_atama(String atilacak) {
        String isim;
        int plaka;
        String ayrac = ",";
        int j;
        String[] tokens = atilacak.split(ayrac);
        plaka = Integer.parseInt(tokens[0]);
        isim = tokens[1];
        sehirler.add(new AnaSehir(plaka, isim, tokens.length - 2));
        for (j = 2; j < tokens.length; j++) {
            komsu_atama(plaka, tokens[j]);
        }
    }

    public static void komsu_atama(int plaka, String atilacak) {

        String[] tokens = atilacak.split("-");
        sehirler.get(plaka - 1).komsu_sehirler.add(new KomsuSehir(Integer.parseInt(tokens[0]), tokens[1], Integer.parseInt(tokens[2])));
    }
    public static List<List<String>> listPermutations(List<String> list) {

        if (list.size() == 0) {
            List<List<String>> result = new ArrayList<List<String>>();
            result.add(new ArrayList<String>());
            return result;
        }

        List<List<String>> returnMe = new ArrayList<List<String>>();
        String firstElement = list.remove(0);
        List<List<String>> recursiveReturn = listPermutations(list);
        for (List<String> li : recursiveReturn) {

            for (int index = 0; index <= li.size(); index++) {
                List<String> temp = new ArrayList<String>(li);
                temp.add(index, firstElement);
                returnMe.add(temp);
            }
        }
        return returnMe;
    }
}
