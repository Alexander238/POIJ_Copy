package de.fhkiel.fotomanager.controller.viewController.datastructures;

import de.fhkiel.fotomanager.controller.modelController.AlbumController;
import de.fhkiel.fotomanager.controller.modelController.PhotoManagerController;
import de.fhkiel.fotomanager.controller.modelController.SlideshowController;
import de.fhkiel.fotomanager.controller.modelController.XMLController;
import de.fhkiel.fotomanager.controller.viewController.ViewController;
import de.fhkiel.fotomanager.controller.viewController.components.MediaGridController;
import de.fhkiel.fotomanager.controller.viewController.components.SearchGUIController;
import de.fhkiel.fotomanager.controller.viewController.mediatypes.MediaDetailViewController;
import de.fhkiel.fotomanager.model.datastructures.DataStructure;
import de.fhkiel.fotomanager.model.datastructures.impl.Album;
import de.fhkiel.fotomanager.model.datastructures.impl.Folder;
import de.fhkiel.fotomanager.model.datastructures.impl.Slideshow;
import de.fhkiel.fotomanager.model.mediatypes.Media;
import de.fhkiel.fotomanager.model.mediatypes.impl.Photo;
import de.fhkiel.fotomanager.model.mediatypes.impl.Video;
import de.fhkiel.fotomanager.view.datastructures.AllMediaView;
import de.fhkiel.fotomanager.view.components.MediaGrid;
import de.fhkiel.fotomanager.view.components.MediaPreview;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * The type All media view controller.
 */
public class AllMediaViewController extends ViewController<AllMediaView> {
    /**
     * The all media view controller instance.
     */
    private static AllMediaViewController instance;

    private AllMediaViewController() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static AllMediaViewController get() {
        if (instance == null) {
            instance = new AllMediaViewController();
        }
        return instance;
    }

    /**
     * Create the all media view.
     *
     * @param stage         the stage
     * @param history       the history
     * @param addMedia      the add media
     * @param dataStructure the data structure
     * @return the AllMediaView instance
     */
    public AllMediaView createView(Stage stage, Stack<Scene> history, boolean addMedia, DataStructure dataStructure) {
        this.stage = stage;
        this.history = history;
        this.dataStructure = dataStructure;
        this.addMedia = addMedia;
        boolean showAddMediaButton = false;

        if ((dataStructure instanceof Album || dataStructure instanceof Slideshow)) {
            showAddMediaButton = true;
        }

        view = new AllMediaView(stage, this::search, this::resetSearch, addMedia, showAddMediaButton, dataStructure);

        setActions(showAddMediaButton);

        if (dataStructure != null && !addMedia) {
            showOnlyDataStructureMedia();
        }
        return view;
    }

    public void setActions(boolean showAddMediaButton) {
        if (dataStructure instanceof Folder)
            view.getBackButton().setOnAction(event -> goBackAndRebuild(FolderMenuViewController.get().createView(stage, history).getScene()));
        else if (dataStructure instanceof Album)
            view.getBackButton().setOnAction(event -> goBackAndRebuild(AlbumMenuViewController.get().createView(stage, history).getScene()));
        else if (dataStructure instanceof Slideshow)
            view.getBackButton().setOnAction(event -> goBackAndRebuild(SlideshowMenuViewController.get().createView(stage, history).getScene()));
        else view.getBackButton().setOnAction(event -> goBack());

        if (dataStructure != null && !(dataStructure instanceof Folder) && showAddMediaButton) {
            view.getAddMediaButton().setOnAction(e -> addMedia());
        }

        if (dataStructure instanceof Slideshow) {
            view.getOpenSlideshowButton().setOnAction(e -> openSlideshow());
        }

        view.getShowAllButton().setOnAction(event -> filterMedia("all"));
        view.getShowPhotosButton().setOnAction(event -> filterMedia("photos"));
        view.getShowVideosButton().setOnAction(event -> filterMedia("videos"));
        view.getToggleSearchButton().setOnAction(event -> toggleSearch());
        view.getShowUntaggedButton().setOnAction(event -> filterMedia("untagged"));

        if (dataStructure == null) {
            view.getMediaGrid().getMediaPreviews().forEach(mediaPreview -> mediaPreview.getImage().setOnMouseClicked(event -> {
                handleClick(mediaPreview, addMedia, view.getMediaGrid(), null);
            }));
        } else {
            view.getMediaGrid().getMediaPreviews().forEach(mediaPreview -> mediaPreview.getImage().setOnMouseClicked(event -> {
                handleClick(mediaPreview, addMedia, view.getMediaGrid(), dataStructure);
            }));
        }
    }

