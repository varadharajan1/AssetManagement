package com.pfg.asset.dao;

import java.io.Reader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.postgresql.PGConnection;
import org.postgresql.copy.CopyManager;

import com.pfg.asset.core.AssetException;
import com.pfg.asset.core.DataSourceListener;
import com.pfg.asset.dto.AssetConfig;

public class AssetConfigDAO {

	private static final Logger logger = Logger.getLogger(AssetConfigDAO.class.getName());
	
	private String selectAssetConfig = "SELECT SMTP_HOST, SMTP_PORT, SMTP_USERNAME, SMTP_PASSWORD, SMPT_SENDER, SMTP_RECIPIENT, SMPT_REPLYTO, RECORDS_PER_PAGE, PAGINATION_COUNT, CRON_EXPRESSION, RENEWAL_PERIOD_OPTIONS, DEFAULT_FILTER_TYPE, DEFAULT_FILTER_VALUE FROM AssetConfig";
	private String queryTableNames = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public'";
	

	public AssetConfig selectAssetConfig() {
		logger.log(Level.INFO, "AssetConfigDAO: selectAssetConfig() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    AssetConfig assetConfig = new AssetConfig();
	    try {
	    	conn = DataSourceListener.getAssetDS().getConnection();

			logger.log(Level.INFO, "AssetConfigDAO: selectAssetConfig SQL: {0} ", selectAssetConfig);
			
			ps = conn.prepareStatement(selectAssetConfig);

			rs = ps.executeQuery();
			if (rs.next()) {
				assetConfig = populateItems(rs);
			}
		    logger.log(Level.INFO, "AssetConfigDAO: selectAssetConfig: {0}", assetConfig);
	    } catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AssetException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(rs);
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "AssetConfigDAO: selectAssetConfig() ended.");
	    
	    return assetConfig;
	}

	public List<String> getTableNames() {
		logger.log(Level.INFO, "getTableNames() executed.");
		
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
				logger.log(Level.INFO, "table name: {0}", tableName);
		   }
	    } catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AssetException(e.getMessage());
	    }
	    finally {
	    	DAOUtil.close(rs);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "getTableNames() ended.");
	    
	    return tableList;
	}

	public List<String> queryTableNames() {
		logger.log(Level.INFO, "queryTableNames() executed.");
		
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
				logger.log(Level.INFO, "queryTableNames: table name: {0}", tableName);
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
	    logger.log(Level.INFO, "queryTableNames() ended.");
	    
	    return tableList;
	}
	
	public long importData(String tableName, Reader reader) {
		logger.log(Level.INFO, "importData() executed.");
		
		Connection conn = null;
		long result = 0L;
		
		try {
			conn = DataSourceListener.getAssetDS().getConnection();
			//CopyManager cm = new CopyManager((BaseConnection) conn);
			CopyManager cm = conn.unwrap(PGConnection.class).getCopyAPI();
			
			result = cm.copyIn("COPY "+ tableName +" FROM STDIN WITH DELIMITER ',' CSV", reader);

		    logger.log(Level.INFO, "{0} row(s) imported.", result);
		} catch (Exception e) {
			logger.log(Level.INFO, e.getMessage(), e);
			throw new AssetException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "importData() ended.");
	    
	    return result;
	}

	private AssetConfig populateItems(ResultSet rs) throws SQLException{
		AssetConfig assetConfig = new AssetConfig();

		assetConfig.setSmtpHost(rs.getString("SMTP_HOST"));
		assetConfig.setSmtpPort(rs.getString("SMTP_PORT"));
		assetConfig.setSmtpUsername(rs.getString("SMTP_USERNAME"));
		assetConfig.setSmtpPassword(rs.getString("SMTP_PASSWORD"));
		assetConfig.setSmtpSender(rs.getString("SMPT_SENDER"));
		assetConfig.setSmtpRecipient(rs.getString("SMTP_RECIPIENT"));
		assetConfig.setSmtpReplyTo(rs.getString("SMPT_REPLYTO"));
		assetConfig.setCronExpression(rs.getString("CRON_EXPRESSION"));
		assetConfig.setRecordsPerPage(rs.getInt("RECORDS_PER_PAGE"));
		assetConfig.setPaginationCount(rs.getInt("PAGINATION_COUNT"));
		assetConfig.setRenewalPeriod(rs.getString("RENEWAL_PERIOD_OPTIONS"));
		assetConfig.setFilterType(rs.getString("DEFAULT_FILTER_TYPE"));
		assetConfig.setFilterValue(rs.getString("DEFAULT_FILTER_VALUE"));
		
		return assetConfig;
	}
}
