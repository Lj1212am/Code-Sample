import static org.junit.Assert.assertTrue;

import cs3500.animator.IMVCController;
import cs3500.animator.IView;
import cs3500.animator.MVCController;
import cs3500.animator.SVGView;
import cs3500.animator.TextView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.Test;

/**
 * Tests that all inputs sent through the controller work as expected.
 */
public class ControllerTest extends AbstractViewTest {

  @Test(expected = FileNotFoundException.class)
  public void wrongFileTextView() throws IOException, InterruptedException {
    IView view = new TextView("file.txt");
    IMVCController controller = new MVCController(new File("whatisthis"), view);
    controller.playGame();
  }

  @Test
  public void sendSVGThrough() throws IOException, InterruptedException {
    IView view = new SVGView("file.svg", 100);
    IMVCController controller = new MVCController(new File("toh-3.txt"), view);
    controller.playGame();

    assertTrue(fileChecker("SvgFile.svg", "file.svg"));
  }

  @Test
  public void sendTextThrough() throws IOException, InterruptedException {
    IView view = new TextView("file.txt");
    IMVCController controller = new MVCController(new File("toh-3.txt"), view);
    controller.playGame();

    assertTrue(fileChecker("fileCheck.txt", "file.txt"));

  }

}
