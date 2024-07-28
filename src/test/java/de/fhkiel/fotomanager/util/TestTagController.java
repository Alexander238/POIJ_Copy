package de.fhkiel.fotomanager.util;

import de.fhkiel.fotomanager.model.tags.Tag;
import de.fhkiel.fotomanager.model.tags.TagType;

import javafx.scene.paint.Color;

/**
 * The type Test tag controller.
 */
public class TestTagController {

    /**
     * The constant instance.
     */
    public static TestTagController instance;
    /**
     * The constant defaultName.
     */
    public static String defaultName = "Tag";
    /**
     * The constant defaultType.
     */
    public static TagType defaultType = TagType.INDEX;
    /**
     * The constant defaultColor.
     */
    public static Color defaultColor = Color.WHITE;

    private TestTagController() {}

    /**
     * Create default tag tag.
     *
     * @return the tag
     */
    public static Tag createDefaultTag() {
        return new Tag(defaultName, defaultColor, defaultType);
    }
}
