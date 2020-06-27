package app;

import javafx.scene.control.*;

public class Player extends Label implements Comparable<Player>
{
    private int nr;
    private int score;

    public Player(int nr)
    {
        this.nr = nr;
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        setStyle("-fx-font-size: 2.5em;");
        score = 0;
    }

    public void displayInfos()
    {
        setText("Player " + nr + "\nScore: " + score);
    }

    public void refreshInfos()
    {
        setText("");
        displayInfos();
    }

    public void pushScore()
    {
        score++;
    }

    public int getScore()
    {
        return score;
    }

    @Override
    public int compareTo(Player o)
    {
        if (this.getScore() == o.getScore())
        {
            return 0;
        }
        if (this.getScore() > o.getScore())
        {
            return -1;
        }
        return 1;
    }
}
