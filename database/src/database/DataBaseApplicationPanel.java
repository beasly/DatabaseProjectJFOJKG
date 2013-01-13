package database;


import java.awt.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import database.tabs.BookPanel;
import database.tabs.HomePanel;

public class DataBaseApplicationPanel extends JPanel {

	public DataBaseApplicationPanel(CheckURL db) {
 		initComponents(db);
	}

	public void initComponents(final CheckURL db) {
		final JTabbedPane jTabbedPane = new JTabbedPane();
		final HomePanel homeTab = new HomePanel(db);
		final BookPanel bookTab = new BookPanel(db);
		setLayout(new BorderLayout());
		jTabbedPane.addTab("Home", homeTab);
		jTabbedPane.addTab("Buch", bookTab);
		jTabbedPane.setSelectedIndex(0);
		jTabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent changeEvent) {
				switch (jTabbedPane.getSelectedIndex()){
					case 0:
						homeTab.updateAndAddTable(db);
						break;
					case 1:
						bookTab.updateAndAddTable(db);
						break;
				}
			}
		});
		add(jTabbedPane);
	}

}


