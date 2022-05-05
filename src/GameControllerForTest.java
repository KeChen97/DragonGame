import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * <Purpose of the file>
 * This controller is just for JUnit Test, since we need pass the difficulty and playerNumber into it without manually click
 */
public class GameControllerForTest implements IGameController, ActionListener, KeyListener {
  JFrame frame;
  OptionView optionView;
  SnakeGameModel model;
  int difficulty;
  int playerNumber;

  /**
   * Constructor of controller just for test
   * it will create a new OptionWindow, which is the View user can input options of the game
   * @param difficulty an integer got from the tester input
   * @param playerNumber an integer got from the tester input
   */
  public GameControllerForTest(int difficulty, int playerNumber) {
    this.optionView = new OptionView();
    this.difficulty = difficulty;
    this.playerNumber = playerNumber;
  }

  @Override
  public void gameStart() {
    this.frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(OptionView.frameWidth,OptionView.frameHeight);
    // Create the game model
    model = new SnakeGameModel();
    model.setPreferredSize(new Dimension(OptionView.panelWidth, OptionView.panelHeight));
    frame.add(model);
    frame.setTitle("Snake Game by kk");
    frame.setVisible(true);
    optionView.setVisible(false);

   model.setDifficulty(this.difficulty);
   model.setPlayerNumber(this.playerNumber);

    // set up the alarm, which fires periodically (16 ms == 60fps).
    // the time period depends on the difficulty level
    // Every time it fires, we update the snake's location and repaint it.
    if (model.difficulty == 1) {
      model.timer = new Timer(model.timerEasy, this); // if it's easy mode, timer is 400 ms
    } else if (model.difficulty == 2) {
      model.timer = new Timer(model.timerHard, this); // if it's hard mode, timer is 150 ms
    } else {
      throw new IllegalStateException("Please choose difficulty level");
    }

    model.timer.start();
    System.out.println("Print this if the game start");
    // register this panel as the keyboard event listener.
    model.setFocusable(true);
    model.addKeyListener(this);

  }

  /**
   * This function control the move of snake
   * @param e an ActionEvent object, processing individual mouse movements and mouse clicks
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    model.snakeMove(model.snake_loc, 1);
    if(model.playerNumber == 2) {
      model.snakeMove(model.snake2_loc, 2);
    }
    // Call repaint, which further invokes `paintComponent`.
    model.repaint();
  }

  /**
   * keyTyped is fired when a key is pressed that can be converted into a unicode character. The
   * basic idea is that keyTyped is used to find characters that are typed. For this game, we don't
   * need any keycode except up/down/left/right to control the snake, so that it will do nothing if
   * receive keyTyped
   *
   * @param e a KeyEvent object
   */
  @Override
  public void keyTyped(KeyEvent e) {
    // do nothing
  }

  /**
   * This function read the keyboard action, it is used for obtain raw key presses. For this game,
   * the direction will change based on when which key (up/down/left/right) is received from the
   * keyboard,
   * This is part of the Controller of the Snake Game
   * @param e a KeyEvent object
   */
  @Override
  public void keyPressed(KeyEvent e) {
    System.out.println("Print this if the keyPressed listened");
    int keyCode = e.getKeyCode();
    System.out.println(keyCode);
    model.directionUpdated(keyCode);
  }

  /**
   * For this game, do nothing if keyboard is released.
   * @param e a KeyEvent object
   */
  @Override
  public void keyReleased(KeyEvent e) {
    // do nothing
  }
}

