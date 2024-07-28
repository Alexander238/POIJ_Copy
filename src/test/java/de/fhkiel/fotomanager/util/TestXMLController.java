package de.fhkiel.fotomanager.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * The type Test xml controller.
 */
public class TestXMLController {

    /**
     * Create tag element.
     *
     * @return the element
     * @throws ParserConfigurationException the parser configuration exception
     */
    public static Element createTag() throws ParserConfigurationException {
        String tagName = "TestTag";
        String tagColor = "ffffff";
        String tagType = "INDEX";

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();

        Document document = documentBuilder.newDocument();
        Element tagElement = document.createElement("Photo");
        tagElement.setAttribute("name", tagName);
        tagElement.setAttribute("color", tagColor);
        tagElement.setAttribute("type", tagType);

        return tagElement;
    }

    /**
     * Create invalid color tag element.
     *
     * @return the element
     * @throws ParserConfigurationException the parser configuration exception
     */
    public static Element createInvalidColorTag() throws ParserConfigurationException {
        String tagName = "TestTag";
        String tagColor = "INVALID_COLOR";
        String tagType = "INDEX";

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();

        Document document = documentBuilder.newDocument();
        Element tagElement = document.createElement("Photo");
        tagElement.setAttribute("name", tagName);
        tagElement.setAttribute("color", tagColor);
        tagElement.setAttribute("type", tagType);

        return tagElement;
    }

    /**
     * Create folder element element.
     *
     * @return the element
     * @throws ParserConfigurationException the parser configuration exception
     */
    public static Element createFolderElement() throws ParserConfigurationException {
        String folderName = "TestFolder";
        String event = "TestEvent";

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();

        Document document = documentBuilder.newDocument();
        Element folderElement = document.createElement("Folder");
        folderElement.setAttribute("name", folderName);
        folderElement.setAttribute("date", "Left(2021-01-01)");
        folderElement.setAttribute("event", event);

        Element photoElement = document.createElement("Photo");
        photoElement.setAttribute("file", TestUtilMediaController.defaultFile);
        photoElement.setAttribute("name", TestUtilMediaController.defaultName);
        photoElement.setAttribute("date", "2021-01-01");
        photoElement.setAttribute("description", TestUtilMediaController.defaultDescription);
        photoElement.setAttribute("isPrivate", "false");
        photoElement.setAttribute("zoomFactor", "1.0");
        photoElement.setAttribute("rating", "ZERO_STARS");
        photoElement.setAttribute("orientation", "D0");
        photoElement.setAttribute("resolution", "1920x1080");
        folderElement.appendChild(photoElement);

        Element videoElement = document.createElement("Video");
        videoElement.setAttribute("file", TestUtilMediaController.defaultFile);
        videoElement.setAttribute("name", TestUtilMediaController.defaultName);
        videoElement.setAttribute("date", "2021-01-01");
        videoElement.setAttribute("description", TestUtilMediaController.defaultDescription);
        videoElement.setAttribute("isPrivate", "false");
        videoElement.setAttribute("duration", String.valueOf(TestUtilMediaController.defaultDuration));
        videoElement.setAttribute("orientation", "D0");
        videoElement.setAttribute("resolution", "1920x1080");
        videoElement.setAttribute("playbackSpeed", "X1");
        videoElement.setAttribute("rating", "ZERO_STARS");
        folderElement.appendChild(videoElement);

        return folderElement;
    }

    /**
     * Create document document.
     *
     * @return the document
     * @throws ParserConfigurationException the parser configuration exception
     */
    public static Document createDocument() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        return documentBuilder.newDocument();
    }

}
