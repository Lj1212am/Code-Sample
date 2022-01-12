
import static org.junit.Assert.assertEquals;

import cs3500.model.ModelView;
import cs3500.model.ModelViewImpl;
import cs3500.model.Move;
import cs3500.model.MoveImpl;
import cs3500.model.Shape;
import cs3500.model.ShapeImpl;
import org.junit.Test;

/**
 * Test file for the cs3500.model.ModelViewImpl class which implements the cs3500.model.ModelView
 * Interface.
 */
public class ModelViewImplTest {

  Move move = new MoveImpl(10, 15, 3, 56, 10, 30, 60, 220);
  Move move1 = new MoveImpl(11, 20, 3, 56, 7, 30, 60, 250);
  Move move2 = new MoveImpl(15, 20, 3, 56, 7, 35, 60, 250);
  Move move3 = new MoveImpl(20, 2, 10, 30, 17, 55, 65, 50);
  Move move4 = new MoveImpl(0, 15, 3, 56, 7, 30, 60, 250);
  ModelView modelView = new ModelViewImpl();

  Shape rect = new ShapeImpl("Rectangle", "R", move);
  Shape ellipse = new ShapeImpl("Ellipse", "E", move4);


  @Test(expected = IllegalArgumentException.class)
  public void nullAddShapeTest() {
    modelView.addShape(null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullNameAddShapeTest() {
    modelView.addShape("rectangle", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullTypeAddShapeTest() {
    modelView.addShape(null, "R");
  }

  @Test(expected = IllegalArgumentException.class)
  public void multipleSameAddShapeTest() {
    modelView.addShape(rect.getType(), rect.getName());
    modelView.addShape(rect.getType(), rect.getName());
  }

  @Test(expected = IllegalArgumentException.class)
  public void motionNoShapeTest() {
    modelView.addShape(rect.getType(), rect.getName());
    modelView.motion("E", move);
  }

  @Test
  public void addShapesTest() {
    modelView.addShape(rect.getType(), rect.getName());
    modelView.addShape(ellipse.getType(), ellipse.getName());
    modelView.motion("R", move);
    modelView.motion("E", move4);

    assertEquals(2, modelView.getShapes().size());
  }

  @Test
  public void removeShapeTest() {
    modelView.addShape(rect.getType(), rect.getName());
    modelView.addShape(ellipse.getType(), ellipse.getName());

    modelView.removeShape("R");

    assertEquals(1, modelView.getShapes().size());
  }

}
