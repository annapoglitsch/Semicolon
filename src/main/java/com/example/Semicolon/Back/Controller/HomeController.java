package com.example.Semicolon.Back.Controller;

import com.example.Semicolon.Back.ClickEventHandler;
import com.example.Semicolon.Back.Movie;
import com.example.Semicolon.Back.MovieAPI;
import com.example.Semicolon.Exceptions.MovieApiException;
import com.example.Semicolon.database.WatchlistRepository;
import javafx.animation.TranslateTransition;
import javafx.beans.value.*;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Variables
 */
public class HomeController implements Initializable {
    @FXML
    GridPane HomeGrid, menu;
    @FXML
    Button menuButton, watchlistButton, homeButton;
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
    public static final String[] allGenres = new String[]{"---ALL GENRES---", "ACTION", "ADVENTURE", "ANIMATION", "BIOGRAPHY", "COMEDY",
            "CRIME", "DRAMA", "DOCUMENTARY", "FAMILY", "FANTASY", "HISTORY", "HORROR",
            "MUSICAL", "MYSTERY", "ROMANCE", "SCIENCE_FICTION", "SPORT", "THRILLER", "WAR",
            "WESTERN"};
    private ObservableList<String> allYears = FXCollections.observableArrayList();
    public boolean menuActive = false, sortedByTitle = false;
    public static boolean watchlistActive = false;
    private static String URL = "https://prog2.fh-campuswien.ac.at/movies";
    private String query = "";
    private String genre = "";
    private String rating = "";
    private String releaseYear = "";
    /******Lists******/
    private static Movie emptyMovie = new Movie("Movie-list-is-empty", "zzzzzzzzzzzzzzzzzzzzz", allGenres, 0, "", "No Movies", 0, null, null, null, 0);
    private static MovieAPI api = new MovieAPI();
    public static List<Movie> originalMovieList = new ArrayList<>();

    public static void setOriginalMovieList() {
        try {
            originalMovieList = api.initializeMoviesNew(URL);
        } catch (MovieApiException e) {
            if (Objects.equals(e.getMessage(), "Malformed URL")) {
                originalMovieList.add(new Movie("error", " ", allGenres, 0, " ", "Error-404 \n Could not find movies on external site!", 0, null, null, null, 0));
            } else if (Objects.equals(e.getMessage(), "No Internet connection")) {
                originalMovieList.add(new Movie("error", " ", allGenres, 0, " ", "Error-502 \n Check your internet connection!", 0, null, null, null, 0));
            } else if (Objects.equals(e.getMessage(), "Unexpected Error")) {
                originalMovieList.add(new Movie("error", " ", allGenres, 0, " ", "An unexpected Error accrued", 0, null, null, null, 0));
            }
        }
    }

    public static List<Movie> watchlist = new ArrayList<>();

    public static void setWatchlist() {
        watchlist = WatchlistRepository.getWatchlistAsMovies();
        if (watchlist.isEmpty()) {
            if (originalMovieList.get(0).id == "error") {
                Movie error = new Movie(originalMovieList.get(0));
                error.imgUrl += "\nTherefor displaying watchlist isn't possible!";
                watchlist.add(error);
            } else {
                watchlist.add(emptyMovie);
            }
        }
    }

    //public List<Movie> originalMovieList = movie.staticMovieList();
    public ObservableList<Movie> movieList = FXCollections.observableArrayList();
    private ObservableList<String> genres = FXCollections.observableList(Arrays.asList(allGenres));
    public ObservableList<String> sortingKeywords = FXCollections.observableList(Arrays.asList("---NO SORTING---", "A-Z", "Z-A", "Rating - High to Low", "Rating - Low to High", "New to Old", "Old to New"));

