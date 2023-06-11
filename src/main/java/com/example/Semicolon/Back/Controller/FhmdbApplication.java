package com.example.Semicolon.Back.Controller;

import com.example.Semicolon.Back.Controller.Pattern.Factory;
import com.example.Semicolon.database.UserDatabase;
import com.example.Semicolon.database.UserRepo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import static com.example.Semicolon.Back.Controller.HomeController.allGenres;

public class FhmdbApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Factory factory = new Factory();
        UserDatabase.getUserDatabase();
        UserRepo.setDao();
        FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("Login.fxml"));
        fxmlLoader.setControllerFactory(factory);
        Scene scene = new Scene(fxmlLoader.load(),  1000, 1000);
        scene.getStylesheets().add(Objects.requireNonNull(FhmdbApplication.class.getResource("styles.css")).toExternalForm());
        stage.setTitle("FHMDb");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}