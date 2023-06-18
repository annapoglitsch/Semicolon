package com.example.Semicolon.Back.Controller;

import com.example.Semicolon.Back.Movie;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class DetailsController /*implements Initializable*/ {
    /*@FXML
    public GridPane menu, ArticleGrid, HomeGrid;
    @FXML
    public Button menuButton;
    @FXML
    public Label Title, description, Runtime, Rating, directors, writers, cast, genres;
    @FXML
    public ImageView Image;
    private boolean menuActive = false;
    public static Movie movie;

    @FXML
    private void activateMenu() {
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
        Title.setText(movie.title + " " + (int) movie.releaseYear);
        description.setText(movie.description);
        description.setWrapText(true);
        Runtime.setText(String.valueOf((int) movie.lengthInMinutes) + " Minutes");
        Rating.setText("Rating: " + String.valueOf(movie.rating));
        StringBuilder director = new StringBuilder();
        for (String d : movie.directors){
            director.append(", ").append(d);
        }
        directors.setText(director.substring(2,director.length()));
        StringBuilder writer = new StringBuilder();
        for (String d : movie.writers){
            writer.append(", ").append(d);
        }
        writers.setText(writer.substring(2,writer.length()));
        StringBuilder casts = new StringBuilder();
        for (String d : movie.mainCast){
            casts.append(", ").append(d);
        }
        cast.setText(casts.substring(2,casts.length()));
        StringBuilder genre = new StringBuilder();
        for (String d : movie.genres){
            genre.append(", ").append(d);
        }
        genres.setText(genre.substring(2,genre.length()));
        Image.setImage(getImage());
        Image.setPreserveRatio(true);
        Image.setFitHeight(700);
    }
    private Image getImage(){
        if(movie.imgUrl != null){
            try {
                WebClient client = new WebClient();
                client.getOptions().setJavaScriptEnabled(false);
                client.getOptions().setCssEnabled(false);
                client.getOptions().setUseInsecureSSL(true);
                HtmlPage page = client.getPage(movie.imgUrl);
                HtmlElement imgElement = page.getFirstByXPath("//img[not(@height='1' or @width='1')]");
                Image image = new Image(imgElement.getAttribute("src"));
                return image;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
    private void switchScene(Event event) {
        try {
            Scene scene = ((Node) event.getSource()).getScene();
            scene.setRoot(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("test.fxml"))));
            scene.getStylesheets().add(Objects.requireNonNull(FhmdbApplication.class.getResource("styles.css")).toExternalForm());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void switchHome(ActionEvent actionEvent) {
        HomeController.watchlistActive = false;
        switchScene(actionEvent);
    }

    public void switchWatchlist(ActionEvent actionEvent) {
        HomeController.watchlistActive = true;
        switchScene(actionEvent);
    }
    public void goBack(ActionEvent actionEvent) {
        switchScene(actionEvent);
    }*/
}
