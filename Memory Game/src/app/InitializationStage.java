package app;

import app.Modes.Mode;
import app.Modes.Modes;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class InitializationStage extends Stage
{
    private int nop; // equivalent to numberOfPlayers
    private int bs; //equivalent to boardSize
    private Modes inputMode;

    private final int MAX_BOARD_SIZE = 8;
    private final int MIN_BOARD_SIZE = 3;

    private final int MAX_NOP = 4;
    private final int MIN_NOP = 1;

    public InitializationStage()
    {
        GridPane pane = new GridPane();
        pane.setPrefSize(400.0, 100.0);
        pane.setVgap(15.0);
        pane.setHgap(10.0);
        pane.setPadding(new Insets(10));

        //Number of players input
        Label labelNOP = new Label("How many players? (" + MIN_NOP + "-" + MAX_NOP + ") ");
        TextField inputNOP = new TextField();
        inputNOP.setStyle("-fx-border-color: transparent;");
        inputNOP.setPromptText("Enter number of players...");

        //Board size input
        Label labelBS = new Label("How big should the board be? (" + MIN_BOARD_SIZE + "-" + MAX_BOARD_SIZE + ") ");
        TextField inputBS = new TextField();
        inputBS.setStyle("-fx-border-color: transparent;");
        inputBS.setPromptText("Enter board size...");

        //Mode selection
        Label labelMode = new Label("Select a mode: ");
        ComboBox modes = new ComboBox();
        modes.getItems().add(Modes.EASY);
        modes.getItems().add(Modes.HARD);
        modes.getItems().add(Modes.SWITCH);

        //Set default value: HARD
        modes.setValue(Modes.HARD);

        Button submit = new Button("Submit");
        submit.setOnAction(e ->
        {
            if (checkInput(inputNOP.getText(), inputBS.getText()))
            {
                nop = Integer.parseInt(inputNOP.getText());
                bs = Integer.parseInt(inputBS.getText());
                inputMode = (Modes) modes.getValue();
                hide();
            }
            else
            {
                inputNOP.setStyle("-fx-border-color: red;");
                inputBS.setStyle("-fx-border-color: red;");
            }
        });

        pane.add(labelNOP, 0, 0);
        pane.add(inputNOP, 1, 0);
        pane.add(labelBS, 0, 1);
        pane.add(inputBS, 1, 1);
        pane.add(labelMode, 0, 2);
        pane.add(modes, 1, 2);
        pane.add(submit, 1, 3);

        Scene welcomeScene = new Scene(pane);
        setScene(welcomeScene);
        setResizable(false);
    }

    //Check if the input in the two TextFields is valid
    public boolean checkInput(String NOP, String BS)
    {
        boolean condition;
        int intNOP;
        int intBS;
        if (!NOP.equals("") && !BS.equals(""))
        {
            condition = true;
        }
        else
        {
            return false;
        }
        try
        {
            intNOP = Integer.parseInt(NOP);
            intBS = Integer.parseInt(BS);
        } catch (NumberFormatException e)
        {
            return false;
        }
        if (intNOP < MIN_NOP || intNOP > MAX_NOP || intBS < MIN_BOARD_SIZE || intBS > MAX_BOARD_SIZE)
        {
            return false;
        }
        return condition;
    }

    public int getNumberOfPlayers()
    {
        return nop;
    }

    public int getBoardSize()
    {
        return bs;
    }

    public Modes getMode()
    {
        return inputMode;
    }
}
