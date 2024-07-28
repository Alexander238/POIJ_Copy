package de.fhkiel.fotomanager.controller.modelController;

import de.fhkiel.fotomanager.model.Period;
import de.fhkiel.fotomanager.model.datastructures.impl.Folder;
import de.fhkiel.fotomanager.model.mediatypes.Media;
import de.fhkiel.fotomanager.model.mediatypes.impl.Photo;
import de.fhkiel.fotomanager.model.mediatypes.impl.Video;
import io.vavr.control.Either;

import java.time.LocalDate;

/**
 * The type Folder controller.
 */
public class FolderController {
    /**
     * The Instance.
     */
    private static FolderController instance;

    private FolderController() {}

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static FolderController get() {
        if (instance == null) {
            instance = new FolderController();
        }
        return instance;
    }

    /**
     * Create folder folder.
     *
     * @param name  the name
     * @param date  the date
     * @param event the event
     * @return the folder
     */
    public Folder createFolder(String name, Either<LocalDate, Period> date, String event) {
        return new Folder(name, date, event);
    }

    /**
     * Add media to folder.
     *
     * @param folder the folder
     * @param media  the media
     */
    public void addMediaToFolder(Folder folder, Media... media) {
        for (Media m : media) {
            if (!folder.getMediaList().contains(m)) folder.addMedia(m);
        }
    }

    /**
     * Delete folder.
     *
     * @param folder the folder
     */
    public void deleteFolder(Folder folder) {
        for (Media m : folder.getMediaList()) {
            if (m instanceof Photo) {
                PhotoManagerController.get().deletePhoto((Photo) m);
            } else {
                PhotoManagerController.get().deleteVideo((Video) m);
            }
        }
        folder.delete();
    }
}
