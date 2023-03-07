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
    private List<Movie> originalMovieList = movie.initializeMovies(), tempSortedMovieList = new ArrayList<>();
    private boolean menuActive = false, started = false, sortedByGenre = false;
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
        if (!started) {
            movieList.addAll(originalMovieList);
            tempSortedMovieList.addAll(originalMovieList);
            movieDisplay.setItems(movieList);
            movieDisplay.setCellFactory(movieDisplay -> new MovieCard());
            started = true;
        }
        genresChoice.setItems(genres);
        genresChoice.setValue("---All GENRES---");
        sortingChoice.setItems(sortingKeywords);
        sortingChoice.setValue("---NO SORTING---");
        sortingChoice.setOnAction(this::sortMoviesByTitle);
        genresChoice.setOnAction(this::sortMoviesByGenre);
    }

    private ObservableList<Movie> sortMoviesByTitle(Event event) {
        tempSortedMovieList.clear();
        if (sortingChoice.getValue().equals("A-Z")) {
            Collections.sort(movieList, new Comparator<Movie>() {
                @Override
                public int compare(Movie o1, Movie o2) {
                    return o1.title.compareTo(o2.title);
                }
            });
            tempSortedMovieList.addAll(originalMovieList);
            System.out.println(tempSortedMovieList.size());
            return movieList;
        } else if (sortingChoice.getValue().equals("Z-A")) {
            Collections.sort(movieList, new Comparator<Movie>() {
                @Override
                public int compare(Movie o1, Movie o2) {
                    return o2.title.compareTo(o1.title);
                }
            });
            tempSortedMovieList.addAll(originalMovieList);
            System.out.println(tempSortedMovieList.size());
            return movieList;
        } else if (sortingChoice.getValue().equals("---NO SORTING---")) {
            if (!sortedByGenre) {
                movieList.clear();
                movieList.addAll(originalMovieList);
            }
            tempSortedMovieList.addAll(originalMovieList);
            System.out.println(tempSortedMovieList.size());
            return movieList;
        }
        return null;
    }

    private ObservableList<Movie> sortMoviesByGenre(Event event) {
        ObservableList<Movie> tempList = FXCollections.observableArrayList();
        movieList.clear();
        movieList.addAll(tempSortedMovieList);
        if (!genresChoice.getValue().equals("---All GENRES---")) {
            sortedByGenre = true;
            for (int j = 0; j < movieList.size(); j++) {
                for (int i = 0; i < movieList.get(j).genres.length; i++) {
                    if (genresChoice.getValue().equals(movieList.get(j).genres[i])) {
                        tempList.add(movieList.get(j));
                        break;
                    }
                }
            }
            movieList.clear();
            movieList.addAll(tempList);
        } else {
            sortedByGenre = false;
        }
        return movieList;
    }

    @FXML
    private ObservableList<Movie> searchMovie(ActionEvent event) {
        movieList.clear();
        for (int i = 0; i < originalMovieList.size(); i++) {
            if (originalMovieList.get(i).description.contains(searchField.getText()) ||
                    originalMovieList.get(i).title.contains(searchField.getText())) {
                movieList.add(originalMovieList.get(i));
            }
        }
        return movieList;
    }
}
