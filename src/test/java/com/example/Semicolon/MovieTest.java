package com.example.Semicolon;

import com.example.Semicolon.Back.Movie;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import org.junit.jupiter.api.Test;

import java.util.List;

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
}