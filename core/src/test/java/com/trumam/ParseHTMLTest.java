package com.trumam;

import java.io.*;
import java.util.*;
import java.net.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
/**
 * Test for the Movie class.
 */
@RunWith(Parameterized.class)
public class ParseHTMLTest {
	String file;
	Movie expected;
    /**
     * Constructor with expected value injection
     * @param path path to load file
     */
    public ParseHTMLTest(String htmlFile, Movie expected){
        this.file = htmlFile;
		this.expected = expected;
    }
    /**
	 * Collection of paths to files to read
     * @return Collection of tables
     */
    @Parameterized.Parameters(name = "Expected is {1}")
    public static Collection<Object[]> getTestData(){
		return Arrays.asList( new Object[][] {
			//List some movies
			{ System.getProperty("user.dir") + "/src/main/resources/Filly_Brown",
			  new Movie("Filly Brown", "Drama - Music", "A promising hip-hop rhymer from Los Angeles finds herself in a gray area when a record producer offers her a compromising shot at stardom.")},
			{ System.getProperty("user.dir") + "/src/main/resources/La_dinastia_de_Los_Perez",
			  new Movie("La dinastía de Los Pérez", "Action", "")},//This movie do not have a description
		});
	}
	/**
	 * Test getting ONLY movie links from the current html page
	 */
	@Test
	public void testGetMovieLinks(){
		try{
			String html = "";
			BufferedReader br = new BufferedReader(new FileReader(file + ".html"));
			String line;
			while ((line = br.readLine()) != null)
				html += line;
			br.close();
			List<URL> movieLinks = ParseHTML.getMovieLinks(html);
			for(URL url : movieLinks){
				System.out.println(url.toString());
			}
		}catch(Exception e){
			Assert.fail("An error ocurred");
		}
	}
	/**
	 * Integration Test:<p>
	 * Test that that we get the expected movies when parsing the page (htmlt file)
	 */
	@Test
	public void testMovie() throws Exception{
		//Get the 
		try{
			JSONObject json = ParseHTML.readJSON( new FileReader(file + ".json") );
			Assert.assertEquals(expected.toString(), ParseHTML.parseJSON(json).toString());
		}catch(FileNotFoundException e){
			Assert.fail("File not found: " + file + ".json");
		}catch(Exception e1){
			Assert.fail("JSON parsing is incorrect");
		}
	}
	/**
	 * Test that the html returns a json object
	 */
	@Test
	public void testHtmlToJson() throws Exception {
		//Read the file to a string
		String html = "";
		String jsonFile = "";
		// --> Parse html to json object (actual)
		BufferedReader br = new BufferedReader(new FileReader(file + ".html"));
		String line;
		while ((line = br.readLine()) != null)
			html += line;
		br.close();
		JSONObject json = ParseHTML.HTMLtoJSON(html);
		// <-- Parse html to json object (actual)

		// --> Parse the json file to json object (expected)
		br = new BufferedReader(new FileReader(file + ".json"));
		line = "";
		while ((line = br.readLine()) != null)
			jsonFile += line;
		br.close();
		JSONObject expectedJson = (JSONObject)JSONValue.parse(jsonFile);
		// <-- Parse the json file to json object (expected)

		//assert that the json objects are equal
		Assert.assertEquals(expectedJson.toString(), json.toString());
	}
	/**
	 * Test json convertion to a movie
	 */
	@Test
	public void testJsonToMovies(){
		try{
			JSONObject json = ParseHTML.readJSON( new FileReader(file + ".json") );
			Assert.assertEquals(expected.toString(), ParseHTML.parseJSON(json).toString());
			//System.out.println("Expected is: " + expected.toString() + "\nActual is: " + ParseHTML.parseJSON(json).toString());
		}catch(FileNotFoundException e){
			Assert.fail("File not found: " + file + ".json");
		}catch(Exception e1){
			Assert.fail("JSON parsing is incorrect");
		}
	}
	/**
	 * Test that JSON is correctly parsed
	 */
	@Test
	public void testJsonParsing(){
		try{
			JSONObject json = ParseHTML.readJSON( new FileReader(file + ".json") );
			System.out.println(json.toString());
			System.out.println("JSON parsing is correct");
		}catch(FileNotFoundException e){
			Assert.fail("File not found: " + file + ".json");
		}catch(Exception e1){
			Assert.fail("JSON parsing is incorrect");
		}
	}
}