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
 * Created with IntelliJ IDEA.
 * User: kevingoy
 * Date: 14.01.13
 * Time: 16:07
 * To change this template use File | Settings | File Templates.
 */
public class LenderPanel extends JPanel{


	private JPanel metaBox = new JPanel();
	private JTable lenderTable;
	private JScrollPane scrollPane = new JScrollPane(lenderTable);
	private String[] lenderTableHeader = new String[]{"Name", "Vorname", "Email"};
	private CheckURL db;

	public LenderPanel(CheckURL db) {
		this.db = db;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		generateMetaBoxComponents();
		updateAndAddTable();


	}

	public void updateAndAddTable() 	{
		ResultSet authorSet = db.executeSelect("Select * from ausleiher");
		lenderTable = new JTable(getTableContent(authorSet, lenderTableHeader.length), lenderTableHeader){
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;   //Disallow the editing of any cell
			}
		};
		lenderTable.setAutoCreateRowSorter(true);
		lenderTable.addMouseListener(new MouseListener() {
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
		scrollPane = new JScrollPane(lenderTable);
		add(scrollPane);

	}

	private void generateMetaBoxComponents() {
		JLabel nameLabel = new JLabel("Name");
		final JTextField nameTextField = new JTextField();

		JLabel firstNameLabel = new JLabel("Vorname");
		final JTextField firstNameTextField = new JTextField();

		JLabel mailLabel = new JLabel("Email");
		final JTextField mailTextField = new JTextField();

		JButton okButton = new JButton("Hinzufügen");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				getAllContentOfComponentsAndInsert(nameTextField, firstNameTextField, mailTextField);
			}
		});
		metaBox.setLayout(new GridLayout(0, 2));

		metaBox.add(nameLabel);
		metaBox.add(nameTextField);
		metaBox.add(firstNameLabel);
		metaBox.add(firstNameTextField);
		metaBox.add(mailLabel);
		metaBox.add(mailTextField);
		metaBox.add(new Label(""));
		metaBox.add(okButton);

    metaBox.setPreferredSize(new Dimension(800, 100));
    metaBox.setMinimumSize(new Dimension(800, 100));
    metaBox.setMaximumSize(new Dimension(800, 100));

    add(metaBox);

	}

	private void makeUpdatePopUp() {
		int lenderID = 0;
		final String name = String.valueOf(lenderTable.getValueAt(lenderTable.getSelectedRow(), 0));
		final String firstName = String.valueOf(lenderTable.getValueAt(lenderTable.getSelectedRow(), 1));
		ResultSet rs_lenderid = db.executeSelect("SELECT Ausleiherid FROM ausleiher WHERE name='" + name + "' AND vorname='" + firstName + "'");
		try {
			rs_lenderid.next();
			lenderID = rs_lenderid.getInt("ausleiherid");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		final JFrame updateFrame = new JFrame();
		updateFrame.setTitle("Autor aendern.");
		updateFrame.setVisible(true);
		updateFrame.setAlwaysOnTop(true);
		updateFrame.setSize(400, 150);
		JLabel nameLabel = new JLabel("Name");
		final JTextField nameTextField = new JTextField();
		JLabel firstNameLabel = new JLabel("Vorname");
		final JTextField firstNameTextField = new JTextField();
		JLabel mailLabel = new JLabel("Email");
		final JTextField mailTextField = new JTextField();

		JButton okButton = new JButton("OK");
		final int finalLenderID = lenderID;
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				String newName = nameTextField.getText();
				String newFirstName = firstNameTextField.getText();
				String newMail = mailTextField.getText();
				if (!nameTextField.getText().equals("") || !firstNameTextField.getText().equals("") || !mailTextField.getText().equals("")) {
					if(!nameTextField.getText().equals("")){
						db.executeChanges("UPDATE ausleiher SET name='" + newName + "' WHERE AusleiherID='" + finalLenderID + "'");
					}
					if (!firstNameTextField.getText().equals("")) {
						db.executeChanges("UPDATE ausleiher SET vorname='" + newFirstName + "' WHERE AusleiherID='" + finalLenderID + "'");
					}
					if (!mailTextField.getText().equals("")) {
						db.executeChanges("UPDATE ausleiher SET mail='" + newMail + "' WHERE AusleiherID='" + finalLenderID + "'");
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
		updateFrame.add(mailLabel);
		updateFrame.add(mailTextField);		
		updateFrame.add(cancelButton);
		updateFrame.add(okButton);


	}

	private void getAllContentOfComponentsAndInsert(JTextField nameTextField, JTextField firstNameTextField, JTextField mailTextField) {
		String name = nameTextField.getText();
		String firstName = firstNameTextField.getText();
		String mail = mailTextField.getText();
		boolean validName = true;
		ResultSet authorSet = db.executeSelect("Select * from ausleiher");
		try {
			while (authorSet.next()) {
				if (authorSet.getString(2).equals(name) && authorSet.getString(1).equals(firstName)) {
					validName = false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (validName && !name.equals("") && !firstName.equals("")) {
			db.executeChanges("INSERT INTO ausleiher Values(DEFAULT,'" + name + "','" + firstName + "','" + mail + "')");
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
				tableContent[rowIndex][2] = resultSet.getString("Email");
				rowIndex++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tableContent;
	}

}
