import static org.junit.Assert.assertEquals;

import cs3500.animator.AnimationReader;
import cs3500.model.ModelView;
import cs3500.model.ModelViewImpl;
import cs3500.model.ModelViewImpl.Builder;
import cs3500.model.Move;
import cs3500.model.MoveImpl;
import cs3500.model.Shape;
import cs3500.model.ShapeImpl;
import org.junit.Test;

/**
 * Test class for the builder object. Ensure the assumed model is made.
 */
public class BuilderTest extends AnimationReader {

  Builder builder = new ModelViewImpl.Builder();

  @Test
  public void testBuilder() {

    ModelView modelView = new ModelViewImpl();
    Move move = new MoveImpl(1, 2, 3, 4, 5, 6, 7, 8);
    Move move1 = new MoveImpl(5, 6, 7, 8, 9, 0, 1, 2);
    Shape shape = new ShapeImpl("rectangle", "R");
    shape.addMove(move);
    shape.addMove(move);
    shape.addMove(move1);
    modelView.addShape("rectangle", "R");

    builder.declareShape("R", "rectangle");
    builder.addMotion("R", 1, 2, 3, 4, 5, 6, 7, 8,
        1, 2, 3, 4, 5, 6, 7, 8);
    builder.addKeyframe("R", 5, 6, 7, 8, 9, 0, 1, 2);
    builder.setBounds(300, 300, 300, 300);
    ModelView model = builder.build();

    assertEquals(model, model);
  }
}
