package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TelaInvestimentos extends JFrame {
    private Cliente clienteInvestidor;
    private ContaInvestidor contaCliente;

    public TelaInvestimentos(Cliente cliente){
        super("Investimentos");

        clienteInvestidor = cliente;
        contaCliente = (ContaInvestidor) clienteInvestidor.getConta();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)); // Painel principal

        JLabel labelTitulo = new JLabel("SEUS INVESTIMENTOS");
        labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 22));
        labelTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        getContentPane().add(labelTitulo);
        
        // Dividido em painéis para organizar o layout
        // Panel com as ações dos clientes
        JPanel panelListas = new JPanel();
        panelListas.setLayout(new BoxLayout(panelListas, BoxLayout.X_AXIS));
        panelListas.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Lista de ações do cliente
        // Tratar caso em que cliente não tem ações
        ArrayList<String> acoesCliente = contaCliente.getAcoesString();
        JList<String> listAcoes = new JList<String>(acoesCliente.toArray(new String[0]));
        listAcoes.setBackground(getBackground());
        listAcoes.setAlignmentY(Component.TOP_ALIGNMENT);
        listAcoes.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelListas.add(listAcoes);

        // Caixa de espaçamento entre as listas
        panelListas.add(Box.createRigidArea(new Dimension(30,0)));

        // Lista de RF do cliente
        // Tratar caso em que cliente não tem RF
        ArrayList<String> RFCliente = contaCliente.getRFString();
        JList<String> listRF = new JList<String>(RFCliente.toArray(new String[0]));
        listRF.setBackground(getBackground());
        listRF.setAlignmentY(Component.TOP_ALIGNMENT);
        listRF.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelListas.add(listRF);

        getContentPane().add(panelListas);

        JPanel panelVenda = new JPanel();
        panelVenda.setLayout(new BoxLayout(panelVenda, BoxLayout.X_AXIS));
        panelVenda.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelVenda.add(new JLabel("Ativo selecionado: "));
        
        JLabel labelAtivoSelecionado = new JLabel("-");
        panelVenda.add(labelAtivoSelecionado);
        
        JButton btVender = new JButton("Vender");
        panelVenda.add(btVender);

        getContentPane().add(panelVenda);

        /*
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


        getContentPane().add(labelTitulo);
        getContentPane().add(panelListas);
        getContentPane().add(painelEntradas);
        getContentPane().add(painelBts);
         */
    }
}
