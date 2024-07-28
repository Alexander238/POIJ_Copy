package de.fhkiel.fotomanager.controller;

import de.fhkiel.fotomanager.controller.modelController.SearchController;
import de.fhkiel.fotomanager.model.search.Search;
import de.fhkiel.fotomanager.model.search.SearchParameter;
import de.fhkiel.fotomanager.util.TestSearchController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Search controller test.
 */
public class SearchControllerTest {
    /**
     * The Search controller.
     */
    private static SearchController searchController;

    /**
     * Sets up.
     */
    @BeforeAll
    public static void setUp() {
        searchController = SearchController.get();
    }

    /**
     * Gets singleton instance test.
     */
    @Test
    public void getSingletonInstanceTest() {
        SearchController searchController2 = SearchController.get();
        assertEquals(searchController, searchController2);
    }

    /**
     * Create search test.
     */
    @Test
    public void createSearchTest() {
        Search search = searchController.createSearch(List.of(), List.of(), List.of());
        assertEquals(search.getAlbums().size(), 0);
        assertEquals(search.getFolders().size(), 0);
        assertEquals(search.getSlideshows().size(), 0);
        assertNotNull(search);
    }

    /**
     * Create search parameter test.
     */
    @Test
    public void createSearchParameterTest() {
        SearchParameter searchParameter = searchController.createSearchParameter(Optional.empty(),Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
        assertNotNull(searchParameter);
    }

    /**
     * Add search parameter test.
     */
    @Test
    public void addSearchParameterTest() {
        Search search = searchController.createSearch(List.of(), List.of(), List.of());
        SearchParameter searchParameter = TestSearchController.createDefaultSearchParameter();
        searchController.addSearchParameter(search, searchParameter);
        assertEquals(search.getSearchParameters().size(), 1);
        assertTrue(search.getSearchParameters().contains(searchParameter));
    }
}
