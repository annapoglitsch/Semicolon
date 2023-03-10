package com.example.Semicolon;

import com.example.Semicolon.Back.Movie;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import org.junit.jupiter.api.Test;

import java.util.List;

class MovieTest extends ActionEvent {
    @Test
    void is_movie_sorted_by_a_to_z(){
        //given
        HomeController controller = new HomeController();
        Movie movies = new Movie();
        List<Movie> aMovieList = movies.initializeMovies();

        //when
        ObservableList<Movie> actual = controller.sortMoviesByTitle(this);

        //then

      //  List<Movie> expected = aMovieList;

    }

}