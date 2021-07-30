package com.trumam;
/**
 * Representation of a movie. <p>
 * Atrributes:
 * 		Title
 * 		Tags
 * 		Synopsis
 */
public class Movie {
	String title, tag, synopsis;
	/**
	 * Constructor
	 */
	public Movie(String title, String tag, String synopsis) {
		this.setTitle(title);
		this.setTag(tag);
		this.setSynopsis(synopsis);
	}
	public Movie(Movie mv){
		this.title= mv.getTitle();
		this.tag= mv.getTag();
		this.synopsis= mv.getSynopsis();
	}
	/**
	 * Get the title of the movie
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * Get the tag of the movie.
	 */
	public String getTag() {
		return tag;
	}
	/**
	 * Get the synopsis of the movie.
	 */
	public String getSynopsis() {
		return synopsis;
	}
	/**
	 * Set the title of the movie.
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * Set the tag of the movie.
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}
	/**
	 * Set the synopsis of the movie.
	 * @param synopsis
	 */
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	/**
	 * Convert to String <p>
	 * Example: <p>
	 * {@code Movie("The Matrix", "Sci-Fi", "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.")} <p>
	 * {@code "The Matrix, Sci-Fi, A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers."}
	 * @return the properties of the movie in a string
	 */
	public String toString() {
		return title + "," + tag + "," + synopsis;
	}
	/**
	 * Compare two movies. <p>
	 */
	public boolean equals(Movie mv) {
		return this.toString().equals(mv.toString());
	}
}
