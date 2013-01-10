package database;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;

import com.toedter.calendar.JDateChooser;

/**
 * Created with IntelliJ IDEA.
 * User: kevingoy
 * Date: 28.12.12
 * Time: 10:05
 * To change this template use File | Settings | File Templates.
 */
public class BookPanel extends JPanel {

	private JTable bookTable;

	//not all depends on the metabox.. for the first try, i just implemented all book attributes
	private String[] bookTableHeader = new String[]{"ISBN", "Preis", "Titel"};

	public BookPanel(final CheckURL db) {
		setLayout(new GridLayout(2, 1));
		//adds Metabox
		JPanel metaBox = new JPanel();
		//generate components
		JLabel titleLabel = new JLabel("Titel");
		final JTextField titleTextField = new JTextField();
		JLabel priceLabel = new JLabel("Preis");
		final JTextField priceTextField = new JTextField();
		JLabel isbnLabel = new JLabel("ISBN");
		final JTextField isbnTextField = new JTextField();
		JLabel genreLabel = new JLabel("Genre");
		//fill genre combobox
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
		JLabel dateLabel = new JLabel("Datum");
		Date date = GregorianCalendar.getInstance(Locale.GERMANY).getTime();
		final JDateChooser jDateChooser = new JDateChooser();
		jDateChooser.setDateFormatString("dd/MM/yyyy");
		jDateChooser.setDate(date);
		((JTextField)jDateChooser.getDateEditor()).setEditable(false);


		JLabel wordLabel = new JLabel("Schlagwort");
		ResultSet wordResultSet = db.executeSelect("Select Schlagwort from Schlagwort;");
		String[] wordArray = db.resultSetToStringArray(wordResultSet, 1);

		final JComboBox wordBox = new JComboBox(wordArray);
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				String title = titleTextField.getText();
				//ISBN ueber die regulaeren Ausdruck pruefen---> Jufi Methode
				String isbn = isbnTextField.getText();
				Pattern isbnPattern = Pattern.compile("[\\d][\\d][\\d]-[\\d][\\d][\\d][\\d][\\d][\\d][\\d][\\d][\\d][\\d]");
				Matcher m = isbnPattern.matcher(isbn);

				if (title.equals("") || isbn.equals("") || !m.matches()) {
					JOptionPane jOptionPane = new JOptionPane();
					JOptionPane.showMessageDialog(jOptionPane, "Sie haben keinen Titel oder keine bzw. keine gueltige ISBN eingetragen.", "Buchtitel", JOptionPane.ERROR_MESSAGE);
				} else {
					Float price = null;
					if (! priceTextField.getText().equals(null)) {
						price = Float.parseFloat(priceTextField.getText());
					}
					String genre = (String) genreBox.getSelectedItem();
					Object[] author = authorBox.getSelectedObjects();
					String publisher = (String) publisherBox.getSelectedItem();
					String shelf = (String) shelfBox.getSelectedItem();

					Date date = jDateChooser.getDate();
					String word = (String) wordBox.getSelectedItem();

					// SQL HELP

					//GenreID
					int genreid = 0;
					ResultSet rs_genreid = db.executeSelect("SELECT genreid FROM genre WHERE genre ='" + genre + "'");
					try {
						rs_genreid.next();
						genreid = rs_genreid.getInt("genreid");
					} catch (SQLException e) {
						e.printStackTrace();
					}

					//AutorenID
					int autorenid = 0;
					StringTokenizer stringTokenizer = new StringTokenizer(author[0].toString());
					String name  = stringTokenizer.nextToken();
					String vorname = stringTokenizer.nextToken();
					name = name.substring(0, name.length() - 1);
					System.out.println(name + " "+ vorname);
					ResultSet rs_autorenid = db.executeSelect("SELECT autorenid FROM autoren WHERE name='" + name + "' AND vorname='" + vorname + "'");
					try {
						rs_autorenid.next();
						autorenid = rs_autorenid.getInt("autorenid");
					} catch (SQLException e) {
						e.printStackTrace();
					}

					//VerlagID
					int verlagsid = 0;
					ResultSet rs_verlagsid = db.executeSelect("SELECT verlagsid FROM verlag WHERE name='" + publisher + ("'"));
					try {
						rs_verlagsid.next();
						verlagsid = rs_verlagsid.getInt("verlagsid");
					} catch (SQLException e) {
						e.printStackTrace();
					}

					//RegalID
					int regalid = 0;
					ResultSet rs_regalid = db.executeSelect("SELECT regalid FROM regal WHERE ort='" + shelf + ("'"));
					try {
						rs_regalid.next();
						regalid = rs_regalid.getInt("regalid");
					} catch (SQLException e) {
						e.printStackTrace();
					}

					//SchlagwortID
					int schlagwortid = 0;
					ResultSet rs_schlagwortid = db.executeSelect("SELECT schlagwortid FROM schlagwort WHERE schlagwort='" + word + ("'"));
					try {
						rs_schlagwortid.next();
						schlagwortid = rs_schlagwortid.getInt("schlagwortid");
					} catch (SQLException e) {
						e.printStackTrace();
					}

					//INSERT IN TABLES
					db.executeChanges("INSERT INTO buch (isbn, preis, titel) VALUES ('" + isbn + ("', ") + price + (", '") + title + ("')"));
					db.executeChanges("INSERT INTO hatgenre (buch, genre) VALUES ('" + isbn + ("', ") + genreid + (")"));
					db.executeChanges("INSERT INTO geschriebenvon (buch, autoren) VALUES ('" + isbn + ("', ") + autorenid + (")"));
					db.executeChanges("INSERT INTO veroeffentlichtvon (buch, verlag, datum) VALUES ('" + isbn + ("', " + verlagsid + (" , '") + date + ("')")));
					db.executeChanges("INSERT INTO liegtin (buch, regal) VALUES ('" + isbn + ("', ") + regalid + (")"));
					db.executeChanges("INSERT INTO hatschlagwort (buch, schlagwort) VALUES ('" + isbn + ("', ") + schlagwortid + (")"));
				}

			}
		}

		);


	//layouting    and adding components
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

	metaBox.add(dateLabel);
	metaBox.add(jDateChooser);

	metaBox.add(wordLabel);
	metaBox.add(wordBox);
	//empty label becaus of ugly layout
	metaBox.add(new Label(""));
	metaBox.add(okButton);

	add(metaBox);

	//then get the resultset for the table
	ResultSet bookResult = db.executeSelect("Select * from Buch;");
	bookTable=new JTable(getTableContent(bookResult, bookTableHeader.length),bookTableHeader){
		public boolean isCellEditable(int rowIndex, int colIndex) {
			return false;
		}
	};


		bookTable.addMouseListener(new MouseListener() {
		@Override
		public void mouseClicked(MouseEvent mouseEvent) {
			if(mouseEvent.getClickCount() == 2) {
				new EditBookPopUp(db);
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
	JScrollPane scrollPane = new JScrollPane(bookTable);

	add(scrollPane);

}


	public String[][] getTableContent(ResultSet bookResult, int columnLength) {
		String[][] tableContent = null;
		try {
			//getrowCount
			int rowCount = getRowCount(bookResult);
			//set tablecontent
			tableContent = new String[rowCount][columnLength];
			int rowIndex = 0;
			//go through Resultset
			while (bookResult.next()) {
				tableContent[rowIndex][0] = bookResult.getString("ISBN");
				tableContent[rowIndex][1] = String.valueOf(bookResult.getFloat("Preis"));
				tableContent[rowIndex][2] = bookResult.getString("Titel");
				rowIndex++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tableContent;

	}

	private int getRowCount(ResultSet bookResult) throws SQLException {
		int rowCount = 0;
		while (bookResult.next()) {
			rowCount++;
		}
		bookResult.beforeFirst();
		return rowCount;
	}

}
