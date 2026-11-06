package com.example.sudoku.controller;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Clase adaptadora de {@link ICellKeyListener}.
 * <p>
 * Implementa todos los métodos de la interfaz vacíos, así quien la herede
 * solo sobrescribe los que necesita. Además recibe el {@link KeyEvent} de JavaFX
 * y decide a cuál de los tres métodos llamar según la tecla presionada.
 *
 * @author Juan Manuel Cadena
 * @version 1.0
 */
public abstract class CellKeyAdapter implements ICellKeyListener, EventHandler<KeyEvent> {

    /**
     * Revisa qué tecla se presionó y llama al método correspondiente.
     * Las teclas que no escriben nada (Tab, flechas, Shift...) se dejan pasar.
     *
     * @param event evento de teclado de la celda
     */
    @Override
    public void handle(KeyEvent event) {
        String text = event.getText();

        if (text.length() == 1 && text.charAt(0) >= '1' && text.charAt(0) <= '6') {
            onNumberTyped(text.charAt(0) - '0');
            event.consume();
        } else if (event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.DELETE) {
            onDeletePressed();
            event.consume();
        } else if (!text.isEmpty()) {
            onInvalidKey();
        }
    }

    /**
     * No hace nada por defecto.
     *
     * @param number número escrito
     */
    @Override
    public void onNumberTyped(int number) {
    }

    /**
     * No hace nada por defecto.
     */
    @Override
    public void onDeletePressed() {
    }

    /**
     * No hace nada por defecto.
     */
    @Override
    public void onInvalidKey() {
    }
}
