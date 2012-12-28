package database;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.swing.*;

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
	private String[] bookTableHeader = new String[]{
					"ISBN", "Titel", "Preis"
	};

	public BookPanel(){
		//get Connection to the database
		CheckURL db = new CheckURL();
		setLayout(new GridLayout(2,1));
		//adds Metabox
		JPanel metaBox = new JPanel();
	  //generate components
		JLabel titleLabel = new JLabel("Titel");
		JTextField titleTextField = new JTextField();
		JLabel priceLabel = new JLabel("Preis");
		JTextField priceTextField = new JTextField();
		JLabel isbnLabel = new JLabel("ISBN");
		JTextField isbnTextField = new JTextField();
		JLabel genreLabel = new JLabel("Genre");
		JComboBox genre = new JComboBox();
		JLabel authorLabel = new JLabel("Autor");
		JComboBox author = new JComboBox();
		JLabel publisherLabel = new JLabel("Verlag");
		JComboBox publisher = new JComboBox();
		JLabel shelfLabel = new JLabel("Regal");
		JComboBox shelf = new JComboBox();
		JLabel dateLabel = new JLabel("Datum");
		Date date	 = GregorianCalendar.getInstance(Locale.GERMANY).getTime();
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		JTextField dateTextField = new JTextField(formater.format(date));
		JLabel wordLabel = new JLabel("Schlagwort");
		JComboBox word = new JComboBox();


		//layouting    and adding components
		metaBox.setLayout(new GridLayout(0,2));

		metaBox.add(titleLabel);
		metaBox.add(titleTextField);

		metaBox.add(priceLabel);
		metaBox.add(priceTextField);

		metaBox.add(isbnLabel);
		metaBox.add(isbnTextField);

		metaBox.add(genreLabel);
		metaBox.add(genre);

		metaBox.add(authorLabel);
		metaBox.add(author);

		metaBox.add(publisherLabel);
		metaBox.add(publisher);

		metaBox.add(shelfLabel);
		metaBox.add(shelf);

		metaBox.add(dateLabel);
		metaBox.add(dateTextField);

		metaBox.add(wordLabel);
		metaBox.add(word);

		add(metaBox);

		//then get the resultset for the table
		ResultSet bookResult = db.executeSelect("Select * from Buch;");
		bookTable = new JTable(getTableContent(bookResult, bookTableHeader.length), bookTableHeader);


		JScrollPane scrollPane = new JScrollPane(bookTable);
		add(scrollPane);

	}



	public String[][] getTableContent(ResultSet bookResult, int columnLength){
		String[][] tableContent = null;
		try {
			//getrowCount
			int rowCount = 0;
			while (bookResult.next()) {
				rowCount++;
			}
			bookResult.beforeFirst();
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

}
