package de.fhkiel.fotomanager.view.mainView;

import de.fhkiel.fotomanager.view.components.TopBar;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import lombok.Getter;

/**
 * The type Data management menu view.
 */
@Getter
public class DataManagementMenuView {
    /**
     * The Import button.
     */
    private final Button importButton;
    /**
     * The Export button.
     */
    private final Button exportButton;
    /**
     * The button to import a folder.
     */
    private final Button folderImportButton;
    /**
     * The back button.
     */
    private Button backButton;
    /**
     * The Scene to display on the stage.
     */
    private Scene scene;

    /**
     * Instantiates a new Data management menu view.
     *
     * @param stage the stage
     */
    public DataManagementMenuView(Stage stage) {
        stage.setTitle("Datamanagement");
        importButton = new Button("Import XML");
        exportButton = new Button("Export XML");
        folderImportButton = new Button("Import Folder");
        backButton = new Button("Back");

        HBox topBar = TopBar.createTopBar(backButton);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20, 0, 20, 0));
        buttonBox.getChildren().addAll(importButton, exportButton, folderImportButton);

        BorderPane root = new BorderPane();
        root.setCenter(buttonBox);
        root.setTop(topBar);

        scene = new Scene(root, 800, 600);

        String css = getClass().getResource("/darkTheme.css").toExternalForm();
        scene.getStylesheets().add(css);
    }
}
