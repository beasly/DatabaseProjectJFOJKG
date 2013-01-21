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
 * Time: 15:41
 * To change this template use File | Settings | File Templates.
 */
public class GenrePanel extends JPanel {

	private JPanel metaBox = new JPanel();
	private JTable genreTable;
	private JScrollPane scrollPane = new JScrollPane(genreTable);
	private String[] genreTableHeader = new String[]{"Genre"};
	private CheckURL db;

	public GenrePanel(CheckURL db) {
		this.db = db;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		generateMetaBoxComponents();
		updateAndAddTable();
	}

	public void updateAndAddTable() 	{
		ResultSet shelfSet = db.executeSelect("Select * from genre");
		genreTable = new JTable(getTableContent(shelfSet, genreTableHeader.length), genreTableHeader){
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;   //Disallow the editing of any cell
			}
		};
		genreTable.setAutoCreateRowSorter(true);
		genreTable.addMouseListener(new MouseListener() {
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
		scrollPane = new JScrollPane(genreTable);
		add(scrollPane);

	}

	private void generateMetaBoxComponents() {
		final JLabel residenceLabel = new JLabel("Genre");
		final JTextField residenceTextField = new JTextField();

		JButton okButton = new JButton("Hinzufügen");
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

    metaBox.setPreferredSize(new Dimension(800, 50));
    metaBox.setMinimumSize(new Dimension(800, 50));
    metaBox.setMaximumSize(new Dimension(800, 50));

    add(metaBox);

	}

	private void makeUpdatePopUp() {
		final String genre = String.valueOf(genreTable.getValueAt(genreTable.getSelectedRow(), 0));
		final int genreid = getID("Select genreid FROM genre where genre ='", genre);
		final JFrame updateFrame = new JFrame();
		updateFrame.setTitle("Genre aendern.");
		updateFrame.setVisible(true);
		updateFrame.setAlwaysOnTop(true);
		updateFrame.setSize(400, 100);
		JLabel genreLabel = new JLabel("Genre");
		final JTextField genreTextField = new JTextField();
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				String newResidence = String.valueOf(genreTextField.getText());
				if (!genreTextField.getText().equals("")) {
					db.executeChanges("UPDATE genre SET genre='" + newResidence + "' WHERE genreid='" + genreid + "'");
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
		updateFrame.add(genreLabel);
		updateFrame.add(genreTextField);
		updateFrame.add(cancelButton);
		updateFrame.add(okButton);


	}

	private void getAllContentOfComponentsAndInsert(JTextField residenceTextField) {
		String genre = residenceTextField.getText();
		boolean validName = true;
		ResultSet genreSet = db.executeSelect("Select * from genre");
		try {
			while (genreSet.next()) {
				if (genreSet.getString(2).equals(genre)) {
					validName = false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (validName) {
			db.executeChanges("INSERT INTO genre Values(DEFAULT,'" + genre + "')");
			updateAndAddTable();
		} else {
			JOptionPane jOptionPane = new JOptionPane();
			JOptionPane.showMessageDialog(jOptionPane, "Das Genre ist bereits vorhanden.", "Genrefehler", JOptionPane.ERROR_MESSAGE);
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
				tableContent[rowIndex][0] = resultSet.getString("Genre");
				rowIndex++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tableContent;
	}

}
