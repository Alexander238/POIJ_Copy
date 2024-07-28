package de.fhkiel.fotomanager.controller.viewController.mediatypes;

import de.fhkiel.fotomanager.controller.modelController.PhotoManagerController;
import de.fhkiel.fotomanager.controller.modelController.XMLController;
import de.fhkiel.fotomanager.controller.viewController.ViewController;
import de.fhkiel.fotomanager.controller.viewController.components.ShowAlert;
import de.fhkiel.fotomanager.controller.viewController.datastructures.AllMediaViewController;
import de.fhkiel.fotomanager.model.datastructures.DataStructure;
import de.fhkiel.fotomanager.model.datastructures.impl.Folder;
import de.fhkiel.fotomanager.model.mediatypes.Media;
import de.fhkiel.fotomanager.model.mediatypes.impl.Photo;
import de.fhkiel.fotomanager.model.mediatypes.impl.Video;
import de.fhkiel.fotomanager.view.mediatypes.MediaDetailView;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

/**
 * The type Photo view controller.
 */
public class MediaDetailViewController extends ViewController<MediaDetailView> {
    /**
     * The Photo view controller instance.
     */
    public static MediaDetailViewController instance;

    /**
     * Zoom range
     */
    private final int zoomRange = 1;

    /**
     * Zoom
     */
    private int currentZoom = 0;

    /**
     * The data structure, used for deletion.
     */
    private DataStructure<Media> dataStructure;

    private MediaDetailViewController() {}

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static MediaDetailViewController get() {
        if (instance == null) {
            instance = new MediaDetailViewController();
        }
        return instance;
    }

    /**
     * Create the photo view.
     *
     * @param stage         the stage
     * @param history       the history
     * @param mediaList     the media list
     * @param dataStructure the data structure
     * @param media         the media
     * @return the PhotoView instance
     */
    public MediaDetailView createView(Stage stage, Stack<Scene> history, List<Media> mediaList, DataStructure<Media> dataStructure, Media media) {
        this.stage = stage;
        this.history = history;
        this.dataStructure = dataStructure;
        showMediaView(mediaList, media);
        return view;
    }

    /**
     * Show photo view.
     * Shows the photo view with the given mediaList and index.
     *
     * @param mediaList the mediaList
     * @param media     the media
     */
    private void showMediaView(List<Media> mediaList, Media media) {
        view = new MediaDetailView(stage, mediaList, media);

        if (media instanceof Video) {
            initializeVideoView();
        } else {
            initializePhotoView();
        }
        setVisibilityOfButtons(media);
        setActions();
    }

    /**
     * Sets the visibility of the buttons.
     */
    private void setVisibilityOfButtons(Media media) {
        boolean videoView = (media instanceof Video);
        view.getPauseButton().setVisible(videoView);
        view.getPlayButton().setVisible(videoView);
        view.getTotalTimeLabel().setVisible(videoView);
        view.getCurrentTimeLabel().setVisible(videoView);
        view.getTimeSlider().setVisible(videoView);
        view.getRotateClockwiseButton().setVisible(!videoView);
        view.getRotateCounterClockwiseButton().setVisible(!videoView);
        view.getZoomInButton().setVisible(!videoView);
        view.getZoomOutButton().setVisible(!videoView);
    }

