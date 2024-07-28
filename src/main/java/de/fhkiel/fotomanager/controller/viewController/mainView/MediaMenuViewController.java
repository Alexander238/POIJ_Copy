package de.fhkiel.fotomanager.controller.viewController.mainView;

import de.fhkiel.fotomanager.controller.viewController.*;
import de.fhkiel.fotomanager.controller.viewController.datastructures.AlbumMenuViewController;
import de.fhkiel.fotomanager.controller.viewController.datastructures.AllMediaViewController;
import de.fhkiel.fotomanager.controller.viewController.datastructures.FolderMenuViewController;
import de.fhkiel.fotomanager.controller.viewController.datastructures.SlideshowMenuViewController;
import de.fhkiel.fotomanager.controller.viewController.search.SearchViewController;
import de.fhkiel.fotomanager.controller.viewController.tags.TagManagementViewController;
import de.fhkiel.fotomanager.view.mainView.MediaMenuView;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Stack;

/**
 * The type Media menu view controller.
 */
public class MediaMenuViewController extends ViewController<MediaMenuView> implements ViewInteractable<MediaMenuView> {

    /**
     * The media view controller instance.
     */
    public static MediaMenuViewController instance;

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static MediaMenuViewController get() {
        if (instance == null) {
            instance = new MediaMenuViewController();
        }
        return instance;
    }

    private MediaMenuViewController() {}

    /**
     * Create the media menu view.
     *
     * @param stage the stage
     * @param history the history
     * @return the media menu view
     */
    @Override
    public MediaMenuView createView(Stage stage, Stack<Scene> history) {
        this.stage = stage;
        this.history = history;
        view = new MediaMenuView(stage);
        view.getAlbumsButton().setOnAction(event -> switchScene(AlbumMenuViewController.get().createView(stage, history).getScene()));
        view.getFoldersButton().setOnAction(event -> switchScene(FolderMenuViewController.get().createView(stage, history).getScene()));
        view.getSlideshowsButton().setOnAction(event -> switchScene(SlideshowMenuViewController.get().createView(stage, history).getScene()));
        view.getTagManagementButton().setOnAction(event -> switchScene(TagManagementViewController.get().createView(stage, history).getScene()));
        view.getSearchButton().setOnAction(event -> switchScene(SearchViewController.get().createView(stage, history).getScene()));
        view.getAllMediaButton().setOnAction(event -> switchScene(AllMediaViewController.get().createView(stage, history, false, null).getScene()));
        view.getBackButton().setOnAction(event -> goBack());
        return view;
    }
}
