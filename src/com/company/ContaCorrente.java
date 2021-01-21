package com.company;

public class ContaCorrente extends ContaBancaria { // Conta comum
    // Classe mãe ContaBancaria declara serialVersionUID

    //METODO CONSTRUTOR
    public ContaCorrente (float saldo, Cliente dono) {
        super(saldo, dono);
    }

    public void rendeConta(int diasPassados) {
        //para conta corrende não há qualquer rendimento de investimento ou montante
    }

    @Override
    public String toString () {
        String out = "CONTA CORRENTE \n";
        out += super.toString();
        return out;
    }
}
