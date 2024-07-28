package de.fhkiel.fotomanager.model.tags;

import de.fhkiel.fotomanager.util.TestXMLController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.paint.Color;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.ParserConfigurationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * The type Tag test.
 */
public class TagTest {

    /**
     * The Tag to be tested.
     */
    private Tag tag;

    /**
     * Sets up.
     */
// Set up a new Tag before each test
    @BeforeEach
    public void setUp() {
        tag = new Tag("Test", Color.BLACK, TagType.INDEX);
    }

    /**
     * Create tag test.
     */
// Test the instantiation of a new Tag
    @Test
    public void createTagTest() {
        // Check if the tag name is set correctly
        assertEquals("Test", tag.getName());

        // Check if the tag color is set correctly
        assertEquals(Color.BLACK, tag.getColor());

        // Check if the tag type is set correctly
        assertEquals(TagType.INDEX, tag.getType());
    }

    /**
     * Rename test.
     */
// Test the rename function to set a new name
    @Test
    public void renameTest() {
        String newName = "NewName";
        tag.rename(newName);

        // Check if the tag name is set to the new name
        assertEquals(newName, tag.getName());
    }

    /**
     * Change color test.
     */
// Test the changeColor function to set a new color
    @Test
    public void changeColorTest() {
        Color newColor = Color.RED;
        tag.changeColor(newColor);

        // Check if the tag color is set to the new color
        assertEquals(newColor, tag.getColor());
    }

    /**
     * Delete test.
     */
// Test the delete function to set all attributes to null
    // so that the garbage collector can delete the object.
    // The TagController should also remove the tag from the list of tags and all media.
    @Test
    public void deleteTest() {
        tag.delete();

        // Check if name is set to null
        assertEquals(null, tag.getName());

        // Check if color is set to null
        assertEquals(null, tag.getColor());

        // Check if type is set to null
        assertEquals(null, tag.getType());
    }

    /**
     * Change type test.
     */
// Test the changeType function to set a new type
    @Test
    public void changeTypeTest() {
        TagType newType = TagType.PERSON;
        tag.changeType(newType);

        // Check if the tag type is set to the new type
        assertEquals(newType, tag.getType());
    }

    /**
     * From invalid color xml test.
     *
     * @throws ParserConfigurationException the parser configuration exception
     */
    @Test
    public void fromInvalidColorXMLTest() throws ParserConfigurationException {
        Element tagElement = TestXMLController.createInvalidColorTag();

        // Check if the function throws an IllegalArgumentException when the color is invalid
        assertThrows(IllegalArgumentException.class, () -> Tag.fromXML(tagElement));
    }

    /**
     * From xml test.
     *
     * @throws ParserConfigurationException the parser configuration exception
     */
    @Test
    public void fromXMLTest() throws ParserConfigurationException {
        Element tagElement = TestXMLController.createTag();

        // Check if the function returns a new Tag with the correct attributes
        Tag newTag = Tag.fromXML(tagElement);
        assertEquals("TestTag", newTag.getName());
        assertEquals(Color.WHITE, newTag.getColor());
        assertEquals(TagType.INDEX, newTag.getType());
    }

    /**
     * To xml test.
     *
     * @throws ParserConfigurationException the parser configuration exception
     */
    @Test
    public void toXMLTest() throws ParserConfigurationException {
        Document document = TestXMLController.createDocument();
        Element tagElement = tag.toXML(document);

        // Check if the function returns an Element with the correct attributes
        assertEquals("Tag", tagElement.getTagName());
        assertEquals("Test", tagElement.getAttribute("name"));
        assertEquals("#000000", tagElement.getAttribute("color"));
        assertEquals("INDEX", tagElement.getAttribute("type"));
    }
}
