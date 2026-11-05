package com.example.sudoku.model;

import java.util.ArrayList;
import java.util.Collections;
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

    /** Cantidad de números que aparecen en cada bloque al iniciar el juego. */
    private static final int CELLS_PER_BLOCK = 2;

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
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                board.get(row).set(col, EMPTY);
                solution.get(row).set(col, EMPTY);
                fixed[row][col] = false;
            }
        }
        fillSolution(0);

        for (int startRow = 0; startRow < SIZE; startRow += BLOCK_ROWS) {
            for (int startCol = 0; startCol < SIZE; startCol += BLOCK_COLS) {
                revealCellsInBlock(startRow, startCol);
            }
        }
        remainingHints = MAX_HINTS;
    }

    /**
     * Llena la solución con backtracking, celda por celda, de izquierda a derecha
     * y de arriba hacia abajo. En cada celda prueba los números del 1 al 6 en orden
     * aleatorio; si ninguno sirve, devuelve false para que la celda anterior pruebe
     * con otro número.
     *
     * @param position número de celda, de 0 a 35 (fila = position / 6, columna = position % 6)
     * @return true si logró llenar desde esta celda hasta el final
     */
    private boolean fillSolution(int position) {
        if (position == SIZE * SIZE) {
            return true;
        }
        int row = position / SIZE;
        int col = position % SIZE;

        List<Integer> numbers = new ArrayList<>();
        for (int number = 1; number <= SIZE; number++) {
            numbers.add(number);
        }
        Collections.shuffle(numbers, random);

        for (int number : numbers) {
            if (canPlace(solution, row, col, number)) {
                solution.get(row).set(col, number);
                if (fillSolution(position + 1)) {
                    return true;
                }
                solution.get(row).set(col, EMPTY);
            }
        }
        return false;
    }

    /**
     * Elige al azar {@link #CELLS_PER_BLOCK} celdas de un bloque de 2x3, copia en
     * ellas el número de la solución y las marca como fijas.
     *
     * @param startRow primera fila del bloque
     * @param startCol primera columna del bloque
     */
    private void revealCellsInBlock(int startRow, int startCol) {
        List<int[]> cells = new ArrayList<>();
        for (int row = startRow; row < startRow + BLOCK_ROWS; row++) {
            for (int col = startCol; col < startCol + BLOCK_COLS; col++) {
                cells.add(new int[]{row, col});
            }
        }
        Collections.shuffle(cells, random);

        for (int i = 0; i < CELLS_PER_BLOCK; i++) {
            int row = cells.get(i)[0];
            int col = cells.get(i)[1];
            board.get(row).set(col, solution.get(row).get(col));
            fixed[row][col] = true;
        }
    }

    /**
     * Revisa si un número se puede poner en una celda de la cuadrícula dada
     * sin repetirse en su fila, su columna ni su bloque de 2x3.
     * La celda misma no se compara consigo.
     *
     * @param grid  cuadrícula a revisar (el tablero o la solución)
     * @param row   fila, de 0 a 5
     * @param col   columna, de 0 a 5
     * @param value número de 1 a 6
     * @return true si el número no se repite
     */
    private boolean canPlace(ArrayList<ArrayList<Integer>> grid, int row, int col, int value) {
        for (int i = 0; i < SIZE; i++) {
            if (i != col && grid.get(row).get(i) == value) {
                return false;
            }
            if (i != row && grid.get(i).get(col) == value) {
                return false;
            }
        }

        int startRow = (row / BLOCK_ROWS) * BLOCK_ROWS;
        int startCol = (col / BLOCK_COLS) * BLOCK_COLS;
        for (int r = startRow; r < startRow + BLOCK_ROWS; r++) {
            for (int c = startCol; c < startCol + BLOCK_COLS; c++) {
                boolean sameCell = (r == row && c == col);
                if (!sameCell && grid.get(r).get(c) == value) {
                    return false;
                }
            }
        }
        return true;
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
