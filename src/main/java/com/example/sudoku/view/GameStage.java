package com.example.sudoku.view;

import com.example.sudoku.controller.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Ventana principal del juego. Carga game-view.fxml y solo existe
 * una instancia (patrón Holder, igual que en el Craps).
 *
 * @author Juan Manuel Cadena
 * @version 1.0
 */
public class GameStage extends Stage {

    /** Controlador asociado a la vista. */
    private final GameController controller;

    /**
     * Crea la ventana del juego.
     *
     * @throws IOException si no se puede cargar el archivo FXML
     */
    public GameStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/com/example/sudoku/game-view.fxml")
        );
        Parent root = fxmlLoader.load();
        controller = fxmlLoader.getController();

        setTitle("Sudoku 6x6");
        setScene(new Scene(root));
        setResizable(false);
    }

    /**
     * Devuelve el controlador de la vista.
     *
     * @return controlador del juego
     */
    public GameController getController() {
        return controller;
    }

    /**
     * Devuelve la única instancia de la ventana, y la crea si no existe.
     *
     * @return la ventana del juego
     * @throws IOException si no se puede cargar el archivo FXML
     */
    public static GameStage getInstance() throws IOException {
        if (GameStageHolder.INSTANCE == null) {
            GameStageHolder.INSTANCE = new GameStage();
        }
        return GameStageHolder.INSTANCE;
    }

    /**
     * Guarda la única instancia de {@link GameStage}.
     */
    private static class GameStageHolder {
        private static GameStage INSTANCE = null;
    }
}
