module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    //might need this if using JavaFX properties
    //requires javafx.base;

    /*
    //only need to include new packages if that package uses fxml controllers (fxml references objects in the package)
    opens com.example.demo.activeActors to javafx.fxml;
    opens com.example.demo.controller to javafx.fxml;
    opens com.example.demo.handlers to javafx.fxml;
    opens com.example.demo.images to javafx.fxml;
    opens com.example.demo.levels to javafx.fxml;
    opens com.example.demo.levelViews to javafx.fxml;
    opens com.example.demo.planes to javafx.fxml;
    opens com.example.demo.projectiles to javafx.fxml;
    */

    exports com.example.demo.activeActors;
    exports com.example.demo.controller;
    exports com.example.demo.eventListeners;
    exports com.example.demo.handlers;
    exports com.example.demo.images;
    exports com.example.demo.levels;
    exports com.example.demo.levelViews;
    exports com.example.demo.planes;
    exports com.example.demo.projectiles;

}