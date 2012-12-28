package database;

import javax.swing.*;
import java.awt.*;

/**
 * Main Class.
 * User: Julian Fink, Oskar Jung, Kevin Goy
 */
public class DataBaseApplication extends JFrame{


  public DataBaseApplication() {
    setTitle("Dbapp");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(new DataBaseApplicationPanel());
    setSize( 800, 800 );
    setVisible( true );
  }

  public static void main( String[] args ) {
    new DataBaseApplication();

  }

}
