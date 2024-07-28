package de.fhkiel.fotomanager.controller;

import de.fhkiel.fotomanager.controller.modelController.*;
import de.fhkiel.fotomanager.model.mediatypes.Orientation;
import de.fhkiel.fotomanager.model.mediatypes.PlaybackSpeed;
import de.fhkiel.fotomanager.model.mediatypes.impl.Photo;
import de.fhkiel.fotomanager.model.mediatypes.impl.Video;
import de.fhkiel.fotomanager.model.tags.Tag;
import de.fhkiel.fotomanager.model.tags.TagType;
import de.fhkiel.fotomanager.util.TestUtilMediaController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.UnexpectedException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Media controller test.
 */
public class MediaControllerTest {
    /**
     * The Media controller.
     */
    private static MediaController mediaController;
    /**
     * The Tag controller.
     */
    private static TagController tagController;
    /**
     * The Album controller.
     */
    private static AlbumController albumController;
    /**
     * The Slideshow controller.
     */
    private static SlideshowController slideshowController;
    /**
     * The Folder controller.
     */
    private static FolderController folderController;
    /**
     * The Photo manager controller.
     */
    private static PhotoManagerController photoManagerController;
    /**
     * A Photo.
     */
    private static Photo photo;
    /**
     * A Video.
     */
    private static Video video;

    /**
     * Sets up.
     */
    @BeforeAll
    public static void setUp() {
        mediaController = MediaController.get();
        tagController = TagController.get();
        albumController = AlbumController.get();
        slideshowController = SlideshowController.get();
        folderController = FolderController.get();
        photoManagerController = PhotoManagerController.get();
    }

    /**
     * Reset.
     */
    @BeforeEach
    public void reset() {
        photo = TestUtilMediaController.createDefaultPhoto();
        video = TestUtilMediaController.createDefaultVideo();
        video.changePlaybackSpeed(PlaybackSpeed.X1);
        photo.getTags().getTags().clear();
        photoManagerController.getPhotoManager().getAlbums().clear();
        photoManagerController.getPhotoManager().getFolders().clear();
        photoManagerController.getPhotoManager().getSlideshows().clear();
    }

    /**
     * Gets singleton instance test.
     */
    @Test
    public void getSingletonInstanceTest() {
        MediaController anotherInstance = MediaController.get();
        assertNotNull(mediaController, "MediaController instance should not be null");
        assertSame(mediaController, anotherInstance, "MediaController instance should be the same");
    }

    /**
     * Create photo test.
     */
    @Test
    public void createPhotoTest() {
        photo = mediaController.createPhoto(
                TestUtilMediaController.defaultFile,
                TestUtilMediaController.defaultName,
                TestUtilMediaController.defaultDate,
                TestUtilMediaController.defaultDescription,
                TestUtilMediaController.defaultIsPrivate,
                TestUtilMediaController.defaultTags,
                TestUtilMediaController.defaultZoomFactor,
                TestUtilMediaController.defaultRating,
                TestUtilMediaController.defaultOrientation,
                TestUtilMediaController.defaultResolution
        );
        assertNotNull(photo, "Photo should not be null");
        assertEquals(TestUtilMediaController.defaultFile, photo.getFile(), "Photo should have the file 'defaultFile'");
        assertEquals(TestUtilMediaController.defaultName, photo.getName(), "Photo should have the name 'defaultName'");
        assertEquals(TestUtilMediaController.defaultDate, photo.getDate(), "Photo should have the date 'new Date()'");
        assertEquals(TestUtilMediaController.defaultDescription, photo.getDescription(), "Photo should have the description 'defaultDescription'");
        assertEquals(TestUtilMediaController.defaultIsPrivate, photo.isPrivate(), "Photo should be private");
        assertEquals(TestUtilMediaController.defaultTags, photo.getTags(), "Photo should have the tags 'defaultTags'");
        assertEquals(TestUtilMediaController.defaultZoomFactor, photo.getZoomFactor(), "Photo should have the zoom factor 1");
        assertEquals(TestUtilMediaController.defaultRating, photo.getRating(), "Photo should have the rating 'ZERO_STARS'");
        assertEquals(TestUtilMediaController.defaultOrientation, photo.getOrientation(), "Photo should have the orientation 'D0'");
        assertEquals(TestUtilMediaController.defaultResolution, photo.getResolution(), "Photo should have the resolution '1920x1080'");
    }

