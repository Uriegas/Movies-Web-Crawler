package com.trumam;

import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;

/**
 *JavaFX GUI
 */
public class App extends Application {
    /**
     * Start method
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        //-->Create main window
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/MainWindow.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        // Window main = loader.getController();
        // main.initModel(model);
        // scene.getStylesheets().add(getClass().getResource("/styles/Styles.css").toExternalForm());
        primaryStage.setTitle("Movies WebCrawler");
        primaryStage.setScene(scene);
        //<--Create main window
        primaryStage.show();
    }
    /**
     * Dummy main method
     */
    public static void main( String[] args ){
        launch(args);
    }
}
