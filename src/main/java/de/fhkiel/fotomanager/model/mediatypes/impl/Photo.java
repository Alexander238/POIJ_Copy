package de.fhkiel.fotomanager.model.mediatypes.impl;

import de.fhkiel.fotomanager.controller.modelController.TagListController;
import de.fhkiel.fotomanager.model.mediatypes.Media;
import de.fhkiel.fotomanager.model.mediatypes.Orientation;
import de.fhkiel.fotomanager.model.mediatypes.Rating;
import de.fhkiel.fotomanager.model.mediatypes.Resolution;
import de.fhkiel.fotomanager.model.tags.Tag;
import de.fhkiel.fotomanager.model.taglists.TagList;
import lombok.Getter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Photo.
 */
@Getter
public class Photo extends Media {
    /**
     * The Zoom factor.
     */
    private double zoomFactor;
    /**
     * The step size for zooming in and out.
     */
    private final double ZOOM_STEP_SIZE = 0.1;

    /**
     * Instantiates a new Photo.
     *
     * @param file        the file
     * @param name        the name
     * @param date        the date
     * @param description the description
     * @param isPrivate   the is private
     * @param tags        the tags
     * @param zoomFactor  the zoom factor
     * @param rating      the rating
     * @param orientation the orientation
     * @param resolution  the resolution
     */
    public Photo(
            String file,
            String name,
            LocalDate date,
            String description,
            boolean isPrivate,
            TagList tags,
            double zoomFactor,
            Rating rating,
            Orientation orientation,
            Resolution resolution
    ) {
        super(file, name, date, description, isPrivate, tags, rating, orientation, resolution);
        this.zoomFactor = zoomFactor;
    }

    /**
     * Instantiates a new Photo with only the file path.
     *
     * @param filePath the file path
     */
    public Photo(String filePath) {
        super(new File(filePath).getPath(), extractFileName(filePath), LocalDate.now(), "No Description", false, null, Rating.ZERO_STARS, Orientation.D0, new Resolution(1920, 1080));
        this.zoomFactor = 0;
    }

    /**
     * Extracts the file name from a file path.
     *
     * @param filePath the file path
     * @return the file name
     */
    private static String extractFileName(String filePath) {
        File file = new File(filePath);
        return file.getName();
    }

    /**
     * Delete a photo
     * @return
     */
    @Override
    public boolean delete() {
        super.delete();
        this.zoomFactor = 0;
        return true;
    }

    /**
     * Zoom into a photo.
     */
    public void zoomIn() {
        this.zoomFactor += this.ZOOM_STEP_SIZE;
    }

    /**
     * Zoom out from a photo.
     */
    public void zoomOut() {
        this.zoomFactor -= this.ZOOM_STEP_SIZE;
    }

    /**
     * Converts a photo to an XML element
     *
     * @param document the XML document
     * @return the XML element
     */
    public Element toXML(Document document) {
        Element element = document.createElement("Photo");
        element.setAttribute("file", this.getFile());
        element.setAttribute("name", this.getName());
        element.setAttribute("date", this.getDate().toString());
        element.setAttribute("description", this.getDescription());
        element.setAttribute("isPrivate", String.valueOf(this.isPrivate()));
        element.setAttribute("zoomFactor", String.valueOf(this.getZoomFactor()));
        element.setAttribute("rating", this.getRating().name());
        element.setAttribute("orientation", this.getOrientation().name());
        element.setAttribute("resolution", this.getResolution().getWidth() + "x" + this.getResolution().getHeight());
        for (Tag tag : this.getTags().getTags()) {
            Element tagElement = tag.toXML(document);
            element.appendChild(tagElement);
        }
        return element;
    }

    /**
     * Converts a photo from an XML element
     *
     * @param element the XML element
     * @return the photo
     */
    public static Photo fromXML(Element element) {
        String name = element.getAttribute("name");
        String fileString = element.getAttribute("file");
        LocalDate date = LocalDate.parse(element.getAttribute("date"));
        String description = element.getAttribute("description");
        boolean isPrivate = Boolean.parseBoolean(element.getAttribute("isPrivate"));
        double zoomFactor = Double.parseDouble(element.getAttribute("zoomFactor"));
        Rating rating = Rating.valueOf(element.getAttribute("rating"));
        Orientation orientation = Orientation.valueOf(element.getAttribute("orientation"));
        String[] resolutionParts = element.getAttribute("resolution").split("x");
        long width = Long.parseLong(resolutionParts[0]);
        long height = Long.parseLong(resolutionParts[1]);
        Resolution resolution = new Resolution(width, height);
        TagList tags = Photo.extractTags(element);
        return new Photo(fileString, name, date, description, isPrivate, tags, zoomFactor, rating, orientation, resolution);
    }

    /**
     * Extract Tags from XML element
     *
     * @param element the XML element
     * @return the list of tags
     */
    public static TagList extractTags(Element element) {
        TagList tags = TagListController.get().createEmptyTagList();
        NodeList tagNodes = element.getElementsByTagName("Tag");
        for (int i = 0; i < tagNodes.getLength(); i++) {
            tags.addTag(Tag.fromXML((Element) tagNodes.item(i)));
        }
        return tags;
    }

    @Override
    /**
     * Checks if the photo matches the search parameters
     *
     * @param obj
     * @return true if the photo matches the search parameters
     */
    public boolean equals(Object obj) {
        if (obj instanceof Photo) {
            Photo photo = (Photo) obj;
            return this.getFile().equals(photo.getFile())
                    && this.getName().equals(photo.getName())
                    && this.getDate().equals(photo.getDate())
                    && this.getDescription().equals(photo.getDescription())
                    && this.isPrivate() == photo.isPrivate()
                    && this.getTags().equals(photo.getTags())
                    && this.getZoomFactor() == photo.getZoomFactor()
                    && this.getRating().equals(photo.getRating())
                    && this.getOrientation().equals(photo.getOrientation())
                    && this.getResolution().equals(photo.getResolution());
        }
        return false;
    }
}
