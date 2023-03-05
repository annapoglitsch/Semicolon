package com.example.Semicolon;

import com.example.Semicolon.Back.Movie;
import com.example.Semicolon.Back.MovieCard;
import javafx.animation.TranslateTransition;
import javafx.collections.*;
import javafx.event.Event;
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
    ChoiceBox<String> genresChoice;
    @FXML
    ChoiceBox<String> sortingChoice;
    @FXML
    TextField searchField;
    @FXML
    ListView movieDisplay;

    private boolean menuActive = false;
    private ObservableList<Movie> originalMovieList;
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
            originalMovieList.addAll(m.initializeMovies());
            movieDisplay.setItems(movieList);
            movieDisplay.setCellFactory(movieDisplay -> new MovieCard());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        sortingChoice.setOnAction(this::sortMoviesByTitle);
    }

    private ObservableList<Movie> sortMoviesByTitle(Event event) {
        if (sortingChoice.getValue().equals("A-Z")) {
        Collections.sort(movieList, new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2) {
                return o1.title.compareTo(o2.title);
            }
        });
        }
        else if (sortingChoice.getValue().equals("Z-A")) {
            Collections.sort(movieList, new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2) {
                return o2.title.compareTo(o1.title);
            }
        });

        }
        else if(sortingChoice.getValue().equals("---NO SORTING---")) {
            return movieList;
        }
        return null;
    }
}