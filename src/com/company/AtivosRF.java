package com.company;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public enum AtivosRF {
	TESOURO_SELIC ("Tesouro Selic", 0.0017f, new GregorianCalendar(2021,11,1), 0.01f),
	CDB_BANCO_ITAU ("CDB Itau", 0.0016f, new GregorianCalendar(2022,10,1), 0.05f),
	CDB_BANCO_INTER ("CDB Inter", 0.0014f, new GregorianCalendar(2024, 5, 1), 0.05f),
	LCI_CAIXA ("LCI Caixa", 0.0011f, new GregorianCalendar(2022, 11, 1), 0.01f);
	
	private String nome;
	private float rentabilidade; // rendimento diario
	private GregorianCalendar vencimento;
	private float penalidade; // caso retirar o dinheiro antes do vencimento, tem uma penalidade
	
	//CONSTRUTOR
	AtivosRF(String nome, float rentabilidade, GregorianCalendar vencimento, float penalidade) {
		this.nome = nome;
		this.rentabilidade = rentabilidade;
		this.vencimento = vencimento;
		this.penalidade = penalidade;
	}

	public static ArrayList<String> RFDisponiveisString(){
		// Usado em TelaInvestimentos
		ArrayList<String> saida = new ArrayList<String>();

		for (AtivosRF i : AtivosRF.values()){
			float rent = (float) Math.round(i.getRentabilidade()*10000)/100; // Arredonda pra duas casas decimais e transforma em por cento
			saida.add(i.getNome() + " (rent: " + rent+ "% dia, venc: " + Data.formataDMA(i.getVencimento()) + ")");
		}

		return saida;
	}

	// GETS
	public float getRentabilidade() {
		return rentabilidade;
	}
	public GregorianCalendar getVencimento() {
		return vencimento;
	}
	public float getPenalidade() {
		return penalidade;
	}
	public String getNome() {
		return nome;
	}
	

}
