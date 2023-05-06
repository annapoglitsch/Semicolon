module com.example.demo2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.mail;
    requires okhttp3;
    requires java.sql;
    requires htmlunit;
    requires ormlite.jdbc;


    exports com.example.Semicolon.Back;
    opens com.example.Semicolon.Back to javafx.fxml;
    exports com.example.Semicolon.database;
    opens com.example.Semicolon.database to javafx.fxml;
}