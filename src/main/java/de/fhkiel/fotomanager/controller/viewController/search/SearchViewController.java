package de.fhkiel.fotomanager.controller.viewController.search;

import de.fhkiel.fotomanager.controller.viewController.ViewController;
import de.fhkiel.fotomanager.controller.viewController.components.SearchGUIController;
import de.fhkiel.fotomanager.model.mediatypes.Media;
import de.fhkiel.fotomanager.view.search.SearchView;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Stack;

/**
 * The type Search view controller.
 */
public class SearchViewController extends ViewController<SearchView> {
    /**
     * The search view controller instance.
     */
    public static SearchViewController instance;

    private SearchViewController() {}

    /**
     * Gets the instance.
     *
     * @return the instance.
     */
    public static SearchViewController get() {
        if (instance == null) {
            instance = new SearchViewController();
        }
        return instance;
    }


    /**
     * Create view search view.
     *
     * @param stage   the stage
     * @param history the history
     * @return the search view
     */
    public SearchView createView(Stage stage, Stack<Scene> history) {
        this.history = history;
        this.stage = stage;
        view = new SearchView(stage);
        view.getBackButton().setOnAction(event -> goBack());
        view.getSearchGUI().getSearchButton().setOnAction(event -> search());
        view.getSearchGUI().getResetSearchButton().setOnAction(event -> resetSearch());
        return view;
    }

    /**
     * Resets all search fields.
     */
    private void resetSearch() {
        view.getSearchGUI().getTagsField().clear();
        view.getSearchGUI().getNameField().clear();
        view.getSearchGUI().getRatingChoiceBox().getSelectionModel().clearSelection();
        view.getSearchGUI().getPrivateCheckBox().setSelected(false);
        view.getSearchGUI().getDescriptionField().clear();
        view.getSearchGUI().getStartDatePicker().setValue(null);
        view.getSearchGUI().getEndDatePicker().setValue(null);
        view.getSearchGUI().getErrorLabel().setText("");
        view.getSearchResults().getItems().clear();
    }

    /**
     * Starts the search and displays the results.
     */
    private void search() {
        if (view == null) {
            return;
        }

        try {
            ObservableList<Media> results = SearchGUIController.get().search(view.getSearchGUI(), null);
            view.getSearchResults().getItems().clear();
            view.getSearchResults().setItems(results);
        } catch (Exception e) {
            view.getSearchGUI().getErrorLabel().setText(e.getMessage());
        }
    }
}
