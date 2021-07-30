package com.trumam;

import java.util.*;
import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
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
        primaryStage.setTitle("Movies WebCrawler");
        primaryStage.setScene(scene);
        //<--Create main window
        primaryStage.getIcons().add(new Image("/spider.ico"));
        primaryStage.setOnCloseRequest(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.getDialogPane().getStylesheets().add("/estilo.css");
            alert.setTitle("Confirmation");
            alert.setHeaderText("Are you sure you want to exit?");
            alert.setContentText("You are about to exit the application.");
            Button exitButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
            Button closeButton = (Button)alert.getDialogPane().lookupButton(ButtonType.CANCEL);
            closeButton.setText("Cancelar");
            exitButton.setText("Salir");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(primaryStage);

            Optional<ButtonType> closeResponse = alert.showAndWait();

            if (!ButtonType.OK.equals(closeResponse.get()))
                e.consume();
            else
                Platform.exit();
        });
        primaryStage.show();
    }
    /**
     * Dummy main method
     */
    public static void main( String[] args ){
        launch(args);
    }
}
