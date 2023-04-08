package com.example.Semicolon;

import com.example.Semicolon.Back.*;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

                                         /** Variables */
public class HomeController implements Initializable {
    @FXML
    GridPane HomeGrid, menu;
    @FXML
    Button advancedOptions;
    @FXML
    ChoiceBox<String> genresChoice, sortingChoice, releaseYearChoice;
    @FXML
    TextField searchField;
    @FXML
    ListView movieDisplay;
    @FXML
    Slider ratingSlider;
    @FXML
    Label ratingLabel;
    private String[] allGenres = new String[]{"---ALL GENRES---", "ACTION", "ADVENTURE", "ANIMATION", "BIOGRAPHY", "COMEDY",
            "CRIME", "DRAMA", "DOCUMENTARY", "FAMILY", "FANTASY", "HISTORY", "HORROR",
            "MUSICAL", "MYSTERY", "ROMANCE", "SCIENCE_FICTION", "SPORT", "THRILLER", "WAR",
            "WESTERN"};
    private ObservableList<String> allYears = FXCollections.observableArrayList();
    public boolean menuActive = false, sortedByGenre = false, sortedByTitle = false, checkedOthers = false;
    private String URL, query = "", genre = "", title = "", rating = "", releaseYear = "";
    //                                  ******Lists******
    private Movie movie = new Movie(), emptyMovie = new Movie("Movie-list-is-empty", "zzzzzzzzzzzzzzzzzzzzz", allGenres, 0, "", "No Movies", 0, null, null, null, 0);
    private MovieAPI api = new MovieAPI();
    public List<Movie> originalMovieList = api.initializeMoviesNew("https://prog2.fh-campuswien.ac.at/movies");
    //public List<Movie> originalMovieList = movie.staticMovieList();
    public ObservableList<Movie> movieList = FXCollections.observableArrayList();
    private ObservableList<String> genres = FXCollections.observableList(Arrays.asList(allGenres));
    public ObservableList<String> sortingKeywords = FXCollections.observableList(Arrays.asList("---NO SORTING---", "A-Z", "Z-A"));

