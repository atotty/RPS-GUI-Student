package RPSGUI;

/*
 * This is the main class for RPS. Run this classes main method
 * to launch the application.
 *
 * Note: You do not need to modify anything in this file, but you may
 * find it useful to read this source code to better understand how the
 * program works.
 *
 */
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RPS extends Application {

    /*
     * The fields for this GUI application. The field game
     * represents the game logic of the RPS game.
     *
     * computerStrat can be changed here to modify how the
     * computer chooses moves. Change this field to test your
     * strategies.
     *
     * The button fields attach to the GUI buttons defined in
     * FXML_SOURCE.
     *
     * Finally, LOG_LEVEL can be changed here to change log
     * behavior in the application. Any messages passed to
     * Util.log  with a message level less than or equal to
     * LOG_LEVEL will print in console. Higher level messages are
     * ignored. The lowest log level is 0. By default, LOG_LEVEL is 1.
     *
     */
    RPSGame game = new RPSGame();
    Move computerMove;
    Strategy computerStrat = new RandomStrategy();

    private final String FXML_SOURCE = "/rps.fxml";
    private final String WINDOW_TITLE = "RPS";
    private static int LOG_LEVEL = 1; // modify for different log levels

    // graphics-related fields (do not modify)
    @FXML Button rockButton, paperButton, scissorsButton;
    @FXML ImageView playerImageView;
    @FXML ImageView computerImageView;
    @FXML Text playerScoreText;
    @FXML Text computerScoreText;
    @FXML MenuButton computerStratDropdown;
    // images from Sudowoodo on Getty Images
    Image rockImage = new Image("/rock.jpg");
    Image paperImage = new Image("/paper.jpg");
    Image scissorsImage = new Image("/scissors.jpg");
    Image blankImage = new Image("/blank.jpg");


    /**
     * Sets up the GUI and present window
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource(FXML_SOURCE));
        primaryStage.setResizable(false);
        primaryStage.setTitle(WINDOW_TITLE);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    /*
     * These methods handle button actions. When a player selects
     * a play, their move is set in the RPS game object and the
     * round is played.
     */
    @FXML private void handleRockButton() {
        Util.log(LOG_LEVEL, 3, "Debug: player move set to rock");
        game.setPlayerMove(Move.ROCK);
        playerImageView.setImage(rockImage);
        playRound();
    }
    @FXML private void handlePaperButton() {
        Util.log(LOG_LEVEL, 3, "Debug: player move set to paper");
        game.setPlayerMove(Move.PAPER);
        playerImageView.setImage(paperImage);
        playRound();
    }
    @FXML private void handleScissorsButton() {
        Util.log(LOG_LEVEL, 3, "Debug: player move set to scissors");
        game.setPlayerMove(Move.SCISSORS);
        playerImageView.setImage(scissorsImage);
        playRound();
    }
    @FXML private void handleRandomStratButton() {
        Util.log(LOG_LEVEL, 3, "Debug: computer strategy set to random");
        computerStrat = new RandomStrategy();
        computerStratDropdown.setText("Random");
    }
    @FXML private void handleMemoryStratButton() {
        Util.log(LOG_LEVEL, 3, "Debug: computer strategy set to memory");
        computerStrat = new MemoryStrategy();
        computerStratDropdown.setText("Memory");
    }
    @FXML private void handleMyStratButton() {
        Util.log(LOG_LEVEL, 3, "Debug: computer strategy set to my strat");
        computerStrat = new MyCustomStrategy();
        computerStratDropdown.setText("MyCustomStrat");
    }
    @FXML private void reset() {
        game.zeroScores();
        playerScoreText.setText("Player Score: " + game.getPlayerScore());
        computerScoreText.setText("Computer Score: " + game.getComputerScore());
    }
    @FXML private void close() {
        Platform.exit();
        System.exit(0);
    }


    /**
     * Move a round of the game by first selecting a computer
     * move based on the computer strategy. This method is called
     * by the button handlers.
     */
    @FXML private void playRound() {
        // select computer move
        computerMove = computerStrat.getMove();

        game.setComputerMove(computerMove);
        if (computerMove == Move.ROCK)
            computerImageView.setImage(rockImage);
        else if (computerMove == Move.PAPER)
            computerImageView.setImage(paperImage);
        else if (computerMove == Move.SCISSORS)
            computerImageView.setImage(scissorsImage);

        // play round
        int res = 0;
        try { res = game.playRound(); }
        catch (Exception e) { Util.log(LOG_LEVEL, 0, "Failed to play round.\n" + e); }

        // update score box
        playerScoreText.setText("Player Score: " + game.getPlayerScore());
        computerScoreText.setText("Computer Score: " + game.getComputerScore());

        // log the result of the round
        if (res < 0)
            Util.log(LOG_LEVEL, 2, "Computer wins this round!");
        else if (res > 0)
            Util.log(LOG_LEVEL, 2, "Player wins this round!");
        else if (res == 0)
            Util.log(LOG_LEVEL, 2, "Draw!");
    }


    /*
     * Launcher
     */
    public static void main(String[] args) {
        if (LOG_LEVEL < 0) { LOG_LEVEL = 0; }
        launch(args);
    }
}

