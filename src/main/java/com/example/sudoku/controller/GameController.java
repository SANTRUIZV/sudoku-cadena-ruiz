package com.example.sudoku.controller;

import com.example.sudoku.model.ISudokuBoard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

    /** Celdas del tablero: cells[fila][columna]. */
    private final TextField[][] cells = new TextField[ISudokuBoard.SIZE][ISudokuBoard.SIZE];

    /**
     * JavaFX lo llama automáticamente después de cargar el FXML.
     * Crea las celdas del tablero.
     */
    @FXML
    public void initialize() {
        createBoardCells();
    }

    /**
     * Crea las 36 celdas del tablero. Cada bloque de 2x3 es un {@link GridPane}
     * propio, y los 6 bloques se acomodan en {@code boardGrid} (2 columnas x 3 filas).
     * El espacio entre bloques es lo que los separa visualmente.
     */
    private void createBoardCells() {
        int blockColumns = ISudokuBoard.SIZE / ISudokuBoard.BLOCK_COLS;   // 2 bloques por fila
        int blockRows = ISudokuBoard.SIZE / ISudokuBoard.BLOCK_ROWS;      // 3 bloques por columna

        for (int blockRow = 0; blockRow < blockRows; blockRow++) {
            for (int blockCol = 0; blockCol < blockColumns; blockCol++) {
                GridPane block = new GridPane();
                block.getStyleClass().add("block");

                for (int r = 0; r < ISudokuBoard.BLOCK_ROWS; r++) {
                    for (int c = 0; c < ISudokuBoard.BLOCK_COLS; c++) {
                        int row = blockRow * ISudokuBoard.BLOCK_ROWS + r;
                        int col = blockCol * ISudokuBoard.BLOCK_COLS + c;

                        TextField cell = new TextField();
                        cell.getStyleClass().add("cell");
                        cell.setEditable(false);
                        cells[row][col] = cell;
                        block.add(cell, c, r);
                    }
                }
                boardGrid.add(block, blockCol, blockRow);
            }
        }
    }

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
