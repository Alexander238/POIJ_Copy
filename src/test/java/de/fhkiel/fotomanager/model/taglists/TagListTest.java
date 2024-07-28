package de.fhkiel.fotomanager.model.taglists;

import de.fhkiel.fotomanager.controller.modelController.TagController;
import de.fhkiel.fotomanager.model.tags.Tag;
import de.fhkiel.fotomanager.model.tags.TagType;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Tag list test.
 */
public class TagListTest {

    /**
     * TagController is needed to create Tags for the tests
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
     * Create tag list test.
     */
// Test if a TagList can be created
    @Test
    public void createTagListTest() {
        TagList indexList = new TagList();

        // Check if the TagList exists
        assertNotNull(indexList);

        // Check if the TagList is empty
        assertEquals(0, indexList.getTags().size());
    }

    /**
     * Add tag test.
     */
// Test if a Tag can be added to a TagList
    @Test
    public void addTagTest() {
        TagList indexList = new TagList();
        Tag index = tagController.createTag(TagType.INDEX, "Index");
        indexList.addTag(index);

        // Check if the TagList contains the added Tag
        assertTrue(indexList.getTags().contains(index));

        // Check if the TagList has a size of 1
        assertEquals(1, indexList.getTags().size());
    }

    /**
     * Remove tag test.
     */
// Test if a Tag can be removed from a TagList
    @Test
    public void removeTagTest() {
        TagList indexList = new TagList();
        Tag index = tagController.createTag(TagType.INDEX, "Index");
        indexList.addTag(index);
        indexList.removeTag(index);

        // Check if the TagList does not contain the removed Tag
        assertFalse(indexList.getTags().contains(index));

        // Check if the TagList is empty again
        assertEquals(0, indexList.getTags().size());
    }

    /**
     * Set all tags test.
     */
// Test if all Tags can be set in a TagList
    @Test
    public void setAllTagsTest() {
        TagList indexList = new TagList();
        Tag index = tagController.createTag(TagType.INDEX, "Index");
        Tag adjective = tagController.createTag(TagType.ADJECTIVE, "Adjective");
        indexList.addTag(index);
        indexList.addTag(adjective);

        // Check if the TagList contains the added Tags
        assertTrue(indexList.getTags().contains(index));
        assertTrue(indexList.getTags().contains(adjective));

        // Check if the TagList has a size of 2
        assertEquals(2, indexList.getTags().size());

        // Create a new List of Tags
        TagList newTagList = new TagList();
        newTagList.setAllTags(indexList.getTags());

        // Check if the new TagList contains the Tags from the old TagList
        assertTrue(newTagList.getTags().contains(index));
        assertTrue(newTagList.getTags().contains(adjective));

        // Check if the new TagList has a size of 2
        assertEquals(2, newTagList.getTags().size());
    }

    /**
     * Gets observable tags test.
     */
// Test if the observable tags can be retrieved from a TagList
    @Test
    public void getObservableTagsTest() {
        TagList tagList = new TagList();
        Tag index = tagController.createTag(TagType.INDEX, "Index");
        Tag adjective = tagController.createTag(TagType.ADJECTIVE, "Adjective");
        tagList.addTag(index);
        tagList.addTag(adjective);

        // Check if the observable tags contain the Tags from the TagList
        assertTrue(tagList.getObservableTags().contains(index));
        assertTrue(tagList.getObservableTags().contains(adjective));

        // Check if the observable tags have a size of 2
        assertEquals(2, tagList.getObservableTags().size());

        // Check if the observable tags are not the same as the Tags from the TagList
        assertNotSame(tagList.getTags(), tagList.getObservableTags());

        // Check if the observable tags are from the type ObservableList
        assertTrue(tagList.getObservableTags() instanceof ObservableList);
    }

    /**
     * Gets observable adjective tags test.
     */
// Test if the observable adjective tags can be retrieved from a TagList
    @Test
    public void getObservableAdjectiveTagsTest() {
        TagList tagList = new TagList();
        Tag index = tagController.createTag(TagType.INDEX, "Index");
        Tag adjective = tagController.createTag(TagType.ADJECTIVE, "Adjective");
        tagList.addTag(index);
        tagList.addTag(adjective);

        // Check if the observable tags contain the adjective tags
        assertTrue(tagList.getObservableAdjectiveTags().contains(adjective));

        // Check if the observable tags have a size of 2
        assertEquals(1, tagList.getObservableAdjectiveTags().size());

        // Check if the observable tags are from the type ObservableList
        assertTrue(tagList.getObservableAdjectiveTags() instanceof ObservableList);
    }

    /**
     * Gets observable person tags test.
     */
// Test if the observable person tags can be retrieved from a TagList
    @Test
    public void getObservablePersonTagsTest() {
        TagList tagList = new TagList();
        Tag index = tagController.createTag(TagType.INDEX, "Index");
        Tag person = tagController.createTag(TagType.PERSON, "Person");
        tagList.addTag(index);
        tagList.addTag(person);

        // Check if the observable tags contain the person tags
        assertTrue(tagList.getObservablePersonTags().contains(person));

        // Check if the observable tags have a size of 2
        assertEquals(1, tagList.getObservablePersonTags().size());

        // Check if the observable tags are from the type ObservableList
        assertTrue(tagList.getObservablePersonTags() instanceof ObservableList);
    }

    /**
     * Gets observable location tags test.
     */
// Test if the observable location tags can be retrieved from a TagList
    @Test
    public void getObservableLocationTagsTest() {
        TagList tagList = new TagList();
        Tag index = tagController.createTag(TagType.INDEX, "Index");
        Tag location = tagController.createTag(TagType.LOCATION, "Location");
        tagList.addTag(index);
        tagList.addTag(location);

        // Check if the observable tags contain the location tags
        assertTrue(tagList.getObservableLocationTags().contains(location));

        // Check if the observable tags have a size of 2
        assertEquals(1, tagList.getObservableLocationTags().size());

        // Check if the observable tags are from the type ObservableList
        assertTrue(tagList.getObservableLocationTags() instanceof ObservableList);
    }

    /**
     * Gets observable index tags test.
     */
// Test if the observable index tags can be retrieved from a TagList
    @Test
    public void getObservableIndexTagsTest() {
        TagList tagList = new TagList();
        Tag index = tagController.createTag(TagType.INDEX, "Index");
        Tag index2 = tagController.createTag(TagType.INDEX, "Index2");
        tagList.addTag(index);
        tagList.addTag(index2);

        // Check if the observable tags contain the index tags
        assertTrue(tagList.getObservableIndexTags().contains(index));
        assertTrue(tagList.getObservableIndexTags().contains(index2));

        // Check if the observable tags have a size of 2
        assertEquals(2, tagList.getObservableIndexTags().size());

        // Check if the observable tags are from the type ObservableList
        assertTrue(tagList.getObservableIndexTags() instanceof ObservableList);
    }
}
