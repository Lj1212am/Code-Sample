package cs3500.animator;

import cs3500.model.Move;
import cs3500.model.MoveImpl;
import cs3500.model.Shape;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeListener;

/**
 * The view which animates a given ModelView.  It extends a JFrame to initialize and create the
 * window. It initializes the PaintAnimate panel adds scrollbars and paints every tick. Allows the
 * user to pause, restart, and loop the animation as well as change key frames before the start.
 */
public class EditView extends AbstractView implements ISmarterView {

  private Timer timer;
  private PaintAnimate panel;
  private JPanel editPane;
  private JPanel mainPanel;
  private CardLayout cl;
  public JButton button;
  public JTextField textField;
  private boolean isPaused;
  private boolean isLooping;
  private int tempo;
  private int maxTick;
  private int t;
  private int time;
  private Task tsk;
  private JSlider slider;



  /**
   * Constructs a new edit view and initializes the tempo.
   *
   * @param tempo tick per second for the animation.
   */
  public EditView(int tempo) {

    this.timer = new Timer();
    this.isPaused = false;
    this.isLooping = false;
    this.boundsX = 300;
    this.boundsY = 300;
    this.boundsW = 300;
    this.boundsH = 300;
    this.tempo = tempo;
    this.t = 1;
    this.maxTick = 1000;
    this.time = 1000 / tempo;
    this.shapes = new ArrayList<Shape>();
    this.cl = new CardLayout();
    this.mainPanel = new JPanel(cl);
    this.editPane = new JPanel();
    this.editPane.setPreferredSize(new Dimension(boundsX, boundsY));
    this.panel = new PaintAnimate();
    this.slider = new JSlider(1, 10, t);
    mainPanel.add(panel, "animation");
    mainPanel.add(editPane, "edit");
    this.setBackground(Color.WHITE);
    this.setSize(this.boundsX, this.boundsY);
    this.button = new JButton("Submit");
    JScrollPane pain = new JScrollPane(this.mainPanel);
    this.add(pain);
    this.pack();
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocationByPlatform(true);
    this.setVisible(true);
  }

  @Override
  public void makeVisible() {
    JLabel label = new JLabel(
        "                    s: to start    ' ': to pause" +
            "    l: to loop    a: to slow down animation" +
            "    d: to speed up animation");
    JLabel commandLabel = new JLabel(
        "                    Commands: Remove shape-name tick" +
            "     Modify shape-name tick x y width height r g b" +
            "     Add shape-name tick x y width height r g b");
    this.editPane.setLayout(new GridLayout(4, 1));
    this.editPane.add(label);
    this.editPane.add(commandLabel);
    this.editPane.setPreferredSize(new Dimension(boundsX, boundsY));
    this.button.setBounds(100, 100, 100, 100);
    this.textField = new JTextField(20);
    this.editPane.add(button);
    this.editPane.add(textField);
    cl.show(mainPanel, "edit");
  }

  @Override
  public void refresh() {
    this.repaint();
  }

  @Override
  public void refocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public void start() {
    this.slider.setMinimum(1);
    this.slider.setMaximum(getMaxTick());
    this.slider.setMajorTickSpacing(10);
    this.slider.setMinorTickSpacing(1);
    this.slider.setPaintTicks(true);
    this.panel.setPreferredSize(new Dimension(boundsW, boundsH));
    this.panel.add(this.slider);
    cl.show(mainPanel, "animation");
    setView();
  }

  @Override
  public void restart() {
    timer.cancel();
    timer.purge();
    timer = new Timer();
    this.t = 1;
    this.tsk = (new Task(shapes, panel, t, maxTick));
    timer.schedule(tsk, 0, time);
    this.isPaused = false;
  }

  @Override
  public void pause() {
    if (isPaused) {
      timer = new Timer();
      this.tsk = (new Task(shapes, panel, t, maxTick));
      timer.schedule(tsk, 0, time);
      this.isPaused = false;
    } else {
      timer.cancel();
      timer.purge();
      this.t = tsk.getTick();
      this.isPaused = true;
    }
  }

  @Override
  public void loop() {
    this.isLooping = !isLooping;
  }

  @Override
  public void speed(boolean faster) {
    if (faster) {
      this.tempo++;
    } else if (tempo > 1) {
      this.tempo--;
    }
    this.time = 1000 / tempo;
    this.t = tsk.getTick();
    timer.cancel();
    timer.purge();
    timer = new Timer();
    this.tsk = (new Task(shapes, panel, t, maxTick));
    timer.schedule(tsk, 0, time);
  }

  @Override
  public void setKeyListener(KeyListener listener) {
    this.addKeyListener(listener);
    this.setFocusable(true);
  }

  @Override
  public void setButtonListener(ActionListener listener) {
    button.addActionListener(listener);
  }

  @Override
  public void changeShapes() {
    if (textField.getText() == null || textField.getText().equals("")) {
      return;
    }
    String text = "";
    text = textField.getText();
    textField.setText("  ");
    String[] lines = text.split(" ");

    int i = 0;
    switch (lines[i++]) {
      case "remove":
        removeKey(lines[i++], Integer.parseInt(lines[i++]));
        break;
      case "modify":
        modifyKey(lines[i++], Integer.parseInt(lines[i++]), Integer.parseInt(lines[i++]),
            Integer.parseInt(lines[i++]),
            Integer.parseInt(lines[i++]), Integer.parseInt(lines[i++]),
            Integer.parseInt(lines[i++]), Integer.parseInt(lines[i++]),
            Integer.parseInt(lines[i++]));
        break;
      case "add":
        addKey(lines[i++], Integer.parseInt(lines[i++]), Integer.parseInt(lines[i++]),
            Integer.parseInt(lines[i++]),
            Integer.parseInt(lines[i++]), Integer.parseInt(lines[i++]),
            Integer.parseInt(lines[i++]), Integer.parseInt(lines[i++]),
            Integer.parseInt(lines[i++]));
        break;
      default:
        break;
    }
  }


