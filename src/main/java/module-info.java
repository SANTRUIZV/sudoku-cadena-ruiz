module com.example.sudoku {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.sudoku to javafx.fxml;
    exports com.example.sudoku;

    // Agregar cada línea cuando el paquete tenga su primera clase
    // (Java no deja exportar un paquete vacío):
    exports com.example.sudoku.model;
    exports com.example.sudoku.view;
    opens com.example.sudoku.controller to javafx.fxml;
    exports com.example.sudoku.controller;
}
