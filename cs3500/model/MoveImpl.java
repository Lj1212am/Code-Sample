package cs3500.model;

/**
 * Models a movement of a shape on the view by taking in the changes in position, size, or color
 * that are needed to be made to the shape at the given tick time.
 */
public class MoveImpl implements Move {

  private final int tick;
  private final int x;
  private final int y;
  private final int w;
  private final int h;
  private final int r;
  private final int g;
  private final int b;
  private int rotationRadians;

  /**
   * Constructs a new movement for the shape using the inputted changes.
   *
   * @param tick tick time of the movement.
   * @param x    new x-position of the shape.
   * @param y    new y-position of the shape.
   * @param w    new width of the shape.
   * @param h    new height of the shape.
   * @param r    new red color value of the shape.
   * @param g    new green color value the shape.
   * @param b    new blue color value of the shape.
   * @throws IllegalArgumentException if color values aren't valid or w and h are negative or tick
   *                                  is negative.
   */
  public MoveImpl(int tick, int x, int y, int w, int h, int r, int g, int b) {
    if (tick < 0 || w < 0 || h < 0 || r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
      throw new IllegalArgumentException("Size cannot be negative and color not between 0 - 255");
    }

    this.tick = tick;
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.r = r;
    this.g = g;
    this.b = b;
    this.rotationRadians = 0;
  }


  @Override
  public int getTick() {
    return this.tick;
  }

  @Override
  public int getX() {
    return this.x;
  }

  @Override
  public int getY() {
    return this.y;
  }

  @Override
  public int getW() {
    return this.w;
  }

  @Override
  public int getH() {
    return this.h;
  }

  @Override
  public int getR() {
    return this.r;
  }

  @Override
  public int getG() {
    return this.g;
  }

  @Override
  public int getB() {
    return this.b;
  }


  /**
   * Ensures that two Objects are equal, as they pertain to Moves.
   *
   * @param that Another un-identified Object
   * @return a boolean as to whether the Objects are equal
   */
  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (!(that instanceof Move)) {
      return false;
    }
    return (((Move) that).getTick() == this.tick
        && ((Move) that).getX() == this.x
        && ((Move) that).getY() == this.y
        && ((Move) that).getW() == this.w
        && ((Move) that).getH() == this.h
        && ((Move) that).getR() == this.r
        && ((Move) that).getG() == this.g
        && ((Move) that).getB() == this.b);
  }


  @Override
  public int hashCode() {
    return Integer.hashCode(getTick());
  }


  @Override
  public void rotate(int radians) {
    this.rotationRadians = radians;

  }


  @Override
  public int getRotation() {
    return this.rotationRadians;
  }


}
