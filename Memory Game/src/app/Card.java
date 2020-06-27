package app;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.image.Image;

import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Card extends Label
{
    private Image back;
    private Image front;
    private Image blank;
    private int cardId;
    private final int flipAnimationDuration = 300; //ms
    private Status status;

    public Card(String path, int cardId)
    {
        back = new Image(getClass().getResourceAsStream("../images/pepega.png"));
        blank = new Image(getClass().getResourceAsStream("../images/emptyImage.png"));
        if (path == null)
        {
            this.setGraphic(new ImageView(blank));
            status = Status.EMPTY;
        } else
        {
            front = new Image(getClass().getResourceAsStream(path));
            this.setGraphic(new ImageView(back));
            status = Status.COVERED;
        }
        this.cardId = cardId;
        setStyle("-fx-background-color: white;");
        setOnMouseEntered(e ->
        {
            if (status != Status.EMPTY && status != Status.REVEALED)
            {
                setStyle("-fx-opacity: 0.7;" +
                        "-fx-background-color: white;");
            }
        });
        setOnMouseExited(e -> setStyle("-fx-opacity: 1;" +
                "-fx-background-color: white;"));
    }

    public int getCardId()
    {
        return cardId;
    }

    public Status getStatus()
    {
        return status;
    }

    public void setEmpty()
    {

        changeImageView(this.getGraphic(), blank);
        status = Status.EMPTY;
    }

    private void changeImageView(Node currentImage, Image nextImage)
    {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(flipAnimationDuration), currentImage);
        fadeTransition.setToValue(0.0);
        fadeTransition.setAutoReverse(false);
        fadeTransition.setCycleCount(1);
        fadeTransition.setOnFinished(e ->
        {
            //This allows us to make changes on the javafx application thread, otherwise the GUI would freeze
            Platform.runLater(() -> this.setGraphic(new ImageView(nextImage)));
        });
        fadeTransition.play();
    }

    public void flip()
    {
        if (status == Status.COVERED)
        {
            status = Status.REVEALED;
            changeImageView(this.getGraphic(), front);
        }
        else if (status == Status.REVEALED)
        {
            status = Status.COVERED;
            changeImageView(this.getGraphic(), back);
        }
    }
}
