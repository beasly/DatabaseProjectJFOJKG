package database;

import java.sql.*;
import java.util.LinkedList;

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
	private Connection dbCon = null;
	private Statement statement;
  private ResultSet result = null;

	public CheckURL() {
		try {
			dbCon = DriverManager.getConnection("jdbc:postgresql://db.f4.htw-berlin.de:5432/_s0538977__buechersammlung", "_s0538977__buechersammlung_generic", "database");
			statement = dbCon.createStatement();
		} catch (Exception e) {
			System.err.println("FATAL ERROR: "+e.getMessage());
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
   * @author Julian Fink
   * @param sql SQL Query
   * @return Matched tupels are returned into an ResultSet object
   */
   public ResultSet executeSelect(String sql)
  {
    try {
      Statement stm = dbCon.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
      result = stm.executeQuery(sql);
    }
    catch(SQLException e) {
      e.printStackTrace();
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
      boolean st = stm.execute(sql);
      result = stm.getUpdateCount();
    }
    catch(SQLException e) {
      e.printStackTrace();
      result = -1;
    }
    return result;
  }

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

  public void disableAutoCommit() {
    try {
      dbCon.setAutoCommit(false);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void enableAutoCommit() {
    try {
      dbCon.setAutoCommit(true);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void commitTransaction() {
    try {
      dbCon.commit();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }



}




