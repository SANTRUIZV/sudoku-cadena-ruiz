package com.example.sudoku.model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Implementación de {@link IAlertBox} con la clase {@link Alert} de JavaFX.
 *
 * @author Juan Manuel Cadena
 * @version 1.0
 */
public class AlertBox implements IAlertBox {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean showConfirmBox(String title, String header, String message) {
        Alert alert = createAlert(Alert.AlertType.CONFIRMATION, title, header, message);
        Optional<ButtonType> response = alert.showAndWait();
        return response.isPresent() && response.get() == ButtonType.OK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showInfoBox(String title, String header, String message) {
        createAlert(Alert.AlertType.INFORMATION, title, header, message).showAndWait();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showErrorBox(String title, String header, String message) {
        createAlert(Alert.AlertType.ERROR, title, header, message).showAndWait();
    }

    /**
     * Crea una alerta del tipo indicado con sus textos.
     *
     * @param type    tipo de alerta (confirmación, información o error)
     * @param title   título de la ventana
     * @param header  texto principal de la alerta
     * @param message texto de detalle
     * @return la alerta lista para mostrarse
     */
    private Alert createAlert(Alert.AlertType type, String title, String header, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        return alert;
    }
}
