import java.awt.HeadlessException;
import java.awt.Panel;
import javax.swing.JFrame;

/**
 *  <Purpose of the file>
 * It is the game window as View of Snake Game
 */
public class GameView extends JFrame {

  /**
   * This class is for showing the game window for this snake game.
   * @throws HeadlessException
   */
  public GameView() throws HeadlessException {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(OptionView.frameWidth,OptionView.frameHeight);
  }

}


