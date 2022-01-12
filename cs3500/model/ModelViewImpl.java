package cs3500.model;

import cs3500.animator.AnimationBuilder;
import cs3500.animator.AnimationReader;
import java.util.ArrayList;
import java.util.List;


/**
 * Model for the view that constructs a scene of shape objects that can be moved (changed) using
 * inputted positions, sizes, and colors.
 */
public final class ModelViewImpl implements ModelView {

  private final List<Shape> shapes;
  private int boundX;
  private int boundY;
  private int boundW;
  private int boundH;

  /**
   * Constructs a new view model with an empty list of shape objects (empty scene).
   */
  public ModelViewImpl() {
    this.boundX = 300;
    this.boundY = 300;
    this.boundW = 300;
    this.boundH = 300;
    shapes = new ArrayList<Shape>() {
    };
  }

  @Override
  public void motion(String name, Move move) {
    boolean present = false;
    for (Shape s : shapes) {
      if (s.getName().equals(name)) {
        s.addMove(move);
        present = true;
      }
    }

    if (!present) {
      throw new IllegalArgumentException("No shape with this name is present.");
    }
  }

  @Override
  public void removeMotion(String name, Move move) {
    for (Shape h : shapes) {
      if (h.getName().equals(name)) {
        h.removeMove(move);
      }
    }
  }

  @Override
  public List<Move> getAllMotions(String name) {
    for (Shape h : shapes) {
      if (h.getName().equals(name)) {
        return h.getMoves();
      }
    }
    throw new IllegalArgumentException("No such shape exists");
  }

  @Override
  public void addShape(String type, String name) {
    if (type == null || name == null) {
      throw new IllegalArgumentException("cs3500.model.Shape is null");
    }
    for (Shape h : shapes) {
      if (h.getName().equals(name)) {
        throw new IllegalArgumentException("A shape of this name already exists");
      }
    }
    Shape s = new ShapeImpl(type, name);
    shapes.add(s);
  }

  @Override
  public void removeShape(String name) {
    shapes.removeIf(s -> s.getName().equals(name));
  }

  @Override
  public List<Shape> getShapes() {
    return new ArrayList<Shape>(shapes);
  }

  @Override
  public void setBounds(int x, int y, int w, int h) {
    this.boundX = x;
    this.boundY = y;
    this.boundW = w;
    this.boundH = h;
  }

  @Override
  public int getBoundX() {
    return boundX;
  }

  @Override
  public int getBoundY() {
    return boundY;
  }

  @Override
  public int getBoundW() {
    return boundW;
  }

  @Override
  public int getBoundH() {
    return boundH;
  }


  @Override
  public void rotate(String name, int tick, int radians) {
    boolean present = false;
    for (Shape s : shapes) {
      if (s.getName().equals(name)) {
        s.rotateMove(tick, radians);
        present = true;
      }
    }

    if (!present) {
      throw new IllegalArgumentException("No valid shape found to rotate");
    }
  }


  /**
   * A inner class for builder which is used to construct a ModelView. Is used to process
   * information from a .txt file and build a ModelView.
   */
  public static class Builder extends AnimationReader implements AnimationBuilder<ModelView> {

    private final ModelView model;


    /**
     * Constructor for the builder class, creates an empty ModelView.
     */
    public Builder() {
      this.model = new ModelViewImpl();

    }

    @Override
    public ModelView build() {
      return model;
    }

    @Override
    public AnimationBuilder<ModelView> setBounds(int x, int y, int width, int height) {
      model.setBounds(x, y, width, height);
      return this;
    }

    @Override
    public AnimationBuilder<ModelView> declareShape(String name, String type) {
      model.addShape(type, name);
      return this;
    }

    @Override
    public AnimationBuilder<ModelView> addMotion(String name, int t1, int x1, int y1, int w1,
        int h1, int r1, int g1, int b1, int t2, int x2, int y2, int w2, int h2, int r2, int g2,
        int b2) {

      Move move1 = new MoveImpl(t1, x1, y1, w1, h1, r1, g1, b1);
      Move move2 = new MoveImpl(t2, x2, y2, w2, h2, r2, g2, b2);

      try {
        model.motion(name, move1);
        model.motion(name, move2);

      } catch (IllegalArgumentException iae) {
        model.motion(name, move2);

      }
      return this;
    }

    @Override
    public AnimationBuilder<ModelView> addKeyframe(String name, int t, int x, int y, int w, int h,
        int r, int g, int b) {
      Move move = new MoveImpl(t, x, y, w, h, r, g, b) {
      };
      model.motion(name, move);
      return this;
    }


    @Override
    public AnimationBuilder<ModelView> addRotation(String name, int tick, int radians) {

      model.rotate(name, tick, radians);
      return this;
    }


  }
}

