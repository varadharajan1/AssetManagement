package com.pfg.asset.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.pfg.asset.core.AssetException;
import com.pfg.asset.core.DataSourceListener;

public class AssetDashboardDAO1 {

	private static final Logger logger = Logger.getLogger(AssetDashboardDAO1.class.getName());
	
	private String selectCount = "SELECT count(*) FROM AssetDetails";
	
	private String getCustomerNameCount = "SELECT count(DISTINCT CustomerName) FROM AssetDetails";
	private String getCustomerAddressCount = "SELECT count(DISTINCT CustomerAddress1) FROM AssetDetails";
	private String getProductNumberCount = "SELECT count(DISTINCT ProductNumber) FROM AssetDetails";
	private String getTrackNameCount = "SELECT count(DISTINCT TrackName) FROM AssetDetails";
	private String getOEMNameCount = "SELECT count(DISTINCT OEMName) FROM AssetDetails";
	private String getSerialNumberCount   = "SELECT count(DISTINCT SerialNumber) FROM AssetDetails";
	
	private String filterByTrackName   = "SELECT DISTINCT TrackName FROM AssetDetails ORDER BY TrackName";
	private String filterByOEMName   = "SELECT DISTINCT OEMName FROM AssetDetails ORDER BY OEMName";
	private String filterByProductNumber   = "SELECT DISTINCT ProductNumber FROM AssetDetails ORDER BY ProductNumber";
	private String filterByCustomerName   = "SELECT DISTINCT CustomerName FROM AssetDetails ORDER BY CustomerName";
	private String filterByCustomerAddress   = "SELECT DISTINCT CustomerAddress1 FROM AssetDetails ORDER BY CustomerAddress1";
	private String filterBySerialNumber   = "SELECT DISTINCT SerialNumber FROM AssetDetails ORDER BY SerialNumber";

	private String filterByOEMNameCount = "SELECT count(*) FROM AssetDetails WHERE OEMName=?";

	public int getNumberOfRows(String filter) {
		logger.log(Level.INFO, "AssetDashboardDAO: getNumberOfRows() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    int noOfRows = 0;
	    try {
	    	conn = DataSourceListener.getAssetDS().getConnection();
			if("PRODUCT".equals(filter)) {
				ps = conn.prepareStatement(getProductNumberCount);
			} else if ("CUSTOMER".equals(filter)) {
				ps = conn.prepareStatement(getCustomerNameCount);
			} else if ("ADDRESS".equals(filter)) {
				ps = conn.prepareStatement(getCustomerAddressCount);
			} else if ("TRACK".equals(filter)) {
				ps = conn.prepareStatement(getTrackNameCount);
			} else if ("OEM".equals(filter)) {
				ps = conn.prepareStatement(getOEMNameCount);
			} else if ("SERIAL".equals(filter)) {
				ps = conn.prepareStatement(getSerialNumberCount);
			} else {
				ps = conn.prepareStatement(selectCount);
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				noOfRows = rs.getInt(1);
			}
		    logger.log(Level.INFO, "AssetDashboardDAO: noOfRows: {0}", noOfRows);
	    } catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AssetException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(rs);
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "AssetDashboardDAO: getNumberOfRows() ended.");
	    
	    return noOfRows;
	}

	public List<String> filterByColumn(String column) {
		logger.log(Level.INFO, "filterByColumn() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    List<String> columnList = new ArrayList<>();
	    try {
	    	conn = DataSourceListener.getAssetDS().getConnection();

			if("PRODUCT".equals(column)) {
				ps = conn.prepareStatement(filterByProductNumber);
			} else if ("CUSTOMER".equals(column)) {
				ps = conn.prepareStatement(filterByCustomerName);
			} else if ("ADDRESS".equals(column)) {
				ps = conn.prepareStatement(filterByCustomerAddress);
			} else if ("TRACK".equals(column)) {
				ps = conn.prepareStatement(filterByTrackName);
			} else if ("OEM".equals(column)) {
				ps = conn.prepareStatement(filterByOEMName);
			} else if ("SERIAL".equals(column)) {
				ps = conn.prepareStatement(filterBySerialNumber);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				columnList.add(rs.getString(1));
			}
		    logger.log(Level.INFO, "column '{0}' list: {1}", new Object[] {column, columnList.size()});
	    } catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AssetException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(rs);
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "filterByColumn() ended.");
	    
	    return columnList;
	}

	public Map<String,Integer> getOEMNameCount(List<String> oemNameList) {
		logger.log(Level.INFO, "AssetDashboardDAO: getOEMCount() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    Map<String,Integer> result = new TreeMap<>();
	    try {
	    	conn = DataSourceListener.getAssetDS().getConnection();
	    	if(oemNameList != null) {
	    		for (String oemName : oemNameList) {
			    	logger.log(Level.INFO, "Filter type: {0}", oemName);
					ps = conn.prepareStatement(filterByOEMNameCount);
					ps.setString(1, oemName);
		
					rs = ps.executeQuery();
					if (rs.next()) {
						int noOfRows = rs.getInt(1);
						result.put(oemName, noOfRows);
						logger.log(Level.INFO, "AssetDashboardDAO: noOfRows: {0}", noOfRows);
					}
	    		}
	    	}
	    } catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AssetException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(rs);
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "AssetDashboardDAO: getOEMCount() ended.");
	    
	    return result;
	}
}
