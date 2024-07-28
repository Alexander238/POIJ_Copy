package de.fhkiel.fotomanager.controller.modelController;

import de.fhkiel.fotomanager.model.datastructures.impl.Album;
import de.fhkiel.fotomanager.model.mediatypes.Media;

/**
 * The type Album controller.
 */
public class AlbumController {
    /**
     * The Instance.
     */
    private static AlbumController instance;

    private AlbumController() {}

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static AlbumController get() {
        if (instance == null) {
            instance = new AlbumController();
        }
        return instance;
    }

    /**
     * Create album album.
     *
     * @param name the name
     * @return the album
     */
    public Album createAlbum(String name) {
        return new Album(name);
    }

    /**
     * Rename album.
     *
     * @param album   the album
     * @param newName the new name
     */
    public void renameAlbum(Album album, String newName) {
        album.rename(newName);
    }

    /**
     * Delete album.
     *
     * @param album the album
     */
    public void deleteAlbum(Album album) {
        album.delete();
    }

    /**
     * Add media to album.
     *
     * @param album the album
     * @param media the media
     */
    public void addMediaToAlbum(Album album, Media... media) {
        for (Media m : media) {
            System.out.println("Adding media to album: " + m.getName());
            if (!album.getMediaList().contains(m)) album.addMedia(m);
        }
    }

    /**
     * Remove media from album.
     *
     * @param album the album
     * @param media the media
     */
    public void removeMediaFromAlbum(Album album, Media media) {
        album.removeMedia(media);
    }
}
