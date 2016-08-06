package com.test.chan.login.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/userlogin";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "root";

	/**
	 * get user by username
	 * 
	 * @param username
	 * @return
	 * @throws SQLException
	 */
	public static String getUserByLogin(String username) {
		Connection connection = null;
		CallableStatement callStmt = null;
		String sql = "SELECT password from users where user_name=?";
		try {
			// get jdbc connection
			connection = getJDBConnection();
			// create CallableStatement
			callStmt = connection.prepareCall(sql);
			callStmt.setString(1, username);
			// execute select SQL stetement
			callStmt.execute();
			// get data
			ResultSet rs = callStmt.getResultSet();
			while (rs.next()) {
				String password = rs.getString("password");
				return password;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (callStmt != null) {
					callStmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * get user roles
	 * 
	 * @param username
	 * @return
	 * @throws SQLException
	 */
	public static List<String> getUserRoles(String username) {
		List<String> roles = new ArrayList<String>();
		Connection connection = null;
		CallableStatement callStmt = null;
		String sql = "SELECT ur.authority from users u, user_roles ur where u.user_id=ur.user_id and u.user_name=?";
		try {
			// get jdbc connection
			connection = getJDBConnection();
			// create CallableStatement
			callStmt = connection.prepareCall(sql);
			callStmt.setString(1, username);
			// execute select SQL stetement
			callStmt.execute();
			// get data
			ResultSet rs = callStmt.getResultSet();
			while (rs.next()) {
				String role = rs.getString("authority");
				roles.add(role);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (callStmt != null) {
					callStmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return roles;
	}

	/**
	 * connect to database
	 * 
	 * @return
	 */
	private static Connection getJDBConnection() {
		try {
			Class.forName(DB_DRIVER);
			return DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
