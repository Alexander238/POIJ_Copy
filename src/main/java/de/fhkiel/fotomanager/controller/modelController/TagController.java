package de.fhkiel.fotomanager.controller.modelController;

import de.fhkiel.fotomanager.model.tags.Tag;
import de.fhkiel.fotomanager.model.tags.TagType;
import javafx.scene.paint.Color;

/**
 * The type Tag controller.
 */
public class TagController {
    /**
     * The constant instance.
     */
    private static TagController instance;

    private TagController() {}

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static synchronized TagController get() {
        if (instance == null) {
            instance = new TagController();
        }
        return instance;
    }

    /**
     * Create tag tag.
     *
     * @param type the type
     * @param name the name
     * @return the tag
     */
    public Tag createTag(TagType type, String name) {
        return new Tag(name, Color.BLACK, type);
    }

    /**
     * Create tag tag.
     *
     * @param type  the type
     * @param name  the name
     * @param color the color
     * @return the tag
     */
    public Tag createTag(TagType type, String name, Color color) {
        return new Tag(name, color, type);
    }

    /**
     * Rename tag.
     *
     * @param tag  the tag
     * @param name the name
     */
    public void renameTag(Tag tag, String name) {
        tag.rename(name);
    }

    /**
     * Change tag color.
     *
     * @param tag   the tag
     * @param color the color
     */
    public void changeTagColor(Tag tag, Color color) {
        tag.changeColor(color);
    }

    /**
     * Change tagType of a type
     *
     * @param tag     the tag
     * @param tagType the tag type
     */
    public void changeTagType(Tag tag, TagType tagType) { tag.changeType(tagType); }


    /**
     * Deletes a tag from all photos and videos and removes it from the tag list.
     *
     * @param tag the tag
     */
    public void deleteTag(Tag tag) {
        PhotoManagerController.get().getPhotoManager().getPhotos().forEach(photo -> photo.removeTag(tag));
        PhotoManagerController.get().getPhotoManager().getVideos().forEach(video -> video.removeTag(tag));
        TagListController.get().removeTag(PhotoManagerController.get().getPhotoManager().getTagList(), tag);
        tag.delete();
    }
}
