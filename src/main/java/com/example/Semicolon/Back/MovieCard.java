package com.example.Semicolon.Back;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.List;

public class MovieCard extends ListCell<Movie> {

    private final Label title = new Label();
    private final Label description = new Label();
    private final ImageView cover = new ImageView();
    private final VBox card = new VBox(cover, title, description);

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
            /*if(movie.imgUrl != null){
                try {
                    WebClient client = new WebClient();
                    client.getOptions().setJavaScriptEnabled(false);
                    client.getOptions().setCssEnabled(false);
                    client.getOptions().setUseInsecureSSL(true);
                    HtmlPage page = client.getPage(movie.imgUrl);
                    HtmlElement imgElement = page.getFirstByXPath("//img[not(@height='1' or @width='1')]");
                    Image image = new Image(imgElement.getAttribute("src"));
                    cover.setImage(image);
                    cover.setFitHeight(10);
                    cover.setFitWidth(10);
                    System.out.println("1");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else{
            }*/
            title.getStyleClass().add("title-text-color");
            description.getStyleClass().add("description-text-color");
            title.fontProperty().set(title.getFont().font(20));
            //description.setMaxWidth(this.getScene().getWidth() - 30);
            description.setWrapText(true);
            card.getStyleClass().add("cell-border");
            card.spacingProperty().set(10);
            card.setPadding(new Insets(5,30,5,10));
            card.alignmentProperty().set(javafx.geometry.Pos.CENTER_LEFT);
            setGraphic(card);
        }
    }
}
