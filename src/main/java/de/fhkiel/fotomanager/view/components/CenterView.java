package de.fhkiel.fotomanager.view.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * The type Center view.
 */
public class CenterView {
    /**
     * Create center view v box.
     *
     * @param content the content
     * @return the v box
     */
    public static VBox createCenterView(Pane content) {
        VBox centerView = new VBox();
        centerView.getChildren().add(content);
        centerView.setAlignment(Pos.CENTER);
        centerView.setPadding(new Insets(20));
        return centerView;
    }
}
