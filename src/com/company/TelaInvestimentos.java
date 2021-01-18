package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TelaInvestimentos extends JFrame {
    private JButton btComprar, btVender;
    private JTextField entradaTicker;
    private JLabel legendaTicker;
    private Cliente clienteInvestidor;
    private ContaInvestidor contaCliente;

    public TelaInvestimentos(Cliente cliente){
        super("Investimentos");

        clienteInvestidor = cliente;
        contaCliente = (ContaInvestidor) clienteInvestidor.getConta();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BoxLayout(getContentPane() ,BoxLayout.Y_AXIS)); // Painel principal
        
        // Dividido em painéis para organizar o layout
        
        JPanel JPanelListas = new JPanel();
        JPanelListas.setLayout(new BoxLayout(JPanelListas, BoxLayout.Y_AXIS));
        JPanelListas.setAlignmentY(Component.TOP_ALIGNMENT);
        
        ArrayList<String> acoesCliente = contaCliente.getAcoesString();
        JList<String> JListAcoes = new JList<String>(acoesCliente.toArray(new String[0]));
        JListAcoes.setBackground(getBackground());
        
        JPanelListas.add(JListAcoes);
        JPanelListas.add(JListAcoes);


        btComprar = new JButton("Comprar");
        btVender = new JButton("Vender");
        legendaTicker = new JLabel("Código da Ação: ");
        entradaTicker = new JTextField(10);

        JPanel painelEntradas = new JPanel();
        painelEntradas.setLayout(new FlowLayout());

        JPanel painelBts = new JPanel();
        painelBts.setLayout(new FlowLayout());


        painelBts.add(btComprar);
        painelBts.add(btVender);
        painelEntradas.add(legendaTicker);
        painelEntradas.add(entradaTicker);

        getContentPane().add(JPanelListas);
        getContentPane().add(painelEntradas);
        getContentPane().add(painelBts);
    }
}
