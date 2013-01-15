package database;


import java.awt.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import database.tabs.*;

public class DataBaseApplicationPanel extends JPanel {

	public DataBaseApplicationPanel(CheckURL db) {
 		initComponents(db);
	}

	public void initComponents(final CheckURL db) {
		final JTabbedPane jTabbedPane = new JTabbedPane();
		final HomePanel homeTab = new HomePanel(db);
		final BookPanel bookTab = new BookPanel(db);
		final PublisherPanel pubTab = new PublisherPanel(db);
		final AuthorPanel authorTab = new AuthorPanel(db);
		final ShelfPanel shelfTab = new ShelfPanel(db);
		final WordPanel wordTab = new WordPanel(db);
		final GenrePanel genreTab = new GenrePanel(db);
		final LenderPanel lenderTab = new LenderPanel(db);
		setLayout(new BorderLayout());
		jTabbedPane.addTab("Home", homeTab);
		jTabbedPane.addTab("Buch", bookTab);
		jTabbedPane.addTab("Autor", authorTab);
		jTabbedPane.addTab("Verlag", pubTab);
		jTabbedPane.addTab("Regal", shelfTab);
		jTabbedPane.addTab("Schlagwort", wordTab);
		jTabbedPane.addTab("Genre", genreTab);
		jTabbedPane.addTab("Ausleiher", lenderTab);
		jTabbedPane.setSelectedIndex(0);
		jTabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent changeEvent) {
				switch (jTabbedPane.getSelectedIndex()){
					case 0:
						homeTab.updateAndAddTable();
						break;
					case 1:
						bookTab.generateMetaBoxComponents();
						bookTab.updateAndAddTable();
						break;
					case 2 :
						authorTab.updateAndAddTable();
						break;
					case 3:
						pubTab.updateAndAddTable();
						break;
					case 4:
						shelfTab.updateAndAddTable();
						break;
					case 5:
						wordTab.updateAndAddTable();
						break;
					case 6:
						lenderTab.updateAndAddTable();
						break;
				}
			}
		});
		add(jTabbedPane);
	}
}


