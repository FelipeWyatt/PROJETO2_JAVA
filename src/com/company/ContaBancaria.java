//package com.company;

import java.text.DecimalFormat;

public abstract class ContaBancaria {
    // ATRIBUTOS
    private static int numContas = 0;
    private final int id; // Numero/login da conta
    private float saldo; //saldo disponivel para saques e investimentos. Diferente do dinheiro investido (para contas do tipo investidor)
    private final Cliente dono;

    // METODO CONSTRUTOR
    public ContaBancaria (float saldo, Cliente dono) {
        this.id = numContas + 1; // Segue a logica do id +1 em Cliente
        this.saldo = saldo;
        this.dono = dono;

        numContas++;
    }


    // METODOS
    public boolean retirar (float valor) { //faz saques da conta e retorna se foi possivel ou nao com base no saldo disponivel
    	if (dono.getStatus()) {
	        if (valor <= getSaldo()) {
	            this.saldo = this.saldo - valor;
	            // tambem deve-se atualizar o montanteTotal do cliente
	            getDono().setDinheiroTotal(getDono().getDinheiroTotal() - valor);

	            return true;
	        } else {
	            return false;
	        }
    	}
    	else {
    		return false;
    	}
    		
    }

    public boolean depositar (float valor) { //adicina determinada quantia ao saldo da conta
    	if (dono.getStatus()) {
	        this.saldo = this.saldo + valor;
	        // tambem deve-se atualizar o montanteTotal do cliente
	        getDono().setDinheiroTotal(getDono().getDinheiroTotal() + valor);
	        return true;
    	}
    	else {
    		return false;
    	}
    }

    public abstract void rendeConta (int diasPassados);

    @Override
    public String toString () {
    	DecimalFormat d1 = new DecimalFormat("#. 00"); // Formata do jeito certo
        String out = "Id/login da conta: " + getId() + "\n";
        out += "Dono da conta: " + getDono().getNome() + "\n";
        out += "Saldo disponivel: R$" + d1.format(getSaldo()) + "\n";
        return out;
    }

    // GETTERS E SETTERS
    public static int getNumContas () { return numContas; }
    public static void setNumContas (int numC) { numContas = numC; }

    public int getId () { return id; }

    public float getSaldo () { return saldo; }
    public void setSaldo (float saldo) { this.saldo = saldo; }

    public Cliente getDono () { return dono; }
}
