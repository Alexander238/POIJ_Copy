package de.fhkiel.fotomanager.controller.viewController.datamanagement;

import de.fhkiel.fotomanager.ConfigLoader;
import de.fhkiel.fotomanager.controller.modelController.FileImportController;
import de.fhkiel.fotomanager.view.datamanagement.ImportView;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Stack;

/**
 * The type Folder import view controller.
 */
public class FolderImportViewController extends ImportViewController {
    /**
     * The Folder import view controller instance.
     */
    public static FolderImportViewController instance;

    private FolderImportViewController() {}

    /**
     * The Config loader.
     */
    private final ConfigLoader configLoader = new ConfigLoader();
    /**
     * The Path to the media folder.
     */
    private final String pathToMediaFolder = configLoader.getValue("PATH_TO_MEDIA_FOLDER");

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static FolderImportViewController get() {
        if (instance == null) {
            instance = new FolderImportViewController();
        }
        return instance;
    }

    /**
     * Create the folder import view.
     *
     * @param stage   the stage
     * @param history the history
     * @return the FolderImportView instance
     */
    @Override
    public ImportView createView(Stage stage, Stack<Scene> history) {
        this.history = history;
        this.stage = stage;
        showFolderImportView();
        return view;
    }

    /**
     * Show the folder import view.
     */
    private void showFolderImportView() {
        view = new ImportView(stage, "Folder Import");
        view.getSourceButton().setOnAction(event -> setSource());
        view.getStartImportButton().setOnAction(event -> startImport());
        view.getBackButton().setOnAction(event -> goBack());
    }

    /**
     * Start the import process.
     */
    private void startImport() {
        String projectDirectory = System.getProperty("user.dir");

        // Set initial directory
        String initialDirectory = projectDirectory + "\\" + pathToMediaFolder;

        view.getStatusLabel().setText("");
        if (view.getSelectedSourceDirectory() != null) {
            try {
                FileImportController.get().importFilesAndFolders(view.getSelectedSourceDirectory().getAbsolutePath(), initialDirectory);
                view.getStatusLabel().setStyle("-fx-text-fill: green");
                view.getStatusLabel().setText("Import successful");
            } catch (Exception e) {
                view.getStatusLabel().setStyle("-fx-text-fill: red");
                view.getStatusLabel().setText("Error: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            if (view.getSelectedSourceDirectory() == null) {
                view.getSelectedSourceFolderLabel().setText("No source folder selected");
            }
        }
    }
}
