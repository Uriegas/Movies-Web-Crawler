package com.trumam;

import java.io.*;
import java.net.*;

import org.htmlparser.*;
import org.htmlparser.filters.*;
import org.htmlparser.tags.*;
import org.htmlparser.util.NodeList;
import org.json.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import java.util.*;

/**
 * Class ParseHTML with static methods to parse HTML and JSON files. <p>
 * Main method is: {@link ParseHTML#getMovies(String)} and {@link ParseHTML#getMoviesLinks(String)}
 */
public class ParseHTML {
	//Nodefilter for script of type=type="application/ld+json"
	private static final NodeFilter filter = new NodeFilter() {
		public boolean accept (Node node) {
			try{
				if (((Tag)node).getAttribute("type").equals("application/ld+json"))
					return true;
				else
					return false;
			}catch(Exception e){
			return false;
			}
		}
	};
	//Nodefilter for links to other movies inside this site
	private static final NodeFilter filter2 = new NodeFilter() {
		public boolean accept (Node node) {
			try{
				if (((Tag)node).getAttribute("href").contains("/title/") && !((Tag)node).getAttribute("href").contains("pro."))
					return true;
				else
					return false;
			}catch(Exception e){
			return false;
			}
		}
	};
	/**
	 * Get the movies from a HTML file. <p>
	 * @param html the HTML source code
	 * @return a list of links to other movies
	 */
	public static List<URL> getMovieLinks(String html) throws Exception {
		List<URL> movies = new ArrayList<URL>();
		Parser parser = new Parser(html);
		NodeList list = parser.extractAllNodesThatMatch(filter2);
		for(int i = 0; i < list.size(); i++){
			String url = ((Tag)list.elementAt(i)).getAttribute("href");
			if(!(url.contains("http:") || url.contains("https:")))
				url = "https://www.imdb.com" + url; //TODO: Change hardcoded web page
			try{
				movies.add(new URL(url));
			}catch(Exception e){ continue; }
		}
		return movies;
	}
	/**
	 * Get a {@link Movie} from a valid HTML page <p>
	 * The following domains are valid:
	 * 	<ul>
	 * 		<li>http://www.imdb.com/</li>
	 * 		<li>http://www.rottentomatoes.com/</li>
	 *  </ul>
	 * @param html
	 * @return {@link Movie}
	 * @throws Exception
	 */
	public static Movie getMovie(String html) throws Exception {
		return parseJSON(HTMLtoJSON(html));
	}
	/**
	 * Given a HTML string, find and parse the JSON data
	 * @param htmlString
	 * @return parsed JSON file
	 */
	public static JSONObject HTMLtoJSON(String html) throws Exception {
		JSONObject json = new JSONObject();
		// URL urilink = new URL(html);
		// Parser parser = new Parser(urilink.openConnection()); //Parse URL
		Parser parser = new Parser(html); //Parse String
		//Get script of type="application/ld+json" from the html
		NodeList nodeList = parser.extractAllNodesThatMatch(ParseHTML.filter);
		// System.out.println("Number of scripts: " + nodeList.size());
		//Convert first node to JsonObject
		if (nodeList.size() > 0) {
			ScriptTag script = (ScriptTag) nodeList.elementAt(0);
			// System.out.println("Script: " + script.getScriptCode());
			json = (JSONObject) JSONValue.parse(script.getScriptCode());
		}
		return json;
	}
	/**
	 * Given a JSON file, instantiate a movie data object
	 */
	public static Movie parseJSON(JSONObject json) {
		String title, tags = "", synopsis;
		title = (json.get("name") != null) ? (String)json.get("name") : "";

		if(json.get("genre") != null) {//Genres is a list
			Iterator it = ((JSONArray)json.get("genre")).iterator();
			while(it.hasNext()) {
				tags += (String)it.next().toString() + " - ";
			}
			//Rempove last 3 characters from tags
			tags = tags.substring(0, tags.length() - 3);
		}

		synopsis = (json.get("description") != null) ? (String)json.get("description") : "";
		return new Movie(title, tags, synopsis);
	}
	public static JSONObject readJSON(FileReader reader) throws Exception {
		//Parse json file to a JSONObject
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(reader);
		return json;
	}
	// public static Movie getMovie(File file) {
	// 	return parseHTML(file);
	// }
}
