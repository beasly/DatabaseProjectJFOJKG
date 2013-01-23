package database;

import java.sql.*;
import java.util.LinkedList;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: kevingoy
 * Date: 22.11.12
 * Time: 20:55
 * To change this template use File | Settings | File Templates.
 *
 * Change Comment
 * User: Jufi
 * Date: 22.12.12
 * Time: 17:30
 *
 * I added some methods to execute SQL Queries. Those methods uses the connection object (dbCon), which is created by the constructor
 * of <code> CheckURL </code>. SQL Queries have to be passed without semicolons at the end.
 *
 *
 */
public class CheckURL {

	/**
	 * the connection object to connect with the database
	 */
	private Connection dbCon = null;
	/**
	 * the sql statement
	 */
	private Statement statement;


	public CheckURL() {
		try {
			dbCon = DriverManager.getConnection("jdbc:postgresql://db.f4.htw-berlin.de:5432/_s0538977__buechersammlung", "_s0538977__buechersammlung_generic", "database");
			statement = dbCon.createStatement();
		} catch (Exception e) {
			System.err.println("FATAL ERROR: "+e.getMessage());
			System.exit(0);
		}

  }

	/**
	 * To close the connection to the database.
	 *
	 */
	public void close_database() {
		try {
			dbCon.close();
			System.out.println("Database closed");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

  /**
   * To execute a select query.
   *
   * @author Julian Fink
   * @param sql SQL Query
   * @return Matched tupels are returned into an ResultSet object
   */
   public ResultSet executeSelect(String sql)
  {
		ResultSet result = null;
		try {
      Statement stm = dbCon.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
      result = stm.executeQuery(sql);
    }
    catch(SQLException e) {
      e.printStackTrace();
			JOptionPane jOptionPane = new JOptionPane();
			JOptionPane.showMessageDialog(jOptionPane, "Es ist ein Fehler aufgetreten, bitte wechseln Sie den Tab und versuchen Sie es erneut.", "Fehler", JOptionPane.ERROR_MESSAGE);
		}
    return result;
  }

  /**
   * To execute an INSERT, UPDATE or DELETE statement. Returns -1 in case of exception.
   *
   * @author Julian Fink
   * @param sql SQL Query
   * @return Amount of changed tupels.
   */
  public int executeChanges(String sql)
  {
    int result = 0;
    try {
      Statement stm = dbCon.createStatement();
      stm.execute(sql);
      result = stm.getUpdateCount();
    }
    catch(SQLException e) {
      e.printStackTrace();
			JOptionPane jOptionPane = new JOptionPane();
			JOptionPane.showMessageDialog(jOptionPane, "Es ist ein Fehler aufgetreten, bitte wechseln Sie den Tab und versuchen Sie es erneut.", "Fehler", JOptionPane.ERROR_MESSAGE);
			result = -1;
    }
    return result;
  }

	/**
	 * A method to write a column of the resultset into a string array.
	 *
	 * @param resultSet	the resultset to convert
	 * @param columnIndex	the column from the resultset
	 * @return	the column as a string array
	 */
	public String[] resultSetToStringArray(ResultSet resultSet, int columnIndex) {
		LinkedList<String> resultList = new LinkedList<String>();

		try {
			while (resultSet.next()) {
				resultList.add(resultSet.getString(columnIndex));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultList.toArray(new String[0]);
	}

	/**
	 * A method to write two column of the resultset into a string array.
	 * I.e. to get a name out of two columns.
	 *
	 * @param resultSet	the resultset to convert
	 * @param columnIndex	the column from the resultset
	 * @param columnIndexTwo	the column from the resultset
	 * @return	the column as a string array
	 */
	public String[] resultSetToStringArrayWithTwo(ResultSet resultSet, int columnIndex, int columnIndexTwo) {
		LinkedList<String> resultList = new LinkedList<String>();

		try {
			while (resultSet.next()) {
				resultList.add(resultSet.getString(columnIndex) +", "+ resultSet.getString(columnIndexTwo));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultList.toArray(new String[0]);
	}

	/**
	 * A method to disable the auto commit function of the database connection.
	 */
  public void disableAutoCommit() {
    try {
      dbCon.setAutoCommit(false);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

	/**
	 * A method to enable the auto commit function of the database connection.
	 */
  public void enableAutoCommit() {
    try {
      dbCon.setAutoCommit(true);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

	/**
	 * A method to commit the transaction to the database.
	 */
  public void commitTransaction() {
    try {
      dbCon.commit();
    } catch (SQLException e) {
      e.printStackTrace();
		}
  }



}




