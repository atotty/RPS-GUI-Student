import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RPS extends JFrame {
    RPSGame game = new RPSGame();
    Move computerMove;
    Strategy computerStrat = new RandomStrategy();

    private static int LOG_LEVEL = 3; // modify for different log levels

    BufferedImage rockImage;
    BufferedImage paperImage;
    BufferedImage scissorsImage;
    BufferedImage blankImage;

    JLabel playerImage;
    JLabel computerImage;
    JTextArea playerScore;
    JTextArea computerScore;

    // containers
    JMenuBar menuBar = new JMenuBar();
    JPanel centerContainer = new JPanel();
    JPanel scoreContainer = new JPanel();

    public RPS() {
        super("RPS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.WHITE);
        setSize(930, 550);

        // menu
        JMenu menu1 = new JMenu("File");
        JMenuItem menu11 = new JMenuItem("Reset");
        menu11.addActionListener(e -> {
            game.zeroScores();
            playerScore.setText("Player Score: " + game.getPlayerScore());
            computerScore.setText("Computer Score: " + game.getComputerScore());
        });
        JMenuItem menu12 = new JMenuItem("Close");
        menu12.addActionListener(e -> System.exit(0));
        menu1.add(menu11);
        menu1.add(menu12);
        menuBar.add(menu1);

        // buttons for r, p, and s
        JButton rockButton = new JButton("Rock");
        rockButton.addActionListener(e -> {
            Util.log(LOG_LEVEL, 3, "Debug: player move set to rock");
            game.setPlayerMove(Move.ROCK);
            playerImage.setIcon(new ImageIcon(rockImage));
            playRound();
        });
        JButton paperButton = new JButton("Paper");
        paperButton.addActionListener(e -> {
            Util.log(LOG_LEVEL, 3, "Debug: player move set to paper");
            game.setPlayerMove(Move.PAPER);
            playerImage.setIcon(new ImageIcon(paperImage));
            playRound();
        });
        JButton scissorsButton = new JButton("Scissors");
        scissorsButton.addActionListener(e -> {
            Util.log(LOG_LEVEL, 3, "Debug: player move set to scissors");
            game.setPlayerMove(Move.SCISSORS);
            playerImage.setIcon(new ImageIcon(scissorsImage));
            playRound();
        });
        JPanel buttonContainer = new JPanel();
        buttonContainer.add(BorderLayout.WEST, rockButton);
        buttonContainer.add(BorderLayout.CENTER, paperButton);
        buttonContainer.add(BorderLayout.EAST, scissorsButton);

        // load rps images
        try {
            rockImage = ImageIO.read(new File("resources/rock.jpg"));
            paperImage = ImageIO.read(new File("resources/paper.jpg"));
            scissorsImage = ImageIO.read(new File("resources/scissors.jpg"));
            blankImage = ImageIO.read(new File("resources/blank.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        playerImage = new JLabel(new ImageIcon(blankImage));
        computerImage = new JLabel(new ImageIcon(blankImage));

        // score boxes
        playerScore = new JTextArea("Player Score: " + game.getPlayerScore());
        computerScore = new JTextArea("Computer Score: " + game.getComputerScore());
        scoreContainer.add(playerScore,0);
        scoreContainer.add(computerScore,1);

        // comp strat dropdown
        String[] strats = {"Random", "Memory", "MyCustomStrat"};
        JComboBox computerStratDropdown = new JComboBox(strats);
        computerStratDropdown.addActionListener(e -> {
            String selectedStrat = (String)computerStratDropdown.getSelectedItem();
            if (selectedStrat.equals("Random")) {
                Util.log(LOG_LEVEL, 3, "Debug: computer strategy set to random");
                computerStrat = new RandomStrategy();
            }
            else if (selectedStrat.equals("Memory")) {
                Util.log(LOG_LEVEL, 3, "Debug: computer strategy set to memory");
                computerStrat = new MemoryStrategy();
            }
            else if (selectedStrat.equals("MyCustomStrat")) {
                Util.log(LOG_LEVEL, 3, "Debug: computer strategy set to my strat");
                computerStrat = new MyCustomStrategy();
            }
            else {
                Util.log(LOG_LEVEL, 3, "Debug: failed to set computer strategy");
                computerStrat = null;
            }
        });

        // containerize stuff
        JPanel imagesContainer = new JPanel();
        imagesContainer.setLayout(new BoxLayout(imagesContainer, BoxLayout.Y_AXIS));
        imagesContainer.add(computerImage);
        imagesContainer.add(playerImage);
        imagesContainer.setAlignmentY(Component.LEFT_ALIGNMENT);

        centerContainer.setLayout(new BoxLayout(centerContainer, BoxLayout.Y_AXIS));
        centerContainer.add(computerStratDropdown);
        centerContainer.add(imagesContainer);
        centerContainer.add(buttonContainer);
//        centerContainer.add(BorderLayout.PAGE_START, computerStratDropdown);
//        centerContainer.add(BorderLayout.CENTER, imagesContainer);
//        centerContainer.add(BorderLayout.PAGE_END, buttonContainer);

        for(Component c : centerContainer.getComponents())
            c.setBackground(Color.WHITE);

        // add components to window
        this.getContentPane().add(BorderLayout.NORTH, menuBar);
        this.getContentPane().add(BorderLayout.CENTER, centerContainer);
        this.getContentPane().add(BorderLayout.SOUTH, scoreContainer);

        // set colors
        for (Component c : this.getContentPane().getComponents())
            c.setBackground(Color.WHITE);
    }


    /**
     * Advance a round of the game by first selecting a computer
     * move based on the computer strategy. This method is called
     * by the button handlers.
     */
    private void playRound() {
        // select computer move
        computerMove = computerStrat.getMove();

        game.setComputerMove(computerMove);
        if (computerMove == Move.ROCK)
            computerImage.setIcon(new ImageIcon(rockImage));
        else if (computerMove == Move.PAPER)
            computerImage.setIcon(new ImageIcon(paperImage));
        else if (computerMove == Move.SCISSORS)
            computerImage.setIcon(new ImageIcon(scissorsImage));

        // play round
        int res = 0;
        try { res = game.playRound(); }
        catch (Exception e) { Util.log(LOG_LEVEL, 0, "Failed to play round.\n" + e); }

        // update score box
        playerScore.setText("Player Score: " + game.getPlayerScore());
        computerScore.setText("Computer Score: " + game.getComputerScore());

        // log the result of the round
        if (res < 0)
            Util.log(LOG_LEVEL, 2, "Computer wins this round!");
        else if (res > 0)
            Util.log(LOG_LEVEL, 2, "Player wins this round!");
        else if (res == 0)
            Util.log(LOG_LEVEL, 2, "Draw!");
    }

    public static void main(String[] args) {
        RPS rps = new RPS();
        rps.setVisible(true);
    }
}
