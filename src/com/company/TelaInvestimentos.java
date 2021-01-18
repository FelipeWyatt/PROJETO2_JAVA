package com.company;

import javax.swing.*;
import java.awt.*;

public class TelaInvestimentos extends JFrame {
    private JButton btComprar, btVender;
    private JTextField entradaTicker;
    private JLabel legendaTicker;

    public TelaInvestimentos(){
        btComprar = new JButton("Comprar");
        btVender = new JButton("Vender");
        legendaTicker = new JLabel("Código da Ação: ");
        entradaTicker = new JTextField(10);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new FlowLayout());
        getContentPane().add(legendaTicker);
        getContentPane().add(entradaTicker);
        getContentPane().add(btComprar);
        getContentPane().add(btVender);
    }
}
