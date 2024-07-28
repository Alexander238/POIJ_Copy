package de.fhkiel.fotomanager.model.datastructures;

import de.fhkiel.fotomanager.model.mediatypes.Media;

/**
 * The interface Data structure interface.
 *
 * @param <T> the type parameter
 */
public interface DataStructureInterface<T extends Media> {

    /**
     * Add media.
     *
     * @param media the media
     */
    void addMedia(T media);

    /**
     * Remove media.
     *
     * @param media the media
     */
    void removeMedia(T media);
}
