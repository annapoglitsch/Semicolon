package com.example.Semicolon;

import com.example.Semicolon.Back.Movie;
import javafx.collections.*;
import javafx.event.ActionEvent;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MovieTest extends ActionEvent {

    HomeController controller;
    @Test
    @Order(1)
    void is_movie_sorted_a_to_z() {
        try {
            ObservableList<Movie> movieList = FXCollections.observableArrayList();
            ObservableList<Movie> rightOrder = FXCollections.observableArrayList();
            for (int i = 9; i >= 0; i--) {
                Movie movie = new Movie(null, String.valueOf(i), new String[]{"ACTION", "DRAMA"}, 0, String.valueOf(i), null, null, null, null, null, 0);
                movieList.add(movie);
                if(i != 10) {
                    rightOrder.add(0, movie);
                }else{
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
    @Order(2)
    void is_movie_sorted_z_to_a() {
        try {
            ObservableList<Movie> rightOrder = FXCollections.observableArrayList();
            ObservableList<Movie> movieList = FXCollections.observableArrayList();
            for (int i = 9; i >= 0; i--) {
                Movie movie = new Movie(null, String.valueOf(i), new String[]{"ACTION", "DRAMA"}, 0, String.valueOf(i), null, null, null, null, null, 0);
                rightOrder.add(movie);
                if(i != 10) {
                    movieList.add(0, movie);
                }else{
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
    @Order(3)
    void is_movie_reset_sorting() {
        try {
            ObservableList<Movie> rightOrder = FXCollections.observableArrayList();
            ObservableList<Movie> movieList = FXCollections.observableArrayList();
            for (int i = 9; i >= 0; i--) {
                Movie movie = new Movie(null, String.valueOf(i), new String[]{"ACTION", "DRAMA"}, 0, String.valueOf(i), null, null, null, null, null, 0);
                rightOrder.add(movie);
                if(i != 10) {
                    movieList.add(0, movie);
                }else{
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
    @Order(4)
    void is_movie_sorted_by_genre() {
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
                Movie movie = new Movie(null, String.valueOf(i), new String[]{genre}, 0, String.valueOf(i), null, null, null, null, null, 0);
                movieList.add(movie);
                if (i % 2 == 0) {
                    movieList2.add(movie);
                }
            }
            controller = new HomeController();
            controller.originalMovieList = movieList;
            controller.tempSortedMovieList = movieList;
            assertEquals(movieList2, controller.sortMoviesByGenre(this, "ACTION"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        @Test
        @Order(5)
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
                    Movie movie = new Movie(null, movieString[i], new String[]{"ACTION", "DRAMA"}, 0, String.valueOf(i), null, null, null, null, null, 0);
                    movieList.add(movie);
                    if (movieString[i].toLowerCase().contains("this")) {
                        rightOrder.add( movie);
                    }
                }
                controller = new HomeController();
                controller.movieList.addAll(movieList);
                controller.tempSortedMovieList.addAll(movieList);
                assertEquals(rightOrder, controller.searchMovie("this"));
            } catch(Exception e){
                e.printStackTrace();
            }

        }
    }


