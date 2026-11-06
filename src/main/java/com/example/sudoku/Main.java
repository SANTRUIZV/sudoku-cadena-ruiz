package com.example.sudoku;

import com.example.sudoku.view.GameStage;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Punto de entrada de la aplicación JavaFX del Sudoku 6x6.
 *
 * @author Santiago Ruiz
 * @author Juan Manuel Cadena
 * @version 1.0
 */
public class Main extends Application {

    /**
     * Abre la ventana principal del juego.
     *
     * @param stage ventana que entrega JavaFX (no se usa, el juego usa {@link GameStage})
     * @throws IOException si no se puede cargar la vista
     */
    @Override
    public void start(Stage stage) throws IOException {
        GameStage.getInstance().show();
    }
}
