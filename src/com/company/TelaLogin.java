package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class TelaLogin extends JDialog {
    private JButton buttonOK;
    private JTextField campoLogin;
    private JPasswordField campoSenha;

    public TelaLogin() {
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)); // Painel principal
        setResizable(false);
        setModal(true);
        setTitle("Login");
        setBackground(Color.white);
        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        getRootPane().setDefaultButton(buttonOK = new JButton("OK"));

        try{
            // Adiciona a imagem com o logo do projeto
            BufferedImage logoBuff = ImageIO.read(new File("imagens/logoBankCamp.png"));

            JLabel logoLabel = new JLabel(new ImageIcon(logoBuff));
            getContentPane().add(logoLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JPanel panelColunas = new JPanel();
        panelColunas.setLayout(new BoxLayout(panelColunas, BoxLayout.X_AXIS));
        panelColunas.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelColunas.setBackground(Color.white);

        JPanel panelLabels = new JPanel();
        panelLabels.setLayout(new BoxLayout(panelLabels, BoxLayout.Y_AXIS));
        panelLabels.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelLabels.setBackground(Color.white);

        panelLabels.add(new JLabel("ID: "));
        panelLabels.add(new JLabel("Senha: "));

        panelColunas.add(panelLabels);

        JPanel panelEntradas = new JPanel();
        panelEntradas.setLayout(new BoxLayout(panelEntradas, BoxLayout.Y_AXIS));
        panelEntradas.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelEntradas.setBackground(Color.white);

        campoLogin = new JTextField();
        campoLogin.setColumns(10);
        campoLogin.setBackground(Color.white);
        campoLogin.setBorder(BorderFactory.createMatteBorder(2,3,1,3, Color.black));
        panelEntradas.add(campoLogin);

        campoSenha = new JPasswordField();
        campoSenha.setColumns(10);
        campoSenha.setBackground(Color.white);
        campoSenha.setBorder(BorderFactory.createMatteBorder(1,3,2,3, Color.black));
        panelEntradas.add(campoSenha);

        panelColunas.add(panelEntradas);

        panelColunas.add(Box.createRigidArea(new Dimension(10,0)));

        buttonOK = new JButton("Entrar");
        buttonOK.setAlignmentX(Component.RIGHT_ALIGNMENT);
        buttonOK.setBackground(Color.lightGray);
        buttonOK.setSize(new Dimension(campoLogin.getHeight()*2, 30));
        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int login = -1;
                try{
                    login = Integer.parseInt(campoLogin.getText());
                } catch (Exception erro){
                    JOptionPane.showMessageDialog(null,
                            "ID inválido! Digite um inteiro.", null, JOptionPane.ERROR_MESSAGE);
                    return;
                }

                char[] senha = campoSenha.getPassword();

                Cliente clienteValidado = null;
                for(Cliente c : Admin.getClientes()){
                    if(c.getId() == login) clienteValidado = c;
                }

                campoLogin.setText("");
                campoSenha.setText("");

                // Trata as entradas
                if(clienteValidado == null){
                    // Não foi encontrado o Cliente com esse id
                    JOptionPane.showMessageDialog(null,
                            "Cliente não foi encontrado.", null, JOptionPane.WARNING_MESSAGE);
                } else if(!Arrays.equals(senha, clienteValidado.getSenha().toCharArray())){
                    // Senha entrada é diferente da senha do cliente
                    JOptionPane.showMessageDialog(null,
                            "Senha incorreta.", null, JOptionPane.WARNING_MESSAGE);
                } else {
                    // Senha correta para cliente
                    // Fecha essa TelaLogin
                    dispose();
                    // Abre a TelaConta do Cliente
                    TelaConta telaConta = new TelaConta(clienteValidado);
                    // Quando TelaConta fecha, finaliza-se a execução do programa e então salva-se todos os objetos do sistema chamando Main.salvaClientes()
                    telaConta.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            super.windowClosing(e);
                            if(Main.salvaClientes()){
                                //System.out.println("Dados salvos com Sucesso!");
                            } else {
                                // Caso não funcione resgatar os clientes pelo arquivo clientes.dat
                                JOptionPane.showMessageDialog(null,
                                        "Não foi possível salvar os dados do cliente, dados criados nessa execução foram perdidos.", null, JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    });
                    telaConta.pack();
                    telaConta.setLocation(400, 250); // Posição inicial da TelaConta no monitor do pc
                    telaConta.setVisible(true);
                }
            }
        });

        panelColunas.add(buttonOK);

        getContentPane().add(panelColunas);

        pack();
        setLocation(500, 70);
        setVisible(true);

    }
}