package com.pfg.asset.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOUtil {

	private static final Logger logger = Logger.getLogger(DAOUtil.class.getName());
  
	private DAOUtil() { }
	
	public static void close(Connection connection) {
		if (connection == null) {
			return;
		}
		try {
			connection.close();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Cannot close connection" + e);
		}
	}
  
	public static void close(Statement statement) {
		if (statement == null) {
			return;
		}
		try {
			statement.close();
		} catch (Exception e) {
			logger.log(Level.INFO, "Cannot close statement" + e);
		}
	}
  
	public static void closePreparedStatement(PreparedStatement pstatement) {
		if (pstatement == null) {
			return;
		}
		try {
			pstatement.close();
		}catch (Exception e){
			logger.log(Level.INFO, "Cannot close PreparedStatement" + e);
		}
	}

	public static void close(ResultSet rs) {
		if (rs == null) {
			return;
		}
		try {
			rs.close();
		}catch (Exception e){
			logger.log(Level.INFO, "Cannot close ResultSet" + e);
		}
	}
}
