package de.fhkiel.fotomanager.controller.viewController.datastructures;

import de.fhkiel.fotomanager.controller.modelController.PhotoManagerController;
import de.fhkiel.fotomanager.controller.viewController.ViewController;
import de.fhkiel.fotomanager.controller.viewController.components.ShowAlert;
import de.fhkiel.fotomanager.model.datastructures.DataStructure;
import de.fhkiel.fotomanager.model.datastructures.DataStructureType;
import de.fhkiel.fotomanager.view.datastructures.DataStructureMenuView;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Stack;

/**
 * The type Slideshow menu view controller.
 */
public class SlideshowMenuViewController extends ViewController<DataStructureMenuView> {
    /**
     * The constant instance.
     */
    public static SlideshowMenuViewController instance;

    private SlideshowMenuViewController() {}

    /**
     * Get the instance of the SlideshowMenuViewController.
     *
     * @return the instance of the SlideshowMenuViewController
     */
    public static SlideshowMenuViewController get() {
        if (instance == null) {
            instance = new SlideshowMenuViewController();
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
        showSlideshowMenuView();
        return view;
    }

    /**
     * Delete a slideshow.
     */
    private void deleteSlideshow() {
        DataStructure selectedSlideshow = view.getDataStructureListView().getSelectionModel().getSelectedItem();
        if (selectedSlideshow != null) {
            PhotoManagerController.get().deleteDataStructure(selectedSlideshow);
            replaceScene(createView(stage, history).getScene());
        } else {
            ShowAlert.showAlert("Attention", "Please choose a slideshow!");
        }
    }

    /**
     * Show slideshow menu view. Sets all the event handlers for the buttons in the view.
     */
    public void showSlideshowMenuView() {
        view = new DataStructureMenuView(stage, true, true, DataStructureType.SLIDESHOW);

        view.getOpenButton().setOnAction(event -> openSlideshow());
        view.getBackButton().setOnAction(event -> goBack());
        view.getCreateButton().setOnAction(event -> switchScene(SlideshowCreationViewController.get().createView(stage, history).getScene()));
        view.getDeleteButton().setOnAction(event -> deleteSlideshow());
        view.getDataStructureListView().setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                openSlideshow();
            }
        });
    }

    /**
     * Open slideshow.
     * This method is used to start the slideshow.
     */
    private void openSlideshow() {
        DataStructure selectedSlideshow = view.getDataStructureListView().getSelectionModel().getSelectedItem();
        if (selectedSlideshow != null) {
            switchScene(AllMediaViewController.get().createView(stage, history, false, selectedSlideshow).getScene());
        } else {
            ShowAlert.showAlert("Attention", "Please choose a slideshow!");
        }
    }
}
