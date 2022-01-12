import static cs3500.animator.AnimationReader.parseFile;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import cs3500.animator.IMVCController;
import cs3500.animator.IView;
import cs3500.animator.MVCController;
import cs3500.animator.TextView;
import cs3500.model.ModelView;
import cs3500.model.ModelViewImpl;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import org.junit.Test;

/**
 * Test class for the SVG view.  Makes sure each type of contructor works, that exceptions are
 * thrown as expected, and each method used by the IView interface works correctly.
 */
public class TextViewTest extends AbstractViewTest {

  @Test(expected = IOException.class)
  public void wrongFileTextView() throws IOException, InterruptedException {
    IView view = new TextView("file.txt");
    IMVCController controller = new MVCController(new File("whatisthis"), view);
    controller.playGame();
  }

  @Test
  public void fileOutputConstructorTextView() throws IOException, InterruptedException {
    IView view = new TextView("file.txt");
    ModelView model = parseFile(new FileReader("toh-3.txt"), new ModelViewImpl.Builder());

    view.getBounds(model.getBoundX(), model.getBoundY(), model.getBoundW(), model.getBoundH());
    view.getShapes(model.getShapes());
    view.makeVisible();

    assertTrue(fileChecker("file.txt", "fileCheck.txt"));


  }

  @Test
  public void emptyConstructorTextView() throws IOException, InterruptedException {
    final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setOut(new PrintStream(outContent));
    IView view = new TextView();
    IMVCController controller = new MVCController(new File("toh-3.txt"), view);
    controller.playGame();

    String[] lines = outContent.toString().split("\n");
    assertEquals(108, lines.length);

    String lastMsg = String.join("\n",
        Arrays.copyOfRange(lines, lines.length - 3, lines.length - 2));
    assertEquals(
        "motion disk3 161\t445\t240\t110\t30\t0\t255\t0\t\t161\t445\t240\t110\t30\t0\t255\t0",
        lastMsg);
  }


  @Test
  public void testEmptyFileTextView() throws IOException, InterruptedException {
    IView view = new TextView("file.txt");
    File f = new File("empty.txt");
    ModelView model = parseFile(new FileReader("empty.txt"), new ModelViewImpl.Builder());
    view.getBounds(model.getBoundX(), model.getBoundY(), model.getBoundW(), model.getBoundH());
    view.getShapes(model.getShapes());
    view.refresh();
    FileWriter write = new FileWriter("empty.txt");
    write.write("canvas 300\t300\t300\t300");
    write.close();
    assertTrue(fileChecker("file.txt", "empty.txt"));
  }

}