package cs3500.controller;

import java.io.IOException;

/**
 * The interface for the edit controller which connects the model to the view. Is used to initialize
 * the given edit model with the file of instructions and tempo passed through.
 */
public interface IEditController {

  /**
   * Makes the initial edit view panel visible and waits for inputs from the user.
   *
   * @throws IOException          if the file is invalid.
   * @throws InterruptedException if a command tries to run while waiting.
   */
  void playGame() throws IOException, InterruptedException;

}
