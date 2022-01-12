package cs3500.animator;

import cs3500.model.Shape;
import java.io.IOException;
import java.util.List;
import javax.swing.JFrame;

/**
 * Abstract class to initialize all the common functions and fields between each view.
 */
public abstract class AbstractView extends JFrame implements IView {

  protected Appendable output;
  protected List<Shape> shapes;
  protected int boundsX;
  protected int boundsY;
  protected int boundsW;
  protected int boundsH;
  protected int tempo;

  @Override
  public void getBounds(int x, int y, int w, int h) throws IOException {
    this.boundsX = x;
    this.boundsY = y;
    this.boundsW = w;
    this.boundsH = h;
  }

  @Override
  public void getShapes(List<Shape> shapes) { // can be abstracted
    this.shapes = shapes;
  }

  /**
   * Gets the max tick of the animation.
   *
   * @return an int of the max tick
   */
  protected int getMaxTick() {
    int maxTick = 0;
    for (Shape s : this.shapes) {
      int curr = s.getMoves().get(s.getMoves().size() - 1).getTick();
      if (curr > maxTick) {
        maxTick = curr;
      }
    }
    return maxTick;
  }


}