    /**
     * Create video test.
     */
    @Test
    public void createVideoTest() {
        Video video = mediaController.createVideo(
                TestUtilMediaController.defaultFile,
                TestUtilMediaController.defaultName,
                TestUtilMediaController.defaultDate,
                TestUtilMediaController.defaultDescription,
                TestUtilMediaController.defaultIsPrivate,
                TestUtilMediaController.defaultTags,
                TestUtilMediaController.defaultDuration,
                TestUtilMediaController.defaultRating,
                TestUtilMediaController.defaultOrientation,
                TestUtilMediaController.defaultResolution
        );
        assertNotNull(video, "Video should not be null");
        assertEquals(TestUtilMediaController.defaultFile, video.getFile(), "Video should have the file 'defaultFile'");
        assertEquals(TestUtilMediaController.defaultName, video.getName(), "Video should have the name 'defaultName'");
        assertEquals(TestUtilMediaController.defaultDate, video.getDate(), "Video should have the date 'new Date()'");
        assertEquals(TestUtilMediaController.defaultDescription, video.getDescription(), "Video should have the description 'defaultDescription'");
        assertEquals(TestUtilMediaController.defaultIsPrivate, video.isPrivate(), "Video should be private");
        assertEquals(TestUtilMediaController.defaultTags, video.getTags(), "Video should have the tags 'defaultTags'");
        assertEquals(TestUtilMediaController.defaultDuration, video.getDuration(), "Video should have the duration 1000");
        assertEquals(TestUtilMediaController.defaultRating, video.getRating(), "Video should have the rating 'ZERO_STARS'");
        assertEquals(TestUtilMediaController.defaultOrientation, video.getOrientation(), "Video should have the orientation 'D0'");
        assertEquals(TestUtilMediaController.defaultResolution, video.getResolution(), "Video should have the resolution '1920x1080'");
        assertEquals(0, video.getCurrentTime(), "Video should have the current time 0");
    }

    /**
     * Rename media test.
     */
    @Test
    public void renameMediaTest() {
        String newName = "newName";
        mediaController.renameMedia(photo, newName);
        assertEquals(newName, photo.getName(), "Photo should have the new name 'newName'");
    }

    /**
     * Rename with null string test.
     */
    @Test
    public void renameWithNullStringTest() {
        assertThrows(IllegalArgumentException.class, () -> mediaController.renameMedia(photo, null), "Should throw IllegalArgumentException");
    }

    /**
     * Rename with empty string test.
     */
    @Test
    public void renameWithEmptyStringTest() {
        assertThrows(IllegalArgumentException.class, () -> mediaController.renameMedia(photo, ""), "Should throw IllegalArgumentException");
    }

    /**
     * Add tag test.
     */
    @Test
    public void addTagTest() {
        Tag tag = tagController.createTag(TagType.INDEX, "tag");
        mediaController.addTag(photo, tag);
        assertTrue(photo.getTags().getTags().contains(tag), "Photo should contain the tag 'tag'");
        assertEquals(1, photo.getTags().getTags().size(), "Photo should have 1 tag");
    }

    /**
     * Add duplicate tag test.
     */
    @Test
    public void addDuplicateTagTest() {
        Tag tag = tagController.createTag(TagType.INDEX, "tag");
        mediaController.addTag(photo, tag);
        mediaController.addTag(photo, tag);
        assertTrue(photo.getTags().getTags().contains(tag), "Photo should contain the tag 'tag'");
        assertEquals(1, photo.getTags().getTags().size(), "Photo should have 1 tag");
    }

