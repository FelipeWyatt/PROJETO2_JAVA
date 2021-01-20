package com.company;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.GregorianCalendar;

public class Main {
    // Tudo static porque Main nao e instanciada
    public static GregorianCalendar dataAtual = new GregorianCalendar(2020, 11, 7);


    public static void main(String[] args) {

        if(resgataClientes()){
            //System.out.println("Dados resgatados com Sucesso!");
            // Inicia TelaLogin, que eventualmente chama as outras telas
            TelaLogin telaLogin = new TelaLogin();

        } else {
            // Caso não funcione resgatar os clientes pelo arquivo clientes.dat
            JOptionPane.showMessageDialog(null,
                    "Não foi possível acessar os dados dos clientes.", null, JOptionPane.WARNING_MESSAGE);
        }

        /*
        //Novo Cliente:
        Admin.novoCliente("Renata", "adbc", 24, Sexo.FEMININO, "PED", 3000f);
        Cliente c5 = Admin.getClientes().get(4);
        Cliente c6 = new Cliente("Pedro", "1senha3", 24, Sexo.MASCULINO, "PED", 2000f);

        //Abrir Conta:
        c5.abrirConta(1); // Conta Corrente
        c6.abrirConta(2); // Conta Poupanca
        System.out.println("\n*** Visualizando Objetos ***\n");
        System.out.println(c5);
        System.out.println(c6);

        //Fechar Conta:
        System.out.println("\n*** Fechando Conta ***\n");
        c6.fecharConta();
        System.out.println(c6);

        //Desativa Cliente:
        System.out.println("\n*** Metodos de Admin ***\n");
        System.out.println("-> Admin.desativaCliente()");
        Admin.desativaCliente(c5); // seta o status do cliente para false
        System.out.println(c5); // Status estará como "Cliente inativo"

        // Nao eh possivel depositar pois cliente inativo
        System.out.println("-> c5 tenta depositar");
        int valor = 10000;
        if (c5.getConta().depositar(valor)) System.out.println("Deposito de R$" + valor + " realizado com sucesso!");
        else System.out.println("Nao foi possivel fazer o deposito!");
        System.out.println(c5); // Saldo permanecera igual, porque cliente esta inativo

        //Ativa Cliente:
        System.out.println("-> Admin.ativaCliente()");
        Admin.ativaCliente(c5);// "Cliente ativo"

        System.out.println("-> c5 tenta depositar");
        valor = 1280;
        if (c5.getConta().depositar(valor)) System.out.println("Deposito de R$" + valor + " realizado com sucesso!");
        else System.out.println("Nao foi possivel fazer o deposito!");
        System.out.println(c5); // Agora cliente esta ativo, entao Saldo = 5000 + 1280

         */


        /*
        //setDataAtual(new GregorianCalendar(2021, 2, 19));
        Cliente c1 = Admin.getClientes().get(0);
        Cliente c2 = Admin.getClientes().get(1);
        Cliente c3 = Admin.getClientes().get(2);

        TelaConta TConta1 = new TelaConta(c1);
        TConta1.pack();
        TConta1.setVisible(true);

        TelaConta TConta2 = new TelaConta(c2);
        TConta2.pack();
        TConta2.setVisible(true);

        TelaConta TConta3 = new TelaConta(c3);
        TConta3.pack();
        TConta3.setVisible(true);

         */



        /*


        tela3.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
            }

            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                if(salvaClientes()){
                    System.out.println("Dados salvos com Sucesso!");
                } else {
                    System.out.println("Erro! Dados não foram salvos.");
                }
            }
        });

         */





         /*
        // Alguns objetos do sistema
        Admin.novoCliente("Felipe", "abcd", 19, Sexo.MASCULINO, "Engenheiro", 5000f);
        Cliente c1 = Admin.getClientes().get(0);
        Admin.novoCliente("Matheus", "02", 13, Sexo.MASCULINO, "Engenheiro", 40000f);
        Cliente c2 = Admin.getClientes().get(1);
        Cliente c3 = new Cliente("Miguelzinho", "senha123", 25, Sexo.MASCULINO, "Soldador", 10000.01f);
        Cliente c4 = new Cliente("Pedrao", "Diniz", 34, Sexo.MASCULINO, "Tecnico do senai", 20000f);

        c1.abrirConta(1); // Conta Corrente
        c2.abrirConta(2); // Conta Poupanca
        c3.abrirConta(3); // Conta Investidor
        c4.abrirConta(1); // Conta Corrente

        ((ContaInvestidor) c3.getConta()).comprarRF(AtivosRF.TESOURO_SELIC, 500f);
        ((ContaInvestidor) c3.getConta()).comprarRF(AtivosRF.LCI_CAIXA, 600f);
        ((ContaInvestidor) c3.getConta()).comprarRF(AtivosRF.LCI_CAIXA, 350f);
        ((ContaInvestidor) c3.getConta()).comprarAcao(Acoes.TESLA, 5);
        ((ContaInvestidor) c3.getConta()).comprarAcao(Acoes.AMAZON, 4);
        ((ContaInvestidor) c3.getConta()).comprarAcao(Acoes.APPLE, 3);


        System.out.println(((ContaInvestidor) c3.getConta()).verInvestimentos());

         */

    }


