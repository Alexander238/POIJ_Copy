package de.fhkiel.fotomanager.model.search;

import de.fhkiel.fotomanager.model.datastructures.impl.Album;
import de.fhkiel.fotomanager.model.datastructures.impl.Folder;
import de.fhkiel.fotomanager.model.datastructures.impl.Slideshow;
import de.fhkiel.fotomanager.model.mediatypes.Media;
import de.fhkiel.fotomanager.model.mediatypes.impl.Photo;
import de.fhkiel.fotomanager.model.tags.Tag;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Search.
 */
@Getter
public class Search {
    /**
     * The Search parameters.
     */
    private final List<SearchParameter> searchParameters = new ArrayList<>();
    /**
     * The Albums.
     */
    private final List<Album> albums;
    /**
     * The Folders.
     */
    private final List<Folder> folders;
    /**
     * The Slideshows.
     */
    private final List<Slideshow> slideshows;

    /**
     * The Search results.
     */
    private List<Media> searchResults;

    /**
     * Instantiates a new Search.
     *
     * @param albums     the albums
     * @param folders    the folders
     * @param slideshows the slideshows
     */
    public Search(List<Album> albums, List<Folder> folders, List<Slideshow> slideshows) {
        this.albums = albums;
        this.folders = folders;
        this.slideshows = slideshows;
        this.searchResults = new ArrayList<>();
    }

    /**
     * Search for media based on the search parameters.
     *
     * @return the list of media that match the search parameters.
     */
    public List<Media> search() {
        searchResults = new ArrayList<>();
        for (Album album : albums) {
            iterateAndAddMediaToSearchResults(album.getMediaList());
        }
        for (Folder folder : folders) {
            iterateAndAddMediaToSearchResults(folder.getMediaList());
        }
        for (Slideshow slideshow : slideshows) {
            for (Photo media : slideshow.getMediaList()) {
                checkSearchParametersAndAddToSearchResults(media);
            }
        }

        return searchResults;
    }

    private void iterateAndAddMediaToSearchResults(List<Media> mediaList) {
        for (Media media : mediaList) {
            checkSearchParametersAndAddToSearchResults(media);
        }
    }

    private void checkSearchParametersAndAddToSearchResults(Media media) {
        if (searchParameters.isEmpty()) {
            boolean containsMedia = false;
            for (Media searchResult : searchResults) {
                if (searchResult.equals(media)) {
                    containsMedia = true;
                    break;
                }
            }

            if (!containsMedia) {
                searchResults.add(media);
            }
        }

        for (SearchParameter searchParameter : searchParameters) {
            if (mediaMatchesSearchParameters(media, searchParameter)) {
                boolean containsMedia = false;
                for (Media searchResult : searchResults) {
                    if (searchResult.equals(media)) {
                        containsMedia = true;
                        break;
                    }
                }

                if (!containsMedia) {
                    searchResults.add(media);
                    break;
                }
            }
        }
    }

    /**
     * Add a new search parameter when it is not already in the list.
     *
     * @param searchParameter the search parameter to add.
     */
    public void addSearchParameter(SearchParameter searchParameter) {
        for (SearchParameter parameter : searchParameters) {
            if (parameter.equals(searchParameter)) {
                return;
            }
        }
        searchParameters.add(searchParameter);
    }

    /**
     * Remove a search parameter from the list.
     *
     * @param searchParameter the search parameter to remove.
     */
    public void removeSearchParameter(SearchParameter searchParameter) {
        searchParameters.remove(searchParameter);
    }

    /**
     * Check if the media matches the given search parameter.
     * @param media the media to check.
     * @param searchParameter the search parameter to check against.
     * @return true if the media matches the search parameter, false otherwise.
     */
    private boolean mediaMatchesSearchParameters(Media media, SearchParameter searchParameter) {
        if (searchParameter.getName().isPresent()) {
            if (!media.getName().contains(searchParameter.getName().get())) {
                return false;
            }
        }
        if (searchParameter.getDate().isPresent()) {
            if (searchParameter.getDate().get().isLeft()) {
                if (media.getDate().compareTo(searchParameter.getDate().get().getLeft()) != 0) {
                    return false;
                }
            } else {
                if (searchParameter.getDate().get().get().getStart() == null) {
                    if (media.getDate().isAfter(searchParameter.getDate().get().get().getEnd())) {
                        return false;
                    }
                } else if (searchParameter.getDate().get().get().getEnd() == null) {
                    if (media.getDate().isBefore(searchParameter.getDate().get().get().getStart())) {
                        return false;
                    }
                } else
                    if (
                        (media.getDate().isBefore(searchParameter.getDate().get().get().getStart())
                        ||
                        (media.getDate().isAfter(searchParameter.getDate().get().get().getEnd())))
                    ) {
                    return false;
                }
            }
        }
        if (searchParameter.getRating().isPresent()) {
            if (media.getRating() != searchParameter.getRating().get()) {
                return false;
            }
        }
        if (searchParameter.getIsPrivate().isPresent()) {
            if (media.isPrivate() != searchParameter.getIsPrivate().get()) {
                return false;
            }
        }
        if (searchParameter.getTags().isPresent()) {
            boolean containsTag = false;
            for (Tag tag : media.getTags().getTags()) {
                for (Tag searchTag : searchParameter.getTags().get().getTags()) {
                    if (tag.getName().equals(searchTag.getName())) {
                        containsTag = true;
                    }
                }
            }
            if (!containsTag) {
                return false;
            }
        }
        if (searchParameter.getDescription().isPresent()) {
            if (!media.getDescription().contains(searchParameter.getDescription().get())) {
                return false;
            }
        }
        return true;
    }
}
