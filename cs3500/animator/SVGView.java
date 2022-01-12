package cs3500.animator;

import cs3500.model.Move;
import cs3500.model.Shape;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The SVG View class.  It is used to take in the components of a model and write out the
 * animations.  However, unlike the TextView class the written text is in the SVG text format. Which
 * can be used to be animated independently.  If not file is given the class writes it's output
 * using System.Out, and if no tempo is given the default tempo is 1 tick per second.
 */
public class SVGView extends AbstractView {

  private final String out;

  /**
   * A constructor for the View which takes in the file being written to and the tempo at which the
   * animation moves.
   *
   * @param out   the file being written to
   * @param tempo the tempo the animation moves
   */
  public SVGView(String out, int tempo) {
    this.shapes = new ArrayList<Shape>();
    this.out = out;
    this.tempo = tempo;
    this.output = new StringBuilder();
  }

  /**
   * Constructor that takes in the tempo the animation moves.
   *
   * @param tempo the tempo of the animation
   */
  public SVGView(int tempo) {
    this.shapes = new ArrayList<Shape>();
    this.out = null;
    this.tempo = tempo;
    this.output = new StringBuilder();
  }

  /**
   * Constructor that takes in no inputs.
   */
  public SVGView() {
    this.shapes = new ArrayList<Shape>();
    this.out = null;
    this.tempo = 1;
    this.output = new StringBuilder();
  }

  @Override
  public void makeVisible() throws IOException {
    createShapes();
    writeToFile();
  }

  @Override
  public void refresh() throws IOException {
    this.makeVisible();
  }

  /**
   * Writes the string to a file.
   *
   * @throws IOException if the file cannot be found
   */
  private void writeToFile() throws IOException { // can be abstracted
    if (out == null) {
      System.out.println(output.toString());
    } else {
      FileWriter write = new FileWriter(new File(out));
      write.write(output.toString());
      write.close();
    }
  }

  /**
   * Creates the SVG text version for the shapes being passed in.
   *
   * @throws IOException if append fails.
   */
  private void createShapes() throws IOException {
    for (Shape s : shapes) {
      switch (s.getType()) {
        case "rectangle":
          makeRectangle(s);
          break;
        case "ellipse":
          makeEllipse(s);
          break;
        default:
          throw new IllegalArgumentException("Not a valid shape");
      }
    }
    output.append("</svg>");
  }

  /**
   * Creates the rectangle SVG text version.
   *
   * @param s string version of the text.
   * @throws IOException if append fails.
   */
  private void makeRectangle(Shape s) throws IOException {
    output.append("<rect id=\"" + s.getName() + "\" x=\"" + s.getMoves().get(0).getX() + "\" y=\"" +
        s.getMoves().get(0).getY() + "\" width=\"" + s.getMoves().get(0).getW() + "\" height=\"" +
        s.getMoves().get(0).getH() + "\" fill=\"rgb(" + s.getMoves().get(0).getR() + "," +
        s.getMoves().get(0).getG() + "," + s.getMoves().get(0).getB() +
        ")\" visibility=\"visible\" >\n\n");

    for (int i = 0; i < s.getMoves().size() - 1; i++) {
      checkAtt("rect", s.getMoves().get(i), s.getMoves().get(i + 1));
    }

    output.append("</rect>\n\n");
  }

  /**
   * Creates the ellipse SVG text version.
   *
   * @param s string version of the text
   */
  private void makeEllipse(Shape s) throws IOException {
    output.append(
        "<ellipse id=\"" + s.getName() + "\" cx=\"" + s.getMoves().get(0).getX() + "\" cy=\"" +
            s.getMoves().get(0).getY() + "\" rx=\"" + s.getMoves().get(0).getW() + "\" ry=\"" +
            s.getMoves().get(0).getH() + "\" fill=\"rgb(" + s.getMoves().get(0).getR() + "," +
            s.getMoves().get(0).getG() + "," + s.getMoves().get(0).getB() +
            ")\" visibility=\"visible\" >\n\n");

    for (int i = 0; i < s.getMoves().size() - 1; i++) {
      checkAtt("ellipse", s.getMoves().get(i), s.getMoves().get(i + 1));
    }

    output.append("</ellipse>\n\n");
  }

