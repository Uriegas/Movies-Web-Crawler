package com.trumam.net;

import com.trumam.*;
import org.htmlparser.Parser;
import java.io.*;
import java.net.*;
import java.util.*;

public class LinkFinder implements Runnable {

    private URL url;
    private LinkHandler<Movie> linkHandler;
    private int depth;

    /**
     * Se utiliza para estadísticas
     */
    // private static final long t0 = System.nanoTime();

    public LinkFinder(URL url, LinkHandler<Movie> handler){
        this.url = url;
        this.linkHandler = handler;
        this.depth = WebCrawler.depth;
    }

    @Override
    public void run() {
        getSimpleLinks(url);
    }
    /**
     * Obtenemos los links dentro de una página
     * @param url
     */
    private void getSimpleLinks(URL url) {
        // si no se ha visitado la url, se visita
        if (!linkHandler.visited(url)) {
            try {
                Parser parser = new Parser(url.openConnection()); //Parse URL
                parser.toString();
                URLConnection connection = url.openConnection();
                connection.setRequestProperty("Accept", "text/html");
                connection.setRequestProperty("Connection", "close");
                connection.setRequestProperty("Accept-Language", "en-US");
                connection.setRequestProperty("User-Agent", "Mozilla/5.0");

                // --> Get the html page into a string
                String html = "";
                try {//Code to read the response
                    String line = "";
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while( (line = reader.readLine()) != null) {
                        html += line;
                    }
                } catch (Exception e) {
                    System.out.println("Error al obtener el html de la url: " + url);
                    e.printStackTrace();
                }
                // <-- Get the html page into a string

                // --> Parse the html page (get the movies links and the movie object)
                List<URL> urls = ParseHTML.getMovieLinks(html);
                Movie movie = ParseHTML.getMovie(html);

                // NodeList list = parser.extractAllNodesThatMatch(new NodeClassFilter(LinkTag.class));//Change this to match only links to movies
                // List<String> urls = new ArrayList<>(); //List of links
                //System.out.println("Analizando: " + url);
                // for (int i=0; i < list.size(); i++) { // Iterate over each link to get the movie links
                //     LinkTag extracted = (LinkTag) list.elementAt(i);
                //     if (!extracted.getLink().isBlank() && !linkHandler.visited(extracted.getLink()))
                //         urls.add(extracted.getLink());
                // }
                
                // // --> Check if the movie is already visited
                // for(URL u : urls)
                //     if(!linkHandler.visited(u))
                //         linkHandler.addMovie(u, movie);

                linkHandler.addVisited(url,movie); // Agregar a visitados para no volver a visitarlo

                // if (linkHandler.size() == 50)
                //     System.out.println("Tiempo para visitar 50 links = " + (System.nanoTime() - t0) / 1000000000.0 + " segundos");
                if(depth > 0){
                    for (URL l: urls)
                        linkHandler.queueLink(l);
                    depth--;
                }
                
            }
            catch(Exception e) {
                // Ignoramos todos las excepciones
                // e.printStackTrace();
            }
        }
    }
}