package de.fhkiel.fotomanager.controller.viewController.mainView;

import de.fhkiel.fotomanager.controller.viewController.ViewController;
import de.fhkiel.fotomanager.controller.viewController.datamanagement.*;
import de.fhkiel.fotomanager.view.mainView.DataManagementMenuView;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Stack;

/**
 * The type Data management menu view controller.
 */
public class DataManagementMenuViewController extends ViewController<DataManagementMenuView> {

    /**
     * The Data management menu view controller instance.
     */
    public static DataManagementMenuViewController instance;

    private DataManagementMenuViewController() {}

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static DataManagementMenuViewController get() {
        if (instance == null) {
            instance = new DataManagementMenuViewController();
        }
        return instance;
    }

    /**
     * Create view data management menu view.
     *
     * @param stage   the stage
     * @param history the history
     * @return the data management menu view
     */
    public DataManagementMenuView createView(Stage stage, Stack<Scene> history) {
        this.stage = stage;
        this.history = history;
        showDataManagementMenuView();
        return view;
    }

    /**
     *
     * Show data management menu view.
     * This method is used to show the data management menu view.
     */
    private void showDataManagementMenuView() {
        view = new DataManagementMenuView(stage);
        view.getImportButton().setOnAction(event -> switchScene(XMLImportViewController.get().createView(stage, history).getScene()));
        view.getExportButton().setOnAction(event -> switchScene(XMLExportViewController.get().createView(stage, history).getScene()));
        view.getFolderImportButton().setOnAction(event -> switchScene(FolderImportViewController.get().createView(stage, history).getScene()));
        view.getBackButton().setOnAction(event -> goBack());
    }
}
