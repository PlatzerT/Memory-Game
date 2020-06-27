package app;

import app.Modes.*;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.*;

public class Game implements EventHandler<MouseEvent>
{
    public Board board;
    public Player[] players;
    public Card[][] cards;
    public Player[] ranking;

    public int CARD_DECAY = 500; //ms
    private Mode mode;

    private int revealedCards;
    private int repeatingCards;
    public int currentPlayerCount;

    public Game(int numberOfPlayers, int boardSize, Modes modeType)
    {
        board = new Board(boardSize);
        players = new Player[numberOfPlayers];
        cards = new Card[boardSize][boardSize];
        ranking = new Player[numberOfPlayers];
        selectMode(modeType);
        repeatingCards = (boardSize * boardSize) / 2;
        revealedCards = 0;
        currentPlayerCount = 0;
    }

    private void selectMode(Modes modeType)
    {
        switch (modeType)
        {
            case EASY:
                mode = new EasyMode();
                break;

            case HARD:
                mode = new HardMode();
                break;

            case SWITCH:
                mode = new SwitchMode();
                break;
        }
    }

    public void run()
    {
        //initPlayers
        initPlayers();
        //initCards()
        initCards();
        //shuffleCards
        cards = shuffleCards();
        //fillBoardWithCards
        board.fillBoardWithCards(cards);
        //fillPlayers
        board.fillPlayers(players);
        if (mode instanceof EasyMode)
        {
            ((EasyMode) mode).revealAllCards(cards);
        }
        //Mark first player
        players[currentPlayerCount].setTextFill(Color.GREEN);
    }

    public void createRanking()
    {
        List<Player> playerList = Arrays.asList(players);
        Collections.sort(playerList);
        int counter = 0;
        int points = 0;
        for (int i = 0; i < playerList.size(); i++)
        {
            if (points != playerList.get(i).getScore())
            {
                counter++;
            }
            playerList.get(i).setText("[" + counter + "] " + playerList.get(i).getText());
            ranking[i] = playerList.get(i);
            ranking[i].setTextFill(Color.GREEN);
            points = playerList.get(i).getScore();
        }
    }

    public int incrementCurrentPlayerCount(int currentPlayerCount)
    {
        if ((currentPlayerCount+1) >= players.length)
        {
            return 0;
        }
        return currentPlayerCount+1;
    }

    public boolean areCardsEqual(int y1, int x1, int y2, int x2)
    {
        return cards[y1][x1].getCardId() == cards[y2][x2].getCardId();
    }

    public int[][] scanBoard()
    {
        int[][] boardCoords = new int[2][2];
        int rowCounter = 0;
        for (int i = 0; i < cards.length; i++)
        {
            for (int j = 0; j < cards.length; j++)
            {
                if (Status.REVEALED == cards[i][j].getStatus() && rowCounter != 2)
                {
                    boardCoords[rowCounter][0] = i;
                    boardCoords[rowCounter][1] = j;
                    rowCounter++;
                }
            }
        }
        return boardCoords;
    }

    public boolean isGameFinished()
    {
        for (int i = 0; i < cards.length; i++)
        {
            for (int j = 0; j < cards.length; j++)
            {
                if (cards[i][j].getStatus() == Status.COVERED)
                {
                    return false;
                }
            }
        }
        return true;
    }

    public void initPlayers()
    {
        for (int i = 0; i < players.length; i++)
        {
            players[i] = new Player((i+1));
            players[i].displayInfos();
        }
    }

    public Card[][] shuffleCards()
    {
        ArrayList<Card> toShuffle = new ArrayList<>();
        Card[][] result = new Card[cards.length][cards.length];
        for (int i = 0; i < cards.length; i++)
        {
            toShuffle.addAll(Arrays.asList(cards[i]));
        }
        Collections.shuffle(toShuffle, new Random(System.nanoTime()));
        int counter = 0;
        for (int i = 0; i < result.length; i++)
        {
            result[i] = (toShuffle.subList(counter, (counter+cards.length))).toArray(result[i]);
            counter += cards.length;
        }
        return result;
    }

    public void initCards()
    {
        int counter = 1;
        for (int i = 0; i < cards.length; i++)
        {
            for (int j = 0; j < cards.length; j++)
            {
                if (counter == (repeatingCards+1))
                {
                    counter = 1;
                }
                //If board size is odd, set last element to emptyImage
                if (i == cards.length-1 && j == cards.length-1 && cards.length % 2 != 0)
                {
                    cards[i][j] = new Card(null, 0);
                    break;
                }
                cards[i][j] = new Card("../images/cards/card" + counter + ".png", counter);
                cards[i][j].setOnMouseClicked(this::handle);
                counter++;
            }
        }

    }

    public Board getBoard()
    {
        return board;
    }

    public boolean isSameCard(Card card)
    {
        return card.getStatus() == Status.REVEALED;
    }

    @Override
    public void handle(MouseEvent mouseEvent)
    {
        if (!isSameCard(((Card)mouseEvent.getSource())))
        {
            revealedCards++;
            ((Card)mouseEvent.getSource()).flip();
        }
        if (revealedCards == 2)
        {
            mode.play(this);
            revealedCards = 0;
        }
    }
}
