package de.fhkiel.fotomanager.view.tags;

import de.fhkiel.fotomanager.model.tags.Tag;
import de.fhkiel.fotomanager.model.tags.TagType;
import de.fhkiel.fotomanager.view.components.TopBar;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Getter;

/**
 * The type Tag management view.
 */
@Getter
public class TagManagementView {
    /**
     * The Back button.
     */
    private final Button backButton;
    /**
     * The Add button.
     */
    private final Button addButton;
    /**
     * The Delete button.
     */
    private final Button deleteButton;
    /**
     * The Change button.
     */
    private final Button changeButton;
    /**
     * The Name field.
     */
    private final TextField nameField;
    /**
     * The Color picker.
     */
    private final ColorPicker colorPicker;
    /**
     * The Combo box for the tag types.
     */
    private final ComboBox<TagType> tagTypeComboBox;
    /**
     * The List view for the tags.
     */
    private final ListView<Tag> tagListView;
    /**
     * The Tab pane for the different tag types.
     */
    private final TabPane tabPane;
    /**
     * The Tab for all tags.
     */
    private final Tab allTab;
    /**
     * The Tab for adjective tags.
     */
    private final Tab adjectiveTab;
    /**
     * The Tab for location tags.
     */
    private final Tab locationTab;
    /**
     * The Tab for person tags.
     */
    private final Tab personTab;
    /**
     * The Tab for normal tags.
     */
    private final Tab indexTab;
    /**
     * The Scene to display on the stage.
     */
    private Scene scene;
    /**
     * Error message label.
     */
    private Label errorLabel;

    /**
     * Instantiates a new Tag management view.
     *
     * @param stage the stage
     */
    public TagManagementView(Stage stage) {
        stage.setTitle("Tag Management");

        backButton = new Button("Back");
        addButton = new Button("Add");
        deleteButton = new Button("Delete");
        changeButton = new Button("Change");
        nameField = new TextField();
        nameField.setPromptText("Tag Name");
        colorPicker = new ColorPicker();
        tagTypeComboBox = new ComboBox<>();
        tagTypeComboBox.setPromptText("Select Tag Type");
        tagListView = new ListView<>();
        errorLabel = new Label();
        this.errorLabel.setStyle("-fx-text-fill: red");

        VBox tagForm = new VBox(10);
        tagForm.setAlignment(Pos.CENTER);
        tagForm.getChildren().addAll(
                new Label("Name:"),
                nameField,
                new Label("Color:"),
                colorPicker,
                new Label("Type:"),
                tagTypeComboBox,
                errorLabel,
                addButton,
                changeButton,
                deleteButton
        );

        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        allTab = new Tab("All");
        adjectiveTab = new Tab("Adjective");
        locationTab = new Tab("Location");
        personTab = new Tab("Person");
        indexTab = new Tab("Index");
        tabPane.getTabs().addAll(allTab, adjectiveTab, locationTab, personTab, indexTab);

        VBox listViewWithTabs = new VBox(tabPane, tagListView);
        listViewWithTabs.setAlignment(Pos.CENTER);
        listViewWithTabs.setPadding(new Insets(20));

        HBox mainLayout = new HBox(20);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(20));
        mainLayout.getChildren().addAll(listViewWithTabs, tagForm);

        HBox topBar = TopBar.createTopBar(backButton);

        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(mainLayout);

        scene = new Scene(root, 800, 600);

        String css = getClass().getResource("/darkTheme.css").toExternalForm();
        scene.getStylesheets().add(css);
    }
}
