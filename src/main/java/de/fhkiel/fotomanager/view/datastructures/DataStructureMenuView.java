package de.fhkiel.fotomanager.view.datastructures;

import de.fhkiel.fotomanager.controller.modelController.PhotoManagerController;
import de.fhkiel.fotomanager.model.Period;
import de.fhkiel.fotomanager.model.datastructures.DataStructure;
import de.fhkiel.fotomanager.model.datastructures.DataStructureType;
import de.fhkiel.fotomanager.model.datastructures.impl.Folder;
import de.fhkiel.fotomanager.view.components.BottomBar;
import de.fhkiel.fotomanager.view.components.TopBar;
import io.vavr.control.Either;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * The type Data structure menu view.
 */
@Getter
public class DataStructureMenuView {
    /**
     * The Data structure list view.
     */
    private ListView<DataStructure> dataStructureListView;
    /**
     * The list of data structures.
     */
    private List<DataStructure> dataStructures;
    /**
     * Back button.
     */
    private final Button backButton;
    /**
     * Open button.
     */
    private final Button openButton;
    /**
     * Delete button.
     */
    private final Button deleteButton;
    /**
     * Create button.
     */
    private final Button createButton;
    /**
     * The stage.
     */
    private final Stage stage;
    /**
     * The type of the data structure.
     */
    private final DataStructureType type;
    /**
     * The Scene to display on the stage.
     */
    private Scene scene;

    /**
     * Instantiates a new Data structure view.
     *
     * @param stage               the stage
     * @param deleteButtonVisible the delete button visible
     * @param createButtonVisible the create button visible
     * @param type                the type
     */
    public DataStructureMenuView(Stage stage, boolean deleteButtonVisible, boolean createButtonVisible, DataStructureType type) {
        this.stage = stage;
        this.backButton = new Button("Back");
        this.openButton = new Button("Open");
        this.deleteButton = deleteButtonVisible ? new Button("Delete") : null;
        this.createButton = createButtonVisible ? new Button("Create") : null;
        this.type = type;
        initialize();
    }

    /**
     * Formats the date.
     *
     * @param date the date
     * @return the formatted date
     */
    private String formatDate(Either<LocalDate, Period> date) {
        if (date.isLeft()) {
            return date.getLeft().format(DateTimeFormatter.ofPattern("d.M.yyyy"));
        } else {
            Period period = date.get();
            String startDate = period.getStart().format(DateTimeFormatter.ofPattern("d.M.yyyy"));
            String endDate = period.getEnd().format(DateTimeFormatter.ofPattern("d.M.yyyy"));
            return startDate + " - " + endDate;
        }
    }

    /**
     * Initializes the view.
     * Sets the title of the stage, creates the UI elements and sets the scene.
     */
    private void initialize() {
        switch (this.type) {
            case ALBUM -> stage.setTitle("Album Menu");
            case FOLDER -> stage.setTitle("Folder Menu");
            case SLIDESHOW -> stage.setTitle("Slideshow Menu");
        }

        dataStructures = PhotoManagerController.get().getDataStructures(this.type);

        dataStructureListView = new ListView<>();
        dataStructureListView.getItems().addAll(dataStructures);
        dataStructureListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            /**
             * Updates the item in the list view.
             * @param dataStructure the data structure
             * @param empty        the empty flag
             */
            protected void updateItem(DataStructure dataStructure, boolean empty) {
                super.updateItem(dataStructure, empty);
                if (empty || dataStructure == null) {
                    setText(null);
                } else {
                    String displayName = dataStructure.getName();
                    if (dataStructure instanceof Folder) {
                        Folder folder = (Folder) dataStructure;
                        String event = folder.getEvent();
                        String date = formatDate(folder.getDate());
                        displayName = event + " (" + date + ")";
                    }
                    setText(displayName);
                }
            }
        });

        HBox topBar = TopBar.createTopBar(backButton);
        HBox bottomBar = BottomBar.createBottomBar();

        if (openButton != null) bottomBar.getChildren().add(openButton);
        if (createButton != null) bottomBar.getChildren().add(createButton);
        if (deleteButton != null) bottomBar.getChildren().add(deleteButton);

        BorderPane root = new BorderPane();
        root.setCenter(dataStructureListView);
        root.setBottom(bottomBar);
        root.setTop(topBar);

        scene = new Scene(root, 800, 600);

        String css = getClass().getResource("/darkTheme.css").toExternalForm();
        scene.getStylesheets().add(css);
    }
}
