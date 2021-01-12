package com.company;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Admin implements Serializable {
	// ATRIBUTOS
	private static final long serialVersionUID = 303L;
	private static List<Cliente> clientes = new ArrayList<>(); // Array para alocar todos os clientes criados
	private static final int id = 0; // O ID do Admin eh por padrao definido como 0
	private static String senha = "abcd";

	// METODOS
	// Admin pode criar um cliente
	public static void novoCliente(String nome, String senha, int idade, Sexo sexo, String profissao, float dinheiroTotal){
		new Cliente(nome, senha, idade, sexo, profissao, dinheiroTotal);
		// adicionaCliente e chamado no construtor do Cliente, portanto e adicionado no ArrayList

	}

	// Utilizado para adicionar os clintes dentro do Array, o metodo se encontra dentro do construtor de Cliente
	public static void adicionaCliente(Cliente novo){
		clientes.add(novo);
	}

	// Muda o status do cliente para false, assim o cliente esta impossibilitado de utilizar os metodos
	public static void desativaCliente(Cliente cliente){
		cliente.setStatus(false);
	}

	public static void ativaCliente(Cliente cliente){
		cliente.setStatus(true);
	}

	// Igual o de cima, mas agora pega com ID do cliente como parametro, ao inves do objeto cliente
	public static void desativaCliente(int id){
		for(Cliente i : clientes){
			if(i.getId() == id){
				i.setStatus(false);
			}
		}
	}

	// Admin consegue visualizar a informacao de qualquer cliente
	public static String verCliente(int id) {
		for (Cliente cliente : clientes) {
			if (cliente.getId() == id) {
				return cliente.toString();
			}
		}
		return "Cliente invalido";
	}
	public static Cliente verObjetoCliente(int id) {
		for (Cliente cliente : clientes) {
			if (cliente.getId() == id) {
				return cliente;
			}
		}
		return null;
	}
	// E pode alterar o saldo de qualquer cliente tambem
	public static void alterarSaldo(int id, float saldo) {
		for (Cliente cliente : clientes) {
			if (cliente.getId() == id) { // procura o cliente 
				cliente.getConta().setSaldo(saldo);
				if (cliente.getConta() instanceof ContaInvestidor){ // Tem diferenca entre mudar o dinheiro entre uma conta investidor e das demais
					cliente.setDinheiroTotal(cliente.getConta().getSaldo() + ((ContaInvestidor) cliente.getConta()).getMontanteTotal()); // O dinheiro total e o saldo + o montante total pra o cliente investidor
				} else {
					cliente.setDinheiroTotal(cliente.getConta().getSaldo()); // Para as outras contas o dinheiro total e = ao saldo
				}
			}
		}
	}


	// GET E SETTERS
	public static List<Cliente> getClientes() {
		return clientes;
	}
	// Nao faz sentido ter setClientes

	public static String getSenha() {
		return senha;
	}
	public static void setSenha(String novaSenha) {
		senha = novaSenha;
	}

	public static int getId() {
		return id;
	}

	
}
