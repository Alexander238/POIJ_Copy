package de.fhkiel.fotomanager.controller.viewController.datastructures;

import de.fhkiel.fotomanager.controller.modelController.PhotoManagerController;
import de.fhkiel.fotomanager.controller.modelController.XMLController;
import de.fhkiel.fotomanager.controller.viewController.components.ShowAlert;
import de.fhkiel.fotomanager.controller.viewController.ViewController;
import de.fhkiel.fotomanager.model.datastructures.DataStructure;
import de.fhkiel.fotomanager.model.datastructures.DataStructureType;
import de.fhkiel.fotomanager.model.datastructures.impl.Album;
import de.fhkiel.fotomanager.view.datastructures.DataStructureMenuView;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Stack;

/**
 * The type Album view controller.
 */
public class AlbumMenuViewController extends ViewController<DataStructureMenuView> {
    /**
     * The Album view controller instance.
     */
    public static AlbumMenuViewController instance;

    private AlbumMenuViewController() {}

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static AlbumMenuViewController get() {
        if (instance == null) {
            instance = new AlbumMenuViewController();
        }
        return instance;
    }

    /**
     * Create album view.
     *
     * @param stage   the stage
     * @param history the history
     * @return the DataStructureView instance
     */
    public DataStructureMenuView createView(Stage stage, Stack<Scene> history) {
        this.history = history;
        this.stage = stage;
        showAlbumMenuView();
        return view;
    }

    /**
     * Show album view.
     */
    private void showAlbumMenuView() {
        view = new DataStructureMenuView(stage, true, true, DataStructureType.ALBUM);

        view.getCreateButton().setOnAction(event -> switchScene(AlbumCreationViewController.get().createView(stage, history).getScene()));
        view.getOpenButton().setOnAction(event -> openAlbum());
        view.getBackButton().setOnAction(event -> goBack());
        view.getDeleteButton().setOnAction(event -> deleteAlbum());
        view.getDataStructureListView().setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                openAlbum();
            }
        });
    }

    /**
     * Opens an album and shows the media in it.
     * If no album is selected, an alert is shown.
     */
    private void openAlbum() {
        DataStructure selectedAlbum = view.getDataStructureListView().getSelectionModel().getSelectedItem();
        if (selectedAlbum != null) {
            switchScene(AllMediaViewController.get().createView(stage, history, false, selectedAlbum).getScene());
        } else {
            ShowAlert.showAlert("Attention", "Please choose an album!");
        }
    }

    /**
     * Deletes an album from the photo manager.
     * If no album is selected, an alert is shown.
     */
    private void deleteAlbum() {
        DataStructure selectedAlbum = view.getDataStructureListView().getSelectionModel().getSelectedItem();
        if (selectedAlbum != null) {
            // delete the album from the XML file
            XMLController.get().deleteAlbumFromXML((Album) selectedAlbum);

            PhotoManagerController.get().deleteDataStructure(selectedAlbum);

            replaceScene(createView(stage, history).getScene());
        } else {
            ShowAlert.showAlert("Attention", "Please choose an album!");
        }
    }
}
