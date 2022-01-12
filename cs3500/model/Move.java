package cs3500.model;

/**
 * Interface that represents a cs3500.model.Move which contains the parameters needed to move or
 * change a shape within a view model, including the tick time of the change, the position, the size
 * change, and the color.
 */
public interface Move {

  /**
   * Returns the tick time that the given move will occur on.
   *
   * @return tick time of the move.
   */
  int getTick();

  /**
   * Returns the x-position the user wants the shape to move to.
   *
   * @return new x-position of the move.
   */
  int getX();

  /**
   * Returns the y-position the user wants the shape to move to.
   *
   * @return new y-position of the move.
   */
  int getY();

  /**
   * Returns the new width the user wants the shape to be.
   *
   * @return width of the shape.
   */
  int getW();

  /**
   * Returns the new height the user wants the shape to be.
   *
   * @return height of the shape.
   */
  int getH();

  /**
   * Returns the new red value of the user inputted color of the shape.
   *
   * @return red rgb value of the shape color.
   */
  int getR();

  /**
   * Returns the new green value of the user inputted color of the shape.
   *
   * @return green rgb value of the shape color.
   */
  int getG();

  /**
   * Returns the new blue value of the user inputted color of the shape.
   *
   * @return blue rgb value of the shape color.
   */
  int getB();

  /**
   * Rotates a given move by the degrees passed in.
   *
   * @param degrees The amount of degrees the move should be rotated
   */
  void rotate(int degrees);

  /**
   * Get's the degrees that the move is being rotated.
   *
   * @return an int of the degrees of the move's rotation
   */
  int getRotation();

}
