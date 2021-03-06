package database.tabs;

import database.CheckURL;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The Panel is a tabbedpanel of the main program. It consists of a metabox,
 * where you can insert a word in the database, and a table with an overview
 * over the table schlagwort from the database.
 *
 * @version RC 1.0
 */
public class WordPanel extends JPanel {

	/**
	 * the metabox to add a new word
	 */
	private JPanel metaBox = new JPanel();
	/**
	 * the table with an overview of all words
	 */
	private JTable wordTable;
	/**
	 * the scrollpane to make the table scrollable
	 */
	private JScrollPane scrollPane = new JScrollPane(wordTable);
	/**
	 * the array with the the strings for the tableheader
	 */
	private String[] wordTableHeader = new String[]{"Schlagwort"};
	/**
	 * the connection to the database
	 */
	private CheckURL db;
	/**
	 * Constructor to initialize the panel.
	 *
	 * @param db	connection to the database
	 */
	public WordPanel(CheckURL db) {
		this.db = db;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		generateMetaBoxComponents();
		updateAndAddTable();
	}

	public void updateAndAddTable() {
		ResultSet shelfSet = db.executeSelect("Select * from Schlagwort");
		wordTable = new JTable(getTableContent(shelfSet, wordTableHeader.length), wordTableHeader) {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;   //Disallow the editing of any cell
			}
		};
		wordTable.setAutoCreateRowSorter(true);
		wordTable.addMouseListener(new MouseListener() {
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
		scrollPane = new JScrollPane(wordTable);
		add(scrollPane);

	}

	private void generateMetaBoxComponents() {
		final JLabel residenceLabel = new JLabel("Schlagwort");
		final JTextField residenceTextField = new JTextField();

		JButton okButton = new JButton("Hinzufügen");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				getAllContentOfComponentsAndInsert(residenceTextField);
			}
		});
		JButton deleteButton = new JButton("Schlagwort löschen");
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				try {
					int wordID = getID("Select schlagwortid FROM schlagwort where schlagwort ='", String.valueOf(wordTable.getValueAt(wordTable.getSelectedRow(), 0)));
					if (JOptionPane.showConfirmDialog(null, "Moechten Sie das Schlagwort wirklich loeschen?", "Schlagwort loeschen", JOptionPane.YES_NO_CANCEL_OPTION) == 0) {
						if (db.executeChanges("Delete From Schlagwort where Schlagwortid = '" + wordID + "'") == 0) {
							JOptionPane.showMessageDialog(null, "Das Schlagwort kann nicht geloescht werden, da das Schlagwort noch auf Buecher referenziert.");
						}
						updateAndAddTable();
					}
				} catch (IndexOutOfBoundsException e) {
					JOptionPane.showMessageDialog(null, "Bitte waehlen Sie einen Datensatz.");
				}
			}
		});

		metaBox.setLayout(new GridLayout(0, 2));
		metaBox.add(residenceLabel);
		metaBox.add(residenceTextField);
		metaBox.add(deleteButton);
		metaBox.add(okButton);

		metaBox.setPreferredSize(new Dimension(800, 50));
		metaBox.setMinimumSize(new Dimension(800, 50));
		metaBox.setMaximumSize(new Dimension(800, 50));

		add(metaBox);

	}

	private void makeUpdatePopUp() {
		final String word = String.valueOf(wordTable.getValueAt(wordTable.getSelectedRow(), 0));
		final int wordID = getID("Select schlagwortid FROM schlagwort where schlagwort ='", word);
		final JFrame updateFrame = new JFrame();
		updateFrame.setTitle("Schlagwort aendern.");
		updateFrame.setVisible(true);
		updateFrame.setAlwaysOnTop(true);
		updateFrame.setSize(400, 100);
		JLabel wordLabel = new JLabel("Schlagwort");
		final JTextField wordTextField = new JTextField();
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				String newWord = String.valueOf(wordTextField.getText());
				if (! wordTextField.getText().equals("")) {
					db.executeChanges("UPDATE schlagwort SET schlagwort='" + newWord + "' WHERE schlagwortid='" + wordID + "'");
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
		updateFrame.add(wordLabel);
		updateFrame.add(wordTextField);
		updateFrame.add(cancelButton);
		updateFrame.add(okButton);
	}

	private void getAllContentOfComponentsAndInsert(JTextField wordTextField) {
		String word = wordTextField.getText();
		boolean validName = true;
		ResultSet shelfSet = db.executeSelect("Select * from Schlagwort");
		try {
			while (shelfSet.next()) {
				if (shelfSet.getString(2).equals(word)) {
					validName = false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (validName) {
			db.executeChanges("INSERT INTO schlagwort Values(DEFAULT,'" + word + "')");
			updateAndAddTable();
		} else {
			JOptionPane jOptionPane = new JOptionPane();
			JOptionPane.showMessageDialog(jOptionPane, "Das Schlagwort ist bereits vorhanden.", "Schlagwortfehler", JOptionPane.ERROR_MESSAGE);
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
				tableContent[rowIndex][0] = resultSet.getString("schlagwort");
				rowIndex++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tableContent;
	}
}



