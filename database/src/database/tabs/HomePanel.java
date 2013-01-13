package database.tabs;

import javax.swing.*;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.CheckURL;

public class HomePanel extends JPanel {

	private JTable homeTable;

	private String[] homeTableHeader = new String[]{"Buch", "Autor", "Verlag", "Genre", "Schlagwort", "Regal", "Ausleiher"};

	private JScrollPane homePane = new JScrollPane();

	public HomePanel(final CheckURL db) {
//metabox evtl. but first gridlayout with one component
		setLayout(new GridLayout(1, 1));


		updateAndAddTable(db);
	}

	public void updateAndAddTable(CheckURL db) {
		ResultSet bookResults = db.executeSelect("select * from overview;");
		homeTable = new JTable(getTableContent(bookResults, homeTableHeader.length), homeTableHeader);
		homeTable.setAutoCreateRowSorter(true);
		homeTable.setRowSelectionAllowed(true);
		remove(homePane);
		homePane = new JScrollPane(homeTable);
		add(homePane);

	}

	//get all content of the table for the home table first, if finished do that for all tables
	public String[][] getTableContent(ResultSet bookResults, int columnLength) {
		//needs changes
		String[][] tableContent = null;
		try {
			int rowAmount = 0;
			while (bookResults.next()) {
				rowAmount++;
			}
			bookResults.beforeFirst();
			tableContent = new String[rowAmount][columnLength];
			int rowIndex = 0;
			while (bookResults.next()) {
				tableContent[rowIndex][0] = bookResults.getString("buch");
				tableContent[rowIndex][1] = bookResults.getString("autor");
				tableContent[rowIndex][2] = bookResults.getString("verlag");
				tableContent[rowIndex][3] = bookResults.getString("genre");
				tableContent[rowIndex][4] = bookResults.getString("schlagwort");
				tableContent[rowIndex][5] = bookResults.getString("regal");
				tableContent[rowIndex][6] = bookResults.getString("ausleiher");
				rowIndex++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tableContent;
	}
}
