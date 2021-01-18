package com.company.views;

import com.company.Admin;
import com.company.Cliente;
import com.company.ContaInvestidor;
import com.company.ContaPoupanca;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLOutput;

public class TelaLogin extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField campoLogin;
    private JPasswordField campoSenha;

    public TelaLogin() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        Integer login = Integer.valueOf(campoLogin.getText());
        String senha = campoSenha.getText();

        Cliente clienteValidado = Admin.verObjetoCliente(login);

//        TelaContas01 dialog = new TelaContas01();  Para Chamar Outras Telas
//        dialog.pack();
//        dialog.setVisible(true);

        // Tela de Erro
        if (clienteValidado == null ||
                (clienteValidado != null && !clienteValidado.getSenha().equals(senha))) {
            System.out.println("Erro no Login");
            return;
        }
        if (clienteValidado.getConta() instanceof ContaInvestidor) {
            System.out.println("Conta Investidor");
            // Chamar Tela Conta Investimento
        } else if (clienteValidado.getConta() instanceof ContaPoupanca) {
            System.out.println("Conta Poupança");
            // Chama Tela Conta Poupança
        } else {
            System.out.println("Conta Corrente");
            // Automaticamente Conta Corrente
        }
    }
    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    //Na Main do Programa: TelaLogin

    //public static void main(String[] args) {
        //TelaLogin dialog = new TelaLogin();
        //dialog.pack();
        //dialog.setVisible(true);
        //System.exit(0);
    //}
}
