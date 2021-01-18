package com.company;

import javax.swing.*;
import java.awt.*;

public class TelaInvestimentos extends JFrame {
    private JButton btComprar, btVender;
    private JTextField entradaTicker;
    private JLabel legendaTicker;

    public TelaInvestimentos(){
        super("Investimentos");
        btComprar = new JButton("Comprar");
        btVender = new JButton("Vender");
        legendaTicker = new JLabel("Código da Ação: ");
        entradaTicker = new JTextField(10);

        JPanel painelEntradas = new JPanel();
        painelEntradas.setLayout(new FlowLayout());

        JPanel painelBts = new JPanel();
        painelBts.setLayout(new FlowLayout());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BoxLayout(getContentPane() ,BoxLayout.Y_AXIS));
        //getContentPane().add(legendaTicker);
        //getContentPane().add(entradaTicker);
        //getContentPane().add(btComprar);
        //getContentPane().add(btVender);

        painelBts.add(btComprar);
        painelBts.add(btVender);
        painelEntradas.add(legendaTicker);
        painelEntradas.add(entradaTicker);

        getContentPane().add(painelEntradas);
        getContentPane().add(painelBts);
    }
}
