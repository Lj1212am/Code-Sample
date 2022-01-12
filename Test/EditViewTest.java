import static org.junit.Assert.assertEquals;

import cs3500.animator.AbstractView;
import cs3500.animator.AnimationReader;
import cs3500.animator.ISmarterView;
import cs3500.controller.EditController;
import cs3500.model.Shape;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.junit.Test;

/**
 * Test class for the EditView object. Tests whether the Edit controller correctly passes in
 * Listener input. And that the model parameters are still being passed in correctly.
 */
public class EditViewTest extends AnimationReader {


  @Test
  public void KeyListenerTest() throws IOException {
    Appendable stringBuilder = new StringBuilder();
    ISmarterView mockView = new MockView(stringBuilder);
    EditController controller = new EditController(new File("toh-3.txt"),
        mockView);

    KeyEvent keyS = new KeyEvent((Component) mockView, KeyEvent.KEY_PRESSED,
        System.currentTimeMillis(), 0, KeyEvent.VK_S, 's');

    KeyEvent keyR = new KeyEvent((Component) mockView, KeyEvent.KEY_PRESSED,
        System.currentTimeMillis(), 0, KeyEvent.VK_R, 'r');

    KeyEvent keyA = new KeyEvent((Component) mockView, KeyEvent.KEY_PRESSED,
        System.currentTimeMillis(), 0, KeyEvent.VK_A, 'a');

    KeyEvent keyD = new KeyEvent((Component) mockView, KeyEvent.KEY_PRESSED,
        System.currentTimeMillis(), 0, KeyEvent.VK_D, 'd');

    KeyEvent keyL = new KeyEvent((Component) mockView, KeyEvent.KEY_PRESSED,
        System.currentTimeMillis(), 0, KeyEvent.VK_L, 'l');

    KeyEvent keySpace = new KeyEvent((Component) mockView, KeyEvent.KEY_PRESSED,
        System.currentTimeMillis(), 0, 49, ' ');

    KeyEvent keyBad = new KeyEvent((Component) mockView, KeyEvent.KEY_PRESSED,
        System.currentTimeMillis(), 0, KeyEvent.VK_C, 'C');

    JSlider ce = new JSlider();
    ce.setValueIsAdjusting(true);

    ChangeEvent sliderMoved = new ChangeEvent(ce);

    ActionEvent ae = new ActionEvent(mockView, 0, "Submit");

    assertEquals("setBounds\nsetShapes\n", stringBuilder.toString());

    controller.keyTyped(keyS);

    assertEquals("setBounds\nsetShapes\nstart\n", stringBuilder.toString());

    controller.keyTyped(keyR);

    assertEquals("setBounds\nsetShapes\nstart\nrestart\n", stringBuilder.toString());

    controller.keyTyped(keyA);

    assertEquals("setBounds\nsetShapes\nstart\nrestart\nspeedSlower\n", stringBuilder.toString());

    controller.keyTyped(keyD);

    assertEquals("setBounds\nsetShapes\nstart\nrestart\nspeedSlower\nspeedFaster\n",
        stringBuilder.toString());

    controller.keyTyped(keyL);

    assertEquals("setBounds\nsetShapes\nstart\nrestart\nspeedSlower\nspeedFaster\nLoop\n",
        stringBuilder.toString());

    controller.keyTyped(keyBad);

    assertEquals("setBounds\nsetShapes\nstart\nrestart\nspeedSlower\nspeedFaster\nLoop\n",
        stringBuilder.toString());

    controller.keyTyped(keySpace);

    assertEquals("setBounds\nsetShapes\nstart\nrestart\nspeedSlower\nspeedFaster\nLoop\nPause\n",
        stringBuilder.toString());

    controller.actionPerformed(ae);

    assertEquals(
        "setBounds\nsetShapes\nstart\nrestart\nspeedSlower\nspeedFaster\nLoop\nPause\nbutton\n",
        stringBuilder.toString());

    controller.stateChanged(sliderMoved);

    assertEquals(
        "setBounds\nsetShapes\nstart\nrestart\nspeedSlower\nspeedFaster\nLoop\nPause"
            + "\nbutton\nslider\n",
        stringBuilder.toString());
  }


  /**
   * Mock View used to receive the sent KeyEvents and ActionEvents and record whether they have been
   * received.
   */
  private static class MockView extends AbstractView implements ISmarterView {

    private final Appendable stringBuilder;
    private boolean faster;
    private JButton button;
    private JSlider slider;

    /**
     * Constructor Which takes in an object that records the methods reached.
     *
     * @param stringBuilder records the methods reached
     */
    public MockView(Appendable stringBuilder) {
      this.stringBuilder = stringBuilder;
      this.faster = false;
      this.button = new JButton();
      this.slider = new JSlider();

    }


    @Override
    public void start() {
      try {
        this.stringBuilder.append("start\n");
      } catch (IOException ioe) {
        ioe.getMessage();
      }
    }

    @Override
    public void refocus() {
      return;
    }

    @Override
    public void restart() {
      try {
        this.stringBuilder.append("restart\n");
      } catch (IOException ioe) {
        ioe.getMessage();
      }

    }

    @Override
    public void pause() {
      try {
        this.stringBuilder.append("Pause\n");
      } catch (IOException ioe) {
        ioe.getMessage();
      }

    }

    @Override
    public void loop() {
      try {
        this.stringBuilder.append("Loop\n");
      } catch (IOException ioe) {
        ioe.getMessage();
      }
    }

    @Override
    public void speed(boolean faster) {
      if (this.faster) {
        try {
          this.stringBuilder.append("speedFaster\n");
          this.faster = !this.faster;
        } catch (IOException ioe) {
          ioe.getMessage();
        }
      } else {
        try {
          this.stringBuilder.append("speedSlower\n");
          this.faster = !this.faster;
        } catch (IOException ioe) {
          ioe.getMessage();
        }
      }

    }

    @Override
    public void setKeyListener(KeyListener listener) {
      return;
    }

    @Override
    public void setButtonListener(ActionListener listener) {
      this.button.addActionListener(listener);
    }

    @Override
    public void changeShapes() {
      try {
        this.stringBuilder.append("button\n");
      } catch (IOException ioe) {
        ioe.getMessage();
      }

    }

    @Override
    public void setChangeListener(ChangeListener listener) {
      this.slider.addChangeListener(listener);
      //this.slider.fireStateChanged();
    }

    @Override
    public void sliderAnimation(int currentTick) {
      try {
        this.stringBuilder.append("slider\n");
      } catch (IOException ioe) {
        ioe.getMessage();
      }
    }

    @Override
    public int getTick() {
      return 0;
    }

    @Override
    public void makeVisible() {
      try {
        this.stringBuilder.append("makeVisible\n");
      } catch (IOException ioe) {
        ioe.getMessage();
      }

    }

    @Override
    public void refresh() {

      try {
        this.stringBuilder.append("Refresh\n");
      } catch (IOException ioe) {
        ioe.getMessage();
      }

    }

    @Override
    public void getShapes(List<Shape> shapes) {
      try {
        this.stringBuilder.append("setShapes\n");
      } catch (IOException ioe) {
        ioe.getMessage();
      }

    }

    @Override
    public void getBounds(int x, int y, int w, int h) {
      try {
        this.stringBuilder.append("setBounds\n");
      } catch (IOException ioe) {
        ioe.getMessage();
      }

    }

  }
}
