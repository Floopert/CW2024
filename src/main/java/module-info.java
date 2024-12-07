module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;


    opens com.example.demo.controller.fxmlPageControllers to javafx.fxml;
    exports com.example.demo;
    
}