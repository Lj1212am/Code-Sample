import static cs3500.animator.AnimationReader.parseFile;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import cs3500.animator.IMVCController;
import cs3500.animator.IView;
import cs3500.animator.MVCController;
import cs3500.animator.SVGView;
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
 * Test class for the SVG view.  Makes sure each type of constructor works, that exceptions are
 * thrown as expected, and each method used by the IView interface works correctly.
 */
public class SVGTest extends AbstractViewTest {


  @Test(expected = IOException.class)
  public void wrongFileSVGView() throws IOException, InterruptedException {
    IView view = new SVGView("file.svg", 100);
    IMVCController controller = new MVCController(new File("whatisthis.svg"), view);
    controller.playGame();
  }

  @Test
  public void testEmptyFileSVGView() throws IOException, InterruptedException {
    IView view = new SVGView("file.svg", 100);
    File f = new File("empty.svg");
    ModelView model = parseFile(new FileReader("emptyCheck.txt"), new ModelViewImpl.Builder());
    view.getBounds(model.getBoundX(), model.getBoundY(), model.getBoundW(), model.getBoundH());
    view.getShapes(model.getShapes());
    view.refresh();
    FileWriter write = new FileWriter("empty.svg");
    write.write("<svg width=\"600\" height=\"600\" version=\"1.1\"\n"
        + "\txmlns=\"http://www.w3.org/2000/svg\">\n"
        + "\n"
        + "</svg>");
    write.close();
    assertTrue(fileChecker("file.svg", "empty.svg"));
  }

  @Test
  public void OutputTxtFileTest() throws IOException, InterruptedException {
    IView view = new SVGView("file.txt", 100);
    ModelView model = parseFile(new FileReader("toh-3.txt"), new ModelViewImpl.Builder());

    view.getBounds(model.getBoundX(), model.getBoundY(), model.getBoundW(), model.getBoundH());
    view.getShapes(model.getShapes());
    view.makeVisible();


    assertTrue(fileChecker("file.txt", "SvgText.txt"));
  }

  @Test
  public void fileOutputConstructorSVGView() throws IOException, InterruptedException {
    IView view = new SVGView("file.svg", 100);
    ModelView model = parseFile(new FileReader("toh-3.txt"), new ModelViewImpl.Builder());

    view.getBounds(model.getBoundX(), model.getBoundY(), model.getBoundW(), model.getBoundH());
    view.getShapes(model.getShapes());
    view.makeVisible();


    assertTrue(fileChecker("file.svg", "fileCheck.svg"));
  }

  @Test
  public void tempoConstructorSVGView() throws IOException, InterruptedException {
    final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setOut(new PrintStream(outContent));
    IView view = new SVGView(100);
    IMVCController controller = new MVCController(new File("toh-5.txt"), view);
    controller.playGame();

    String[] lines = outContent.toString().split("\n");
    assertEquals(122, lines.length);

    String lastMsg = String.join("\n",
        Arrays.copyOfRange(lines, lines.length - 3, lines.length - 2));
    assertEquals("</rect>", lastMsg);
  }

  @Test
  public void emptyConstructorSVGView() throws IOException, InterruptedException {
    final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    System.setOut(new PrintStream(outContent));
    IView view = new SVGView();
    IMVCController controller = new MVCController(new File("toh-5.txt"), view);
    controller.playGame();

    String[] lines = outContent.toString().split("\n");
    assertEquals(122, lines.length);

    String lastMsg = String.join("\n",
        Arrays.copyOfRange(lines, lines.length - 5, lines.length - 4));
    assertEquals(
        "\t<animate attributeType=\"xml\" begin=\"528000ms\" dur=\"10000ms\" "
            + "attributeName=\"y\" from=\"50\" to=\"240\" fill=\"freeze\" />",
        lastMsg);
  }

  @Test
  public void rotationTest() throws IOException, InterruptedException {
    IView view = new SVGView("rotationCheck.svg", 100);
    IMVCController controller = new MVCController(new File("buildings.txt"), view);
    controller.playGame();


    assertTrue(fileChecker("rotationCheck.svg", "rotation.svg"));

    IView ellipseView = new SVGView("UntitledCheck.svg", 100);
    IMVCController ellipseController = new MVCController(new File("Untitled.txt"), ellipseView);
    ellipseController.playGame();

    assertTrue(fileChecker("UntitledCheck.svg", "rotationEclipse.svg"));
  }


}
