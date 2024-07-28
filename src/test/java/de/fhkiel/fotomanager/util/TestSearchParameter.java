package de.fhkiel.fotomanager.util;

import de.fhkiel.fotomanager.controller.modelController.TagListController;
import de.fhkiel.fotomanager.model.search.SearchParameter;
import de.fhkiel.fotomanager.model.Period;
import de.fhkiel.fotomanager.model.mediatypes.Rating;
import de.fhkiel.fotomanager.model.taglists.TagList;
import de.fhkiel.fotomanager.model.tags.Tag;
import io.vavr.control.Either;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * This class provides methods to create SearchParameter objects for testing purposes.
 */
public class TestSearchParameter {

    /**
     * The instance of the TestSearchParameter class.
     */
    public static TestSearchParameter instance;

    /**
     * Create default search parameter 1 search parameter.
     *
     * @return the search parameter
     */
    public static SearchParameter createDefaultSearchParameter1() {
        Optional<String> name = Optional.of("Name");
        Optional<Either<LocalDate, Period>> date = Optional.of(Either.right(new Period(LocalDate.of(2020, 1, 1), LocalDate.of(2024, 1, 1))));
        Optional<Rating> rating = Optional.of(Rating.TWO_STARS);
        Optional<Boolean> isPrivate = Optional.of(true);
        Optional<String> description = Optional.of("Description");
        Optional<TagList> tags = Optional.of(TagListController.get().createTagList(List.of(TestTagController.createDefaultTag())));
        return new SearchParameter(name, date, rating, isPrivate, tags, description);
    }

    /**
     * Create exact date search parameter search parameter.
     *
     * @return the search parameter
     */
    public static SearchParameter createExactDateSearchParameter() {
        Optional<String> name = Optional.empty();
        Optional<Either<LocalDate, Period>> date = Optional.of(Either.left(LocalDate.of(2022, 11, 17)));
        Optional<Rating> rating = Optional.empty();
        Optional<Boolean> isPrivate = Optional.empty();
        Optional<String> description = Optional.empty();
        Optional<TagList> tags = Optional.empty();
        return new SearchParameter(name, date, rating, isPrivate, tags, description);
    }

    /**
     * Create period search parameter search parameter.
     *
     * @return the search parameter
     */
    public static SearchParameter createPeriodSearchParameter() {
        Optional<String> name = Optional.empty();
        Optional<Either<LocalDate, Period>> date = Optional.of(Either.right(new Period(LocalDate.of(2020, 1, 1), LocalDate.of(2024, 1, 1))));
        Optional<Rating> rating = Optional.empty();
        Optional<Boolean> isPrivate = Optional.empty();
        Optional<String> description = Optional.empty();
        Optional<TagList> tags = Optional.empty();
        return new SearchParameter(name, date, rating, isPrivate, tags, description);
    }

    /**
     * Create rating search parameter search parameter.
     *
     * @return the search parameter
     */
    public static SearchParameter createRatingSearchParameter() {
        Optional<String> name = Optional.empty();
        Optional<Either<LocalDate, Period>> date = Optional.empty();
        Optional<Rating> rating = Optional.of(Rating.FIVE_STARS);
        Optional<Boolean> isPrivate = Optional.empty();
        Optional<String> description = Optional.empty();
        Optional<TagList> tags = Optional.empty();
        return new SearchParameter(name, date, rating, isPrivate, tags, description);
    }

    /**
     * Create private search parameter search parameter.
     *
     * @return the search parameter
     */
    public static SearchParameter createPrivateSearchParameter() {
        Optional<String> name = Optional.empty();
        Optional<Either<LocalDate, Period>> date = Optional.empty();
        Optional<Rating> rating = Optional.empty();
        Optional<Boolean> isPrivate = Optional.of(true);
        Optional<String> description = Optional.empty();
        Optional<TagList> tags = Optional.empty();
        return new SearchParameter(name, date, rating, isPrivate, tags, description);
    }

    /**
     * Create description search parameter search parameter.
     *
     * @return the search parameter
     */
    public static SearchParameter createDescriptionSearchParameter() {
        Optional<String> name = Optional.empty();
        Optional<Either<LocalDate, Period>> date = Optional.empty();
        Optional<Rating> rating = Optional.empty();
        Optional<Boolean> isPrivate = Optional.empty();
        Optional<String> description = Optional.of("Description");
        Optional<TagList> tags = Optional.empty();
        return new SearchParameter(name, date, rating, isPrivate, tags, description);
    }

    /**
     * Create tag search parameter search parameter.
     *
     * @return the search parameter
     */
    public static SearchParameter createTagSearchParameter() {
        Optional<String> name = Optional.empty();
        Optional<Either<LocalDate, Period>> date = Optional.empty();
        Optional<Rating> rating = Optional.empty();
        Optional<Boolean> isPrivate = Optional.empty();
        Optional<String> description = Optional.empty();
        Optional<TagList> tags = Optional.of(TagListController.get().createTagList(List.of(TestTagController.createDefaultTag())));
        return new SearchParameter(name, date, rating, isPrivate, tags, description);
    }
}
