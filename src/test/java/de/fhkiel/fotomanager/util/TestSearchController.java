package de.fhkiel.fotomanager.util;

import de.fhkiel.fotomanager.model.search.SearchParameter;
import de.fhkiel.fotomanager.controller.modelController.SearchController;
import de.fhkiel.fotomanager.model.Period;
import de.fhkiel.fotomanager.model.mediatypes.Rating;
import de.fhkiel.fotomanager.model.taglists.TagList;
import de.fhkiel.fotomanager.model.tags.Tag;
import io.vavr.control.Either;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * The type Test search controller.
 */
public class TestSearchController {
    /**
     * The Search controller.
     */
    private static final SearchController searchController = SearchController.get();
    /**
     * The Default name.
     */
    private static final Optional<String> defaultName = Optional.empty();
    /**
     * The Default date.
     */
    private static final Optional<Either<LocalDate, Period>> defaultDate = Optional.empty();
    /**
     * The Default rating.
     */
    private static final Optional<Rating> defaultRating = Optional.empty();
    /**
     * The Default is private.
     */
    private static final Optional<Boolean> defaultIsPrivate = Optional.empty();
    /**
     * The Default tags.
     */
    private static final Optional<TagList> defaultTags = Optional.empty();
    /**
     * The Default description.
     */
    private static final Optional<String> defaultDescription = Optional.empty();

    /**
     * Create default search parameter search parameter.
     *
     * @return the search parameter
     */
    public static SearchParameter createDefaultSearchParameter() {
        return searchController.createSearchParameter(defaultName, defaultDate, defaultRating, defaultIsPrivate, defaultTags, defaultDescription);
    }

}
