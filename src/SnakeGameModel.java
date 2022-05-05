import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *  <Purpose of the file>
 * The Model of the Snake Game.
 */
public class SnakeGameModel extends JPanel  {

  /**
   * Set up the panel, set the source of apple and snake images
   */
  public SnakeGameModel() {
    // Load the image
    try {
      background = ImageIO.read(new File("/Users/chen/Downloads/backg5.png"));
      title = ImageIO.read(new File("/Users/chen/Downloads/title2.png"));
      apple_image = ImageIO.read(new File("/Users/chen/Downloads/heart.png"));
      snake_head_image = ImageIO.read(new File("/Users/chen/Downloads/snakehead.png"));
      snake_body_image = ImageIO.read(new File("/Users/chen/Downloads/snakebody.png"));
      snake2_head_image = ImageIO.read(new File("/Users/chen/Downloads/snakehead22.png"));
      snake2_body_image = ImageIO.read(new File("/Users/chen/Downloads/snakebody2.png"));
    } catch (Exception e) {
      System.out.println("url doesn't work");
    }
    rnd = new Random();
    setUp();
  }

  /**
   * This function initialize the location of snakes and apple for the first round
   */
  public void setUp(){
    gameoverP1 = false;
    gameoverP2 = false;
    player1Attack = false;
    player2Attack = false;
    score2 = 0;
    score1 = 0;
    // Init the location of the apple
    apple_loc = new Coordinate(200, 200);
    // Init the location of the snake.
    // It starts with head only
    // The list head is the snake head.
    // Assume the radius of each dot is 25.
    //Initialize snake1
    snake_loc = new ArrayList<>();
    snake_loc.add(new Coordinate(400, 400));
    snake_loc.add(new Coordinate(400 + dot_size, 400));
    snake_loc.add(new Coordinate(400 + 2 * dot_size, 400));

    //Initialize snake2
    snake2_loc = new ArrayList<>();
    snake2_loc.add(new Coordinate(300, 300));
    snake2_loc.add(new Coordinate(300 + dot_size, 300));
    snake2_loc.add(new Coordinate(300 + 2 * dot_size, 300));

    // initialize direction: snake1 to up, snake2 to left
    direction1 = Direction.UP;
    direction2 = Direction.LEFT;
  }

  public static void setDifficulty(int difficultyLevel){difficulty = difficultyLevel;
  }

  public static void setPlayerNumber(int numPlayer){
    playerNumber = numPlayer;
  }
  /**
   * Paint background ,title and scores components,
   * Paint apple and snakes in this function based on their real-time locations
   * Paint GameOver notice once the snake hits itself or the other snake
   * This is part of the View of Snake Game.
   * @param g a Graphics object
   */
  @Override
  protected void paintComponent(Graphics g) {
    // System.out.println("repainting");
    super.paintComponent(g);

    g.drawImage(title, 0, 0, this);
    g.drawImage(background, 0, title.getHeight(null), this);
    // paint the apple
    g.drawImage(apple_image, apple_loc.x, apple_loc.y, this);
    // paint snake1
    g.drawImage(snake_head_image, snake_loc.get(0).x, snake_loc.get(0).y, this);
    for (int i = 1; i < snake_loc.size(); i++) {
      g.drawImage(snake_body_image, snake_loc.get(i).x, snake_loc.get(i).y, this);
    }
    // paint snake2
    if(playerNumber == 2) {
      g.drawImage(snake2_head_image, snake2_loc.get(0).x, snake2_loc.get(0).y, this);
      for (int i = 1; i < snake2_loc.size(); i++) {
        g.drawImage(snake2_body_image, snake2_loc.get(i).x, snake2_loc.get(i).y, this);
      }
    }
    // paint the scores
    g.setColor(new Color(236,124,150));
    g.setFont(new Font("Arial Black",Font.BOLD,12));
    g.drawString("PLAYER 1",20,35);
    g.drawString("SCORE:  "+ score1 , 20,55);
    if(playerNumber == 2) {
      g.drawString("PLAYER 2", 620, 35);
      g.drawString("SCORE:  " + score2, 620, 55);
    }

    // paint the GameOver notice
    if(gameoverP1){
      g.setColor(Color.WHITE);
      g.setFont(new Font("Chalkduster", Font.BOLD, 50));
      g.drawString("Game Over!", 200, 400);
      g.setFont(new Font("Arial", Font.BOLD, 20));
      g.drawString("Oops! Player1 eats its body", 225, 450);
      g.drawString("Press SPACE to restart", 245, 480);
      timer.stop();
    }
    if(gameoverP2){
      g.setColor(Color.WHITE);
      g.setFont(new Font("Chalkduster", Font.BOLD, 50));
      g.drawString("Game Over!", 200, 400);
      g.setFont(new Font("Arial", Font.BOLD, 20));
      g.drawString("Oops! Player2 eats its body.", 225, 450);
      g.drawString("Press SPACE to restart", 245, 480);
      timer.stop();
    }
    if(player1Attack){
      g.setColor(Color.WHITE);
      g.setFont(new Font("Chalkduster", Font.BOLD, 50));
      g.drawString("Game Over!", 200, 400);
      g.setFont(new Font("Arial", Font.BOLD, 20));
      g.drawString("Oops! Player1 attacked Player2, Player1 loses.", 150, 450);
      g.drawString("Press SPACE to restart", 245, 480);
      timer.stop();
    }
    if(player2Attack){
      g.setColor(Color.WHITE);
      g.setFont(new Font("Chalkduster", Font.BOLD, 50));
      g.drawString("Game Over!", 200, 400);
      g.setFont(new Font("Arial", Font.BOLD, 20));
      g.drawString("Oops! Player2 attacked Player1, Player2 loses.", 150, 450);
      g.drawString("Press SPACE to restart", 245, 480);
      timer.stop();
    }
  }

