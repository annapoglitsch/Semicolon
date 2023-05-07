package com.example.Semicolon.Back.Controller;

import com.example.Semicolon.Back.ClickEventHandler;
import com.example.Semicolon.Back.Controller.DetailsController;
import com.example.Semicolon.Back.Controller.FhmdbApplication;
import com.example.Semicolon.Back.Controller.HomeController;
import com.example.Semicolon.Back.Movie;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.Objects;

public class MovieCard extends ListCell<Movie> {

    private final Label title = new Label(), genres = new Label(), description = new Label(), rating = new Label();

    private final Button watchListButton = new Button();
    private final ImageView cover = new ImageView();
    private final VBox card = new VBox(cover, title, rating, description, genres, watchListButton);

    @Override
    protected void updateItem(Movie movie, boolean empty) {
        super.updateItem(movie, empty);
        if (movie != null) {
            if (movie.id.equals("error") || movie.id.equals("Movie-list-is-empty")) {
                setErrorMessage(movie);
                return;
            }
        }
        if (empty || movie == null) {
            setText(null);
            setGraphic(null);
        } else {
            this.getStyleClass().add("movie-cell");
            title.setText(movie.title + " (" + (int) movie.releaseYear + ")");
            if (movie.description != null) {
                description.setText(movie.description);
            } else {
                description.setText("No description available");
            }
            String genreList = "";
            for (int i = 0; i < movie.genres.length; i++) {
                if (i == 0) {
                    genreList = movie.genres[i];
                } else {
                    genreList += (", " + movie.genres[i]);
                }
            }
            rating.setText("FHMDb rating: " + movie.rating + "/10");
            rating.getStyleClass().clear();
            rating.getStyleClass().add("description-text-color");
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
            card.setPadding(new Insets(5, 30, 5, 10));
            card.alignmentProperty().set(javafx.geometry.Pos.CENTER_LEFT);
            if (!HomeController.watchlist.isEmpty()) {
                System.out.println(HomeController.watchlist);
                for (Movie m : HomeController.watchlist) {
                    System.out.println(m.id + " " + movie.id);
                    if (Objects.equals(m.id, movie.id)) {
                        watchListButton.setText("remove from Watchlist");
                        break;
                    } else {
                        watchListButton.setText("Watchlist");
                    }
                }
            } else {
                watchListButton.setText("Watchlist");
            }
            card.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    DetailsController.movie = movie;
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("com/example/Semicolon/Details.fxml"));
                    try {
                        switchScene(mouseEvent);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            watchListButton.setOpacity(1);
            watchListButton.setDisable(false);
            setGraphic(card);
        }
    }

    private void switchScene(Event event) throws IOException {
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Details.fxml"))));
        scene.getStylesheets().add(Objects.requireNonNull(FhmdbApplication.class.getResource("styles.css")).toExternalForm());
    }
    private void setErrorMessage(Movie movie) {
        this.getStyleClass().add("movie-cell");
        title.setText(movie.imgUrl);
        title.alignmentProperty().set(Pos.TOP_CENTER);
        title.getStyleClass().clear();
        title.getStyleClass().add("text-color");
        title.fontProperty().set(title.getFont().font(20));
        description.setText("");
        genres.setText("");
        rating.setText("");
        card.alignmentProperty().set(javafx.geometry.Pos.CENTER_LEFT);
        card.spacingProperty().set(10);
        card.setPadding(new Insets(5, 30, 5, 10));
        card.getStyleClass().clear();
        card.getStyleClass().add("background-black");
        card.alignmentProperty().set(Pos.CENTER);
        watchListButton.setOpacity(0);
        watchListButton.setDisable(true);
        setGraphic(card);
    }

    public MovieCard() {
    }

    public MovieCard(ClickEventHandler addToWatchListClicked) {
        super();
        watchListButton.setOnMouseClicked(mouseEvent -> {
            addToWatchListClicked.onClick(getItem());
            if (!HomeController.watchlist.isEmpty())
                for (Movie m : HomeController.watchlist) {
                    if (Objects.equals(m.id, getItem().id)) {
                        watchListButton.setText("remove from Watchlist");
                        break;
                    } else {
                        watchListButton.setText("Watchlist");
                    }
                }
            else {
                watchListButton.setText("Watchlist");
            }
        });

    }
}
