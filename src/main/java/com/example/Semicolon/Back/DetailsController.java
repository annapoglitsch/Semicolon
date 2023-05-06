package com.example.Semicolon.Back;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class DetailsController {
    @FXML
    public GridPane menu;
    public GridPane HomeGrid;
    public Button menuButton;
    public Label Title, description, Runtime, Rating, directors, writers, cast, genres;
    private boolean menuActive = false;

    @FXML
    private void activateMenu() { /**make menu slide down/up */
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
}
