package de.fhkiel.fotomanager.controller;

import de.fhkiel.fotomanager.controller.modelController.PhotoManagerController;
import de.fhkiel.fotomanager.controller.modelController.TagController;
import de.fhkiel.fotomanager.controller.modelController.TagListController;
import de.fhkiel.fotomanager.model.taglists.TagList;
import de.fhkiel.fotomanager.model.tags.Tag;
import de.fhkiel.fotomanager.model.tags.TagType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Tag list controller test.
 */
public class TagListControllerTest {
    /**
     * The Tag list controller.
     */
    private static TagListController tagListController;
    /**
     * The Tag controller.
     */
    private static TagController tagController;
    /**
     * The Photo manager controller.
     */
    private static PhotoManagerController photoManagerController;
    /**
     * A Tag list.
     */
    private static TagList tagList;

    /**
     * Sets up.
     */
    @BeforeAll
    public static void setUp() {
        tagListController = TagListController.get();
        tagController = TagController.get();
        photoManagerController = PhotoManagerController.get();
    }

    /**
     * Clear tag lists.
     */
    @BeforeEach
    public void clearTagLists() {
        tagList = photoManagerController.getPhotoManager().getTagList();
        tagList.getTags().clear();
    }

    /**
     * Gets singleton instance test.
     */
    @Test
    public void getSingletonInstanceTest() {
        TagListController anotherInstance = TagListController.get();
        assertNotNull(tagListController, "TagListController instance should not be null");
        assertSame(tagListController, anotherInstance, "TagListController instance should be the same");
    }

    /**
     * Add index test.
     */
    @Test
    public void addIndexTest() {
        Tag index = tagController.createTag(TagType.INDEX, "Index");
        tagListController.addTag(tagList, index);
        assertNotNull(tagList, "TagList should not be null");
        assertTrue(tagList.getTags().contains(index), "Index should be added to TagList");
        assertEquals(1, tagList.getTags().size(), "TagList should contain 1 Index");
    }

    /**
     * Remove index test.
     */
    @Test
    public void removeIndexTest() {
        Tag index = tagController.createTag(TagType.INDEX, "Index");
        tagListController.addTag(tagList, index);
        tagListController.removeTag(tagList, index);
        assertFalse(tagList.getTags().contains(index), "Index should be removed from IndexList");
        assertEquals(0, tagList.getTags().size(), "IndexList should be empty");
    }

    /**
     * Add adjective test.
     */
    @Test
    public void addAdjectiveTest() {
        Tag adjective = tagController.createTag(TagType.ADJECTIVE, "Adjective");
        tagListController.addTag(tagList, adjective);
        assertNotNull(tagList, "AdjectiveList should not be null");
        assertTrue(tagList.getTags().contains(adjective), "Adjective should be added to AdjectiveList");
        assertEquals(1, tagList.getTags().size(), "AdjectiveList should contain 1 Adjective");
    }

    /**
     * Remove adjective test.
     */
    @Test
    public void removeAdjectiveTest() {
        Tag adjective = tagController.createTag(TagType.ADJECTIVE, "Adjective");
        tagListController.addTag(tagList, adjective);
        tagListController.removeTag(tagList, adjective);
        assertFalse(tagList.getTags().contains(adjective), "Adjective should be removed from AdjectiveList");
        assertEquals(0, tagList.getTags().size(), "AdjectiveList should be empty");
    }

    /**
     * Add person test.
     */
    @Test
    public void addPersonTest() {
        Tag person = tagController.createTag(TagType.PERSON, "Person");
        tagListController.addTag(tagList, person);
        assertNotNull(tagList, "PersonList should not be null");
        assertTrue(tagList.getTags().contains(person), "Person should be added to PersonList");
        assertEquals(1, tagList.getTags().size(), "PersonList should contain 1 Person");
    }

    /**
     * Remove person test.
     */
    @Test
    public void removePersonTest() {
        Tag person = tagController.createTag(TagType.PERSON, "Person");
        tagListController.addTag(tagList, person);
        tagListController.removeTag(tagList, person);
        assertFalse(tagList.getTags().contains(person), "Person should be removed from PersonList");
        assertEquals(0, tagList.getTags().size(), "PersonList should be empty");
    }

    /**
     * Add location test.
     */
    @Test
    public void addLocationTest() {
        Tag location = tagController.createTag(TagType.LOCATION, "Location");
        tagListController.addTag(tagList, location);
        assertNotNull(tagList, "LocationList should not be null");
        assertTrue(tagList.getTags().contains(location), "Location should be added to LocationList");
        assertEquals(1, tagList.getTags().size(), "LocationList should contain 1 Location");
    }

    /**
     * Remove location test.
     */
    @Test
    public void removeLocationTest() {
        Tag location = tagController.createTag(TagType.LOCATION, "Location");
        tagListController.addTag(tagList, location);
        tagListController.removeTag(tagList, location);
        assertFalse(tagList.getTags().contains(location), "Location should be removed from LocationList");
        assertEquals(0, tagList.getTags().size(), "LocationList should be empty");
    }

    /**
     * Add duplicate index test.
     */
    @Test
    public void addDuplicateIndexTest() {
        Tag index = tagController.createTag(TagType.INDEX, "Index");
        tagListController.addTag(tagList, index);
        assertThrows(IllegalArgumentException.class, () -> tagListController.addTag(tagList, index), "IllegalArgumentException should be thrown when adding duplicate Index");
    }

    /**
     * Add duplicate adjective test.
     */
    @Test
    public void addDuplicateAdjectiveTest() {
        Tag adjective = tagController.createTag(TagType.ADJECTIVE, "Adjective");
        tagListController.addTag(tagList, adjective);
        assertThrows(IllegalArgumentException.class, () -> tagListController.addTag(tagList, adjective), "IllegalArgumentException should be thrown when adding duplicate Adjective");
    }

    /**
     * Add duplicate person test.
     */
    @Test
    public void addDuplicatePersonTest() {
        Tag person = tagController.createTag(TagType.PERSON, "Person");
        tagListController.addTag(tagList, person);
        assertThrows(IllegalArgumentException.class, () -> tagListController.addTag(tagList, person), "IllegalArgumentException should be thrown when adding duplicate Person");
    }

    /**
     * Add duplicate location test.
     */
    @Test
    public void addDuplicateLocationTest() {
        Tag location = tagController.createTag(TagType.LOCATION, "Location");
        tagListController.addTag(tagList, location);
        assertThrows(IllegalArgumentException.class, () -> tagListController.addTag(tagList, location), "IllegalArgumentException should be thrown when adding duplicate Location");
    }
}

