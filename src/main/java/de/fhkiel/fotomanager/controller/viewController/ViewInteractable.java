package de.fhkiel.fotomanager.controller.viewController;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Stack;

/**
 * The interface View interactable.
 *
 * @param <T> the type parameter
 */
public interface ViewInteractable<T> {

    /**
     * Create view t.
     *
     * @param stage   the stage
     * @param history the history
     * @return the t
     */
    T createView(Stage stage, Stack<Scene> history);
}
