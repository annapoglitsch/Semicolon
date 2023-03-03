package com.example.Semicolon;

import com.example.Semicolon.Back.Movie;
import com.example.Semicolon.Back.MovieCard;
import javafx.animation.TranslateTransition;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class HomeController implements Initializable {
    @FXML
    GridPane HomeGrid, menu;
    @FXML
    Button Test, searchButton;
    @FXML
    ChoiceBox genresChoice, sortingChoice;
    @FXML
    TextField searchField;
    @FXML
    ListView movieDisplay;

    private boolean menuActive = false;
    private ObservableList<Movie> movieList = FXCollections.observableArrayList();
    private ObservableList<String> genres = FXCollections.observableList(Arrays.asList("---All GENRES---", "ACTION", "ADVENTURE", "ANIMATION", "BIOGRAPHY", "COMEDY",
            "CRIME", "DRAMA", "DOCUMENTARY", "FAMILY", "FANTASY", "HISTORY", "HORROR",
            "MUSICAL", "MYSTERY", "ROMANCE", "SCIENCE_FICTION", "SPORT", "THRILLER", "WAR",
            "WESTERN"));
    private ObservableList<String> sortingKeywords = FXCollections.observableList(Arrays.asList("---NO SORTING---", "A-Z", "Z-A"));

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
        genresChoice.setItems(genres);
        genresChoice.setValue("---All GENRES---");
        sortingChoice.setItems(sortingKeywords);
        sortingChoice.setValue("---NO SORTING---");
        Movie m = new Movie();
        try {
            movieList.addAll(m.initializeMovies());
            movieDisplay.setItems(movieList);
            movieDisplay.setCellFactory(movieDisplay -> new MovieCard());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}