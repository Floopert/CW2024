module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    //might need this if using JavaFX properties
    //requires javafx.base;

    //only need to include new packages if that package uses fxml controllers (fxml references objects in the package)
    opens com.example.demo to javafx.fxml;
    exports com.example.demo.controller;
}