package com.example.sudoku;

import javafx.application.Application;

/**
 * Clase que lanza la aplicación. Sirve para ejecutar el juego
 * desde IntelliJ sin configurar los módulos de JavaFX a mano.
 *
 * @author Santiago Ruiz
 * @author Juan Manuel Cadena
 * @version 1.0
 */
public class Launcher {

    /**
     * Método principal.
     *
     * @param args argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}
