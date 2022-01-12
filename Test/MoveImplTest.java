
import static org.junit.Assert.assertEquals;

import cs3500.model.Move;
import cs3500.model.MoveImpl;
import org.junit.Test;


/**
 * Test file for the cs3500.model.MoveImpl class which implements the cs3500.model.Move Interface.
 */
public class MoveImplTest {

  Move move = new MoveImpl(10, 15, 3, 56, 7, 30, 60, 250);


  @Test(expected = IllegalArgumentException.class)
  public void invalidTickTest() {
    Move badMove = new MoveImpl(-10, 15, 3, 56, 7, 30, 60, 250);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidWTest() {
    Move badMove = new MoveImpl(10, 15, 3, -56, 7, 30, 60, 250);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidHTest() {
    Move badMove = new MoveImpl(10, 15, 3, 56, -7, 30, 60, 250);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeRTest() {
    Move badMove = new MoveImpl(10, 15, 3, 56, 7, -30, 60, 250);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeGTest() {
    Move badMove = new MoveImpl(10, 15, 3, 56, 7, 30, -60, 250);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeBTest() {
    Move badMove = new MoveImpl(10, 15, 3, 56, 7, 30, 60, -250);
  }

  @Test(expected = IllegalArgumentException.class)
  public void tooBigRTest() {
    Move badMove = new MoveImpl(10, 15, 3, 56, 7, 300, 60, 250);
  }

  @Test(expected = IllegalArgumentException.class)
  public void tooBigGTest() {
    Move badMove = new MoveImpl(10, 15, 3, 56, 7, 30, 600, 250);
  }

  @Test(expected = IllegalArgumentException.class)
  public void tooBigBTest() {
    Move badMove = new MoveImpl(10, 15, 3, 56, 7, 30, 60, 2500);
  }

  @Test
  public void getTickTest() {
    assertEquals(10, move.getTick());
  }

  @Test
  public void getXTest() {
    assertEquals(15, move.getX());
  }

  @Test
  public void getYTest() {
    assertEquals(3, move.getY());
  }

  @Test
  public void getWTest() {
    assertEquals(56, move.getW());
  }

  @Test
  public void getHTest() {
    assertEquals(7, move.getH());
  }

  @Test
  public void getRTest() {
    assertEquals(30, move.getR());
  }

  @Test
  public void getGTest() {
    assertEquals(60, move.getG());
  }

  @Test
  public void getBTest() {
    assertEquals(250, move.getB());
  }

}
