package de.fhkiel.fotomanager.controller.viewController.datamanagement;

import de.fhkiel.fotomanager.controller.viewController.ViewController;
import de.fhkiel.fotomanager.controller.viewController.ViewInteractable;
import de.fhkiel.fotomanager.view.datamanagement.ImportView;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;

/**
 * The type Import view controller.
 */
public abstract class ImportViewController extends ViewController<ImportView> implements ViewInteractable<ImportView> {
    /**
     * Check if the subdirectory is a subdirectory of the base directory.
     *
     * @param base the base directory
     * @param sub  the subdirectory
     * @return true if the subdirectory is a subdirectory of the base directory, false otherwise
     */
    protected boolean isSubdirectory(File base, File sub) {
        try {
            String basePath = base.getCanonicalPath();
            String subPath = sub.getCanonicalPath();
            return subPath.startsWith(basePath);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Set the source directory.
     */
    protected void setSource() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Source Folder for Import");
        view.setSelectedSourceDirectory(directoryChooser.showDialog(stage));
        if (view.getSelectedSourceDirectory() != null) {
            view.getSelectedSourceFolderLabel().setText("Selected source folder: " + view.getSelectedSourceDirectory().getAbsolutePath());
        } else {
            view.getSelectedSourceFolderLabel().setText("Source folder selection cancelled.");
        }
    }
}
