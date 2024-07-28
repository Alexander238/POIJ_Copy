package de.fhkiel.fotomanager.view.mediatypes;

import de.fhkiel.fotomanager.model.mediatypes.Media;
import de.fhkiel.fotomanager.model.mediatypes.impl.Photo;
import de.fhkiel.fotomanager.model.mediatypes.impl.Video;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import lombok.Data;

import java.io.File;
import java.util.List;
import java.util.Objects;

/**
 * The type Photo view.
 */
@Data
public class MediaDetailView {
    /**
     * The Photos.
     */
    private final List<Media> mediaList;
    /**
     * The Back button.
     */
    private Button backButton;
    /**
     * The button to zoom in.
     */
    private Button zoomInButton;
    /**
     * The button to zoom out.
     */
    private Button zoomOutButton;
    /**
     * The button to rotate the image clockwise.
     */
    private Button rotateClockwiseButton;
    /**
     * The button to rotate the image counter-clockwise.
     */
    private Button rotateCounterClockwiseButton;
    /**
     * The button to show the next image.
     */
    private Button nextButton;
    /**
     * The button to show the previous image.
     */
    private Button prevButton;
    /**
     * The Attributes button.
     */
    private Button attributesButton;
    /**
     * The Delete button.
     */
    private Button deleteButton;
    /**
     * The play button for videos.
     */
    private Button playButton;
    /**
     * The pause button for videos.
     */
    private Button pauseButton;
    /**
     * The current time label for videos.
     */
    private Label currentTimeLabel;
    /**
     * The total time label for videos.
     */
    private Label totalTimeLabel;
    /**
     * The Image view.
     */
    private ImageView imageView;
    /**
     * The standard Zoom factor.
     */
    private double zoomFactor = 1.0;
    /**
     * The current media.
     */
    private Media currentMedia;
    /**
     * The Scene to display the image.
     */
    private Scene scene;
    /**
     * The Scene to display on the stage.
     */
    private Stage stage;
    /**
     * The Media player for videos.
     */
    private MediaPlayer mediaPlayer;
    /**
     * The media view for videos.
     */
    private MediaView mediaView;
    /**
     * The slider for videos.
     */
    private Slider timeSlider;

    /**
     * Instantiates a new Photo view.
     *
     * @param stage     the stage
     * @param mediaList the mediaList
     * @param media     the media
     */
    public MediaDetailView(Stage stage, List<Media> mediaList, Media media) {
        this.mediaList = mediaList;
        this.stage = stage;
        this.currentMedia = media;
        backButton = new Button("Close");
        deleteButton = new Button("Delete");
        zoomInButton = new Button("Zoom In");
        zoomOutButton = new Button("Zoom Out");
        nextButton = new Button(">");
        prevButton = new Button("<");
        attributesButton = new Button("Attributes");
        rotateClockwiseButton = new Button("Rotate >");
        rotateCounterClockwiseButton = new Button("< Rotate");
        playButton = new Button("Play");
        pauseButton = new Button("Pause");
        currentTimeLabel = new Label("0:00");
        totalTimeLabel = new Label("0:00");
        timeSlider = new Slider();
        initialize();
    }

    /**
     * Initialize the photo view.
     *
     * @param images       the images
     * @param currentIndex the current index
     * @param allTags      the all tags
     */
    public void initialize() {
        stage.setTitle(currentMedia.getName());

        BorderPane root = new BorderPane();

        File file = new File(currentMedia.getFile());
        Image image = new Image(file.toURI().toString());
        imageView = new ImageView(image);

        HBox topControls = new HBox(10);
        topControls.setAlignment(Pos.CENTER);
        topControls.setPadding(new Insets(10));
        topControls.getChildren().add(backButton);

        HBox navControls = new HBox(10);
        navControls.setAlignment(Pos.CENTER);
        navControls.setPadding(new Insets(10));
        navControls.getChildren().addAll(prevButton, nextButton);

        root.setBottom(navControls);

        if (currentMedia instanceof Photo) topControls.getChildren().add(rotateCounterClockwiseButton);
        if (currentMedia instanceof Photo) topControls.getChildren().add(rotateClockwiseButton);
        if (currentMedia instanceof Photo) topControls.getChildren().add(zoomInButton);
        if (currentMedia instanceof Photo) topControls.getChildren().add(zoomOutButton);
        if (currentMedia instanceof Video) topControls.getChildren().add(playButton);
        if (currentMedia instanceof Video) topControls.getChildren().add(pauseButton);
        if (currentMedia instanceof Video) topControls.getChildren().add(currentTimeLabel);
        if (currentMedia instanceof Video) topControls.getChildren().add(timeSlider);
        if (currentMedia instanceof Video) topControls.getChildren().add(totalTimeLabel);

        topControls.getChildren().add(attributesButton);
        topControls.getChildren().add(deleteButton);

        root.setTop(topControls);

        if (currentMedia instanceof Video) {
            javafx.scene.media.Media media = new javafx.scene.media.Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaView = new MediaView(mediaPlayer);
            mediaView.setPreserveRatio(true);

            timeSlider.setMin(0);
            timeSlider.setValue(0);

            mediaView.fitWidthProperty().bind(root.widthProperty());
            mediaView.fitHeightProperty()
                    .bind(root.heightProperty()
                            .subtract(topControls.heightProperty())
                            .subtract(20)
                            .subtract(navControls.heightProperty())
                    );

            VBox.setVgrow(mediaView, Priority.ALWAYS);
            root.setCenter(mediaView);
        } else {
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(800);
            imageView.setFitHeight(600);
            imageView.setRotate(currentMedia.getOrientation().toAngle());

            StackPane imageContainer = new StackPane(imageView);
            imageContainer.setPadding(new Insets(10));

            root.setCenter(imageContainer);
        }

        scene = new Scene(root, 800, 600);

        String css = Objects.requireNonNull(getClass().getResource("/darkTheme.css")).toExternalForm();
        scene.getStylesheets().add(css);
    }
}
