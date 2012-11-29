package database;

import java.sql.Driver;

/**
 * Created with IntelliJ IDEA.
 * User: kevingoy
 * Date: 22.11.12
 * Time: 20:55
 * To change this template use File | Settings | File Templates.
 */
public class CheckURL {

	public CheckURL() {
		try {
			Driver driver = (Driver) Class.forName("symantex.itools.db.jdbc.Driver").newInstance();
			// "org.postgresql.Driver").newInstance();
			String dbURL = "jdbc:dbaw://localhost:8888/Uni";
			if (driver.acceptsURL(dbURL)) {
				System.out.println("URL is valid");
			} else {
				System.out.println("URL is not valid");
			}
		} catch (Exception e) {
			System.err.println("FATAL ERROR: "+e.getMessage());
		}
	}

}
