package de.fhkiel.fotomanager.search;

import de.fhkiel.fotomanager.controller.modelController.TagListController;
import de.fhkiel.fotomanager.model.search.SearchParameter;
import de.fhkiel.fotomanager.controller.modelController.TagController;
import de.fhkiel.fotomanager.model.Period;
import de.fhkiel.fotomanager.model.mediatypes.Rating;
import de.fhkiel.fotomanager.model.taglists.TagList;
import de.fhkiel.fotomanager.model.tags.Tag;
import de.fhkiel.fotomanager.model.tags.TagType;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Search parameter test.
 */
public class SearchParameterTest {

    /**
     * The TagController is needed to create a Tag object.
     */
    private static TagController tagController;

    /**
     * Sets up.
     */
// Set up TagController before running the tests by getting the instance of it
    @BeforeAll
    public static void setUp() {
        tagController = TagController.get();
    }

    /**
     * Create empty search parameter test.
     */
// Test if the SearchParameter class can be instantiated with empty parameters
    @Test
    public void createEmptySearchParameterTest() {
        Optional<String> name = Optional.empty();
        Optional<Either<LocalDate, Period>> date = Optional.empty();
        Optional<Rating> rating = Optional.empty();
        Optional<Boolean> isPrivate = Optional.empty();
        Optional<String> description = Optional.empty();
        Optional<TagList> tags = Optional.empty();
        SearchParameter searchParameter = new SearchParameter(name, date, rating, isPrivate, tags, description);

        // Check if the object is not null
        assertNotNull(searchParameter);

        // Check if the name is empty
        assertTrue(searchParameter.getName().isEmpty());

        // Check if the date is empty
        assertTrue(searchParameter.getDate().isEmpty());

        // Check if the rating is empty
        assertTrue(searchParameter.getRating().isEmpty());

        // Check if isPrivate is empty
        assertTrue(searchParameter.getIsPrivate().isEmpty());

        // Check if the tags is empty
        assertTrue(searchParameter.getTags().isEmpty());

        // Check if the description is empty
        assertTrue(searchParameter.getDescription().isEmpty());
    }

    /**
     * Create search parameter test.
     */
// Test if the SearchParameter class can be instantiated with parameters
    @Test
    public void createSearchParameterTest() {
        Optional<String> name = Optional.of("name");
        Optional<Either<LocalDate, Period>> date = Optional.of(Either.left(LocalDate.now()));
        Optional<Rating> rating = Optional.of(Rating.FIVE_STARS);
        Optional<Boolean> isPrivate = Optional.of(true);
        Optional<String> description = Optional.of("description");
        Optional<TagList> tags = Optional.of(TagListController.get().createTagList(List.of(tagController.createTag(TagType.INDEX, "tag"))));
        SearchParameter searchParameter = new SearchParameter(name, date, rating, isPrivate, tags, description);

        // Check if the object is not null
        assertNotNull(searchParameter);

        // Check if the name is not empty
        assertTrue(searchParameter.getName().isPresent());

        // Check if the date is not empty
        assertTrue(searchParameter.getDate().isPresent());

        // Check if the rating is not empty
        assertTrue(searchParameter.getRating().isPresent());

        // Check if isPrivate is not empty
        assertTrue(searchParameter.getIsPrivate().isPresent());

        // Check if the tags is not empty
        assertTrue(searchParameter.getTags().isPresent());

        // Check if the description is not empty
        assertTrue(searchParameter.getDescription().isPresent());
    }

    /**
     * Equals test.
     */
// Test if two SearchParameter objects are equal when they have the same parameters
    @Test
    public void equalsTest() {
        Optional<String> name = Optional.of("name");
        Optional<Either<LocalDate, Period>> date = Optional.of(Either.left(LocalDate.now()));
        Optional<Rating> rating = Optional.of(Rating.FIVE_STARS);
        Optional<Boolean> isPrivate = Optional.of(true);
        Optional<String> description = Optional.of("description");
        Optional<TagList> tags = Optional.of(TagListController.get().createTagList(List.of(tagController.createTag(TagType.INDEX, "tag"))));
        SearchParameter searchParameter1 = new SearchParameter(name, date, rating, isPrivate, tags, description);
        SearchParameter searchParameter2 = new SearchParameter(name, date, rating, isPrivate, tags, description);

        // Check if the two objects are equal
        assertTrue(searchParameter1.equals(searchParameter2));
    }

    /**
     * Not equals test.
     */
// Test if two SearchParameter objects are not equal when they have different parameters
    @Test
    public void notEqualsTest() {
        Optional<String> name = Optional.of("name");
        Optional<Either<LocalDate, Period>> date = Optional.of(Either.left(LocalDate.now()));
        Optional<Rating> rating = Optional.of(Rating.FIVE_STARS);
        Optional<Boolean> isPrivate = Optional.of(true);
        Optional<String> description = Optional.of("description");
        Optional<TagList> tags = Optional.of(TagListController.get().createTagList(List.of(tagController.createTag(TagType.INDEX, "tag"))));
        SearchParameter searchParameter1 = new SearchParameter(name, date, rating, isPrivate, tags, description);

        Optional<String> name2 = Optional.of("name2");
        Optional<Either<LocalDate, Period>> date2 = Optional.of(Either.left(LocalDate.now()));
        Optional<Rating> rating2 = Optional.of(Rating.FIVE_STARS);
        Optional<Boolean> isPrivate2 = Optional.of(true);
        Optional<String> description2 = Optional.of("description");
        Optional<TagList> tags2 = Optional.of(TagListController.get().createTagList(List.of(tagController.createTag(TagType.INDEX, "tag"))));
        SearchParameter searchParameter2 = new SearchParameter(name2, date2, rating2, isPrivate2, tags2, description2);

        // Check if the two objects are not equal
        assertFalse(searchParameter1.equals(searchParameter2));
    }

    /**
     * Not equals to another object test.
     */
// Test if SearchParameter object is not equal to another object
    @Test
    public void notEqualsToAnotherObjectTest() {
        Optional<String> name = Optional.of("name");
        Optional<Either<LocalDate, Period>> date = Optional.of(Either.left(LocalDate.now()));
        Optional<Rating> rating = Optional.of(Rating.FIVE_STARS);
        Optional<Boolean> isPrivate = Optional.of(true);
        Optional<String> description = Optional.of("description");
        Optional<TagList> tags = Optional.of(TagListController.get().createTagList(List.of(tagController.createTag(TagType.INDEX, "tag"))));
        SearchParameter searchParameter = new SearchParameter(name, date, rating, isPrivate, tags, description);

        // Check if the object is not equal to a string
        assertFalse(searchParameter.equals("string"));

        // Check if the object is not equal to an Object
        assertFalse(searchParameter.equals(new Object()));
    }

}
