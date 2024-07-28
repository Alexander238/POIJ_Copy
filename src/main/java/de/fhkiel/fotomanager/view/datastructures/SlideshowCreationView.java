package de.fhkiel.fotomanager.view.datastructures;

import de.fhkiel.fotomanager.model.mediatypes.Media;
import de.fhkiel.fotomanager.view.components.CenterView;
import de.fhkiel.fotomanager.view.components.TopBar;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Slideshow creation view.
 */
@Getter
public class SlideshowCreationView {
    /**
     * The Back button.
     */
    private final Button backButton;
    /**
     * The button to create a slideshow.
     */
    private final Button createSlideshowButton;
    /**
     * The text field for the slideshow name.
     */
    private final TextField slideshowNameTextField;
    /**
     * The text field for the seconds per image.
     */
    private final TextField slideshowSecondsPerImageTextField;
    /**
     * The Stage.
     */
    private final Stage stage;
    /**
     * The Scene.
     */
    private Scene scene;
        /**
     * The Error label.
     */
    private final Label errorLabel;
        /**
     * Add media button.
     */
    private Button addMediaButton;
    /**
     * The Selected media.
     */
    private List<Media> selectedMedia = new ArrayList<>();

    /**
     * Instantiates a new Slideshow creation view.
     *
     * @param stage the stage
     */
    public SlideshowCreationView(Stage stage) {
        this.stage = stage;
        this.backButton = new Button("Back");
        this.createSlideshowButton = new Button("Create Slideshow");
        this.slideshowNameTextField = new TextField();
        this.addMediaButton = new Button("Add media");
        this.slideshowSecondsPerImageTextField = new TextField();
        this.errorLabel = new Label();
        this.errorLabel.setStyle("-fx-text-fill: red");
        initialize();
    }

    /**
     * Initializes the view.
     * Sets the title of the stage, creates the UI elements and sets the scene.
     */
    private void initialize() {
        stage.setTitle("Create Slideshow");

        // Grid for Inputs
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));

        // Create UI elements
        Label slideshowNameLabel = new Label("Slideshow Name:");
        gridPane.add(slideshowNameLabel, 0, 0);
        slideshowNameTextField.setPrefWidth(200);
        gridPane.add(slideshowNameTextField, 1, 0);

        Label slideshowTimeLabel = new Label("Seconds per Image:");
        gridPane.add(slideshowTimeLabel, 0, 1);
        slideshowSecondsPerImageTextField.setPrefWidth(200);
        gridPane.add(slideshowSecondsPerImageTextField, 1, 1);

        // Layout
        VBox mainBox = new VBox(10);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setPadding(new Insets(20));
        mainBox.getChildren().addAll(gridPane, addMediaButton, createSlideshowButton, errorLabel);
        VBox centerView = CenterView.createCenterView(mainBox);

        HBox topBar = TopBar.createTopBar(backButton);

        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(centerView);

        scene = new Scene(root, 800, 600);

        String css = getClass().getResource("/darkTheme.css").toExternalForm();
        scene.getStylesheets().add(css);
    }
}
