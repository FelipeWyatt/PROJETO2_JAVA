package com.company;

import java.util.GregorianCalendar;

public class Acao extends Investimento {

    public Acao(float montante, GregorianCalendar dataCompra) {
        super(montante, dataCompra);
    }
    
    public void rende(int diasPassados){
           // 02
           setMontante((float) (Math.pow(1.01, diasPassados)*getMontante()));
    }

}
