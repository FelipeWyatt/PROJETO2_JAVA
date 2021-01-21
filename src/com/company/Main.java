package com.company;

import javax.swing.*;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Main {
    // Tudo static porque Main nao e instanciada
    public static GregorianCalendar dataAtual = new GregorianCalendar();

    public static void main(String[] args) {
        /*-------------------------------------------------------------------------------------------------------------
        O sistema contém os seguintes usuários pré-determinados, que podem sem utilizados para login:
        (id) -> (senha)
        1 -> abcd
        2 -> 02
        3 -> senha123
        4 -> Diniz
        5 -> asdf
        6 -> minhasenha
         -------------------------------------------------------------------------------------------------------------*/

        if(resgataClientes()){
            //setDataAtual(new GregorianCalendar(2021, 2, 21));
            rendeTudo();

            TelaLogin telaLogin = new TelaLogin(); // Inicia TelaLogin, que eventualmente chama as outras telas

        } else {
            // Caso não funcione resgatar os clientes pelo arquivo clientes.dat, pode-se reescrever o arquivo novamente
            //com os objetos criados para demonstração
            int resposta;

            resposta = JOptionPane.showConfirmDialog(null,
                    "Não foi possível acessar os dados dos clientes. Desejar executar um backup dos clientes?");

            if (resposta == JOptionPane.YES_OPTION){
                backupDemonstracao();
            }
            System.exit(0);
        }


        // CÓDIGO PARA DEMONSTRAÇÃO DE FUNÇÕES
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

        //setDataAtual(new GregorianCalendar(2021, 2, 19));

         */

    }


    public static void rendeTudo(){
        // Atualiza todos os valores de todas as contas de todos os clientes e salva no arquivo a ultima vez que rendeu
        //para impedir que renda mais de uma vez no dia caso o programa for executado novamente
        // Para fins de teste do sistema foi criado o metodo Main.setDataAtual

        try{
            // Recupera no arquivo a data da ultima vez que rendeu
            BufferedReader arqData = new BufferedReader(new FileReader("arquivos/ultima_vez_que_rendeu.txt"));
            String[] ultimaVezQueRendeuString = arqData.readLine().split("/");
            arqData.close();
            int dia = Integer.parseInt(ultimaVezQueRendeuString[0]);
            int mes = Integer.parseInt(ultimaVezQueRendeuString[1]);
            int ano = Integer.parseInt(ultimaVezQueRendeuString[2]);

            // Obtem o objeto GregorianCalendar da ultima vez que rendeu que estava guardada no arquivo
            GregorianCalendar ultimaVezQueRendeu = new GregorianCalendar(ano, mes, dia);

            int diasPassados = (int) Data.diasEntre(ultimaVezQueRendeu, dataAtual);

            if (diasPassados > 0){
                // diasPassados = 0 se ja rendeu hoje
                for(Cliente cliente : Admin.getClientes()){
                    // Percorre todos os clientes, que estao guardados no ArrayList do Admin
                    if(cliente.getStatus()){ // Se cliente esta ativo, rende sua conta
                        // Polimorfismo presente na utilização do método rendeConta, que funciona independente do
                        // tipo de conta, já que a classe ContaBancaria se tornou abstrata.
                        cliente.getConta().rendeConta(diasPassados);
                    }
                }
            }

            // Salva a data atual para como a data da ultima vez que rendeu no mesmo arquivo em que a data foi resgatada
            BufferedWriter arqDataSaida = new BufferedWriter(new FileWriter("arquivos/ultima_vez_que_rendeu.txt"));
            arqDataSaida.write(dataAtual.get(GregorianCalendar.DAY_OF_MONTH) + "/" +
                    dataAtual.get(GregorianCalendar.MONTH) + "/" +
                    dataAtual.get(GregorianCalendar.YEAR));
            arqDataSaida.flush();
            arqDataSaida.close();


        } catch(Exception erro){
            JOptionPane.showMessageDialog(null,
                    "Não foi possível render as contas. Contactar gerenciador do sistema", null, JOptionPane.ERROR_MESSAGE);
            erro.printStackTrace();
            System.exit(-1);
        }

    }

    public static void setDataAtual(GregorianCalendar novaData){
        // altera a data atual para mostrar funcionamento do sistema
        dataAtual = novaData;
    }

    public static boolean salvaClientes(){
        // Guarda os dados de todos os clientes do sistema e consequentemente das contas e investimentos
        String filename = "arquivos/clientes.dat";
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
        String filename = "arquivos/clientes.dat";
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
            System.out.println("Erro 1 lendo arquivo arquivos/clientes.dat");
        } catch (IOException e) {
            System.out.println("Erro 2 lendo arquivo arquivos/clientes.dat");
        } catch (ClassNotFoundException e) {
            System.out.println("Erro 3 lendo arquivo arquivos/clientes.dat");
        }
        return false;
    }

    private static void backupDemonstracao(){
        // Salva alguns clientes pré-determinados para demonstração do sistema
        Cliente c1 = new Cliente("Felipe", "abcd", 19, Sexo.MASCULINO, "Engenheiro", 5000f);
        Cliente c2 = new Cliente("Matheus", "02", 13, Sexo.MASCULINO, "Engenheiro", 40000f);
        Cliente c3 = new Cliente("Miguelzinho", "senha123", 25, Sexo.MASCULINO, "Soldador", 10000f);
        Cliente c4 = new Cliente("Pedrao", "Diniz", 34, Sexo.MASCULINO, "Tecnico do senai", 20321f);
        Cliente c5 = new Cliente("Renata", "asdf", 24, Sexo.FEMININO, "PED", 35000f);
        Cliente c6 = new Cliente("Pedro", "minhasenha", 24, Sexo.MASCULINO, "PED", 2000f);

        c1.abrirConta(1); // Conta Corrente
        c2.abrirConta(2); // Conta Poupanca
        c3.abrirConta(3); // Conta Investidor
        c4.abrirConta(1); // Conta Corrente
        c5.abrirConta(3); // Conta Corrente
        c6.abrirConta(2); // Conta Poupanca

        ((ContaInvestidor) c3.getConta()).comprarRF(AtivosRF.TESOURO_SELIC, 500f);
        ((ContaInvestidor) c3.getConta()).comprarRF(AtivosRF.LCI_CAIXA, 600f);
        ((ContaInvestidor) c3.getConta()).comprarRF(AtivosRF.LCI_CAIXA, 350f);
        ((ContaInvestidor) c3.getConta()).comprarAcao(Acoes.TESLA, 5);
        ((ContaInvestidor) c3.getConta()).comprarAcao(Acoes.AMAZON, 4);
        ((ContaInvestidor) c3.getConta()).comprarAcao(Acoes.APPLE, 3);

        ((ContaInvestidor) c5.getConta()).comprarAcao(Acoes.TESLA, 3);

        salvaClientes();
    }

}
