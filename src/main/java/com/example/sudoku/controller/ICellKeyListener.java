package com.example.sudoku.controller;

/**
 * Acciones que puede hacer el jugador con el teclado sobre una celda.
 *
 * @author Juan Manuel Cadena
 * @version 1.0
 */
public interface ICellKeyListener {

    /**
     * Se llama cuando el jugador escribe un número del 1 al 6.
     *
     * @param number número escrito
     */
    void onNumberTyped(int number);

    /**
     * Se llama cuando el jugador presiona Backspace o Delete.
     */
    void onDeletePressed();

    /**
     * Se llama cuando el jugador escribe cualquier otro carácter (letras, 0, 7, 8, 9...).
     */
    void onInvalidKey();
}
