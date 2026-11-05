package com.example.sudoku;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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
     * @param stage ventana que entrega JavaFX
     */
    @Override
    public void start(Stage stage) {
        // Temporal: en el commit J3 se cambia por GameStage.getInstance().show()
        stage.setTitle("Sudoku 6x6");
        stage.setScene(new Scene(new BorderPane(), 600, 650));
        stage.show();
    }
}
