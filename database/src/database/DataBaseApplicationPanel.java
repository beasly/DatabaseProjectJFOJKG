package database;


import java.awt.*;

import javax.swing.*;

public class DataBaseApplicationPanel extends JPanel {

	public DataBaseApplicationPanel() {
 		initComponents();
	}

	public void initComponents() {
		JTabbedPane jTabbedPane = new JTabbedPane();

		setLayout(new BorderLayout());

		jTabbedPane.addTab("Home", new HomePanel());

		jTabbedPane.addTab("Buch", new BookPanel());
		add(jTabbedPane);
	}

}


