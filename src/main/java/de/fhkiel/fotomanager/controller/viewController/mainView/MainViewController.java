package de.fhkiel.fotomanager.controller.viewController.mainView;

import de.fhkiel.fotomanager.controller.viewController.ViewController;
import de.fhkiel.fotomanager.controller.viewController.ViewInteractable;
import de.fhkiel.fotomanager.view.mainView.MainView;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Stack;

/**
 * The type Main view controller.
 */
public class MainViewController extends ViewController<MainView> implements ViewInteractable<MainView> {
    /**
     * The main view controller instance.
     */
    private static MainViewController instance;

    /**
     * Creates a new history stack at start.
     */
    private MainViewController() {
        this.history = new Stack<>();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static synchronized MainViewController get() {
        if (instance == null) {
            instance = new MainViewController();
        }
        return instance;
    }

    /**
     * Sets stage and initializes the main view.
     *
     * @param stage the stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
        createView(this.stage, history);
    }

    /**
     * Initialize the main view.
     */
    @Override
    public MainView createView(Stage stage, Stack<Scene> history) {
        this.view = new MainView();
        this.history.add(view.getScene());
        view.start(stage);

        // Set the action for the buttons
        view.getMediaMenuButton().setOnAction(event -> switchScene(MediaMenuViewController.get().createView(stage, history).getScene()));
        view.getDatamanagementButton().setOnAction(event -> switchScene(DataManagementMenuViewController.get().createView(stage, history).getScene()));
        return view;
    }
}
