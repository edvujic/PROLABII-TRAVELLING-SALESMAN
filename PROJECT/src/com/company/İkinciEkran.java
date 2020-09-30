package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class İkinciEkran extends JFrame{
    private JPanel iki;
    private JLabel metin;
    public  JTextField textField1;
    public  JTextField textField2;
    public  JTextField textField3;
    public  JTextField textField4;
    public  JTextField textField5;
    public  JTextField textField6;
    public  JTextField textField7;
    public  JTextField textField8;
    public  JTextField textField9;
    public  JTextField textField10;
    private JButton devamButton;

    public static ArrayList<JTextField> TextAlanlari = new ArrayList<>();

    public İkinciEkran(){
        JFrame ikinci = new JFrame("İkinci Ekran");
        ikinci.setContentPane(iki);
        ikinci.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ikinci.pack();
        ikinci.setVisible(true);

        //şehir isimlerini girmek için oluşturulan text alanları Arrayliste atılıyor.
        TextAlanlari.add(textField1);
        TextAlanlari.add(textField2);
        TextAlanlari.add(textField3);
        TextAlanlari.add(textField4);
        TextAlanlari.add(textField5);
        TextAlanlari.add(textField6);
        TextAlanlari.add(textField7);
        TextAlanlari.add(textField8);
        TextAlanlari.add(textField9);
        TextAlanlari.add(textField10);

        Giris sehir_sayisi_giris = new Giris();
        System.out.println(sehir_sayisi_giris.sehir_sayisi);

        for(int i = 0 ; i < sehir_sayisi_giris.sehir_sayisi; i++){
            TextAlanlari.get(i).setVisible(true);
        }

        devamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UcuncuEkran y = null;
                try {
                    y = new UcuncuEkran();
                    System.out.println("başarılı");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Hata" + e);
                }
                iki.setVisible(false);
            }
        });
    }
}
