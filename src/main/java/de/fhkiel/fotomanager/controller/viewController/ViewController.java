package de.fhkiel.fotomanager.controller.viewController;

import de.fhkiel.fotomanager.model.datastructures.DataStructure;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Stack;

/**
 * The type View controller.
 *
 * @param <T> the type parameter
 */
abstract public class ViewController<T> {
    /**
     * The view
     */
    protected T view = null;

    /**
     * The history.
     */
    protected Stack<Scene> history;

    /**
     * The stage
     */
    protected Stage stage;

    /**
     * The Data structure.
     */
    protected DataStructure dataStructure;

    /**
     * The Add media.
     */
    protected boolean addMedia = false;

    /**
     * Method to go back to the previous view.
     */
    protected void goBack() {
        if (!history.isEmpty()) {
            stage.setScene(history.pop());
        }
    }

    /**
     * Method to show the view.
     */
    public void showView() {
        if (stage != null) {
            stage.show();
        }
    }

    /**
     * Switch scene.
     *
     * @param scene the scene
     */
    public void switchScene(Scene scene) {
        history.push(stage.getScene());
        stage.setScene(scene);
    }

    /**
     * Go back and rebuild.
     *
     * @param scene the scene
     */
    public void goBackAndRebuild(Scene scene) {
        int lastIndex = history.size() - 1;
        history.setElementAt(scene, lastIndex);
        stage.setScene(history.pop());
    }

    /**
     * Replace scene.
     *
     * @param scene the scene
     */
    public void replaceScene(Scene scene) {
        history.push(scene);
        stage.setScene(history.pop());
    }
}
