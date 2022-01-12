package cs3500.animator;

import cs3500.model.Move;
import cs3500.model.Shape;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The class for the TextView.  It implements IView and produces a text file of the moves of the
 * animation being created. It can throw exceptions if the file created cannot be found. It will
 * output the desired text using System.Out if no file is given to be written to.
 */
public class TextView extends AbstractView {

  private final String out;

  /**
   * The constructor that takes in the file which the text is being written to.
   *
   * @param out The text file being written to
   */
  public TextView(String out) {
    this.boundsX = 300;
    this.boundsY = 300;
    this.boundsW = 300;
    this.boundsH = 300;
    this.shapes = new ArrayList<Shape>();
    this.out = out;
    this.output = new StringBuilder();
    this.tempo = 1;
  }

  /**
   * The constructor that takes in nothing, and initializes the game.
   */
  public TextView() {
    this.boundsX = 300;
    this.boundsY = 300;
    this.boundsW = 300;
    this.boundsH = 300;
    this.shapes = new ArrayList<Shape>();
    this.out = null;
    this.output = new StringBuilder();
    this.tempo = 1;
  }

  @Override
  public void makeVisible() throws IOException {
    makeState();
    writeToFile(output.toString());
  }

  @Override
  public void refresh() throws IOException {
    this.makeVisible();
  }

  /**
   * Returns the current state of the view, which is all of the shapes and corresponding changes.
   *
   * @return all shapes with their movements as strings.
   * @throws IOException if append fails.
   */
  private void makeState() throws IOException {
    output.append("canvas " + boundsX + "\t" + boundsY
        + "\t" + boundsW + "\t" + boundsH + "\n");

    for (Shape s : shapes) {
      List<Move> m = s.getMoves();
      output.append("shape" + " " + s.getType() + " " + s.getName() + "\n");

      for (int i = 0; i < m.size(); i++) {

        if (i < m.size() - 1) {
          Move current = m.get(i);
          Move next = m.get(i + 1);
          output.append("motion" + " " + s.getName() + " " + current.getTick() + "\t" +
              current.getX() + "\t" + current.getY() + "\t" + current.getW() +
              "\t" + current.getH() + "\t" + current.getR() + "\t" +
              current.getG() + "\t" + current.getB() + "\t" + "\t");

          output.append(next.getTick() + "\t" +
              next.getX() + "\t" + next.getY() + "\t" + next.getW() +
              "\t" + next.getH() + "\t" + next.getR() + "\t" +
              next.getG() + "\t" + next.getB() + "\n");
        }
      }
      if (shapes.indexOf(s) < shapes.size() - 1) {
        output.append("\n\n");
      }
    }
  }

  /**
   * Writes the string to a file.
   *
   * @param s the string used to write
   * @throws IOException if the file cannot be found
   */
  private void writeToFile(String s) throws IOException {
    if (out == null) {
      System.out.println(s);
    } else {
      FileWriter write = new FileWriter(new File(out));
      write.write(s);
      write.close();
    }
  }

}
