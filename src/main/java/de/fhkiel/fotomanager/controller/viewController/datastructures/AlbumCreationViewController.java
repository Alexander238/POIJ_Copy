package de.fhkiel.fotomanager.controller.viewController.datastructures;

import de.fhkiel.fotomanager.controller.modelController.AlbumController;
import de.fhkiel.fotomanager.controller.modelController.PhotoManagerController;
import de.fhkiel.fotomanager.controller.modelController.XMLController;
import de.fhkiel.fotomanager.controller.viewController.ViewController;
import de.fhkiel.fotomanager.model.datastructures.impl.Album;
import de.fhkiel.fotomanager.view.datastructures.AllMediaView;
import de.fhkiel.fotomanager.view.datastructures.AlbumCreationView;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Stack;

/**
 * The type Album creation view controller.
 */
public class AlbumCreationViewController extends ViewController<AlbumCreationView> {
    /**
     * The album creation view controller instance.
     */
    public static AlbumCreationViewController instance;

    private AlbumCreationViewController() {}

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static AlbumCreationViewController get() {
        if (instance == null) {
            instance = new AlbumCreationViewController();
        }
        return instance;
    }

    /**
     * Creates the album creation view.
     *
     * @param stage   the stage
     * @param history the history
     * @return the album creation view
     */
    public AlbumCreationView createView(Stage stage, Stack<Scene> history) {
        this.history = history;
        this.stage = stage;
        view = new AlbumCreationView(stage);
        view.getBackButton().setOnAction(event -> goBack());
        view.getCreateAlbumButton().setOnAction(event -> createAlbum());
        view.getAddMediaButton().setOnAction(event -> addMedia());
        return view;
    }

    /**
     * Adds media to the created album.
     */
    private void addMedia() {
        AllMediaView allMediaView = AllMediaViewController.get().createView(stage, history, true,null);
        allMediaView.getSaveButton().setOnAction(event -> {
            view.getSelectedMedia().addAll(allMediaView.getSelectedMedia());
            goBack();
        });
        switchScene(allMediaView.getScene());
    }

    /**
     * Creates a new album.
     * If the album name is empty, an error message is displayed.
     * If the album name is already given, an error message is displayed.
     */
    private void createAlbum() {
        view.getErrorLabel().setText("");
        try {
            String albumName = view.getAlbumNameTextField().getText();

            Album newAlbum = AlbumController.get().createAlbum(albumName);
            PhotoManagerController.get().addDataStructure(newAlbum);

            for (int i = 0; i < view.getSelectedMedia().size(); i++) {
                newAlbum.addMedia(view.getSelectedMedia().get(i));
            }

            // save the album to the XML file
            XMLController.get().saveAlbumToXML(newAlbum);

            goBackAndRebuild(AlbumMenuViewController.get().createView(stage, history).getScene());
        } catch (Exception e) {
            view.getErrorLabel().setText(e.getMessage());
            e.printStackTrace();
        }
    }
}
