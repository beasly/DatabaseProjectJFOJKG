package database;


import java.awt.*;
import java.sql.SQLException;

import javax.swing.*;

public class DataBaseApplicationPanel extends JPanel {

	public DataBaseApplicationPanel(CheckURL db) {
 		initComponents(db);
	}

	public void initComponents(CheckURL db) {
		JTabbedPane jTabbedPane = new JTabbedPane();

		setLayout(new BorderLayout());

		jTabbedPane.addTab("Home", new HomePanel(db));

		jTabbedPane.addTab("Buch", new BookPanel(db));
		add(jTabbedPane);
	}

}


