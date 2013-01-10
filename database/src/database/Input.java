package database;

/**
 * Created with IntelliJ IDEA.
 * User: jufert
 * Date: 10.01.13
 * Time: 22:11
 * To change this template use File | Settings | File Templates.
 */
public class Input {

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
