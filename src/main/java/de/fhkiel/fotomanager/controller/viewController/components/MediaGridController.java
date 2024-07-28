package de.fhkiel.fotomanager.controller.viewController.components;

import de.fhkiel.fotomanager.controller.viewController.ViewController;
import de.fhkiel.fotomanager.model.mediatypes.Media;
import de.fhkiel.fotomanager.view.components.MediaGrid;
import de.fhkiel.fotomanager.view.components.MediaPreview;
import javafx.geometry.Pos;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Media grid controller.
 */
public class MediaGridController extends ViewController<MediaGrid> {
    /**
     * The media grid controller instance.
     */
    private static MediaGridController instance;

    private MediaGridController() {}

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static MediaGridController get() {
        if (instance == null) {
            instance = new MediaGridController();
        }
        return instance;
    }

    /**
     * Build the grid pane for the media grid.
     *
     * @param mediaGrid the media grid
     * @return the grid pane
     */
    public FlowPane buildMediaGrid(MediaGrid mediaGrid) {
        FlowPane flowPane = mediaGrid.createMediaFlowPane();
        for (int i = 0; i < mediaGrid.getDisplayedMedia().size(); i++) {
            Media mediaFile = mediaGrid.getDisplayedMedia().get(i);

            MediaPreview mediaPreview = new MediaPreview(mediaFile);
            mediaGrid.getMediaPreviews().add(mediaPreview);
            mediaGrid.getDisplayedMediaPreviews().add(mediaPreview);

            VBox vbox = new VBox(5);
            vbox.setAlignment(Pos.CENTER);
            vbox.getChildren().add(mediaPreview.getVBox());

            flowPane.getChildren().add(vbox);
        }
        return flowPane;
    }

    /**
     * Adjust the media previews.
     *
     * @param mediaGrid the media grid
     */
    public void adjustMediaPreviews(MediaGrid mediaGrid) {
        List<MediaPreview> newMediaPreviews = new ArrayList<>();
        for (MediaPreview mediaPreview : mediaGrid.getMediaPreviews()) {
            for (Media media : mediaGrid.getDisplayedMedia()) {
                if (mediaPreview.getMedia().equals(media)) {
                    newMediaPreviews.add(mediaPreview);
                    break;
                }
            }
        }
        mediaGrid.setDisplayedMediaPreviews(newMediaPreviews);
    }

    /**
     * Update the media grid.
     *
     * @param mediaGrid the media grid to update
     * @return the updated grid pane
     */
    public FlowPane updateMediaGrid(MediaGrid mediaGrid) {
        FlowPane newFLowPane = mediaGrid.createMediaFlowPane();
        for (int i = 0; i < mediaGrid.getDisplayedMediaPreviews().size(); i++) {
            if (mediaGrid.getDisplayedMediaPreviews().get(i).isSelected()) {
                mediaGrid.getDisplayedMediaPreviews().get(i).getImage().setStyle("-fx-border-color: #5db9d7; -fx-border-style: solid; -fx-border-width: 2px;");
            } else {
                mediaGrid.getDisplayedMediaPreviews().get(i).getImage().setStyle("-fx-border-color: transparent; -fx-border-style: solid; -fx-border-width: 2px;");
            }

            VBox vbox = new VBox(5);
            vbox.setAlignment(Pos.CENTER);
            vbox.getChildren().add(mediaGrid.getDisplayedMediaPreviews().get(i).getVBox());

            newFLowPane.getChildren().add(vbox);
        }
        return newFLowPane;
    }
}