    /**
     * Add tag to photo in two albums test.
     */
    /* needs adjustment because of XML
    @Test
    public void addTagToPhotoInTwoAlbumsTest() {
        Tag tag = tagController.createTag(TagType.INDEX, "tag");
        Album album1 = albumController.createAlbum("album1");
        Album album2 = albumController.createAlbum("album2");
        photoManagerController.addDataStructure(album1);
        photoManagerController.addDataStructure(album2);
        albumController.addMediaToAlbum(album1, photo);
        albumController.addMediaToAlbum(album2, photo);
        mediaController.addTag(photo, tag);
        assertTrue(photo.getTags().contains(tag), "Photo should contain the tag 'tag'");
        assertTrue(album1.getMediaList().get(0).getTags().contains(tag), "Photo in album1 should contain the tag 'tag'");
        assertTrue(album2.getMediaList().get(0).getTags().contains(tag), "Photo in album2 should contain the tag 'tag'");
    }
    */

    /**
     * Remove tag test.
     */
    @Test
    public void removeTagTest() {
        Tag tag = tagController.createTag(TagType.INDEX, "tag");
        mediaController.addTag(photo, tag);
        mediaController.removeTag(photo, tag);
        assertFalse(photo.getTags().getTags().contains(tag), "Photo should not contain the tag 'tag'");
        assertEquals(0, photo.getTags().getTags().size(), "Photo should have 0 tags");
    }

    /**
     * Remove non existing tag test.
     */
    @Test
    public void removeNonExistingTagTest() {
        Tag tag = tagController.createTag(TagType.INDEX, "tag");
        mediaController.removeTag(photo, tag);
        assertFalse(photo.getTags().getTags().contains(tag), "Photo should not contain the tag 'tag'");
        assertEquals(0, photo.getTags().getTags().size(), "Photo should have 0 tags");
    }

    /**
     * Delete media test.
     */
    /* needs adjustment because of XML
    @Test
    public void deleteMediaTest() {
        Album album1 = albumController.createAlbum("album1");
        Album album2 = albumController.createAlbum("album2");
        photoManagerController.addDataStructure(album1);
        photoManagerController.addDataStructure(album2);
        album1.addMedia(photo);
        album2.addMedia(photo);
        mediaController.deleteMedia(photo);
        assertTrue(photo.getTags().isEmpty(), "Photo should have no tags");
        assertNull(photo.getFile(), "Photo should have no file");
        assertNull(photo.getName(), "Photo should have no name");
        assertNull(photo.getDate(), "Photo should have no date");
        assertNull(photo.getDescription(), "Photo should have no description");
        assertNull(photo.getRating(), "Photo should have no rating");
        assertNull(photo.getOrientation(), "Photo should have no orientation");
        assertNull(photo.getResolution(), "Photo should have no resolution");
        assertFalse(photo.isPrivate(), "Photo should not be private");
        photoManagerController.getPhotoManager().getAlbums().forEach(album -> assertFalse(album.getMediaList().contains(photo), "Photo should not be in any album"));
    }
     */

    /**
     * Rotate clockwise test.
     *
     * @throws UnexpectedException the unexpected exception
     */
    @Test
    public void rotateClockwiseTest() throws UnexpectedException {
        mediaController.rotateMediaClockwise(photo);
        assertEquals(Orientation.D90, photo.getOrientation(), "Photo should have the orientation 'D90'");
    }

    /**
     * Rotate clockwise twice test.
     *
     * @throws UnexpectedException the unexpected exception
     */
    @Test
    public void rotateClockwiseTwiceTest() throws UnexpectedException {
        mediaController.rotateMediaClockwise(photo);
        mediaController.rotateMediaClockwise(photo);
        assertEquals(Orientation.D180, photo.getOrientation(), "Photo should have the orientation 'D180'");
    }

    /**
     * Rotate counter clockwise test.
     *
     * @throws UnexpectedException the unexpected exception
     */
    @Test
    public void rotateCounterClockwiseTest() throws UnexpectedException {
        mediaController.rotateMediaCounterClockwise(photo);
        assertEquals(Orientation.D270, photo.getOrientation(), "Photo should have the orientation 'D270'");
    }

    /**
     * Zoom in photo test.
     */
    @Test
    public void zoomInPhotoTest() {
        mediaController.zoomInPhoto(photo);
        assertEquals(1.1, photo.getZoomFactor(), "Photo should have the zoom factor 1.1");
    }

