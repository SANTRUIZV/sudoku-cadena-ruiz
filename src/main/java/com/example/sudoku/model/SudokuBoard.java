package com.example.sudoku.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Tablero del Sudoku 6x6.
 * <p>
 * Guarda el tablero del jugador y la solución completa en listas de filas
 * ({@code ArrayList<ArrayList<Integer>>}). Una matriz de booleanos indica
 * qué celdas vinieron con el tablero y no se pueden editar.
 *
 * @author Santiago Ruiz
 * @version 1.0
 */
public class SudokuBoard implements ISudokuBoard {

    /** Números que ve el jugador. Cada lista interna es una fila. */
    private final ArrayList<ArrayList<Integer>> board;

    /** Solución completa del tablero actual. */
    private final ArrayList<ArrayList<Integer>> solution;

    /** true en las celdas que vinieron con el tablero. */
    private final boolean[][] fixed;

    /** Generador de números aleatorios para armar tableros distintos. */
    private final Random random;

    /** Ayudas que le quedan al jugador en esta partida. */
    private int remainingHints;

    /**
     * Crea un tablero vacío. Para jugar hay que llamar a {@link #generateNewBoard()}.
     */
    public SudokuBoard() {
        board = createEmptyGrid();
        solution = createEmptyGrid();
        fixed = new boolean[SIZE][SIZE];
        random = new Random();
        remainingHints = MAX_HINTS;
    }

    /**
     * Crea una cuadrícula de 6x6 llena de celdas vacías.
     *
     * @return lista de 6 filas con 6 ceros cada una
     */
    private ArrayList<ArrayList<Integer>> createEmptyGrid() {
        ArrayList<ArrayList<Integer>> grid = new ArrayList<>();
        for (int row = 0; row < SIZE; row++) {
            ArrayList<Integer> newRow = new ArrayList<>();
            for (int col = 0; col < SIZE; col++) {
                newRow.add(EMPTY);
            }
            grid.add(newRow);
        }
        return grid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void generateNewBoard() {
        throw new UnsupportedOperationException("Pendiente: generación del tablero");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getValue(int row, int col) {
        return board.get(row).get(col);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(int row, int col, int value) {
        if (value < EMPTY || value > SIZE) {
            throw new IllegalArgumentException("El número debe estar entre 0 y " + SIZE + ": " + value);
        }
        if (fixed[row][col]) {
            return;
        }
        board.get(row).set(col, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFixed(int row, int col) {
        return fixed[row][col];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValidMove(int row, int col, int value) {
        throw new UnsupportedOperationException("Pendiente: validación");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<int[]> getConflictingCells() {
        throw new UnsupportedOperationException("Pendiente: validación");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int[] getHint() {
        throw new UnsupportedOperationException("Pendiente: ayuda");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRemainingHints() {
        return remainingHints;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSolved() {
        throw new UnsupportedOperationException("Pendiente: validación");
    }
}
