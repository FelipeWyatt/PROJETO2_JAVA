package com.company;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

public class TelaInvestimentos extends JFrame {
    private JList<String> listAcoes, listRF, listAcoesCompra;
    private JLabel labelAtivoSelecionado;
    private Cliente clienteInvestidor;
    private ContaInvestidor contaCliente;

    public TelaInvestimentos(Cliente cliente){
        super("Investimentos");

        clienteInvestidor = cliente;
        contaCliente = (ContaInvestidor) clienteInvestidor.getConta();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)); // Painel principal
        setResizable(false);
        getRootPane().setBorder(BorderFactory.createEmptyBorder(5, 20, 20, 20));

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
        listAcoes = new JList<String>(acoesCliente.toArray(new String[0]));
        listAcoes.setBackground(getBackground());
        listAcoes.setAlignmentY(Component.TOP_ALIGNMENT);
        listAcoes.setAlignmentX(Component.LEFT_ALIGNMENT);
        listAcoes.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                listRF.clearSelection(); // desseleciona a outra lista
                labelAtivoSelecionado.setText(listAcoes.getSelectedValue());
            }
        });

        panelListas.add(listAcoes);

        // Caixa de espaçamento entre as listas
        panelListas.add(Box.createRigidArea(new Dimension(30,0)));

        // Lista de RF do cliente
        // Tratar caso em que cliente não tem RF
        ArrayList<String> RFCliente = contaCliente.getRFString();
        listRF = new JList<String>(RFCliente.toArray(new String[0]));
        listRF.setBackground(getBackground());
        listRF.setAlignmentY(Component.TOP_ALIGNMENT);
        listRF.setAlignmentX(Component.LEFT_ALIGNMENT);
        listRF.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                listAcoes.clearSelection();
                labelAtivoSelecionado.setText(listRF.getSelectedValue());
            }
        });
        panelListas.add(listRF);

        getContentPane().add(panelListas);

        JPanel panelVenda = new JPanel();
        panelVenda.setLayout(new BoxLayout(panelVenda, BoxLayout.X_AXIS));
        panelVenda.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelVenda.add(new JLabel("Ativo selecionado: "));
        
        labelAtivoSelecionado = new JLabel("-");
        panelVenda.add(labelAtivoSelecionado);

        panelVenda.add(Box.createHorizontalGlue()); // Componente para layout
        
        JButton btVender = new JButton("Vender");
        btVender.setBackground(Color.white);
        panelVenda.add(btVender);

        getContentPane().add(panelVenda);

        // INICIO DA PARTE DE COMPRA
        getContentPane().add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel labelInvestimentosDisponiv = new JLabel("INVESTIMENTOS DISPONÍVEIS");
        labelInvestimentosDisponiv.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 18));
        labelInvestimentosDisponiv.setAlignmentX(Component.LEFT_ALIGNMENT);
        getContentPane().add(labelInvestimentosDisponiv);

        JPanel panelListasCompra = new JPanel();
        panelListasCompra.setLayout(new BoxLayout(panelListas, BoxLayout.X_AXIS));
        panelListasCompra.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Lista de ações do cliente
        // Tratar caso em que cliente não tem ações
        ArrayList<String> acoesCompra = Acoes.acoesDisponiveisString();
        listAcoesCompra = new JList<String>(acoesCompra.toArray(new String[0]));
        listAcoesCompra.setBackground(getBackground());
        listAcoesCompra.setAlignmentY(Component.TOP_ALIGNMENT);
        listAcoesCompra.setAlignmentX(Component.LEFT_ALIGNMENT);
        listAcoesCompra.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                ;
            }
        });

        panelListasCompra.add(listAcoes);

        getContentPane().add(panelListasCompra);

    }


}
