package app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*************************
 * Name: Thomas Platzer, Sebastion Fekete, Asim Ahmedowski, Gregor Hangler
 * Erstellt am: 05.06.2020
 * Zuletzt geändert am: 27.06.2020
 * Beschreibung: Memory Card Game in einer JavaFX Anwendung
 */

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        //Get the players, board size and mode
        InitializationStage initStage = new InitializationStage();
        initStage.showAndWait();

        //Start a game
        Game game = new Game(initStage.getNumberOfPlayers(), initStage.getBoardSize(), initStage.getMode());
        game.run();

        Scene scene = new Scene(game.getBoard());

        primaryStage.setResizable(false);
        primaryStage.setTitle("MEMORY GAME");
        primaryStage.setScene(scene);

        //We have to do that because of the child threads, which are created in this program
        primaryStage.setOnCloseRequest(e ->
        {
            Platform.exit();
            System.exit(0);
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
