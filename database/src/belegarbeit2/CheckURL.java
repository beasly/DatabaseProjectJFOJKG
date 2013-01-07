package belegarbeit2;

import java.io.PrintStream;
import java.sql.*;
import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: kevingoy
 * Date: 22.11.12
 * Time: 20:55
 * To change this template use File | Settings | File Templates.
 * <p/>
 * Change Comment
 * User: Jufi
 * Date: 22.12.12
 * Time: 17:30
 * <p/>
 * I added some methods to execute SQL Queries. Those methods uses the connection object (dbCon), which is created by the constructor
 * of <code> CheckURL </code>. SQL Queries have to be passed without semicolons at the end.
 */
public class CheckURL {

	private Connection dbCon = null;
	private ResultSet result = null;

	public CheckURL() {
		try {

			dbCon = DriverManager.getConnection("jdbc:postgresql://db.f4.htw-berlin.de:5432/_s0538977__belegarbeit2", "_s0538977__belegarbeit2_generic", "database");
			System.out.println();
			System.out.println("Connection established.");
		} catch (Exception e) {
			System.err.println("FATAL ERROR: " + e.getMessage());
			System.exit(0);
		}

	}

	public void close_database() {
		try {
			dbCon.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Database closed");
	}

	/**
	 * To execute a select query.
	 *
	 * @param sql SQL Query
	 * @return Matched tupels are returned into an ResultSet object
	 * @author Julian Fink
	 */
	public ResultSet executeSelect(String sql) {
		try {
			Statement stm = dbCon.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			result = stm.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * To execute an INSERT, UPDATE or DELETE statement. Returns -1 in case of exception.
	 *
	 * @param sql SQL Query
	 * @return Amount of changed tupels.
	 * @author Julian Fink
	 */
	public int executeChanges(String sql) {
		int result = 0;
		try {
			Statement stm = dbCon.createStatement();
			stm.execute(sql);
			result = stm.getUpdateCount();
		} catch (SQLException e) {
			e.printStackTrace();
			result = - 1;
		}
		return result;
	}
}





