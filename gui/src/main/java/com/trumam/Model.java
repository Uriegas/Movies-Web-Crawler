package com.trumam;

import java.util.*;
import javafx.beans.property.*;
import javafx.collections.*;

/**
 * Data model for the WebCrawler Desktop App
 */
public class Model {
	private StringProperty url = new SimpleStringProperty();
	private ObservableList<MovieFX> movies = FXCollections.observableList(new ArrayList<MovieFX>());
	private IntegerProperty depth = new SimpleIntegerProperty(1);

	public class MovieFX {
		private StringProperty title = new SimpleStringProperty();
		private StringProperty tag = new SimpleStringProperty();
		private StringProperty synopsis = new SimpleStringProperty();
		public MovieFX(String title, String tag, String synopsis){
			setTitle(title);
			setTag(tag);
			setSynopsis(synopsis);
		}
		public StringProperty titleProperty(){
			return this.title;
		}
		public StringProperty tagProperty(){
			return this.tag;
		}
		public StringProperty synopsisProperty(){
			return this.synopsis;
		}
		public void setTitle(String title){
			this.title.set(title);
		}
		public void setTag(String tag){
			this.tag.set(tag);
		}
		public void setSynopsis(String synopsis){
			this.synopsis.set(synopsis);
		}
		public String getTitle(){
			return this.title.get();
		}
		public String getTag(){
			return this.tag.get();
		}
		public String getSynopsis(){
			return this.synopsis.get();
		}
		public String toString(){
			return this.title.get() + ',' + this.tag.get() + ',' + this.synopsis.get();
		}
	}
	public StringProperty urlProperty(){
		return this.url;
	}
	public void setUrl(String url){
		this.url.set(url);
	}
	public String getUrl(){
		return this.url.get();
	}
	public ObservableList<MovieFX> getMovies(){
		return this.movies;
	}
	public void addMovie(MovieFX movie){
		this.movies.add(movie);
	}
	public void removeMovie(MovieFX movie){
		this.movies.remove(movie);
	}
	public ObservableList<MovieFX> moviesProperty(){
		return this.movies;
	}
	public IntegerProperty depthProperty(){
		return this.depth;
	}
	public void setDepth(int depth){
		this.depth.set(depth);
	}
	public int getDepth(){
		return this.depth.get();
	}
}
