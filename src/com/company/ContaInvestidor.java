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

        return 0;
        /*
        // Código antigo, polimosfismo pensado anteriormente nesse método não é viavel
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

    //GETTERS E SETTERS
    public ArrayList<Investimento> getInvestimentos () { return investimentos; }
    // Nao faz sentido ter um set para um ArrayList

}
