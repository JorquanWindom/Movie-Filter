module com.example.moviefilter {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.moviefilter to javafx.fxml;
    exports com.example.moviefilter;

    opens com.example.moviefilter.models to javafx.fxml;
    exports com.example.moviefilter.models;

    opens com.example.moviefilter.controllers to javafx.fxml;
    exports com.example.moviefilter.controllers;
}