package com.example.Semicolon;

import com.example.Semicolon.Back.*;
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
    Button advancedOptions;
    @FXML
    ChoiceBox<String> genresChoice, sortingChoice;
    @FXML
    TextField searchField;
    @FXML
    ListView movieDisplay;
    private String[] allGenres = new String[]{"---ALL GENRES---", "ACTION", "ADVENTURE", "ANIMATION", "BIOGRAPHY", "COMEDY",
            "CRIME", "DRAMA", "DOCUMENTARY", "FAMILY", "FANTASY", "HISTORY", "HORROR",
            "MUSICAL", "MYSTERY", "ROMANCE", "SCIENCE_FICTION", "SPORT", "THRILLER", "WAR",
            "WESTERN"};

    private Movie movie = new Movie(), emptyMovie = new Movie("Movie-list-is-empty", "zzzzzzzzzzzzzzzzzzzzz", allGenres, 0, "", "No Movies", 0, null, null, null, 0);
    public List<Movie> originalMovieList = movie.initializeMovies("https://prog2.fh-campuswien.ac.at/movies");
    //public List<Movie> originalMovieList = movie.staticMovieList();
    public boolean menuActive = false, sortedByGenre = false, sortedByTitle = false, checkedOthers = false;
    public ObservableList<Movie> movieList = FXCollections.observableArrayList();
    private ObservableList<String> genres = FXCollections.observableList(Arrays.asList(allGenres));
    public ObservableList<String> sortingKeywords = FXCollections.observableList(Arrays.asList("---NO SORTING---", "A-Z", "Z-A"));

    @FXML
    private void activateMenu(ActionEvent event) { //make menu slide down/up
        if(event.getTarget() == advancedOptions || event.getTarget() == genresChoice) {
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
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        movieList.addAll(originalMovieList);
        movieDisplay.setItems(movieList);
        movieDisplay.setCellFactory(movieDisplay -> new MovieCard());
        if(originalMovieList.size() > 1) {
            searchField.setPromptText(originalMovieList.get(new Random().nextInt(originalMovieList.size() - 1)).title);
        }else if(!Objects.equals(originalMovieList.get(0).id, "error")){
            searchField.setPromptText(originalMovieList.get(0).title);
        }else {
            searchField.setPromptText("Search");
        }
        genresChoice.setItems(genres);
        genresChoice.setValue("---ALL GENRES---");
        sortingChoice.setItems(sortingKeywords);
        sortingChoice.setValue("---NO SORTING---");
        sortingChoice.setOnAction(this::sortMoviesByTitlePreparation);  //choiceBox sorting set on action
        genresChoice.setOnAction(this::filterMoviesByGenrePrep);          //choiceBox genre set on action
        searchField.setOnKeyTyped(event -> {
            searchMoviePrep();
        });
    }

    public void sortMoviesByTitlePreparation(ActionEvent event) {   //prep so that choiceBox sorting is not null
        sortMoviesByTitle(event, sortingChoice.getValue());
    }

    public void filterMoviesByGenrePrep(ActionEvent event) {      //prep so that choiceBox genre is not null
        filterMoviesByGenre(event, genresChoice.getValue());
    }

    @FXML
    public void searchMoviePrep() {
        searchMovie(searchField.getText().toLowerCase()); //so that searchField is not null
    }

    public ObservableList<Movie> sortMoviesByTitle(ActionEvent event, String keyWord) {
        if(HomeGrid == null){
            event = new ActionEvent();
        }
        if (keyWord.equals("A-Z")) {
            sortedByTitle = true;
            Collections.sort(movieList, new Comparator<Movie>() {
                @Override
                public int compare(Movie o1, Movie o2) { //compare class compare two movie title
                    return o1.title.compareTo(o2.title);
                }
            });
            return movieList;
        } else if (keyWord.equals("Z-A")) {
            sortedByTitle = true;
            Collections.sort(movieList, new Comparator<Movie>() {
                @Override
                public int compare(Movie o1, Movie o2) {
                    return o2.title.compareTo(o1.title);
                }                                       //same structure as a-z but o1 and o2 changed position
            });
            return movieList;
        } else if (keyWord.equals("---NO SORTING---")) { //if you want to undo sorting mmovieList is originalList
            sortedByTitle = false;
            movieList.clear();
            movieList.addAll(originalMovieList);
            if(!checkedOthers) {
                checkOthers(event);
            }
            checkedOthers = false;
            return movieList;
        }
        return null;

    }

    public ObservableList<Movie> filterMoviesByGenre(ActionEvent event, String genre) {
        if(HomeGrid == null){
            event = new ActionEvent();
        }
        if (!genre.equals("---ALL GENRES---")) {
            sortedByGenre = true;
            movieList.removeIf(m -> !Arrays.asList(m.genres).contains(genre));
        } else {
            sortedByGenre = false;
            movieList.clear();
            movieList.addAll(originalMovieList);
        }
        if(!checkedOthers) {
            checkOthers(event);
        }
        checkedOthers = false;
        return movieList;
    }

    public ObservableList<Movie> searchMovie(String temp) {
        movieList.clear();
        movieList.addAll(originalMovieList);
        if (sortingChoice != null) {
            filterMoviesByGenrePrep(new ActionEvent());
        }
        movieList.removeIf(m -> !m.description.toLowerCase().contains(temp) && !m.title.toLowerCase().contains(temp));
        if(!checkedOthers) {
            checkOthers(new ActionEvent());
        }
        checkedOthers = false;
        return movieList;
    }

    private void checkOthers(ActionEvent event){
        checkedOthers = true;
        if(HomeGrid != null) {
            if(event.getTarget() == genresChoice){
                if(sortedByTitle){
                    sortMoviesByTitlePreparation(event);
                }
                if (searchField.getText() != "") {
                    searchMovie(searchField.getText());
                }
                activateMenu(event);
            }else if(event.getTarget() == sortingChoice){
                if (searchField.getText() != "") {
                    searchMovie(searchField.getText());
                }
                if(sortedByGenre) {
                    filterMoviesByGenrePrep(event);
                }
            }else {
                if(sortedByGenre) {
                    filterMoviesByGenrePrep(event);
                }
                if(sortedByTitle){
                    sortMoviesByTitlePreparation(event);
                }
            }
        }
        if (movieList.size() == 0) {
            movieList.add(emptyMovie);
        }
    }
}

