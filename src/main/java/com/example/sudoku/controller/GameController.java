package com.example.sudoku.controller;

import com.example.sudoku.model.AlertBox;
import com.example.sudoku.model.IAlertBox;
import com.example.sudoku.model.ISudokuBoard;
import com.example.sudoku.model.SudokuBoard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.List;

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

    /** Modelo del juego. */
    private final ISudokuBoard board = new SudokuBoard();

    /** Muestra las alertas (confirmación, información y error). */
    private final IAlertBox alertBox = new AlertBox();

    /** Celda que el jugador tiene seleccionada, o null si no hay ninguna. */
    private TextField selectedCell;

    /** Celda donde se está mostrando la sugerencia de ayuda, o null si no hay. */
    private TextField hintCell;

    /**
     * JavaFX lo llama automáticamente después de cargar el FXML.
     * Crea las celdas del tablero. El tablero y el botón de ayuda quedan
     * desactivados hasta que el jugador inicie un juego.
     */
    @FXML
    public void initialize() {
        createBoardCells();
        boardGrid.setDisable(true);
        btnHelp.setDisable(true);
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
                        addCellEvents(cell, row, col);
                        cells[row][col] = cell;
                        block.add(cell, c, r);
                    }
                }
                boardGrid.add(block, blockCol, blockRow);
            }
        }
    }

    /**
     * Agrega a una celda el evento de mouse (seleccionarla) y el de teclado
     * (escribir o borrar un número).
     *
     * @param cell celda de la vista
     * @param row  fila de la celda
     * @param col  columna de la celda
     */
    private void addCellEvents(TextField cell, int row, int col) {
        cell.setOnMouseClicked(event -> selectCell(cell));

        cell.setOnKeyPressed(new CellKeyAdapter() {
            @Override
            public void onNumberTyped(int number) {
                if (board.isFixed(row, col)) {
                    return;
                }
                board.setValue(row, col, number);
                cell.setText(String.valueOf(number));
                afterCellChanged(cell);
            }

            @Override
            public void onDeletePressed() {
                if (board.isFixed(row, col)) {
                    return;
                }
                board.setValue(row, col, ISudokuBoard.EMPTY);
                cell.setText("");
                afterCellChanged(cell);
            }

            @Override
            public void onInvalidKey() {
                showMessage("Solo puedes escribir números del 1 al 6", true);
            }
        });
    }

    /**
     * Marca una celda como seleccionada y le quita la marca a la anterior.
     *
     * @param cell celda que el jugador hizo clic
     */
    private void selectCell(TextField cell) {
        if (selectedCell != null) {
            selectedCell.getStyleClass().remove("selected-cell");
        }
        selectedCell = cell;
        selectedCell.getStyleClass().add("selected-cell");
        selectedCell.requestFocus();
    }

    /**
     * Se ejecuta al presionar "Nuevo juego".
     *
     * @param event evento del botón
     */
    @FXML
    private void handleNewGame(ActionEvent event) {
        boolean accepted = alertBox.showConfirmBox(
                "Nuevo juego",
                "¿Quieres iniciar un juego nuevo?",
                "Se borrará el tablero actual."
        );
        if (!accepted) {
            return;
        }

        board.generateNewBoard();
        refreshBoard();
        boardGrid.setDisable(false);
        btnHelp.setDisable(false);
        showMessage("Haz clic en una celda y escribe un número del 1 al 6", false);
    }

    /**
     * Copia el estado del modelo a la vista: el número de cada celda,
     * qué celdas son fijas y cuántas ayudas quedan.
     */
    private void refreshBoard() {
        for (int row = 0; row < ISudokuBoard.SIZE; row++) {
            for (int col = 0; col < ISudokuBoard.SIZE; col++) {
                TextField cell = cells[row][col];
                int value = board.getValue(row, col);
                cell.setText(value == ISudokuBoard.EMPTY ? "" : String.valueOf(value));

                cell.getStyleClass().removeAll("fixed-cell", "selected-cell", "error-cell");
                if (board.isFixed(row, col)) {
                    cell.getStyleClass().add("fixed-cell");
                }
            }
        }
        clearHint();
        selectedCell = null;
        lblHints.setText("Ayudas restantes: " + board.getRemainingHints());
    }

    /**
     * Se ejecuta al presionar "Ayuda".
     *
     * @param event evento del botón
     */
    @FXML
    private void handleHelp(ActionEvent event) {
        int[] hint = board.getHint();
        if (hint == null) {
            String reason = board.getRemainingHints() == 0
                    ? "Ya usaste todas las ayudas de esta partida."
                    : "La ayuda no puede llenar la última celda. ¡Esa te toca a ti!";
            alertBox.showInfoBox("Ayuda", "No hay ayuda disponible", reason);
            return;
        }

        clearHint();
        int row = hint[0];
        int col = hint[1];
        int value = hint[2];
        hintCell = cells[row][col];
        hintCell.setPromptText(String.valueOf(value));
        hintCell.getStyleClass().add("hint-cell");

        lblHints.setText("Ayudas restantes: " + board.getRemainingHints());
        showMessage("Sugerencia: prueba el " + value + " en la celda verde", false);
    }

    /**
     * Quita la sugerencia de ayuda que se esté mostrando, si hay una.
     */
    private void clearHint() {
        if (hintCell != null) {
            hintCell.setPromptText("");
            hintCell.getStyleClass().remove("hint-cell");
            hintCell = null;
        }
    }

    /**
     * Se llama cada vez que el jugador escribe o borra un número.
     * Quita la sugerencia si estaba en esa celda, revisa errores y revisa si ganó.
     *
     * @param cell celda que cambió
     */
    private void afterCellChanged(TextField cell) {
        if (cell == hintCell) {
            clearHint();
        }
        boolean hasErrors = highlightErrors();
        if (!hasErrors) {
            showMessage("", false);
            checkVictory();
        }
    }

    /**
     * Pinta con borde rojo las celdas que tienen números repetidos en su fila,
     * columna o bloque, y muestra un mensaje de error. La validación se hace
     * cada vez que cambia una celda (en tiempo real).
     *
     * @return true si hay al menos un error
     */
    private boolean highlightErrors() {
        for (int row = 0; row < ISudokuBoard.SIZE; row++) {
            for (int col = 0; col < ISudokuBoard.SIZE; col++) {
                cells[row][col].getStyleClass().remove("error-cell");
            }
        }

        List<int[]> conflicts = board.getConflictingCells();
        for (int[] position : conflicts) {
            cells[position[0]][position[1]].getStyleClass().add("error-cell");
        }

        if (!conflicts.isEmpty()) {
            showMessage("Hay números repetidos en una fila, columna o bloque", true);
        }
        return !conflicts.isEmpty();
    }

    /**
     * Si el tablero está completo y sin errores, felicita al jugador
     * y bloquea el tablero hasta que inicie otro juego.
     */
    private void checkVictory() {
        if (!board.isSolved()) {
            return;
        }
        boardGrid.setDisable(true);
        btnHelp.setDisable(true);
        showMessage("¡Completaste el Sudoku!", false);
        alertBox.showInfoBox("¡Ganaste!", "¡Felicitaciones!", "Completaste el Sudoku sin errores.");
    }

    /**
     * Muestra un mensaje debajo del título. Los errores se ven en rojo.
     *
     * @param text    mensaje a mostrar
     * @param isError true si es un mensaje de error
     */
    private void showMessage(String text, boolean isError) {
        lblMessage.setText(text);
        lblMessage.getStyleClass().remove("error-message");
        if (isError) {
            lblMessage.getStyleClass().add("error-message");
        }
    }
}
