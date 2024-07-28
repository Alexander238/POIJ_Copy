package de.fhkiel.fotomanager.view.datastructures;

import de.fhkiel.fotomanager.controller.modelController.PhotoManagerController;
import de.fhkiel.fotomanager.controller.viewController.components.MediaGridController;
import de.fhkiel.fotomanager.model.datastructures.DataStructure;
import de.fhkiel.fotomanager.model.datastructures.impl.Slideshow;
import de.fhkiel.fotomanager.model.mediatypes.Media;
import de.fhkiel.fotomanager.view.components.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Media view.
 * This class is responsible for displaying the media files in the media folder.
 */
@Getter
public class AllMediaView {
    /**
     * The Back button.
     */
    private final Button backButton;
    /**
     * The Toggle search button.
     */
    private Button toggleSearchButton;
    /**
     * Add media button.
     */
    private Button addMediaButton;
    /**
     * The search gui.
     */
    private SearchGUI searchGUI;
    /**
     * The Search box.
     */
    private VBox searchBox;
    /**
     * The Stage.
     */
    private final Stage stage;
    /**
     * The Media files.
     */
    private List<Media> mediaList = new ArrayList<>();
    /**
     * The Grid pane.
     */
    @Setter
    private FlowPane flowPane;
    /**
     * The Scene to display on the stage.
     */
    private Scene scene;
    /**
     * The Show all button.
     */
    private Button showAllButton;
    /**
     * The Show photos button.
     */
    private Button showPhotosButton;
    /**
     * The open Slideshow button.
     */
    private Button openSlideshowButton = null;
    /**
     * The Show videos button.
     */
    private Button showVideosButton;
    /**
     * The Media grid.
     */
    private MediaGrid mediaGrid;
    /**
     * The Show search.
     */
    @Setter
    private boolean showSearch = false;
    /**
     * The Save button for adding media.
     */
    private Button saveButton;
    /**
     * The selected media files.
     */
    private List<Media> selectedMedia = new ArrayList<>();
    /**
     * Flag if media should be selectable and to save.
     */
    private boolean saveMedia;
    /**
     * toggle show untagged images button.
     */
    private Button showUntaggedButton;
    /**
     * Show add Media button.
     */
    private boolean showAddMediaButton;

    /**
     * Instantiates a new Media view.
     *
     * @param stage              the stage
     * @param searchFunc         the search function
     * @param resetSearchFunc    the reset search function
     * @param saveMedia          flag if media should be selectable and to save
     * @param showAddMediaButton the show add media button
     * @param dataStructure      the data structure
     */
    public AllMediaView(Stage stage, Runnable searchFunc, Runnable resetSearchFunc, boolean saveMedia, boolean showAddMediaButton, DataStructure dataStructure) {
        this.stage = stage;
        this.backButton = new Button("Back");
        this.saveMedia = saveMedia;
        this.showAddMediaButton = showAddMediaButton;
        showAllButton = new Button("Show All");
        showPhotosButton = new Button("Photos");
        showVideosButton = new Button("Videos");
        toggleSearchButton = new Button("Search");
        showUntaggedButton = new Button("Show untagged images");
        if (showAddMediaButton) addMediaButton = new Button("Add Media");
        if (saveMedia) saveButton = new Button("Save selected media");
        if (dataStructure instanceof Slideshow && showAddMediaButton) {
            openSlideshowButton = new Button("Open Slideshow");
        }
        if (dataStructure != null) {
            mediaList = dataStructure.getMediaList();
        } else {
            mediaList.addAll(PhotoManagerController.get().getPhotos());
            mediaList.addAll(PhotoManagerController.get().getVideos());
        }

        initialize(searchFunc, resetSearchFunc);
    }

    /**
     * Initialize the view.
     *
     * @param searchFunc      the search function
     * @param resetSearchFunc the reset search function
     */
    public void initialize(Runnable searchFunc, Runnable resetSearchFunc) {
        stage.setTitle("All Media");
        buildScene(searchFunc, resetSearchFunc);
    }

    /**
     * Build the scene.
     *
     * @param searchFunc      the search function
     * @param resetSearchFunc the reset search function
     */
    public void buildScene(Runnable searchFunc, Runnable resetSearchFunc) {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10));

        HBox bottomBar = BottomBar.createBottomBar(showAllButton, showPhotosButton, showVideosButton, showUntaggedButton);
        HBox topBar = TopBar.createTopBar(backButton, toggleSearchButton);

        if (saveButton != null) topBar.getChildren().add(saveButton);

        if (openSlideshowButton != null && !saveMedia) {
            topBar.getChildren().add(openSlideshowButton);
        }

        if (addMediaButton != null && !saveMedia) {
            topBar.getChildren().add(addMediaButton);
        }

        VBox searchPane = new VBox(10);
        searchPane.setPadding(new Insets(10));
        searchPane.setAlignment(Pos.CENTER);
        searchPane.setVisible(showSearch);

        this.searchGUI = new SearchGUI();
        this.searchBox = searchGUI.createSearchBox();

        this.mediaGrid = new MediaGrid(mediaList);
        this.flowPane = MediaGridController.get().buildMediaGrid(mediaGrid);

        searchGUI.getSearchButton().setOnAction(e -> {
            searchFunc.run();
        });
        searchGUI.getResetSearchButton().setOnAction(e -> {
            resetSearchFunc.run();
        });

        searchPane.getChildren().addAll(searchBox);

        VBox centerView = new VBox();
        centerView.getChildren().add(flowPane);
        centerView.setAlignment(Pos.TOP_LEFT);
        centerView.setPadding(new Insets(20));

        BorderPane root = new BorderPane();
        root.setCenter(centerView);
        root.setTop(topBar);
        root.setBottom(bottomBar);
        root.setRight(searchPane);

        scene = new Scene(root, 800, 600);
        String css = getClass().getResource("/darkTheme.css").toExternalForm();
        scene.getStylesheets().add(css);
    }

    public void setMediaGrid(MediaGrid mediaGrid) {
        this.mediaGrid = mediaGrid;
        this.flowPane = MediaGridController.get().buildMediaGrid(mediaGrid);
    }
}