    public static void rendeTudo(GregorianCalendar ultimaVezQueRendeu){
        // Atualiza todos os valores de todas as contas de todos os clientes
        // Deve ser chamado uma vez ao dia. Para fins de teste do sistema foi criado o metodo Main.setDataAtual
        int diasPassados = (int) Data.diasEntre(ultimaVezQueRendeu, dataAtual); //POR QUE LONG?

        for(Cliente cliente : Admin.getClientes()){
            // Percorre todos os clientes, que estao guardados no ArrayList do Admin
            if(cliente.getStatus()){ // Se cliente esta ativo, rende sua conta
                // Polimorfismo presente na utilização do método rendeConta, que funciona independente do
                // tipo de conta, já que a classe ContaBancaria se tornou abstrata.
                cliente.getConta().rendeConta(diasPassados);
            }
        }
    }

    public static void setDataAtual(GregorianCalendar novaData){
        // Metodo para fins de testes, para definir a data do sistema e analisar os rendimentos dos investimentos
        GregorianCalendar dataAntiga = dataAtual;
        dataAtual = novaData;
        rendeTudo(dataAntiga); // Compara com a data atual
    }

    public static boolean salvaClientes(){
        // Guarda os dados de todos os clientes do sistema e consequentemente das contas e investimentos
        String filename = "clientes.dat";
        try{
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filename));
            for(Cliente c : Admin.getClientes()){
                output.writeObject(c);
                output.flush();
            }
            output.close();
            return true;
        } catch(IOException erro){
            System.out.println("Erro escrevendo os clientes");
        }
        return false;
    }

    public static boolean resgataClientes(){
        // Resgata os objetos Clientes guardados no Arquivo e associa à Admin.clientes
        String filename = "clientes.dat";
        int maxIdCliente = 0, maxIdConta = 0;
        try{
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(filename));
            while(true){
                Cliente c = (Cliente) input.readObject();
                Admin.adicionaCliente(c);
                if(c.getId() > maxIdCliente) maxIdCliente = c.getId();
                if(c.getConta().getId() > maxIdConta) maxIdConta = c.getConta().getId();
            }
        } catch(EOFException endOfFileException){
            // Arquivo terminou de ser lido
            // Atualiza atributos de classe
            Cliente.setNumClientes(maxIdCliente);
            ContaBancaria.setNumContas(maxIdConta);
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("Erro 1 lendo arquivo clientes.dat");
        } catch (IOException e) {
            System.out.println("Erro 2 lendo arquivo clientes.dat");
        } catch (ClassNotFoundException e) {
            System.out.println("Erro 3 lendo arquivo clientes.dat");
        }
        return false;
    }

}
