package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TelaLogin extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK, buttonCancel;
    private JTextField campoLogin;
    private JPasswordField campoSenha;

    public TelaLogin() {
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)); // Painel principal
        setResizable(false);
        setModal(true);
        setTitle("Login");
        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        getRootPane().setDefaultButton(buttonOK = new JButton("OK"));

        try{
            BufferedImage logoBuff = ImageIO.read(new File("logoBankCamp.png"));

            JLabel logoLabel = new JLabel(new ImageIcon(logoBuff));
            getContentPane().add(logoLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }


        JPanel panelId = new JPanel();
        panelId.setLayout(new BoxLayout(panelId, BoxLayout.X_AXIS));
        panelId.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelId.add(new JLabel("ID: "));

        panelId.add(Box.createHorizontalGlue());

        campoLogin = new JTextField();
        campoLogin.setColumns(10);
        panelId.add(campoLogin);

        getContentPane().add(panelId);

        JPanel panelSenha = new JPanel();
        panelSenha.setLayout(new BoxLayout(panelSenha, BoxLayout.X_AXIS));
        panelSenha.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelSenha.add(new JLabel("Senha: "));
        campoSenha = new JPasswordField();
        campoSenha.setColumns(10);
        panelSenha.add(campoSenha);

        getContentPane().add(panelSenha);

        JPanel panelBt = new JPanel();
        panelBt.setLayout(new BoxLayout(panelBt, BoxLayout.X_AXIS));
        panelBt.setAlignmentX(Component.LEFT_ALIGNMENT);

        buttonOK = new JButton("OK");
        buttonOK.setAlignmentX(Component.RIGHT_ALIGNMENT);
        buttonOK.setBackground(Color.white);
        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer login = Integer.parseInt(campoLogin.getText());
                char[] senha = campoSenha.getPassword();
                System.out.println(senha);

                Cliente clienteValidado = null;
                for(Cliente c : Admin.getClientes()){
                    if(c.getId() == login) clienteValidado = c;
                }

//        TelaContas01 dialog = new TelaContas01();  Para Chamar Outras Telas
//        dialog.pack();
//        dialog.setVisible(true);

                // Tela de Erro
                if (clienteValidado == null || !clienteValidado.getSenha().equals(senha)) {
                    System.out.println("Erro no Login");
                    System.out.println(login);
                    System.out.println(senha);
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
        });


        buttonCancel = new JButton("Cancelar");
        buttonCancel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        buttonCancel.setBackground(Color.white);
        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        panelBt.add(buttonCancel);
        panelBt.add(buttonOK);

        getContentPane().add(panelBt);

    }
}