    /**
     * Filter media.
     * This method filters the media files based on the filter type.
     *
     * @param filterType the filter type to filter the media files
     */
    private void filterMedia(String filterType) {
        List<Media> mediaToFilter = new ArrayList<>();
        List<Media> filteredMediaFiles = new ArrayList<>();

        if (dataStructure != null && !this.addMedia) {
            mediaToFilter = dataStructure.getMediaList();
        } else {
            for (Media media : PhotoManagerController.get().getPhotoManager().getPhotos()) {
                mediaToFilter.add(media);
            }
            for (Media media : PhotoManagerController.get().getPhotoManager().getVideos()) {
                mediaToFilter.add(media);
            }
        }

        switch (filterType) {
            case "photos":
                for (Media media : mediaToFilter) {
                    if (media instanceof Photo) {
                        filteredMediaFiles.add(media);
                    }
                }
                break;
            case "videos":
                for (Media media : mediaToFilter) {
                    if (media instanceof Video) {
                        filteredMediaFiles.add(media);
                    }
                }
                break;
            case "untagged":
                for (Media media : mediaToFilter) {
                    if (media.getTags().getTags().isEmpty()) {
                        filteredMediaFiles.add(media);
                    }
                }
                break;
            case "all":
                for (Media media : mediaToFilter) {
                    filteredMediaFiles.add(media);
                }
        }
        view.getMediaGrid().setDisplayedMedia(filteredMediaFiles);
        MediaGridController.get().adjustMediaPreviews(view.getMediaGrid());
        view.setFlowPane(MediaGridController.get().updateMediaGrid(view.getMediaGrid()));
        replaceScene(view.getScene());
    }

    /**
     * Show only data structure media.
     */
    private void showOnlyDataStructureMedia() {
        List<Media> filteredMediaFiles = new ArrayList<>();

        dataStructure.getMediaList().forEach(media -> {
            filteredMediaFiles.add((Media) media);
        });
        view.getMediaGrid().setDisplayedMedia(filteredMediaFiles);
        MediaGridController.get().adjustMediaPreviews(view.getMediaGrid());
        view.setFlowPane(MediaGridController.get().updateMediaGrid(view.getMediaGrid()));
        replaceScene(view.getScene());
    }

    /**
     * Toggle the search bar.
     */
    private void toggleSearch() {
        boolean showSearch = view.isShowSearch();
        AllMediaView newView = createView(stage, history, addMedia, dataStructure);
        newView.setShowSearch(!showSearch);
        initialize(newView);
        replaceScene(newView.getScene());
    }

    private void initialize(AllMediaView view) {
        view.initialize(this::search, this::resetSearch);
        setActions(view.isShowAddMediaButton());
    }

    /**
     * Resets all search fields and search results.
     */
    private void resetSearch() {
        SearchGUIController.get().resetSearch(view.getSearchGUI());
        view.getMediaGrid().setDisplayedMedia(view.getMediaList());
        MediaGridController.get().adjustMediaPreviews(view.getMediaGrid());
        view.setFlowPane(MediaGridController.get().updateMediaGrid(view.getMediaGrid()));
        replaceScene(view.getScene());
    }

