package cs3500.animator;

import cs3500.model.Shape;
import java.io.IOException;
import java.util.List;

/**
 * The interface for the views used for the MarbleSolitaireGame. Holds the methods necessary to
 * accurately display and receive the pertinent information of a animation sent through.
 */
public interface IView {

  /**
   * Make the view visible. This is usually called after the view is constructed.
   *
   * @throws IOException          if file is invalid.
   * @throws InterruptedException if sleep or timer fails.
   */
  void makeVisible() throws IOException, InterruptedException;

  /**
   * Signal the view to draw itself.
   *
   * @throws IOException          if file is invalid.
   * @throws InterruptedException if sleep or timer fails.
   */
  void refresh() throws IOException, InterruptedException;

  /**
   * Sets the bounds for view and sets them.
   *
   * @param x the x-position of the frame.
   * @param y the y-position of the frame.
   * @param w the width of the frame.
   * @param h the height of the frame.
   * @throws IOException if append fails.
   */
  void getBounds(int x, int y, int w, int h) throws IOException;

  /**
   * Sets the list of shapes to be drawn in the animation.
   *
   * @param shapes list of shapes.
   */
  void getShapes(List<Shape> shapes);

}
