package de.fhkiel.fotomanager.controller.modelController;

import de.fhkiel.fotomanager.model.taglists.TagList;
import de.fhkiel.fotomanager.model.tags.Tag;
import lombok.Getter;

import java.util.List;

/**
 * The type Tag list controller.
 */
@Getter
public class TagListController {
    /**
     * The constant instance.
     */
    private static TagListController instance;

    private TagListController() {}

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static synchronized TagListController get() {
        if (instance == null) {
            instance = new TagListController();
        }
        return instance;
    }

    /**
     * Create empty tag list.
     * @return the tag list
     */
    public TagList createEmptyTagList() {
        return new TagList();
    }

    /**
     * Create tag list with tags.
     * @param tags the list of tags
     * @return the tag list
     */
    public TagList createTagList(List<Tag> tags) {
        TagList tagList = new TagList();
        for (Tag tag : tags) {
            tagList.addTag(tag);
        }
        return tagList;
    }

    /**
     * Add tag.
     *
     * @param tagList the tag list
     * @param tag     the tag
     */
    public void addTag(TagList tagList, Tag tag) {
         // check if tag with same name and type already exists
         for (Tag t : tagList.getTags()) {
             if (t.getName().equals(tag.getName()) && t.getType().equals(tag.getType())) {
                 throw new IllegalArgumentException("Tag with name '" + tag.getName() + "' and type '" + tag.getType() + "' already exists");
             }
         }
         tagList.addTag(tag);
    }

    /**
     * Remove tag.
     *
     * @param tagList the tag list
     * @param tag     the tag
     */
    public void removeTag(TagList tagList, Tag tag) {
        tagList.removeTag(tag);
    }
}
