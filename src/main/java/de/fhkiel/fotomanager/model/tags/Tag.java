package de.fhkiel.fotomanager.model.tags;

import lombok.AllArgsConstructor;
import lombok.Getter;
import javafx.scene.paint.Color;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * The type Tag.
 */
@AllArgsConstructor
@Getter
public class Tag {
    /**
     * The Name.
     */
    private String name;
    /**
     * The Color.
     */
    private Color color;
    /**
     * The TagType.
     */
    private TagType type;

    /**
     * Rename.
     *
     * @param newName the new name
     */
    public void rename(String newName) {
        this.name = newName;
    }

    /**
     * Change color.
     *
     * @param newColor the new color
     */
    public void changeColor(Color newColor) {
        this.color = newColor;
    }

    /**
     * Change type.
     *
     * @param tagType the tag type
     */
    public void changeType(TagType tagType){
        this.type = tagType;
    }

    /**
     * Delete.
     */
    public void delete() {
        this.name = null;
        this.color = null;
        this.type = null;
    }

    /**
     * To xml element.
     *
     * @param document the document
     * @return the element
     */
    public Element toXML(Document document) {
        Element tagElement = document.createElement("Tag");
        tagElement.setAttribute("name", this.getName());
        tagElement.setAttribute("color", colorToHex(this.getColor()));
        tagElement.setAttribute("type", this.getType().name());
        return tagElement;
    }

    /**
     * Color to hex string.
     *
     * @param color the color
     * @return the string
     */
    private String colorToHex(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    /**
     * From xml tag.
     *
     * @param element the element
     * @return the tag
     */
    public static Tag fromXML(Element element) {
        String tagName = element.getAttribute("name");
        Color color;
        try {
            color = hexToColor(element.getAttribute("color"));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid color for tag: " + tagName, e);
        }
        TagType tagType = TagType.valueOf(element.getAttribute("type"));
        return new Tag(tagName, color, tagType);
    }

    /**
     * Hex to color.
     *
     * @param hex the hex
     * @return the color
     */
    private static Color hexToColor(String hex) {
        if (hex == null || hex.length() < 6) {
            throw new IllegalArgumentException("Invalid hex color code: " + hex);
        }

        hex = hex.replace("#", "");

        try {
            int red = Integer.parseInt(hex.substring(0, 2), 16);
            int green = Integer.parseInt(hex.substring(2, 4), 16);
            int blue = Integer.parseInt(hex.substring(4, 6), 16);

            return Color.rgb(red, green, blue);
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Invalid hex color code: " + hex, e);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Tag tag = (Tag) obj;
        return name.equals(tag.name) && color.equals(tag.color) && type == tag.type;
    }
}
