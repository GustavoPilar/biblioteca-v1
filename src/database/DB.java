package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class DB {

	private static final String URL = "jdbc:mysql://localhost:3306/biblioteca";
	private static final String USER = "developer";
	private static final String PASSWORD = "1234567";
	
	private static Connection conn = null;
	
	public static Connection getConnection() {
		if (conn == null) {
			try {
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
			}
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		
		return conn;
	}
	
	public static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			}
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			}
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			}
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
}
