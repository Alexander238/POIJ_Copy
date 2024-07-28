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
import lombok.Getter;

/**
 * The type Import view.
 * ImportView is a view class that allows the user to import an XML file.
 */
@Getter
public class XMLImportExportView {
    /**
     * The Back button.
     */
    private Button backButton;
    /**
     * The Selected folder label.
     */
    private Label selectedFolderLabel;
    /**
     * The Import button.
     */
    private Button actionButton;
    /**
     * The Scene to display on the stage.
     */
    private Scene scene;
    /**
     * The Status label.
     */
    private Label statusLabel;

    /**
     * Instantiates a new Xml import export view.
     *
     * @param stage the stage
     * @param title the title
     */
    public XMLImportExportView(Stage stage, String title) {
        stage.setTitle(title);
        // Create a label to display the selected folder
        selectedFolderLabel = new Label("No folder selected");

        // Create a button to open the directory chooser
        actionButton = new Button("Import XML");

        // Create a back button
        backButton = new Button("Back");

        // Create a status label
        statusLabel = new Label("");

        HBox topBar = TopBar.createTopBar(backButton);

        // Arrange the buttons and label in a vertical box layout
        VBox vbox = new VBox(10, actionButton, selectedFolderLabel, statusLabel);
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