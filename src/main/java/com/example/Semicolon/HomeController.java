package com.example.Semicolon;

import com.example.Semicolon.Back.Movie;
import com.example.Semicolon.Back.MovieCard;
import javafx.animation.TranslateTransition;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;

public class HomeController implements Initializable {
    @FXML
    GridPane HomeGrid, menu;
    @FXML
    Button Test, searchButton;
    @FXML
    ChoiceBox<String> genresChoice, sortingChoice;
    @FXML
    TextField searchField;
    @FXML
    ListView movieDisplay;

    private Movie movie = new Movie(), emptyMovieList = new Movie("Movie-list-is-empty","zzzzzzzzzzzzzzzzzzzzz", null, 0, "",  "No Movies", null, null, null, null,0);
    public List<Movie> originalMovieList = movie.initializeMovies("https://prog2.fh-campuswien.ac.at/movies"), tempSortedMovieList = new ArrayList<>();
    public boolean menuActive = false, started = false, sortedByGenre = false, sortedByTitle = false;
    public ObservableList<Movie> movieList = FXCollections.observableArrayList();
    private ObservableList<String> genres = FXCollections.observableList(Arrays.asList("---ALL GENRES---", "ACTION", "ADVENTURE", "ANIMATION", "BIOGRAPHY", "COMEDY",
            "CRIME", "DRAMA", "DOCUMENTARY", "FAMILY", "FANTASY", "HISTORY", "HORROR",
            "MUSICAL", "MYSTERY", "ROMANCE", "SCIENCE_FICTION", "SPORT", "THRILLER", "WAR",
            "WESTERN"));
    public ObservableList<String> sortingKeywords = FXCollections.observableList(Arrays.asList("---NO SORTING---", "A-Z", "Z-A"));


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
        }  else {
            tt.setFromY(0);
            tt.setToY(menu.getHeight());
            menu.setDisable(false);
            menuActive = true;
        }
        tt.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            tempSortedMovieList.addAll(originalMovieList);
            movieList.addAll(originalMovieList);
            movieDisplay.setItems(movieList);
            movieDisplay.setCellFactory(movieDisplay -> new MovieCard());
        genresChoice.setItems(genres);
        genresChoice.setValue("---ALL GENRES---");
        sortingChoice.setItems(sortingKeywords);
        sortingChoice.setValue("---NO SORTING---");
        sortingChoice.setOnAction(this::sortMoviesByTitlePreparation);  //choiceBox sorting set on action
        genresChoice.setOnAction(this::sortMoviesByGenrePrep);          //choiceBox ggenre set on action
        searchField.setOnKeyTyped(event -> {
            searchMoviePrep();});
    }

    public void sortMoviesByTitlePreparation(ActionEvent event) {   //prep so that choiceBox sorting is not null
        sortMoviesByTitle(event, sortingChoice.getValue());
    }

    public void sortMoviesByGenrePrep(ActionEvent event) {      //prep so that choiceBox genre is not null
        sortMoviesByGenre(event, genresChoice.getValue());
        searchField.setText("");
    }
    public ObservableList<Movie> sortMoviesByTitle(ActionEvent event, String keyWord){
        tempSortedMovieList.clear();
        if (keyWord.equals("A-Z")) {
            sortedByTitle = true;
            Collections.sort(movieList, new Comparator<Movie>() {
                @Override
                public int compare(Movie o1, Movie o2) { //compare class compare two movie title
                    return o1.title.compareTo(o2.title);
                }
            });
            tempSortedMovieList.addAll(originalMovieList); //after sorting add movieList to temp
            return movieList;
        } else if (keyWord.equals("Z-A")) {
            sortedByTitle = true;
            Collections.sort(movieList, new Comparator<Movie>() {
                @Override
                public int compare(Movie o1, Movie o2) {
                    return o2.title.compareTo(o1.title);
                }                                       //same structure as a-z but o1 and o2 changed position
            });
            tempSortedMovieList.addAll(originalMovieList);
            return movieList;
        } else if (keyWord.equals("---NO SORTING---")) { //if you want to undo sorting mmovieList is originalList
            sortedByTitle = false;
            if (!sortedByGenre) {
                movieList.clear();
                movieList.addAll(originalMovieList);
            }
            tempSortedMovieList.addAll(originalMovieList);
            movieList.clear();
            movieList.addAll(originalMovieList);
            sortMoviesByGenrePrep(event);
            return movieList;
        }
        if(movieList.size() == 0){
            movieList.add(emptyMovieList);
        }
        return null;

    }
    public ObservableList<Movie> sortMoviesByGenre(ActionEvent event, String genre){
        ObservableList<Movie> tempList = FXCollections.observableArrayList();
        movieList.clear();
        movieList.addAll(tempSortedMovieList);      //if other filter is in action
        if (!genre.equals("---ALL GENRES---")) {
            sortedByGenre = true;
            for (int j = 0; j < movieList.size(); j++) {
                for (int i = 0; i < movieList.get(j).genres.length; i++) {  //get genre and add into list
                    if (genre.equals(movieList.get(j).genres[i])) {
                        tempList.add(movieList.get(j));
                        break;
                    }
                }
            }
            movieList.clear();
            movieList.addAll(tempList);
        } else {
            sortedByGenre = false;
            movieList.clear();
            movieList.addAll(originalMovieList);
        }
        if(sortedByTitle){
            sortMoviesByTitlePreparation(event);
        }
        if(movieList.size() == 0){
            movieList.add(emptyMovieList);
        }
        tempSortedMovieList.clear();
        tempSortedMovieList.addAll(movieList);
        return movieList;
    }
    @FXML
    public void searchMoviePrep() {
        searchMovie(searchField.getText().toLowerCase()); //so that searchField is not null
    }
    public ObservableList<Movie> searchMovie(String temp){
        movieList.clear();
        for (int i = 0; i < tempSortedMovieList.size(); i++) {
            if (tempSortedMovieList.get(i).description.toLowerCase().contains(temp) ||
                    tempSortedMovieList.get(i).title.toLowerCase().contains(temp)) {
                movieList.add(tempSortedMovieList.get(i));
            }
            //compare description with text in search field
        }
        if(movieList.size() == 0){      //if there is no suitable movie, no movie should be displayed
            movieList.add(emptyMovieList);
        }
        return movieList;
    }
}

