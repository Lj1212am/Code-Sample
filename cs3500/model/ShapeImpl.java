package cs3500.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Models a shape in the view that has a specific position, size, and color, that can be modified
 * and is quantified using the type and name.
 */
public class ShapeImpl implements Shape {

  private final String name;
  private final String type;
  private final List<Move> moves;


  /**
   * Constructs a new shape to be added to the view using the type, position, size, color, and a
   * given name to list as (Can currently be a rectangle or ellipse).
   *
   * @param type the String type of shape.
   * @param name the String name quantifier.
   * @param move the cs3500.model.Move position that the shape originally begins at.
   * @throws IllegalArgumentException if a shape is not entered.
   */
  public ShapeImpl(String type, String name, Move move) {

    if (notShape(type)) {
      throw new IllegalArgumentException("Not a shape");
    }

    this.type = type;
    this.name = name;
    this.moves = new ArrayList<Move>() {
    };
    moves.add(move);

  }

  /**
   * Constructor for a shape that takes in a name and type.
   *
   * @param type type of shape.
   * @param name name of shape.
   */
  public ShapeImpl(String type, String name) {

    if (notShape(type)) {
      throw new IllegalArgumentException("Not a shape");
    }

    this.type = type;
    this.name = name;
    this.moves = new ArrayList<Move>() {
    };

  }

  /**
   * Returns whether the shape is a type of shape, such as a rectangle or ellipse. If not, it is
   * null or not a shape.
   *
   * @param type the shape type that it was created as.
   * @return whether the object is of a proper shape type.
   */
  private boolean notShape(String type) {
    if (type == null) {
      return true;
    }

    return !(type.equalsIgnoreCase("rectangle") ||
        type.equalsIgnoreCase("ellipse"));
  }

  @Override
  public void removeMove(Move m) {
    moves.removeIf(v -> v.equals(m));
  }

  @Override
  public List<Move> getMoves() {
    return new ArrayList<Move>(moves);
  }

  @Override
  public String getType() {
    return this.type;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public int[] getPosition() {
    List<Move> n = getMoves();
    int x = (n.get(n.size() - 1)).getX();
    int y = (n.get(n.size() - 1)).getY();
    return new int[]{x, y};
  }

  @Override
  public int[] getSize() {
    List<Move> n = getMoves();
    int w = (n.get(n.size() - 1)).getW();
    int h = (n.get(n.size() - 1)).getH();
    return new int[]{w, h};
  }

  @Override
  public int[] getColor() {
    List<Move> n = getMoves();
    int r = (n.get(n.size() - 1)).getR();
    int g = (n.get(n.size() - 1)).getG();
    int b = (n.get(n.size() - 1)).getB();
    return new int[]{r, g, b};
  }

  @Override
  public void addMove(Move move) {
    if (this.moves.size() > 0 && move.getTick() != 1) {
      validMotion(move.getTick(), move.getX(), move.getY(), move.getW(),
          move.getH(), move.getR(), move.getG(), move.getB(),
          this.moves.get(this.moves.size() - 1));
    }
    moves.add(move);
  }

  @Override
  public void insertMove(Move move) {
    if (this.moves.size() == 0) {
      moves.add(move);
    } else {
      for (int i = this.moves.size() - 1; i > -1; i--) {
        if (i == 0) {
          moves.add(i + 1, this.moves.get(i));
          moves.add(0, move);
        } else if (move.getTick() > this.moves.get(i).getTick()) {
          moves.add(i + 1, move);
          break;
        } else {
          moves.add(i + 1, this.moves.get(i));
        }
      }
    }
  }


  @Override
  public void rotateMove(int tick, int radians) {
    boolean present = false;
    for (Move move : this.moves) {

      //System.out.println("rotation: " + move.getRotation());
      if (move.getTick() >= tick) {
        move.rotate(radians);
        present = true;

      }
    }

    if (!present) {
      throw new IllegalArgumentException("Move can't be found to rotate");
    }

  }




  /**
   * Checks whether the given motion is valid or not.
   *
   * @param tick start tick time of the change.
   * @param x    new x-position of the shape.
   * @param y    new y-position of the shape.
   * @param w    new width of the shape.
   * @param h    new height of the shape.
   * @param r    new red color value of the shape.
   * @param g    new green color value of the shape.
   * @param b    new blue color value of the shape.
   * @param m    the previous move to check whether the new move is valid comparedd to that.
   * @throws IllegalArgumentException if the tick is not valid
   * @throws IllegalArgumentException No changes are made to the shape
   */
  private void validMotion(int tick, int x, int y, int w, int h, int r, int g, int b, Move m) {
    if (((x != m.getX()) || (y != m.getY()) || (w != m.getW()) || (h != m.getH()) ||
        (r != m.getR()) || (g != m.getG()) || (b != m.getB())) && (tick == m.getTick())) {
      throw new IllegalArgumentException("Teleporting in same tick");
    }

    if (tick < m.getTick()) {
      throw new IllegalArgumentException("Not a valid tick, trying to teleport");
    }
  }

  /**
   * Ensures that two Objects are equal, as they pertain to Shapes.
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
    return ((Shape) that).getName().equals(this.name);
  }

  /**
   * Outputs hashcode in int.
   *
   * @return the hashcode in name
   */
  @Override
  public int hashCode() {
    int prime = 31;
    return prime + Objects.hashCode(this.name);
  }


}
