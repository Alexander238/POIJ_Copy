package de.fhkiel.fotomanager.controller.modelController;

import de.fhkiel.fotomanager.model.search.Search;
import de.fhkiel.fotomanager.model.search.SearchParameter;
import de.fhkiel.fotomanager.model.Period;
import de.fhkiel.fotomanager.model.datastructures.DataStructure;
import de.fhkiel.fotomanager.model.datastructures.impl.Album;
import de.fhkiel.fotomanager.model.datastructures.impl.Folder;
import de.fhkiel.fotomanager.model.datastructures.impl.Slideshow;
import de.fhkiel.fotomanager.model.mediatypes.Media;
import de.fhkiel.fotomanager.model.mediatypes.Rating;
import de.fhkiel.fotomanager.model.search.SearchParameterBuilder;
import de.fhkiel.fotomanager.model.taglists.TagList;
import de.fhkiel.fotomanager.model.tags.Tag;
import io.vavr.control.Either;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * The type Search controller.
 */
public class SearchController {
    /**
     * The constant instance.
     */
    private static SearchController instance;

    private SearchController() {}

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static synchronized SearchController get() {
        if (instance == null) {
            instance = new SearchController();
        }
        return instance;
    }

    /**
     * Create a new search.
     *
     * @param albums     the albums to search in
     * @param folders    the folders to search in
     * @param slideshows the slideshows to search in
     * @return the search
     */
    public Search createSearch(List<Album> albums, List<Folder> folders, List<Slideshow> slideshows) {
        return new Search(albums, folders, slideshows);
    }

    /**
     * Create a global search that searches in all data structures.
     *
     * @return the search
     */
    public Search createGlobalSearch() {
        return new Search(PhotoManagerController.get().getPhotoManager().getAlbums(), PhotoManagerController.get().getPhotoManager().getFolders(), PhotoManagerController.get().getPhotoManager().getSlideshows());
    }

    /**
     * Create a specific search for a data structure.
     *
     * @param dataStructure the data structure to create the search for
     * @return the search
     */
    public Search createSpecificSearch(DataStructure dataStructure) {
        if (dataStructure instanceof Album) {
            return new Search(List.of((Album) dataStructure), List.of(), List.of());
        } else if (dataStructure instanceof Folder) {
            return new Search(List.of(), List.of((Folder) dataStructure), List.of());
        } else if (dataStructure instanceof Slideshow) {
            return new Search(List.of(), List.of(), List.of((Slideshow) dataStructure));
        } else {
            return new Search(List.of(), List.of(), List.of());
        }
    }

    /**
     * Add a search parameter to the search.
     *
     * @param search    the search to add the parameter to
     * @param parameter the parameter to add
     */
    public void addSearchParameter(Search search, SearchParameter parameter) {
        search.addSearchParameter(parameter);
    }

    /**
     * Remove a search parameter from the search.
     *
     * @param search    the search to remove the parameter from
     * @param parameter the parameter to remove
     */
    public void removeSearchParameter(Search search, SearchParameter parameter) {
        search.removeSearchParameter(parameter);
    }

    /**
     * Create a new search parameter.
     *
     * @param name        the name
     * @param date        the date
     * @param rating      the rating
     * @param isPrivate   the is private
     * @param tags        the tags
     * @param description the description
     * @return the search parameter
     */
    public SearchParameter createSearchParameter(Optional<String> name,
                                                 Optional<Either<LocalDate, Period>> date,
                                                 Optional<Rating> rating,
                                                 Optional<Boolean> isPrivate,
                                                 Optional<TagList> tags,
                                                 Optional<String> description) {
        return new SearchParameter(name, date, rating, isPrivate, tags, description);
    }

    /**
     * Starts the search.
     *
     * @param search the search
     * @return the list of media that match the search parameters.
     */
    public List<Media> startSearch(Search search) {
        return search.search();
    }

    /**
     * Create a search parameter builder.
     *
     * @return the search parameter builder
     */
    public SearchParameterBuilder createSearchParameterBuilder() {
        return new SearchParameterBuilder();
    }
}
