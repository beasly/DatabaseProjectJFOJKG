package database;

import javax.swing.*;
import java.awt.*;

/**
 * Main Class.
 * User: Julian Fink, Oskar Jung, Kevin Goy
 *
 */
public class DataBaseApplication extends JFrame{


	public DataBaseApplication(){
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

	public static void main(String[] args){
		new DataBaseApplication();

	}

}
class DataBaseCenterPanel extends JPanel {
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.black);
		int x = getWidth();
		int y = getHeight();

		// resizableLine(g, x, y);
		drawHorizontal(g, x, y);
		drawVertical(g, x, y);
	}

	private void drawVertical(Graphics g, int x, int y) {
		int abstand = 20;
		for (int width = 0; width <= x; width = width + abstand) {
			g.drawLine(width, 0, width, y);
		}
	}

	private void drawHorizontal(Graphics g, int x, int y) {
		int abstand = 20;
		for (int height = 0; height <= y; height = height + abstand) {
			g.drawLine(0, height, x, height);
		}
	}

	private void resizableLine(Graphics g, int x, int y) {
		g.drawLine(100, 100, x - 100, y - 100);
	}}
