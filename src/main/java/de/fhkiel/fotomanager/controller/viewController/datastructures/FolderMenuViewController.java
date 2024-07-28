package de.fhkiel.fotomanager.controller.viewController.datastructures;

import de.fhkiel.fotomanager.controller.modelController.PhotoManagerController;
import de.fhkiel.fotomanager.controller.viewController.components.ShowAlert;
import de.fhkiel.fotomanager.controller.viewController.ViewController;
import de.fhkiel.fotomanager.model.datastructures.DataStructure;
import de.fhkiel.fotomanager.model.datastructures.DataStructureType;
import de.fhkiel.fotomanager.view.datastructures.DataStructureMenuView;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Stack;

/**
 * The type Folder menu view controller.
 */
public class FolderMenuViewController extends ViewController<DataStructureMenuView> {
    /**
     * The folder view controller instance.
     */
    public static FolderMenuViewController instance;

    private FolderMenuViewController() {}

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static FolderMenuViewController get() {
        if (instance == null) {
            instance = new FolderMenuViewController();
        }
        return instance;
    }


    /**
     * Create view data structure menu view.
     *
     * @param stage   the stage
     * @param history the history
     * @return the data structure menu view
     */
    public DataStructureMenuView createView(Stage stage, Stack<Scene> history) {
        this.history = history;
        this.stage = stage;
        showFolderMenuView();
        return view;
    }

    /**
     * Show album view.
     */
    private void showFolderMenuView() {
        view = new DataStructureMenuView(stage, true, false, DataStructureType.FOLDER);

        view.getOpenButton().setOnAction(event -> openFolder());
        view.getBackButton().setOnAction(event -> goBack());
        view.getDataStructureListView().setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                openFolder();
            }
        });
        view.getDeleteButton().setOnAction(event -> deleteFolder());
    }

    /**
     * Deletes the selected folder.
     */
    private void deleteFolder() {
        DataStructure selectedFolder = view.getDataStructureListView().getSelectionModel().getSelectedItem();
        if (selectedFolder != null) {
            if (ShowAlert.showConfirmationAlert("Delete Folder", "Are you sure you want to delete this folder?")) {
                PhotoManagerController.get().deleteDataStructure(selectedFolder);
                replaceScene(createView(stage, history).getScene());
            }
        } else {
            ShowAlert.showAlert("Attention", "Please choose a folder!");
        }
    }

    /**
     * Opens an album and shows the media in it.
     * If no album is selected, an alert is shown.
     */
    private void openFolder() {
        DataStructure selectedFolder = view.getDataStructureListView().getSelectionModel().getSelectedItem();
        if (selectedFolder != null) {
            switchScene(AllMediaViewController.get().createView(stage, history, false, selectedFolder).getScene());
        } else {
            ShowAlert.showAlert("Attention", "Please choose a folder!");
        }
    }
}
