package com.company;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class WebScraping {
    // Classe simples para pegar preços das Ações
    // Pega o código fonte do link que é a pesquisa no google do preço da ação e retira o preço desse código
    public static void pull() throws IOException{
        URL url = new URL("https://www.google.com/search?q=tesla+stock&oq=tesla+stock&aqs=chrome..69i57j0i131i433j0l3j0i395l5.5058j1j7&sourceid=chrome&ie=UTF-8");
        URLConnection urlCon = url.openConnection();
        urlCon.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
        InputStreamReader inStream = new InputStreamReader(urlCon.getInputStream());
        BufferedReader buff = new BufferedReader(inStream);
        String price = "Not found";
        String line = buff.readLine();
        while(line != null){
            System.out.println(line);
            line = buff.readLine();
        }
    }
}