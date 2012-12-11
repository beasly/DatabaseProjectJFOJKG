package database;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

/**
 * Created with IntelliJ IDEA.
 * User: kevingoy
 * Date: 22.11.12
 * Time: 20:55
 * To change this template use File | Settings | File Templates.
 */
public class CheckURL {
	private Connection dbCon;

	public CheckURL() {
		try {
			try{
				Class.forName("org.postgresql.Driver"); }
			catch (Exception e) {
				System.err.println("Driver not found: "+e.toString()); System.exit(0);
			}

			dbCon = DriverManager.getConnection("localhost:8889","root", "root");
		} catch (Exception e) {
			System.err.println("FATAL ERROR: "+e.getMessage());
		}
	}

}