  /**
   * This function operates how the snake move
   * @param snake_move a list of moving snake body dots location
   * @param player   an integer indicating the player, 1 is player1, 2 is player2
   */
  public void snakeMove(List<Coordinate> snake_move, int player) {
    // We update the snake's location.
    // Make it look like the snake is moving forward
    // Trick: move the tail to become the new head,
    // and keep other nodes the same

    // 1. chop off the tail
    Coordinate old_tail;
    if (snake_move.size() == 1) {
      old_tail = snake_move.get(0);
    } else {
      old_tail = snake_move.remove(snake_move.size() - 1);
    }
    // 2. get the loc of the old head
    int old_head_x = snake_move.get(0).x;
    int old_head_y = snake_move.get(0).y;
    // 3. add a new head. The loc of the new head
    // depends on the direction of movement
    int new_head_x = old_head_x;
    int new_head_y = old_head_y;

    // snake1 moves according to direction1, snake2 moves according to direction2
    Direction direction;
    if (player == 1) {
      direction = direction1;
    } else {
      direction = direction2;
    }

    if (direction == Direction.UP) {
      // up, if the snake touch the boundary, it will appear on the other side
      new_head_y -= dot_size;
      if (new_head_y < title.getHeight(null) + OptionView.panelFrame) {
        new_head_y =
            title.getHeight(null) + OptionView.panelHeight - OptionView.panelFrame - dot_size;
      }
    } else if (direction == Direction.DOWN) {
      // down, if the snake touch the boundary, it will appear on the other side
      new_head_y += dot_size;
      if (new_head_y
          > title.getHeight(null) + OptionView.panelHeight - OptionView.panelFrame - dot_size) {
        new_head_y = title.getHeight(null) + OptionView.panelFrame;
      }
    } else if (direction == Direction.LEFT) {
      // left, if the snake touch the boundary, it will appear on the other side
      new_head_x -= dot_size;
      if (new_head_x < OptionView.panelFrame) {
        new_head_x = OptionView.panelWidth - OptionView.panelFrame - dot_size;
      }
    } else if (direction == Direction.RIGHT) {
      // right, if the snake touch the boundary, it will appear on the other side
      new_head_x += dot_size;
      if (new_head_x >= OptionView.panelWidth - OptionView.panelFrame) {
        new_head_x = OptionView.panelFrame;
      }
    }

    snake_move.add(0, new Coordinate(new_head_x, new_head_y));
    System.out.println("Snake " + player + "Head Location: " + new_head_x + " " + new_head_y);

    ifEatApple(snake_move, player, old_tail);
    ifEatBody(snake_move,player);
    if(playerNumber == 2) {
      ifAttack(snake_move, player);
    }
  }

  /**
   * Check if the snake eats its body (if the head's coordinate is equal to any coordinate of its body)
   * if it is, the game will be over, this player loses
   * @param snake_move the moving snake's list of coordinates
   * @param player an integer indicating the player, 1 is player1, 2 is player2
   */
  public void ifEatBody(List<Coordinate> snake_move, int player){
        for(int i = snake_move.size() - 1 ; i > 0; i--){
          if ((snake_move.get(i).equals(snake_move.get(0)))) {
            String gameover = "Game Over!\n";
            if (player == 1) {
              gameoverP1 = true;
            } else {
              gameoverP2 = true;
            }
          }
        }
  }

  /**
   * Check if the snake eats the other snake (if the head's coordinate is equal to any coordinate of the other snake)
   * if it is, the game will be over, this player loses
   * @param snake_move the moving snake's list of coordinates
   * @param player an integer indicating the player, 1 is player1, 2 is player2
   */
  public void ifAttack(List<Coordinate> snake_move, int player) {
    List<Coordinate> snake_other;
    if (player == 1) {
      snake_other = snake2_loc;
    } else {
      snake_other = snake_loc;
    }
    for (int i = snake_other.size() - 1; i > 0; i--) {
      if ((snake_other.get(i).equals(snake_move.get(0)))) {
        if (player == 1) {
          player1Attack = true;
        } else {
          player2Attack = true;
        }
      }
    }
  }

