package de.fhkiel.fotomanager.controller.viewController.datamanagement;

import de.fhkiel.fotomanager.controller.modelController.XMLController;
import de.fhkiel.fotomanager.controller.viewController.ViewController;
import de.fhkiel.fotomanager.controller.viewController.ViewInteractable;
import de.fhkiel.fotomanager.view.datamanagement.XMLImportExportView;
import javafx.scene.Scene;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Stack;

/**
 * The type Xml import view controller.
 */
public class XMLImportViewController extends ViewController<XMLImportExportView> implements ViewInteractable<XMLImportExportView> {
    /**
     * The XML import view controller instance.
     */
    public static XMLImportViewController instance;

    private XMLImportViewController() {}

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static XMLImportViewController get() {
        if (instance == null) {
            instance = new XMLImportViewController();
        }
        return instance;
    }

    /**
     * @param stage
     * @param history
     * @return
     */
    @Override
    public XMLImportExportView createView(Stage stage, Stack<Scene> history) {
        this.history = history;
        this.stage = stage;
        showXMLImportView();
        return view;
    }

    /**
     * Show the XML import view.
     */
    private void showXMLImportView() {
        view = new XMLImportExportView(stage, "XML Import");
        view.getBackButton().setOnAction(event -> goBack());
        view.getActionButton().setOnAction(event -> importXML());
        view.getActionButton().setText("Import XML");
    }

    /**
     * Import the XML file.
     */
    private void importXML() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Folder for Import");
        File selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            view.getSelectedFolderLabel().setText("Selected folder: " + selectedDirectory.getAbsolutePath());
            try {
                XMLController.get().importXMLFiles(selectedDirectory.getAbsolutePath());
                view.getStatusLabel().setStyle("-fx-text-fill: green");
                view.getStatusLabel().setText("Import of XML successful");
            } catch (Exception e) {
                view.getSelectedFolderLabel().setText("Error importing XML files.");
                view.getStatusLabel().setStyle("-fx-text-fill: red");
                view.getStatusLabel().setText("Error: " + e.getMessage());
            }

        } else {
            view.getSelectedFolderLabel().setText("Folder selection cancelled.");
        }
    }
}
