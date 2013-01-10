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
		ResultSet authorResultSet = db.executeSelect("Select Name from Autoren;");
		String[] authorArray = db.resultSetToStringArray(authorResultSet, 1);

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
				if (title.equals("") || isbn.equals("")) {
					JOptionPane jOptionPane = new JOptionPane();
					JOptionPane.showMessageDialog(jOptionPane, "Sie haben keinen Titel oder keine ISBN eingetragen.", "Buchtitel", JOptionPane.ERROR_MESSAGE);
				} else {
					if (priceTextField.getText().equals(null)) {
						Double price = Double.parseDouble(priceTextField.getText());
					} else {
						Double price = null;
					}
					String genre = (String) genreBox.getSelectedItem();
					String author = (String) authorBox.getSelectedItem();
					String publisher = (String) publisherBox.getSelectedItem();
					String shelf = (String) shelfBox.getSelectedItem();

					Date date = jDateChooser.getDate();
					String word = (String) wordBox.getSelectedItem();

					//INSERT IN TABLES
			}

		}
	}

	);


	//layouting    and adding components
	metaBox.setLayout(new GridLayout(0,2));

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
			return false;   //Disallow the editing of any cell
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
