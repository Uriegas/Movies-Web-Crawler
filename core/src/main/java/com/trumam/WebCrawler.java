package com.trumam;

import com.trumam.net.LinkFinder;

import java.net.URL;
import java.util.*;
import java.util.concurrent.*;

public class WebCrawler implements LinkHandler<Movie> {

    private final Collection<URL> visitedLinks = Collections.synchronizedSet(new HashSet<URL>());
    private final Collection<Movie> visitedMovies = Collections.synchronizedSet(new HashSet<Movie>());
    // private final Collection<String> visitedLinks = Collections.synchronizedList(new ArrayList<String>());
    private URL url;
    private ExecutorService executorService;
    public static int depth = 0;

    public WebCrawler(URL startingURL, int maxThreads, int depth) {
        this.url = startingURL;
        executorService = Executors.newFixedThreadPool(maxThreads);
        WebCrawler.depth = depth;
    }

    @Override
    public void queueLink(URL link) throws Exception {
        startNewThread(link);
    }

    private ArrayList<Movie> startNewThread(URL link) throws Exception {
        executorService.execute(new LinkFinder(link, this));
        return null;
    }

    public void startCrawling() throws Exception {
        //System.out.println("visitando: " + this.url);
        startNewThread(this.url);
    }

    @Override
    public int size() {//Visited links and movies should be of the same size
        return visitedLinks.size();
    }

    @Override
    public void addVisited(URL s, Movie m) {
        visitedLinks.add(s);
        visitedMovies.add(m);
    }

    @Override
    public boolean visited(URL link) {
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
    public ArrayList<Movie> crawl(URL url, int maxThreads, int depth) throws Exception {
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
        new WebCrawler(new URL("https://www.rottentomatoes.com/top/bestofrt/"), 64, 2).startCrawling(); //Top peliculas en RottenTomatoes
    }
}