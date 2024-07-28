package de.fhkiel.fotomanager.controller.modelController;

import de.fhkiel.fotomanager.ConfigLoader;
import de.fhkiel.fotomanager.controller.viewController.components.ShowAlert;
import de.fhkiel.fotomanager.model.datastructures.impl.Album;
import de.fhkiel.fotomanager.model.datastructures.impl.Folder;
import de.fhkiel.fotomanager.model.datastructures.impl.Slideshow;
import de.fhkiel.fotomanager.model.mediatypes.Media;
import de.fhkiel.fotomanager.model.mediatypes.impl.Photo;
import de.fhkiel.fotomanager.model.mediatypes.impl.Video;
import de.fhkiel.fotomanager.model.taglists.TagList;
import de.fhkiel.fotomanager.model.tags.Tag;
import javafx.scene.paint.Color;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Xml controller.
 */
public class XMLController {
    /**
     * The constant instance.
     */
    private static XMLController instance;
    /**
     * The Config loader.
     */
    private final ConfigLoader configLoader = new ConfigLoader();
    /**
     * The Path to the XMLSaveFolder.
     */
    private final String pathToXmlSaveFolder = configLoader.getValue("PATH_TO_XML_SAVE_FOLDER");
    /**
     * The Path to the media folder.
     */
    private final String pathToMediaFolder = configLoader.getValue("PATH_TO_MEDIA_FOLDER");

    /**
     * The constant LOGGER.
     */
    private static final Logger LOGGER = Logger.getLogger(XMLController.class.getName());

    /**
     * The enum Data structure type.
     */
    public enum DataStructureType {
        /**
         * Album data structure type.
         */
        ALBUM,
        /**
         * Folder data structure type.
         */
        FOLDER,
        /**
         * Slideshow data structure type.
         */
        SLIDESHOW
    }

    /**
     * The enum Media type.
     */
    public enum MediaType {
        /**
         * Photo media type.
         */
        PHOTO,
        /**
         * Video media type.
         */
        VIDEO
    }

    private XMLController() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static synchronized XMLController get() {
        if (instance == null) {
            instance = new XMLController();
        }
        return instance;
    }

