package com.company;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;

public class ContaInvestidor extends ContaBancaria { // Tem acesso a investimentos
    private ArrayList<Investimento> investimentos;

    //METODO CONSTRUTOR
    public ContaInvestidor (float saldo, Cliente dono) {
        super(saldo, dono);
        investimentos = new ArrayList<Investimento>();
    }

    // METODOS
    public boolean comprarRF(AtivosRF ativo, float montante) {
    	if (getDono().getStatus() && getSaldo() >= montante && montante > 0) {
		    // Implementação de polimosfismo pois ArrayList de Investimento contém Acao e RF
	        Investimento novo_investimento = new RendaFixa(montante, new GregorianCalendar(), ativo);
	        investimentos.add(novo_investimento);
	        setSaldo(getSaldo() - montante);
            return true;
    	} else {
    		return false;
    	}
    }

    public float venderRF(RendaFixa investimento) {
        // Retira o dinheiro aplicado em um investimento de renda fixa, rendendo uma quantidade proporcional ao tempo que o
        //dinheiro ficou aplicado. Caso a retirada Seja feita antes da data de vencimento do ativo, uma penalidade é aplicada

        if(getDono().getStatus()){
            getInvestimentos().remove(investimento);
            GregorianCalendar dataCompra = investimento.getDataCompra();
            GregorianCalendar dataVencimento = ((RendaFixa) investimento).getAtivo().getVencimento();

            if (Data.diasEntre(dataCompra, dataVencimento) <= 0) { // verificar ordem das datas
                // Entao esta sendo resgatado depois do prazo, sem penalidade
                setSaldo(getSaldo() + investimento.getMontante());
                return investimento.getMontante();
            } else {
                // Entao esta sendo resgatado antes do prazo, com penalidade
                float montante = investimento.getMontante();
                float valorPenalidade = montante * ((RendaFixa) investimento).getAtivo().getPenalidade();
                setSaldo(getSaldo() + montante - valorPenalidade);
                return montante - valorPenalidade;
            }
        }

        return -1f;
        
    }

    public boolean comprarAcao(Acoes acao, int quantidade) {
        float montante = acao.precoTempoReal()*quantidade;

        if(getDono().getStatus()  && getSaldo() >= montante && montante > 0){
            // Verifica se investimentos já contém essa ação
            for(Investimento i: investimentos){
                if(i instanceof Acao){
                    if(((Acao) i).getAcao() == acao){
                        ((Acao) i).setQuantidade(((Acao) i).getQuantidade() + quantidade);
                        i.rendeInvestimento(0); // Atualiza o montante do investimento
                        setSaldo(getSaldo() - montante); // retira o valor das ações do saldo da conta

                        return true;
                    }
                }
            }

            // Caso o cliente não possua essa ação:
            Investimento novo_investimento = new Acao(acao, quantidade, acao.precoTempoReal(), new GregorianCalendar());
            investimentos.add(novo_investimento);
            setSaldo(getSaldo() - montante);
            return true;
        }
        return false;
    }

    public float venderAcao(Acoes acao, int quantidade) {
        // Vende uma acao de acordo com o valor dela na data da venda. A diferença entre o valor atual (precoVenda) e o valor 
	    //na compra (precoCompra) irá determinar lucro ou prejuízo.

        if(getDono().getStatus()) { //se cliente ativo
            for (Investimento i : investimentos) {
                if(i instanceof Acao) {
                    if(((Acao) i).getAcao() == acao && quantidade <= ((Acao) i).getQuantidade() && quantidade > 0) {
                        if(quantidade == ((Acao) i).getQuantidade()){
                            // DEseja-se se desfazer do investimento e vender todas as ações
                            investimentos.remove(i);
                        } else {
                            // Deseja-se vender uma parte das ações
                            ((Acao) i).setQuantidade(((Acao) i).getQuantidade() - quantidade);
                            i.rendeInvestimento(0); // Apenas atualiza montante do investimento com a nova quantidade
                        }
                        float valor = quantidade*acao.precoTempoReal();
                        setSaldo(getSaldo() + valor); //diferença de preços da acao em dias diferentes

                        return i.getMontante();
                    }
                }
            }
        }
        return -1f;
    }

    public void rendeConta (int diasPassados) {
        //rende todos os investimentos da conta (atualiza o montante), independente do tipo
        for (Investimento i : investimentos) {
	    // Polimorfismo na utilização do método rendeInvestimento.
	    // Note que não importa o tipo de investimento (Renda Fixa ou Ações) devido ao método abstrato.
            i.rendeInvestimento(diasPassados);
        }
        //atualiza dinheito total em Cliente
        getDono().setDinheiroTotal(getSaldo() + getMontanteTotal());
    }

