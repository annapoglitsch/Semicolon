package com.example.Semicolon.Back;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.lang.reflect.Array;

public class MovieCard extends ListCell<Movie> {

    private final Label title = new Label();
    private final Label description = new Label();
    private final Label genres = new Label();
    private final ImageView cover = new ImageView();
    private final VBox card = new VBox(cover, title, description, genres);

    @Override
    protected void updateItem(Movie movie, boolean empty) {
        super.updateItem(movie, empty);
        if(movie != null) {
            if (movie.id.equals("error-404-Page-Not-Found") || movie.id.equals("error-502-Bad-Gateway-server")) {
                setGraphic(null);
                setErrorMessage(movie);
                return;
            }
            if(movie.id.equals("Movie-list-is-empty")){
                setGraphic(null);
                setErrorMessage(movie);
                return;
            }
        }
        if (empty || movie == null) {
            setText(null);
            setGraphic(null);
        } else {
            this.getStyleClass().add("movie-cell");
            title.setText(movie.title);
            if(movie.description != null){
                description.setText(movie.description);
            }else{
                description.setText("No description available");
            }
            String genreList = "";
            for (int i = 0; i < movie.genres.length; i++){
                if(i == 0){
                    genreList = movie.genres[i];
                }else{
                    genreList += (", " + movie.genres[i]);
                }
            }
            genres.setText(genreList);
            genres.getStyleClass().add("description-text-color");
            genres.setWrapText(true);
            title.getStyleClass().clear();
            title.getStyleClass().add("title-text-color");
            title.fontProperty().set(title.getFont().font(20));
            description.getStyleClass().clear();
            description.getStyleClass().add("description-text-color");
            description.setWrapText(true);
            card.getStyleClass().clear();
            card.getStyleClass().add("cell-border");
            card.spacingProperty().set(10);
            card.setPadding(new Insets(5,30,5,10));
            card.alignmentProperty().set(javafx.geometry.Pos.CENTER_LEFT);
            setGraphic(card);
        }
    }
    private void setErrorMessage(Movie movie){
        this.getStyleClass().add("movie-cell");
        title.setText(movie.imgUrl);
        title.alignmentProperty().set(Pos.TOP_CENTER);
        title.getStyleClass().clear();
        title.getStyleClass().add("text-color");
        title.fontProperty().set(title.getFont().font(20));
        description.setText("");
        card.alignmentProperty().set(javafx.geometry.Pos.CENTER_LEFT);
        card.spacingProperty().set(10);
        card.setPadding(new Insets(5,30,5,10));
        card.getStyleClass().clear();
        card.getStyleClass().add("background-black");
        card.alignmentProperty().set(Pos.CENTER);
        setGraphic(card);
    }
}
