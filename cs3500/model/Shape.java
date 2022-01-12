package cs3500.model;

import java.util.List;

/**
 * Represents a new shape to be added to the view including the shape type, name, and initial
 * positions, that keeps tracks of any movements the shape must complete.
 */
public interface Shape {

  /**
   * Returns all changes (moves) the user made with this shape.
   *
   * @return List of moves that were made from the starting position of the shape.
   */
  List<Move> getMoves();

  /**
   * Removes the given move from the array list if present.
   *
   * @param m the move to be removed.
   */
  void removeMove(Move m);

  /**
   * Returns shape type that the given shape represents.
   *
   * @return the type of shape (rectangle/ellipse).
   */
  String getType();

  /**
   * Returns the name that the user gave to the shape object.
   *
   * @return the name of the shape.
   */
  String getName();

  /**
   * Returns the current position of the shape with the x-value first at int[0] and followed by the
   * y-value at int[1].
   *
   * @return the x and y positions of the shape.
   */
  int[] getPosition();

  /**
   * Returns the current size of the shape with the width first at int[0] followed by the height at
   * int[1].
   *
   * @return the width and height of the shape.
   */
  int[] getSize();

  /**
   * Returns the current color of the shape with the red-value first at int[0], followed by the
   * green-value at int[1], and the blue-value last at int[2].
   *
   * @return the rgb values of the shape.
   */
  int[] getColor();

  /**
   * Adds a new movement (change) to the move ArrayList for the given shape.
   *
   * @param move is the move being added to the shape.
   * @throws IllegalArgumentException if the tick is not valid
   * @throws IllegalArgumentException No changes are made to the shape
   */
  void addMove(Move move);

  /**
   * Inserts a move into the correct position of the moves list, in ascending order by tick.
   *
   * @param move the move to be added to the list.
   */
  void insertMove(Move move);

  /**
   * Finds a move of the shape and changes the rotation of that shape for that move and every move
   * after it.
   *
   * @param tick    the tick that the rotation is started
   * @param degrees the degrees the shape is rotated
   * @throws IllegalArgumentException if a move can't be found
   */
  void rotateMove(int tick, int degrees);


}
