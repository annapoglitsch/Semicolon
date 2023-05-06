package com.example.Semicolon.Back;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class DetailsController implements Initializable {
    @FXML
    public GridPane menu;
    @FXML
    public GridPane HomeGrid;
    @FXML
    public Button menuButton;
    @FXML
    public Label Title, description, Runtime, Rating, directors, writers, cast, genres;
    private boolean menuActive = false;
    public static Movie movie;

    @FXML
    private void activateMenu() { /**make menu slide down/up */
        TranslateTransition tt = new TranslateTransition();
        tt.setNode(menu);
        tt.setDuration(Duration.millis(500));
        if (menuActive) {
            tt.setFromY(menu.getHeight());
            tt.setToY(0);
            menu.setDisable(true);
            menuActive = false;
        } else {
            tt.setFromY(0);
            tt.setToY(menu.getHeight());
            menu.setDisable(false);
            menuActive = true;
        }
        tt.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Title.setText(movie.title + " " + movie.releaseYear);
        description.setText(movie.description);
        description.setWrapText(true);
        Runtime.setText(String.valueOf(movie.length));
        Rating.setText(String.valueOf(movie.rating));
        StringBuilder director = new StringBuilder();
        for (String d : movie.directors){
            director.append(", ").append(d);
        }
        directors.setText(director.substring(2,director.length()));
        StringBuilder writer = new StringBuilder();
        for (String d : movie.writers){
            writer.append(", ").append(d);
        }
        writers.setText(writer.substring(2,writer.length()));
        StringBuilder casts = new StringBuilder();
        for (String d : movie.mainCast){
            casts.append(", ").append(d);
        }
        cast.setText(casts.substring(2,casts.length()));
        StringBuilder genre = new StringBuilder();
        for (String d : movie.genres){
            genre.append(", ").append(d);
        }
        genres.setText(genre.substring(2,genre.length()));
    }
}
