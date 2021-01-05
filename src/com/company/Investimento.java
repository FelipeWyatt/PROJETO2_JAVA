package com.company;

import java.text.DecimalFormat;
import java.util.GregorianCalendar;

public class Investimento { // Classe mae para qualquer tipo de investimento, no futuro serao adicionados outros tipos alem de RendaFixa
	// ATRIBUTOS
	private float montante; // quanto foi investido
	private GregorianCalendar dataCompra;
	
	// METODO CONSTRUTOR
	public Investimento(float montante, GregorianCalendar dataCompra) {
		this.montante = montante;
		this.dataCompra = dataCompra;
	}

	// METODOS
	public String toString() {
		DecimalFormat d1 = new DecimalFormat("#. 00"); //Formata para "00,00"
		String out = "Montante: R$" + d1.format(getMontante())+"\n";
		out = out+"Data da Compra: "+ Data.formataDMA(dataCompra)+"\n";
		return out;
	}

	//GET E SETTERS
	public float getMontante() {
		return montante;
	}
	public void setMontante(float montante) {
		this.montante = montante;
	}

	public GregorianCalendar getDataCompra() {
		return dataCompra;
	}
	public void setDataCompra(GregorianCalendar dataCompra) {
		this.dataCompra = dataCompra;
	}

}
