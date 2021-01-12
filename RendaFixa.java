package com.company;

import java.text.DecimalFormat;
import java.util.GregorianCalendar;

class RendaFixa extends Investimento { // Um tipo de investimento, entao e uma classe-filha de investimento
	// ATRIBUTOS
	private AtivosRF ativo; //Ativo que sera comprado


	// CONSTRUTOR
	public RendaFixa(float montante, GregorianCalendar dataCompra, AtivosRF ativo) {
		super(montante, dataCompra);
		this.ativo = ativo;
	}

	// METODOS
	public String toString() {
		DecimalFormat d1 = new DecimalFormat("#. 00");
		String out = "Montante: R$" + d1.format(getMontante()) + "\n";
		out = out + "Data da Compra: " + Data.formataDMA(getDataCompra()) + "\n";
		out = out + "Ativo: " + ativo.getNome() + "\n";
		out = out + "*rentabilidade: " + 100*ativo.getRentabilidade()+ "%" + "\n";
		out = out + "*vencimento: " + Data.formataDMA(ativo.getVencimento()) + "\n";
		out = out + "*penalidade: " + 100*ativo.getPenalidade()+ "%" + "\n";
		return out;
	}

	// GETTERS E SETTERS
	public AtivosRF getAtivo() {
		return ativo;
	}
	public void setAtivo(AtivosRF ativo) {
		this.ativo = ativo;
	}
}
