package database;

/**
 * The class <code> Input </code> contains methods to get and check input data.
 *
 * User: Julian Fink
 * Date: 24.12.12
 * Time: 22:22
 *
 */
public class Input {

  /**
   * Checks if a String is an Integer.
   *
   *
   * @param s String Input
   * @return Boolean value
   */
  public static boolean isFloat(String s) {
    try
    {
      Float.parseFloat(s);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }


}
