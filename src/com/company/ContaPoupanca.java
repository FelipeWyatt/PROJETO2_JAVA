package com.company;

public class ContaPoupanca extends ContaBancaria { // Rende diariamente a uma taxa fixa
    private static final float rendimentoDiario = 0.00007f; // 0.007%

    //METODO CONSTRUTOR
    public ContaPoupanca (float saldo, Cliente dono) {
        super(saldo, dono);
    }

    public void rendeConta (int diasPassados) {
        // Saldo apos n dias = saldo*(1 + rendimentoDiario)^n
        float novoSaldo = (float) (getSaldo() * Math.pow((1 + rendimentoDiario), diasPassados));
        novoSaldo = (float) Math.round(novoSaldo*100)/100; // Arredonda pra duas casas decimais
        setSaldo(novoSaldo);
        // atualiza o dinheiroTotal do Cliente
        getDono().setDinheiroTotal(novoSaldo);
    }

    @Override
    public String toString () {
        String out = "CONTA POUPANCA \n";
        out += super.toString();
        out += "Rendimento Diario da poupanca: " + 100 * getRendimentoDiario() + "%" + "\n";
        return out;
    }

    //GETTERS E SETTERS
    public static float getRendimentoDiario () { return rendimentoDiario; }
}
