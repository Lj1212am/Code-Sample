package cs3500.animator;

import cs3500.model.ModelView;
import cs3500.model.ModelViewImpl;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * The Controller that takes in the file which has the instructions for the animation, build the
 * model for the animation, and sends in the pertinent values to the given View.
 */
public class MVCController extends AnimationReader implements IMVCController {

  private final IView view;
  private final ModelView model;

  /**
   * Constructor for the Controller, Takes in the instructions for the animation in the file And the
   * view which will display the animation.
   *
   * @param in   the File which holds the instructions for the animation
   * @param view the view which will display the animation
   * @throws FileNotFoundException When a file is not found the exception is thrown
   */
  public MVCController(File in, IView view) throws FileNotFoundException {

    this.view = view;
    AnimationBuilder<ModelView> build = new ModelViewImpl.Builder();
    this.model = AnimationReader.parseFile(new FileReader(in), build);
  }

  @Override
  public void playGame() throws IOException, InterruptedException {
    view.getBounds(model.getBoundX(), model.getBoundY(), model.getBoundW(), model.getBoundH());
    view.getShapes(model.getShapes());
    view.makeVisible();
  }
}
