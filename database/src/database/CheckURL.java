package database;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Created with IntelliJ IDEA.
 * User: kevingoy
 * Date: 22.11.12
 * Time: 20:55
 * To change this template use File | Settings | File Templates.
 */
public class CheckURL {
	private Connection dbCon;
	private Statement statement;

	public CheckURL() {
		try {
			dbCon = DriverManager.getConnection("jdbc:postgresql://db.f4.htw-berlin.de:5432/_s0538977__buechersammlung","_s0538977__buechersammlung_generic", "database");
			statement = dbCon.createStatement();
		} catch (Exception e) {
			System.err.println("FATAL ERROR: "+e.getMessage());
			System.exit(0);
		}
	}

}
