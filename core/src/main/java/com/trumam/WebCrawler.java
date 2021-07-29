package com.trumam;

import com.trumam.net.LinkFinder;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.*;

public class WebCrawler implements LinkHandler {

    private final Collection<String> visitedLinks = Collections.synchronizedSet(new HashSet<String>());
    // private final Collection<String> visitedLinks = Collections.synchronizedList(new ArrayList<String>());
    private String url;
    private ExecutorService executorService;

    public WebCrawler(String startingURL, int maxThreads) {
        this.url = startingURL;
        executorService = Executors.newFixedThreadPool(maxThreads);
    }

    @Override
    public void queueLink(String link) throws Exception {
        startNewThread(link);
    }

    private void startNewThread(String link) throws Exception {
        executorService.execute(new LinkFinder(link, this));
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
    public ArrayList<Movie> crawl(String url) { System.out.println("crawling: " + url); return new ArrayList<Movie>(); }

	/**
	 * Exception to handle the case when the user enters an invalid URL
	 */
	public class URLException extends Exception{
		public URLException(String message){
			super(message);
		}
	} 
}