package de.fhkiel.fotomanager.controller.viewController.mediatypes;

import de.fhkiel.fotomanager.controller.modelController.MediaController;
import de.fhkiel.fotomanager.controller.modelController.PhotoManagerController;
import de.fhkiel.fotomanager.controller.modelController.TagListController;
import de.fhkiel.fotomanager.controller.modelController.XMLController;
import de.fhkiel.fotomanager.controller.viewController.ViewController;
import de.fhkiel.fotomanager.model.datastructures.DataStructure;
import de.fhkiel.fotomanager.model.datastructures.impl.Slideshow;
import de.fhkiel.fotomanager.model.mediatypes.Media;
import de.fhkiel.fotomanager.model.mediatypes.Orientation;
import de.fhkiel.fotomanager.model.mediatypes.Rating;
import de.fhkiel.fotomanager.model.mediatypes.Resolution;
import de.fhkiel.fotomanager.model.mediatypes.impl.Photo;
import de.fhkiel.fotomanager.model.mediatypes.impl.Video;
import de.fhkiel.fotomanager.model.taglists.TagList;
import de.fhkiel.fotomanager.model.tags.Tag;
import de.fhkiel.fotomanager.model.tags.TagType;
import de.fhkiel.fotomanager.view.mediatypes.MediaAttributesView;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * The type Attributes view controller.
 */
public class MediaAttributesViewController extends ViewController<MediaAttributesView> {
    /**
     * The folder view controller instance.
     */
    public static MediaAttributesViewController instance;

    private MediaAttributesViewController() {}

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static MediaAttributesViewController get() {
        if (instance == null) {
            instance = new MediaAttributesViewController();
        }
        return instance;
    }

