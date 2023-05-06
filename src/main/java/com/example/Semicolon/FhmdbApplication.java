package com.example.Semicolon;

import com.example.Semicolon.database.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class FhmdbApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Database.getDatabase();
        HomeController.setWatchlist();
        FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("test.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),  1000, 1000);
        scene.getStylesheets().add(Objects.requireNonNull(FhmdbApplication.class.getResource("styles.css")).toExternalForm());
        stage.setTitle("FHMDb");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
        System.out.println(HomeController.watchlist.size());
    }

    public static void main(String[] args) {
        launch();
    }
}