    /**
     * Zoom out photo test.
     */
    @Test
    public void zoomOutPhotoTest() {
        mediaController.zoomOutPhoto(photo);
        assertEquals(0.9, photo.getZoomFactor(), "Photo should have the zoom factor 0.9");
    }

    /**
     * Change playback speed test.
     */
    @Test
    public void changePlaybackSpeedTest() {
        mediaController.changePlaybackSpeed(video, PlaybackSpeed.X2);
        assertEquals(PlaybackSpeed.X2, video.getPlaybackSpeed(), "Video should have the playback speed 2");
    }

    /**
     * Increase playback speed test.
     */
    @Test
    public void increasePlaybackSpeedTest() {
        mediaController.increasePlaybackSpeed(video);
        assertEquals(PlaybackSpeed.X2, video.getPlaybackSpeed(), "Video should have the playback speed 2");
    }

    /**
     * Increase playback speed to max test.
     */
    @Test
    public void increasePlaybackSpeedToMaxTest() {
        mediaController.changePlaybackSpeed(video, PlaybackSpeed.X32);
        assertThrows(IllegalArgumentException.class, () -> mediaController.increasePlaybackSpeed(video), "Should throw IllegalArgumentException");
    }

    /**
     * Decrease playback speed test.
     */
    @Test
    public void decreasePlaybackSpeedTest() {
        mediaController.decreasePlaybackSpeed(video);
        assertEquals(PlaybackSpeed.X05, video.getPlaybackSpeed(), "Video should have the playback speed 1");
    }

    /**
     * Decrease playback speed to min test.
     */
    @Test
    public void decreasePlaybackSpeedToMinTest() {
        mediaController.changePlaybackSpeed(video, PlaybackSpeed.X025);
        assertThrows(IllegalArgumentException.class, () -> mediaController.decreasePlaybackSpeed(video), "Should throw IllegalArgumentException");
    }

    /**
     * Show albums test.
     */
    /* needs adjustment because of XML
    @Test
    public void showAlbumsTest() {
        Album album1 = albumController.createAlbum("album1");
        Album album2 = albumController.createAlbum("album2");
        Album album3 = albumController.createAlbum("album3");
        photoManagerController.addDataStructure(album1);
        photoManagerController.addDataStructure(album2);
        photoManagerController.addDataStructure(album3);
        album1.addMedia(photo);
        album3.addMedia(photo);
        assertEquals(List.of(album1, album3), mediaController.showAlbums(photo), "Photo should be in album1 and album3");
    }
     */

    /**
     * Show slideshows test.
     */
    /* needs adjustment because of XML
    @Test
    public void showSlideshowsTest() {
        Slideshow slideshow1 = slideshowController.createSlideshow("slideshow1", 10);
        Slideshow slideshow2 = slideshowController.createSlideshow("slideshow2", 10);
        Slideshow slideshow3 = slideshowController.createSlideshow("slideshow3", 10);
        photoManagerController.addDataStructure(slideshow1);
        photoManagerController.addDataStructure(slideshow2);
        photoManagerController.addDataStructure(slideshow3);
        slideshow1.addMedia(photo);
        slideshow3.addMedia(photo);
        assertEquals(List.of(slideshow1, slideshow3), mediaController.showSlideshows(photo), "Photo should be in slideshow1 and slideshow3");
    }
     */

    /**
     * Show folders test.
     */
    /* needs adjustment because of XML
    @Test
    public void showFoldersTest() {
        Folder folder1 = folderController.createFolder("folder1", null, "event1");
        Folder folder2 = folderController.createFolder("folder2", null, "event2");
        Folder folder3 = folderController.createFolder("folder3", null, "event3");
        photoManagerController.addDataStructure(folder1);
        photoManagerController.addDataStructure(folder2);
        photoManagerController.addDataStructure(folder3);
        folder1.addMedia(photo);
        folder3.addMedia(photo);
        assertEquals(List.of(folder1, folder3), mediaController.showFolders(photo), "Photo should be in folder1 and folder3");
    }
     */
}