  /**
   * Check if the snake eats the apple (if the head's coordinate is equal to any coordinate of the apple)
   * if it is, the size and the score of this player will be increased by 1，and the apple's location will be regenerated
   * @param snake_move the moving snake's list of coordinates
   * @param player an integer indicating the player, 1 is player1, 2 is player2
   * @param old_tail removed tail will be added back if the snake eats the apple
   */
  public void ifEatApple(List<Coordinate> snake_move, int player, Coordinate old_tail){
    // check whether the head overlaps with the apple
    if (snake_move.get(0).equals(apple_loc)) {
      // eating the apple.
      // Increase size by 1.
      // Added the removed tail back.
      snake_move.add(old_tail);
      // increase score by 1
      if (player == 1) {
        score1++;
      } else {
        score2++;
      }
      // also need to regenerate the apple's location
      regenApple();
    }

  }


  /**
   * This function regenerate a random location for the apple in panel range
   */
  private void regenApple() {
    // Random location within the panel.
    int new_x = rnd.nextInt(
        OptionView.panelFrame, OptionView.panelWidth - OptionView.panelFrame);
    int new_y = rnd.nextInt(OptionView.panelFrame + title.getHeight(null),
        title.getHeight(null) + OptionView.panelHeight - OptionView.panelFrame);
    // Round the location to dot_size.
    apple_loc = new Coordinate((new_x / dot_size) * dot_size, (new_y / dot_size) * dot_size);
    System.out.println(
        "Apple Location: " + (new_x / dot_size) * dot_size + " " + (new_y / dot_size) * dot_size);
  }

  /**
   * The game will restart once the game was over and the user pressed SPACE.
   */
  private void restart(){
    setUp();
    timer.start();
  }

  public void directionUpdated(int keyCode){
    if (keyCode == KeyEvent.VK_UP) {
      // up key is pressed
      direction1 = Direction.UP;
    } else if (keyCode == KeyEvent.VK_DOWN) {
      // down
      direction1 = Direction.DOWN;
    } else if (keyCode == KeyEvent.VK_LEFT) {
      // left
      direction1 = Direction.LEFT;
    } else if (keyCode == KeyEvent.VK_RIGHT) {
      // right
      direction1 = Direction.RIGHT;
    }

    if (keyCode == KeyEvent.VK_W) {
      // W key is pressed - UP
      direction2 = Direction.UP;
    } else if (keyCode == KeyEvent.VK_S) {
      // S - down
      direction2 = Direction.DOWN;
    } else if (keyCode == KeyEvent.VK_A) {
      // A - left
      direction2 = Direction.LEFT;
    } else if (keyCode == KeyEvent.VK_D) {
      // D - right
      direction2 = Direction.RIGHT;
    }

    if(keyCode == KeyEvent.VK_SPACE){
      restart();
    }
  }

  /**
   * @return A coordinate which is the apple's location for now
   */
  public Coordinate getApple_loc(){
    return apple_loc;
  }

  /**
   * @return A integer which is the score of player1
   */
  public int getScore1(){
    return score1;
  }

  /**
   * @return A integer which is the score of player2
   */
  public int getScore2(){
    return score2;
  }

  /**
   * @return true if the player 1 lost the game: it hits itself or the other snake. Otherwise, return false
   */
  public boolean ifGameOverP1(){
    return gameoverP1;
  }

  /**
   * @return true if the player 2 lost the game: it hits itself or the other snake. Otherwise, return false.
   */
  public boolean ifGameOverP2(){
    return gameoverP2;
  }

  /**
   * @return true if player 1 hits the other snake. Otherwise, return false
   */
  public boolean ifAttackP1(){
    return player1Attack;
  }

  /**
   * @return  true if player 2 hits the other snake. Otherwise, return false
   */
  public boolean ifAttackP2(){
    return player2Attack;
  }

  /**
   * @return the Direction Enum for player1's direction for now
   */
  public Direction getDirection1(){
    return direction1;
  }


  private Coordinate apple_loc;
  private final int dot_size = 25;
  private Image apple_image;
  private Image background;
  private Image title;
  private Image snake_head_image;
  private Image snake_body_image;
  private Image snake2_head_image;
  private Image snake2_body_image;

  private int score1;
  private int score2;
  private Direction direction1;
  private Direction direction2;
  private Random rnd;
  private boolean gameoverP1 = false;
  private boolean gameoverP2 = false;
  private boolean player1Attack = false;
  private boolean player2Attack = false;
  List<Coordinate> snake_loc;
  List<Coordinate> snake2_loc;
  public Timer timer;
  public static int difficulty;
  public static int playerNumber;
  public static int timerEasy = 400;   // if it's easy mode, timer is 400 ms
  public static int timerHard = 150;   // if it's hard mode, timer is 150 ms
}