    /**
     * Initialize video buttons and actions.
     */
    private void initializeVideoView() {
        // Update slider and currentTimeLabel as video progresses
        view.getMediaPlayer().currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            if (!view.getTimeSlider().isValueChanging()) {
                view.getTimeSlider().setValue(newTime.toMillis());
                view.getCurrentTimeLabel().setText(formatTime(newTime.toMillis()));
            }
        });

        // Seek video when slider value changes
        view.getTimeSlider().valueProperty().addListener((obs, oldVal, newVal) -> {
            if (view.getTimeSlider().isValueChanging()) {
                view.getMediaPlayer().seek(view.getMediaPlayer().getTotalDuration().multiply(newVal.doubleValue() / view.getTimeSlider().getMax()));
            }
        });

        view.getMediaPlayer().setOnReady(() -> {
            double totalTimeMillis = view.getMediaPlayer().getTotalDuration().toMillis();
            view.getTimeSlider().setMax(totalTimeMillis);
            view.getTotalTimeLabel().setText(formatTime(totalTimeMillis));
            view.getPlayButton().setVisible(true);
            view.getPauseButton().setVisible(true);
        });

        view.getMediaPlayer().setOnEndOfMedia(() -> {
            view.getTimeSlider().setValue(view.getTimeSlider().getMax());
            view.getPlayButton().setDisable(false);
            view.getPauseButton().setDisable(true);
        });

        view.getPlayButton().setOnAction(event -> {
            view.getMediaPlayer().play();
            view.getPlayButton().setDisable(true);
            view.getPauseButton().setDisable(false);
        });

        view.getPauseButton().setOnAction(event -> {
            view.getMediaPlayer().pause();
            view.getPlayButton().setDisable(false);
            view.getPauseButton().setDisable(true);
        });
    }

    /**
     * Initialize photo buttons and actions.
     */
    private void initializePhotoView() {
        view.getZoomInButton().setOnAction(event -> zoomIn());
        view.getZoomOutButton().setOnAction(event -> zoomOut());
        view.getRotateClockwiseButton().setOnAction(event -> rotateClockwise());
        view.getRotateCounterClockwiseButton().setOnAction(event -> rotateCounterClockwise());
    }

    /**
     * Sets the actions for the buttons.
     */
    private void setActions() {
        view.getBackButton().setOnAction(event -> {
            if(view.getMediaPlayer() != null) view.getMediaPlayer().stop();
            if (view.getPauseButton() != null) view.getPauseButton().setDisable(true);
            goBackAndRebuild(AllMediaViewController.get().createView(stage, history, false, dataStructure).getScene());
        });
        view.getNextButton().setOnAction(event -> showNextImage());
        view.getPrevButton().setOnAction(event -> showPrevImage());
        view.getDeleteButton().setOnAction(event -> deleteMedia(view.getCurrentMedia()));
        view.getAttributesButton().setOnAction(event -> showAttributesView(stage));
    }

    /**
     * Rotates the image clockwise.
     */
    private void rotateClockwise() {
        view.getCurrentMedia().rotateClockwise();
        XMLController.get().savePhotoToXML((Photo) view.getCurrentMedia(), true);
        replaceScene(MediaDetailViewController.get().createView(stage, history, view.getMediaList(), dataStructure, view.getCurrentMedia()).getScene());
    }

    /**
     * Rotates the image counter-clockwise.
     */
    private void rotateCounterClockwise() {
        view.getCurrentMedia().rotateCounterClockwise();
        XMLController.get().savePhotoToXML((Photo) view.getCurrentMedia(), true);
        replaceScene(MediaDetailViewController.get().createView(stage, history, view.getMediaList(), dataStructure, view.getCurrentMedia()).getScene());
    }

    /**
     * Deletes the current media.
     *
     * @param currentMedia the current media
     */
    private void deleteMedia(Media currentMedia) {
        if (ShowAlert.showConfirmationAlert("Delete photo", "Are you sure you want to delete this photo?")) {
            if (dataStructure == null) {
                fullDeleteMedia(currentMedia);
            } else {
                removeMediaFromDataStructure(currentMedia);
            }
        }
    }

    /**
     * Deletes the photo from the list and the file system.
     *
     * @param currentMedia the current media
     */
    private void fullDeleteMedia(Media currentMedia) {
        if (currentMedia instanceof Photo) PhotoManagerController.get().deletePhoto((Photo) currentMedia);
        if (currentMedia instanceof Video) PhotoManagerController.get().deleteVideo((Video) currentMedia);
        goBackAndRebuild(AllMediaViewController.get().createView(stage, history, false, null).getScene());
    }

    /**
     * Removes the media from the data structure.
     *
     * @param currentMedia the current media
     */
    private void removeMediaFromDataStructure(Media currentMedia) {
        if (dataStructure instanceof Folder) {
            fullDeleteMedia(currentMedia);
        } else {
            PhotoManagerController.get().removeMediaFromDataStructure(currentMedia, dataStructure);
            goBackAndRebuild(AllMediaViewController.get().createView(stage, history, false, dataStructure).getScene());
        }
    }

    /**
     * Zooms the image by the set size.
     */
    private void zoomIn() {
        if (currentZoom > zoomRange) {
            return;
        }
        currentZoom++;

        ((Photo) view.getCurrentMedia()).zoomIn();
        view.getImageView().setFitWidth(view.getImageView().getFitWidth() * (1 + ((Photo) view.getCurrentMedia()).getZOOM_STEP_SIZE()));
        view.getImageView().setFitHeight(view.getImageView().getFitHeight() * (1 + ((Photo) view.getCurrentMedia()).getZOOM_STEP_SIZE()));

        System.out.println("Zoomed in with factor: " + ((Photo) view.getCurrentMedia()).getZoomFactor());
    }

    /**
     * Zooms the image out by the set size.
     */
    private void zoomOut() {
        if (currentZoom < -zoomRange) {
            return;
        }
        currentZoom--;

        ((Photo) view.getCurrentMedia()).zoomOut();
        view.getImageView().setFitWidth(view.getImageView().getFitWidth() * (1 - ((Photo) view.getCurrentMedia()).getZOOM_STEP_SIZE()));
        view.getImageView().setFitHeight(view.getImageView().getFitHeight() * (1 - ((Photo) view.getCurrentMedia()).getZOOM_STEP_SIZE()));

        System.out.println("Zoomed out with factor: " + ((Photo) view.getCurrentMedia()).getZoomFactor());
    }

    /**
     * Shows the next image in the list.
     */
    private void showNextImage() {
        int currentIndex = view.getMediaList().indexOf(view.getCurrentMedia());

        if (currentIndex < view.getMediaList().size() - 1) {
            view.setCurrentMedia(view.getMediaList().get(currentIndex + 1));
            updateImage();
        } else {
            view.setCurrentMedia(view.getMediaList().getFirst());
            updateImage();
        }
        replaceScene(MediaDetailViewController.get().createView(
                stage,
                history,
                view.getMediaList(),
                dataStructure,
                view.getCurrentMedia()
        ).getScene());
    }

    /**
     * Shows the previous image in the list.
     */
    private void showPrevImage() {
        int currentIndex = view.getMediaList().indexOf(view.getCurrentMedia());

        if (currentIndex > 0) {
            view.setCurrentMedia(view.getMediaList().get(currentIndex - 1));
            updateImage();
        } else {
            view.setCurrentMedia(view.getMediaList().getLast());
            updateImage();
        }
        replaceScene(MediaDetailViewController.get().createView(
                stage,
                history,
                view.getMediaList(),
                dataStructure,
                view.getCurrentMedia()
        ).getScene());
    }

    /**
     * Show attributes view.
     * Shows the attributes view for the current photo.
     *
     * @param stage the stage
     */
    private void showAttributesView(Stage stage) {
        switchScene(MediaAttributesViewController.get().createView(
                history,
                view.getCurrentMedia(),
                stage,
                view.getMediaList(),
                dataStructure
        ).getScene());
    }

    /**
     * Updates the image view with the current image.
     */
    private void updateImage() {
        File file = new File(view.getCurrentMedia().getFile());
        Image image = new Image(file.toURI().toString());
        view.getImageView().setImage(image);
        stage.setTitle(view.getCurrentMedia().getName());

        view.getImageView().setFitWidth(800 * view.getZoomFactor());
        view.getImageView().setFitHeight(600 * view.getZoomFactor());
    }

        /**
     * Format time string.
     * Formats the time in milliseconds to a string in the format "mm:ss".
     *
     * @param timeMillis the time in milliseconds
     * @return the formatted time string
     */
    private String formatTime(double timeMillis) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes((long) timeMillis);
        long seconds = TimeUnit.MILLISECONDS.toSeconds((long) timeMillis) % 60;
        return String.format("%d:%02d", minutes, seconds);
    }
}
