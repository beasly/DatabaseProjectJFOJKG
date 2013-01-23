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
 * The Panel is a tabbedpanel of the main programm. It consists of a metabox,
 * where you can insert a publisher in the database, and a table with an overview
 * over the table verlag from the database.
 *
 * @version RC 1.0
 */

public class PublisherPanel extends JPanel{

	/**
	 * the metabox to add a new publisher
	 */
	private JPanel metaBox = new JPanel();
	/**
	 * the table with an overview of all publishers
	 */
	private JTable publisherTable;
	/**
	 * the scrollpane to make the table scrollable
	 */
	private JScrollPane scrollPane = new JScrollPane(publisherTable);
	/**
	 * the array with the the strings for the tableheader
	 */
	private String[] publisherTableHeader = new String[]{"Name", "Ort"};
	/**
	 * the connection to the database
	 */
	private CheckURL db;
	/**
	 * Constructor to initialize the panel.
	 *
	 * @param db	connection to the database
	 */
	public PublisherPanel(CheckURL db) {
		this.db = db;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		generateMetaBoxComponents();
		add(metaBox);
		updateAndAddTable();
	}

	public void updateAndAddTable() 	{
		ResultSet publisherSet = db.executeSelect("Select * from verlag");
		publisherTable = new JTable(getTableContent(publisherSet, publisherTableHeader.length), publisherTableHeader){
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;   //Disallow the editing of any cell
			}
		};
		publisherTable.setAutoCreateRowSorter(true);
		publisherTable.addMouseListener(new MouseListener() {
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
		scrollPane = new JScrollPane(publisherTable);
		add(scrollPane);

	}

	private void generateMetaBoxComponents() {
		JLabel nameLabel = new JLabel("Name");
		final JTextField nameTextField = new JTextField();

		final JLabel residenceLabel = new JLabel("Ort");
		final JTextField residenceTextField = new JTextField();

		JButton okButton = new JButton("Hinzufügen");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				getAllContentOfComponentsAndInsert(nameTextField, residenceTextField);
			}
		});
		JButton deleteButton = new JButton("Verlag löschen");
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				try {
					int publisherID = getID("Select verlagsid FROM verlag where name ='", String.valueOf(publisherTable.getValueAt(publisherTable.getSelectedRow(), 0)));
					if (JOptionPane.showConfirmDialog(null, "Moechten Sie den Verlag wirklich loeschen?", "Verlag loeschen", JOptionPane.YES_NO_CANCEL_OPTION) == 0) {
						if (db.executeChanges("Delete From Verlag where verlagsid = '" + publisherID + "'") == 0) {
							JOptionPane.showMessageDialog(null, "Der Verlag kann nicht geloescht werden, da der Verlag noch auf Buecher referenziert.");
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
		metaBox.add(residenceLabel);
		metaBox.add(residenceTextField);
		metaBox.add(deleteButton);
		metaBox.add(okButton);

    metaBox.setPreferredSize(new Dimension(800, 75));
    metaBox.setMinimumSize(new Dimension(800, 75));
    metaBox.setMaximumSize(new Dimension(800, 75));

    add(metaBox);
	}

	private void makeUpdatePopUp() {
		final String name = String.valueOf(publisherTable.getValueAt(publisherTable.getSelectedRow(), 0));
		final int publisherID = getID("Select verlagsid FROM verlag where name ='", name);
		final JFrame updateFrame = new JFrame();
		updateFrame.setTitle("Verlag aendern.");
		updateFrame.setVisible(true);
		updateFrame.setAlwaysOnTop(true);
		updateFrame.setSize(400, 125);
		JLabel nameLabel = new JLabel("Name");
		final JTextField nameTextField = new JTextField();
		JLabel residenceLabel = new JLabel("Ort");
		final JTextField residenceTextField = new JTextField();
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				String newName = String.valueOf(nameTextField.getText());
				String newResidence = String.valueOf(residenceTextField.getText());
				if (!nameTextField.getText().equals("") || !residenceTextField.getText().equals("")) {
					if(!nameTextField.getText().equals("")){
						db.executeChanges("UPDATE verlag SET name='" + newName + "' WHERE verlagsid='" + publisherID + "'");
					}
					if (!residenceTextField.getText().equals("")) {
						db.executeChanges("UPDATE verlag SET ort='" + newResidence + "' WHERE verlagsid='" + publisherID + "'");
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
		updateFrame.add(residenceLabel);
		updateFrame.add(residenceTextField);
		updateFrame.add(cancelButton);
		updateFrame.add(okButton);


	}

	private void getAllContentOfComponentsAndInsert(JTextField nameTextField, JTextField residenceTextField) {
		String name = nameTextField.getText();
		String residence = residenceTextField.getText();
		boolean validName = true;
		ResultSet publisherSet = db.executeSelect("Select * from Verlag");
		try {
			while (publisherSet.next()) {
				if (publisherSet.getString(2).equals(name) && publisherSet.getString(3).equals(residence)) {
					validName = false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (validName && !name.equals("")) {
			db.executeChanges("INSERT INTO verlag Values(DEFAULT,'" + name + "','" + residence + "')");
			updateAndAddTable();
		} else {
			JOptionPane jOptionPane = new JOptionPane();
			JOptionPane.showMessageDialog(jOptionPane, "Der Verlag ist bereits vorhanden.", "Verlagfehler", JOptionPane.ERROR_MESSAGE);
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
				tableContent[rowIndex][0] = resultSet.getString("Name");
				tableContent[rowIndex][1] = resultSet.getString("Ort");
				rowIndex++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tableContent;
	}
}

