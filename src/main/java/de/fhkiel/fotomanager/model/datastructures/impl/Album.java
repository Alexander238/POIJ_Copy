package de.fhkiel.fotomanager.model.datastructures.impl;

import de.fhkiel.fotomanager.model.datastructures.DataStructure;
import de.fhkiel.fotomanager.model.mediatypes.Media;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Album.
 */
public class Album extends DataStructure<Media> {

    /**
     * Instantiates a new Album.
     *
     * @param name the name
     */
    public Album(String name) {
        super(name);
    }

    /**
     * Instantiates a new Album.
     *
     * @param name      the name
     * @param mediaList the media list
     */
    public Album(String name, List<Media> mediaList) {
        super(name);
        this.getMediaList().addAll(mediaList);
    }

    /**
     * Delete.
     */
    public void delete() {
        this.getMediaList().clear();
        this.setNameToNull();
    }

    /**
     * convert the album to an XML element
     *
     * @param document the document
     * @return the element
     */
    public Element toXML(Document document) {
        Element albumElement = document.createElement("Album");
        albumElement.setAttribute("name", this.getName());

        this.mediaListToXML(document, albumElement);

        return albumElement;
    }

    /**
     * Extract photos from XML list.
     *
     * @param element the element
     * @return the list
     */
    public static Album fromXML(Element element) {
        String name = element.getAttribute("name");
        List<Media> mediaList = new ArrayList<>();
        mediaList.addAll(extractPhotosFromXML(element));
        mediaList.addAll(extractVideosFromXML(element));
        return new Album(name, mediaList);
    }
}
