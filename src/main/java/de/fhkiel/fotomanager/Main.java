package de.fhkiel.fotomanager;

import de.fhkiel.fotomanager.controller.modelController.PhotoManagerController;
import de.fhkiel.fotomanager.controller.viewController.mainView.MainViewController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The Main class of the application.
 */
public class Main extends Application {
    /**
     * Start method of the application.
     *
     * @param primaryStage the primary stage
     */
    @Override
    public void start(Stage primaryStage) {
        PhotoManagerController.get().loadData();
        MainViewController.get().setStage(primaryStage);
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}