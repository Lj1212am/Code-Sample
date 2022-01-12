package cs3500.animator;

import static java.lang.Thread.sleep;

import cs3500.model.Move;
import cs3500.model.MoveImpl;
import cs3500.model.Shape;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 * The view which animates a given ModelView.  It extends a JFrame to initialize and create the
 * window.  It initializes the PaintAnimate panel adds scrollbars and paints every tick.
 */
public class AnimationView extends AbstractView {

  private final int tempo;
  private final PaintAnimate panel;
  private final JFrame frame;

  /**
   * The constructor to create an Animation view, initializing the tempo.
   *
   * @param tempo the tempo in ticks per second that the view animates.
   */
  public AnimationView(int tempo) {
    this.frame = new JFrame();
    this.boundsX = 300;
    this.boundsY = 300;
    this.boundsW = 300;
    this.boundsH = 300;
    this.tempo = tempo;
    this.shapes = new ArrayList<Shape>();
    this.panel = new PaintAnimate();
    this.frame.setBackground(Color.WHITE);
    this.frame.setSize(this.boundsX, this.boundsY);
    this.panel.setPreferredSize(new Dimension(boundsW, boundsH));
    JScrollPane pain = new JScrollPane(this.panel);
    this.frame.add(pain);
    this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  /**
   * Paints the animation onto the panel going tick by tick.
   *
   * @throws InterruptedException If the sleep function is interrupted
   */
  private void setView() throws InterruptedException {
    ArrayList<String> typesList = new ArrayList<String>();
    ArrayList<Move> movesList = new ArrayList<Move>();

    int time = 1000 / tempo;
    int t = 1;
    int maxTick = 0;

    boolean found = false;
    while (t <= maxTick) {
      for (Shape s : this.shapes) {
        found = false;
        for (Move m : s.getMoves()) {
          if (m.getTick() == t && !found) {
            typesList.add(s.getType());
            movesList.add(m);
            found = true;
            break;
          }
          if ((m.getTick() > t) && !found && (s.getMoves().indexOf(m)) > 0) {
            Move next = m;
            Move current = s.getMoves().get((s.getMoves().indexOf(next)) - 1);
            typesList.add(s.getType());
            movesList.add(between(t, current.getTick(), next.getTick(), current, next));
            found = true;
            break;
          }
        }
      }
      this.panel.paintMove(typesList, movesList);
      sleep(time);
      refresh();
      t++;
      typesList = new ArrayList<String>();
      movesList = new ArrayList<Move>();
    }

  }


  /**
   * Determines the move when the Ticks are in between separate moves.
   *
   * @param currentTick  the current tick they're at
   * @param startTime    the start tick of the first move
   * @param endTime      the end time of the second move
   * @param firstMotion  the first motion
   * @param secondMotion the second motion
   * @return a Move of the in-between values
   */
  private Move between(int currentTick, int startTime, int endTime, Move firstMotion,
      Move secondMotion) {

    if (startTime == endTime) {
      return firstMotion;
    }

    int x = tweenFunc(currentTick, startTime, endTime, firstMotion.getX(), secondMotion.getX());
    int y = tweenFunc(currentTick, startTime, endTime, firstMotion.getY(), secondMotion.getY());
    int w = tweenFunc(currentTick, startTime, endTime, firstMotion.getW(), secondMotion.getW());
    int h = tweenFunc(currentTick, startTime, endTime, firstMotion.getH(), secondMotion.getH());
    int r = tweenFunc(currentTick, startTime, endTime, firstMotion.getR(), secondMotion.getR());
    int g = tweenFunc(currentTick, startTime, endTime, firstMotion.getG(), secondMotion.getG());
    int b = tweenFunc(currentTick, startTime, endTime, firstMotion.getB(), secondMotion.getB());

    Move m = new MoveImpl(currentTick, x, y, w, h, r, g, b);
    return m;
  }

  /**
   * Determines the value of the between positions.
   *
   * @param currentTick  the current tick they're at
   * @param startTime    the start tick of the first move
   * @param endTime      the end time of the second move
   * @param firstMotion  the first motion attribute value
   * @param secondMotion the second motion attribute value
   * @return a Move of the in-between values
   */
  private int tweenFunc(int currentTick, int startTime, int endTime, int firstMotion,
      int secondMotion) {
    double f = ((double) firstMotion * (((double) endTime - (double) currentTick) / (
        (double) endTime - (double) startTime)));
    double s = ((double) secondMotion * (((double) currentTick - (double) startTime) / (
        (double) endTime - (double) startTime)));
    int ans = (int) (s + f);
    return ans;
  }

  @Override
  public void makeVisible() throws InterruptedException {
    this.frame.setVisible(true);
    this.setView();
  }

  @Override
  public void refresh() {
    this.frame.repaint();
  }

  @Override
  public void getBounds(int x, int y, int w, int h) throws IOException {
    super.getBounds(x, y, w, h);
    this.panel.setBounds(boundsX, boundsY, boundsH, boundsW);
    this.frame.setSize(this.boundsW, this.boundsH);
    this.panel.setPreferredSize(new Dimension(boundsW, boundsH));
  }


}
