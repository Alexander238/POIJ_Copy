package de.fhkiel.fotomanager.controller.viewController.components;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * The type Show alert.
 */
public class ShowAlert {
    /**
     * Shows an alert dialog with the given title and message.
     *
     * @param title   the title of the dialog
     * @param message the message content of the dialog
     */
    public static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Shows a confirmation dialog with the given title and message.
     *
     * @param title   the title of the dialog
     * @param message the message content of the dialog
     * @return true if the user clicks OK, false if Cancel or closes the dialog
     */
    public static boolean showConfirmationAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(title);
        alert.setContentText(message);

        alert.showAndWait();

        return alert.getResult() == ButtonType.OK;
    }
}
