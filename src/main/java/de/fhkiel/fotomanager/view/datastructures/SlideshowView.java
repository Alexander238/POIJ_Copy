package de.fhkiel.fotomanager.view.datastructures;

import de.fhkiel.fotomanager.view.components.TopBar;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Getter;

import java.io.File;
import java.util.ArrayList;

/**
 * The SlideshowGridView class.
 */
@Getter
public class SlideshowView {
    private final Button backButton;
    /**
     * The Play button.
     */
    private final Button playButton;
    /**
     * The Pause button.
     */
    private final Button pauseButton;
    /**
     * The button to show the next image.
     */
    private final Button nextButton;
    /**
     * The button to show the previous image.
     */
    private final Button previousButton;
    /**
     * The last stage.
     */
    private Stage lastStage;
    /**
     * The Stage.
     */
    private final Stage stage;
    /**
     * The Scene to display on the stage.
     */
    private Scene scene;
    /**
     * The list of image paths.
     */
    private final ArrayList<String> imagePaths;
    /**
     * The current index of the image.
     */
    private int currentImageIndex = 0;
    /**
     * The timeline.
     */
    private Timeline timeline;
    /**
     * The image view.
     */
    private ImageView imageView;
    /**
     * The seconds per image.
     */
    private int secondsPerImage = 5;

    /**
     * Instantiates a new Slideshow detail view.
     *
     * @param stage      the stage
     * @param imagePaths the image paths
     * @param seconds    the seconds
     */
    public SlideshowView(Stage stage, ArrayList<String> imagePaths, int seconds) {
        this.stage = stage;
        this.imagePaths = imagePaths;
        this.secondsPerImage = seconds;

        backButton = new Button("Back");
        playButton = new Button("Play");
        pauseButton = new Button("Pause");
        nextButton = new Button("Next");
        previousButton = new Button("Previous");

        initialize();
    }

    /**
     * Initializes the view.
     */
    private void initialize() {
        lastStage = stage;

        stage.setTitle("Slideshow");

        VBox mainLayout = new VBox(10);
        mainLayout.setAlignment(Pos.CENTER);

        HBox topBar = TopBar.createTopBar(backButton);

        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(mainLayout);

        scene = new Scene(root, 800, 800);

        String css = getClass().getResource("/darkTheme.css").toExternalForm();
        scene.getStylesheets().add(css);

        imageView = createImageView();
        BorderPane.setAlignment(imageView, Pos.CENTER);
        root.setCenter(imageView);

        // Create a layout for the control buttons at the bottom
        HBox bottomBox = new HBox(10);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(10));
        bottomBox.getChildren().addAll(previousButton, playButton, pauseButton, nextButton);
        root.setBottom(bottomBox);

        timeline = new Timeline(new KeyFrame(Duration.seconds(secondsPerImage), e -> showNextImage()));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    /**
     * Start a slideshow.
     */
    public void startSlideshow() {
        if (timeline != null) {
            showAlertAndDontWait("Slideshow", "Starting Slideshow", Alert.AlertType.INFORMATION);

            timeline.play();
        }
    }

    /**
     * Pause a slideshow.
     */
    public void pauseSlideshow() {
        if (timeline != null) {
            showAlertAndDontWait("Slideshow", "Pausing Slideshow", Alert.AlertType.INFORMATION);

            timeline.pause();
        }
    }

    /**
     * Show the next image.
     */
    public void showNextImage() {
        if (imagePaths.isEmpty()) {
            return;
        }

        currentImageIndex = (currentImageIndex + 1) % imagePaths.size();
        updateImageView();
    }

    /**
     * Show the previous image.
     */
    public void showPreviousImage() {
        if (imagePaths.isEmpty()) {
            return;
        }

        currentImageIndex = (currentImageIndex - 1 + imagePaths.size()) % imagePaths.size();
        updateImageView();
    }

    /**
     * Update the image view to display the current image.
     */
    private void updateImageView() {
        if (imageView != null) {
            File file = new File(imagePaths.get(currentImageIndex));
            if (file.exists()) {
                Image image = new Image(file.toURI().toString());
                imageView.setImage(image);
            } else {
                // If file doesn't exist, use a placeholder
                Image placeholder = new Image("https://via.placeholder.com/600x400");
                imageView.setImage(placeholder);
            }
        }
    }

    /**
     * Create an image view.
     *
     * @return the image view
     */
    private ImageView createImageView() {
        Image image;
        if (!imagePaths.isEmpty()) {
            File file = new File(imagePaths.get(currentImageIndex));
            if (file.exists()) {
                image = new Image(file.toURI().toString());
            } else {
                // If file doesn't exist, use a placeholder
                image = new Image("https://via.placeholder.com/600x400");
            }
        } else {
            image = new Image("https://via.placeholder.com/600x400");
        }

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(600);
        imageView.setFitHeight(600);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    /**
     * Show alert without waiting for user input.
     *
     * @param title     the title
     * @param message   the message
     * @param alertType the alert type
     */
    public void showAlertAndDontWait(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
}
