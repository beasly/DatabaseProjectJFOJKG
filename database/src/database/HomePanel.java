package database;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

public class HomePanel extends JPanel{
	private JTable homeTable;

	private String[] homeTableHeader = new String[]{"Buch", "Autor" , "Verlag","Genre", "Schlagwort", "Regal"};


	public HomePanel(){
		CheckURL db = new CheckURL();
//metabox evtl. but first gridlayout with one component
		setLayout(new GridLayout(1,1));

		ResultSet bookResults = db.executeSelect("Select Titel from Buch;");
		homeTable = new JTable(getTableContent(bookResults, homeTableHeader.length), homeTableHeader);
		homeTable.setRowSelectionAllowed(true);
		add(new JScrollPane(homeTable));
	}

	private int getRowCount(ResultSet bookResults) throws SQLException {
		int rowAmount = 0;
		while (bookResults.next()) {
			rowAmount++;
		}
		bookResults.beforeFirst();
		return rowAmount;
	}


	//get all content of the table for the home table first, if finished do that for all tables
	public String[][] getTableContent(ResultSet bookResults, int columnLength) {
		//needs changes
		String[][] tableContent = null;
		try {

			int rowCount = getRowCount(bookResults);

			tableContent = new String[rowCount][columnLength];
			int rowIndex = 0;
			while (bookResults.next()) {
				tableContent[rowIndex][0] = bookResults.getString("Titel");
				rowIndex++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tableContent;
	}
}
