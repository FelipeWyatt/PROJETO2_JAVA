//package com.company;

import java.text.DecimalFormat;
import java.util.ArrayList;
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
    	if (getDono().getStatus() && getSaldo() >= montante) {
	        RendaFixa novo_investimento = new RendaFixa(montante, new GregorianCalendar(), ativo);
	        investimentos.add(novo_investimento);
	        setSaldo(getSaldo() - montante);
            return true;
    	} else {
    		return false;
    	}
    }


    public float venderRF(RendaFixa investimento) {
        // Retira o dinheiro aplicado em um investimento de renda fixa, rendendo uma quantidade propoRcional ao tempo que o
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

        return 0;
        /*
        // Código antigo, polimosfismo pensado anteriormente nesse método náo é viavel
    	if (getDono().getStatus()) {
            // Metodo mais geral que podera ser adicionado outros tipos de investimentos
            if (investimento instanceof RendaFixa) { // Tem regras de resgate diferentes para cada tipo de investimento
                investimentos.remove(investimento);
                //investimento = (RendaFixa) investimento;
                GregorianCalendar dataCompra = investimento.getDataCompra();
                GregorianCalendar dataVencimento = ((RendaFixa) investimento).getAtivo().getVencimento();

                if (Data.diasEntre(dataCompra, dataVencimento) <= 0) { // verificar ordem das datas
                    // Entao esta sendo resgatado depois do prazo, sem penalidade
                    setSaldo(getSaldo() + investimento.getMontante());

                } else {
                    // Entao esta sendo resgatado antes do prazo, com penalidade
                    float montante = investimento.getMontante();
                    float valorPenalidade = montante * ((RendaFixa) investimento).getAtivo().getPenalidade();
                    setSaldo(getSaldo() + montante - valorPenalidade);

                }
            }

            // Para outro tipo de investimento adicionar else if (investimento instanceof tipoInvestimento)
        }*/
    }

    public void rendeConta (int diasPassados) {
        // deve render os montantes de todos os investimentos da conta
        for(Investimento i : investimentos) {
            if (i instanceof RendaFixa){ // RendaFixa rende de uma forma especifica
                AtivosRF ativo = ((RendaFixa) i).getAtivo();

                // Montante apos n dias = montante * (1 + rentabilidade)^n
                float novoMontante = (float) (i.getMontante() * Math.pow(1 + ativo.getRentabilidade(), diasPassados));
                novoMontante = (float) Math.round(novoMontante*100)/100; // Arredonda pra duas casas decimais
                i.setMontante(novoMontante);
            }
        }
        // atualiza o dinheiroTotal do Cliente, que sera o saldo da conta + o dinheiro investido
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

    // SEPARAR A LISTA DE INVESTIMENTOS ENTRE AÇÕES E RF
    // MOSTRAR A LISTA ORDENADA COM Collections.sort QUE USA INTERFACE Comparable E MÉTODO .compareTo()
    public String verInvestimentos() { // Chama os toString de cada investimento, mais geral
        DecimalFormat d1 = new DecimalFormat("#. 00"); //formata do jeito certo
        if (getDono().getStatus()) {
            String out = "Investimentos:\n";
            for (Investimento i : investimentos){
                if (i instanceof RendaFixa) {
                    out += "- " + ((RendaFixa) i).getAtivo().getNome() + ": R$" + d1.format(i.getMontante()) + "\n";
                }
                // Para outro tipo de investimento adicionar else if (investimento instanceof tipoInvestimento)
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
            RendaFixa aux = (RendaFixa) i;
            total += aux.getMontante();
        }
        return (float) Math.round(total*100)/100; // Arredonda para 2 casas decimais
    }

    public String getNomesAtivos() {
        String out = "";
        for (Investimento i : investimentos){
            if (i instanceof RendaFixa) {
                out += ((RendaFixa) i).getAtivo().getNome() + "   ";
            }
            // Para outro tipo de investimento adicionar else if (investimento instanceof tipoInvestimento)
        }
        return out;
    }

    //GETTERS E SETTERS
    public ArrayList<Investimento> getInvestimentos () { return investimentos; }
    // Nao faz sentido ter um set para um ArrayList

}
