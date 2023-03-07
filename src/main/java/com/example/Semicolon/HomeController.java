package com.example.Semicolon;

import com.example.Semicolon.Back.Movie;
import com.example.Semicolon.Back.MovieCard;
import javafx.animation.TranslateTransition;
import javafx.collections.*;
import javafx.event.ActionEvent;
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

    private Movie movie = new Movie();
    private List<Movie> originalMovieList = movie.initializeMovies();
    private boolean menuActive = false;
    private ObservableList<Movie> movieList = FXCollections.observableArrayList();
    private List<Movie> tempList;
    private ObservableList<String> genres = FXCollections.observableList(Arrays.asList("---All GENRES---", "ACTION", "ADVENTURE", "ANIMATION", "BIOGRAPHY", "COMEDY",
            "CRIME", "DRAMA", "DOCUMENTARY", "FAMILY", "FANTASY", "HISTORY", "HORROR",
            "MUSICAL", "MYSTERY", "ROMANCE", "SCIENCE_FICTION", "SPORT", "THRILLER", "WAR",
            "WESTERN"));
    private ObservableList<String> sortingKeywords = FXCollections.observableList(Arrays.asList("---NO SORTING---", "A-Z", "Z-A"));
    private String searchWord = "";

    @FXML
    private void activateMenu() {
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
        genresChoice.setItems(genres);
        genresChoice.setValue("---All GENRES---");
        sortingChoice.setItems(sortingKeywords);
        sortingChoice.setValue("---NO SORTING---");
        if (originalMovieList != null) {
            movieList.addAll(originalMovieList);
            movieDisplay.setItems(movieList);
            movieDisplay.setCellFactory(movieDisplay -> new MovieCard());
        }
        sortingChoice.setOnAction(this::sortMoviesByTitle);
        genresChoice.setOnAction(this::sortMovieGenres);
        searchField.setOnKeyPressed(event -> {
            searchMovie();
        });
    }

    private ObservableList<Movie> sortMoviesByTitle(Event event) {
        if (sortingChoice.getValue().equals("A-Z")) {
            Collections.sort(movieList, new Comparator<Movie>() {
                @Override
                public int compare(Movie o1, Movie o2) {
                    return o1.title.compareTo(o2.title);
                }
            });
        } else if (sortingChoice.getValue().equals("Z-A")) {
            Collections.sort(movieList, new Comparator<Movie>() {
                @Override
                public int compare(Movie o1, Movie o2) {
                    return o2.title.compareTo(o1.title);
                }
            });

        } else if (sortingChoice.getValue().equals("---NO SORTING---")) {
            movieList.clear();
            movieList.addAll(originalMovieList);
            return movieList;
        }
        return null;
    }

    private ObservableList<Movie> sortMovieGenres(ActionEvent event) {
        movieList.removeAll();
        for (Movie movie : originalMovieList) {

        }
        return movieList;
    }

    @FXML
    private ObservableList<Movie> searchMovie() {
        movieList.clear();
        for (int i = 0; i < originalMovieList.size(); i++) {
            if (originalMovieList.get(i).description.toLowerCase().contains(searchField.getText()) ||
                    originalMovieList.get(i).title.toLowerCase().contains(searchField.getText())) {
                movieList.add(originalMovieList.get(i));
            }
        }
        return movieList;
    }
}
