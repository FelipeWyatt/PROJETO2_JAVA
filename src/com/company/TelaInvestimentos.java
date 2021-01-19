package com.company;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class TelaInvestimentos extends JFrame {
    private JList<String> listAcoes, listRF, listAcoesCompra, listRFCompra;
    private JLabel labelAtivoSelecionado, labelCompraSelecionado;

    private ArrayList<Acao> acoesCliente;
    private ArrayList<RendaFixa> RFCliente;
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
        //ArrayList<String> acoesClienteString = contaCliente.getAcoesString();
        acoesCliente = contaCliente.getAcoes();

        // Model pois assim a lista pode ser atualizada automaticamente quando o model for alterado
        DefaultListModel<String> modeloAcoesString = new DefaultListModel<String>();
        modeloAcoesString.addAll(contaCliente.getAcoesString());

        listAcoes = new JList<String>(modeloAcoesString);
        listAcoes.setBackground(getBackground());
        listAcoes.setAlignmentY(Component.TOP_ALIGNMENT);
        listAcoes.setAlignmentX(Component.LEFT_ALIGNMENT);
        listAcoes.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Deve-se verificar pois o listener de listRF é ativado quando listRF.clearSelection()
                if(!listAcoes.isSelectionEmpty()){
                    listRF.clearSelection(); // desseleciona a outra lista
                    Acao acaoSelecionada = acoesCliente.get(listAcoes.getSelectedIndex());
                    labelAtivoSelecionado.setText(acaoSelecionada.getAcao().getTicker());
                }
            }
        });

        panelListas.add(listAcoes);

        // Caixa de espaçamento entre as listas
        panelListas.add(Box.createRigidArea(new Dimension(30,0)));

        // Lista de RF do cliente
        // Tratar caso em que cliente não tem RF
        //ArrayList<String> RFClienteString = contaCliente.getRFString();
        RFCliente = contaCliente.getRF();

        // Model pois assim a lista pode ser atualizada automaticamente quando o model for alterado
        DefaultListModel<String> modeloRFString = new DefaultListModel<String>();
        modeloRFString.addAll(contaCliente.getRFString());

        listRF = new JList<String>(modeloRFString);
        listRF.setBackground(getBackground());
        listRF.setAlignmentY(Component.TOP_ALIGNMENT);
        listRF.setAlignmentX(Component.LEFT_ALIGNMENT);
        listRF.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Deve-se verificar pois o listener de listAcoes é ativado quando listAcoes.clearSelection()
                if(!listRF.isSelectionEmpty()){
                    listAcoes.clearSelection();
                    RendaFixa rendaFixaSelecionada = RFCliente.get(listRF.getSelectedIndex());
                    labelAtivoSelecionado.setText(rendaFixaSelecionada.getAtivo().getNome() + " (" + rendaFixaSelecionada.getMontante() + ")");
                }
            }
        });
        panelListas.add(listRF);

        getContentPane().add(panelListas);

        JPanel panelVenda = new JPanel();
        panelVenda.setLayout(new BoxLayout(panelVenda, BoxLayout.X_AXIS));
        panelVenda.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelVenda.add(new JLabel("Ativo selecionado: "));
        
        labelAtivoSelecionado = new JLabel("");
        panelVenda.add(labelAtivoSelecionado);

        panelVenda.add(Box.createHorizontalGlue()); // Componente para layout
        
        JButton btVender = new JButton("Vender");
        btVender.setBackground(Color.white);
        btVender.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!listAcoes.isSelectionEmpty()){
                    DecimalFormat d1 = new DecimalFormat("#. 00"); //formata do jeito certo
                    Acao acaoSelecionada = acoesCliente.get(listAcoes.getSelectedIndex());
                    String qtdString = JOptionPane.showInputDialog(
                            "Quantas unidades de " + acaoSelecionada.getAcao().getTicker() + " a R$" +
                                    d1.format(acaoSelecionada.getAcao().precoTempoReal()) + " deseja vender ?");
                    int qtd = -1;
                    try{
                        qtd = Integer.parseInt(qtdString);
                    } catch (NumberFormatException erro){
                        JOptionPane.showMessageDialog(null,
                                "Quantidade inválida! Digite um inteiro para a quantidade.", null, JOptionPane.ERROR_MESSAGE);
                    }

                    if(contaCliente.venderAcao(acaoSelecionada.getAcao(), qtd) < 0){
                        // Não foi possível vender
                        JOptionPane.showMessageDialog(null,
                                "Não foi possível vender a ação.", null, JOptionPane.WARNING_MESSAGE);
                    } else {
                        // Atualiza o modelo da lista de ações do usuário que atualiza no Jlist automaticamente
                        modeloAcoesString.clear();
                        modeloAcoesString.addAll(contaCliente.getAcoesString());
                        acoesCliente = contaCliente.getAcoes(); // Atualiza a lista
                        JOptionPane.showMessageDialog(null,
                                "Ação vendida com sucesso, seu novo saldo R$" + d1.format(contaCliente.getSaldo()),
                                null, JOptionPane.INFORMATION_MESSAGE);
                    }
                } else if(!listRF.isSelectionEmpty()){
                    DecimalFormat d1 = new DecimalFormat("#. 00"); //formata do jeito certo
                    RendaFixa RFSelecionado = RFCliente.get(listRF.getSelectedIndex());

                    if(contaCliente.venderRF(RFSelecionado) < 0){
                        // Não foi possível vender
                        JOptionPane.showMessageDialog(null,
                                "Não foi possível vender o investimento em Renda Fixa.", null, JOptionPane.WARNING_MESSAGE);
                    } else {
                        // Atualiza o modelo da lista de ações do usuário que atualiza no Jlist automaticamente
                        modeloRFString.clear();
                        modeloRFString.addAll(contaCliente.getRFString());
                        RFCliente = contaCliente.getRF(); // Atualiza a lista
                        JOptionPane.showMessageDialog(null,
                                "Investimento vendido com sucesso, seu novo saldo R$" + d1.format(contaCliente.getSaldo()),
                                null, JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
        panelVenda.add(btVender);

        getContentPane().add(panelVenda);

        // INICIO DA PARTE DE COMPRA
        getContentPane().add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel labelInvestimentosDisponiv = new JLabel("INVESTIMENTOS DISPONÍVEIS");
        labelInvestimentosDisponiv.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 18));
        labelInvestimentosDisponiv.setAlignmentX(Component.LEFT_ALIGNMENT);
        getContentPane().add(labelInvestimentosDisponiv);

        JPanel panelListasCompra = new JPanel();
        panelListasCompra.setLayout(new BoxLayout(panelListasCompra, BoxLayout.X_AXIS));
        panelListasCompra.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Lista de ações do cliente
        // Tratar caso em que cliente não tem ações
        ArrayList<String> acoesCompraString = Acoes.acoesDisponiveisString();
        Acoes[] acoesCompra = Acoes.values();
        listAcoesCompra = new JList<String>(acoesCompraString.toArray(new String[0]));
        listAcoesCompra.setBackground(getBackground());
        listAcoesCompra.setAlignmentY(Component.TOP_ALIGNMENT);
        listAcoesCompra.setAlignmentX(Component.LEFT_ALIGNMENT);
        listAcoesCompra.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!listAcoesCompra.isSelectionEmpty()){
                    listRFCompra.clearSelection(); // desseleciona a outra lista
                    Acoes acaoSelecionada = acoesCompra[(listAcoesCompra.getSelectedIndex())];
                    labelCompraSelecionado.setText(acaoSelecionada.getTicker());
                }
            }
        });

        panelListasCompra.add(listAcoesCompra);

        // Caixa de espaçamento entre as listas
        panelListasCompra.add(Box.createRigidArea(new Dimension(30,0)));

        // Lista de RF do cliente
        // Tratar caso em que cliente não tem RF
        ArrayList<String> RFCompraString = AtivosRF.RFDisponiveisString();
        AtivosRF[] RFCompra = AtivosRF.values();
        listRFCompra = new JList<String>(RFCompraString.toArray(new String[0]));
        listRFCompra.setBackground(getBackground());
        listRFCompra.setAlignmentY(Component.TOP_ALIGNMENT);
        listRFCompra.setAlignmentX(Component.LEFT_ALIGNMENT);
        listRFCompra.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!listRFCompra.isSelectionEmpty()){
                    listAcoesCompra.clearSelection(); // desseleciona a outra lista
                    AtivosRF RFSelecionado = RFCompra[(listRFCompra.getSelectedIndex())];
                    labelCompraSelecionado.setText(RFSelecionado.getNome());
                }
            }
        });

        panelListasCompra.add(listRFCompra);

        getContentPane().add(panelListasCompra);

        JPanel panelCompra = new JPanel();
        panelCompra.setLayout(new BoxLayout(panelCompra, BoxLayout.X_AXIS));
        panelCompra.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelCompra.add(new JLabel("Ativo selecionado: "));

        labelCompraSelecionado = new JLabel("");
        panelCompra.add(labelCompraSelecionado);

        panelCompra.add(Box.createHorizontalGlue()); // Componente para layout

        JButton btCompra = new JButton("Comprar");
        btCompra.setBackground(Color.white);
        panelCompra.add(btCompra);

        getContentPane().add(panelCompra);

    }


}
