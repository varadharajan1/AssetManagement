package com.pfg.asset.dao;

import java.io.Reader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.postgresql.PGConnection;
import org.postgresql.copy.CopyManager;

import com.pfg.asset.core.AssetException;
import com.pfg.asset.core.DataSourceListener;

public class LoadAssetData {

	private static final Logger logger = Logger.getLogger(LoadAssetData.class.getName());
	
	private String queryTableNames = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public'";
	
	public List<String> getTableNames() {
		logger.log(Level.INFO, "LoadAssetData: getTableNames() executed.");
		
		List<String> tableList = new ArrayList<String>();

		Connection conn = null;
		DatabaseMetaData dbmd = null;
	    ResultSet rs = null;
	    try {
			conn = DataSourceListener.getAssetDS().getConnection();
			dbmd = conn.getMetaData();

			String[] types = { "TABLE" };
		    rs = dbmd.getTables(null, null, "%", types);

		    while (rs.next()) {
				String tableName = rs.getString("TABLE_NAME");
				tableList.add(tableName);
				logger.log(Level.INFO, "LoadAssetData: table name: {0}", tableName);
		   }
	    } catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AssetException(e.getMessage());
	    }
	    finally {
	    	DAOUtil.close(rs);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "LoadAssetData: getTableNames() ended.");
	    
	    return tableList;
	}

	public List<String> queryTableNames() {
		logger.log(Level.INFO, "LoadAssetData: queryTableNames() executed.");
		
		List<String> tableList = new ArrayList<String>();

		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    try {
			conn = DataSourceListener.getAssetDS().getConnection();
			ps = conn.prepareStatement(queryTableNames);

			rs = ps.executeQuery();

		    while (rs.next()) {
				String tableName = rs.getString("TABLE_NAME");
				tableList.add(tableName);
				logger.log(Level.INFO, "LoadAssetData: queryTableNames: table name: {0}", tableName);
		   }
	    } catch (Exception e) {
			logger.log(Level.INFO, e.getMessage(), e);
			throw new AssetException(e.getMessage());
	    }
	    finally {
	    	DAOUtil.close(rs);
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "LoadAssetData: queryTableNames() ended.");
	    
	    return tableList;
	}
	
	public long importData(String tableName, Reader reader) {
		logger.log(Level.INFO, "LoadAssetData: importData() executed.");
		
		Connection conn = null;
		long result = 0L;
		
		try {
			conn = DataSourceListener.getAssetDS().getConnection();
			//CopyManager cm = new CopyManager((BaseConnection) conn);
			CopyManager cm = conn.unwrap(PGConnection.class).getCopyAPI();
			
			result = cm.copyIn("COPY "+ tableName +" FROM STDIN WITH DELIMITER ',' CSV", reader);

		    logger.log(Level.INFO, "LoadAssetData: {0} row(s) imported.", result);
		} catch (Exception e) {
			logger.log(Level.INFO, e.getMessage(), e);
			throw new AssetException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "LoadAssetData: importData() ended.");
	    
	    return result;
	}
}
