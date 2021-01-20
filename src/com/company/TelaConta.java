package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class TelaConta extends JFrame {
    private Cliente cliente;
    private ContaBancaria contaCliente;
    private DefaultListModel<String> modeloDadosCliente;

    public TelaConta(Cliente c){
        cliente = c;
        contaCliente = cliente.getConta();

        // Setando o Frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)); // Painel principal
        setResizable(false);
        setBackground(Color.white);
        getRootPane().setBorder(BorderFactory.createEmptyBorder(5, 20, 20, 20));
        getContentPane().setBackground(Color.white);
        ImageIcon logo = new ImageIcon("imagens/Icone_moeda.png");
        setIconImage(logo.getImage());

        String titulo = "Conta";
        if(contaCliente instanceof ContaCorrente) titulo = "Conta Corrente";
        if(contaCliente instanceof ContaPoupanca) titulo = "Conta Poupança";
        if(contaCliente instanceof ContaInvestidor) titulo = "Conta Investidor";

        JLabel tituloLabel = new JLabel(titulo);
        tituloLabel.setFont(new Font("Dialog", Font.PLAIN, 26));
        tituloLabel.setBackground(getBackground());
        tituloLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        getContentPane().add(tituloLabel);

        // Model pois assim a lista pode ser atualizada automaticamente quando o model for alterado
        modeloDadosCliente = new DefaultListModel<String>();
        atualizaDadosCliente(cliente);

        JList<String> listaDados = new JList<String>(modeloDadosCliente);
        listaDados.setBackground(getBackground());
        listaDados.setAlignmentY(Component.TOP_ALIGNMENT);
        listaDados.setAlignmentX(Component.LEFT_ALIGNMENT);
        listaDados.setFont(new Font("Dialog", Font.PLAIN, 16));
        listaDados.setEnabled(false);

        getContentPane().add(listaDados);

        getContentPane().add(Box.createRigidArea(new Dimension(0, 30)));

        JPanel panelBts = new JPanel();
        panelBts.setLayout(new BoxLayout(panelBts, BoxLayout.X_AXIS));
        panelBts.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelBts.setBackground(getBackground());

        if(cliente.getConta() instanceof ContaInvestidor){
            JButton btInvestimentos = new JButton("Investimentos");
            btInvestimentos.setAlignmentX(Component.LEFT_ALIGNMENT);
            btInvestimentos.setBackground(Color.lightGray);
            TelaConta telaQueChamou = this;
            btInvestimentos.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Abre a tela dos investimentos
                    TelaInvestimentos telaInvestimentos = new TelaInvestimentos(cliente, telaQueChamou);
                    telaInvestimentos.pack();
                    telaInvestimentos.setLocation(920, 250); // Posição inicial da TelaConta no monitor do pc
                    telaInvestimentos.setVisible(true);
                }
            });

            panelBts.add(btInvestimentos);
            panelBts.add(Box.createRigidArea(new Dimension(5, 0)));
        }

        panelBts.add(Box.createHorizontalGlue());

        JButton btRetirar = new JButton("Retirar Saldo");
        btRetirar.setAlignmentX(Component.RIGHT_ALIGNMENT);
        btRetirar.setBackground(Color.lightGray);
        btRetirar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String valorString = JOptionPane.showInputDialog(
                        "Quanto deseja retirar do seu saldo?");

                float valor = -1;
                try{
                    valor = Float.parseFloat(valorString);
                } catch (NumberFormatException erro){
                    JOptionPane.showMessageDialog(null,
                            "Valor inválido! Digite um número da forma XXXX.XX", null, JOptionPane.ERROR_MESSAGE);
                }

                if(valor > 0 && cliente.getConta().getSaldo() >= valor && cliente.getConta().retirar(valor)){
                    atualizaDadosCliente(cliente);
                    JOptionPane.showMessageDialog(null,
                            "Valor retirado com sucesso.",
                            null, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Não foi possível retirar o valor.",
                            null, JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        panelBts.add(btRetirar);
        panelBts.add(Box.createRigidArea(new Dimension(5, 0)));

        JButton btAdicionar = new JButton("Adicionar Saldo");
        btAdicionar.setAlignmentX(Component.RIGHT_ALIGNMENT);
        btAdicionar.setBackground(Color.lightGray);
        btAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String valorString = JOptionPane.showInputDialog(
                        "Quanto deseja adicionar ao seu saldo?");

                float valor = -1;
                try{
                    valor = Float.parseFloat(valorString);
                } catch (NumberFormatException erro){
                    JOptionPane.showMessageDialog(null,
                            "Valor inválido! Digite um número da forma XXXX.XX", null, JOptionPane.ERROR_MESSAGE);
                }

                if(valor > 0 && cliente.getConta().depositar(valor)){
                    atualizaDadosCliente(cliente);
                    JOptionPane.showMessageDialog(null,
                            "Valor adicionado com sucesso.",
                            null, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Não foi possível adiocionar o valor.",
                            null, JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        panelBts.add(btAdicionar);


        getContentPane().add(panelBts);
    }

    public void atualizaDadosCliente(Cliente cliente){
        DecimalFormat d1 = new DecimalFormat("#. 00"); // Formatacao para deixar no formato "00,00"
        ArrayList<String> dados = new ArrayList<String>();

        dados.add("ID: " + cliente.getId());
        dados.add("Nome: " + cliente.getNome());
        dados.add("Idade: " + cliente.getIdade());
        dados.add("");
        if(cliente.getConta() != null){
            dados.add("Dinheiro total: R$ " + d1.format(cliente.getDinheiroTotal()));
            if(cliente.getConta() instanceof ContaInvestidor){
                dados.add("Saldo em conta: R$ " + d1.format(cliente.getConta().getSaldo()));
                dados.add("Dinheiro investido: R$ " + d1.format(((ContaInvestidor) cliente.getConta()).getMontanteTotal()));
            } else if(cliente.getConta() instanceof ContaPoupanca){
                float rent = (float) Math.round(ContaPoupanca.getRendimentoDiario()*1000000)/10000; // Arredonda para 3 casas decimais
                dados.add("Rendimento diário da Poupança: " + rent + " %");
            }
        } else {
            dados.add("Cliente não tem conta!");
        }


        modeloDadosCliente.clear();
        modeloDadosCliente.addAll(dados);
    }
}
