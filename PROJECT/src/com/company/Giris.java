package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Giris extends JFrame{

    public static int sehir_sayisi;
    private JPanel ilk;
    private JTextField textField1;
    private JButton Devam;

    public Giris() {
        Devam.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                sehir_sayisi = Integer.parseInt(textField1.getText());
                İkinciEkran x = new İkinciEkran();
                x.setVisible(true);
                ilk.setVisible(false);
            }
        });
    }
    public static void main(String[] args) {
        JFrame yeni = new JFrame("yeni");
        yeni.setContentPane(new Giris().ilk);
        yeni.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        yeni.pack();
        yeni.setVisible(true);
    }
}
