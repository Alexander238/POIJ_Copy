package de.fhkiel.fotomanager.controller.viewController.datastructures;

import de.fhkiel.fotomanager.controller.viewController.ViewController;
import de.fhkiel.fotomanager.model.datastructures.impl.Slideshow;
import de.fhkiel.fotomanager.model.mediatypes.impl.Photo;
import de.fhkiel.fotomanager.view.datastructures.SlideshowView;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Stack;

/**
 * The type Slideshow creation view controller.
 */
public class SlideshowViewController extends ViewController<SlideshowView> {
    /**
     * The slideshow view controller instance.
     */
    public static SlideshowViewController instance;

    private SlideshowViewController() {}

    /**
     * Gets the instance of the SlideshowViewController.
     *
     * @return the instance of the SlideshowViewController
     */
    public static SlideshowViewController get() {
        if (instance == null) {
            instance = new SlideshowViewController();
        }
        return instance;
    }

    /**
     * Create a SlideshowView.
     *
     * @param stage     the stage
     * @param history   the history
     * @param slideshow the slideshow
     * @return the SlideshowView
     */
    public SlideshowView createView(Stage stage, Stack<Scene> history, Slideshow slideshow) {
        this.history = history;
        this.stage = stage;

        ArrayList<String> photoPaths = new ArrayList<>();
        for (Photo photo : slideshow.getMediaList()) {
            photoPaths.add(photo.getFile());
        }

        view = new SlideshowView(stage, photoPaths, slideshow.getSecondsPerImage());

        view.getBackButton().setOnAction(event -> goBack());
        view.getPlayButton().setOnAction(e -> view.startSlideshow());
        view.getPauseButton().setOnAction(e -> view.pauseSlideshow());
        view.getNextButton().setOnAction(e -> view.showNextImage());
        view.getPreviousButton().setOnAction(e -> view.showPreviousImage());
        return view;
    }
}
