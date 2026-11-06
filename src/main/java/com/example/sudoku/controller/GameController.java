package com.example.sudoku.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * Controlador de la ventana del juego (game-view.fxml).
 * Recibe los eventos de la vista y se comunica con el modelo.
 *
 * @author Juan Manuel Cadena
 * @version 1.0
 */
public class GameController {

    /** Contenedor de los 6 bloques del tablero. */
    @FXML
    private GridPane boardGrid;

    /** Muestra cuántas ayudas le quedan al jugador. */
    @FXML
    private Label lblHints;

    /** Muestra mensajes para el jugador (instrucciones y errores). */
    @FXML
    private Label lblMessage;

    /** Botón para iniciar un juego nuevo. */
    @FXML
    private Button btnNewGame;

    /** Botón para pedir una ayuda. */
    @FXML
    private Button btnHelp;

    /**
     * Se ejecuta al presionar "Nuevo juego".
     *
     * @param event evento del botón
     */
    @FXML
    private void handleNewGame(ActionEvent event) {
        // Se completa en el commit "Conecta nuevo juego con confirmación"
    }

    /**
     * Se ejecuta al presionar "Ayuda".
     *
     * @param event evento del botón
     */
    @FXML
    private void handleHelp(ActionEvent event) {
        // Se completa en el commit "Resalta errores y muestra la ayuda"
    }
}
