package com.trumam.net;

import com.trumam.LinkHandler;
import com.trumam.WebCrawler;

import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import java.net.URL;
import java.util.*;

public class LinkFinder implements Runnable {

    private String url;
    private LinkHandler linkHandler;
    private int depth;

    /**
     * Se utiliza para estadísticas
     */
    private static final long t0 = System.nanoTime();

    public LinkFinder(String url, LinkHandler handler){
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
    private void getSimpleLinks(String url) {
        // si no se ha visitado la url, se visita
        if (!linkHandler.visited(url)) {
            try {
                URL urilink = new URL(url);
                Parser parser = new Parser(urilink.openConnection()); //Parse URL
                NodeList list = parser.extractAllNodesThatMatch(new NodeClassFilter(LinkTag.class));//Change this to match only links to movies
                List<String> urls = new ArrayList<>(); //List of links

                //System.out.println("Analizando: " + url);
                for (int i=0; i < list.size(); i++) { // Iterate over each link
                    LinkTag extracted = (LinkTag) list.elementAt(i);
                    if (!extracted.getLink().isBlank() && !linkHandler.visited(extracted.getLink()))
                        urls.add(extracted.getLink());
                }

                linkHandler.addVisited(url); // Agregar a visitados para no volver a visitarlo

                // if (linkHandler.size() == 50)
                //     System.out.println("Tiempo para visitar 50 links = " + (System.nanoTime() - t0) / 1000000000.0 + " segundos");

                for (String l: urls)
                    linkHandler.queueLink(l);
            }
            catch(Exception e) {
                // Ignoramos todos las excepciones
                // e.printStackTrace();
            }
        }
    }
}