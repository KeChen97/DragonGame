import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *  <Purpose of the file>
 * Includes one option window as View of Snake Game, and three listeners as Controller of Snake Game.
 */
public class OptionView extends JFrame {
  public static JComboBox playerBox;
  public static JComboBox difficultyModeBox;
  public static int difficultyLevel;
  public static int numPlayer;
  private JButton start;

  public static JFrame optionFrame;
  public static final int frameWidth = 700;  // width of the frame container
  public static final int frameHeight = 800; // height of the frame container
  public static final int panelWidth = 700;  // 600 + panelframe * 2
  public static final int panelHeight = 700; // 600 + panelframe * 2
  public static final int panelFrame = 50; // the dark pink frame of the panel view

  /**
   * This class is for showing a "option window" for this snake game. User can choose different modes for this game.
   * The mode user chooses decides how's this game like:
   * 1. easy (slow) or hard (fast)
   * 2. one player or two player
   * Once the "Start" button is clicked, the game will begin.
   * This is part of the View of Snake Game.
   */
  public OptionView() {
    super();
    final BorderLayout borderLayout = new BorderLayout();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    getContentPane().setLayout(borderLayout);
    setTitle("Snake Game by kk");
    setSize(300, 170); //the size of this window
    setLocation(550, 350);

    final JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());
    mainPanel.setBorder(new EmptyBorder(1, 1, 10, 10));
    getContentPane().add(mainPanel); // add this main panel to window

    final JPanel centerPanel = new JPanel();
    final GridLayout gridLayout = new GridLayout(3, 2);
    centerPanel.setLayout(gridLayout);
    mainPanel.add(centerPanel); // add this center panel to main panel

    // two space holders
    JLabel emptyLabel1 = new JLabel();
    centerPanel.add(emptyLabel1);
    JLabel emptyLabel12 = new JLabel();
    centerPanel.add(emptyLabel12);

    // the difficulty mode option box
    final JLabel difficultyModelabel = new JLabel();
    difficultyModelabel.setHorizontalAlignment(SwingConstants.CENTER);
    difficultyModelabel.setPreferredSize(new Dimension(0, 0));
    difficultyModelabel.setMinimumSize(new Dimension(0, 0));
    centerPanel.add(difficultyModelabel);
    difficultyModelabel.setText("Game Difficulty: ");
    difficultyModeBox = new JComboBox();
    difficultyModeBox.addItem("--Options--");
    difficultyModeBox.addItem("Easy");
    difficultyModeBox.addItem("Hard");
    // This ItemListener reads the user's choice for difficulty
    difficultyModeBox.addItemListener(new difficultyListener());
    centerPanel.add(difficultyModeBox);

		// the number of player option box
    final JLabel playerLabel = new JLabel();
    playerLabel.setHorizontalAlignment(SwingConstants.CENTER);
    centerPanel.add(playerLabel);
    playerLabel.setText("Number of Player:");
    playerBox = new JComboBox();
    playerBox.addItem("--Options--");
    playerBox.addItem("One");
    playerBox.addItem("Two");
    // This ItemListener reads the user's choice for player number
    playerBox.addItemListener(new playerNumListener());
    centerPanel.add(playerBox);

		// add a new panel at the bottom
    final JPanel bottomPanel = new JPanel();
		bottomPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    start = new JButton();
		start.addActionListener(new StartAction());
		start.setText("Start");
    bottomPanel.add(start);

    setVisible(true);
    setResizable(false);
  }
}






