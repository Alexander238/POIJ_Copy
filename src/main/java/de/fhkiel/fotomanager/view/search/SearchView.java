package de.fhkiel.fotomanager.view.search;

import de.fhkiel.fotomanager.model.mediatypes.Media;
import de.fhkiel.fotomanager.view.components.SearchGUI;
import de.fhkiel.fotomanager.view.components.TopBar;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Getter;

/**
 * The type Search view.
 */
@Getter
public class SearchView {
    /**
     * The Back button.
     */
    private final Button backButton;
    /**
     * The Search results in form of a list view.
     */
    private final ListView<Media> searchResults;
    /**
     * The Search box.
     */
    private final VBox searchBox;
    /**
     * The Search gui.
     */
    private final SearchGUI searchGUI;
    /**
     * The Scene to display on the stage.
     */
    private Scene scene;

    /**
     * Instantiates a new Search view.
     *
     * @param stage the stage
     */
    public SearchView(Stage stage) {
        stage.setTitle("Search");
        backButton = new Button("Back");

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

        searchGUI = new SearchGUI();
        searchBox = searchGUI.createSearchBox();

        VBox mainLayout = new VBox(10);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(20));
        mainLayout.getChildren().addAll(searchBox, searchResults);

        HBox topBar = TopBar.createTopBar(backButton);

        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(mainLayout);

        scene = new Scene(root, 800, 600);

        String css = getClass().getResource("/darkTheme.css").toExternalForm();
        scene.getStylesheets().add(css);
    }
}
