package com.trumam;

import com.trumam.net.LinkFinder;

import java.util.*;
import java.util.concurrent.*;

public class WebCrawler implements LinkHandler {

    private final Collection<String> visitedLinks = Collections.synchronizedSet(new HashSet<String>());
    // private final Collection<String> visitedLinks = Collections.synchronizedList(new ArrayList<String>());
    private String url;
    private ExecutorService executorService;
    public static int depth = 0;

    public WebCrawler(String startingURL, int maxThreads, int depth) {
        this.url = startingURL;
        executorService = Executors.newFixedThreadPool(maxThreads);
        WebCrawler.depth = depth;
    }

    @Override
    public void queueLink(String link) throws Exception {
        startNewThread(link);
    }

    private ArrayList<Movie> startNewThread(String link) throws Exception {
        executorService.execute(new LinkFinder(link, this));
        return null;
    }

    public void startCrawling() throws Exception {
        //System.out.println("visitando: " + this.url);
        startNewThread(this.url);
    }

    @Override
    public int size() {
        return visitedLinks.size();
    }

    @Override
    public void addVisited(String s) {
        visitedLinks.add(s);
    }

    @Override
    public boolean visited(String link) {
        return visitedLinks.contains(link);
    }
    /**
     * Crawl only imdb and rottentomatoes links
     * @param url, starting url
     * @param maxThreads, max threads
     * @param depth, how many links go down when crawling
     * @return list of movies
     * @throws Exception
     */
    public ArrayList<Movie> crawl(String url, int maxThreads, int depth) throws Exception {
        if(depth < 0) // if depth is negative throw exception
            throw new IllegalArgumentException("Depth cannot be negative");
        WebCrawler.depth = depth;
        this.url = url;
        ArrayList<Movie> movies = new ArrayList<>();
        System.out.println("crawling: " + url);
        executorService = Executors.newFixedThreadPool(maxThreads);
        movies = startNewThread(url);//TODO add depth
        
        return movies;
    }

	/**
	 * Exception to handle the case when the user enters an invalid URL
	 */
	public class URLException extends Exception{
		public URLException(String message){
			super(message);
		}
	} 

    public static void main(String[] args) throws Exception {
        new WebCrawler("https://www.rottentomatoes.com/top/bestofrt/", 64, 2).startCrawling(); //Top peliculas en RottenTomatoes
    }
}