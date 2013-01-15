package database.tabs;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

import database.CheckURL;

/**
 * Created with IntelliJ IDEA.
 * User: kevingoy
 * Date: 14.01.13
 * Time: 14:44
 * To change this template use File | Settings | File Templates.
 */
public class ShelfPanel extends JPanel {

	private JPanel metaBox = new JPanel();
	private JTable shelfTable;
	private JScrollPane scrollPane = new JScrollPane(shelfTable);
	private String[] shelfTableHeader = new String[]{"Ort"};
	private CheckURL db;

	public ShelfPanel(CheckURL db) {
		this.db = db;
		setLayout(new GridLayout(2, 1));
		generateMetaBoxComponents();
		updateAndAddTable();
	}

	public void updateAndAddTable() 	{
		ResultSet shelfSet = db.executeSelect("Select * from regal");
		shelfTable = new JTable(getTableContent(shelfSet, shelfTableHeader.length), shelfTableHeader){
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;   //Disallow the editing of any cell
			}
		};
		shelfTable.setAutoCreateRowSorter(true);
		shelfTable.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				if (mouseEvent.getClickCount() == 2) {
					makeUpdatePopUp();
				}
			}
			@Override
			public void mousePressed(MouseEvent mouseEvent) {
			}
			@Override
			public void mouseReleased(MouseEvent mouseEvent) {
			}
			@Override
			public void mouseEntered(MouseEvent mouseEvent) {
			}
			@Override
			public void mouseExited(MouseEvent mouseEvent) {
			}
		});
		remove(scrollPane);
		scrollPane = new JScrollPane(shelfTable);
		add(scrollPane);

	}

	private void generateMetaBoxComponents() {
		final JLabel residenceLabel = new JLabel("Ort");
		final JTextField residenceTextField = new JTextField();

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				getAllContentOfComponentsAndInsert(residenceTextField);
			}
		});
		metaBox.setLayout(new GridLayout(0, 2));
		metaBox.add(residenceLabel);
		metaBox.add(residenceTextField);
		metaBox.add(new Label(""));
		metaBox.add(okButton);
	}

	private void makeUpdatePopUp() {
		final String residence = String.valueOf(shelfTable.getValueAt(shelfTable.getSelectedRow(), 0));
		final int shelfID = getID("Select regalid FROM regal where ort ='", residence);
		final JFrame updateFrame = new JFrame();
		updateFrame.setTitle("Regal aendern.");
		updateFrame.setVisible(true);
		updateFrame.setAlwaysOnTop(true);
		updateFrame.setSize(400, 400);
		JLabel residenceLabel = new JLabel("Ort");
		final JTextField residenceTextField = new JTextField();
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				String newResidence = String.valueOf(residenceTextField.getText());
					if (!residenceTextField.getText().equals("")) {
						db.executeChanges("UPDATE regal SET ort='" + newResidence + "' WHERE regalid='" + shelfID + "'");
					}
					updateFrame.setVisible(false);
					updateAndAddTable();

			}
		});
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				updateFrame.setVisible(false);
			}
		});
		updateFrame.setLayout(new GridLayout(0, 2));
		updateFrame.add(residenceLabel);
		updateFrame.add(residenceTextField);
		updateFrame.add(cancelButton);
		updateFrame.add(okButton);


	}

	private void getAllContentOfComponentsAndInsert(JTextField residenceTextField) {
		String residence = residenceTextField.getText();
		boolean validName = true;
		ResultSet shelfSet = db.executeSelect("Select * from Regal");
		try {
			while (shelfSet.next()) {
				if (shelfSet.getString(2).equals(residence)) {
					validName = false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (validName) {
			db.executeChanges("INSERT INTO regal Values(DEFAULT,'" + residence + "')");
			updateAndAddTable();
		} else {
			JOptionPane jOptionPane = new JOptionPane();
			JOptionPane.showMessageDialog(jOptionPane, "Das Regal ist bereits vorhanden.", "Verlagfehler", JOptionPane.ERROR_MESSAGE);
		}

	}

	private int getID(String sql, String idName) {
		int id = 0;
		ResultSet resultSet = db.executeSelect(sql + idName + "'");
		try {
			resultSet.next();
			id = resultSet.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return id;
	}

	private String[][] getTableContent(ResultSet resultSet, int columnLength) {
		String[][] tableContent = null;
		try {
			int rowCount = 0;
			while (resultSet.next()) {
				rowCount++;
			}
			resultSet.beforeFirst();
			tableContent = new String[rowCount][columnLength];
			int rowIndex = 0;
			while (resultSet.next()) {
				tableContent[rowIndex][0] = resultSet.getString("Ort");
				rowIndex++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tableContent;
	}
}
