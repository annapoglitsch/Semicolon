package com.example.Semicolon.Back;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.*;

public class MovieCard extends ListCell<Movie> {

    private final Label title = new Label();
    private final Label description = new Label();
    private final VBox card = new VBox(title, description);

    @Override
    protected void updateItem(Movie movie, boolean empty) {
        super.updateItem(movie, empty);
        if (empty || movie == null) {
            setText(null);
        } else {
            this.getStyleClass().add("movie-cell");
            title.setText(movie.title);
            if(movie.description != null){
                description.setText(movie.description);
            }else{
                description.setText("No description available");
            }
            title.getStyleClass().add("text-yellow");
            description.getStyleClass().add("text-white");
            title.fontProperty().set(title.getFont().font(20));
            description.setMaxWidth(this.getScene().getWidth() - 30);
            description.setWrapText(true);
            card.getStyleClass().add("cell-border");
            card.spacingProperty().set(10);
            card.alignmentProperty().set(javafx.geometry.Pos.CENTER_LEFT);
            setGraphic(card);
        }
    }
}
