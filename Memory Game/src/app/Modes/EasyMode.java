package app.Modes;

import app.Card;
import app.Game;
import app.Status;
import javafx.scene.paint.Color;

import java.util.Timer;
import java.util.TimerTask;

public class EasyMode implements Mode {
    public void revealAllCards(Card[][] cards)
    {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                for (int i = 0; i < cards.length; i++) {
                    for (int j = 0; j < cards.length; j++) {
                        cards[i][j].flip();
                    }
                }
            }
        };
        timer.schedule(timerTask, 1000);

        for (int i = 0; i < cards.length; i++) {
            for (int j = 0; j < cards.length; j++) {
                cards[i][j].flip();
            }
        }
    }

    @Override
    public void play(Game game) {
        int[][] boardCoords = game.scanBoard();
        int y1 = boardCoords[0][0];
        int y2 = boardCoords[1][0];
        int x1 = boardCoords[0][1];
        int x2 = boardCoords[1][1];

        if (game.areCardsEqual(y1, x1, y2, x2))
        {
            game.cards[y1][x1].setEmpty();
            game.cards[y2][x2].setEmpty();
            game.cards[y1][x1].setOnMouseClicked(null);
            game.cards[y2][x2].setOnMouseClicked(null);
            game.players[game.currentPlayerCount].pushScore();
            game.players[game.currentPlayerCount].refreshInfos();
            if (game.isGameFinished())
            {
                game.createRanking();
                game.board.showEndScreen(game.ranking);
            }
            return;
        }
        else
        {
            //Remove current player mark
            game.players[game.currentPlayerCount].setTextFill(Color.rgb(50, 50, 50));
            //Set mark on next one
            game.players[game.incrementCurrentPlayerCount(game.currentPlayerCount)].setTextFill(Color.GREEN);
            //Creating a timer, which is used to set the time before the cards flip
            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask()
            {
                @Override
                public void run()
                {
                    game.cards[y1][x1].flip();
                    game.cards[y2][x2].flip();
                }
            };
            timer.schedule(timerTask, game.CARD_DECAY);
        }
        game.currentPlayerCount = game.incrementCurrentPlayerCount(game.currentPlayerCount);
    }
}