                                             /** Methods */
    @FXML
    private void activateMenu(ActionEvent event) { //make menu slide down/up
        if (event.getTarget() == advancedOptions || event.getTarget() == genresChoice) {
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
        if (originalMovieList.size() > 1) {
            searchField.setPromptText(originalMovieList.get(new Random().nextInt(originalMovieList.size() - 1)).title);
        } else if (!Objects.equals(originalMovieList.get(0).id, "error")) {
            searchField.setPromptText(originalMovieList.get(0).title);
        } else {
            searchField.setPromptText("Search");
        }
        genresChoice.setItems(genres);
        genresChoice.setValue("---ALL GENRES---");
        sortingChoice.setItems(sortingKeywords);
        sortingChoice.setValue("---NO SORTING---");
        setYearChoice();
        releaseYearChoice.setItems(allYears);
        releaseYearChoice.setValue("---All YEARS---");
        sortingChoice.setOnAction(this::sortMoviesByTitlePreparation);  //choiceBox sorting set on action
        genresChoice.setOnAction(this::filterMoviesByGenrePrep);          //choiceBox genre set on action
        searchField.setOnKeyTyped(event -> {
            searchMoviePrep();
        });
        releaseYearChoice.setOnAction(this::filterByReleaseYearPrep);
        ratingSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                filterByRatingFrom((int) ratingSlider.getValue());
                setYearChoice();
                if(movieList.size() == 0){
                    movieList.add(emptyMovie);
                }
            }
        });
    }

    public void sortMoviesByTitlePreparation(ActionEvent event) {   //prep so that choiceBox sorting is not null
        sortMoviesByTitle(event, sortingChoice.getValue());
    }

    public void filterMoviesByGenrePrep(ActionEvent event) {      //prep so that choiceBox genre is not null
        filterMoviesByGenre(event, genresChoice.getValue());
        setYearChoice();
        if(movieList.size() == 0){
            movieList.add(emptyMovie);
        }
    }
    public void filterByReleaseYearPrep(ActionEvent event){
        filterByReleaseYear(event, releaseYearChoice.getValue());
    }

    @FXML
    public void searchMoviePrep() {
        searchMovies(searchField.getText().toLowerCase()); //so that searchField is not null
        setYearChoice();
        if(movieList.size() == 0){
            movieList.add(emptyMovie);
        }
    }

    public void changeURL(String addon, String source) {
        addon = addon.replaceAll(" ", "%20");
        if (addon.equals("RESET")) {
            addon = "";
        }
        switch (source) {
            case "genre":
                genre = addon;
                break;
            case "title":
                title = addon;
                break;
            case "rating":
                rating = addon;
                break;
            case "releaseYear":
                releaseYear = addon;
                break;
            case "query":
                query = addon;
                break;
        }
        URL = "https://prog2.fh-campuswien.ac.at/movies?query=" + query + "&genre=" + genre + "&title=" + title + "&ratingFrom=" + rating + "&releaseYear=" + releaseYear;
        setMovieList();
    }

    private void setMovieList() {
        movieList.clear();
        movieList.addAll(api.initializeMoviesNew(URL));
        if (sortedByTitle) {
            sortMoviesByTitle(new ActionEvent(), sortingChoice.getValue());
        }
    }

    public void filterMoviesByGenre(ActionEvent event, String keyWord) {
        if (Objects.equals(keyWord, "---ALL GENRES---")) {
            keyWord = "RESET";
        }
        changeURL(keyWord, "genre");
    }

    public void filterByRatingFrom(int value) {
        ratingLabel.setText(String.valueOf(value / 10.));
        String keyWord;
        if (value / 10. >= 10) {
            keyWord = "RESET";
        } else {
            keyWord = String.valueOf(value / 10.);
        }
        changeURL(keyWord, "rating");
    }

    public void filterByReleaseYear(ActionEvent event, String keyWord) {
        if (Objects.equals(keyWord, "---All Years---")) {
            keyWord = "RESET";
        }
        changeURL(keyWord, "releaseYear");
    }

    public void searchMovies(String keyWord) {
        if (keyWord == null) {
            keyWord = "RESET";
        }
        changeURL(keyWord, "query");
    }


    public ObservableList<Movie> sortMoviesByTitle(ActionEvent event, String keyWord) {
        if (HomeGrid == null) {
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
        } else if (keyWord.equals("---NO SORTING---")) { //if you want to undo sorting movieList is originalList
            sortedByTitle = false;
            setMovieList();
            return movieList;
        }
        return null;

    }

    public void setYearChoice() {
        System.out.println(movieList);
        if(movieList.size() != 0) {
            System.out.println("test");
            double endingYear, startingYear;
            endingYear = movieList.stream().max(Comparator.comparing(Movie::getReleaseYear)).orElseThrow(NoSuchElementException::new).releaseYear;
            startingYear = movieList.stream().min(Comparator.comparing(Movie::getReleaseYear)).orElseThrow(NoSuchElementException::new).releaseYear;
            allYears.clear();
            allYears.add("---All Years---");
            for (double currentYear = startingYear; currentYear < endingYear; currentYear++) {
                allYears.add(1, String.valueOf((int) currentYear));
            }
            releaseYearChoice.setItems(allYears);
        }
    }
                                        /** Stream Methods */
    String getMostPopularActor(List<Movie> movies){
    List<String> newList = new ArrayList<>();

         movies.stream().map(m -> newList.addAll(Arrays.asList(m.mainCast))).collect(Collectors.toList());
        // newList.stream().max(String).stream().count();
       Map<String, Long> test = newList.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        Map.Entry<String, Long> maxEntry = Collections.max(test.entrySet(), new Comparator<Map.Entry<String, Long>>() {
            @Override
            public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
       return maxEntry.getKey();
       // return "Toni";
        }
   int getLongestMovieTitle(List<Movie> movies) {

      List<String> newMovieTitleList = movies.stream().map(m -> m.title).collect(Collectors.toList());

      return newMovieTitleList.stream().mapToInt(String::length).max().orElse(0); //or Else damit es kein OptionalInt ist

   }


    /**
     * Gibt die Anzahl der Filme eines bestimmten Regisseurs zurück.
     *
     * @param movies    Liste von Filmen
     * @param director  Regisseur, dessen Filme gezählt werden sollen
     * @return Anzahl der Filme des Regisseurs
     */
    public long countMoviesFrom(List<Movie> movies, String director) {
        return movies.stream()
                .filter(movie -> movie.getDirector().equals(director))
                .count();
    }

    /**
     * Gibt die Filme zurück, die zwischen zwei gegebenen Jahren veröffentlicht wurden.
     *
     * @param movies     Liste von Filmen
     * @param startYear  Anfangsjahr
     * @param endYear    Endjahr
     * @return Liste von Filmen, die zwischen startYear und endYear veröffentlicht wurden
     */
    public List<Movie> getMoviesBetweenYears(List<Movie> movies, int startYear, int endYear) {
        return movies.stream()
                .filter(movie -> movie.getYear() >= startYear && movie.getYear() <= endYear)
                .collect(Collectors.toList());
    }
    public static void main(String[] args) {
        HomeController controller = new HomeController();
        List<String> newMovieTitleList = new ArrayList<>();

        controller.originalMovieList.stream().map(m -> newMovieTitleList.addAll(Arrays.asList(m.title))).collect(Collectors.toList()); //add movie titles to new list
        newMovieTitleList.stream().map(m -> m.replaceAll("\\s+","")); //replace white spaces
        //System.out.println(newMovieTitleList.stream().max(Comparator.comparing(String::length)));
        newMovieTitleList.stream().filter(m -> m.length() == 0);    //filter movies with no title - exception
        newMovieTitleList.stream().max(Comparator.comparing(String::length));   //compare length of title
        //System.out.println(newMovieTitleList);



    }


}


