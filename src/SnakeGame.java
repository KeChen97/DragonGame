/**
 *  <Purpose of the file>
 * Author : KK 4/29/2022
 * Source code: Snake Game Starter https://northeastern.instructure.com/courses/103018/files/14752164?wrap=1
 * This is for demonstrating this snake game.
 *
 * The snake game rules:
 * 1. User can choose different difficulty mode : easy or hard.
 *    Snakes will move faster in hard mode.
 * 2. User can choose number of players : one or two.
 *    One player's goal is eating more apples (head touches apple) and get more scores. Two players need to compete for the apple.
 * 3. Game will be over if the snake's head touches its body.
 * 4. The snake will lose the game if it hits another snake.
 * 5. User can press SPACE to restart the game if game is over.
 *
 */
public class SnakeGame {

  public static void main(String[] args) {
    SnakeGameControllerWindow.optionFrame = new SnakeGameControllerWindow();
  }
}

