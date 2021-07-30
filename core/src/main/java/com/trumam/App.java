package com.trumam;

import java.net.URL;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
            new WebCrawler(new URL("https://imdb.com/"), 64, 2).startCrawling();
            //new WebCrawler7("https://es.wikipedia.org/wiki/Roma", 64).startCrawling();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
