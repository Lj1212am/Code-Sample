package cs3500.animator;

import java.io.IOException;

/**
 * The interface for the controller which connects the model to the views. Is used to initialize the
 * given model with the file of instructions passed through.
 */
public interface IMVCController {

  /**
   * Starts the animation by sending information to the view.
   *
   * @throws IOException          if file is invalid.
   * @throws InterruptedException if a command tries to run while waiting.
   */
  void playGame() throws IOException, InterruptedException;

}
