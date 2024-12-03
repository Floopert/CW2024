module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    //might need this if using JavaFX properties
    //requires javafx.base;

    opens com.example.demo.controller.fxmlPageControllers to javafx.fxml;
    exports com.example.demo;
    
}