    @Override
    public String toString () {
    	DecimalFormat d1 = new DecimalFormat("#. 00");
        String out = "CONTA INVESTIDOR \n";
        out += super.toString();
        out += "Montante total investido: R$" + d1.format(getMontanteTotal()) + "\n";
        out += verInvestimentos();
        return out;
    }

    public String verInvestimentos() { // Chama os toString de cada investimento, mais geral
        DecimalFormat d1 = new DecimalFormat("#. 00"); //formata do jeito certo
        if (getDono().getStatus()) {
            Collections.sort(investimentos); // ordena a lista do maior para o menor montante
            String out = "Investimentos:\n";
            out += "* Renda Fixa:\n"; // Primeiro printa os investimentos de RF
            for (Investimento i : investimentos){        
                if (i instanceof RendaFixa) {
                    out += "- " + ((RendaFixa) i).getAtivo().getNome() + ": R$" + d1.format(i.getMontante()) + "\n";
                }
            }
            out += "* Ações:\n"; // Depois printa os investimentos de Acao
            for (Investimento i : investimentos){               	
                if (i instanceof Acao) {
                	out += "- " + ((Acao) i).getAcao().getEmpresa() + ": R$" + d1.format(i.getMontante()) + "\n";
                }
            }
            return out;
        }
        else {
            return "Erro: Cliente desativado";
        }
    }

    public float getMontanteTotal () {
        float total = 0;
        for (Investimento i : investimentos) {
            total += i.getMontante();
        }
        return (float) Math.round(total*100)/100; // Arredonda para 2 casas decimais
    }

    public String getNomesAtivos() {
        String out = "";
        Collections.sort(investimentos);
        out += "* Renda Fixa:\n";
        for (Investimento i : investimentos){        
            if (i instanceof RendaFixa) {
            	out += ((RendaFixa) i).getAtivo().getNome() + "   ";
            }
        }
        out += "\n* Ações:\n";
        for (Investimento i : investimentos){               	
            if (i instanceof Acao) {
            	out += ((Acao) i).getAcao().getEmpresa() + "   ";
            }
        }
        return out;
    }

    public ArrayList<Acao> getAcoes(){
        // Usado em TelaInvestimentos
        Collections.sort(investimentos); // ordena a lista do maior para o menor montante
        ArrayList<Acao> saida = new ArrayList<Acao>();

        for (Investimento i : investimentos){
            if (i instanceof Acao) {
                saida.add((Acao) i);
            }
        }

        return saida;
    }

    public ArrayList<String> getAcoesString(){
        // Usado em TelaInvestimentos
        DecimalFormat d1 = new DecimalFormat("#. 00"); //formata do jeito certo
        Collections.sort(investimentos); // ordena a lista do maior para o menor montante
        ArrayList<String> saida = new ArrayList<String>();

        for (Investimento i : investimentos){
            if (i instanceof Acao) {
                saida.add(((Acao) i).getAcao().getTicker() + " (x" + ((Acao) i).getQuantidade() + ") : R$" + d1.format(i.getMontante()));
            }
        }

        return saida;
    }
    public ArrayList<RendaFixa> getRF(){
        // Usado em TelaInvestimentos
        Collections.sort(investimentos); // ordena a lista do maior para o menor montante
        ArrayList<RendaFixa> saida = new ArrayList<RendaFixa>();

        for (Investimento i : investimentos){
            if (i instanceof RendaFixa) {
                saida.add((RendaFixa) i);
            }
        }

        return saida;
    }


    public ArrayList<String> getRFString(){
        // Usado em TelaInvestimentos
        DecimalFormat d1 = new DecimalFormat("#. 00"); //formata do jeito certo
        Collections.sort(investimentos); // ordena a lista do maior para o menor montante
        ArrayList<String> saida = new ArrayList<String>();

        for (Investimento i : investimentos){
            if (i instanceof RendaFixa) {
                saida.add(((RendaFixa) i).getAtivo().getNome() + ": R$" + d1.format(i.getMontante()));
            }
        }

        return saida;
    }

    //GETTERS E SETTERS
    public ArrayList<Investimento> getInvestimentos () { return investimentos; }
    // Nao faz sentido ter um set para um ArrayList
}
