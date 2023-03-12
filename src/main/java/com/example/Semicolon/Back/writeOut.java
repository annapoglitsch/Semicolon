package com.example.Semicolon.Back;

import java.util.Arrays;
import java.util.List;

public class writeOut {
    public static void main(String[] args) {
        Movie movie = new Movie();
        List<Movie> list = movie.initializeMovies("https://prog2.fh-campuswien.ac.at/movies");
        for(Movie m : list){
            System.out.println("movieList.add(new Movie(\"" + m.id + "\", \"" + m.title + "\", new String[]{" + Arrays.toString(m.genres) + "}, " + m.releaseYear + ", \"" + m.description + "\", \"" + m.imgUrl + "\", " + m.length + ", new String[]{" + Arrays.toString(m.directors) + "}, new String[]{" + Arrays.toString(m.writers) + "}, new String[]{" + Arrays.toString(m.cast) + "}, " + m.rating + "));");
        }
    }
}
