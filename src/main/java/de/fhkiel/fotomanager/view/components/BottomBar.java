package de.fhkiel.fotomanager.view.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.layout.HBox;

/**
 * The type Bottom bar.
 */
public class BottomBar {

    /**
     * Create bottom bar h box.
     *
     * @param controls the controls
     * @return the h box
     */
    public static HBox createBottomBar(Control... controls) {
        HBox bottomBar = new HBox(10);
        bottomBar.setAlignment(Pos.CENTER);
        bottomBar.setPadding(new Insets(20));
        bottomBar.getChildren().addAll(controls);
        return bottomBar;
    }
}
