package com.trumam;

// import java.util.function.UnaryOperator;
import java.io.*;
import java.util.regex.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
/**
 * Controller of the Main Window
 */
public class MainWindowController {
	@FXML private TextField urlLink;
	@FXML private Spinner<Integer> spinner;
	@FXML private Button crawl;
	@FXML private Button help;
	@FXML private ProgressBar progress;
	@FXML private Button exportData;

	// --> Table attributes
	@FXML private TableView<Model.MovieFX> table;
	@FXML private TableColumn<Model.MovieFX, String> title;
	@FXML private TableColumn<Model.MovieFX, String> tag;
	@FXML private TableColumn<Model.MovieFX, String> synopsis;
	// <-- Table attributes

	private Model model;
	private Pattern pattern = Pattern.compile("^(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");// regex to check if the link is valid

	WebCrawler crawler;

	/**
	 * Initialize the controller class. This method is automatically called
	 */
	public void initialize(){
		model = new Model();
		spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10));//Valid range is 1-10

		// --> Bind url texfield to model urlLink
		this.model.urlProperty().bind(urlLink.textProperty());
		// <-- Bind url texfield to model urlLink

		//--> Bind the spinner to the depth of the crawler
		this.model.depthProperty().bind(spinner.valueProperty());
		//<-- Bind the spinner to the depth of the crawler

		// --> Test movie data (for debug)
		model.addMovie(model.new MovieFX("The Godfather", "Crime", "https://www.imdb.com/title/tt0068646/"));
		model.addMovie(model.new MovieFX("The Godfather: Part II", "Crime", "https://www.imdb.com/title/tt0068646/"));
		// <-- Test movie data

		// --> Set cells and items of the table (data binding)
		title.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
		tag.setCellValueFactory(cellData -> cellData.getValue().tagProperty());
		synopsis.setCellValueFactory(cellData -> cellData.getValue().synopsisProperty());
		table.setItems(this.model.getMovies());
		// <-- Set cells and items of the table (data binding)

		// --> Set rendering for each column of the table
		title.setCellFactory(column -> new TableCell<Model.MovieFX, String>() {
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null || empty) {
					setText(null);
				} else {
					setText(item);
				}
			}
		});

		tag.setCellFactory(column -> new TableCell<Model.MovieFX, String>() {
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null || empty) {
					setText(null);
				} else {
					setText(item);
				}
			}
		});

		synopsis.setCellFactory(column -> new TableCell<Model.MovieFX, String>() {
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null || empty) {
					setText(null);
				} else {
					setText(item);
				}
			}
		});
		// <-- Set rendering for each column of the table

		// --> When crawl button is clicked clear the table and start crawling
		crawl.setOnAction(e -> {
			try{
				this.model.moviesProperty().clear();
				String s = validateURL(urlLink.getText());
				crawler.crawl(s);
			}catch(WebCrawler.URLException e1){//Alert not a valid url
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Invalid URL");
				alert.setContentText("Please enter a valid URL");
				alert.showAndWait();
			}
			catch (Exception e2){
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Couldn't crawl this link");
				alert.setContentText("Please try again");
				alert.showAndWait();
			}
		});
		// <-- When crawl button is clicked clear the table and start crawling

		// --> When help button is clicked show help window
		help.setOnAction(e ->{
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Help");
			alert.setHeaderText("Help");
			alert.setContentText("Add a valid IMDB or RottenTomatoes URL to crawl");
			alert.showAndWait();
		});
		// <-- When help button is clicked show help window

		// --> When in textfield is pressed enter, then start crawling
		urlLink.setOnKeyPressed(e -> {if(e.getCode() == KeyCode.ENTER) crawl(); });
		// <-- When in textfield is pressed enter, then start crawling

		// --> When export button is clicked export data to file
		exportData.setOnAction(e ->{ crawl(); });
		// <-- When export button is clicked export data to file
	}
	/**
	 * Method to validate the url entered by the user using regex for only http and https links
	 */
	private String validateURL(String url) throws WebCrawler.URLException {
		if(pattern.matcher(url).find())
			return url;
		else
			throw crawler.new URLException("Invalid URL:" + url);
	}
	/**
	 * Method to handle crawl process with validation, exceptions and progress bar
	 * Necesarry due to the fact that it is used inside two methods.
	 */
	private void crawl(){
		try{ // Export data to csv file
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Export data");
			fileChooser.setInitialFileName(((this.model.getUrl()==null || this.model.getUrl().isEmpty() ||this.model.getUrl().equals(""))
											? "untitled"
											: (this.model.getUrl().split("/")[this.model.getUrl().split("/").length-1]))
											+ "_depth_" + this.model.getDepth() + ".csv");
			File file = fileChooser.showSaveDialog(null);
			if(file != null){
				CreateCSV c = new CreateCSV(file.toString());
				c.crearCSV(this.model.toMovies(), file.getName());
			}
		}catch(Exception e1){ // If export fails show error
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Couldn't export data");
			alert.setContentText("Please try again");
			alert.showAndWait();
		}
	}
}
