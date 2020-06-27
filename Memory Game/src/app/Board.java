package app;

import javafx.geometry.Insets;
import javafx.scene.layout.*;

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
