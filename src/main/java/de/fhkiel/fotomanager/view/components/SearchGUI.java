package de.fhkiel.fotomanager.view.components;

import de.fhkiel.fotomanager.model.mediatypes.Media;
import de.fhkiel.fotomanager.model.mediatypes.Rating;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import lombok.Getter;

/**
 * The type Search gui.
 */
@Getter
public class SearchGUI {
    /**
     * The Search button.
     */
    private Button searchButton;
    /**
     * The Reset search button.
     */
    private Button resetSearchButton;
    /**
     * The Tags field.
     */
    private TextField tagsField;
    /**
     * The Name field.
     */
    private TextField nameField;
    /**
     * The Description field.
     */
    private TextField descriptionField;
    /**
     * The Rating choice box.
     */
    private ChoiceBox<Rating> ratingChoiceBox;
    /**
     * The Private check box.
     */
    private CheckBox privateCheckBox;
    /**
     * The date picker for the start date.
     */
    private DatePicker startDatePicker;
    /**
     * The date picker for the end date.
     */
    private DatePicker endDatePicker;
    /**
     * The Search results.
     */
    private ListView<Media> searchResults;
    /**
     * The Error label.
     */
    private Label errorLabel;

    /**
     * Instantiates a new Search gui.
     */
    public SearchGUI() {
    }

    /**
     * Create search box.
     *
     * @return the search box
     */
    public VBox createSearchBox() {
        searchButton = new Button("Search");
        resetSearchButton = new Button("Reset Search");

        tagsField = new TextField();
        tagsField.setPromptText("Tags,MoreTags (separated by comma)");

        nameField = new TextField();
        nameField.setPromptText("Name");

        descriptionField = new TextField();
        descriptionField.setPromptText("Description");

        ratingChoiceBox = new ChoiceBox<>();
        for (Rating rating : Rating.values()) {
            ratingChoiceBox.getItems().add(rating);
        }

        privateCheckBox = new CheckBox("Private");

        startDatePicker = new DatePicker();
        startDatePicker.setPromptText("Start Date");

        endDatePicker = new DatePicker();
        endDatePicker.setPromptText("End Date");

        errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red");

        searchResults = new ListView<>();

        searchResults.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Media media, boolean empty) {
                super.updateItem(media, empty);
                if (empty || media == null) {
                    setText(null);
                } else {
                    setText(media.getName());
                }
            }
        });

        GridPane searchGrid = new GridPane();
        searchGrid.setAlignment(Pos.CENTER);
        searchGrid.setHgap(10);
        searchGrid.setVgap(10);
        searchGrid.setPadding(new Insets(20, 20, 20, 20));

        searchGrid.add(new Label("Tags:"), 0, 0);
        searchGrid.add(tagsField, 1, 0);

        searchGrid.add(new Label("Name:"), 0, 1);
        searchGrid.add(nameField, 1, 1);

        searchGrid.add(new Label("Description:"), 0, 2);
        searchGrid.add(descriptionField, 1, 2);

        searchGrid.add(new Label("Rating:"), 0, 3);
        searchGrid.add(ratingChoiceBox, 1, 3);

        searchGrid.add(new Label("Private:"), 0, 4);
        searchGrid.add(privateCheckBox, 1, 4);

        searchGrid.add(new Label("Start Date:"), 0, 5);
        searchGrid.add(startDatePicker, 1, 5);

        searchGrid.add(new Label("End Date:"), 0, 6);
        searchGrid.add(endDatePicker, 1, 6);

        VBox searchBox = new VBox(10);
        searchBox.setAlignment(Pos.CENTER);
        searchBox.getChildren().addAll(searchGrid, searchButton, errorLabel, resetSearchButton);

        return searchBox;
    }
}