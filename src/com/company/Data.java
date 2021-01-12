package com.company;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Data {
    // Metodos relacionados a manipulacao de datas usados em outras classes

    public static long diasEntre(GregorianCalendar dataAntiga, GregorianCalendar dataProxima){
        // Para evitar que as horas, minutos e segundos facam diferenca no numero de dias, deve-se zerar os seus valores
        dataAntiga.set(Calendar.HOUR, 0);
        dataAntiga.set(Calendar.MINUTE, 0);
        dataAntiga.set(Calendar.SECOND, 0);
        dataAntiga.set(Calendar.MILLISECOND, 0);

        dataProxima.set(Calendar.HOUR, 0);
        dataProxima.set(Calendar.MINUTE, 0);
        dataProxima.set(Calendar.SECOND, 0);
        dataProxima.set(Calendar.MILLISECOND, 0);
        // Diferenca entre as datas em segundos
        long dif_segundos = (dataProxima.getTimeInMillis() - dataAntiga.getTimeInMillis()) / 1000;
        long dif_dias = dif_segundos/(3600*24);

        return dif_dias;
    }

    public static String formataDMA(GregorianCalendar data){
        // Converte o objeto GregorianCalendar em String formatada em dd/mm/aaaa.

        int dia = data.get(Calendar.DAY_OF_MONTH);
        int mes = data.get(Calendar.MONTH);
        int ano = data.get(Calendar.YEAR);

        return (dia + "/" + mes  + "/" + ano);
    }

    public static String formataDMA_HMS(GregorianCalendar data){
        // Converte o objeto GregorianCalendar em String formatada em dd/mm/aaaa hh:mm:ss.

        int seg = data.get(Calendar.SECOND);
        int minuto = data.get(Calendar.MINUTE);
        int hora = data.get(Calendar.HOUR_OF_DAY);

        int dia = data.get(Calendar.DAY_OF_MONTH);
        int mes = data.get(Calendar.MONTH);
        int ano = data.get(Calendar.YEAR);

        return (dia + "/" + mes  + "/" + ano + " " + hora + ":" + minuto + ":" + seg);
    }
}
