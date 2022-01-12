import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Abstract class for testing the views. Holds methods used in both test classes.
 */
public abstract class AbstractViewTest {

  /**
   * Tests whether two files are equal.
   *
   * @param f1 the pathway for the first file
   * @param f2 the pathway for the second file
   * @return boolean for if the files are equal or not
   * @throws IOException when a pathway cannot be found
   */
  protected boolean fileChecker(String f1, String f2) throws IOException {
    BufferedReader reader1 = new BufferedReader(new FileReader(f1));
    BufferedReader reader2 = new BufferedReader(new FileReader(f2));

    String line1 = reader1.readLine();
    String line2 = reader2.readLine();

    boolean areEqual = true;

    while (line1 != null || line2 != null) {
      if (line1 == null || line2 == null) {
        areEqual = false;
        break;
      } else if (!line1.equalsIgnoreCase(line2)) {
        areEqual = false;
        break;
      }
      line1 = reader1.readLine();
      line2 = reader2.readLine();
    }

    reader1.close();
    reader2.close();
    return areEqual;
  }
}
