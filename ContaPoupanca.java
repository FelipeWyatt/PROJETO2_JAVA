package com.company;

public class ContaPoupanca extends ContaBancaria { // Rende diariamente a uma taxa fixa
    private static final float rendimentoDiario = 0.00007f; // 0.007%

    //METODO CONSTRUTOR
    public ContaPoupanca (float saldo, Cliente dono) {
        super(saldo, dono);
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
