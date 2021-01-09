package com.company;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Main {
    // Tudo static porque Main nao e instanciada
    public static GregorianCalendar dataAtual = new GregorianCalendar(2020, 11, 7);

    public static void main(String[] args) {
        // acoes
        for(Acoes acao : Acoes.values()){
            System.out.println("Ação da " + acao.getEmpresa() + " (" + acao.getTicker() + ") em tempo Real: R$" + acao.precoTempoReal());
        }

        /*
        float valor;
        boolean v;

        // Instanciando objetos
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

        // clinte c5 definido com entradas do usuario
        Scanner entrada = new Scanner(System.in);
        Cliente c5 = new Cliente();
        v = false;

        System.out.print("*** Cadastro de novo cliente - Entradas do usuario ***\nInsira as informacoes solicitadas abaixo:\n");
        System.out.print("Nome: ");
        c5.setNome(entrada.nextLine());
        System.out.print("Idade: ");
        c5.setIdade(entrada.nextInt());
        System.out.print("Sexo (M ou F): ");
        while (!v) {
            String v2 = entrada.next();
            if (v2.equals("M")){
                c5.setSexo(Sexo.MASCULINO);
                v = true;
            } else if (v2.equals("F")) {
                c5.setSexo(Sexo.FEMININO);
                v = true;
            } else {
                System.out.print("Entrada invalida, por favor, redigite: ");
            }
        }
        entrada.nextLine();
        System.out.print("Profissao: ");
        c5.setProfissao(entrada.nextLine());
        System.out.print("O login para acesso a sua conta: ");
        System.out.print(c5.getId());
        System.out.print("\nDefina uma senha (sem espacos): ");
        c5.setSenha(entrada.next());
        System.out.print("Qual o tipo da conta?\n 1 - Conta Corrente\n 2 - Conta Poupanca\n 3 - Conta Investidor\nResp: ");
        v = false;
        while (!v) {
            int v3 = entrada.nextInt();
            if (v3 == 1){
                c5.abrirConta(v3);
                v = true;
            } else if (v3 == 2) {
                c5.abrirConta(v3);
                v = true;
            } else if (v3 == 3){
                c5.abrirConta(v3);
                v = true;
            } else {
                System.out.print("Entrada invalida, por favor, redigite: ");
            }
        }
        System.out.print("Qual o valor do deposito inicial (utilize ',' para casas decimais)? ");
        c5.getConta().depositar(entrada.nextFloat());
        System.out.print("Novo cliente criado. Seja bem-vindo!\n\n");
        entrada.close();

        // Mostrando objetos criados com toString()
        System.out.println("*** Instanciando Objetos ***");
        System.out.println(c1);
        System.out.println(c2);
        System.out.println(c3);
        System.out.println(c4);
        System.out.println(c5);

        // Testando os metodos de Cliente
        System.out.println("*** Metodos de Cliente ***");
        System.out.println("-> Cliente.fecharConta()");
        c4.fecharConta();
        System.out.println(c4);
        System.out.println("-> Cliente.abrirConta()");
        c4.abrirConta(3); // Abre nova conta Investidor
        System.out.println(c4);
        System.out.println("-> Cliente.verConta()");
        System.out.println(c2.verConta());

        // Testando os metodos de ContaBancaria
        System.out.println("*** Metodos de ContaBancaria ***");
        System.out.println("-> ContaBancaria.depositar()");

        // ContaBancaria.depositar() retorna boolean
        valor = 1350;
        if (c2.getConta().depositar(valor)) System.out.println("Deposito de R$" + valor + " realizado com sucesso!");
        else System.out.println("Nao foi possivel fazer o deposito!");
        System.out.println(c2); //Mostrara 40000 + 1350

        System.out.println("-> ContaBancaria.retirar()");

        // ContaBancaria.retirar() retorna boolean
        valor = 720;
        if (c3.getConta().retirar(valor)) System.out.println("Valor de R$" + valor + " retirado com sucesso!");
        else System.out.println("Nao foi possivel retirar o valor de R$" + valor + " !");
        System.out.println(c3); // Mostrara 10000,01 - 720

        // ContaBancaria.depositar() retorna boolean
        System.out.println("-> ContaBancaria.depositar() - objeto criado via entradas do usuario");
        valor = 200;
        if (c5.getConta().depositar(valor)) System.out.println("Deposito de R$" + valor + " realizado com sucesso!");
        else System.out.println("Nao foi possivel fazer o deposito!");
        System.out.println(c5);

        // ContaBancaria.retirar() retorna boolean
        System.out.println("-> ContaBancaria.retirar() - objeto criado via entradas do usuario");
        valor = 2000;
        if (c5.getConta().retirar(valor)) System.out.println("Valor de R$" + valor + " retirado com sucesso!");
        else System.out.println("Nao foi possivel retirar o valor de R$" + valor + " !");
        System.out.println(c5);

        // Testando metodos de Admin que nao eh instanciado
        System.out.println("*** Metodos de Admin ***");
        System.out.println("-> Admin.desativaCliente()");
        Admin.desativaCliente(c1); // seta o status do cliente para false
        System.out.println(c1); // Status estara como "Cliente inativo"

        // Nao eh possivel depositar pois cliente inativo
        System.out.println("-> c1 tenta depositar");
        valor = 10000;
        if (c1.getConta().depositar(valor)) System.out.println("Deposito de R$" + valor + " realizado com sucesso!");
        else System.out.println("Nao foi possivel fazer o deposito!");
        System.out.println(c1); // Saldo permanecera igual, porque cliente esta inativo

        // Nao eh possivel retirar pois cliente esta inativo
        System.out.println("-> c1 tenta retirar");
        valor = 423;
        if (c1.getConta().retirar(valor)) System.out.println("Valor de R$" + valor + " retirado com sucesso!");
        else System.out.println("Nao foi possivel retirar o valor de R$" + valor + " !");
        System.out.println(c1); // Saldo permanecera igual, porque cliente esta inativo
        
        System.out.println("-> Admin.ativaCliente()");
        Admin.ativaCliente(c1);// "Cliente ativo"
        
        System.out.println("-> c1 tenta depositar");
        valor = 1280;
        if (c1.getConta().depositar(valor)) System.out.println("Deposito de R$" + valor + " realizado com sucesso!");
        else System.out.println("Nao foi possivel fazer o deposito!");
        System.out.println(c1); // Agora cliente esta ativo, entao Saldo = 5000 + 1280 
        
        System.out.println("-> Admin.alterarSaldo()");
        Admin.alterarSaldo(2, 1234); // Altera o saldo do cliente com id = 2 para 1234
        System.out.println(c2);
        
        System.out.println("-> Admin.verCliente()");
        System.out.println(Admin.verCliente(3));

        // Testando metodos de ContaInvestidor
        System.out.println("\n*** Metodos de ContaInvestidor ***");
        System.out.println("-> ContaInvestidor.criaInvestimentoRF()");

        // ContaInvestidor.criarInvestimentoRF retorna boolean
        ContaInvestidor conta3 = (ContaInvestidor) c3.getConta();
        if (conta3.comprarRF(AtivosRF.TESOURO_SELIC, 500f)) System.out.println("Novo investimento criado com sucesso!");
        else System.out.println("Nao foi possivel criar o investimento!");
        if (conta3.comprarRF(AtivosRF.CDB_BANCO_INTER, 1000f)) System.out.println("Novo investimento criado com sucesso!");
        else System.out.println("Nao foi possivel criar o investimento!");

        System.out.println(conta3); // Foi investido 1500 em investimento, entao sera tirado 1500 do saldo disponivel e adicionado 1550 no montante investido

        ContaInvestidor conta4 = (ContaInvestidor) c4.getConta();
        if (conta4.comprarRF(AtivosRF.CDB_BANCO_ITAU, 1300f)) System.out.println("Novo investimento criado com sucesso!");
        else System.out.println("Nao foi possivel criar o investimento!");
        if (conta4.comprarRF(AtivosRF.LCI_CAIXA, 900f)) System.out.println("Novo investimento criado com sucesso!");
        else System.out.println("Nao foi possivel criar o investimento!");

        System.out.println(conta4);

        System.out.println("-> ContaInvestidor.resgatarInvestimento()");
        conta3.venderRF((RendaFixa) conta3.getInvestimentos().get(1));
        System.out.println(conta3); // Conseguira resgatar 95% do valor investido, pois retirou antes do prazo e sofre penalidade

        System.out.println("*** Simulando passagem do tempo, mostrando rendimentos ***");
        System.out.println("Data Atual: " + Data.formataDMA(dataAtual) + "\n");
        System.out.println("-> setDataAtual()");
        setDataAtual(new GregorianCalendar(2020, 11, 8)); // Mudanca de data, afim de teste para ver os rendimentos
        System.out.println("Data Atual: " + Data.formataDMA(dataAtual) + "\n");
        
        System.out.println(c2.getConta());// Conta poupanca, rendeu apos 1 dia (0,007% no caso)
        System.out.println(conta3);
        System.out.println(conta4);// Os investimentos renderam apos 1 dia

        System.out.println("-> setDataAtual()");
        setDataAtual(new GregorianCalendar(2021, 5, 13));
        System.out.println("Data Atual: " + Data.formataDMA(dataAtual) + "\n");
        System.out.println(c2.getConta()); 
        System.out.println(conta3);
        System.out.println(conta4); //Mostrara como rendeu os investimentos durante o tempo q passou entre uma data e outra

         */

    }

    public static void rendeTudo(GregorianCalendar ultimaVezQueRendeu){
        // Atualiza todos os valores de todas as contas de todos os clientes
        // Deve ser chamado uma vez ao dia. Para fins de teste do sistema foi criado o metodo Main.setDataAtual
        long diasPassados = Data.diasEntre(ultimaVezQueRendeu, dataAtual);

        for(Cliente cliente : Admin.getClientes()){
            // Percorre todos os clientes, que estao guardados no ArrayList do Admin
            if(cliente.getStatus()){ // Se cliente esta ativo, rende sua conta
                ContaBancaria conta = cliente.getConta();
                if (conta instanceof ContaPoupanca){ // ContaPoupanca rende diferente de ContaInvestidor
                    // Saldo apos n dias = saldo*(1 + rendimentoDiario)^n
                    float novoSaldo = (float) (conta.getSaldo() * Math.pow((1 + ContaPoupanca.getRendimentoDiario()), diasPassados));

                    novoSaldo = (float) Math.round(novoSaldo*100)/100; // Arredonda pra duas casas decimais
                    conta.setSaldo(novoSaldo);
                    // atualiza o dinheiroTotal do Cliente
                    cliente.setDinheiroTotal(novoSaldo);
                } else if (conta instanceof ContaInvestidor){
                    // deve render os montantes de todos os investimentos da conta
                    for(Investimento i : ((ContaInvestidor) conta).getInvestimentos()){
                        if (i instanceof RendaFixa){ // RendaFixa rende de uma forma especifica
                            AtivosRF ativo = ((RendaFixa) i).getAtivo();
                            // Montante apos n dias = montante * (1 + rentabilidade)^n
                            float novoMontante = (float) (i.getMontante() * Math.pow(1 + ativo.getRentabilidade(), diasPassados));
                            novoMontante = (float) Math.round(novoMontante*100)/100; // Arredonda pra duas casas decimais
                            i.setMontante(novoMontante);
                        }
                    }
                    // atualiza o dinheiroTotal do Cliente, que sera o saldo da conta + o dinheiro investido
                    cliente.setDinheiroTotal(conta.getSaldo() + ((ContaInvestidor) conta).getMontanteTotal());

                } // Conta Corrente nao rende nada
            }
        }
    }

    public static void setDataAtual(GregorianCalendar novaData){
        // Metodo para fins de testes, para definir a data do sistema e analisar os rendimentos dos investimentos
        GregorianCalendar dataAntiga = dataAtual;
        dataAtual = novaData;
        rendeTudo(dataAntiga); // Compara com a data atual
    }


}