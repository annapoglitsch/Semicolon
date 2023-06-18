module com.example.demo2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.sql;
    requires ormlite.jdbc;
    requires javafx.media;


    exports com.example.Semicolon.Back;
    opens com.example.Semicolon.Back to javafx.fxml;
    exports com.example.Semicolon.database;
    opens com.example.Semicolon.database to javafx.fxml;
    opens com.example.Semicolon.Back.Controller to javafx.fxml;
    exports com.example.Semicolon.Back.Controller;
}