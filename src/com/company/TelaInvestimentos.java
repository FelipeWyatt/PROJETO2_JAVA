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
    private DefaultListModel<String> modeloAcoesString, modeloRFString;

    private ArrayList<Acao> acoesCliente;
    private ArrayList<RendaFixa> RFCliente;
    private Acoes[] acoesCompra;
    private AtivosRF[] RFCompra;
    private Cliente clienteInvestidor;
    private ContaInvestidor contaCliente;

    private JLabel novoTituloJlabel(String texto, int fonte){
        JLabel labelTitulo = new JLabel(texto);
        labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, fonte));
        labelTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        return labelTitulo;
    }

    private JPanel novoListasJPanel(){
        JPanel panelListas = new JPanel();
        panelListas.setLayout(new BoxLayout(panelListas, BoxLayout.X_AXIS));
        panelListas.setAlignmentX(Component.LEFT_ALIGNMENT);
        return panelListas;
    }

    private JPanel novoTextoBotaoJPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return panel;
    }

    private void criaListAcoes(){
        // Model pois assim a lista pode ser atualizada automaticamente quando o model for alterado
        modeloAcoesString = new DefaultListModel<String>();
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
    }

    private void criaListRF(){
        // Model pois assim a lista pode ser atualizada automaticamente quando o model for alterado
        modeloRFString = new DefaultListModel<String>();
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
    }

    private JButton btVender(){
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
                labelAtivoSelecionado.setText("");
                pack(); // Redimensiona a janela
            }
        });
        return btVender;
    }

    private void criaListaAcoesCompra(){
        ArrayList<String> acoesCompraString = Acoes.acoesDisponiveisString();

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
    }

    private void criaListaRFCompra(){
        // Lista de RF do cliente
        // Tratar caso em que cliente não tem RF
        ArrayList<String> RFCompraString = AtivosRF.RFDisponiveisString();

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
    }

    private JButton btCompra(){
        JButton btCompra = new JButton("Comprar");
        btCompra.setBackground(Color.white);
        btCompra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!listAcoesCompra.isSelectionEmpty()){
                    DecimalFormat d1 = new DecimalFormat("#. 00"); //formata do jeito certo
                    Acoes acaoSelecionada = acoesCompra[listAcoesCompra.getSelectedIndex()];
                    String qtdString = JOptionPane.showInputDialog(
                            "Quantas unidades de " + acaoSelecionada.getTicker() + " a R$" +
                                    d1.format(acaoSelecionada.precoTempoReal()) + " deseja comprar ?");
                    int qtd = -1;
                    try{
                        qtd = Integer.parseInt(qtdString);
                    } catch (NumberFormatException erro){
                        JOptionPane.showMessageDialog(null,
                                "Quantidade inválida! Digite um inteiro para a quantidade.", null, JOptionPane.ERROR_MESSAGE);
                    }

                    if(!contaCliente.comprarAcao(acaoSelecionada, qtd)){
                        // Não foi possível comprar
                        JOptionPane.showMessageDialog(null,
                                "Não foi possível comprar a ação.", null, JOptionPane.WARNING_MESSAGE);
                    } else {
                        // Atualiza o modelo da lista de ações do usuário que atualiza no Jlist automaticamente
                        modeloAcoesString.clear();
                        modeloAcoesString.addAll(contaCliente.getAcoesString());
                        acoesCliente = contaCliente.getAcoes(); // Atualiza a lista
                        JOptionPane.showMessageDialog(null,
                                "Ação comprada com sucesso, seu novo saldo R$" + d1.format(contaCliente.getSaldo()),
                                null, JOptionPane.INFORMATION_MESSAGE);
                    }
                } else if(!listRFCompra.isSelectionEmpty()){
                    DecimalFormat d1 = new DecimalFormat("#. 00"); //formata do jeito certo
                    AtivosRF RFSelecionado = RFCompra[listRFCompra.getSelectedIndex()];

                    String montanteString = JOptionPane.showInputDialog(
                            "Quanto quer aplicar em " + RFSelecionado.getNome() + " ?");

                    float montante = -1;
                    try{
                        montante = Float.parseFloat(montanteString);
                    } catch (NumberFormatException erro){
                        JOptionPane.showMessageDialog(null,
                                "Valor inválido! Digite um número.", null, JOptionPane.ERROR_MESSAGE);
                    }
                    if(!contaCliente.comprarRF(RFSelecionado, montante)){
                        // Não foi possível vender
                        JOptionPane.showMessageDialog(null,
                                "Não foi possível comprar o investimento em Renda Fixa.", null, JOptionPane.WARNING_MESSAGE);
                    } else {
                        // Atualiza o modelo da lista de ações do usuário que atualiza no Jlist automaticamente
                        modeloRFString.clear();
                        modeloRFString.addAll(contaCliente.getRFString());
                        RFCliente = contaCliente.getRF(); // Atualiza a lista
                        JOptionPane.showMessageDialog(null,
                                "Investimento comprado com sucesso, seu novo saldo R$" + d1.format(contaCliente.getSaldo()),
                                null, JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                labelCompraSelecionado.setText("");
                pack(); // Redimensiona a janela
            }
        });
        return btCompra;
    }

    public TelaInvestimentos(Cliente cliente){
        // Frame dividido em painéis para organizar o layout
        // Objetos organizados em métodos
        super("Investimentos");

        // Atributos usados nos métodos
        clienteInvestidor = cliente;
        contaCliente = (ContaInvestidor) clienteInvestidor.getConta();
        acoesCliente = contaCliente.getAcoes(); // Lista de ações do cliente
        RFCliente = contaCliente.getRF(); // Lista de investimentos RF do cliente
        acoesCompra = Acoes.values();
        RFCompra = AtivosRF.values();

        // Setando o Frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)); // Painel principal
        setResizable(true);
        getRootPane().setBorder(BorderFactory.createEmptyBorder(5, 20, 20, 20));
        ImageIcon logo = new ImageIcon("Icone_carteira.png");
        setIconImage(logo.getImage());

        // Título 1
        getContentPane().add(novoTituloJlabel("SEUS INVESTIMENTOS", 22));

        // Associa a lista de ações do cliente a listAcoes
        criaListAcoes();

        // Associa a lista de RF do cliente a listRF
        criaListRF();

        // Panel 1 contém os investimentos do cliente
        JPanel panelListas = novoListasJPanel();
        panelListas.add(listAcoes);
        panelListas.add(Box.createRigidArea(new Dimension(30,0))); // Caixa de espaçamento entre as listas
        panelListas.add(listRF);

        getContentPane().add(panelListas);

        // Panel 2 contém o investimento selecionado e o botão de venda
        JPanel panelVenda = novoTextoBotaoJPanel();
        panelVenda.add(new JLabel("Ativo selecionado: "));
        labelAtivoSelecionado = new JLabel("");
        panelVenda.add(labelAtivoSelecionado);
        panelVenda.add(Box.createHorizontalGlue()); // Componente para layout
        panelVenda.add(btVender());

        getContentPane().add(panelVenda);

        // Distância entre Panels
        getContentPane().add(Box.createRigidArea(new Dimension(0, 20)));

        // Título 2
        getContentPane().add(novoTituloJlabel("INVESTIMENTOS DISPONÍVEIS", 20));

        // Gera lista de ações disponíveis para compra
        criaListaAcoesCompra();

        // Gera lista de investimentos RF disponíveis para compra
        criaListaRFCompra();

        // Panel 3 contém os investimentos disponíveis para compra
        JPanel panelListasCompra = novoListasJPanel();
        panelListasCompra.add(listAcoesCompra);
        panelListasCompra.add(Box.createRigidArea(new Dimension(30,0))); // Caixa de espaçamento entre as listas
        panelListasCompra.add(listRFCompra);

        getContentPane().add(panelListasCompra);

        // Panel 4 contém o investimento selecionado e o botão de compra
        JPanel panelCompra = novoTextoBotaoJPanel();
        panelCompra.add(new JLabel("Ativo selecionado: "));
        labelCompraSelecionado = new JLabel("");
        panelCompra.add(labelCompraSelecionado);
        panelCompra.add(Box.createHorizontalGlue()); // Componente para layout
        panelCompra.add(btCompra());

        getContentPane().add(panelCompra);

    }


}
