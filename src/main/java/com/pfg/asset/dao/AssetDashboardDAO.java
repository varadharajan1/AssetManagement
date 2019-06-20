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

public class AssetDashboardDAO {

	private static final Logger logger = Logger.getLogger(AssetDashboardDAO.class.getName());
	
	private String selectCount = "SELECT count(*) FROM AssetInfo";
	
	private String distinctDeployedLocationCount = "SELECT count(DISTINCT DeployedLocation) FROM AssetInfo";
	private String distinctDeployedAddressCount = "SELECT count(DISTINCT Address1) FROM AssetInfo";
	private String distinctProductNumberCount = "SELECT count(DISTINCT ProductNumber) FROM AssetInfo";
	private String distinctTrackNameCount = "SELECT count(DISTINCT TrackName) FROM AssetInfo";
	private String distinctOpcoNameCount = "SELECT count(DISTINCT OpcoName) FROM AssetInfo";
	private String distinctDeviceNameCount = "SELECT count(DISTINCT DeviceName) FROM AssetInfo";
	private String distinctOEMNameCount = "SELECT count(DISTINCT OEMName) FROM AssetInfo";
	private String distinctBusinessSegmentCount = "SELECT count(DISTINCT BusinessSegment) FROM AssetInfo";
	private String distinctSerialNumberCount   = "SELECT count(DISTINCT SerialNumber) FROM AssetInfo";
	
	private String distinctTrackName   = "SELECT DISTINCT TrackName FROM AssetInfo ORDER BY TrackName";
	private String distinctBusinessSegment = "SELECT DISTINCT BusinessSegment FROM AssetInfo ORDER BY BusinessSegment";
	private String distinctOpcoName = "SELECT DISTINCT OpcoName FROM AssetInfo ORDER BY OpcoName";
	private String distinctDeviceName = "SELECT DISTINCT DeviceName FROM AssetInfo ORDER BY DeviceName";
	private String distinctOEMName   = "SELECT DISTINCT OEMName FROM AssetInfo ORDER BY OEMName";
	private String distinctProductNumber   = "SELECT DISTINCT ProductNumber FROM AssetInfo ORDER BY ProductNumber";
	private String distinctDeployedLocation   = "SELECT DISTINCT DeployedLocation FROM AssetInfo ORDER BY DeployedLocation";
	private String distinctDeployedAddress   = "SELECT DISTINCT Address1 FROM AssetInfo ORDER BY Address1";
	private String distinctSerialNumber   = "SELECT DISTINCT SerialNumber FROM AssetInfo ORDER BY SerialNumber";

	private String filterTrackName   = "SELECT DISTINCT TrackName FROM AssetInfo WHERE TrackName LIKE ? ORDER BY TrackName";
	private String filterBusinessSegment   = "SELECT DISTINCT BusinessSegment FROM AssetInfo WHERE BusinessSegment LIKE ? ORDER BY BusinessSegment";
	private String filterOpcoName   = "SELECT DISTINCT OpcoName FROM AssetInfo WHERE OpcoName LIKE ? ORDER BY OpcoName";
	private String filterDeviceName   = "SELECT DISTINCT DeviceName FROM AssetInfo WHERE DeviceName LIKE ? ORDER BY DeviceName";
	private String filterOEMName   = "SELECT DISTINCT OEMName FROM AssetInfo WHERE OEMName LIKE ? ORDER BY OEMName";
	private String filterProductNumber   = "SELECT DISTINCT ProductNumber FROM AssetInfo WHERE ProductNumber LIKE ? ORDER BY ProductNumber";
	private String filterDeployedLocation   = "SELECT DISTINCT DeployedLocation FROM AssetInfo WHERE DeployedLocation LIKE ? ORDER BY DeployedLocation";
	private String filterDeployedAddress   = "SELECT DISTINCT Address1 FROM AssetInfo WHERE DeployedAddress1 LIKE ? ORDER BY Address1";
	private String filterSerialNumber   = "SELECT DISTINCT SerialNumber FROM AssetInfo WHERE SerialNumber LIKE ? ORDER BY SerialNumber";

	private String filterByOEMNameCount = "SELECT OEMName, count(*) FROM AssetInfo GROUP BY OEMName ORDER BY OEMName";
	
