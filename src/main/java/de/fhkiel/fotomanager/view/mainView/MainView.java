package de.fhkiel.fotomanager.view.mainView;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lombok.Getter;

/**
 * The type Main view.
 */
@Getter
public class MainView extends Application {
    /**
     * The Datamanagement button.
     */
    private Button datamanagementButton;
    /**
     * The Media menu button.
     */
    private Button mediaMenuButton;
    /**
     * The Scene to display on the stage.
     */
    private Scene scene;

    /**
     * Instantiates a new Main view.
     *
     * @param stage the stage
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("Photo Manager");

        mediaMenuButton = new Button("Media");
        datamanagementButton = new Button("Data Management");

        Label welcomeLabel = new Label("Welcome to Photo Manager");
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        welcomeLabel.setAlignment(Pos.CENTER);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20, 0, 20, 0));
        buttonBox.getChildren().addAll(mediaMenuButton, datamanagementButton);

        VBox mainLayout = new VBox(10);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.getChildren().addAll(welcomeLabel, buttonBox);

        BorderPane root = new BorderPane();
        root.setCenter(mainLayout);

        scene = new Scene(root, 800, 600);

        String css = getClass().getResource("/darkTheme.css").toExternalForm();
        scene.getStylesheets().add(css);

        setStageSize(stage, 70, 80);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Adjusts the size and position of the given stage according to the provided width and height percentages.
     *
     * @param stage the stage to be adjusted
     * @param widthPercent the percentage of screen width to be set
     * @param heightPercent the percentage of screen height to be set
     */
    private static void setStageSize(Stage stage, double widthPercent, double heightPercent) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        double newWidth = screenWidth * (widthPercent / 100);
        double newHeight = screenHeight * (heightPercent / 100);

        stage.setWidth(newWidth);
        stage.setHeight(newHeight);
        stage.setX((screenWidth - newWidth) / 2);
        stage.setY((screenHeight - newHeight) / 2);
    }
}