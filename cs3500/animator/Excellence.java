package cs3500.animator;

import cs3500.controller.EditController;
import java.io.File;
import java.io.IOException;

/**
 * Main class to run the animation.
 */
public class Excellence {

  /**
   * Main methods that runs the animation.
   *
   * @param args string arguments sent to initialize the game.
   * @throws IOException          an exception thrown when the pathname is invalid for the final
   * @throws InterruptedException thrown when a writing or reading a file is interrupted
   */
  public static void main(String[] args) throws IOException, InterruptedException {

    //IView view = new TextView(new File("text-transcript.txt"));
    //IView view = new AnimationView( 200);
    //IView view = new SVGView("rotation.svg", 20);
    //ISmarterView view = new EditView(10);
    //IEditController controller = new EditController(new File("buildings.txt"), view);
    //IMVCController controller = new MVCController(new File("buildings.txt"), view);
    //controller.playGame();


    int index = 0;
    int tempo = 1;
    String fileOut = "";
    String fileIn = "";
    String viewType = "";

    while (index < args.length) {
      String input = args[index];
      switch (input) {
        case "-view":
          index++;
          input = args[index];
          switch (input) {
            case "text":
            case "visual":
            case "svg":
            case "edit":
              viewType = input;
              index++;
              break;
            default:
              index++;
              break;
          }
          break;
        case "-speed":
          tempo = Integer.parseInt(args[index + 1]);
          index = index + 2;
          break;
        case "-in":
          fileIn = args[index + 1];
          index = index + 2;
          break;
        case "-out":
          fileOut = args[index + 1];
          index = index + 2;
          break;
        default:
          index++;
          break;
      }
    }

    switch (viewType) {
      case "text":
        if (fileOut.equals("")) {
          new MVCController(new File(fileIn), new TextView()).playGame();
        } else {
          new MVCController(new File(fileIn), new TextView(fileOut)).playGame();
        }
        break;
      case "visual":
        new MVCController(new File(fileIn), new AnimationView(tempo)).playGame();
        break;
      case "svg":
        if (fileOut.equals("")) {
          new MVCController(new File(fileIn), new SVGView(tempo)).playGame();
        } else {
          new MVCController(new File(fileIn), new SVGView(fileOut, tempo)).playGame();
        }
        break;
      case "edit":
        new EditController(new File(fileIn), new EditView(tempo)).playGame();
        break;
      default:
        break;
    }

  }

}


