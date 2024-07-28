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
 * The type Xml export view controller.
 */
public class XMLExportViewController extends ViewController<XMLImportExportView> implements ViewInteractable<XMLImportExportView> {
    /**
     * The XML export view controller instance.
     */
    public static XMLExportViewController instance;

    private XMLExportViewController() {}

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static XMLExportViewController get() {
        if (instance == null) {
            instance = new XMLExportViewController();
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
        showXMLExportView();
        return view;
    }

    /**
     * Show the XML export view.
     */
    private void showXMLExportView() {
        view = new XMLImportExportView(stage, "XML Export");
        view.getBackButton().setOnAction(event -> goBack());
        view.getActionButton().setOnAction(event -> exportXML());
        view.getActionButton().setText("Export XML");
    }

    /**
     * Export the XML file.
     */
    private void exportXML() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Folder for Export");
        File selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            view.getSelectedFolderLabel().setText("Folder selected: " + selectedDirectory.getAbsolutePath());
            try {
                XMLController.get().exportXMLFiles(selectedDirectory.getAbsolutePath());
            } catch (Exception e) {
                view.getSelectedFolderLabel().setText("Error exporting XML files.");
                view.getStatusLabel().setStyle("-fx-text-fill: red");
                view.getStatusLabel().setText("Error: " + e.getMessage());
            }
        } else {
            view.getSelectedFolderLabel().setText("Folder selection cancelled.");
        }
    }
}
