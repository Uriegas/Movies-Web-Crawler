package com.trumam;

import javafx.fxml.FXML;
import javafx.scene.control.*;
/**
 * Controller of the Main Window
 */
public class MainWindowController {
	@FXML private TableView<Movie> table;
	@FXML private TextField urlLink;
	@FXML private Spinner<Integer> spinner;
	@FXML private Button crawl;
	@FXML private Button help;
	@FXML private ProgressBar progress;

	public void initialize() {
		// --> When crawl button is clicked print "Crawling"
		crawl.setOnAction(e -> System.out.println("Crawling"));
		// --> When help button is clicked print "Help"
		help.setOnAction(e -> System.out.println("Help"));
		// --> When spinner value is changed print "Spinner value changed"
		spinner.valueProperty().addListener((observable, oldValue, newValue) -> System.out.println("Spinner value changed"));
		// --> When urlLink text is changed print "UrlLink text changed"
		urlLink.textProperty().addListener((observable, oldValue, newValue) -> System.out.println("urlLink text changed"));
	}
}
