package belegarbeit2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: kevingoy
 * Date: 04.01.13
 * Time: 17:22
 * To change this template use File | Settings | File Templates.
 */
public class FindeAlleCoolenTypenMain {


	public static void main(String[] args) {
		CheckURL db = new CheckURL();
		int input = 0;
		while (input != 6) {
			//just to not interrupt the menu with the stacktrace, because printing stacktrace has a little delay
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("\n1. Ausgabe der Tabelle Freunde");
			System.out.println("2. Ausgabe von Freundschaftsbeziehungen");
			System.out.println("3. Eingabe neuer Freunde");
			System.out.println("4. Loeschen von Freunden");
      System.out.println("5. Aendern von Freunden");
			System.out.println("6. Beenden des Programmes");
			System.out.print("Auswahl: ");
			input = Input.intInput(1, 6);
			switch (input) {
				case 1:
					getFriendTable(db);
					break;
				case 2:
					getFriendRelationship(db);
					break;
				case 3:
					addNewFriend(db);
					break;
				case 4:
					deleteFriend(db);
					break;
        case 5:
          setFriendtable(db);
          break;
				default:
					break;
			}
		}
		db.close_database();
	}

	private static void deleteFriend(CheckURL db) {
		System.out.println("Bitte geben Sie die FreundschaftsID der zu loeschenden Person ein:");
		int friendID = Input.intInput();
		int result = db.executeChanges("delete from freunde where '" + friendID + "' = freundid;");
		if(result <= 0){
			System.out.println("Die FreundID ist nicht vorhanden oder hat keine Freundschaftsbeziehungen.");
		}
	}

	private static void addNewFriend(CheckURL db) {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		//freundid is no serial in belegskript, but in description it is a serial ? ?
		System.out.println("Bitte geben Sie die FreundeID an:");
		int id = Input.intInput();
		System.out.println("Bitte geben Sie die Emailadresse ein:");
		String mail = Input.stringInput();
		System.out.println("Bitte geben SIe den Namen ein:");
		String name = Input.stringInput();
		System.out.println("Bitte geben Sie das Alter ein:");
		int age = Input.intInput();
		System.out.println("Bitte geben Sie eine Beschreibung ein:");
		String description = Input.stringInput();
		System.out.println("Bitte geben Sie ein Gehalt ein:");
		int loan = Input.intInput();
		int result = db.executeChanges("Insert into Freunde values('"+id+"','" + mail + "','" + name + "','" + age + "','" + description + "','" + loan + "');");
		if(result < 0){
			System.out.println("Die Eingabe war nicht korrekt.");
		}
	}

	private static void getFriendRelationship(CheckURL db) {
		System.out.println("Bitte geben Sie die FreundschaftsID von der Person Sie die Freundschaftsbeziehungen sehen wollen:");
		int friendID = Input.intInput();
		ResultSet friends = db.executeSelect("Select f.name, b.freundschaftsgrad from freunde as f , istbefreundetmit as b where '" + friendID + "'=befreundetmit and b.freundvon = f.freundid; ");
		ResultSet name = db.executeSelect("Select name from freunde where '" + friendID + "'= freundid;");
		try {
			name.next();
			if(friends.isBeforeFirst()){
			while (friends.next()) {
				System.out.println(name.getString("name") + " ist befreundet mit " + friends.getString("name") + " mit dem Grad " + friends.getInt("freundschaftsgrad"));
			}}
			else{
				System.out.println("Die FreundID ist nicht vorhanden.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void getFriendTable(CheckURL db) {
		ResultSet friendTable = db.executeSelect("Select * From Freunde;");
		System.out.printf("%-10s%-30s%-30s%-10s%-40s%-10s\n", "FreundeID", "Email", "Name", "Alter", "Beschreibung", "Gehalt");
		try {
			while (friendTable.next()) {
				System.out.printf("%-10d%-30s%-30s%-10s%-40s%-10s\n", friendTable.getInt("freundid"), friendTable.getString("email"), friendTable.getString("name"), friendTable.getInt("Alter"), friendTable.getString("beschreibung"), friendTable.getInt("gehalt"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

  private static void setFriendtable(CheckURL db)
  {
    ResultSet friendTable = db.executeSelect("Select * from Freunde;");
    Input input = new Input();
    int rows = 0;
    int index = 0;
    int freundid = 0;
    String email = "";
    String name = "";
    int alter = 0;
    String beschreibung = "";
    int gehalt = 0;
    boolean check = false;
    System.out.println("Treffen Sie eine Wahl!");
    System.out.println("u - Aendern des Datensatzes");
    System.out.println("n - Naechster Datensatz");
    System.out.println("v - Voriger Datensatz");
    System.out.println("q - Beenden der Funktion");
    char menue = ' ';
    try {
      while (friendTable.next()) {
        rows++;
      }
      friendTable.absolute(1);
      System.out.printf("%-10d%-30s%-30s%-10s%-40s%-10s\n", friendTable.getInt("freundid"), friendTable.getString("email"), friendTable.getString("name"), friendTable.getInt("Alter"), friendTable.getString("beschreibung"), friendTable.getInt("gehalt"));
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    try {
    while (menue != 'q') {
      menue = input.charInput();
      if (menue == 'u' || menue == 'n' || menue == 'v'|| menue == 'q')
      {

        switch (menue)
        {
        case 'u':
          System.out.println("Gebe die Email ein!");
          email = Input.stringInput();
          if (!email.equals(""))
            friendTable.updateString("email", email);

          System.out.println("Gebe den Namen ein!");
          name = Input.stringInput();
          if (!name.equals(""))
            friendTable.updateString("name", name);

          System.out.println("Gebe das Alter ein!");
          alter = Input.stringToInt();
          if (alter != 0)
            friendTable.updateInt("Alter", alter);

          System.out.println("Gebe die Beschreibung ein!");
          beschreibung = Input.stringInput();
          if (!beschreibung.equals(""))
            friendTable.updateString("beschreibung", beschreibung);

          System.out.println("Gebe das Gehalt ein!");
          gehalt = Input.stringToInt();
          if (gehalt != 0)
            friendTable.updateInt("gehalt", gehalt);

          friendTable.updateRow();

          System.out.printf("%-10d%-30s%-30s%-10s%-40s%-10s\n", friendTable.getInt("freundid"), friendTable.getString("email"), friendTable.getString("name"), friendTable.getInt("Alter"), friendTable.getString("beschreibung"), friendTable.getInt("gehalt"));

          break;
        case 'n':
          if (index < rows - 1) {
          friendTable.next();
          index++;
          }
          else {
          friendTable.beforeFirst();
          friendTable.next();
          index = 0;
          }
          System.out.printf("%-10d%-30s%-30s%-10s%-40s%-10s\n", friendTable.getInt("freundid"), friendTable.getString("email"), friendTable.getString("name"), friendTable.getInt("Alter"), friendTable.getString("beschreibung"), friendTable.getInt("gehalt"));
          break;
        case 'v':
          if (index != 0) {
          friendTable.absolute(index);
          index--;
          }
          else {
          friendTable.absolute(rows);
          index = rows - 1;
          }
          System.out.printf("%-10d%-30s%-30s%-10s%-40s%-10s\n", friendTable.getInt("freundid"), friendTable.getString("email"), friendTable.getString("name"), friendTable.getInt("Alter"), friendTable.getString("beschreibung"), friendTable.getInt("gehalt"));
          break;
        default:
          break;
        }

      }
      else
        System.out.println("Bitte geben Sie einen gueltigen Buchstaben ein!");



    }
  }

    catch (Exception e) {
      e.printStackTrace();
    }
  }
}
