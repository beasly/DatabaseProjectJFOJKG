package database;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: kevingoy
 * Date: 10.01.13
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public class EditBookPopUp extends JFrame {


	public EditBookPopUp(final CheckURL db, final String isbn){
		setTitle("Buch aendern.");
		setVisible(true);
		setSize(400, 400);

		JLabel titleLabel = new JLabel("Titel");
		final JTextField titleTextField = new JTextField();
		JLabel priceLabel = new JLabel("Preis");
		final JTextField priceTextField = new JTextField();
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				String title = titleTextField.getText();
				Float price;




        if (!Input.isFloat(priceTextField.getText()))
        {
        JOptionPane jOptionPane = new JOptionPane();
        JOptionPane.showMessageDialog(jOptionPane, "Tragen Sie einen gültigen Preis ein!", "Falsche Eingabe", JOptionPane.ERROR_MESSAGE);
        }     else{


        if (priceTextField.getText() != null) {
					price = Float.parseFloat(priceTextField.getText());
				} else {
					price = null;
				}
				if(title == "" && price == null){
						setVisible(false);
				}else{
					if(title != ""){
						db.executeChanges("UPDATE buch SET titel='"+title+"' WHERE isbn='"+isbn+"'");
						//alter title
					}
					if(price != null){
            db.executeChanges("UPDATE buch SET preis="+price+" WHERE isbn='"+price+"'");
						//alter price
					}
					setVisible(false);
				}
        }
			}
		});
		JButton cancelButton= new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				setVisible(false);
			}
		});

		setLayout(new GridLayout(0,2));
		add(titleLabel);
		add(titleTextField);

		add(priceLabel);
		add(priceTextField);

		add(cancelButton);
		add(okButton);
	}


}

