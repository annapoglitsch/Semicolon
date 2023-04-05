module com.example.demo2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.mail;
    requires okhttp3;


    opens com.example.Semicolon to javafx.fxml;
    exports com.example.Semicolon;
    exports com.example.Semicolon.Back;
    opens com.example.Semicolon.Back to javafx.fxml;
}