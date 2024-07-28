package de.fhkiel.fotomanager.model.taglists;

import de.fhkiel.fotomanager.model.mediatypes.Media;
import de.fhkiel.fotomanager.model.tags.Tag;
import de.fhkiel.fotomanager.model.tags.TagType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Tag list.
 */
@Getter
@NoArgsConstructor
public class TagList {
    /**
     * The list of tags.
     */
    private List<Tag> tags = new ArrayList<>();

    /**
     * Add tag.
     *
     * @param tag the tag
     */
    public void addTag(Tag tag) {
        tags.add(tag);
    }

    /**
     * Remove tag.
     *
     * @param tag the tag
     */
    public void removeTag(Tag tag) {
        tags.remove(tag);
    }

    /**
     * Sets all tags.
     *
     * @param tags the tags
     */
    public void setAllTags(List<Tag> tags) {
        this.tags = tags;
    }

    /**
     * Gets observable tags.
     *
     * @return the observable tags
     */
    public ObservableList<Tag> getObservableTags() {
        ObservableList<Tag> observableList = FXCollections.observableArrayList();
        observableList.addAll(tags);
        return observableList;
    }

    /**
     * Gets observable adjective tags.
     *
     * @return the observable adjective tags
     */
    public ObservableList<Tag> getObservableAdjectiveTags() {
        ObservableList<Tag> observableList = FXCollections.observableArrayList();
        for (Tag tag : tags) {
            if (tag.getType().equals(TagType.ADJECTIVE)) {
                observableList.add(tag);
            }
        }
        return observableList;
    }

    /**
     * Gets observable person tags.
     *
     * @return the observable person tags
     */
    public ObservableList<Tag> getObservablePersonTags() {
        ObservableList<Tag> observableList = FXCollections.observableArrayList();
        for (Tag tag : tags) {
            if (tag.getType().equals(TagType.PERSON)) {
                observableList.add(tag);
            }
        }
        return observableList;
    }

    /**
     * Gets observable location tags.
     *
     * @return the observable location tags
     */
    public ObservableList<Tag> getObservableLocationTags() {
        ObservableList<Tag> observableList = FXCollections.observableArrayList();
        for (Tag tag : tags) {
            if (tag.getType().equals(TagType.LOCATION)) {
                observableList.add(tag);
            }
        }
        return observableList;
    }

    /**
     * Gets observable index tags.
     *
     * @return the observable index tags
     */
    public ObservableList<Tag> getObservableIndexTags() {
        ObservableList<Tag> observableList = FXCollections.observableArrayList();
        for (Tag tag : tags) {
            if (tag.getType().equals(TagType.INDEX)) {
                observableList.add(tag);
            }
        }
        return observableList;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TagList tagList)) {
            return false;
        }

        for (Tag tag : tags) {
            boolean contains = false;
            for (Tag otherTag : tagList.getTags()) {
                if (tag.equals(otherTag)) {
                    contains = true;
                    break;
                }
            }

            if (!contains) {
                return false;
            }
        }
        return true;
    }
}
