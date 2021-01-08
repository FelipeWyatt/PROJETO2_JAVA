package com.company;

import java.io.IOException;
import java.nio.channels.AcceptPendingException;

public enum Acoes {
    TESLA("Tesla Inc.", "TSLA34", "https://www.google.com/search?q=tesla+stock&oq=tesla+stock&aqs=chrome..69i57j0i131i433j0l3j0i395l5.5058j1j7&sourceid=chrome&ie=UTF-8"),
    APPLE("Apple Inc.", "AAPL34", "https://www.google.com/search?safe=active&sxsrf=ALeKk00auN1o5F4TCr7_0WuzugnXMkKhAw%3A1610129366149&ei=1p_4X9zICLW65OUPtOun8Ac&q=apple+stock&oq=apple+stock&gs_lcp=CgZwc3ktYWIQAzIHCAAQsQMQQzIECAAQQzIFCAAQsQMyCAgAELEDEIMBMgUIABDLATICCAAyBAgAEEMyAggAMgIIADICCAA6BAgAEEc6BggAEAcQHlDNy3BY59NwYIvecGgAcAN4AIAByAGIAcEHkgEFMC40LjGYAQCgAQGqAQdnd3Mtd2l6yAEIwAEB&sclient=psy-ab&ved=0ahUKEwjci6Wn94zuAhU1HbkGHbT1CX4Q4dUDCA0&uact=5");

    private String empresa;
    private String ticker;
    private String url;

    Acoes(String empresa, String ticker, String url){
        this.empresa = empresa;
        this.ticker = ticker;
        this.url = url;
    }

    public String precoTempoReal() {
        // Aciona o WebScraping para pegar o preço da Ação em tempo real
        // Trata execeção do WebScraping
        String preco = "-";
        try {
            preco = WebScraping.pull(this.url);
        } catch (IOException erroConexao) {
            System.out.println("Erro de conexão: " + erroConexao);
            //erro.printStackTrace();
        } catch (Exception erroGeral){
            System.out.println("Erro: " + erroGeral);
        }
        return preco;
    }

    public String getEmpresa() { return empresa; }

    public String getTicker() { return ticker; }

    public String getUrl() { return url; }
}
