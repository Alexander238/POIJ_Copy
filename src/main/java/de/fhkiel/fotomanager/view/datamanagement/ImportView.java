package de.fhkiel.fotomanager.view.datamanagement;

import de.fhkiel.fotomanager.view.components.CenterView;
import de.fhkiel.fotomanager.view.components.TopBar;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import lombok.Data;

import java.io.File;

/**
 * The type Import view.
 * ImportView is a view class that allows the user to import an XML file.
 */
@Data
public class ImportView {
    /**
     * The Back button.
     */
    private final Button backButton;
    /**
     * The Selected source directory.
     */
    private File selectedSourceDirectory;
    /**
     * The Import button.
     */
    private final Button sourceButton;
    /**
     * The Selected source folder label.
     */
    private Label selectedSourceFolderLabel;
    /**
     * The Start import button.
     */
    private final Button startImportButton;
    /**
     * The Scene to display on the stage.
     */
    private Scene scene;
    /**
     * The status label.
     */
    private Label statusLabel;

    /**
     * Instantiates a new Import view.
     *
     * @param stage the stage
     * @param title the title
     */
    public ImportView(Stage stage, String title) {
        stage.setTitle(title);
        // Create a label to display the selected folder for source
        selectedSourceFolderLabel  = new Label("No folder selected");

        // Create a button to open the directory chooser for source folder
        sourceButton = new Button("Select Source Folder");

        // Create a button to start the import process
        startImportButton = new Button("Start Import");

        // Create a back button
        backButton = new Button("Back");

        // Create an error label
        statusLabel = new Label("");

        HBox topBar = TopBar.createTopBar(backButton);

         // Arrange the buttons and label in a vertical box layout
        VBox vbox = new VBox(
                10,
                sourceButton,
                selectedSourceFolderLabel,
                startImportButton,
                statusLabel
        );
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);

        VBox centerView = CenterView.createCenterView(vbox);

        BorderPane root = new BorderPane();
        root.setCenter(centerView);
        root.setTop(topBar);

        // Set up the scene and stage
        scene = new Scene(root, 800, 600);

        String css = getClass().getResource("/darkTheme.css").toExternalForm();
        scene.getStylesheets().add(css);
    }
}