package com.example.sudoku.model;

import java.util.List;

/**
 * Contrato del modelo del Sudoku 6x6.
 * <p>
 * Define las operaciones que el controlador puede pedirle al tablero.
 * Las filas y columnas van de 0 a 5 y una celda vacía se representa con 0.
 * Los bloques son de 2 filas por 3 columnas, así que el tablero tiene 6 bloques.
 *
 * @author Santiago Ruiz
 * @author Juan Manuel Cadena
 * @version 1.0
 */
public interface ISudokuBoard {

    /** Cantidad de filas y columnas del tablero. */
    int SIZE = 6;

    /** Cantidad de filas de cada bloque. */
    int BLOCK_ROWS = 2;

    /** Cantidad de columnas de cada bloque. */
    int BLOCK_COLS = 3;

    /** Valor que representa una celda vacía. */
    int EMPTY = 0;

    /** Cantidad de ayudas disponibles en cada partida. */
    int MAX_HINTS = 3;

    /**
     * Genera un tablero nuevo y reinicia el contador de ayudas.
     * Cada bloque de 2x3 queda con 2 números visibles, sin repetidos
     * en filas, columnas ni bloques. Esas celdas quedan fijas.
     */
    void generateNewBoard();

    /**
     * Devuelve el número que hay en una celda.
     *
     * @param row fila, de 0 a 5
     * @param col columna, de 0 a 5
     * @return el número de la celda (1 a 6), o {@link #EMPTY} si está vacía
     */
    int getValue(int row, int col);

    /**
     * Escribe un número en una celda o la borra.
     * Si la celda es fija, no hace nada.
     *
     * @param row   fila, de 0 a 5
     * @param col   columna, de 0 a 5
     * @param value número de 1 a 6, o {@link #EMPTY} para borrar
     * @throws IllegalArgumentException si value no está entre 0 y 6
     */
    void setValue(int row, int col, int value);

    /**
     * Indica si una celda vino con el tablero y por eso no se puede editar.
     *
     * @param row fila, de 0 a 5
     * @param col columna, de 0 a 5
     * @return true si la celda es fija
     */
    boolean isFixed(int row, int col);

    /**
     * Revisa si un número se puede poner en una celda sin romper las reglas.
     * No modifica el tablero.
     *
     * @param row   fila, de 0 a 5
     * @param col   columna, de 0 a 5
     * @param value número de 1 a 6
     * @return true si el número no se repite en la fila, la columna ni el bloque
     */
    boolean isValidMove(int row, int col, int value);

    /**
     * Busca todas las celdas que tienen un número repetido en su fila,
     * columna o bloque. El controlador las usa para pintarlas de rojo.
     *
     * @return lista de posiciones {fila, columna}; vacía si no hay errores
     */
    List<int[]> getConflictingCells();

    /**
     * Sugiere el número correcto para una celda vacía elegida al azar.
     * Gasta una ayuda. No escribe el número en el tablero.
     * No da ayuda si no quedan ayudas o si solo queda una celda vacía,
     * para que la ayuda nunca termine el tablero.
     *
     * @return arreglo {fila, columna, valor}, o null si no se puede dar ayuda
     */
    int[] getHint();

    /**
     * Devuelve cuántas ayudas le quedan al jugador en esta partida.
     *
     * @return ayudas restantes, de 0 a {@link #MAX_HINTS}
     */
    int getRemainingHints();

    /**
     * Indica si el jugador ganó.
     *
     * @return true si el tablero está lleno y no tiene errores
     */
    boolean isSolved();
}
