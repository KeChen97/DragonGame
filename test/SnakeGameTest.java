import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import org.junit.Test;

/**
 * Snake Game test
 */
public class SnakeGameTest {
  private SnakeGameModel model;
  private OptionView controlWindow;
  private Robot robot;

  @org.junit.Before
  public void setUp() throws Exception {
  //  controlWindow = new SnakeGameControllerWindow();
    JFrame frame = new JFrame();
    frame.setSize(700,800);
    model = new SnakeGameModel();
    model.setPreferredSize(new Dimension(700,700));
    frame.add(model);
    frame.setVisible(true);
    robot = new Robot();
  }

  /**
   * test for the original data
   */
  @Test
  public void originalDataTest() {
    Coordinate apple = new Coordinate(200,200);
    assertTrue(apple.equals(model.getApple_loc()));
    assertEquals(0,model.getScore1());
    assertEquals(0,model.getScore2());
    assertFalse(model.ifGameOverP1());
    assertFalse(model.ifGameOverP2());
    assertFalse(model.ifAttackP1());
    assertFalse(model.ifAttackP2());
    assertEquals(Direction.UP,model.getDirection1());
  }

  @Test
  public void directionTest() throws AWTException, InterruptedException {
    robot.keyPress(KeyEvent.VK_LEFT);
    model.directionUpdated(KeyEvent.VK_LEFT);
 //   System.out.println(KeyEvent.VK_LEFT);
//    long start = System.nanoTime();
//    long end = start = TimeUnit.SECONDS.toNanos(100);
//    try{Thread.sleep(400);} catch (InterruptedException e) {
//      e.printStackTrace();
//    }
//    while(System.nanoTime() < end){
//    }
//    Thread.sleep(100);
//    robot.keyRelease(KeyEvent.VK_LEFT);
    Thread.sleep(200);
    assertEquals(Direction.LEFT,model.getDirection1());
  }

  /**
   * test if the score increase after snake's head overlap the apple
   */
  @Test
  public void snakeEatsApple(){
    List<Coordinate> snake_loc = snake_loc = new ArrayList<>();
    snake_loc.add(new Coordinate(200, 200));
    snake_loc.add(new Coordinate(200 + 25, 200));
    model.snake_loc = snake_loc;
    assertEquals(1,model.getScore1());

  }

}