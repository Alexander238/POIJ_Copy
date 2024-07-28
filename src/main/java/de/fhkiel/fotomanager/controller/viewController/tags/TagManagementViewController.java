package de.fhkiel.fotomanager.controller.viewController.tags;

import de.fhkiel.fotomanager.controller.modelController.PhotoManagerController;
import de.fhkiel.fotomanager.controller.modelController.TagController;
import de.fhkiel.fotomanager.controller.modelController.TagListController;
import de.fhkiel.fotomanager.controller.modelController.XMLController;
import de.fhkiel.fotomanager.controller.viewController.ViewController;
import de.fhkiel.fotomanager.controller.viewController.ViewInteractable;
import de.fhkiel.fotomanager.controller.viewController.components.ShowAlert;
import de.fhkiel.fotomanager.model.tags.Tag;
import de.fhkiel.fotomanager.model.tags.TagType;
import de.fhkiel.fotomanager.view.tags.TagManagementView;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Stack;

/**
 * The type Tag management view controller.
 */
public class TagManagementViewController extends ViewController<TagManagementView> implements ViewInteractable<TagManagementView> {
    /**
     * The Tag management view controller instance.
     */
    public static TagManagementViewController instance;

    private TagManagementViewController() {}

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static TagManagementViewController get() {
        if (instance == null) {
            instance = new TagManagementViewController();
        }
        return instance;
    }

    /**
     * Create tag management view.
     *
     * @param stage the stage
     * @param history the history
     * @return the TagManagementView instance
     */
    @Override
    public TagManagementView createView(Stage stage, Stack<Scene> history) {
        this.history = history;
        this.stage = stage;
        showTagManagementView();
        return view;
    }

    /**
     * Show tag management view.
     */
    private void showTagManagementView() {
        view = new TagManagementView(stage);

        view.getTagTypeComboBox().setItems(FXCollections.observableArrayList(
                TagType.ADJECTIVE, TagType.PERSON, TagType.LOCATION, TagType.INDEX
        ));
        view.getTagListView().setItems(PhotoManagerController.get().getPhotoManager().getTagList().getObservableTags());
        view.getTagListView().setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Tag tag, boolean empty) {
                super.updateItem(tag, empty);
                if (empty || tag == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Text text = new Text(tag.getName());
                    text.setFill(tag.getColor());
                    setGraphic(text);
                }
            }
        });

        view.getTabPane().getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab == view.getAllTab()) {
                view.getTagListView().setItems(PhotoManagerController.get().getPhotoManager().getTagList().getObservableTags());
            } else if (newTab == view.getAdjectiveTab()) {
                view.getTagListView().setItems(PhotoManagerController.get().getPhotoManager().getTagList().getObservableAdjectiveTags());
            } else if (newTab == view.getLocationTab()) {
                view.getTagListView().setItems(PhotoManagerController.get().getPhotoManager().getTagList().getObservableLocationTags());
            } else if (newTab == view.getPersonTab()) {
                view.getTagListView().setItems(PhotoManagerController.get().getPhotoManager().getTagList().getObservablePersonTags());
            } else if (newTab == view.getIndexTab()) {
                view.getTagListView().setItems(PhotoManagerController.get().getPhotoManager().getTagList().getObservableIndexTags());
            }
        });

        updateTagList();

        // Set up event handlers
        view.getBackButton().setOnAction(event -> goBack());
        view.getAddButton().setOnAction(event -> addTag());
        view.getDeleteButton().setOnAction(event -> deleteTag());
        view.getChangeButton().setOnAction(event -> changeTag());
    }

    /**
     * Add tag.
     * Adds a new tag to the tag list.
     */
    private void addTag() {
        view.getErrorLabel().setText("");
        String name = view.getNameField().getText();
        if (name.isEmpty()) {
            view.getErrorLabel().setText("Tag name cannot be empty.");
            return;
        }
        Color color = view.getColorPicker().getValue();
        TagType tagType = view.getTagTypeComboBox().getValue();
        if (tagType == null) {
            view.getErrorLabel().setText("Tag type must be selected.");
            return;
        }

        try {
            Tag newTag = TagController.get().createTag(tagType, name, color);
            PhotoManagerController.get().addTag(newTag);
            view.getNameField().clear();
            view.getTagTypeComboBox().getSelectionModel().clearSelection();
            saveTags();
        } catch (IllegalArgumentException e) {
            view.getErrorLabel().setText(e.getMessage());
        }
    }

        /**
     * Save tags.
     * Saves the tag list to an XML file.
     */
    private void saveTags() {
        XMLController.get().saveTagsToXML(PhotoManagerController.get().getPhotoManager().getTagList());
        updateTagList();
    }

        /**
     * Update tag list.
     * Updates the tag list view.
     */
    private void updateTagList() {
        view.getTagListView().setItems(PhotoManagerController.get().getPhotoManager().getTagList().getObservableTags());
    }

    /**
     * Delete tag.
     * Deletes the selected tag from the tag list.
     */
    private void deleteTag() {
        Tag selectedTag = view.getTagListView().getSelectionModel().getSelectedItem();
        if (selectedTag != null) {
            TagListController.get().removeTag(PhotoManagerController.get().getPhotoManager().getTagList(), selectedTag);
        } else {
            ShowAlert.showAlert("Error", "No tag selected.");
        }

        PhotoManagerController.get().removeTag(selectedTag);

        saveTags();
    }

    /**
     * Change tag.
     * Changes the name and color of the selected tag.
     */
    private void changeTag() {
        Tag selectedTag = view.getTagListView().getSelectionModel().getSelectedItem();
        if (selectedTag != null) {
            // Remove old version from PhotoManagerController
            PhotoManagerController.get().removeTag(selectedTag);

            String newName = view.getNameField().getText();
            if (!newName.isEmpty()) {
                selectedTag.rename(newName);
            }
            TagController.get().changeTagColor(selectedTag, view.getColorPicker().getValue());
            TagController.get().changeTagType(selectedTag, view.getTagTypeComboBox().getValue());

            view.getTagListView().refresh();
            view.getNameField().clear();
        } else {
            ShowAlert.showAlert("Error", "No tag selected.");
        }

        // Update add new Version back to PhotoManagerController.
        PhotoManagerController.get().addTag(selectedTag);

        saveTags();
    }
}
