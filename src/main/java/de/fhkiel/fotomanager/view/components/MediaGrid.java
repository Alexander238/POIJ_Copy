package de.fhkiel.fotomanager.view.components;

import de.fhkiel.fotomanager.model.mediatypes.Media;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Media grid.
 */
@Getter
public class MediaGrid {
    /**
     * The Grid pane.
     */
    private FlowPane flowPane;
    /**
     * The Displayed media.
     */
    @Setter
    private List<Media> displayedMedia;
    /**
     * The Scroll pane.
     */
    private ScrollPane scrollPane;
    /**
     * The Media previews.
     */
    @Setter
    private List<MediaPreview> mediaPreviews = new ArrayList<>();
    /**
     * The Displayed media previews.
     */
    @Setter
    private List<MediaPreview> displayedMediaPreviews = new ArrayList<>();

    /**
     * Instantiates a new Media grid.
     *
     * @param displayedMedia the displayed media
     */
    public MediaGrid(List<Media> displayedMedia) {
        this.displayedMedia = displayedMedia;
    }

    /**
     * Create media grid pane.
     *
     * @return the grid pane
     */
    public FlowPane createMediaFlowPane() {
        if (this.flowPane == null) {
            this.flowPane = new FlowPane(10, 10);
        } else {
            this.flowPane.getChildren().clear();
        }
        flowPane.setPadding(new Insets(10));

        scrollPane = new ScrollPane();
        scrollPane.setContent(flowPane);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        return flowPane;
    }
}
