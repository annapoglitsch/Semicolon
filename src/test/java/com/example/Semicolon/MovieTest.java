package com.example.Semicolon;

import com.example.Semicolon.Back.Movie;
import javafx.collections.*;
import javafx.event.ActionEvent;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MovieTest extends ActionEvent {

    HomeController controller;
    Movie movie = new Movie();

    @Test
    @Order(1)
    void has_web_list_changed() {
        try {
            List<Movie> correct = movie.staticMovieList();
            List<Movie> movieList = movie.initializeMovies("https://prog2.fh-campuswien.ac.at/movies");
            if (correct.size() == movieList.size()) {
                for (int i = 0; i < correct.size(); i++) {
                    if (!Objects.equals(correct.get(i).id, movieList.get(i).id)) {
                        assertEquals(correct.get(i).id, movieList.get(i).id);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(2)
    void url_is_worng() {
        try {
            List<Movie> movieList = movie.initializeMovies("wrongURL");
            assertEquals("error", movieList.get(0).id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(3)
    void are_movies_sorted_a_to_z() {
        try {
            ObservableList<Movie> movieList = FXCollections.observableArrayList();
            ObservableList<Movie> rightOrder = FXCollections.observableArrayList();
            for (int i = 9; i >= 0; i--) {
                Movie movie = new Movie(null, String.valueOf(i), new String[]{"ACTION", "DRAMA"}, 0, String.valueOf(i), null, 0, null, null, null, 0);
                movieList.add(movie);
                if (i != 10) {
                    rightOrder.add(0, movie);
                } else {
                    rightOrder.add(movie);
                }
            }
            controller = new HomeController();
            controller.movieList.addAll(movieList);
            assertEquals(rightOrder, controller.sortMoviesByTitle(this, "A-Z"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(4)
    void are_movies_sorted_z_to_a() {
        try {
            ObservableList<Movie> rightOrder = FXCollections.observableArrayList();
            ObservableList<Movie> movieList = FXCollections.observableArrayList();
            for (int i = 9; i >= 0; i--) {
                Movie movie = new Movie(null, String.valueOf(i), new String[]{"ACTION", "DRAMA"}, 0, String.valueOf(i), null, 0, null, null, null, 0);
                rightOrder.add(movie);
                if (i != 10) {
                    movieList.add(0, movie);
                } else {
                    movieList.add(movie);
                }
            }
            controller = new HomeController();
            controller.movieList.addAll(movieList);
            assertEquals(rightOrder, controller.sortMoviesByTitle(this, "Z-A"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(5)
    void is_movie_sorting_reseted() {
        try {
            ObservableList<Movie> rightOrder = FXCollections.observableArrayList();
            ObservableList<Movie> movieList = FXCollections.observableArrayList();
            for (int i = 9; i >= 0; i--) {
                Movie movie = new Movie(null, String.valueOf(i), new String[]{"ACTION", "DRAMA"}, 0, String.valueOf(i), null, 0, null, null, null, 0);
                rightOrder.add(movie);
                if (i != 10) {
                    movieList.add(0, movie);
                } else {
                    movieList.add(movie);
                }
            }
            controller = new HomeController();
            controller.movieList.addAll(rightOrder);
            controller.originalMovieList = rightOrder;
            assertEquals(rightOrder, controller.sortMoviesByTitle(this, "---NO SORTING---"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(6)
    void are_movies_sorted_by_genre() {
        try {
            ObservableList<Movie> movieList = FXCollections.observableArrayList();
            ObservableList<Movie> movieList2 = FXCollections.observableArrayList();
            for (int i = 9; i >= 0; i--) {
                String genre;
                if (i % 2 == 0) {
                    genre = "ACTION";
                } else {
                    genre = "DRAMA";
                }
                Movie movie = new Movie(null, String.valueOf(i), new String[]{genre}, 0, String.valueOf(i), null, 0, null, null, null, 0);
                movieList.add(movie);
                if (i % 2 == 0) {
                    movieList2.add(movie);
                }
            }
            controller = new HomeController();
            controller.movieList = movieList;
            assertEquals(movieList2, controller.sortMoviesByGenre(this, "ACTION"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(7)
    void empty_movie_list_after_sorted_by_genre() {
        try {
            Movie empty = new Movie("Movie-list-is-empty", "zzzzzzzzzzzzzzzzzzzzz", null, 0, "", "No Movies", 0, null, null, null, 0);
            ObservableList<Movie> movieList = FXCollections.observableArrayList();
            for (int i = 9; i >= 0; i--) {
                String genre;
                if (i % 2 == 0) {
                    genre = "ACTION";
                } else {
                    genre = "DRAMA";
                }
                Movie movie = new Movie(null, String.valueOf(i), new String[]{genre}, 0, String.valueOf(i), null, 0, null, null, null, 0);
                movieList.add(movie);
            }
            controller = new HomeController();
            controller.movieList = movieList;
            assertEquals(empty.id, controller.sortMoviesByGenre(this, "ANIMATION").get(0).id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(8)
    void empty_movie_list_after_search() {
        try {
            Movie empty = new Movie("Movie-list-is-empty", "zzzzzzzzzzzzzzzzzzzzz", null, 0, "", "No Movies", 0, null, null, null, 0);
            ObservableList<Movie> movieList = FXCollections.observableArrayList();
            for (int i = 9; i >= 0; i--) {
                String genre;
                if (i % 2 == 0) {
                    genre = "ACTION";
                } else {
                    genre = "DRAMA";
                }
                Movie movie = new Movie(null, String.valueOf(i), new String[]{genre}, 0, String.valueOf(i), null, 0, null, null, null, 0);
                movieList.add(movie);
            }
            controller = new HomeController();
            controller.movieList = movieList;
            assertEquals(empty.id, controller.searchMovie("not part of the title").get(0).id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(9)
    void does_searchMethod_search_for_the_right_movies() {
        HomeController controller;
        String[] movieString = new String[10];
        movieString[0] = "Hello";
        movieString[1] = "World";
        movieString[2] = "This is a test";
        movieString[3] = "This is not a test";
        movieString[4] = "Semicolon";
        movieString[5] = "Exercise one";
        movieString[6] = "Bye";
        movieString[7] = "Pizza";
        movieString[8] = "Soup";
        movieString[9] = "I am tired";

        try {
            ObservableList<Movie> movieList = FXCollections.observableArrayList();
            ObservableList<Movie> rightOrder = FXCollections.observableArrayList();
            for (int i = 9; i >= 0; i--) {
                Movie movie = new Movie(null, movieString[i], new String[]{"ACTION", "DRAMA"}, 0, String.valueOf(i), null, 0, null, null, null, 0);
                movieList.add(movie);
                if (movieString[i].toLowerCase().contains("this")) {
                    rightOrder.add(movie);
                }
            }
            controller = new HomeController();
            controller.movieList.addAll(movieList);
            controller.originalMovieList.addAll(movieList);
            assertEquals(rightOrder, controller.searchMovie("this"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    @Order(10)
    void genre_and_sort_method_at_the_same_time() {

        HomeController controller = new HomeController();
        ObservableList<Movie> movieList = FXCollections.observableArrayList();
        ObservableList<Movie> movieList2 = FXCollections.observableArrayList();
    try {
        for (int i = 9; i >= 0; i--) {
            String genre;
            if (i % 2 == 0) {
                genre = "ACTION";
            } else {
                genre = "DRAMA";
            }
            Movie movie = new Movie(null, String.valueOf(i), new String[]{genre}, 0, String.valueOf(i), null, 0, null, null, null, 0);
            movieList.add(movie);
            if (i != 10) {
                movieList2.add(0, movie);
            } else {
                movieList2.add(movie);
            }
        }

        controller.movieList.addAll(movieList);
        assertEquals(movieList2, controller.sortMoviesByTitle(this, "A-Z"));
        assertEquals(movieList2, controller.sortMoviesByGenre(this, "ACTION"));
    }catch (Exception e){
        e.printStackTrace();
    }
    }
}


