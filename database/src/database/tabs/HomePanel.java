package database.tabs;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.CheckURL;

public class HomePanel extends JPanel {

	private JTable homeTable;

	private String[] homeTableHeader = new String[]{"Buch", "Autor", "Verlag", "Genre", "Schlagwort", "Regal", "verliehen an"};

	private JScrollPane homePane = new JScrollPane();

	private CheckURL db;

	public HomePanel(CheckURL db) {
		this.db = db;
		setLayout(new GridLayout(1, 1));
		updateAndAddTable();

	}

	public void updateAndAddTable() {
		ResultSet bookResults = db.executeSelect("select * from overview;");
		homeTable = new JTable(getTableContent(bookResults, homeTableHeader.length), homeTableHeader){
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;   //Disallow the editing of any cell
			}
		};
		homeTable.setAutoCreateRowSorter(true);
		homeTable.setRowSelectionAllowed(true);
		final JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.add("Buch ausleihen").addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				//lendBook(homeTable.rowAtPoint(mouseEvent.getPoint()));
			}
		});
		popupMenu.add(new JPopupMenu.Separator());
		popupMenu.add("Buch wiederbekommen").addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				//	getBookBack(homeTable.rowAtPoint(mouseEvent.getPoint()));
			}
		});
		homeTable.setComponentPopupMenu(popupMenu);
		homeTable.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
			if(SwingUtilities.isRightMouseButton(mouseEvent)) {
				homeTable.changeSelection(homeTable.rowAtPoint(mouseEvent.getPoint()), homeTable.rowAtPoint(mouseEvent.getPoint()), false, false);
				popupMenu.show(mouseEvent.getComponent(), mouseEvent.getX(), mouseEvent.getY());
			}
			}
			@Override
			public void mousePressed(MouseEvent mouseEvent) {				 			}
			@Override
			public void mouseReleased(MouseEvent mouseEvent) {			}
			@Override
			public void mouseEntered(MouseEvent mouseEvent) {			}
			@Override
			public void mouseExited(MouseEvent mouseEvent) {			}
		});
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
