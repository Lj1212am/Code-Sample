package cs3500.controller;

import cs3500.animator.AnimationReader;
import cs3500.animator.ISmarterView;
import cs3500.model.ModelView;
import cs3500.model.ModelViewImpl;
import cs3500.model.ModelViewImpl.Builder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * The edit controller that takes in the file which has the instructions for the animation, build
 * the model for the animation and the speed for the animation, and sends in the pertinent values to
 * the new edit view.
 */
public class EditController implements IEditController, ActionListener, KeyListener,
    ChangeListener {

  private final ISmarterView editView;

  /**
   * Constructs a new edit controller that builds a model using the input file and creates a new
   * edit view with the correct shapes list, bounds, and the edit view.
   *
   * @throws IOException if file is not readable.
   */
  public EditController(File in, ISmarterView view) throws IOException {
    Builder builder = new ModelViewImpl.Builder();
    ModelView model = AnimationReader.parseFile(new FileReader(in), builder);

    this.editView = view;
    this.editView
        .getBounds(model.getBoundX(), model.getBoundY(), model.getBoundW(), model.getBoundH());
    this.editView.getShapes(model.getShapes());
    this.editView.setChangeListener(this);
    editView.setKeyListener(this);
    configureButtonListener();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String s = e.getActionCommand();
    if (s.equals("Submit")) {
      editView.changeShapes();
    }
    editView.refocus();
  }

  /**
   * Adds the button listener to the edit view.
   */
  private void configureButtonListener() {
    editView.setButtonListener(this);
  }

  @Override
  public void playGame() throws IOException, InterruptedException {
    editView.makeVisible();
  }

  @Override
  public void keyTyped(KeyEvent e) {
    switch (e.getKeyChar()) {
      case ' ':
        editView.pause();
        break;
      case 's':
        editView.start();
        break;
      case 'r':
        editView.restart();
        break;
      case 'l':
        editView.loop();
        break;
      case 'a':
        editView.speed(false);
        break;
      case 'd':
        editView.speed(true);
        break;
      default:
        break;
    }

  }

  @Override
  public void keyPressed(KeyEvent e) {
    keyTyped(e);
  }

  @Override
  public void keyReleased(KeyEvent e) {
    keyTyped(e);
  }

  @Override
  public void stateChanged(ChangeEvent e) {

    JSlider source = (JSlider) e.getSource();

    if (source.getValueIsAdjusting()) {
      editView.sliderAnimation(source.getValue());

    }
    editView.refocus();
  }
}