  @Override
  public void setChangeListener(ChangeListener listener) {
    this.slider.addChangeListener(listener);
  }


  @Override
  public void sliderAnimation(int currentTick) {
    isPaused = false;
    timer.cancel();
    timer.purge();
    timer = new Timer();
    this.t = currentTick;
    this.tsk = (new Task(shapes, panel, t, maxTick));
    timer.schedule(tsk, 0, time);


  }

  /**
   * Removes a key frame from the list of motions to be animated.
   *
   * @param n    name of the shape.
   * @param tick the given tick of the keyframe.
   */
  private void removeKey(String n, int tick) {
    for (Shape s : shapes) {
      if (s.getName().equals(n)) {
        for (Move m : s.getMoves()) {
          if (m.getTick() == tick) {
            s.removeMove(m);
          }
        }
      }
    }
    getShapes(this.shapes);
    refocus();
  }

  /**
   * Modifies the parameters of a specified key frame to be animated.
   *
   * @param n    name of the shape.
   * @param tick given tick of the keyframe.
   * @param x    x-position of the shape.
   * @param y    y-position of the shape.
   * @param w    width of the shape.
   * @param h    height of the shape.
   * @param r    red rgb value of the shape.
   * @param g    green rgb value of the shape.
   * @param b    blue rgb value of the shape.
   */
  private void modifyKey(String n, int tick, int x, int y, int w, int h, int r, int g, int b) {
    for (Shape s : shapes) {
      if (s.getName().equals(n)) {
        for (Move m : s.getMoves()) {
          if (m.getTick() == tick) {
            s.removeMove(m);
            Move mv = new MoveImpl(tick, x, y, w, h, r, g, b);
            s.insertMove(mv);
          }
        }
      }
    }
    getShapes(this.shapes);
    refocus();
  }

  /**
   * Adds a new keyframe to be animated by the program.
   *
   * @param n    name of the shape.
   * @param tick given tick of the keyframe.
   * @param x    x-position of the shape.
   * @param y    y-position of the shape.
   * @param w    width of the shape.
   * @param h    height of the shape.
   * @param r    red rgb value of the shape.
   * @param g    green rgb value of the shape.
   * @param b    blue rgb value of the shape.
   */
  private void addKey(String n, int tick, int x, int y, int w, int h, int r, int g, int b) {
    for (Shape s : shapes) {
      if (s.getName().equals(n)) {
        Move m = new MoveImpl(tick, x, y, w, h, r, g, b);
        s.insertMove(m);
      }
    }
    getShapes(this.shapes);
    refocus();
  }

  /**
   * Paints the animation onto the panel going tick by tick.
   */
  private void setView() {
    int time = 1000 / tempo;
    this.time = time;
    int mx = 0;

    for (Shape s : this.shapes) {
      int curr = s.getMoves().get(s.getMoves().size() - 1).getTick();
      if (curr > mx) {
        mx = curr;
      }
    }

    this.maxTick = mx;
    this.tsk = new Task(shapes, panel, t, mx);
    timer.schedule(tsk, 0, time);
  }

  /**
   * Task class for the timer that implements the run method in order to animate the list of shapes
   * and their motions.
   */
  //task has to be lowercase for the time, points should not be taken off
  class Task extends TimerTask {

    private List<Shape> shapes;
    private PaintAnimate panel;
    private int t;
    private int maxTick;

    /**
     * Constructs a new task and initializes the list of shapes, panel to be painted to, and max
     * tick.
     *
     * @param shapes  list of shapes to be animated.
     * @param panel   paintAnimate panel that extends JPanel.
     * @param t       current tick of the animation.
     * @param maxTick the maximum tick of the animation.
     */
    public Task(List<Shape> shapes, PaintAnimate panel, int t, int maxTick) {
      this.shapes = shapes;
      this.panel = panel;
      this.t = t;
      this.maxTick = maxTick;
    }

    /**
     * Returns the current tick of the animation.
     *
     * @return an integer representing the current tick.
     */
    public int getTick() {
      return t;
    }

    /**
     * Uses the list of shapes to create a list of shape types and moves to be sent to the
     * paintAnimate class and loops if specified.
     */
    public void run() {
      List<String> typesList = new ArrayList<String>();
      List<Move> movesList = new ArrayList<Move>();
      boolean found = false;
      slider.setValue(t);

      if (t <= maxTick) {

        for (Shape s : shapes) {
          found = false;
          for (Move m : s.getMoves()) {
            if (m.getTick() == t && !found) {
              typesList.add(s.getType());
              movesList.add(m);
              found = true;
              break;
            } else if ((m.getTick() > t) && !found && (s.getMoves().indexOf(m)) > 0) {
              Move next = m;
              Move current = s.getMoves().get((s.getMoves().indexOf(next)) - 1);
              typesList.add(s.getType());
              movesList.add(between(t, current.getTick(), next.getTick(), current, next));
              found = true;
              break;
            }
          }
        }
        panel.paintMove(typesList, movesList);
        refresh();
        t++;


      } else if (isLooping) {
        restart();
      }
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

    int rotate = tweenFunc(currentTick, startTime, endTime, firstMotion.getRotation(),
        secondMotion.getRotation());

    Move m = new MoveImpl(currentTick, x, y, w, h, r, g, b);

    m.rotate(rotate);

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
    int ans = (int) ((s + f) + 0.5);
    return ans;
  }

  @Override
  public int getTick() {
    return this.t;
  }


}
