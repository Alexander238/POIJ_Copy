package de.fhkiel.fotomanager.view.datastructures;

import de.fhkiel.fotomanager.model.mediatypes.Media;
import de.fhkiel.fotomanager.view.components.TopBar;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Album creation view.
 */
@Getter
public class AlbumCreationView {
    /**
     * The Back button.
     */
    private final Button backButton;
    /**
     * The Create album button.
     */
    private final Button createAlbumButton;
    /**
     * The Album name text field.
     */
    private final TextField albumNameTextField;
    /**
     * The Stage.
     */
    private final Stage stage;
    /**
     * The Scene to display on the stage.
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
     * Instantiates a new Album creation view.
     *
     * @param stage the stage
     */
    public AlbumCreationView(Stage stage) {
        this.stage = stage;
        this.backButton = new Button("Back");
        this.createAlbumButton = new Button("Create Album");
        this.addMediaButton = new Button("Add media");
        this.albumNameTextField = new TextField();
        this.errorLabel = new Label();
        this.errorLabel.setStyle("-fx-text-fill: red");
        initialize();
    }

    /**
     * Initialize.
     * Creates the UI elements and sets the scene.
     */
    private void initialize() {
        stage.setTitle("Create Album");

        // Create UI elements
        Label albumNameLabel = new Label("Album Name:");
        HBox albumNameBox = new HBox(10, albumNameLabel, albumNameTextField);
        albumNameBox.setAlignment(Pos.CENTER);
        albumNameBox.setPadding(new Insets(10));

        // Layout
        VBox mainBox = new VBox(10);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setPadding(new Insets(20));
        mainBox.getChildren().addAll(albumNameBox, addMediaButton, createAlbumButton, errorLabel);

        HBox topBar = TopBar.createTopBar(backButton);

        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(mainBox);

        scene = new Scene(root, 800, 600);

        String css = getClass().getResource("/darkTheme.css").toExternalForm();
        scene.getStylesheets().add(css);
    }
}
