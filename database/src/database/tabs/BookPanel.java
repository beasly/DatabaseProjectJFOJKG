package database.tabs;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;

import database.CheckURL;
import database.Input;

/**
 * The Panel is a tabbedpanel of the main program. It consists of a metabox,
 * where you can insert a book in the database, and a table with an overview
 * over the table buch from the database.
 *
 * @version RC 1.0
 */

public class BookPanel extends JPanel {

	/**
	 * the metabox to add a new book
	 */
	private JPanel metaBox = new JPanel();
	/**
	 * the table with an overview of all books
	 */
	private JTable bookTable;
	/**
	 * the scrollpane to make the table scrollable
	 */
	private JScrollPane scrollPane = new JScrollPane(bookTable);
	/**
	 * the array with the the strings for the tableheader
	 */
	private String[] bookTableHeader = new String[]{"ISBN", "Preis", "Titel"};
	/**
	 * the connection to the database
	 */
	private CheckURL db;

	/**
	 * Constructor to initialize the panel.
	 *
	 * @param db	connection to the database
	 */
	public BookPanel(CheckURL db) {
		this.db = db;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		generateMetaBoxComponents();
		updateAndAddTable();
	}

	public void generateMetaBoxComponents() {
		JLabel titleLabel = new JLabel("Titel");
		final JTextField titleTextField = new JTextField();
		JLabel priceLabel = new JLabel("Preis");
		final JTextField priceTextField = new JTextField();
		JLabel isbnLabel = new JLabel("ISBN");
		final JTextField isbnTextField = new JTextField();
		JLabel genreLabel = new JLabel("Genre");

		ResultSet genreResultSet = db.executeSelect("Select Genre from Genre;");
		String[] genreArray = db.resultSetToStringArray(genreResultSet, 1);
		final JComboBox genreBox = new JComboBox(genreArray);

		JLabel authorLabel = new JLabel("Autor");
		ResultSet authorResultSet = db.executeSelect("Select Name, Vorname from Autoren;");
		String[] authorArray = db.resultSetToStringArrayWithTwo(authorResultSet, 1, 2);
		final JComboBox authorBox = new JComboBox(authorArray);

		JLabel publisherLabel = new JLabel("Verlag");
		ResultSet publisherResultSet = db.executeSelect("Select Name from Verlag;");
		String[] publisherArray = db.resultSetToStringArray(publisherResultSet, 1);
		final JComboBox publisherBox = new JComboBox(publisherArray);

		JLabel shelfLabel = new JLabel("Regal");
		ResultSet shelfResultSet = db.executeSelect("Select Ort from Regal;");
		String[] shelfArray = db.resultSetToStringArray(shelfResultSet, 1);
		final JComboBox shelfBox = new JComboBox(shelfArray);

		JLabel wordLabel = new JLabel("Schlagwort");
		ResultSet wordResultSet = db.executeSelect("Select Schlagwort from Schlagwort;");
		String[] wordArray = db.resultSetToStringArray(wordResultSet, 1);
		final JComboBox wordBox = new JComboBox(wordArray);

		JButton okButton = new JButton("Hinzufügen");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				getAllContentOfComponentsAndInsert(titleTextField, isbnTextField, priceTextField, genreBox, authorBox, publisherBox, shelfBox, wordBox, db);
			}
		});
		JButton deleteButton = new JButton("Buch löschen");
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				String isbn = null;
				try {
					isbn = String.valueOf(bookTable.getValueAt(bookTable.getSelectedRow(), 0));
					if (JOptionPane.showConfirmDialog(null, "Moechten Sie das Buch wirklich loeschen?", "Buch loeschen", JOptionPane.YES_NO_CANCEL_OPTION) == 0) {
						db.executeChanges("Delete From buch where isbn='" + isbn + "'");
						updateAndAddTable();
					}
				} catch (IndexOutOfBoundsException e) {
					JOptionPane.showMessageDialog(null, "Bitte waehlen Sie einen Datensatz.");
				}
			}
		});
		remove(metaBox);
		metaBox = new JPanel();

		//layouting  and adding components
		metaBox.setLayout(new GridLayout(0, 2));

		metaBox.add(titleLabel);
		metaBox.add(titleTextField);
		metaBox.add(priceLabel);
		metaBox.add(priceTextField);
		metaBox.add(isbnLabel);
		metaBox.add(isbnTextField);
		metaBox.add(genreLabel);
		metaBox.add(genreBox);
		metaBox.add(authorLabel);
		metaBox.add(authorBox);
		metaBox.add(publisherLabel);
		metaBox.add(publisherBox);
		metaBox.add(shelfLabel);
		metaBox.add(shelfBox);
		metaBox.add(wordLabel);
		metaBox.add(wordBox);
		metaBox.add(deleteButton);
		metaBox.add(okButton);

		metaBox.setPreferredSize(new Dimension(800, 250));
		metaBox.setMinimumSize(new Dimension(800, 250));
		metaBox.setMaximumSize(new Dimension(800, 250));

		add(metaBox);
	}

	public void updateAndAddTable() {
		final ResultSet bookResult = db.executeSelect("Select * from Buch;");
		bookTable = new JTable(getTableContent(bookResult, bookTableHeader.length), bookTableHeader) {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;   //Disallow the editing of any cell
			}
		};
		bookTable.setAutoCreateRowSorter(true);
		bookTable.addMouseListener(new MouseListener() {
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
		scrollPane = new JScrollPane(bookTable);
		add(scrollPane);
	}

	private void getAllContentOfComponentsAndInsert(JTextField titleTextField, JTextField isbnTextField, JTextField priceTextField, JComboBox genreBox, JComboBox authorBox, JComboBox publisherBox, JComboBox shelfBox, JComboBox wordBox, CheckURL db) {
		String title = titleTextField.getText();
		//ISBN ueber die regulaeren Ausdruck pruefen---> Jufi Methode
		String isbn = isbnTextField.getText();
		Pattern isbnPattern = Pattern.compile("[\\d][\\d][\\d]-[\\d][\\d][\\d][\\d][\\d][\\d][\\d][\\d][\\d][\\d]");
		Matcher m = isbnPattern.matcher(isbn);
		ResultSet allIsbnNumbers = db.executeSelect("Select isbn from Buch");
		boolean validIsbn = true;
		try {
			while (allIsbnNumbers.next()) {
				if (allIsbnNumbers.getString(1).equals(isbn)) {
					validIsbn = false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (title.equals("") || isbn.equals("") || ! m.matches() || ! validIsbn) {
			JOptionPane jOptionPane = new JOptionPane();
			JOptionPane.showMessageDialog(jOptionPane, "Sie haben keinen Titel oder keine bzw. keine gueltige ISBN eingetragen.", "Buchtitel", JOptionPane.ERROR_MESSAGE);
		} else {
			Float price = Float.valueOf(0);
			if (! priceTextField.getText().equals("")) {
				price = Float.parseFloat(priceTextField.getText());
			}
			String genre = (String) genreBox.getSelectedItem();
			Object[] author = authorBox.getSelectedObjects();
			String publisher = (String) publisherBox.getSelectedItem();
			String shelf = (String) shelfBox.getSelectedItem();
			String word = (String) wordBox.getSelectedItem();
			//get all ID's
			int genreid = getID("SELECT genreid FROM genre WHERE genre ='", genre);
			int autorenid = 0;
			StringTokenizer stringTokenizer = new StringTokenizer(author[0].toString());
			String name = stringTokenizer.nextToken();
			String firstName = new String();
			//because of more than one firstName
			while (stringTokenizer.hasMoreTokens()) {
				if (! firstName.equals(new String())) {
					firstName += " ";
				}
				firstName += stringTokenizer.nextToken();
			}
			name = name.substring(0, name.length() - 1);
			ResultSet rs_autorenid = db.executeSelect("SELECT autorenid FROM autoren WHERE name='" + name + "' AND vorname='" + firstName + "'");
			try {
				rs_autorenid.next();
				autorenid = rs_autorenid.getInt("autorenid");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			int verlagsid = getID("SELECT verlagsid FROM verlag WHERE name='", publisher);
			int regalid = getID("SELECT regalid FROM regal WHERE ort='", shelf);
			int schlagwortid = getID("SELECT schlagwortid FROM schlagwort WHERE schlagwort='", word);
			//INSERT IN TABLES
			db.disableAutoCommit();
			db.executeChanges("INSERT INTO buch (isbn, preis, titel) VALUES ('" + isbn + ("', ") + price + (", '") + title + ("')"));
			db.executeChanges("INSERT INTO hatgenre (buch, genre) VALUES ('" + isbn + ("', ") + genreid + (")"));
			db.executeChanges("INSERT INTO geschriebenvon (buch, autoren) VALUES ('" + isbn + ("', ") + autorenid + (")"));
			db.executeChanges("INSERT INTO veroeffentlichtvon (buch, verlag) VALUES ('" + isbn + "', " + verlagsid + ")");
			db.executeChanges("INSERT INTO liegtin (buch, regal) VALUES ('" + isbn + ("', ") + regalid + (")"));
			db.executeChanges("INSERT INTO hatschlagwort (buch, schlagwort) VALUES ('" + isbn + ("', ") + schlagwortid + (")"));
			db.commitTransaction();
			db.enableAutoCommit();
			//and update Table
			updateAndAddTable();

		}
	}

	private void makeUpdatePopUp() {
		final String isbn = bookTable.getValueAt(bookTable.getSelectedRow(), 0).toString();
		final JFrame updateFrame = new JFrame();
		updateFrame.setTitle("Buch aendern.");
		updateFrame.setVisible(true);
		updateFrame.setAlwaysOnTop(true);
		updateFrame.setSize(400, 125);
		JLabel titleLabel = new JLabel("Titel");
		final JTextField titleTextField = new JTextField();
		JLabel priceLabel = new JLabel("Preis");
		final JTextField priceTextField = new JTextField();
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				String title = titleTextField.getText();
				Float price = null;
				if (! priceTextField.getText().equals("") && ! Input.isFloat(priceTextField.getText())) {
					JOptionPane jOptionPane = new JOptionPane();
					JOptionPane.showMessageDialog(jOptionPane, "Tragen Sie einen gültigen Preis ein!", "Falsche Eingabe", JOptionPane.ERROR_MESSAGE);
				} else {
					if (! priceTextField.getText().equals("")) {
						price = Float.parseFloat(priceTextField.getText());
					} else {
						price = null;
					}
					if (title == "" && price == 0) {
						updateFrame.setVisible(false);
					} else {
						db.disableAutoCommit();
						if (! title.equals("")) {
							db.executeChanges("UPDATE buch SET titel='" + title + "' WHERE isbn='" + isbn + "'");
							//alter title
						}
						if (! priceTextField.getText().equals("")) {
							db.executeChanges("UPDATE buch SET preis=" + price + " WHERE isbn='" + isbn + "'");
							//alter price
						}
						db.commitTransaction();
						db.enableAutoCommit();
						updateFrame.setVisible(false);
						updateAndAddTable();
					}
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
		updateFrame.add(titleLabel);
		updateFrame.add(titleTextField);
		updateFrame.add(priceLabel);
		updateFrame.add(priceTextField);
		updateFrame.add(cancelButton);
		updateFrame.add(okButton);
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
			//getrowCount		int rowCount = 0;
			while (resultSet.next()) {
				rowCount++;
			}
			resultSet.beforeFirst();
			//set tablecontent
			tableContent = new String[rowCount][columnLength];
			int rowIndex = 0;
			while (resultSet.next()) {
				tableContent[rowIndex][0] = resultSet.getString("ISBN");
				tableContent[rowIndex][1] = String.valueOf(resultSet.getFloat("Preis"));
				tableContent[rowIndex][2] = resultSet.getString("Titel");
				rowIndex++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tableContent;

	}
}