    /**
     * Create view photo view.
     *
     * @param history the history
     * @param media   the media
     * @param stage   the stage
     * @return the photo view
     */
    public MediaAttributesView createView(Stack<Scene> history, Media media, Stage stage, List<Media> mediaList, DataStructure dataStructure) {
        this.history = history;
        this.stage = stage;
        view = new MediaAttributesView(stage, mediaList, dataStructure);

        // initialize and set contents
        view.initialize(media);
        view.getNameField().setText(media.getName());
        view.getResolutionField().setText(media.getResolution().toString());
        view.getPathField().setText(media.getFile());
        view.getDateLabel().setText(media.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        view.getDescriptionArea().setText(media.getDescription());
        view.getPrivateCheckBox().setSelected(media.isPrivate());

        if (media instanceof Video) view.getDurationField().setText(((Video) media).getDuration() + "s");

        for (Tag tag : media.getTags().getTags()) {
            view.getSelectedTags().add(tag);
        }
        for (Rating rating : Rating.values()) {
            view.getRatingChoiceBox().getItems().add(rating);
        }
        view.getRatingChoiceBox().setValue(media.getRating());

        view.getTagTypeComboBox().setItems(FXCollections.observableArrayList(
                TagType.ADJECTIVE, TagType.PERSON, TagType.LOCATION, TagType.INDEX));

        setActions(media);

        loadTagsFromXML();

        if (media instanceof Photo) {
            setSlideshowsComboBoxConverter();
            loadSlideshowsFromXML();

            String slideshowName = XMLController.get().getSlideshowNameOfPhotoInsideSlideshow((Photo) media);
            if (slideshowName != null) {
                for (Slideshow slideshow : view.getSlideshowsComboBox().getItems()) {
                    if (slideshow != null && slideshow.getName().equals(slideshowName)) {
                        view.getSlideshowsComboBox().setValue(slideshow);
                        view.setCurrentSlideshow(slideshow);
                    }
                }
            }
        }
        return view;
    }

    /**
     * Set button actions.
     * @param media the media
     */
    private void setActions(Media media) {
        // back button
        view.getBackButton().setOnAction(event -> goBack());

        // Save button
        view.getSaveButton().setOnAction(event -> {
            String file = view.getPathField().getText();
            String mediaName = view.getNameField().getText();

            String mediaDescription = view.getDescriptionArea().getText();
            boolean mediaIsPrivate = view.getPrivateCheckBox().isSelected();
            Rating mediaRating = view.getRatingChoiceBox().getValue();
            String mediaResolution = view.getResolutionField().getText();
            TagList selectedTagList = TagListController.get().createTagList(view.getSelectedTags());

            Media updatedMedia = null;

            if (media instanceof Photo) {
                updatedMedia = MediaController.get().createPhoto(file, mediaName, media.getDate(), mediaDescription, mediaIsPrivate,
                    selectedTagList, ((Photo) media).getZoomFactor(), mediaRating, media.getOrientation(), media.getResolution());

                Slideshow slideshow = view.getSlideshowsComboBox().getValue();

                if (slideshow != null) {
                    XMLController.get().deleteSlideshowFromXML(slideshow);

                    slideshow.removeMedia((Photo) media);
                    slideshow.addMedia((Photo) updatedMedia);

                    XMLController.get().saveSlideshowToXML(slideshow);
                }
            } else if (media instanceof Video) {
                // TODO: anpassen
                updatedMedia = MediaController.get().createVideo(file, mediaName, media.getDate(), mediaDescription, mediaIsPrivate,
                    selectedTagList, 1, mediaRating, Orientation.D0,
                    new Resolution(Long.parseLong(mediaResolution.split("x")[0]),
                            Long.parseLong(mediaResolution.split("x")[1])));
            }

            XMLController.get().updateMediaFromAllXML(updatedMedia);
            PhotoManagerController.get().replaceOldMediaInEverything(updatedMedia);
            goBackAndRebuild(MediaDetailViewController.get().createView(stage, history, view.getMediaList(), view.getDataStructure(), updatedMedia).getScene());
        });

        // add tag button
        view.getAddTagButton().setOnAction(event -> {
            String newTagName = view.getNewTagField().getText();
            TagType tagType = view.getTagTypeComboBox().getValue();
            if (!newTagName.isEmpty() && tagType != null) {
                Tag newTag = new Tag(newTagName, Color.BLACK, tagType); // Default color
                view.getAllTags().addTag(newTag);
                view.getSelectedTags().add(newTag);
                view.getNewTagField().clear();

                XMLController.get().saveTagsToXML(view.getAllTags());
                loadTagsFromXML();
            } else {
                Tag selectedTag = view.getTagsComboBox().getValue();

                for (Tag tag : view.getSelectedTags()) {
                    if (tag.getName().equals(selectedTag.getName())) {
                        return;
                    }
                }
                view.getSelectedTags().add(selectedTag);
            }
        });

        view.getRemoveTagButton().setOnAction(event -> {
            Tag selectedTag = view.getSelectedTagsListView().getSelectionModel().getSelectedItem();
            if (selectedTag != null) {
                view.getSelectedTags().remove(selectedTag);
            }
        });
    }

    /**
     * Load tags from XML.
     */
    private void loadTagsFromXML() {
        view.setAllTags(PhotoManagerController.get().getTagList());
        view.getTagsComboBox().setItems(FXCollections.observableArrayList(view.getAllTags().getTags()));
    }

    /**
     *  Sets the String converter and cell factory for the slideshows ComboBox and its elements.
     */
    private void setSlideshowsComboBoxConverter() {
        view.getSlideshowsComboBox().setConverter(new StringConverter<>() {
            @Override
            public String toString(Slideshow slideshow) {
                return slideshow != null ? slideshow.getName() : "None";
            }

            @Override
            public Slideshow fromString(String string) {
                if ("- NONE -".equals(string)) {
                    return null;
                }
                return view.getSlideshowsComboBox().getItems().stream()
                        .filter(slideshow -> slideshow.getName().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });

        view.getSlideshowsComboBox().setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Slideshow slideshow, boolean empty) {
                super.updateItem(slideshow, empty);
                if (empty || slideshow == null) {
                    setText("- NONE -");
                } else {
                    setText(slideshow.getName());
                }
            }
        });
    }

    /**
     * Load slideshows from XML and add a "None" option to the list.
     */
    private void loadSlideshowsFromXML() {
        List<Slideshow> allSlideshows = new ArrayList<>();
        allSlideshows.add(null); // Add a "None" option
        allSlideshows.addAll(PhotoManagerController.get().getSlideshows());

        view.getSlideshowsComboBox().setItems(FXCollections.observableArrayList(allSlideshows));
    }
}
