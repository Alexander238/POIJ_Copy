package de.fhkiel.fotomanager.search;

import de.fhkiel.fotomanager.controller.modelController.*;
import de.fhkiel.fotomanager.model.search.Search;
import de.fhkiel.fotomanager.model.search.SearchParameter;
import de.fhkiel.fotomanager.model.Period;
import de.fhkiel.fotomanager.model.datastructures.impl.Album;
import de.fhkiel.fotomanager.model.datastructures.impl.Folder;
import de.fhkiel.fotomanager.model.datastructures.impl.Slideshow;
import de.fhkiel.fotomanager.model.mediatypes.Media;
import de.fhkiel.fotomanager.model.mediatypes.Orientation;
import de.fhkiel.fotomanager.model.mediatypes.Rating;
import de.fhkiel.fotomanager.model.mediatypes.Resolution;
import de.fhkiel.fotomanager.model.mediatypes.impl.Photo;
import de.fhkiel.fotomanager.model.mediatypes.impl.Video;
import de.fhkiel.fotomanager.model.tags.Tag;
import de.fhkiel.fotomanager.model.tags.TagType;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Search test.
 */
public class SearchTest {

    /**
     * The Search to be tested.
     */
    private Search search;

    // Mock data
    private Photo photo1;
    private Photo photo2;
    private Photo photo3;
    private Video video1;
    private Video video2;
    private Album album1;
    private Album album2;
    private Folder folder1;
    private Slideshow slideshow1;
    private static Tag tag1;
    /**
     * The Search results list.
     */
    private List<Media> searchResults = new ArrayList<>();
    private List<Album> albums = new ArrayList<>();
    private List<Folder> folders = new ArrayList<>();
    private List<Slideshow> slideshows = new ArrayList<>();

    // Controllers
    /**
     * The Media controller.
     */
    private static MediaController mediaController;
    /**
     * The Album controller.
     */
    private static AlbumController albumController;
    /**
     * The Folder controller.
     */
    private static FolderController folderController;
    /**
     * The Slideshow controller.
     */
    private static SlideshowController slideshowController;
    /**
     * The Tag controller.
     */
    private static TagController tagController;
    /**
     * The Search controller.
     */
    private static SearchController searchController;

    /**
     * Sets up controllers.
     */
    @BeforeAll
    public static void setUp() {
        mediaController = MediaController.get();
        albumController = AlbumController.get();
        folderController = FolderController.get();
        tagController = TagController.get();
        slideshowController = SlideshowController.get();
        searchController = SearchController.get();
    }

    /**
     * Set up a testable environment to test the Search class
     */
    @BeforeEach
    public void beforeEach() {
        searchResults.clear();
        albums.clear();
        folders.clear();
        slideshows.clear();

        tag1 = tagController.createTag(TagType.PERSON, "Olaf Scholz");

        photo1 = mediaController.createPhoto("filePath/photo1", "Photo1", LocalDate.of(2022, 3, 5), "A Photo",
                false, TagListController.get().createEmptyTagList(), 1.0, Rating.FIVE_STARS, Orientation.D0, new Resolution(1920, 1080));

        photo2 = mediaController.createPhoto("filePath/photo2", "Photo2", LocalDate.of(2024, 1, 1), "A Photo",
                false, TagListController.get().createEmptyTagList(), 1.0, Rating.FOUR_STARS, Orientation.D0, new Resolution(1920, 1080));

        photo3 = mediaController.createPhoto("filePath/photo2", "Olaf Scholz", LocalDate.of(2023, 2, 10), "Photo of Olaf Scholz",
                true, TagListController.get().createTagList(List.of(tag1)), 1.0, Rating.ZERO_STARS, Orientation.D0, new Resolution(3840, 2160));

        video1 = mediaController.createVideo("filePath/video1", "Video1", LocalDate.of(2024, 1, 1), "Good Video",
                false, TagListController.get().createEmptyTagList(), 100, Rating.THREE_STARS, Orientation.D90, new Resolution(1366, 768));

        video2 = mediaController.createVideo("filePath/video2", "Video2", LocalDate.of(2021, 9, 9), "Better Video",
                false, TagListController.get().createEmptyTagList(), 1000, Rating.FIVE_STARS, Orientation.D90, new Resolution(1920, 1080));

        album1 = albumController.createAlbum("All Photos");
        albumController.addMediaToAlbum(album1, photo1, photo2, photo3);

        album2 = albumController.createAlbum("All Videos");
        albumController.addMediaToAlbum(album2, video1, video2);

        folder1 = folderController.createFolder("Everything", Either.right(new Period(LocalDate.of(2021, 9, 9), LocalDate.of(2024, 1, 1))), "Everything");
        folderController.addMediaToFolder(folder1, photo1, photo2, photo3, video1, video2);

        slideshow1 = slideshowController.createSlideshow("Photo2", 60);
        slideshowController.addPhotoToSlideshow(slideshow1, photo2);

        albums.add(album1);
        albums.add(album2);
        folders.add(folder1);
        slideshows.add(slideshow1);

        search = new Search(
                albums,
                folders,
                slideshows
        );
        search.getSearchResults().clear();
        search.getSearchParameters().clear();
    }

