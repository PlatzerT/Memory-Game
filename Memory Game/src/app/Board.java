package app;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class Board extends BorderPane
{
    private int boardSize;

    private VBox playerGrid;
    private final double PLAYER_GAP = 2.0;

    private GridPane boardGrid;
    private final int BOARD_CELL_SIZE = 100;
    private final double BOARD_GAP = 5.0;

    public Board(int boardSize)
    {
        this.boardSize = boardSize;
        createBoardGrid();
        createPlayerGrid();
        setRight(playerGrid);
        setCenter(boardGrid);
    }

    public void fillPlayers(Player[] players)
    {
        for (int i = 0; i < players.length; i++)
        {
            playerGrid.getChildren().add(players[i]);
            VBox.setVgrow(players[i], Priority.ALWAYS);
        }
    }

    public void showEndScreen(Player[] ranking)
    {
        reArrangePlayers(ranking);
        getChildren().remove(boardGrid);

        setCenter(getExitButton());
    }

    //Set an exit button
    public Button getExitButton()
    {
        Button exit = new Button("Exit");
        exit.setStyle(
                "-fx-background-color: #218000;" +
                "-fx-font-size: 2em;"
        );
        exit.setOnMouseEntered(e ->
        {
            exit.setStyle(
                    "-fx-background-color: #123500;" +
                    "-fx-font-size: 2em;"
            );
        });
        exit.setOnMouseExited(e ->
        {
            exit.setStyle(
                    "-fx-background-color: #218000;" +
                            "-fx-font-size: 2em;" +
                            "-fx-opacity: 1;"
            );
        });
        exit.setTextFill(Color.WHITE);
        exit.setPadding(new Insets(10.0, 25.0, 10.0, 25.0));
        //We have to do that because of the child threads, which are created in this program
        exit.setOnAction(e ->
        {
            Platform.exit();
            System.exit(0);
        });
        return exit;
    }

    //Display ranking
    public void reArrangePlayers(Player[] ranking)
    {
        playerGrid.getChildren().clear();
        for (int i = 0; i < ranking.length; i++)
        {
            playerGrid.getChildren().add(ranking[i]);
            VBox.setVgrow(ranking[i], Priority.ALWAYS);
        }
    }

    public void fillBoardWithCards(Card[][] cards)
    {
        for (int i = 0; i < boardSize; i++)
        {
            for (int j = 0; j < boardSize; j++)
            {
                boardGrid.add(cards[i][j], i, j);
            }
        }
    }

    private void createPlayerGrid()
    {
        playerGrid = new VBox();
        playerGrid.setSpacing(PLAYER_GAP);
        playerGrid.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-padding: 0px 100px;" +
                "-fx-font-size: 0.85em;"
        );
    }

    private void createBoardGrid()
    {
        boardGrid = new GridPane();
        boardGrid.setStyle("-fx-background-color: black;");
        boardGrid.setHgap(BOARD_GAP);
        boardGrid.setVgap(BOARD_GAP);
        boardGrid.setPadding(new Insets(BOARD_GAP-1.0));
        for (int i = 0; i < boardSize; i++)
        {
            ColumnConstraints col = new ColumnConstraints(BOARD_CELL_SIZE);
            boardGrid.getColumnConstraints().add(col);

            RowConstraints row = new RowConstraints(BOARD_CELL_SIZE);
            boardGrid.getRowConstraints().add(row);
        }
    }
}
