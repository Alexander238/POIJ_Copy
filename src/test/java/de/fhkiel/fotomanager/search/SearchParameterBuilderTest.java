package de.fhkiel.fotomanager.search;

import de.fhkiel.fotomanager.controller.modelController.TagListController;
import de.fhkiel.fotomanager.model.Period;
import de.fhkiel.fotomanager.model.mediatypes.Rating;
import de.fhkiel.fotomanager.model.search.SearchParameter;
import de.fhkiel.fotomanager.model.search.SearchParameterBuilder;
import de.fhkiel.fotomanager.model.taglists.TagList;
import de.fhkiel.fotomanager.model.tags.Tag;
import de.fhkiel.fotomanager.model.tags.TagType;
import io.vavr.control.Either;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Search parameter builder test.
 */
public class SearchParameterBuilderTest {
    /**
     * The Search parameter builder.
     */
    private static SearchParameterBuilder searchParameterBuilder;

    /**
     * Sets up.
     */
// Set up SearchParameterBuilder before running the tests
    @BeforeAll
    public static void setUp() {
        searchParameterBuilder = new SearchParameterBuilder();
    }

    /**
     * Create search parameter builder test.
     */
// Test if the SearchParameterBuilder class can be instantiated
    @Test
    public void createSearchParameterBuilderTest() {
        assertNotNull(searchParameterBuilder);
    }

    /**
     * Sets name test.
     */
// Test if the name can be set
    @Test
    public void setNameTest() {
        SearchParameterBuilder searchParameterBuilder1 = searchParameterBuilder.setName("Test");

        // Check if the object is not null
        assertNotNull(searchParameterBuilder1);

        // Check if the object is the same as the original object
        assertSame(searchParameterBuilder, searchParameterBuilder1);

        // Check if the name is present
        assertTrue(searchParameterBuilder.getName().isPresent());

        // Check if the name is "Test"
        assertEquals("Test", searchParameterBuilder.getName().get());
    }

    /**
     * Sets date test.
     */
// Test if the date can be set
    @Test
    public void setDateTest() {
        LocalDate date = LocalDate.now();
        SearchParameterBuilder searchParameterBuilder1 = searchParameterBuilder.setDate(Either.left(date));

        // Check if the object is not null
        assertNotNull(searchParameterBuilder1);

        // Check if the date is present
        assertTrue(searchParameterBuilder.getDate().isPresent());

        // Check if the date is the same as the date set
        assertEquals(date, searchParameterBuilder1.getDate().get().getLeft());
    }

    /**
     * Sets rating test.
     */
// Test if the rating can be set
    @Test
    public void setRatingTest() {
        Rating rating = Rating.FIVE_STARS;
        SearchParameterBuilder searchParameterBuilder1 = searchParameterBuilder.setRating(rating);

        // Check if the object is not null
        assertNotNull(searchParameterBuilder1);

        // Check if rating is present
        assertTrue(searchParameterBuilder.getRating().isPresent());

        // Check if the rating is the same as the rating set
        assertEquals(rating, searchParameterBuilder1.getRating().get());
    }

    /**
     * Sets is private test.
     */
// Test if isPrivate can be set
    @Test
    public void setIsPrivateTest() {
        boolean isPrivate = true;
        SearchParameterBuilder searchParameterBuilder1 = searchParameterBuilder.setIsPrivate(isPrivate);

        // Check if the object is not null
        assertNotNull(searchParameterBuilder1);

        // Check if isPrivate is present
        assertTrue(searchParameterBuilder.getIsPrivate().isPresent());

        // Check if isPrivate is the same as the isPrivate set
        assertEquals(isPrivate, searchParameterBuilder1.getIsPrivate().get());
    }

    /**
     * Sets tags test.
     */
// Test if tags can be set
    @Test
    public void setTagsTest() {
        TagList tags = TagListController.get().createTagList(List.of(new Tag("Test", Color.BLACK, TagType.INDEX)));
        SearchParameterBuilder searchParameterBuilder1 = searchParameterBuilder.setTags(tags);

        // Check if the object is not null
        assertNotNull(searchParameterBuilder1);

        // Check if tags are present
        assertTrue(searchParameterBuilder.getTags().isPresent());

        // Check if tags are the same as the tags set
        assertEquals(tags, searchParameterBuilder1.getTags().get());
    }

    /**
     * Sets description test.
     */
// Test if the description can be set
    @Test
    public void setDescriptionTest() {
        String description = "Test";
        SearchParameterBuilder searchParameterBuilder1 = searchParameterBuilder.setDescription(description);

        // Check if the object is not null
        assertNotNull(searchParameterBuilder1);

        // Check if the description is present
        assertTrue(searchParameterBuilder.getDescription().isPresent());

        // Check if the description is the same as the description set
        assertEquals(description, searchParameterBuilder.getDescription().get());
    }

    /**
     * Build test.
     */
// Test if the SearchParameter object can be built
    @Test
    public void buildTest() {
        SearchParameter searchParameter = searchParameterBuilder.setName("Test").build();
        assertNotNull(searchParameter);
        assertTrue(searchParameter.getName().isPresent());
        assertEquals("Test", searchParameter.getName().get());
    }

    /**
     * Build invalid test.
     */
// Test if the SearchParameter object can't be built with invalid parameters
    @Test
    public void buildInvalidTest() {
        assertThrows(IllegalArgumentException.class,
                () -> searchParameterBuilder.setDate(
                        Either.right(new Period(
                                LocalDate.of(2022, 1, 1),
                                LocalDate.of(2021, 1, 1))
                        )
                ).build()
        );
    }
}