    /**
     * Transform Document to File
     *
     * @param fileName the file name
     * @param document the document
     * @throws TransformerException the transformer exception
     */
    private void transformDocumentToFile(String fileName, Document document) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(fileName));

        transformer.transform(source, result);
    }

    /**
     * Method to convert color to hex
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
     * Append Video and Save to XML
     *
     * @param xmlFile   the xml file
     * @param document  the document
     * @param root      the root
     * @param xml       the xml
     * @throws TransformerException the transformer exception
     */
    private void appendVideoAndSaveToXML(File xmlFile, Document document, Element root, Element xml) throws TransformerException {
        Element videoElement = xml;
        root.appendChild(videoElement);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(xmlFile);

        removeWhitespaceNodes(root);

        transformer.transform(source, result);
    }


    /**
     * Check if a folder exists.
     *
     * @param root     the root
     * @param fileName the file name
     * @return the boolean
     */
    private boolean checkFolderExists(Element root, String fileName) {
        NodeList folderList = root.getElementsByTagName("Folders");
        for (int i = 0; i < folderList.getLength(); i++) {
            Element element = (Element) folderList.item(i);
            String name = element.getAttribute("name");
            LocalDate date = LocalDate.parse(element.getAttribute("date"));
            String event = element.getAttribute("event");

            if (name.equals(fileName) && date.equals(date) && event.equals(event)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Iterate through a photo list and check if the given photo exists.
     *
     * @param fileName  the file name
     * @param photoList the photo list
     * @return the boolean
     */
    private boolean checkIfPhotoExistsInPhotoList(String fileName, NodeList photoList) {
        for (int i = 0; i < photoList.getLength(); i++) {
            Element element = (Element) photoList.item(i);
            String XMLFileName = element.getAttribute("file");

            if (XMLFileName.equals(fileName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if a photo file exists.
     *
     * @param root the root
     * @param fileName the file name
     * @return the boolean
     */
    private boolean checkPhotoFileExists(Element root, String fileName) {
        NodeList photoList = root.getElementsByTagName("Photo");
        return checkIfPhotoExistsInPhotoList(fileName, photoList);
    }

    /**
     * Check if a video file exists.
     *
     * @param root the root
     * @param fileName the file name
     * @return the boolean
     */
    private boolean checkVideoFileExists(Element root, String fileName) {
        NodeList videoList = root.getElementsByTagName("Video");
        return checkIfPhotoExistsInPhotoList(fileName, videoList);
    }

    /**
     * Remove whitespace nodes in the XML document.
     *
     * @param node The node to start the cleanup from
     */
    private void removeWhitespaceNodes(Node node) {
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.TEXT_NODE && child.getNodeValue().trim().isEmpty()) {
                node.removeChild(child);
                i--;
            } else if (child.getNodeType() == Node.ELEMENT_NODE) {
                removeWhitespaceNodes(child);
            }
        }
    }

    /**
     *  Removes an element from a NodeList based on the name attribute.
     *
     * @param searchName The name of the element to remove.
     * @param root The root element of the XML document.
     * @param nodeList The NodeList to search through.
     */
    private void removeElementFromNodeListByFilePath(String searchName, Element root, NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            String XMLFileName = element.getAttribute("file");

            if (XMLFileName.equals(searchName)) {
                root.removeChild(element);
                break;
            }
        }
    }

    private void updateElementFromNodeList(String fileName, Media newMedia, Element root, NodeList nodeList) {
        // iterate nodeList, check for fileName, delete old element and update with the new element
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            String XMLFileName = element.getAttribute("file");

            if (XMLFileName.equals(fileName)) {
                root.removeChild(element);

                if (newMedia instanceof Photo) {
                    root.appendChild(((Photo) newMedia).toXML(root.getOwnerDocument()));
                } else {
                    root.appendChild(((Video) newMedia).toXML(root.getOwnerDocument()));
                }
                break;
            }
        }
    }

    /**
     * Method to normalize and save a document
     *
     * @param file the file
     * @param document the document
     * @throws TransformerException the transformer exception
     */
    private void normalizeAndSaveDocument(File file, Document document) throws TransformerException {
        document.normalizeDocument();

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(file);

        transformer.transform(source, result);
    }

    /**
     * Method to get a document from a file
     *
     * @param fileName The name of the file
     * @return The document
     */
    private Document loadDocumentFromFileName(String fileName) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder documentBuilder = getDocumentBuilder();

        return documentBuilder.parse(new File(fileName));
    }

    /**
     * Get Document Builder
     *
     * @return DocumentBuilder
     */
    private static DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        return documentBuilder;
    }

    /**
     * Export xml files.
     *
     * @param filePath the file path arg
     * @throws IOException the io exception
     */
    public void exportXMLFiles(String filePath) throws IOException {
        String projectDirectory = System.getProperty("user.dir");

        try {
            File destFolder = new File(filePath);
            File sourceFolder = new File(projectDirectory + File.separator + "XMLSaveFolder");

            copyFilesInsideFolder(sourceFolder, destFolder);

            System.out.println("XMLSaveFolder successful copied to: " + destFolder);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to export XML files", e);
            throw e;
        }
    }

    /**
     * Import xml files.
     *
     * @param sourceFolderPath the source folder path
     * @throws IOException the io exception
     */
    public void importXMLFiles(String sourceFolderPath) throws IOException {
        try {
            File sourceFolder = new File(sourceFolderPath);

            String projectDirectory = System.getProperty("user.dir");
            String destFolderPath = projectDirectory + File.separator + "XMLSaveFolder";

            File destinationFolder = new File(destFolderPath);

            if (!sourceFolder.exists() || !sourceFolder.isDirectory()) {
                throw new IllegalArgumentException("Source Folder not found");
            }

            copyFilesInsideFolder(sourceFolder, destinationFolder);

            System.out.println("Folder imported into: " + destinationFolder);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to import XML files", e);
            throw e;
        }
    }

    /**
     *
     * Method to copy files from a folder
     *
     * @param source the source
     * @param dest  the dest
     * @throws IOException the io exception
     */
    private void copyFilesInsideFolder(File source, File dest) throws IOException {
        if (source.isDirectory()) {
            if (!dest.exists()) {
                boolean created = dest.mkdir();
                if (created) {
                    LOGGER.info("Directory created successfully: " + dest);
                } else {
                    LOGGER.severe("Failed to create directory: " + dest);
                }
            }

            String[] files = source.list();

            assert files != null;
            for (String file : files) {
                File srcFile = new File(source, file);
                File destFile = new File(dest, file);

                if (srcFile.isDirectory()) {
                    copyFilesInsideFolder(srcFile, destFile);
                } else {
                    copyFile(srcFile, destFile);
                }
            }
        }
    }

    /**
     * Method to copy a file
     *
     * @param source the source
     * @param dest  the dest
     * @throws IOException the io exception
     */
    private void copyFile(File source, File dest) throws IOException {
        java.nio.file.Files.copy(source.toPath(), dest.toPath());
    }

    /**
     * Method to save tags to xml file
     *
     * @param tags the tags
     */
    public void saveTagsToXML(TagList tags) {
        try {

            String fileName = pathToXmlSaveFolder + "tags.xml";

            DocumentBuilder documentBuilder = getDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element root = document.createElement("Tags");
            document.appendChild(root);

            for (Tag tag : tags.getTags()) {
                Element tagElement = document.createElement("Tag");
                tagElement.setAttribute("name", tag.getName());
                tagElement.setAttribute("color", colorToHex(tag.getColor()));
                tagElement.setAttribute("type", tag.getType().name());
                root.appendChild(tagElement);
            }

            transformDocumentToFile(fileName, document);
        } catch (ParserConfigurationException | TransformerException e) {
            LOGGER.log(Level.SEVERE, "Failed to save tags to XML", e);
            ShowAlert.showAlert("Error", "Failed to save tags to XML: " + e.getMessage());
        }
    }

    /**
     * Method to load tags to xml file
     *
     * @return the tag list
     */
    public TagList loadTagsFromXML() {
        TagList tagList = TagListController.get().createEmptyTagList();
        String fileName = pathToXmlSaveFolder + "/tags.xml";

        try {
            File file = new File(fileName);
            if (!file.exists()) {
                return tagList;
            }

            Document document = loadDocumentFromFileName(fileName);

            NodeList tagNodes = document.getElementsByTagName("Tag");
            for (int i = 0; i < tagNodes.getLength(); i++) {
                tagList.addTag(Tag.fromXML((Element) tagNodes.item(i)));
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to load tags from XML", e);
            ShowAlert.showAlert("Error", "Failed to load tags from XML: " + e.getMessage());
        }
        return tagList;
    }

    /**
     * Save Photo to
     *
     * @param photo                    The photo to save
     * @param overwriteIfAlreadyExists the overwrite if already exists
     */
    public void savePhotoToXML(Photo photo, boolean overwriteIfAlreadyExists) {
        try {
            String fileName = pathToXmlSaveFolder + "/photos.xml";
            File xmlFile = new File(fileName);

            DocumentBuilder documentBuilder = getDocumentBuilder();
            Document document;

            Element root;

            if (xmlFile.exists()) {
                document = documentBuilder.parse(xmlFile);
                root = document.getDocumentElement();
                if (checkPhotoFileExists(root, photo.getFile())) {
                    if (overwriteIfAlreadyExists){
                        removeExistingPhotoFromXML(root, photo.getFile());
                    }
                    else {
                        return;
                    }
                }
            } else {
                document = documentBuilder.newDocument();
                root = document.createElement("Photos");
                document.appendChild(root);
            }
            appendVideoAndSaveToXML(xmlFile, document, root, photo.toXML(document));
        } catch (ParserConfigurationException | TransformerException | IOException | org.xml.sax.SAXException e) {
            LOGGER.log(Level.SEVERE, "Failed to save photo to XML", e);
            ShowAlert.showAlert("Error", "Failed to save photo to XML: " + e.getMessage());
        }
    }

    /**
     * Removes existing photo from xml
     *
     * @param root The root element of the XML document
     * @param photoName The name of the photo to remove
     */
    private void removeExistingPhotoFromXML(Element root, String photoName) {
        NodeList photoList = root.getElementsByTagName("Photo");
        removeElementFromNodeListByFilePath(photoName, root, photoList);
    }

    /**
     * Removes existing folder from xml
     *
     * @param root The root element of the XML document
     * @param folder The folder to remove
     */
    private void removeExistingFolderFromXML(Element root, Folder folder) {
        NodeList folderList = root.getElementsByTagName("Folder");
        for (int i = 0; i < folderList.getLength(); i++) {
            Element element = (Element) folderList.item(i);
            String name = element.getAttribute("name");

            if (name.equals(folder.getName())
                    && element.getAttribute("date").equals(folder.getDate().toString())
                    && element.getAttribute("event").equals(folder.getEvent()))
            {
                root.removeChild(element);
                break;
            }
        }
    }

    /**
     * Loads the photos from the XML file
     *
     * @return List of photos
     */
    public List<Photo> loadPhotosFromXML() {
        List<Photo> photos = new ArrayList<>();
        String fileName = pathToXmlSaveFolder + "/photos.xml";

        try {
            File file = new File(fileName);
            if (!file.exists()) {
                return photos;
            }

            Document document = loadDocumentFromFileName(fileName);

            NodeList photoNodes = document.getElementsByTagName("Photo");
            for (int i = 0; i < photoNodes.getLength(); i++) {
                Photo photo = Photo.fromXML((Element) photoNodes.item(i));
                photos.add(photo);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to load photos from XML", e);
            ShowAlert.showAlert("Error", "Failed to load photos from XML: " + e.getMessage());
        }
        return photos;
    }

    /**
     * Loads the videos from the XML file
     *
     * @return List of videos
     */
    public List<Video> loadVideosFromXML() {
        List<Video> videos = new ArrayList<>();
        String fileName = pathToXmlSaveFolder + "/videos.xml";

        try {
            File file = new File(fileName);
            if (!file.exists()) {
                return videos;
            }

            Document document = loadDocumentFromFileName(fileName);


            NodeList videoNodes = document.getElementsByTagName("Video");
            for (int i = 0; i < videoNodes.getLength(); i++) {
                Video video = Video.fromXML((Element) videoNodes.item(i));
                videos.add(video);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to load videos from XML", e);
            ShowAlert.showAlert("Error", "Failed to load videos from XML: " + e.getMessage());
        }
        return videos;
    }

    /**
     * Load all folders from XML.
     *
     * @return List of folders
     */
    public List<Folder> loadFoldersFromXML() {
        List<Folder> folders = new ArrayList<>();
        String fileName = pathToXmlSaveFolder + "/folders.xml";

        try {
            File file = new File(fileName);
            if (!file.exists()) {
                return folders;
            }

            Document document = loadDocumentFromFileName(fileName);

            NodeList folderNodes = document.getElementsByTagName("Folder");
            for (int i = 0; i < folderNodes.getLength(); i++) {
                Element folderElement = (Element) folderNodes.item(i);
                Folder folder = Folder.fromXML(folderElement);
                folders.add(folder);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to load photos from XML", e);
            ShowAlert.showAlert("Error", "Failed to load photos from XML: " + e.getMessage());
        }
        return folders;
    }

    /**
     * Save Video to XML
     * Saves a Video to XML File
     *
     * @param video                    The video to save
     * @param overwriteIfAlreadyExists the overwrite if already exists
     */
    public void saveVideoToXML(Video video, boolean overwriteIfAlreadyExists) {
        try {
            String fileName = pathToXmlSaveFolder + "/videos.xml";
            File xmlFile = new File(fileName);

            DocumentBuilder documentBuilder = getDocumentBuilder();
            Document document;

            Element root;

            if (xmlFile.exists()) {
                document = documentBuilder.parse(xmlFile);
                root = document.getDocumentElement();
                if (checkVideoFileExists(root, video.getName())){
                    if (overwriteIfAlreadyExists) {
                        removeExistingVideo(root, video.getName());
                    }
                    else {
                        return;
                    }
                }
            } else {
                document = documentBuilder.newDocument();
                root = document.createElement("Videos");
                document.appendChild(root);
            }

            appendVideoAndSaveToXML(xmlFile, document, root, video.toXML(document));
        } catch (ParserConfigurationException | TransformerException | IOException | SAXException e) {
            LOGGER.log(Level.SEVERE, "Failed to save video to XML", e);
            ShowAlert.showAlert("Error", "Failed to save video to XML: " + e.getMessage());
        }
    }

    /**
     * Remove Existing Photo
     * removes existing video from xml
     * @param root The root element of the XML document
     * @param videoName The name of the video to remove
     */
    private void removeExistingVideo(Element root, String videoName) {
        NodeList videoList = root.getElementsByTagName("Video");
        removeElementFromNodeListByFilePath(videoName, root, videoList);
    }

    /**
     * Saves a Folder to XML File
     *
     * @param folder The folder to save
     * @throws Exception the exception
     */
    public void saveFolderToXML(Folder folder) throws Exception {
        String fileName = pathToXmlSaveFolder + "/folders.xml";
        File xmlFile = new File(fileName);

        DocumentBuilder documentBuilder = getDocumentBuilder();
        Document document;

        Element root;

        if (xmlFile.exists()) {
            document = documentBuilder.parse(xmlFile);
            root = document.getDocumentElement();
            if (checkFolderExists(root, folder.getName())) {
                removeExistingFolderFromXML(root, folder);
            }
        } else {
            document = documentBuilder.newDocument();
            root = document.createElement("Folders");
            document.appendChild(root);
        }
        appendVideoAndSaveToXML(xmlFile, document, root, folder.toXML(document));
    }

    /**
     * Delete a Folder from XML
     *
     * @param folder The folder to delete
     */
    public void deleteFolderFromXML(Folder folder) {
        try {
            String fileName = pathToXmlSaveFolder + "folders.xml";
            Document document = loadDocumentFromFileName(fileName);

            Element root = document.getDocumentElement();
            NodeList folderNodes = root.getElementsByTagName("Folder");

            removeElementFromNodeListByName(folder.getName(), root, folderNodes);

            removeWhitespaceNodes(root);

            document.normalizeDocument();

            transformDocumentToFile(fileName, document);

            for (Media media : folder.getMediaList()) {
                if (media instanceof Photo)
                    deleteMediaFromAllXML(media, MediaType.PHOTO);
                else {
                    deleteMediaFromAllXML(media, MediaType.VIDEO);
                }
            }

            deleteFileOrDirectory(new File(pathToMediaFolder + folder.getName()));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to delete folder from XML", e);
            ShowAlert.showAlert("Error", "Failed to delete folder from XML: " + e.getMessage());
        }
    }

    /**
     * Saves the slideshow to XML File
     *
     * @param slideshow The slideshow to save
     */
    public void saveSlideshowToXML(Slideshow slideshow) {
        String fileName = pathToXmlSaveFolder + "slideshows.xml";
        File file = new File(fileName);

        try {
            DocumentBuilder documentBuilder = getDocumentBuilder();
            Document document;

            if (file.exists()) {
                document = documentBuilder.parse(file);
                Element root = document.getDocumentElement();

                removeWhitespaceNodes(root);

                Element slideshowElement = slideshow.toXML(document);
                removeElementFromNodeListByName(slideshow.getName(), root, root.getElementsByTagName("Slideshow"));
                root.appendChild(slideshowElement);
            } else {
                document = documentBuilder.newDocument();
                Element root = document.createElement("Slideshows");
                document.appendChild(root);

                Element slideshowElement = slideshow.toXML(document);
                root.appendChild(slideshowElement);
            }

            normalizeAndSaveDocument(file, document);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to save slideshows to XML", e);
            ShowAlert.showAlert("Error", "Failed to save slideshows to XML: " + e.getMessage());
        }
    }

    /**
     * Gets slideshow name of photo inside slideshow.
     *
     * @param photo the photo
     * @return the slideshow name of photo inside slideshow
     */
    public String getSlideshowNameOfPhotoInsideSlideshow(Photo photo) {
        try {
            String fileName = pathToXmlSaveFolder + "slideshows.xml";
            Document document = loadDocumentFromFileName(fileName);

            Element root = document.getDocumentElement();
            NodeList slideshowNodes = root.getElementsByTagName("Slideshow");

            for (int i = 0; i < slideshowNodes.getLength(); i++) {
                Element slideshowElement = (Element) slideshowNodes.item(i);
                NodeList photoNodes = slideshowElement.getElementsByTagName("Photo");

                for (int j = 0; j < photoNodes.getLength(); j++) {
                    Element photoElement = (Element) photoNodes.item(j);
                    if (photoElement.getAttribute("file").equals(photo.getFile())) {
                        return slideshowElement.getAttribute("name");
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to check if photo is in any slideshow", e);
            ShowAlert.showAlert("Error", "Failed to check if photo is in any slideshow: " + e.getMessage());
        }
        return null;
    }

    /**
     * Loads a slideshow from XML File
     *
     * @return The list of slideshows
     */
    public List<Slideshow> loadSlideshowsFromXML() {
        List<Slideshow> slideshows = new ArrayList<>();
        try {
            String fileName = pathToXmlSaveFolder + "slideshows.xml";
            Document document = loadDocumentFromFileName(fileName);

            Element root = document.getDocumentElement();
            NodeList slideshowNodes = root.getElementsByTagName("Slideshow");

            for (int i = 0; i < slideshowNodes.getLength(); i++) {
                Element slideshowElement = (Element) slideshowNodes.item(i);
                Slideshow slideshow = Slideshow.fromXML(slideshowElement);

                slideshows.add(slideshow);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to load slideshows from XML", e);
            ShowAlert.showAlert("Error", "Failed to load slideshows from XML: " + e.getMessage());
        }
        return slideshows;
    }

    /**
     *  Removes an element from a NodeList based on the name attribute.
     *
     * @param searchName The name of the element to remove.
     * @param root The root element of the XML document.
     * @param nodeList The NodeList to search through.
     */
    private void removeElementFromNodeListByName(String searchName, Element root, NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            String XMLFileName = element.getAttribute("name");

            if (XMLFileName.equals(searchName)) {
                root.removeChild(element);
                break;
            }
        }
    }

    /**
     * Deletes a single slideshow from the XML file.
     *
     * @param slideshow The name of the slideshow to delete.
     */
    public void deleteSlideshowFromXML(Slideshow slideshow) {
        try {
            String fileName = pathToXmlSaveFolder + "slideshows.xml";
            Document document = loadDocumentFromFileName(fileName);

            Element root = document.getDocumentElement();
            NodeList slideshowNodes = root.getElementsByTagName("Slideshow");

            System.out.println("Slideshow Nodes: " + slideshowNodes.getLength());

            removeElementFromNodeListByName(slideshow.getName(), root, slideshowNodes);

            System.out.println("Slideshow deleted: " + slideshow.getName());
            System.out.println("Slideshow Nodes: " + slideshowNodes.getLength());

            removeWhitespaceNodes(root);

            document.normalizeDocument();

            transformDocumentToFile(fileName, document);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to delete slideshow from XML", e);
            ShowAlert.showAlert("Error", "Failed to delete slideshow from XML: " + e.getMessage());
        }
    }

    /**
     * Save Album to XML
     * Saves an album to XML File
     *
     * @param album The album to save
     */
    public void saveAlbumToXML(Album album) {
        String fileName = pathToXmlSaveFolder + "albums.xml";
        File file = new File(fileName);

        try {
            DocumentBuilder documentBuilder = getDocumentBuilder();
            Document document;

            if (file.exists()) {
                document = documentBuilder.parse(file);
                Element root = document.getDocumentElement();

                removeWhitespaceNodes(root);

                Element albumElement = album.toXML(document);
                removeElementFromNodeListByName(album.getName(), root, root.getElementsByTagName("Album"));
                root.appendChild(albumElement);
            } else {
                document = documentBuilder.newDocument();
                Element root = document.createElement("Album");
                document.appendChild(root);

                Element albumElement = album.toXML(document);
                root.appendChild(albumElement);
            }

            normalizeAndSaveDocument(file, document);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to save album to XML", e);
            ShowAlert.showAlert("Error", "Failed to save album to XML: " + e.getMessage());
        }
    }

    /**
     * Load Album from XML
     * Loads an album from XML File
     *
     * @return The list of albums
     */
    public List<Album> loadAlbumsFromXML() {
        List<Album> albums = new ArrayList<>();
        String fileName = pathToXmlSaveFolder + "/albums.xml";

        try {
            File file = new File(fileName);
            if (!file.exists()) {
                return albums;
            }

            Document document = loadDocumentFromFileName(fileName);

            NodeList albumNodes = document.getElementsByTagName("Album");
            for (int i = 0; i < albumNodes.getLength(); i++) {
                Element albumElement = (Element) albumNodes.item(i);
                Album album = Album.fromXML(albumElement);
                albums.add(album);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to load photos from XML", e);
            ShowAlert.showAlert("Error", "Failed to load photos from XML: " + e.getMessage());
        }

        return albums;
    }

    /**
     * Deletes a single slideshow from the XML file.
     *
     * @param album The name of the album to delete.
     */
    public void deleteAlbumFromXML(Album album) {
        try {
            String fileName = pathToXmlSaveFolder + "albums.xml";

            Document document = loadDocumentFromFileName(fileName);

            Element root = document.getDocumentElement();
            NodeList albumNodes = root.getElementsByTagName("Album");

            removeElementFromNodeListByName(album.getName(), root, albumNodes);

            removeWhitespaceNodes(root);

            document.normalizeDocument();

            transformDocumentToFile(fileName, document);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to delete slideshow from XML", e);
            ShowAlert.showAlert("Error", "Failed to delete slideshow from XML: " + e.getMessage());
        }
    }

    /**
     * Deletes a photo from the given XML file.
     *
     * @param media The photo to delete.
     * @param type         the type
     */
    public void deleteMediaFromDatastructureXML(Media media, DataStructureType type) {
        try {
            String fileName;
            String NodeName = switch (type) {
                case ALBUM -> {
                    fileName = pathToXmlSaveFolder + "/albums.xml";
                    yield "Album";
                }
                case FOLDER -> {
                    fileName = pathToXmlSaveFolder + "/folders.xml";
                    yield "Folder";
                }
                case SLIDESHOW -> {
                    fileName = pathToXmlSaveFolder + "/slideshows.xml";
                    yield "Slideshow";
                }
                default -> throw new IllegalArgumentException("Invalid data structure type");
            };

            Document document = loadDocumentFromFileName(fileName);
            Element root = document.getDocumentElement();

            NodeList nodeList = root.getElementsByTagName(NodeName);

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element dataStructureElement = (Element) nodeList.item(i);

                NodeList photoNodes = dataStructureElement.getElementsByTagName("Photo");
                NodeList videoNodes = dataStructureElement.getElementsByTagName("Video");

                iterateAndDeleteMediaFromDatastructureXML(media, dataStructureElement, photoNodes);
                iterateAndDeleteMediaFromDatastructureXML(media, dataStructureElement, videoNodes);
            }

            removeWhitespaceNodes(root);
            document.normalizeDocument();
            transformDocumentToFile(fileName, document);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to delete photo from XML", e);
            ShowAlert.showAlert("Error", "Failed to delete photo from XML: " + e.getMessage());
        }
    }

    /**
     * Update media from datastructure xml.
     *
     * @param media the current media
     * @param type         the type
     */
    public void updateMediaFromDatastructureXML(Media media, DataStructureType type) {
        try {
            String fileName;
            String NodeName = switch (type) {
                case ALBUM -> {
                    fileName = pathToXmlSaveFolder + "/albums.xml";
                    yield "Album";
                }
                case FOLDER -> {
                    fileName = pathToXmlSaveFolder + "/folders.xml";
                    yield "Folder";
                }
                case SLIDESHOW -> {
                    fileName = pathToXmlSaveFolder + "/slideshows.xml";
                    yield "Slideshow";
                }
                default -> throw new IllegalArgumentException("Invalid data structure type");
            };

            Document document = loadDocumentFromFileName(fileName);
            Element root = document.getDocumentElement();

            NodeList nodeList = root.getElementsByTagName(NodeName);

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element dataStructureElement = (Element) nodeList.item(i);

                NodeList photoNodes = dataStructureElement.getElementsByTagName("Photo");
                NodeList videoNodes = dataStructureElement.getElementsByTagName("Video");

                iterateAndUpdateMediaFromDatastructureXML(media, dataStructureElement, photoNodes);
                iterateAndUpdateMediaFromDatastructureXML(media, dataStructureElement, videoNodes);
            }

            removeWhitespaceNodes(root);
            document.normalizeDocument();
            transformDocumentToFile(fileName, document);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to delete photo from XML", e);
            ShowAlert.showAlert("Error", "Failed to delete photo from XML: " + e.getMessage());
        }
    }

    /**
     * Iterates through the given NodeList and deletes the given media from the XML file.
     *
     * @param media The media to delete.
     * @param elementToDelete The element to delete the media from.
     * @param mediaNodes The NodeList to iterate through.
     */
    private void iterateAndDeleteMediaFromDatastructureXML(Media media, Element elementToDelete, NodeList mediaNodes) {
        for (int j = 0; j < mediaNodes.getLength(); j++) {
            Element mediaElement = (Element) mediaNodes.item(j);

            String name = mediaElement.getAttribute("name");

            if (name.equals(media.getName())) {
                elementToDelete.removeChild(mediaElement);
                break;
            }
        }
    }

    /**
     * Iterates through the given NodeList and updates the given media in the XML file.
     * @param media The media to update.
     * @param elementToUpdate The element to update the media in.
     * @param mediaNodes The NodeList to iterate through.
     */
    private void iterateAndUpdateMediaFromDatastructureXML(Media media, Element elementToUpdate, NodeList mediaNodes) {
        String oldMediaFilePath = media.getFile();

        for (int j = 0; j < mediaNodes.getLength(); j++) {
            Element mediaElement = (Element) mediaNodes.item(j);

            String filePath = mediaElement.getAttribute("file");

            if (filePath.equals(oldMediaFilePath)) {
                elementToUpdate.removeChild(mediaElement);

                if (media instanceof Photo) {
                    elementToUpdate.appendChild(((Photo) media).toXML(elementToUpdate.getOwnerDocument()));
                } else {
                    elementToUpdate.appendChild(((Video) media).toXML(elementToUpdate.getOwnerDocument()));
                }
                break;
            }
        }
    }

    /**
     * Deletes a photo from the given XML file.
     *
     * @param media The photo to delete.
     * @param type         the type
     */
    public void deleteMediaFromMediaXML(Media media, MediaType type) {
        try {
            String fileName;
            String NodeName = switch (type) {
                case PHOTO -> {
                    fileName = pathToXmlSaveFolder + "photos.xml";
                    yield "Photo";
                }
                case VIDEO -> {
                    fileName = pathToXmlSaveFolder + "videos.xml";
                    yield "Video";
                }
                default -> throw new IllegalArgumentException("Invalid data media type");
            };

            Document document = loadDocumentFromFileName(fileName);
            Element root = document.getDocumentElement();

            NodeList nodeList = root.getElementsByTagName(NodeName);

            removeElementFromNodeListByFilePath(media.getFile(), root, nodeList);

            removeWhitespaceNodes(root);
            document.normalizeDocument();
            transformDocumentToFile(fileName, document);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to delete photo from XML", e);
            ShowAlert.showAlert("Error", "Failed to delete photo from XML: " + e.getMessage());
        }
    }

    /**
     * Update media from media xml.
     *
     * @param media the current media
     * @param type         the type
     */
    public void updateMediaFromMediaXML(Media media, MediaType type) {
        try {
            String fileName;
            String NodeName = switch (type) {
                case PHOTO -> {
                    fileName = pathToXmlSaveFolder + "photos.xml";
                    yield "Photo";
                }
                case VIDEO -> {
                    fileName = pathToXmlSaveFolder + "videos.xml";
                    yield "Video";
                }
                default -> throw new IllegalArgumentException("Invalid data media type");
            };

            Document document = loadDocumentFromFileName(fileName);
            Element root = document.getDocumentElement();

            NodeList nodeList = root.getElementsByTagName(NodeName);

            updateElementFromNodeList(media.getFile(), media, root, nodeList);

            removeWhitespaceNodes(root);
            document.normalizeDocument();
            transformDocumentToFile(fileName, document);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to delete photo from XML", e);
            ShowAlert.showAlert("Error", "Failed to delete photo from XML: " + e.getMessage());
        }
    }

    // Update Media from All XML files and Structures by comparing the medias file path with the file paths in the xml files
    public void updateMediaFromAllXML(Media media) {
        try {
            if (media instanceof Photo) {
                updateMediaFromMediaXML(media, MediaType.PHOTO);
            } else {
                updateMediaFromMediaXML(media, MediaType.VIDEO);
            }

            updateMediaFromDatastructureXML(media, DataStructureType.ALBUM);
            updateMediaFromDatastructureXML(media, DataStructureType.FOLDER);
            updateMediaFromDatastructureXML(media, DataStructureType.SLIDESHOW);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to update media from all XML", e);
            ShowAlert.showAlert("Error", "Failed to update media from all XML: " + e.getMessage());
        }
    }

    /**
     * Deletes a photo from photos.xml
     *
     * @param media the current media
     * @param type         the type
     */
    public void deleteMediaFromAllXML(Media media, MediaType type) {
        try {
            deleteMediaFromDatastructureXML(media, DataStructureType.ALBUM);
            deleteMediaFromDatastructureXML(media, DataStructureType.FOLDER);
            deleteMediaFromDatastructureXML(media, DataStructureType.SLIDESHOW);
            deleteMediaFromMediaXML(media, type);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to delete media from all XML", e);
            ShowAlert.showAlert("Error", "Failed to delete media from all XML: " + e.getMessage());
        }
    }

    /**
     * Updates a photo in all XML files.
     * @param media the current media
     * @param type        the type
     */
    private void updateMediaInAllXML(Media media, MediaType type) {
        try {
            // Delete the current media from all XML files
            updateMediaFromDatastructureXML(media, DataStructureType.ALBUM);
            updateMediaFromDatastructureXML(media, DataStructureType.FOLDER);
            updateMediaFromDatastructureXML(media, DataStructureType.SLIDESHOW);
            updateMediaFromMediaXML(media, type);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to update media in all XML", e);
            ShowAlert.showAlert("Error", "Failed to update media in all XML: " + e.getMessage());
        }
    }

    /**
     * Deletes file or directory.
     *
     * @param fileOrDir the file or directory
     * @return true if successful, false otherwise
     */
    public boolean deleteFileOrDirectory(File fileOrDir) {
        if (!fileOrDir.exists()) {
            System.out.println("File or directory does not exist.");
            return false;
        }

        if (fileOrDir.isDirectory()) {
            // Recursively delete all contents
            File[] contents = fileOrDir.listFiles();
            if (contents != null) {
                for (File file : contents) {
                    if (!deleteFileOrDirectory(file)) {
                        return false;
                    }
                }
            }
        }
        return fileOrDir.delete();
    }
}
