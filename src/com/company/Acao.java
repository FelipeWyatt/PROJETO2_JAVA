package com.company;

import java.text.DecimalFormat;
import java.util.GregorianCalendar;

public class Acao extends Investimento {

    private Acoes acao;
    private int quantidade;
    private float precoCompra;

    public Acao(Acoes acao, int quantidade, float precoCompra, GregorianCalendar dataCompra) {
        // Quando compra-se uma ação, é pago nela o valor que é definido pelo usuário
        // O montante sempre será o preço da ação * quantidade de cotas dessa ação
        super(quantidade * acao.precoTempoReal(), dataCompra);
        this.acao = acao;
        this.quantidade = quantidade;
        this.precoCompra = precoCompra;
    }

    public void rendeInvestimento (int diasPassados) {
        // Atualiza o montante
        // Para acoes, os dias passados são irrelevantes
        if(acao.precoTempoReal() > 0) setMontante(quantidade * acao.precoTempoReal());
    }

    public String toString() {
       rendeInvestimento(0);

        DecimalFormat d1 = new DecimalFormat("#. 00");
        String out = "Montante: R$" + d1.format(getMontante()) + "\n";
        out = out + "Data da Compra: " + Data.formataDMA(getDataCompra()) + "\n";
        out = out + "Ação: " + acao.getTicker() + " - " + acao.getEmpresa() + "\n";
        out = out + "Quantidade: " + getQuantidade() + "\n";
        out = out + "Preço de Compra: " + getPrecoCompra() + "\n";
        out = out + "Preço Atual: " + acao.precoTempoReal()+ "%" + "\n";
        return out;
    }

    public Acoes getAcao(){ return acao; }
    public void setAcao(Acoes novoAcao){ this.acao = novoAcao; }

    public int getQuantidade(){ return quantidade; }
    public void setQuantidade(int novoQuantidade){ this.quantidade = novoQuantidade; }

    public float getPrecoCompra(){ return precoCompra; }
    public void setPrecoCompra(float novoPrecoCompra){ this.precoCompra = novoPrecoCompra; }

}
