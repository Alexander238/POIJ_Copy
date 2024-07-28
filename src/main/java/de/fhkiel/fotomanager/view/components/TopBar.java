package de.fhkiel.fotomanager.view.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.layout.HBox;

/**
 * The type Top bar.
 */
public class TopBar {
    /**
     * Create top bar h box.
     *
     * @param controls the controls
     * @return the h box
     */
    public static HBox createTopBar(Control... controls) {
        HBox topBar = new HBox(10);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(10));
        topBar.getChildren().addAll(controls);
        return topBar;
    }
}
