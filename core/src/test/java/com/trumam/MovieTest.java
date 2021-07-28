package com.trumam;

import java.util.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
/**
 * Test for the Movie class.
 */
@RunWith(Parameterized.class)
public class MovieTest {
	String expected;
	Movie actual;
    /**
     * Constructor with expected value injection
     * @param path path to load file
     */
    public MovieTest(Movie actual, String expected){
        this.actual = actual;
		this.expected = expected;
    }
    /**
	 * Collection of paths to files to read
     * @return Collection of tables
     */
    @Parameterized.Parameters(name = "Actual is {0}")
    public static Collection<Object[]> getTestData(){
		return Arrays.asList( new Object[][] {
			//List some movies
			{ new Movie("The Matrix", "Sci-Fi", "http://www.imdb.com/title/tt0133093/"),
				"The Matrix,Sci-Fi,http://www.imdb.com/title/tt0133093/"},
			{ new Movie("TrumanShow", "Comedy", "http://www.imdb.com/title/tt0120338/"),
				"TrumanShow,Comedy,http://www.imdb.com/title/tt0120338/"},
			{ new Movie("The Dark Knight", "Action", "http://www.imdb.com/title/tt0468569/"),
				"The Dark Knight,Action,http://www.imdb.com/title/tt0468569/"},
			{ new Movie("The Dark Knight Rises", "Action", "http://www.imdb.com/title/tt1345836/"),
				"The Dark Knight Rises,Action,http://www.imdb.com/title/tt1345836/"}
		});
	}
	/**
	 * Test if the movie is correctly loaded
	 * Assert that strings are equal
	 */
	@Test
	public void testMovie() throws Exception{
		//assert that strings are equal
		Assert.assertEquals(expected, actual.toString());
	}
}