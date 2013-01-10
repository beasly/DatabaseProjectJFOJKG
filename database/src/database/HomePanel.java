package database;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HomePanel extends JPanel{
	private JTable homeTable;

	private String[] homeTableHeader = new String[]{"Buch", "Autor" , "Verlag", "Genre", "Schlagwort", "Regal", "Ausleiher"};


	public HomePanel(CheckURL db){
//metabox evtl. but first gridlayout with one component
		setLayout(new GridLayout(1,1));

		ResultSet bookResults = db.executeSelect("select * from overview;");
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
