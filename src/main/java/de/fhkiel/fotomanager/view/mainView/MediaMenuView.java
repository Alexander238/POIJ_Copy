package de.fhkiel.fotomanager.view.mainView;

import de.fhkiel.fotomanager.view.components.TopBar;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import lombok.Getter;

/**
 * The type Media menu view.
 */
@Getter
public class MediaMenuView {
    /**
     * The Albums button.
     */
    private final Button albumsButton;
    /**
     * The Folders button.
     */
    private final Button foldersButton;
    /**
     * The Slideshows button.
     */
    private final Button slideshowsButton;
    /**
     * The Search button.
     */
    private final Button searchButton;
    /**
     * The Tag management button.
     */
    private final Button tagManagementButton;
    /**
     * The Back button.
     */
    private final Button backButton;
    /**
     * The All media button.
     */
    private final Button allMediaButton;
    /**
     * The Scene to display on the stage.
     */
    private Scene scene;

    /**
     * Instantiates a new Media menu view.
     *
     * @param stage the stage
     */
    public MediaMenuView(Stage stage){
        stage.setTitle("Media");

        albumsButton = new Button("Albums");
        foldersButton = new Button("Folders");
        slideshowsButton = new Button("Slideshows");
        searchButton = new Button("Search");
        tagManagementButton = new Button("Tag Management");
        backButton = new Button("Back");
        allMediaButton = new Button("All Media");

        HBox topBar = TopBar.createTopBar(backButton);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20, 0, 20, 0));
        buttonBox.getChildren().addAll(albumsButton, foldersButton, slideshowsButton, searchButton, tagManagementButton, allMediaButton);

        BorderPane root = new BorderPane();
        root.setCenter(buttonBox);
        root.setTop(topBar);

        scene = new Scene(root, 800, 600);

        String css = getClass().getResource("/darkTheme.css").toExternalForm();
        scene.getStylesheets().add(css);
    }
}