/*
 *  Enum data type for representing the three possible moves a player
 *  of RPS can make. Do not modify this section.
 */
enum Move {
    ROCK,
    PAPER,
    SCISSORS
}

/*
 *  This class represents a game of RPS with multiple rounds. It stores
 *  moves for the player and the computer, and can play a round based on
 *  those moves.
 *
 *  You should complete this class to provide functionality for the
 *  application.
 */
public class RPSGame {
    private Move playerMove = null;
    private Move computerMove = null;
    private int playerScore, computerScore;

    // Mutators and Accessors
    public void setPlayerMove(Move move) {
        playerMove = move;
    }
    public void setComputerMove(Move move) {
        computerMove = move;
    }
    public int getPlayerScore() {
        return playerScore;
    }
    public int getComputerScore() {
        return computerScore;
    }
    public void zeroScores() {
        playerScore = 0;
        computerScore = 0;
    }

    /**
     * This method plays a round based on the moves defined in playerMove
     * and computerMove. If either of the values is not set to a valid move,
     * the method throws an error.
     *
     * Note: Error creation and handling has been done for you. Your work is
     * related to the logic of RPS.
     *
     * @return -1 if player loses, 1 if player wins, 0 if draw
     * @throws Exception
     */
    public int playRound() throws Exception {
        // check play can happen
        if (playerMove == null && computerMove == null)
            throw new IllegalStateException("Neither player selected a move");
        else if (playerMove == null)
            throw new IllegalStateException("The player did not select a move");
        else if (computerMove == null)
            throw new IllegalStateException("The computer did not select a move");

        // play can happen, so find winner

        /*
         *  TODO: STUDENT WORK
         *
         *  Complete the method below to return the correct value. Follow the rules
         *  of RPS and return -1 if the computer wins, 1 if the player wins, and 0
         *  if the players draw.
         *
         *  You should also track both player and computer score so scores can been
         *  seen in the final program.
         *
         */




        // END STUDENT WORK

        return 0;
    }
}
