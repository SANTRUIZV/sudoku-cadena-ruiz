package com.example.sudoku.model;

/**
 * Contrato para mostrar ventanas emergentes (alertas) en el Sudoku.
 * Se usa para confirmar un nuevo juego, avisar errores y anunciar la victoria.
 *
 * @author Juan Manuel Cadena
 * @version 1.0
 */
public interface IAlertBox {

    /**
     * Muestra una alerta de confirmación con los botones Aceptar y Cancelar.
     *
     * @param title   título de la ventana
     * @param header  texto principal de la alerta
     * @param message texto de detalle
     * @return true si el usuario presionó Aceptar
     */
    boolean showConfirmBox(String title, String header, String message);

    /**
     * Muestra una alerta informativa, por ejemplo cuando el jugador gana.
     *
     * @param title   título de la ventana
     * @param header  texto principal de la alerta
     * @param message texto de detalle
     */
    void showInfoBox(String title, String header, String message);

    /**
     * Muestra una alerta de error, por ejemplo cuando un número rompe las reglas.
     *
     * @param title   título de la ventana
     * @param header  texto principal de la alerta
     * @param message texto de detalle
     */
    void showErrorBox(String title, String header, String message);
}
