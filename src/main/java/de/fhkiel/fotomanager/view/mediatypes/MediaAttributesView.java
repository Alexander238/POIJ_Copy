package de.fhkiel.fotomanager.view.mediatypes;

import de.fhkiel.fotomanager.model.datastructures.DataStructure;
import de.fhkiel.fotomanager.model.datastructures.impl.Slideshow;
import de.fhkiel.fotomanager.model.mediatypes.Media;
import de.fhkiel.fotomanager.model.mediatypes.Rating;
import de.fhkiel.fotomanager.model.mediatypes.impl.Photo;
import de.fhkiel.fotomanager.model.mediatypes.impl.Video;
import de.fhkiel.fotomanager.model.taglists.TagList;
import de.fhkiel.fotomanager.model.tags.Tag;
import de.fhkiel.fotomanager.model.tags.TagType;
import de.fhkiel.fotomanager.view.components.TopBar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * The type Photo attributes view.
 */
@Getter
public class MediaAttributesView {
    /**
     * The Back button.
     */
    private Button backButton;
    /**
     * The text field for the name of a picture.
     */
    private TextField nameField;
    /**
     * The Tags combo box.
     */
    private ComboBox<Tag> tagsComboBox;
    /**
     * The text field for a new tag.
     */
    private TextField newTagField;
    /**
     * The button to add a tag.
     */
    private Button addTagButton;
    /**
     * The button to remove a tag.
     */
    private Button removeTagButton;
    /**
     * The save button.
     */
    private Button saveButton;
    /**
     * The list view for selected tags.
     */
    private ListView<Tag> selectedTagsListView;
    /**
     * The text field for the resolution of a picture.
     */
    private Label resolutionField;
    /**
     * The duration field.
     */
    private Label durationField;
    /**
     * The text field for the path of a picture.
     */
    private Label pathField;
    /**
     * The date picker.
     */
    private Label dateLabel;
    /**
     * The text area for the description of a picture.
     */
    private TextArea descriptionArea;
    /**
     * The checkbox for the privacy of a picture.
     */
    private CheckBox privateCheckBox;
    /**
     * The choice box for the rating of a picture.
     */
    private ChoiceBox<Rating> ratingChoiceBox;
    /**
     * The combo box for tag types.
     */
    private ComboBox<TagType> tagTypeComboBox;
    /**
     * The combo box for slideshows.
     */
    private ComboBox<Slideshow> slideshowsComboBox;
    /**
     * List of all tags.
     */
    @Setter
    private TagList allTags;
    /**
     * List of all selected tags.
     */
    private ObservableList<Tag> selectedTags;
    /**
     * The Scene to display on the stage.
     */
    private Scene scene;
    /**
     * The media.
     */
    private Media media;
    /**
     * The current slideshow.
     */
    @Setter
    private Slideshow currentSlideshow;
    /**
     * The stage.
     */
    private Stage stage;
    /**
     * The photos that were displayed before.
     */
    private List<Media> mediaList;
    /**
     * The data structure the media was in.
     */
    private DataStructure dataStructure;

    /**
     * Instantiates a new Photo attributes view.
     */
    public MediaAttributesView(Stage stage, List<Media> mediaList, DataStructure dataStructure) {
        this.stage = stage;
        this.mediaList = mediaList;
        this.dataStructure = dataStructure;
    }

    /**
     * Show.
     *
     * @param media the media
     */
    public void initialize(Media media) {
        this.media = media;
        this.selectedTags = FXCollections.observableArrayList();
        stage.setTitle("Photo Attributes");

        backButton = new Button("Back");
        saveButton = new Button("Save");

        tagsComboBox = new ComboBox<>((ObservableList<Tag>) allTags);
        tagsComboBox.setPromptText("Select Tag");
        tagsComboBox.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Tag tag, boolean empty) {
                super.updateItem(tag, empty);
                if (empty || tag == null) {
                    setText(null);
                } else {
                    setText(tag.getName());
                }
            }
        });
        tagsComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Tag tag, boolean empty) {
                super.updateItem(tag, empty);
                if (empty || tag == null) {
                    setText(null);
                } else {
                    setText(tag.getName());
                }
            }
        });

        nameField = new TextField();
        newTagField = new TextField();
        newTagField.setPromptText("New Tag");
        addTagButton = new Button("Add Tag");
        removeTagButton = new Button("Remove Tag");
        tagTypeComboBox = new ComboBox<>();
        tagTypeComboBox.setPromptText("Select Tag Type");

        slideshowsComboBox = new ComboBox<>();
        slideshowsComboBox.setPromptText("Select Slideshow");

        selectedTagsListView = new ListView<>(selectedTags);
        selectedTagsListView.setCellFactory(listView -> new ListCell<>() {
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

        resolutionField = new Label();
        durationField = new Label();
        pathField = new Label();
        dateLabel = new Label();
        descriptionArea = new TextArea();
        privateCheckBox = new CheckBox("Private");
        ratingChoiceBox = new ChoiceBox<>();

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 20, 20, 20));

        gridPane.add(new Label("Name:"), 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(new Label("Tags:"), 0, 1);
        HBox tagsBox = new HBox(10);
        tagsBox.getChildren().addAll(tagsComboBox, tagTypeComboBox, newTagField, addTagButton, removeTagButton);
        gridPane.add(tagsBox, 1, 1);
        gridPane.add(selectedTagsListView, 1, 2, 1, 3);
        gridPane.add(new Label("Resolution:"), 0, 5);
        gridPane.add(resolutionField, 1, 5);
        gridPane.add(new Label("Path:"), 0, 6);
        gridPane.add(pathField, 1, 6);
        gridPane.add(new Label("Date:"), 0, 7);
        gridPane.add(dateLabel, 1, 7);
        gridPane.add(new Label("Description:"), 0, 8);
        gridPane.add(descriptionArea, 1, 8);
        gridPane.add(new Label("Private:"), 0, 9);
        gridPane.add(privateCheckBox, 1, 9);
        gridPane.add(new Label("Rating:"), 0, 10);
        gridPane.add(ratingChoiceBox, 1, 10);

        if (media instanceof Video) gridPane.add(new Label("Duration:"), 0, 11);
        if (media instanceof Video) gridPane.add(durationField, 1, 11);

        if (media instanceof Photo) gridPane.add(new Label("Slideshow:"), 0, 11);
        if (media instanceof Photo) gridPane.add(slideshowsComboBox, 1, 11);

        HBox topBar = TopBar.createTopBar(backButton);

        BorderPane root = new BorderPane();
        root.setCenter(gridPane);

        HBox backBox = new HBox(10);
        backBox.setAlignment(Pos.CENTER);
        backBox.setPadding(new Insets(10));
        backBox.getChildren().addAll(saveButton);

        root.setBottom(backBox);
        root.setTop(topBar);

        scene = new Scene(root, 800, 800);

        String css = getClass().getResource("/darkTheme.css").toExternalForm();
        scene.getStylesheets().add(css);
    }
}
