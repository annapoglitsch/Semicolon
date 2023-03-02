package com.example.Semicolon;

import com.example.Semicolon.Back.Movie;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    GridPane HomeGrid, menu;
    @FXML
    Button Test;
    @FXML
    ListView movieDisplay;

    private boolean menuActive = false;
    private List<Movie> movieList;

    @FXML
    private void activateMenu(){
        TranslateTransition tt = new TranslateTransition();
        tt.setNode(menu);
        tt.setDuration(Duration.millis(500));
        if(menuActive){
            tt.setFromY(menu.getHeight());
            tt.setToY(0);
            menu.setDisable(true);
            menuActive = false;
        }else {
            tt.setFromY(0);
            tt.setToY(menu.getHeight());
            menu.setDisable(false);
            menuActive = true;
        }
        tt.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Movie m = new Movie();
        try {
            movieList = m.initializeMovies();
            for(int count = 0; count < movieList.size(); count++){

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void generateMovieCard(){

    }
}