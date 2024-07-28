package de.fhkiel.fotomanager.model.datastructures.impl;

import de.fhkiel.fotomanager.model.Period;
import de.fhkiel.fotomanager.model.datastructures.DataStructure;
import de.fhkiel.fotomanager.model.mediatypes.Media;
import io.vavr.control.Either;
import lombok.Getter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Folder.
 */
@Getter
public class Folder extends DataStructure<Media> {
    /**
     * The Date or period of a folder.
     */
    private Either<LocalDate, Period> date;
    /**
     * The Event of a folder.
     */
    private String event;

    /**
     * Instantiates a new Folder.
     *
     * @param name  the name
     * @param date  the date
     * @param event the event
     */
    public Folder(String name, Either<LocalDate, Period> date, String event) {
        super(name);
        this.date = date;
        this.event = event;
    }

    /**
     * Delete.
     */
    public void delete() {
        this.getMediaList().clear();
        this.event = null;
        this.date = null;
        this.setNameToNull();
    }

    /**
     * Instantiates a new Folder with media list.
     *
     * @param name      the name
     * @param date      the date
     * @param event     the event
     * @param mediaList the media list
     */
    public Folder(String name, Either<LocalDate, Period> date, String event, List<Media> mediaList) {
        super(name);
        this.date = date;
        this.event = event;
        this.getMediaList().addAll(mediaList);
    }

    /**
     * function to convert a folder to an XML element
     *
     * @param document the document to create the element in
     * @return the created element
     */
    public Element toXML(Document document) {
        Element folderElement = document.createElement("Folder");
        folderElement.setAttribute("name", this.getName());
        folderElement.setAttribute("date", this.getDate().toString());
        folderElement.setAttribute("event", this.getEvent());

        this.mediaListToXML(document, folderElement);

        return folderElement;
    }

    /**
     * function to convert an XML element to a folder
     *
     * @param element the element to convert
     * @return the created folder
     */
    public static Folder fromXML(Element element) {
        String name = element.getAttribute("name");
        Either<LocalDate, Period> date = parseDate(element.getAttribute("date"));
        String event = element.getAttribute("event");
        List<Media> mediaList = new ArrayList<>();
        mediaList.addAll(extractPhotosFromXML(element));
        mediaList.addAll(extractVideosFromXML(element));
        return new Folder(name, date, event, mediaList);
    }

    /**
     * function to parse a date string to a date or period
     *
     * @param date the date string to parse
     * @return the parsed date or period
     */
    public static Either<LocalDate, Period> parseDate(String date) {
        if (date.startsWith("Left(") && date.endsWith(")")) {
            return Either.left(LocalDate.parse(date.substring(5, date.length() - 1)));
        } else if (date.startsWith("Right(") && date.endsWith(")")) {
            // Remove "Right(" from the start and ")" from the end
            String dateRange = date.substring(6, date.length() - 1);
            // Split the date range by ", "
            String[] dateParts = dateRange.split(", ");
            LocalDate startDate = LocalDate.parse(dateParts[0].trim());
            LocalDate endDate = LocalDate.parse(dateParts[1].trim());
            return Either.right(new Period(startDate, endDate));
        }
        return null;
    }
}
