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
 * where you can insert a author in the database, and a table with an overview
 * over the table autoren from the database.
 *
 * @version RC 1.0
 */

public class AuthorPanel extends JPanel {

	/**
	 * the metabox to add a new author
	 */
	private JPanel metaBox = new JPanel();
	/**
	 * the table with an overview of all authors
	 */
	private JTable authorTable;
	/**
	 * the scrollpane to make the table scrollable
	 */
	private JScrollPane scrollPane = new JScrollPane(authorTable);
	/**
	 * the array with the the strings for the tableheader
	 */
	private String[] authorTableHeader = new String[]{"Name", "Vorname"};
	/**
	 * the connection to the database
	 */
	private CheckURL db;
	/**
	 * Constructor to initialize the panel.
	 *
	 * @param db	connection to the database
	 */
	public AuthorPanel(CheckURL db) {
		this.db = db;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		generateMetaBoxComponents();
		updateAndAddTable();


	}

	public void updateAndAddTable() {
		ResultSet authorSet = db.executeSelect("Select * from autoren");
		authorTable = new JTable(getTableContent(authorSet, authorTableHeader.length), authorTableHeader) {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;   //Disallow the editing of any cell
			}
		};
		authorTable.setAutoCreateRowSorter(true);
		authorTable.addMouseListener(new MouseListener() {
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
		scrollPane = new JScrollPane(authorTable);
		add(scrollPane);

	}

	private void generateMetaBoxComponents() {
		JLabel nameLabel = new JLabel("Name");
		final JTextField nameTextField = new JTextField();

		final JLabel firstNameLabel = new JLabel("Vorname");
		final JTextField firstNameTextField = new JTextField();

		JButton okButton = new JButton("Hinzufügen");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				getAllContentOfComponentsAndInsert(nameTextField, firstNameTextField);
			}
		});
		JButton deleteButton = new JButton("Autor löschen");
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				String isbn = null;
				try {
					int autorenID = 0;
					final String name = String.valueOf(authorTable.getValueAt(authorTable.getSelectedRow(), 0));
					final String firstName = String.valueOf(authorTable.getValueAt(authorTable.getSelectedRow(), 1));
					ResultSet rs_autorenid = db.executeSelect("SELECT autorenid FROM autoren WHERE name='" + name + "' AND vorname='" + firstName + "'");
					try {
						rs_autorenid.next();
						autorenID = rs_autorenid.getInt("autorenid");
					} catch (SQLException e) {
						e.printStackTrace();
					}
					if (JOptionPane.showConfirmDialog(null, "Moechten Sie den Autor wirklich loeschen?", "Autor loeschen", JOptionPane.YES_NO_CANCEL_OPTION) == 0) {
						if(db.executeChanges("Delete From Autoren where autorenid = '"+autorenID+"'")==0){
							JOptionPane.showMessageDialog(null, "Der Autor kann nicht geloescht werden, da der Autor noch auf Buecher referenziert.");
						}
						updateAndAddTable();
					}
				} catch (IndexOutOfBoundsException e) {
					JOptionPane.showMessageDialog(null, "Bitte waehlen Sie einen Datensatz.");
				}
			}
		});

		metaBox.setLayout(new GridLayout(0, 2));

		metaBox.add(nameLabel);
		metaBox.add(nameTextField);
		metaBox.add(firstNameLabel);
		metaBox.add(firstNameTextField);
		metaBox.add(deleteButton);
		metaBox.add(okButton);

		metaBox.setPreferredSize(new Dimension(800, 75));
		metaBox.setMinimumSize(new Dimension(800, 75));
		metaBox.setMaximumSize(new Dimension(800, 75));

		add(metaBox);

	}

	private void makeUpdatePopUp() {
		int autorenID = 0;
		final String name = String.valueOf(authorTable.getValueAt(authorTable.getSelectedRow(), 0));
		final String firstName = String.valueOf(authorTable.getValueAt(authorTable.getSelectedRow(), 1));
		ResultSet rs_autorenid = db.executeSelect("SELECT autorenid FROM autoren WHERE name='" + name + "' AND vorname='" + firstName + "'");
		try {
			rs_autorenid.next();
			autorenID = rs_autorenid.getInt("autorenid");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		final JFrame updateFrame = new JFrame();
		updateFrame.setTitle("Autor aendern.");
		updateFrame.setVisible(true);
		updateFrame.setAlwaysOnTop(true);
		updateFrame.setSize(400, 125);
		JLabel nameLabel = new JLabel("Name");
		final JTextField nameTextField = new JTextField();
		JLabel firstNameLabel = new JLabel("Vorname");
		final JTextField firstNameTextField = new JTextField();
		JButton okButton = new JButton("OK");
		final int finalAutorenID = autorenID;
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				String newName = String.valueOf(nameTextField.getText());
				String newFirstName = String.valueOf(firstNameTextField.getText());
				if (! nameTextField.getText().equals("") || ! firstNameTextField.getText().equals("")) {
					if (! nameTextField.getText().equals("")) {
						db.executeChanges("UPDATE autoren SET name='" + newName + "' WHERE autorenid='" + finalAutorenID + "'");
					}
					if (! firstNameTextField.getText().equals("")) {
						db.executeChanges("UPDATE autoren SET vorname='" + newFirstName + "' WHERE autorenid='" + finalAutorenID + "'");
					}
					updateFrame.setVisible(false);
					updateAndAddTable();
				}
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
		updateFrame.add(nameLabel);
		updateFrame.add(nameTextField);
		updateFrame.add(firstNameLabel);
		updateFrame.add(firstNameTextField);
		updateFrame.add(cancelButton);
		updateFrame.add(okButton);


	}

	private void getAllContentOfComponentsAndInsert(JTextField nameTextField, JTextField firstNameTextField) {
		String name = nameTextField.getText();
		String firstName = firstNameTextField.getText();
		boolean validName = true;
		ResultSet authorSet = db.executeSelect("Select * from autoren");
		try {
			while (authorSet.next()) {
				if (authorSet.getString(3).equals(name) && authorSet.getString(2).equals(firstName)) {
					validName = false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (validName && ! name.equals("") && ! firstName.equals("")) {
			db.executeChanges("INSERT INTO autoren Values(DEFAULT,'" + name + "','" + firstName + "')");
			updateAndAddTable();
		} else {
			JOptionPane jOptionPane = new JOptionPane();
			JOptionPane.showMessageDialog(jOptionPane, "Der Autor ist bereits vorhanden oder ungueltig.", "AutorFehler", JOptionPane.ERROR_MESSAGE);
		}

	}

	public String[][] getTableContent(ResultSet resultSet, int columnLength) {
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
				tableContent[rowIndex][0] = resultSet.getString("Name");
				tableContent[rowIndex][1] = resultSet.getString("Vorname");
				rowIndex++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tableContent;
	}
}