  /**
   * Checks what changes in the moves are and writes those changes. Animations start at tick 1
   * (1000ms).
   *
   * @param type type of shape sent in.
   * @param m1   the first move.
   * @param m2   the next move.
   * @throws IOException if append fails.
   */
  private void checkAtt(String type, Move m1, Move m2) throws IOException {
    int time = 1000 / tempo;
    int begin = 1000;
    int dur = 0;
    String attribute = "";
    String firstVal = "";
    String secondVal = "";

    begin += m1.getTick() * time;
    dur = 1000 + (m2.getTick() * time) - begin;

    if (m1.getX() != m2.getX()) {
      if (type.equals("rect")) {
        attribute = "x";
      } else {
        attribute = "cx";
      }
      firstVal = Integer.toString(m1.getX());
      secondVal = Integer.toString(m2.getX());
      appendMove(begin, dur, attribute, firstVal, secondVal);
    }

    if (m1.getY() != m2.getY()) {
      if (type.equals("rect")) {
        attribute = "y";
      } else {
        attribute = "cy";
      }
      firstVal = Integer.toString(m1.getY());
      secondVal = Integer.toString(m2.getY());
      appendMove(begin, dur, attribute, firstVal, secondVal);
    }

    if (m1.getW() != m2.getW()) {
      if (type.equals("rect")) {
        attribute = "width";
      } else {
        attribute = "rx";
      }
      firstVal = Integer.toString(m1.getW());
      secondVal = Integer.toString(m2.getW());
      appendMove(begin, dur, attribute, firstVal, secondVal);
    }

    if (m1.getH() != m2.getH()) {
      if (type.equals("rect")) {
        attribute = "height";
      } else {
        attribute = "ry";
      }
      firstVal = Integer.toString(m1.getH());
      secondVal = Integer.toString(m2.getH());
      appendMove(begin, dur, attribute, firstVal, secondVal);
    }

    if (m1.getR() != m2.getR() || m1.getG() != m2.getG() || m1.getB() != m2.getB()) {
      attribute = "fill";
      firstVal =
          "rgb(" + (m1.getR()) + "," + (m1.getG()) + "," + (m1.getB()) + ")";
      secondVal =
          "rgb(" + (m2.getR()) + "," + (m2.getG()) + "," + (m2.getB()) + ")";
      appendMove(begin, dur, attribute, firstVal, secondVal);
    }

    if (m1.getRotation() != 0 || m2.getRotation() != 0) {
      String rotAttribute = "\"transform\"";
      String attributeType = "\"XML\"";
      String rotType = "\"rotate\"";
      String rotFirstVal =
          "\"" + m1.getRotation() + " " + (m1.getX() + m1.getW() / 2) + " " + (m1.getY()
              + m1.getH() / 2) + "\"";
      String rotSecondVal =
          "\"" + m2.getRotation() + " " + (m2.getX() + m2.getW() / 2) + " " + (m2.getY()
              + m2.getH() / 2) + "\"";
      appendAnimation(begin, rotAttribute, attributeType, rotType, rotFirstVal, rotSecondVal, dur);
    }

  }

  private void appendAnimation(int begin, String attribute, String attributeType, String type,
      String firstVal, String secondVal, int dur)
      throws IOException {
    output.append("<animateTransform attributeName=" + attribute + "\n"
        + "\tattributeType=" + attributeType + "\n"
        + "\tbegin=\"" + begin + "ms\"\n"
        + "\ttype=" + type + "\n"
        + "\tfrom=" + firstVal + "\n"
        + "\tto=" + secondVal + "\n"
        + "\tdur=\"" + dur + "ms\"\n"
        + "\trepeatCount=\"indefinite\"/>\n");
  }

  /**
   * Creates the text for the move being given.
   *
   * @param begin     the beginning time
   * @param dur       the duration of the move
   * @param attribute the type being changed
   * @param firstVal  the original value changed
   * @param secondVal the second value changed
   * @throws IOException if append fails.
   */
  private void appendMove(int begin, int dur, String attribute, String firstVal, String secondVal)
      throws IOException {
    output.append("\t<animate attributeType=\"xml\" begin=\"" + begin + "ms\" dur=\"" + dur
        + "ms\" attributeName=\"" +
        attribute + "\" from=\"" + firstVal + "\" to=\"" + secondVal + "\" fill=\"freeze\" />\n");
  }

  @Override
  public void getBounds(int x, int y, int w, int h) throws IOException {
    output.append("<svg width=\"" + 2 * w + "\" height=\"" + 2 * h + "\" version=\"1.1\"\n");
    output.append("\txmlns=\"http://www.w3.org/2000/svg\">\n\n");
  }


}