    /**
     * Starts the search and displays the results.
     */
    private void search() {
        if (view == null) {
            return;
        }

        try {
            ObservableList<Media> results = SearchGUIController.get().search(view.getSearchGUI(), dataStructure);
            view.getMediaGrid().setDisplayedMedia(results);
            MediaGridController.get().adjustMediaPreviews(view.getMediaGrid());
            view.setFlowPane(MediaGridController.get().updateMediaGrid(view.getMediaGrid()));

            if (dataStructure != null) {
                replaceScene(view.getScene());
            } else {
                // Das war ein versuch idk. Danach updatet die View iwie nicht richtig, aber mediaPrevious liste enthält dann nich nur das eine Video, sondern auch die Photos. Keine Ahnung warum warum das so ist.
                // view.setMediaGrid(new MediaGrid(view.getMediaGrid().getDisplayedMedia()));

                replaceScene(view.getScene());
            }
        } catch (Exception e) {
            view.getSearchGUI().getErrorLabel().setText(e.getMessage());
        }
    }

    /**
     * Handles the click event on a media preview.
     *
     * @param mediaPreview the media preview
     * @param selectable   flag if the media preview is selectable
     * @param mediaGrid    the media grid
     */
    private void handleClick(MediaPreview mediaPreview, boolean selectable, MediaGrid mediaGrid, DataStructure dataStructure) {
        if (selectable) {
            mediaPreview.setSelected(!mediaPreview.isSelected());
            if (mediaPreview.isSelected()) {
                view.getSelectedMedia().add(mediaPreview.getMedia());
            } else {
                view.getSelectedMedia().remove(mediaPreview.getMedia());
            }
        } else {
            if (dataStructure == null) {
                List<Media> mediaList = PhotoManagerController.get().getAllMedia();
                switchScene(MediaDetailViewController.get().createView(stage, history, mediaList, null, mediaPreview.getMedia()).getScene());
            } else {
                List<Media> mediaList = dataStructure.getMediaList();
                switchScene(MediaDetailViewController.get().createView(stage, history, mediaList, dataStructure, mediaPreview.getMedia()).getScene());
            }
            return;
        }
        view.setFlowPane(MediaGridController.get().updateMediaGrid(mediaGrid));
        replaceScene(view.getScene());
    }

    /**
     * Open slideshow.
     */
    private void openSlideshow() {
        switchScene(SlideshowViewController.get().createView(stage, history, (Slideshow) dataStructure).getScene());
    }

    /**
     * Adds media to the created album.
     */
    private void addMedia() {
        DataStructure dataStructureToSaveIn = dataStructure;
        AllMediaView allMediaView = AllMediaViewController.get().createView(stage, history, true, null);
        allMediaView.getSaveButton().setOnAction(event -> {
            System.out.println("Saving media to album");
            System.out.println(dataStructureToSaveIn);
            for (Media media : allMediaView.getSelectedMedia()) {
                if (dataStructureToSaveIn instanceof Slideshow)
                    SlideshowController.get().addPhotoToSlideshow((Slideshow) dataStructureToSaveIn, (Photo) media);
                else if (dataStructureToSaveIn instanceof Album) {
                    System.out.println("Adding media to album: " + media.getName());
                    AlbumController.get().addMediaToAlbum((Album) dataStructureToSaveIn, media);
                }
            }
            if (dataStructureToSaveIn instanceof Slideshow) {
                XMLController.get().saveSlideshowToXML((Slideshow) dataStructureToSaveIn);
                goBackAndRebuild(AllMediaViewController.get().createView(stage, history, false, dataStructureToSaveIn).getScene());
            } else if (dataStructureToSaveIn instanceof Album) {
                XMLController.get().saveAlbumToXML((Album) dataStructureToSaveIn);
                goBackAndRebuild(AllMediaViewController.get().createView(stage, history, false, dataStructureToSaveIn).getScene());
            }
        });
        switchScene(allMediaView.getScene());
    }

}
