package database;

import javax.swing.*;

import java.awt.*;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Main Class.
 * User: Julian Fink, Oskar Jung, Kevin Goy
 */
public class DataBaseApplication extends JFrame {


	public DataBaseApplication() {
		setTitle("Dbapp");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout(5, 5));
		add(new JButton("bla"), BorderLayout.NORTH);
		add(new JButton("pups"), BorderLayout.EAST);
		add(new JButton("bla"), BorderLayout.WEST);
		add(new JButton("bla"), BorderLayout.SOUTH);
		add(new DataBaseCenterPanel(), BorderLayout.CENTER);
		setSize(800, 800);
		setVisible(true);
	}

	public static void main(String[] args)  {
		new DataBaseApplication();
		CheckURL app = new CheckURL();

	}

}
