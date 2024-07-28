package de.fhkiel.fotomanager.controller.viewController.components;

import de.fhkiel.fotomanager.controller.modelController.SearchController;
import de.fhkiel.fotomanager.controller.modelController.TagController;
import de.fhkiel.fotomanager.controller.modelController.TagListController;
import de.fhkiel.fotomanager.model.Period;
import de.fhkiel.fotomanager.model.datastructures.DataStructure;
import de.fhkiel.fotomanager.model.mediatypes.Media;
import de.fhkiel.fotomanager.model.mediatypes.Rating;
import de.fhkiel.fotomanager.model.search.Search;
import de.fhkiel.fotomanager.model.search.SearchParameter;
import de.fhkiel.fotomanager.model.search.SearchParameterBuilder;
import de.fhkiel.fotomanager.model.taglists.TagList;
import de.fhkiel.fotomanager.model.tags.Tag;
import de.fhkiel.fotomanager.model.tags.TagType;
import de.fhkiel.fotomanager.view.components.SearchGUI;
import io.vavr.control.Either;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Search gui controller.
 */
public class SearchGUIController {
    /**
     * The search gui controller instance.
     */
    public static SearchGUIController instance;

    private SearchGUIController() {}

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static SearchGUIController get() {
        if (instance == null) {
            instance = new SearchGUIController();
        }
        return instance;
    }

    /**
     * Reset search.
     *
     * @param searchGUI the search gui
     */
    public void resetSearch(SearchGUI searchGUI) {
        searchGUI.getTagsField().clear();
        searchGUI.getNameField().clear();
        searchGUI.getRatingChoiceBox().getSelectionModel().clearSelection();
        searchGUI.getPrivateCheckBox().setSelected(false);
        searchGUI.getDescriptionField().clear();
        searchGUI.getStartDatePicker().setValue(null);
        searchGUI.getEndDatePicker().setValue(null);
        searchGUI.getErrorLabel().setText("");
    }

    /**
     * Search through an observable list.
     * - The search is based on the search parameters set in the search gui.
     * - The search is performed by the search controller.
     * - The search results are added to the observable list.
     * - The observable list is returned.
     *
     * @param searchGUI     the search gui
     * @param dataStructure the data structure
     * @return the observable list
     */
    public ObservableList<Media> search(SearchGUI searchGUI, DataStructure dataStructure) {
        searchGUI.getErrorLabel().setText("");

        SearchParameterBuilder searchParameterBuilder = SearchController.get().createSearchParameterBuilder();
        String tagFieldText = searchGUI.getTagsField().getText();
        String nameFieldText = searchGUI.getNameField().getText();
        Rating ratingField = searchGUI.getRatingChoiceBox().getValue();
        boolean isPrivateField = searchGUI.getPrivateCheckBox().isSelected();
        String descriptionFieldText = searchGUI.getDescriptionField().getText();
        LocalDate startDate = searchGUI.getStartDatePicker().getValue();
        LocalDate endDate = searchGUI.getEndDatePicker().getValue();

         if (!tagFieldText.isEmpty()){
            String[] tagsStr = tagFieldText.split(",");
            TagList tags = TagListController.get().createEmptyTagList();
            for (String tagStr : tagsStr) {
                tags.addTag(TagController.get().createTag(TagType.SEARCH, tagStr));
            }
            searchParameterBuilder.setTags(tags);
        }

        if (!nameFieldText.isEmpty()) {
            searchParameterBuilder.setName(nameFieldText);
        }

        if (ratingField != null) {
            searchParameterBuilder.setRating(ratingField);
        }

        searchParameterBuilder.setIsPrivate(isPrivateField);

        if (!descriptionFieldText.isEmpty()) {
            searchParameterBuilder.setDescription(descriptionFieldText);
        }

        if (startDate != null && endDate == null) {
            searchParameterBuilder.setDate(Either.right(new Period(startDate, null)));
        }

        if (startDate == null && endDate != null) {
            searchParameterBuilder.setDate(Either.right(new Period(null, endDate)));
        }

        if (startDate != null && endDate != null) {
            if (startDate.isEqual(endDate)) {
                searchParameterBuilder.setDate(Either.left(startDate));
            } else {
                try {
                    searchParameterBuilder.setDate(Either.right(new Period(startDate, endDate)));
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException(e.getMessage());
                }
            }
        }

        SearchParameter searchParameter = searchParameterBuilder.build();
        Search search;
        if (dataStructure != null) {
            search = SearchController.get().createSpecificSearch(dataStructure);
        } else {
            search = SearchController.get().createGlobalSearch();
        }
        SearchController.get().addSearchParameter(search, searchParameter);
        ObservableList<Media> searchResults = FXCollections.observableArrayList();
        List<Media> listResults = SearchController.get().startSearch(search);
        searchResults.addAll(listResults);
        return searchResults;
    }
}