package com.example.Semicolon.Back;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.net.UnknownHostException;

public class webScraper {
    private String URL = "https://www.rottentomatoes.com/m/harry_potter_and_the_deathly_hallows_part_2";
    private String name = "TITANIC";
    private void scrape(){
        WebClient client = new WebClient();
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setUseInsecureSSL(true);
        URL = "https://www.rottentomatoes.com/m/" + name.toLowerCase().replaceAll(" ", "_").replaceAll(":", "").replaceAll("-", "_").replaceAll("!", "");
        try {
            HtmlPage page2 = client.getPage(URL);
            String rating = page2.asXml();
            if(rating.contains("audiencescore=\"") || rating.contains("tomatometerscore=\"")) {
                System.out.println("Audience: " + rating.substring(rating.indexOf("audiencescore=\"") + 15, rating.indexOf("audiencescore=\"") + 17).replaceAll("\"" , ""));
                System.out.println("Tomatometer: " + rating.substring(rating.lastIndexOf("tomatometerscore=\"") + 18, rating.lastIndexOf("tomatometerscore=\"") + 20).replaceAll("\"" , ""));
            }
        }catch (FailingHttpStatusCodeException f){
            System.out.println("no page found");
        } catch (UnknownHostException o){
            System.out.println("no Internet connection");
        } catch (IOException e){
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        webScraper ws = new webScraper();
        ws.scrape();
    }
}