	public int getNumberOfRows(String column) {
		logger.log(Level.INFO, "getNumberOfRows() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    int noOfRows = 0;
	    try {
	    	conn = DataSourceListener.getAssetDS().getConnection();
			if("PRODUCT".equals(column)) {
				ps = conn.prepareStatement(distinctProductNumberCount);
			} else if ("LOCATION".equals(column)) {
				ps = conn.prepareStatement(distinctDeployedLocationCount);
			} else if ("ADDRESS".equals(column)) {
				ps = conn.prepareStatement(distinctDeployedAddressCount);
			} else if ("TRACK".equals(column)) {
				ps = conn.prepareStatement(distinctTrackNameCount);
			} else if ("OPCO".equals(column)) {
				ps = conn.prepareStatement(distinctOpcoNameCount);
			} else if ("BUSINESS".equals(column)) {
				ps = conn.prepareStatement(distinctBusinessSegmentCount);
			} else if ("DEVICE".equals(column)) {
				ps = conn.prepareStatement(distinctDeviceNameCount);
			} else if ("OEM".equals(column)) {
				ps = conn.prepareStatement(distinctOEMNameCount);
			} else if ("SERIAL".equals(column)) {
				ps = conn.prepareStatement(distinctSerialNumberCount);
			} else {
				ps = conn.prepareStatement(selectCount);
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				noOfRows = rs.getInt(1);
			}
		    logger.log(Level.INFO, "noOfRows: {0}", noOfRows);
	    } catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AssetException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(rs);
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "getNumberOfRows() ended.");
	    
	    return noOfRows;
	}

	public List<String> distinctColumns(String column) {
		logger.log(Level.INFO, "distinctColumns() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    List<String> columnList = new ArrayList<>();
	    try {
	    	conn = DataSourceListener.getAssetDS().getConnection();

			if("PRODUCT".equals(column)) {
				ps = conn.prepareStatement(distinctProductNumber);
			} else if ("LOCATION".equals(column)) {
				ps = conn.prepareStatement(distinctDeployedLocation);
			} else if ("ADDRESS".equals(column)) {
				ps = conn.prepareStatement(distinctDeployedAddress);
			} else if ("TRACK".equals(column)) {
				ps = conn.prepareStatement(distinctTrackName);
			} else if ("OPCO".equals(column)) {
				ps = conn.prepareStatement(distinctOpcoName);
			} else if ("BUSINESS".equals(column)) {
				ps = conn.prepareStatement(distinctBusinessSegment);
			} else if ("DEVICE".equals(column)) {
				ps = conn.prepareStatement(distinctDeviceName);
			} else if ("OEM".equals(column)) {
				ps = conn.prepareStatement(distinctOEMName);
			} else if ("SERIAL".equals(column)) {
				ps = conn.prepareStatement(distinctSerialNumber);
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
	    logger.log(Level.INFO, "distinctColumns() ended.");
	    
	    return columnList;
	}

	public List<String> filterByColumn(String filterType, String filterValue) {
		logger.log(Level.INFO, "filterByColumn() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    List<String> columnList = new ArrayList<>();
	    try {
	    	conn = DataSourceListener.getAssetDS().getConnection();

			logger.log(Level.INFO, "filterType:{0}, filterValue:{1}", new Object[] {filterType, filterValue});

			if("PRODUCT".equals(filterType)) {
				ps = conn.prepareStatement(filterProductNumber);
			} else if ("LOCATION".equals(filterType)) {
				ps = conn.prepareStatement(filterDeployedLocation);
			} else if ("ADDRESS".equals(filterType)) {
				ps = conn.prepareStatement(filterDeployedAddress);
			} else if ("TRACK".equals(filterType)) {
				ps = conn.prepareStatement(filterTrackName);
			} else if ("OPCO".equals(filterType)) {
				ps = conn.prepareStatement(filterOpcoName);
			} else if ("DEVICE".equals(filterType)) {
				ps = conn.prepareStatement(filterDeviceName);
			} else if ("BUSINESS".equals(filterType)) {
				ps = conn.prepareStatement(filterBusinessSegment);
			} else if ("OEM".equals(filterType)) {
				ps = conn.prepareStatement(filterOEMName);
			} else if ("SERIAL".equals(filterType)) {
				ps = conn.prepareStatement(filterSerialNumber);
			}
			ps.setString(1, "%"+ filterValue +"%");
			rs = ps.executeQuery();
			while (rs.next()) {
				columnList.add(rs.getString(1));
			}
		    logger.log(Level.INFO, "filterType '{0}' list: {1}", new Object[] {filterType, columnList.size()});
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
	public Map<String,Integer> getOEMNameCount() {
		logger.log(Level.INFO, "getOEMNameCount() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    Map<String,Integer> result = new TreeMap<>();
	    try {
	    	conn = DataSourceListener.getAssetDS().getConnection();
			ps = conn.prepareStatement(filterByOEMNameCount);
			rs = ps.executeQuery();

			while (rs.next()) {
				String oemName = rs.getString(1);
				int noOfRows = rs.getInt(2);
				result.put(oemName, noOfRows);
		    	logger.log(Level.INFO, "oemName: {0}, noOfRows: {1}", new Object[] {oemName, noOfRows});
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
	    logger.log(Level.INFO, "getOEMNameCount() ended.");
	    
	    return result;
	}
}
