package com.company;

import java.io.IOException;
import java.nio.channels.AcceptPendingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public enum Acoes {
    TESLA("Tesla Inc.", "TSLA34", "https://www.google.com/search?q=tesla+stock&oq=tesla+stock&aqs=chrome..69i57j0i131i433j0l3j0i395l5.5058j1j7&sourceid=chrome&ie=UTF-8"),
    APPLE("Apple Inc.", "AAPL34", "https://www.google.com/search?safe=active&sxsrf=ALeKk00auN1o5F4TCr7_0WuzugnXMkKhAw%3A1610129366149&ei=1p_4X9zICLW65OUPtOun8Ac&q=apple+stock&oq=apple+stock&gs_lcp=CgZwc3ktYWIQAzIHCAAQsQMQQzIECAAQQzIFCAAQsQMyCAgAELEDEIMBMgUIABDLATICCAAyBAgAEEMyAggAMgIIADICCAA6BAgAEEc6BggAEAcQHlDNy3BY59NwYIvecGgAcAN4AIAByAGIAcEHkgEFMC40LjGYAQCgAQGqAQdnd3Mtd2l6yAEIwAEB&sclient=psy-ab&ved=0ahUKEwjci6Wn94zuAhU1HbkGHbT1CX4Q4dUDCA0&uact=5"),
    AMAZON("Amazon", "AMZO34", "https://www.google.com/search?safe=active&sxsrf=ALeKk02ekfEewJUIkNWPWvOgn_Bny6oZFg%3A1610132362621&ei=iqv4X4OiJfrB5OUPxL6U0Ac&q=amazon+stock&oq=apple+stock&gs_lcp=CgZwc3ktYWIQARgBMgQIABBHMgQIABBHMgQIABBHMgQIABBHMgQIABBHMgQIABBHMgQIABBHMgQIABBHUABYAGC-z9ABaABwCHgAgAEAiAEAkgEAmAEAqgEHZ3dzLXdpesgBCMABAQ&sclient=psy-ab"),
    FACEBOOK("Facebook Inc.", "FBOK34", "https://www.google.com/search?safe=active&sxsrf=ALeKk02sZEHfGZ5jd23QAQ6l-dU1c9EDeg%3A1610135960971&ei=mLn4X5ngOujA5OUP7b2V2AM&q=facebook+stock&oq=facebook+stock&gs_lcp=CgZwc3ktYWIQAzIFCAAQsQMyCAgAELEDEIMBMgIIADICCAAyAggAMgIIADICCAAyAggAMgIIADICCAA6BAgAEEc6BggAEAcQHlDFzwFYid4BYNTgAWgAcAN4AIAB3AGIAYAMkgEFMC43LjGYAQCgAQGqAQdnd3Mtd2l6yAEIwAEB&sclient=psy-ab&ved=0ahUKEwiZjPnvj43uAhVoILkGHe1eBTsQ4dUDCA0&uact=5");

    private String empresa;
    private String ticker;
    private String url;

    Acoes(String empresa, String ticker, String url){
        this.empresa = empresa;
        this.ticker = ticker;
        this.url = url;
    }


    public float precoTempoReal() {
        // Aciona o WebScraping para pegar o preço da Ação em tempo real
        // Trata exceção do WebScraping
        try {
            String precoString = DadosWeb.obtemPrecoAcao(this.url);
            float preco = Float.parseFloat(precoString.replace(",", "."));
            return preco;
        } catch (IOException erroConexao) {
            System.out.println("Erro de conexão: " + erroConexao);
            //erro.printStackTrace();
        } catch (NumberFormatException erroParseFloat){
            System.out.println("Erro formato do float: " + erroParseFloat);
        } catch (Exception erroGeral){
            System.out.println("Erro: " + erroGeral);
        }
        return -1f;
    }

    public static ArrayList<String> acoesDisponiveisString(){
        // Usado em TelaInvestimentos
        DecimalFormat d1 = new DecimalFormat("#. 00"); //formata do jeito certo
        ArrayList<String> saida = new ArrayList<String>();

        for (Acoes i : Acoes.values()){
            saida.add(i.getEmpresa() + ": R$" + d1.format(i.precoTempoReal()));
        }

        return saida;
    }

    public String getEmpresa() { return empresa; }

    public String getTicker() { return ticker; }

    public String getUrl() { return url; }
}
