package de.fhkiel.fotomanager.controller.viewController.datastructures;

import de.fhkiel.fotomanager.controller.modelController.PhotoManagerController;
import de.fhkiel.fotomanager.controller.modelController.SlideshowController;
import de.fhkiel.fotomanager.controller.modelController.XMLController;
import de.fhkiel.fotomanager.controller.viewController.ViewController;
import de.fhkiel.fotomanager.model.datastructures.impl.Slideshow;
import de.fhkiel.fotomanager.model.mediatypes.impl.Photo;
import de.fhkiel.fotomanager.view.datastructures.AllMediaView;
import de.fhkiel.fotomanager.view.datastructures.SlideshowCreationView;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.xml.transform.TransformerException;
import java.util.Stack;

/**
 * The type Slideshow creation view controller.
 */
public class SlideshowCreationViewController extends ViewController<SlideshowCreationView> {
    /**
     * The Slideshow creation view controller instance.
     */
    public static SlideshowCreationViewController instance;

    private SlideshowCreationViewController() {}

    /**
     * Gets the instance of the SlideshowCreationViewController.
     *
     * @return the instance
     */
    public static SlideshowCreationViewController get() {
        if (instance == null) {
            instance = new SlideshowCreationViewController();
        }
        return instance;
    }


    /**
     * Create view slideshow creation view.
     *
     * @param stage   the stage
     * @param history the history
     * @return the slideshow creation view
     */
    public SlideshowCreationView createView(Stage stage, Stack<Scene> history) {
        this.history = history;
        this.stage = stage;
        view = new SlideshowCreationView(stage);

        view.getBackButton().setOnAction(event -> goBack());
        view.getCreateSlideshowButton().setOnAction(event -> {
            try {
                createNewSlideshow();
            } catch (TransformerException e) {
                throw new RuntimeException(e);
            }
        });
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
     * Create new slideshow.
     *
     * @throws TransformerException the transformer exception
     */
    public void createNewSlideshow() throws TransformerException {
        String slideshowName = view.getSlideshowNameTextField().getText();
        String secondsPerImageString = view.getSlideshowSecondsPerImageTextField().getText();
        view.getErrorLabel().setText("");

        try {
            if (!isNumeric(secondsPerImageString)) {
                throw new IllegalArgumentException("'" + secondsPerImageString + "' is not a valid number.");
            }
            int secondsPerImage = Integer.parseInt(secondsPerImageString);

            Slideshow slideshow = SlideshowController.get().createSlideshow(slideshowName, secondsPerImage);
            PhotoManagerController.get().addDataStructure(slideshow);

            for (int i = 0; i < view.getSelectedMedia().size(); i++) {
                if (view.getSelectedMedia().get(i) instanceof Photo) {
                    slideshow.addMedia((Photo) view.getSelectedMedia().get(i));
                }
            }

            // save the slideshow to the XML file
            XMLController.get().saveSlideshowToXML(slideshow);
            goBackAndRebuild(SlideshowMenuViewController.get().createView(stage, history).getScene());
        } catch (Exception e) {
            view.getErrorLabel().setText(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Checks if a string is numeric.
     *
     * @param str the string
     * @return true if the string is numeric
     */
    private boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
