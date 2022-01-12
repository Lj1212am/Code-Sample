
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import cs3500.model.Move;
import cs3500.model.MoveImpl;
import cs3500.model.Shape;
import cs3500.model.ShapeImpl;
import java.util.ArrayList;
import org.junit.Test;


/**
 * Test file for the cs3500.model.ShapeImpl class which implements the cs3500.model.Shape
 * Interface.
 */
public class ShapeImplTest {

  Move move = new MoveImpl(10, 15, 3, 56, 7, 30, 60, 250);
  Shape shape = new ShapeImpl("Rectangle", "R", move);
  Shape ellipse = new ShapeImpl("Ellipse", "R", move);

  @Test(expected = IllegalArgumentException.class)
  public void notAShapeTest() {
    Shape shape = new ShapeImpl("blue", "R", move);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullShapeTest() {
    Shape shape = new ShapeImpl(null, "R", move);
  }

  @Test(expected = IllegalArgumentException.class)
  public void teleportMotion() {
    Move badMove = new MoveImpl(10, 10, 3, 56, 7, 30, 60, 250);
    shape.addMove(badMove);
  }

  @Test(expected = IllegalArgumentException.class)
  public void backInTimeMotion() {
    Move badMove = new MoveImpl(9, 10, 3, 40, 7, 30, 60, 250);
    shape.addMove(badMove);
  }

  @Test
  public void movesTest() {
    ArrayList<Move> shapes = new ArrayList<Move>() {
    };

    Move move1 = new MoveImpl(11, 20, 3, 56, 7, 30, 60, 250);
    Move move2 = new MoveImpl(15, 20, 3, 56, 7, 35, 60, 250);
    Move move3 = new MoveImpl(20, 2, 10, 30, 17, 55, 65, 50);

    shape.addMove(move1);
    shape.addMove(move2);
    shape.addMove(move3);

    assertEquals(4, shape.getMoves().size());

    shapes.add(move);
    shapes.add(move1);
    shapes.add(move2);
    shapes.add(move3);

    assertArrayEquals(shapes.toArray(), shape.getMoves().toArray());

  }

  @Test
  public void getTypeTest() {
    assertEquals("Rectangle", shape.getType());
  }

  @Test
  public void getNameTest() {
    assertEquals("R", shape.getName());
  }

  @Test
  public void getSizeTest() {
    int[] size = new int[]{56, 7};
    assertArrayEquals(size, shape.getSize());
  }

  @Test
  public void getPositionTest() {
    int[] size = new int[]{15, 3};
    assertArrayEquals(size, ellipse.getPosition());
  }

  @Test
  public void getColorTest() {
    int[] size = new int[]{30, 60, 250};
    assertArrayEquals(size, shape.getColor());
  }

}
