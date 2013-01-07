package database;

import javax.swing.*;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Main Class.
 * User: Julian Fink, Oskar Jung, Kevin Goy
 */
public class DataBaseApplication extends JFrame {


	public DataBaseApplication() {
		final CheckURL db = new CheckURL();
		setTitle("Dbapp");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(new DataBaseApplicationPanel(db));
		setSize(800, 800);
		setVisible(true);
		addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent windowEvent) {
			}

			@Override
			public void windowClosing(WindowEvent windowEvent) {
				db.close_database();
			}

			@Override
			public void windowClosed(WindowEvent windowEvent) {
			}

			@Override
			public void windowIconified(WindowEvent windowEvent) {
			}

			@Override
			public void windowDeiconified(WindowEvent windowEvent) {
			}

			@Override
			public void windowActivated(WindowEvent windowEvent) {
			}

			@Override
			public void windowDeactivated(WindowEvent windowEvent) {
			}
		});

	}

	public static void main(String[] args) {
		new DataBaseApplication();


	}

}
