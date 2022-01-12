package cs3500.animator;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import javax.swing.event.ChangeListener;

/**
 * The interface for the edit view used for editing and outputting an animation. Holds the methods
 * necessary to accurately display and change the pertinent information of a animation sent
 * through.
 */
public interface ISmarterView extends IView {

  /**
   * Starts the animation by making the paintAnimate panel visible and creating a new task for the
   * timer to paint the given moves.
   */
  void start();

  /**
   * Resets the focus of the view to the frame.
   */
  void refocus();

  /**
   * Restarts the animation from tick 1 from the current spot.
   */
  void restart();

  /**
   * Pauses the animation at the current tick or continues the animation if already paused.
   */
  void pause();

  /**
   * Turns on and off the option to loop the animation back to the start when it is completed.
   */
  void loop();

  /**
   * Increases the ticks per second speed of the animation if the input is true, and decreases the
   * tick per second speed of the animation otherwise.
   *
   * @param faster specifies if the speed should increase or decrease.
   */
  void speed(boolean faster);

  /**
   * this is to force the view to have a method to set up the keyboard. The name has been chosen
   * deliberately. This is the same method signature to add a key listener in Java Swing. Thus our
   * Swing-based implementation of this interface will already have such a method.
   *
   * @param listener key listener for key inputs.
   */
  void setKeyListener(KeyListener listener);

  /**
   * this is to force the view to have a method to set up a listener for the button. The name has
   * been chosen deliberately. This is the same method signature to add a button listener in Java
   * Swing. Thus our Swing-based implementation of this interface will already have such a method.
   *
   * @param listener button listener for the submit button.
   */
  void setButtonListener(ActionListener listener);

  /**
   * Takes in the text field when the button is pressed to modify the key frames according to the
   * command input.
   */
  void changeShapes();

  /**
   * Attaches the ChangeListener to the slider.
   *
   * @param listener the listener begin attached to the slider
   */
  void setChangeListener(ChangeListener listener);

  /**
   * Animates the view based off of the tick the slider is on.
   *
   * @param currentTick The tick the slider has chosen
   */
  void sliderAnimation(int currentTick);

  /**
   * Gets the current tick of the view.
   *
   * @return the int of the current tick
   */
  int getTick();

}