    /**
     * Test if the Search class can be instantiated with empty lists.
     */
    @Test
    public void createSearchTest() {
        Search search = new Search(List.of(), List.of(), List.of());

        // Check if the object is not null
        assertNotNull(search);
    }

    /**
     * Test if the Search class can be instantiated with parameters.
     */
    @Test
    public void createSearchWithParametersTest() {
        // Check if the object is not null
        assertNotNull(search);

        // Check if albums are set correctly
        assertNotNull(search.getAlbums());
        assertEquals(albums, search.getAlbums());

        // Check if folders are set correctly
        assertNotNull(search.getFolders());
        assertEquals(folders, search.getFolders());

        // Check if slideshows are set correctly
        assertNotNull(search.getSlideshows());
        assertEquals(slideshows, search.getSlideshows());
    }

    /**
     * Test if SearchParameter can be added to the Search object.
     */
    @Test
    public void addSearchParameterTest() {
        // Create an empty search parameter
        SearchParameter searchParameter = searchController.createSearchParameter(Optional.empty(),Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
        search.addSearchParameter(searchParameter);

        // Check if the search parameters list contains the added parameter
        assertTrue(search.getSearchParameters().contains(searchParameter), "Search parameters should contain the added parameter");
    }

    /**
     * Test if SearchParameter can be removed from the Search object.
     */
    @Test
    public void removeSearchParameterTest() {
        // Create an empty search parameter
        SearchParameter searchParameter = searchController.createSearchParameter(Optional.empty(),Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
        search.addSearchParameter(searchParameter);

        // Remove the search parameter
        search.removeSearchParameter(searchParameter);

        // Check if the search parameters list does not contain the removed parameter
        assertFalse(search.getSearchParameters().contains(searchParameter), "Search parameters should not contain the removed parameter");
    }

    /**
     * Test adding two identical SearchParameters to the Search object.
     */
    @Test
    public void addIdenticalSearchParametersTest() {
        // Create an empty search parameter
        SearchParameter searchParameter = searchController.createSearchParameter(Optional.empty(),Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
        search.addSearchParameter(searchParameter);

        // Add the same search parameter again
        search.addSearchParameter(searchParameter);

        // Check if the search parameters list contains only one parameter
        assertEquals(1, search.getSearchParameters().size(), "Search parameters should contain only one parameter");

        // Check if the search parameters list contains the added parameter
        assertTrue(search.getSearchParameters().contains(searchParameter), "Search parameters should contain the added parameter");
    }

    /**
     * Test if the search method returns the correct search results with no search parameters.
     */
    @Test
    public void noSearchParameterSearchTest() {
        searchResults = search.search();

        // Check if the search results are not null
        assertNotNull(searchResults, "Search result should not be null");

        // Check if the search results contain photo1
        assertTrue(searchResults.contains(photo1), "Search result should contain photo1");

        // Check if the search results contain photo2
        assertTrue(searchResults.contains(photo2), "Search result should contain photo2");

        // Check if the search results contain photo3
        assertTrue(searchResults.contains(photo3), "Search result should contain photo3");

        // Check if the search results contain video1
        assertTrue(searchResults.contains(video1), "Search result should contain video1");

        // Check if the search results contain video2
        assertTrue(searchResults.contains(video2), "Search result should contain video2");
    }

    /**
     * Test if the search method returns the correct search results with an empty search parameter.
     */
    @Test
    public void emptySearchParameterSearchTest() {
        // Create an empty search parameter
        SearchParameter searchParameter = searchController.createSearchParameter(Optional.empty(),Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
        search.addSearchParameter(searchParameter);

        searchResults = search.search();

        // Check if the search results are not null
        assertNotNull(searchResults, "Search result should not be null");

        // Check if the search results contain photo1
        assertTrue(searchResults.contains(photo1), "Search result should contain photo1");

        // Check if the search results contain photo2
        assertTrue(searchResults.contains(photo2), "Search result should contain photo2");

        // Check if the search results contain photo3
        assertTrue(searchResults.contains(photo3), "Search result should contain photo3");

        // Check if the search results contain video1
        assertTrue(searchResults.contains(video1), "Search result should contain video1");

        // Check if the search results contain video2
        assertTrue(searchResults.contains(video2), "Search result should contain video2");
    }

    /**
     * Test if the search method returns the correct search results with a name search parameter.
     */
    @Test
    public void nameSearchTest() {
        // Create a search parameter for a specific date
        SearchParameter searchParameter =
                searchController.createSearchParameter(
                        Optional.of("Olaf Scholz"),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty()
                );
        search.addSearchParameter(searchParameter);

        searchResults = search.search();

        // Check if the search results are not null
        assertNotNull(searchResults, "Search result should not be null");

        // Check if the search results contain photo1
        assertTrue(searchResults.contains(photo3), "Search result should contain video2");

        // Check if the search results only contain video1
        assertEquals(1, searchResults.size(), "Search result should contain only one media object");
    }


    /**
     * Test if the search method returns the correct search results with a specific date search parameter.
     */
    @Test
    public void specificDateSearchTest() {
        // Create a search parameter for a specific date
        SearchParameter searchParameter =
                searchController.createSearchParameter(
                        Optional.empty(),
                        Optional.of(Either.left(LocalDate.of(2021, 9, 9))),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty()
                );
        search.addSearchParameter(searchParameter);

        searchResults = search.search();

        // Check if the search results are not null
        assertNotNull(searchResults, "Search result should not be null");

        // Check if the search results contain photo1
        assertTrue(searchResults.contains(video2), "Search result should contain video2");

        // Check if the search results only contain video1
        assertEquals(1, searchResults.size(), "Search result should contain only one media object");
    }

    /**
     * Test if the search method returns the correct search results with a time period search parameter.
     */
    @Test
    public void timePeriodSearchTest() {
        // Create a search parameter for a time period
        SearchParameter searchParameter =
                searchController.createSearchParameter(
                        Optional.empty(),
                        Optional.of(Either.right(new Period(LocalDate.of(2022, 1, 1), LocalDate.of(2023, 12, 31)))),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty()
                );
        search.addSearchParameter(searchParameter);

        searchResults = search.search();

        // Check if the search results are not null
        assertNotNull(searchResults, "Search result should not be null");

        // Check if the search results contain photo1
        assertTrue(searchResults.contains(photo1), "Search result should contain photo1");

        // Check if the search results contain photo3
        assertTrue(searchResults.contains(photo3), "Search result should contain photo3");

        // Check if the search results only contain photo3
        assertEquals(2, searchResults.size(), "Search result should contain only one media object");
    }

    /**
     * Test if the search method returns the correct search results with a rating search parameter.
     */
    @Test
    public void ratingSearchTest() {
        // Create a search parameter for a specific rating
        SearchParameter searchParameter =
                searchController.createSearchParameter(
                        Optional.empty(),
                        Optional.empty(),
                        Optional.of(Rating.THREE_STARS),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty()
                );
        search.addSearchParameter(searchParameter);

        searchResults = search.search();

        // Check if the search results are not null
        assertNotNull(searchResults, "Search result should not be null");

        // Check if the search results contain photo1
        assertTrue(searchResults.contains(video1), "Search result should contain video1");

        // Check if the search results only contain video1
        assertEquals(1, searchResults.size(), "Search result should contain only one media objects");
    }

    /**
     * Test if the search method returns the correct search results with a private flag search parameter.
     */
    @Test
    public void privateFlagSearchTest() {
        // Create a search parameter for a private flag
        SearchParameter searchParameter =
                searchController.createSearchParameter(
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.of(true),
                        Optional.empty(),
                        Optional.empty()
                );
        search.addSearchParameter(searchParameter);

        searchResults = search.search();

        // Check if the search results are not null
        assertNotNull(searchResults, "Search result should not be null");

        // Check if the search results contain photo3
        assertTrue(searchResults.contains(photo3), "Search result should contain photo3");

        // Check if the search results only contain photo3
        assertEquals(1, searchResults.size(), "Search result should contain only one media object");
    }

    /**
     * Test if the search method returns the correct search results with a description search parameter.
     */
    @Test
    public void descriptionSearchTest() {
        // Create a search parameter for a description
        SearchParameter searchParameter =
                searchController.createSearchParameter(
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.of("Video")
                );
        search.addSearchParameter(searchParameter);

        searchResults = search.search();

        // Check if the search results are not null
        assertNotNull(searchResults, "Search result should not be null");

        // Check if the search results contain video1
        assertTrue(searchResults.contains(video1), "Search result should contain video1");

        // Check if the search results contain video2
        assertTrue(searchResults.contains(video2), "Search result should contain video2");

        // Check if the search results only contain video1 and video2
        assertEquals(2, searchResults.size(), "Search result should contain only two media objects");
    }

    /**
     * Test if the search method returns the correct search results with a tag search parameter.
     */
    @Test
    public void tagSearchTest() {
        // Create a search parameter for a tag
        SearchParameter searchParameter =
                searchController.createSearchParameter(
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.of(TagListController.get().createTagList(List.of(tag1))),
                        Optional.empty()
                );
        search.addSearchParameter(searchParameter);

        searchResults = search.search();

        // Check if the search results are not null
        assertNotNull(searchResults, "Search result should not be null");

        // Check if the search results contain photo3
        assertTrue(searchResults.contains(photo3), "Search result should contain photo3");

        // Check if the search results only contain photo3
        assertEquals(1, searchResults.size(), "Search result should contain only one media object");
    }

    /**
     * Two search parameters test.
     */
    @Test
    public void twoSearchParametersTest() {
        // Create a search parameter for a specific date
        SearchParameter searchParameter1 =
                searchController.createSearchParameter(
                        Optional.empty(),
                        Optional.of(Either.left(LocalDate.of(2023, 2, 10))),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty()
                );
        search.addSearchParameter(searchParameter1);

        // Create a search parameter for a specific rating
        SearchParameter searchParameter2 =
                searchController.createSearchParameter(
                        Optional.empty(),
                        Optional.of(Either.left(LocalDate.of(2021, 9, 9))),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty()
                );
        search.addSearchParameter(searchParameter2);

        searchResults = search.search();

        // Check if the search results are not null
        assertNotNull(searchResults, "Search result should not be null");

        // Check if the search results contain photo2
        assertTrue(searchResults.contains(photo3), "Search result should contain photo3");

        // Check if the search results only contain video2
        assertTrue(searchResults.contains(video2), "Search result should contain video2");

        // Check if the search results only contain photo2
        assertEquals(2, searchResults.size(), "Search result should contain two media objects");
    }
}
