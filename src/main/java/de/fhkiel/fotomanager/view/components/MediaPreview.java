package de.fhkiel.fotomanager.view.components;

import de.fhkiel.fotomanager.model.mediatypes.Media;
import de.fhkiel.fotomanager.model.mediatypes.impl.Photo;
import de.fhkiel.fotomanager.model.mediatypes.impl.Video;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * The type Media preview.
 */
@Getter
public class MediaPreview {
    /**
     * The Media for the preview.
     */
    private final Media media;
    /**
     * The Image view.
     */
    @Setter
    private ImageView imageView;
    @Setter
    private boolean selected;
    @Setter
    private BorderPane image;
    private VBox vBox;

    /**
     * Instantiates a new Media preview.
     *
     * @param media the media
     */
    public MediaPreview(Media media) {
        this.image = new BorderPane();
        vBox = new VBox();
        vBox.setMaxWidth(100);
        vBox.setMinWidth(100);
        vBox.setMaxHeight(150);
        vBox.setMinHeight(150);
        this.image.setStyle("-fx-border-color: transparent; -fx-border-style: solid; -fx-border-width: 2px;");
        if (media instanceof Photo) {
            this.image.setCenter(createImageView(media.getFile(), 100, 100));
            this.imageView.setRotate(media.getOrientation().toAngle());
        } else if (media instanceof Video) {
            String videoIconPath = "src/main/resources/icon/videopreview.jpg";
            this.image.setCenter(createImageView(videoIconPath, 100, 100));
        }
        Label label = new Label(media.getName());
        label.setWrapText(true);
        label.setTextAlignment(TextAlignment.CENTER);
        vBox.getChildren().addAll(this.image, label);
        this.media = media;
    }

    /**
     * Create image view.
     *
     * @param resourcePath the resource path
     * @param width        the width
     * @param height       the height
     * @return the image view
     */
    private ImageView createImageView(String resourcePath, int width, int height) {
        this.imageView = new ImageView();
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

        try (InputStream imageStream = Files.newInputStream(Paths.get(normalizePath(resourcePath)))) {
            Image image = new Image(imageStream);
            imageView.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading image from path: " + resourcePath);
        }
        return imageView;
    }

    /**
     * Normalize paths.
     *
     * @param path the path
     * @return the string
     */
    private String normalizePath(String path) {
        return path.replace("\\", "/");
    }
}
