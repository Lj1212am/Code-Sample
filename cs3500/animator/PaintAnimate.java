package cs3500.animator;

import cs3500.model.Move;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 * The PaintAnimate class is used to create the panel for the animation. It extends JPanel and draws
 * a given scene onto the panel.
 */
public class PaintAnimate extends JPanel {

  private List<String> typesList;
  private List<Move> movesList;

  /**
   * Initializes the panel.
   */
  public PaintAnimate() {
    super();
    this.typesList = new ArrayList<String>();
    this.movesList = new ArrayList<Move>();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    AffineTransform af = g2d.getTransform();

    for (int i = 0; i < typesList.size(); i++) {

      Move m = movesList.get(i);
      int x = m.getX();
      int y = m.getY();

      switch (typesList.get(i)) {
        case "rectangle":
          g2d.rotate(Math.toRadians(m.getRotation()), m.getX() + m.getW() / 2,
              m.getY() + m.getH() / 2);
          g2d.setColor(new Color(m.getR(), m.getG(), m.getB()));

          g2d.fillRect(x, y, m.getW(), m.getH());
          g2d.setTransform(af);

          break;

        case "ellipse":
          g2d.rotate(Math.toRadians(m.getRotation()), m.getX() + m.getW() / 2,
              m.getY() + m.getH() / 2);
          g2d.setColor(new Color(m.getR(), m.getG(), m.getB()));

          g2d.fillOval(m.getX(), m.getY(), m.getW(), m.getH());
          g2d.setTransform(af);

          break;
        default:
          throw new IllegalArgumentException("Not a valid shape");
      }
    }
  }

  /**
   * Sets the values that the panel has to display.
   *
   * @param typesList the type of shapes being sent in.
   * @param movesList the move list of those shapes.
   */
  protected void paintMove(List<String> typesList, List<Move> movesList) {
    this.typesList = typesList;
    this.movesList = movesList;
  }

}

