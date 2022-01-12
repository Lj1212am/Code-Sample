package cs3500.model;

import java.util.List;

/**
 * Represents a model for the view that can construct different shapes on the screen, change the
 * shape (such as position, size, and color), and keep track of the movements made.
 */
public interface ModelView {

  /**
   * Creates a new movement to change the position, size, or color of the given given shape at the
   * tick time, using the name of the object as an identifier.
   *
   * @param name given shape with a unique name identifier.
   * @param move the move being attributed to the shape
   * @throws IllegalArgumentException see addMove(cs3500.model.Move m) documentation
   * @throws IllegalArgumentException if shape with given name is not present
   */
  void motion(String name, Move move);

  /**
   * Removes a motion from a specific shape if it exists.
   *
   * @param name the name of the shape.
   * @param move the move to be removed.
   */
  void removeMotion(String name, Move move);

  /**
   * Returns a list of all motions that the shape makes.
   *
   * @param name the name of the shape;
   * @return the list of all moves the shape makes.
   * @throws IllegalArgumentException if the shape does not exist.
   */
  List<Move> getAllMotions(String name);

  /**
   * Adds a new shape to the view using the shape's starting parameters (position, size, color).
   *
   * @param type the type of new shape.
   * @param name the name of the new shape.
   * @throws IllegalArgumentException if a shape of that name already exists
   * @throws IllegalArgumentException if the passed in shape is null
   */
  void addShape(String type, String name);

  /**
   * Removes a shape from the view using the shape's name as the identifier.
   *
   * @param name String name of the new shape.
   */
  void removeShape(String name);

  /**
   * Returns the list of shapes in the modelView.
   *
   * @return an Arraylist of shapes in the modelView
   */
  List<Shape> getShapes();

  /**
   * Sets the bounds for the canvas size.
   *
   * @param x x-position of the panel.
   * @param y y-position of the panel.
   * @param w width of the panel.
   * @param h height of the panel.
   */
  void setBounds(int x, int y, int w, int h);


  /**
   * Returns the x-most position of the ModelView.
   *
   * @return int of the x-most  position
   */
  int getBoundX();

  /**
   * Returns the y-most position of the ModelView.
   *
   * @return int of the y-most position
   */
  int getBoundY();

  /**
   * Returns the width of the ModelView.
   *
   * @return int of the width
   */
  int getBoundW();

  /**
   * Returns the height of the ModelView.
   *
   * @return int of the height
   */
  int getBoundH();

  /**
   * Rotates a given shape at a specific time by the specified number of degrees.
   *
   * @param name    the name of the shape
   * @param tick    the time the shape is being rotated
   * @param degrees the degrees that the shape is being rotated
   */
  void rotate(String name, int tick, int degrees);


}