    /**
     * Business Logic Layer
     */
    private final ClickEventHandler onAddToWatchlistClicked = (clickedItem) -> {
        if (clickedItem instanceof Movie) {
            Movie movieWatch = (Movie) clickedItem;
            try {
                if(watchlist.get(0).id == "Movie-list-is-empty"){
                    watchlist.clear();
                }
                if (HomeController.watchlist.contains(movieWatch)) {
                    WatchlistRepository.removeFromWatchlist(WatchlistRepository.movieToWatchlist(movieWatch));
                    HomeController.watchlist.remove(movieWatch);
                } else {
                    WatchlistRepository.addToWatchlist(WatchlistRepository.movieToWatchlist(movieWatch));
                    HomeController.watchlist.add(movieWatch);
                }
                if(watchlist.isEmpty()){
                    watchlist.add(emptyMovie);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    };


    /**
     * Methods
     */


    @FXML
    private void switchWatchlist() {
        resetSearchParameters();

        watchlistActive = true;
        activateMenu();
        movieList.clear();
        movieList.addAll(watchlist);
    }

    @FXML
    private void switchHome() {
        resetSearchParameters();

        watchlistActive = false;
        activateMenu();
        movieList.clear();
        movieList.addAll(originalMovieList);
    }
    private void resetSearchParameters(){
        ratingSlider.setValue(0);
        ratingLabel.setText("0");
        genresChoice.setValue("---ALL GENRES---");
        sortingChoice.setValue("---NO SORTING---");
        releaseYearChoice.setValue("---All YEARS---");

        query = "";
        genre = "";
        rating = "";
        releaseYear = "";
    }

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
        if (watchlistActive) {
            movieList.addAll(watchlist);
        } else {
            movieList.addAll(originalMovieList);
        }
        movieDisplay.setItems(movieList);
        movieDisplay.setCellFactory(movieDisplay -> new MovieCard(onAddToWatchlistClicked));
        if (originalMovieList.size() > 1) {
            searchField.setPromptText(originalMovieList.get(new Random().nextInt(originalMovieList.size() - 1)).title);
            setYearChoice();
        } else if (!Objects.equals(originalMovieList.get(0).id, "error")) {
            searchField.setPromptText(originalMovieList.get(0).title);
        } else {
            searchField.setPromptText("Search");
        }
        genresChoice.setItems(genres);
        genresChoice.setValue("---ALL GENRES---");
        sortingChoice.setItems(sortingKeywords);
        sortingChoice.setValue("---NO SORTING---");
        releaseYearChoice.setItems(allYears);
        releaseYearChoice.setValue("---All YEARS---");
        sortingChoice.setOnAction(this::sortMoviesByTitlePreparation);  /** choiceBox sorting set on action */
        genresChoice.setOnAction(this::filterMoviesByGenrePrep);          /** choiceBox genre set on action */
        searchField.setOnKeyTyped(event -> {
            searchMoviePrep();
        });

        releaseYearChoice.setOnAction(this::filterByReleaseYearPrep);
        ratingSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                filterByRatingFrom((int) ratingSlider.getValue());
                setYearChoice();
                if (movieList.size() == 0) {
                    movieList.add(emptyMovie);
                } else {
                    setYearChoice();
                }
            }
        });
    }

    public void sortMoviesByTitlePreparation(ActionEvent event) {   //prep so that choiceBox sorting is not null
        sortMovies(event, sortingChoice.getValue());
    }

    public void filterMoviesByGenrePrep(ActionEvent event) {      /** prep so that choiceBox genre is not null */
        filterMoviesByGenre(event, genresChoice.getValue());
        if (movieList.size() == 0) {
            movieList.add(emptyMovie);
        } else {
            setYearChoice();
        }
    }

    public void filterByReleaseYearPrep(ActionEvent event) {
        filterByReleaseYear(event, releaseYearChoice.getValue());
    }

    @FXML
    public void searchMoviePrep() {
        searchMovies(searchField.getText().toLowerCase()); /** so that searchField is not null */
        if (movieList.size() == 0) {
            movieList.add(emptyMovie);
        } else {
            setYearChoice();
        }
    }

    public void changeURL(String addon, String source) {
        if (addon != null) {
            addon = addon.replaceAll(" ", "%20");
            if (addon.equals("RESET")) {
                addon = "";
            }
            switch (source) {
                case "genre":
                    genre = addon;
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
            URL = "https://prog2.fh-campuswien.ac.at/movies?query=" + query + "&genre=" + genre + "&ratingFrom=" + rating + "&releaseYear=" + releaseYear;
            setMovieList();
        }
    }

    private List<Movie> getMovieList() {
        List<Movie> list = new ArrayList<>();
        try {
            list = api.initializeMoviesNew(URL);
        } catch (MovieApiException e) {
            if (Objects.equals(e.getMessage(), "Malformed URL")) {
                list.add(new Movie("error", " ", allGenres, 0, " ", "Error-404 \n Could not find movies on external site!", 0, null, null, null, 0));
            } else if (Objects.equals(e.getMessage(), "No Internet connection")) {
                list.add(new Movie("error", " ", allGenres, 0, " ", "Error-502 \n Check your Internetconnection", 0, null, null, null, 0));
            } else if (Objects.equals(e.getMessage(), "Unexpected Error")) {
                list.add(new Movie("error", " ", allGenres, 0, " ", "An unexpected Error accrued", 0, null, null, null, 0));
            }
        }
        return list;
    }

    private void setMovieList() {
        List<Movie> movies = getMovieList();
        movieList.clear();
        if (watchlistActive) {
            for (Movie movie : movies) {
                for (Movie m : watchlist) {
                    if (Objects.equals(movie.id, m.id)) {
                        movieList.add(movie);
                        break;
                    }
                }
            }
            if (movieList.isEmpty()) {
                movieList.add(emptyMovie);
            }
        } else {
            movieList.addAll(movies);
        }
        if (sortedByTitle) {
            sortMovies(new ActionEvent(), sortingChoice.getValue());
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
        if (value / 10. <= 0) {
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

    public ObservableList<Movie> sortMovies(ActionEvent event, String keyWord) {
        sortedByTitle = true;
        if (Objects.equals(keyWord, "---NO SORTING---")) {
            sortedByTitle = false;
            setMovieList();
            return movieList;
        }
        if (sortingKeywords.contains(keyWord)) {
            Collections.sort(movieList, new Comparator<Movie>() {
                @Override
                public int compare(Movie o1, Movie o2) {
                    return HomeController.this.compare(o1, o2, keyWord);
                }
            });
        }
        return movieList;
    }

    private int compare(Movie m1, Movie m2, String keyWord) {
        int value = 0;
        String type = null;
        switch (keyWord) {
            case "Rating - Low to High":
                value = -1;
                type = "Rating";
                break;
            case "Rating - High to Low":
                value = 1;
                type = "Rating";
                break;
            case "Old to New":
                value = -1;
                type = "Year";
                break;
            case "New to Old":
                value = 1;
                type = "Year";
                break;
            case "A-Z":
                value = 1;
                type = "Alphabetical";
                break;
            case "Z-A":
                value = -1;
                type = "Alphabetical";
                break;
        }
        if ((m1.rating > m2.rating && Objects.equals(type, "Rating")) || (m1.releaseYear > m2.releaseYear && Objects.equals(type, "Year"))) {
            return -value;
        } else if ((m1.rating < m2.rating && Objects.equals(type, "Rating")) || (m1.releaseYear < m2.releaseYear && Objects.equals(type, "Year"))) {
            return value;
        } else if (value == 1 && Objects.equals(type, "Alphabetical")) {
            return m1.title.compareTo(m2.title);
        } else if (value == -1 && Objects.equals(type, "Alphabetical")) {
            return m2.title.compareTo(m1.title);
        } else {
            return 0;
        }
    }

    /**
     * Stream Methods
     */
    public void setYearChoice() {
        if (movieList.size() != 0) {
            double endingYear, startingYear;
            endingYear = movieList.stream().max(Comparator.comparing(Movie::getReleaseYear)).orElseThrow(NoSuchElementException::new).releaseYear;
            startingYear = movieList.stream().min(Comparator.comparing(Movie::getReleaseYear)).orElseThrow(NoSuchElementException::new).releaseYear;
            allYears.clear();
            allYears.add("---All Years---");
            for (double currentYear = startingYear; currentYear <= endingYear; currentYear++) {
                allYears.add(1, String.valueOf((int) currentYear));
            }
            releaseYearChoice.setItems(allYears);
            releaseYearChoice.setValue("---All Years---");
        }
    }

    String getMostPopularActor(List<Movie> movies) {
        List<String> newList = new ArrayList<>();   /** erstellt neue Liste*/
        movies.stream().map(m -> newList.addAll(Arrays.asList(m.mainCast))).toList();   /** added den maincast zur neuen Liste*/
        Map<String, Long> newMap = newList.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())); /** grouped alle doppelten Cast zu einem und zählt Auftreten*/
        return newMap.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(null); /** liefert höchsten Wert indem verglichen wird*/
    }

    int getLongestMovieTitle(List<Movie> movies) {
        List<String> newMovieTitleList = movies.stream().map(m -> m.title).toList(); /** fügt titel zur Liste hinzu*/
        return newMovieTitleList.stream().mapToInt(String::length).max().orElse(0); /** or Else damit es kein OptionalInt ist */
    }

    /**
     * Gibt die Anzahl der Filme eines bestimmten Regisseurs zurück.
     *
     * @param movies   Liste von Filmen
     * @param director Regisseur, dessen Filme gezählt werden sollen
     * @return Anzahl der Filme des Regisseurs
     */
    public long countMoviesFrom(List<Movie> movies, String director) {
        return movies.stream()
                .filter(movie -> movie.directors != null && Arrays.asList(movie.directors).contains(director))
                .count();
    }

    /**
     * Gibt die Filme zurück, die zwischen zwei gegebenen Jahren veröffentlicht wurden.
     *
     * @param movies    Liste von Filmen
     * @param startYear Anfangsjahr
     * @param endYear   Endjahr
     * @return Liste von Filmen, die zwischen startYear und endYear veröffentlicht wurden
     */
    public List<Movie> getMoviesBetweenYears(List<Movie> movies, int startYear, int endYear) {
        return movies.stream()
                .filter(movie -> movie.releaseYear >= startYear && movie.releaseYear <= endYear)
                .toList();
    }


    public static void main(String[] args) {
        HomeController controller = new HomeController();
        System.out.println(controller.getMostPopularActor(controller.originalMovieList));
        System.out.println(controller.getLongestMovieTitle(controller.originalMovieList));
        System.out.println(controller.countMoviesFrom(controller.originalMovieList, "Peter Jackson"));
        System.out.println(controller.getMoviesBetweenYears(controller.originalMovieList, 1900, 3000));
    }
}