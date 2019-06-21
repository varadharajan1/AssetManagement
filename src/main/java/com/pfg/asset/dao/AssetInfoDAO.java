package com.pfg.asset.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

import com.pfg.asset.core.AssetException;
import com.pfg.asset.core.DataSourceListener;
import com.pfg.asset.dto.AssetInfo;
import com.pfg.asset.dto.FilterParam;
import com.pfg.asset.util.AssetConstants;
import com.pfg.asset.util.Validator;
import com.pfg.asset.util.ValueConvertor;

public class AssetInfoDAO {

	private static final Logger logger = Logger.getLogger(AssetInfoDAO.class.getName());

	private String insertAssetInfo = "INSERT INTO AssetInfo (TrackName, BusinessSegment, OPCOName, DeviceName, OEMName, ContractedThrough, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, SupportStartDate, SupportEndDate, EOLDate, PurchasedDate, PurchasedVendor, InstalledDate, PurchasedCost, DeployedLocation, Address1, Address2, City, State, ZipCode, Country) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private String deleteAssetInfo = "DELETE FROM AssetInfo WHERE TrackName=? AND BusinessSegment=? AND OPCOName=? AND DeviceName=? AND OEMName=? AND ProductNumber=? AND SerialNumber=? AND SupportEndDate=? AND DeployedLocation=? AND Address1=?";
	//private String updateAssetInfo = "UPDATE AssetInfo SET TrackName=?, BusinessSegment=?, OPCOName=?, DeviceName=?, OEMName=?, ContractedThrough=?, ProductNumber=?, ProductDescription=?, Quantity=?, ContractNumber=?, ServiceLevel=?, SerialNumber=?, ServiceLevelDescription=?, SKU=?, SupportStartDate=?, SupportEndDate=?, EOLDate=?, PurchasedDate=?, PurchasedVendor=?, InstalledDate=?, PurchasedCost=?, DeployedLocation=?, Address1=?, Address2=?, City=?, State=?, ZipCode=?, Country=?, LastUpdated=CURRENT_DATE WHERE TrackName=? AND BusinessSegment=? AND OPCOName=? AND DeviceName=? AND OEMName=? AND ContractedThrough=? AND ProductNumber=? AND ProductDescription=? AND Quantity=? AND ContractNumber=? AND ServiceLevel=? AND SerialNumber=? AND ServiceLevelDescription=? AND SKU=? AND SupportStartDate=? AND SupportEndDate=? AND EOLDate=? AND PurchasedDate=? AND PurchasedVendor=? AND InstalledDate=? AND PurchasedCost=? AND DeployedLocation=? AND Address1=? AND Address2=? AND City=? AND State=? AND ZipCode=? AND Country=?";
	private String updateAssetInfo = "UPDATE AssetInfo SET TrackName=?, BusinessSegment=?, OPCOName=?, DeviceName=?, OEMName=?, ContractedThrough=?, ProductNumber=?, ProductDescription=?, Quantity=?, ContractNumber=?, ServiceLevel=?, SerialNumber=?, ServiceLevelDescription=?, SKU=?, SupportStartDate=?, SupportEndDate=?, EOLDate=?, PurchasedDate=?, PurchasedVendor=?, InstalledDate=?, PurchasedCost=?, DeployedLocation=?, Address1=?, Address2=?, City=?, State=?, ZipCode=?, Country=?, LastUpdated=CURRENT_DATE WHERE ~1 AND ~2 AND ~3 AND ~4 AND ~5 AND ~6 AND ~7 AND ~8 AND ~9 AND ~10 AND ~11 AND ~12 AND ~13 AND ~14 AND ~15 AND ~16 AND ~17 AND ~18 AND ~19 AND ~20 AND ~21 AND ~22 AND ~23 AND ~24 AND ~25 AND ~26 AND ~27 AND ~28";
	
	private String selectAll = "SELECT TrackName, BusinessSegment, OPCOName, DeviceName, OEMName, ContractedThrough, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, SupportStartDate, SupportEndDate, EOLDate, PurchasedDate, PurchasedVendor, InstalledDate, PurchasedCost, DeployedLocation, Address1, Address2, City, State, ZipCode, Country, Created, LastUpdated, UpdatedBy FROM AssetInfo ORDER BY SupportStartDate, SupportEndDate";
	private String selectLimit = "SELECT TrackName, BusinessSegment, OPCOName, DeviceName, OEMName, ContractedThrough, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, SupportStartDate, SupportEndDate, EOLDate, PurchasedDate, PurchasedVendor, InstalledDate, PurchasedCost, DeployedLocation, Address1, Address2, City, State, ZipCode, Country, Created, LastUpdated, UpdatedBy FROM AssetInfo ORDER BY SupportStartDate, SupportEndDate OFFSET ? LIMIT ?";
	private String selectCount = "SELECT count(*) FROM AssetInfo";
	
	private String filterByRenewalLimit = "SELECT TrackName, BusinessSegment, OPCOName, DeviceName, OEMName, ContractedThrough, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, SupportStartDate, SupportEndDate, EOLDate, PurchasedDate, PurchasedVendor, InstalledDate, PurchasedCost, DeployedLocation, Address1, Address2, City, State, ZipCode, Country, Created, LastUpdated, UpdatedBy FROM AssetInfo WHERE SupportEndDate <= (CURRENT_DATE + ?::interval) ORDER BY SupportStartDate, SupportEndDate OFFSET ? LIMIT ?";
	private String filterByOEMLimit = "SELECT TrackName, BusinessSegment, OPCOName, DeviceName, OEMName, ContractedThrough, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, SupportStartDate, SupportEndDate, EOLDate, PurchasedDate, PurchasedVendor, InstalledDate, PurchasedCost, DeployedLocation, Address1, Address2, City, State, ZipCode, Country, Created, LastUpdated, UpdatedBy FROM AssetInfo WHERE OEMName=? ORDER BY SupportStartDate, SupportEndDate OFFSET ? LIMIT ?";
	private String filterByBusinessSegmentLimit = "SELECT TrackName, BusinessSegment, OPCOName, DeviceName, OEMName, ContractedThrough, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, SupportStartDate, SupportEndDate, EOLDate, PurchasedDate, PurchasedVendor, InstalledDate, PurchasedCost, DeployedLocation, Address1, Address2, City, State, ZipCode, Country, Created, LastUpdated, UpdatedBy FROM AssetInfo WHERE BusinessSegment=? ORDER BY SupportStartDate, SupportEndDate OFFSET ? LIMIT ?";
	private String filterByOPCONameLimit = "SELECT TrackName, BusinessSegment, OPCOName, DeviceName, OEMName, ContractedThrough, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, SupportStartDate, SupportEndDate, EOLDate, PurchasedDate, PurchasedVendor, InstalledDate, PurchasedCost, DeployedLocation, Address1, Address2, City, State, ZipCode, Country, Created, LastUpdated, UpdatedBy FROM AssetInfo WHERE OPCOName=? ORDER BY SupportStartDate, SupportEndDate OFFSET ? LIMIT ?";
	private String filterByTrackLimit = "SELECT TrackName, BusinessSegment, OPCOName, DeviceName, OEMName, ContractedThrough, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, SupportStartDate, SupportEndDate, EOLDate, PurchasedDate, PurchasedVendor, InstalledDate, PurchasedCost, DeployedLocation, Address1, Address2, City, State, ZipCode, Country, Created, LastUpdated, UpdatedBy FROM AssetInfo WHERE TrackName=? ORDER BY SupportStartDate, SupportEndDate OFFSET ? LIMIT ?";
	private String filterByProductLimit = "SELECT TrackName, BusinessSegment, OPCOName, DeviceName, OEMName, ContractedThrough, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, SupportStartDate, SupportEndDate, EOLDate, PurchasedDate, PurchasedVendor, InstalledDate, PurchasedCost, DeployedLocation, Address1, Address2, City, State, ZipCode, Country, Created, LastUpdated, UpdatedBy FROM AssetInfo WHERE ProductNumber=? ORDER BY SupportStartDate, SupportEndDate OFFSET ? LIMIT ?";
	private String filterBySerialNumberLimit = "SELECT TrackName, BusinessSegment, OPCOName, DeviceName, OEMName, ContractedThrough, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, SupportStartDate, SupportEndDate, EOLDate, PurchasedDate, PurchasedVendor, InstalledDate, PurchasedCost, DeployedLocation, Address1, Address2, City, State, ZipCode, Country, Created, LastUpdated, UpdatedBy FROM AssetInfo WHERE SerialNumber=? ORDER BY SupportStartDate, SupportEndDate OFFSET ? LIMIT ?";
	private String filterByLocationLimit = "SELECT TrackName, BusinessSegment, OPCOName, DeviceName, OEMName, ContractedThrough, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, SupportStartDate, SupportEndDate, EOLDate, PurchasedDate, PurchasedVendor, InstalledDate, PurchasedCost, DeployedLocation, Address1, Address2, City, State, ZipCode, Country, Created, LastUpdated, UpdatedBy FROM AssetInfo WHERE DeployedLocation=? ORDER BY SupportStartDate, SupportEndDate OFFSET ? LIMIT ?";
	private String filterByAddressLimit = "SELECT TrackName, BusinessSegment, OPCOName, DeviceName, OEMName, ContractedThrough, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, SupportStartDate, SupportEndDate, EOLDate, PurchasedDate, PurchasedVendor, InstalledDate, PurchasedCost, DeployedLocation, Address1, Address2, City, State, ZipCode, Country, Created, LastUpdated, UpdatedBy FROM AssetInfo WHERE Address1=? ORDER BY SupportStartDate, SupportEndDate OFFSET ? LIMIT ?";
	private String filterByExpiringDateLimit = "SELECT TrackName, BusinessSegment, OPCOName, DeviceName, OEMName, ContractedThrough, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, SupportStartDate, SupportEndDate, EOLDate, PurchasedDate, PurchasedVendor, InstalledDate, PurchasedCost, DeployedLocation, Address1, Address2, City, State, ZipCode, Country, Created, LastUpdated, UpdatedBy FROM AssetInfo WHERE SupportEndDate BETWEEN ? AND ? ORDER BY SupportStartDate, SupportEndDate OFFSET ? LIMIT ?";
	private String filterByExpiredDateLimit = "SELECT TrackName, BusinessSegment, OPCOName, DeviceName, OEMName, ContractedThrough, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, SupportStartDate, SupportEndDate, EOLDate, PurchasedDate, PurchasedVendor, InstalledDate, PurchasedCost, DeployedLocation, Address1, Address2, City, State, ZipCode, Country, Created, LastUpdated, UpdatedBy FROM AssetInfo WHERE SupportEndDate < CURRENT_DATE ORDER BY SupportStartDate, SupportEndDate OFFSET ? LIMIT ?";

	private String filterByRenewal = "SELECT TrackName, BusinessSegment, OPCOName, DeviceName, OEMName, ContractedThrough, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, SupportStartDate, SupportEndDate, EOLDate, PurchasedDate, PurchasedVendor, InstalledDate, PurchasedCost, DeployedLocation, Address1, Address2, City, State, ZipCode, Country, Created, LastUpdated, UpdatedBy FROM AssetInfo WHERE SupportEndDate <= (CURRENT_DATE + ?::interval) ORDER BY SupportStartDate, SupportEndDate";
	private String filterByOEM = "SELECT TrackName, BusinessSegment, OPCOName, DeviceName, OEMName, ContractedThrough, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, SupportStartDate, SupportEndDate, EOLDate, PurchasedDate, PurchasedVendor, InstalledDate, PurchasedCost, DeployedLocation, Address1, Address2, City, State, ZipCode, Country, Created, LastUpdated, UpdatedBy FROM AssetInfo WHERE OEMName=? ORDER BY SupportStartDate, SupportEndDate";
	private String filterByBusinessSegment = "SELECT TrackName, BusinessSegment, OPCOName, DeviceName, OEMName, ContractedThrough, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, SupportStartDate, SupportEndDate, EOLDate, PurchasedDate, PurchasedVendor, InstalledDate, PurchasedCost, DeployedLocation, Address1, Address2, City, State, ZipCode, Country, Created, LastUpdated, UpdatedBy FROM AssetInfo WHERE BusinessSegment=? ORDER BY SupportStartDate, SupportEndDate";
	private String filterByOPCOName = "SELECT TrackName, BusinessSegment, OPCOName, DeviceName, OEMName, ContractedThrough, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, SupportStartDate, SupportEndDate, EOLDate, PurchasedDate, PurchasedVendor, InstalledDate, PurchasedCost, DeployedLocation, Address1, Address2, City, State, ZipCode, Country, Created, LastUpdated, UpdatedBy FROM AssetInfo WHERE OPCOName=? ORDER BY SupportStartDate, SupportEndDate";
	private String filterByTrack = "SELECT TrackName, BusinessSegment, OPCOName, DeviceName, OEMName, ContractedThrough, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, SupportStartDate, SupportEndDate, EOLDate, PurchasedDate, PurchasedVendor, InstalledDate, PurchasedCost, DeployedLocation, Address1, Address2, City, State, ZipCode, Country, Created, LastUpdated, UpdatedBy FROM AssetInfo WHERE TrackName=? ORDER BY SupportStartDate, SupportEndDate";
	private String filterByProduct = "SELECT TrackName, BusinessSegment, OPCOName, DeviceName, OEMName, ContractedThrough, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, SupportStartDate, SupportEndDate, EOLDate, PurchasedDate, PurchasedVendor, InstalledDate, PurchasedCost, DeployedLocation, Address1, Address2, City, State, ZipCode, Country, Created, LastUpdated, UpdatedBy FROM AssetInfo WHERE ProductNumber=? ORDER BY SupportStartDate, SupportEndDate";
	private String filterBySerialNumber = "SELECT TrackName, BusinessSegment, OPCOName, DeviceName, OEMName, ContractedThrough, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, SupportStartDate, SupportEndDate, EOLDate, PurchasedDate, PurchasedVendor, InstalledDate, PurchasedCost, DeployedLocation, Address1, Address2, City, State, ZipCode, Country, Created, LastUpdated, UpdatedBy FROM AssetInfo WHERE SerialNumber=? ORDER BY SupportStartDate, SupportEndDate";
	private String filterByLocation = "SELECT TrackName, BusinessSegment, OPCOName, DeviceName, OEMName, ContractedThrough, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, SupportStartDate, SupportEndDate, EOLDate, PurchasedDate, PurchasedVendor, InstalledDate, PurchasedCost, DeployedLocation, Address1, Address2, City, State, ZipCode, Country, Created, LastUpdated, UpdatedBy FROM AssetInfo WHERE DeployedLocation=? ORDER BY SupportStartDate, SupportEndDate";
	private String filterByAddress = "SELECT TrackName, BusinessSegment, OPCOName, DeviceName, OEMName, ContractedThrough, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, SupportStartDate, SupportEndDate, EOLDate, PurchasedDate, PurchasedVendor, InstalledDate, PurchasedCost, DeployedLocation, Address1, Address2, City, State, ZipCode, Country, Created, LastUpdated, UpdatedBy FROM AssetInfo WHERE Address1=? ORDER BY SupportStartDate, SupportEndDate";
	private String filterByExpiringDate = "SELECT TrackName, BusinessSegment, OPCOName, DeviceName, OEMName, ContractedThrough, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, SupportStartDate, SupportEndDate, EOLDate, PurchasedDate, PurchasedVendor, InstalledDate, PurchasedCost, DeployedLocation, Address1, Address2, City, State, ZipCode, Country, Created, LastUpdated, UpdatedBy FROM AssetInfo WHERE SupportEndDate BETWEEN ? AND ? ORDER BY SupportStartDate, SupportEndDate";
	private String filterByExpiredDate = "SELECT TrackName, BusinessSegment, OPCOName, DeviceName, OEMName, ContractedThrough, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, SupportStartDate, SupportEndDate, EOLDate, PurchasedDate, PurchasedVendor, InstalledDate, PurchasedCost, DeployedLocation, Address1, Address2, City, State, ZipCode, Country, Created, LastUpdated, UpdatedBy FROM AssetInfo WHERE SupportEndDate < CURRENT_DATE ORDER BY SupportStartDate, SupportEndDate";

	private String filterByRenewalCount = "SELECT count(*) FROM AssetInfo WHERE SupportEndDate <= (CURRENT_DATE + ?::interval)";
	private String filterByOEMCount = "SELECT count(*) FROM AssetInfo WHERE OEMName=?";
	private String filterByBusinessSegmentCount = "SELECT count(*) FROM AssetInfo WHERE BusinessSegment=?";
	private String filterByOPCONameCount = "SELECT count(*) FROM AssetInfo WHERE OPCOName=?";
	private String filterByTrackCount = "SELECT count(*) FROM AssetInfo WHERE TrackName=?";
	private String filterByProductCount = "SELECT count(*) FROM AssetInfo WHERE ProductNumber=?";
	private String filterBySerialNumberCount = "SELECT count(*) FROM AssetInfo WHERE SerialNumber=?";
	private String filterByLocationCount = "SELECT count(*) FROM AssetInfo WHERE DeployedLocation=?";
	private String filterByAddressCount = "SELECT count(*) FROM AssetInfo WHERE Address1=?";
	private String filterByExpiringDateCount = "SELECT count(*) FROM AssetInfo WHERE SupportEndDate BETWEEN ? AND ?";
	private String filterByExpiredDateCount = "SELECT count(*) FROM AssetInfo WHERE SupportEndDate < CURRENT_DATE";

	public int insertAssetInfo(AssetInfo assetInfo) {
		logger.log(Level.INFO, "entered");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    int result = 0;
	    try {
			logger.log(Level.INFO, "assetInfo: {0} ", assetInfo);
		    if(assetInfo != null) {
			    if (!validateAssetInputs(assetInfo)) {
			    	throw new AssetException(AssetConstants.ERROR_REQUIRED_INPUT);
			    }
				conn = DataSourceListener.getAssetDS().getConnection();

				logger.log(Level.INFO, "insert SQL: {0} ", insertAssetInfo);
				
				ps = conn.prepareStatement(insertAssetInfo);
				
				populatePreparedStatement(ps, assetInfo);
				
				result = ps.executeUpdate();

				logger.log(Level.INFO, "{0} row(s) inserted.", result);
		    }else {
				logger.log(Level.INFO, AssetConstants.ERROR_INVALID_INPUT);
		    	throw new AssetException(AssetConstants.ERROR_INVALID_INPUT);
		    }
	    } catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AssetException(e.getMessage());
	    }
	    finally {
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "exited");
	    
	    return result;
	}

	public int batchInsert(List<AssetInfo> assetInfoList) {
		logger.log(Level.INFO, "entered");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    int result = 0;
	    try {
		    if(assetInfoList != null) {
				conn = DataSourceListener.getAssetDS().getConnection();
				conn.setAutoCommit(false);
				
				logger.log(Level.INFO, "insert SQL: {0} ", insertAssetInfo);
				
				ps = conn.prepareStatement(insertAssetInfo);
				for(AssetInfo assetInfo : assetInfoList) {
				    if (!validateAssetInputs(assetInfo)) {
						logger.log(Level.INFO, AssetConstants.ERROR_REQUIRED_INPUT);
				    	continue;
				    }
					
					populatePreparedStatement(ps, assetInfo);
					
					ps.addBatch();
				}
				int[] batchResult = ps.executeBatch();
				conn.commit();
				
				result = IntStream.of(batchResult).sum();
				
				logger.log(Level.INFO, "{0} row(s) inserted.", result);
		    }else {
				logger.log(Level.INFO, AssetConstants.ERROR_INVALID_INPUT);
		    	throw new AssetException(AssetConstants.ERROR_INVALID_INPUT);
		    }
	    } catch (Exception e) {
	    	try {
	    		if(conn != null) {
	    			conn.rollback();
	    		}
	    	}catch(Exception ee) {
				logger.log(Level.SEVERE, ee.getMessage(), ee);
	    	}
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AssetException(e.getMessage());
	    }
	    finally {
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "exited");
	    
	    return result;
	}

	public int deleteAssetInfo(AssetInfo assetInfo) {
		logger.log(Level.INFO, "entered");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    int result = 0;
	    try {
		    if(assetInfo != null) {
		    	if (!validateAssetInputs(assetInfo)) {
			    	throw new AssetException(AssetConstants.ERROR_REQUIRED_INPUT);
			    }
				conn = DataSourceListener.getAssetDS().getConnection();

				logger.log(Level.INFO, "assetDelete SQL: {0} ", deleteAssetInfo);
				
				ps = conn.prepareStatement(deleteAssetInfo);
				ps.setString(1, assetInfo.getTrackName());
				ps.setString(2, assetInfo.getBusinessSegment());
				ps.setString(3, assetInfo.getOpcoName());
				ps.setString(4, assetInfo.getDeviceName());
				ps.setString(5, assetInfo.getOemName());
				ps.setString(6, assetInfo.getProductNumber());
				ps.setString(7, assetInfo.getSerialNumber());
				ps.setObject(8, ValueConvertor.convertToLocalDate(assetInfo.getSupportEndDate(), AssetConstants.DEFAULT_DATEFORMAT));
				ps.setString(9, assetInfo.getDeployedLocation());
				ps.setString(10, assetInfo.getAddress1());

				result = ps.executeUpdate();

				logger.log(Level.INFO, "{0} row(s) deleted.", result);
		    }else {
				logger.log(Level.INFO, AssetConstants.ERROR_INVALID_INPUT);
		    	throw new AssetException(AssetConstants.ERROR_INVALID_INPUT);
		    }
	    } catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AssetException(e.getMessage());
	    }
	    finally {
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "exited");
	    
	    return result;
	}

	public int batchDelete(List<AssetInfo> assetInfoList) {
		logger.log(Level.INFO, "entered");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    int result = 0;
	    try {
		    if(assetInfoList != null) {
				conn = DataSourceListener.getAssetDS().getConnection();
				conn.setAutoCommit(false);
				
				logger.log(Level.INFO, "assetDelete SQL: {0} ", deleteAssetInfo);
				
				ps = conn.prepareStatement(deleteAssetInfo);
				for(AssetInfo assetInfo : assetInfoList) {
					ps.setString(1, assetInfo.getTrackName());
					ps.setString(2, assetInfo.getBusinessSegment());
					ps.setString(3, assetInfo.getOpcoName());
					ps.setString(4, assetInfo.getDeviceName());
					ps.setString(5, assetInfo.getOemName());
					ps.setString(6, assetInfo.getProductNumber());
					ps.setString(7, assetInfo.getSerialNumber());
					ps.setObject(8, ValueConvertor.convertToLocalDate(assetInfo.getSupportEndDate(), AssetConstants.DEFAULT_DATEFORMAT));
					ps.setString(9, assetInfo.getDeployedLocation());
					ps.setString(10, assetInfo.getAddress1());
					ps.addBatch();
				}
				int[] batchResult = ps.executeBatch();
				conn.commit();
				
				result = IntStream.of(batchResult).sum();
				
				logger.log(Level.INFO, "{0} row(s) deleted.", result);
		    }else {
				logger.log(Level.INFO, AssetConstants.ERROR_INVALID_INPUT);
		    	throw new AssetException(AssetConstants.ERROR_INVALID_INPUT);
		    }
	    } catch (Exception e) {
	    	try {
	    		if(conn != null) {
	    			conn.rollback();
	    		}
	    	}catch(Exception ee) {
				logger.log(Level.SEVERE, ee.getMessage(), ee);
	    	}
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AssetException(e.getMessage());
	    }
	    finally {
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "exited");
	    
	    return result;
	}

	public int batchUpdate(List<AssetInfo> assetInfoList, AssetInfo newAssetInfo) {
		logger.log(Level.INFO, "entered");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    int result = 0;
	    try {
		    if(assetInfoList != null) {
				conn = DataSourceListener.getAssetDS().getConnection();
				conn.setAutoCommit(false);
				
				logger.log(Level.INFO, "update SQL: {0} ", updateAssetInfo);
				
				ps = conn.prepareStatement(updateAssetInfo);
				logger.log(Level.INFO, "newAssetInfo: {0} ", newAssetInfo);
				for(AssetInfo assetInfo : assetInfoList) {
					
					logger.log(Level.INFO, "assetInfo: {0} ", assetInfo);

					populatePreparedStatement(ps, newAssetInfo);
					
					ps.setString(29, assetInfo.getTrackName());
					ps.setString(30, assetInfo.getBusinessSegment());
					ps.setString(31, assetInfo.getOpcoName());
					ps.setString(32, assetInfo.getDeviceName());
					ps.setString(33, assetInfo.getOemName());
					ps.setString(34, assetInfo.getContractedThrough());
					ps.setString(35, assetInfo.getProductNumber());
					ps.setString(36, assetInfo.getProductDescription());
					ps.setInt(37, assetInfo.getQuantity());
					ps.setString(38, assetInfo.getContractNumber());
					ps.setString(39, assetInfo.getServiceLevel());
					ps.setString(40, assetInfo.getSerialNumber());
					ps.setString(41, assetInfo.getServiceLevelDescription());
					ps.setString(42, assetInfo.getSku());
					ps.setObject(43, ValueConvertor.convertToLocalDate(assetInfo.getSupportStartDate(), AssetConstants.DEFAULT_DATEFORMAT));
					ps.setObject(44, ValueConvertor.convertToLocalDate(assetInfo.getSupportEndDate(), AssetConstants.DEFAULT_DATEFORMAT));
					ps.setObject(45, ValueConvertor.convertToLocalDate(assetInfo.getEolDate(), AssetConstants.DEFAULT_DATEFORMAT));
					ps.setObject(46, ValueConvertor.convertToLocalDate(assetInfo.getPurchasedDate(), AssetConstants.DEFAULT_DATEFORMAT));
					ps.setString(47, assetInfo.getPurchasedVendor());
					ps.setObject(48, ValueConvertor.convertToLocalDate(assetInfo.getInstalledDate(), AssetConstants.DEFAULT_DATEFORMAT));
					ps.setString(49, assetInfo.getPurchasedCost());
					ps.setString(50, assetInfo.getDeployedLocation());
					ps.setString(51, assetInfo.getAddress1());
					ps.setString(52, assetInfo.getAddress2());
					ps.setString(53, assetInfo.getCity());
					ps.setString(54, assetInfo.getState());
					ps.setString(55, assetInfo.getCountry());
					ps.setString(56, assetInfo.getZipCode());

					ps.addBatch();
				}
				int[] batchResult = ps.executeBatch();
				conn.commit();
				
				result = IntStream.of(batchResult).sum();
				
				logger.log(Level.INFO, "AssetDetailDAO: {0} row(s) modified.", result);
		    }else {
				logger.log(Level.INFO, AssetConstants.ERROR_INVALID_INPUT);
		    	throw new AssetException(AssetConstants.ERROR_INVALID_INPUT);
		    }
	    } catch (Exception e) {
	    	try {
	    		if(conn != null) {
	    			conn.rollback();
	    		}
	    	}catch(Exception ee) {
				logger.log(Level.SEVERE, ee.getMessage(), ee);
	    	}
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AssetException(e.getMessage());
	    }
	    finally {
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "exited");
	    
	    return result;
	}

	public int updateAssetDetails(List<AssetInfo> assetInfoList, AssetInfo newAssetInfo) {
		logger.log(Level.INFO, "entered");
		
	    int result = 0;
	    try {
		    if(assetInfoList != null) {
				for(AssetInfo assetInfo : assetInfoList) {
					result = result + updateAssetDetail(assetInfo, newAssetInfo);
				}
				
				logger.log(Level.INFO, "AssetDetailDAO: {0} row(s) modified.", result);
		    }else {
				logger.log(Level.INFO, AssetConstants.ERROR_INVALID_INPUT);
		    	throw new AssetException(AssetConstants.ERROR_INVALID_INPUT);
		    }
	    } catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AssetException(e.getMessage());
	    }
	    logger.log(Level.INFO, "exited");
	    
	    return result;
	}

	public int updateAssetDetail(AssetInfo assetInfo, AssetInfo newAssetInfo) {
		logger.log(Level.INFO, "entered");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    int result = 0;
	    String query = null;
	    try {
		    if(newAssetInfo != null) {
		    	if (!validateAssetInputs(newAssetInfo)) {
			    	throw new AssetException(AssetConstants.ERROR_REQUIRED_INPUT);
			    }
				conn = DataSourceListener.getAssetDS().getConnection();

				logger.log(Level.INFO, "update SQL before: {0} ", updateAssetInfo);

				query = new String(updateAssetInfo);
				
				query = query.replaceFirst("~1", (assetInfo.getTrackName() == null) ? "TrackName IS NULL" : "TrackName='"+assetInfo.getTrackName()+"'");
				query = query.replaceFirst("~2", (assetInfo.getBusinessSegment() == null) ? "BusinessSegment IS NULL" : "BusinessSegment='"+assetInfo.getBusinessSegment()+"'");
				query = query.replaceFirst("~3", (assetInfo.getOpcoName() == null) ? "OpcoName IS NULL" : "OpcoName='"+assetInfo.getOpcoName()+"'");
				query = query.replaceFirst("~4", (assetInfo.getDeviceName() == null) ? "DeviceName IS NULL" : "DeviceName='"+assetInfo.getDeviceName()+"'");
				query = query.replaceFirst("~5", (assetInfo.getOemName() == null) ? "OemName IS NULL" : "OemName='"+assetInfo.getOemName()+"'");
				query = query.replaceFirst("~6", (assetInfo.getContractedThrough() == null) ? "ContractedThrough IS NULL" : "ContractedThrough='"+assetInfo.getContractedThrough()+"'");
				query = query.replaceFirst("~7", (assetInfo.getProductNumber() == null) ? "ProductNumber IS NULL" : "ProductNumber='"+assetInfo.getProductNumber()+"'");
				query = query.replaceFirst("~8", (assetInfo.getProductDescription() == null) ? "ProductDescription IS NULL" : "ProductDescription='"+assetInfo.getProductDescription()+"'");
				query = query.replaceFirst("~9", (assetInfo.getQuantity() == 0) ? "Quantity IS NULL" : "Quantity='"+assetInfo.getQuantity()+"'");
				query = query.replaceFirst("~10", (assetInfo.getContractNumber() == null) ? "ContractNumber IS NULL" : "ContractNumber='"+assetInfo.getContractNumber()+"'");
				query = query.replaceFirst("~11", (assetInfo.getServiceLevel() == null) ? "ServiceLevel IS NULL" : "ServiceLevel='"+assetInfo.getServiceLevel()+"'");
				query = query.replaceFirst("~12", (assetInfo.getSerialNumber() == null) ? "SerialNumber IS NULL" : "SerialNumber='"+assetInfo.getSerialNumber()+"'");
				query = query.replaceFirst("~13", (assetInfo.getServiceLevelDescription() == null) ? "ServiceLevelDescription IS NULL" : "ServiceLevelDescription='"+assetInfo.getServiceLevelDescription()+"'");
				query = query.replaceFirst("~14", (assetInfo.getSku() == null) ? "SKU IS NULL" : "SKU='"+assetInfo.getSku()+"'");
				query = query.replaceFirst("~15", (assetInfo.getSupportStartDate() == null) ? "SupportStartDate IS NULL" : "SupportStartDate='"+ ValueConvertor.convertToLocalDate(assetInfo.getSupportStartDate(), AssetConstants.DEFAULT_DATEFORMAT)+"'");
				query = query.replaceFirst("~16", (assetInfo.getSupportEndDate() == null) ? "SupportEndDate IS NULL" : "SupportEndDate='"+ ValueConvertor.convertToLocalDate(assetInfo.getSupportEndDate(), AssetConstants.DEFAULT_DATEFORMAT)+"'");
				query = query.replaceFirst("~17", (assetInfo.getEolDate() == null) ? "EolDate IS NULL" : "EolDate='"+ ValueConvertor.convertToLocalDate(assetInfo.getEolDate(), AssetConstants.DEFAULT_DATEFORMAT)+"'");
				query = query.replaceFirst("~18", (assetInfo.getPurchasedDate() == null) ? "PurchasedDate IS NULL" : "PurchasedDate='"+ ValueConvertor.convertToLocalDate(assetInfo.getPurchasedDate(), AssetConstants.DEFAULT_DATEFORMAT)+"'");
				query = query.replaceFirst("~19", (assetInfo.getInstalledDate() == null) ? "InstalledDate IS NULL" : "InstalledDate='"+ ValueConvertor.convertToLocalDate(assetInfo.getInstalledDate(), AssetConstants.DEFAULT_DATEFORMAT)+"'");
				query = query.replaceFirst("~20", (assetInfo.getPurchasedVendor() == null) ? "PurchasedVendor IS NULL" : "PurchasedVendor='"+assetInfo.getPurchasedVendor()+"'");
				query = query.replaceFirst("~21", (assetInfo.getPurchasedCost() == null) ? "PurchasedCost IS NULL" : "PurchasedCost='"+assetInfo.getPurchasedCost()+"'");
				query = query.replaceFirst("~22", (assetInfo.getDeployedLocation() == null) ? "DeployedLocation IS NULL" : "DeployedLocation='"+assetInfo.getDeployedLocation()+"'");
				query = query.replaceFirst("~23", (assetInfo.getAddress1() == null) ? "Address1 IS NULL" : "Address1='"+assetInfo.getAddress1()+"'");
				query = query.replaceFirst("~24", (assetInfo.getAddress2() == null) ? "Address2 IS NULL" : "Address2='"+assetInfo.getAddress2()+"'");
				query = query.replaceFirst("~25", (assetInfo.getCity() == null) ? "City IS NULL" : "City='"+assetInfo.getCity()+"'");
				query = query.replaceFirst("~26", (assetInfo.getState() == null) ? "State IS NULL" : "State='"+assetInfo.getState()+"'");
				query = query.replaceFirst("~27", (assetInfo.getCountry() == null) ? "Country IS NULL" : "Country='"+assetInfo.getCountry()+"'");
				query = query.replaceFirst("~28", (assetInfo.getZipCode() == null) ? "ZipCode IS NULL" : "ZipCode='"+assetInfo.getZipCode()+"'");

				logger.log(Level.INFO, "update SQL after: {0} ", query);

				ps = conn.prepareStatement(query);

				populatePreparedStatement(ps, newAssetInfo);
				result = ps.executeUpdate();

				logger.log(Level.INFO, "{0} row(s) modified.", result);
		    }else {
				logger.log(Level.INFO, AssetConstants.ERROR_INVALID_INPUT);
		    	throw new AssetException(AssetConstants.ERROR_INVALID_INPUT);
		    }
	    } catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AssetException(e.getMessage());
	    }
	    finally {
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "exited");
	    
	    return result;
	}

	public List<AssetInfo> selectAllAssetInfo() {
		logger.log(Level.INFO, "entered");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    List<AssetInfo> assetInfoList = new ArrayList<>();
	    try {
	    	conn = DataSourceListener.getAssetDS().getConnection();

			logger.log(Level.INFO, "selectAll SQL: {0} ", selectAll);
			
			ps = conn.prepareStatement(selectAll);

			rs = ps.executeQuery();
			while (rs.next()) {
				assetInfoList.add(populateItems(rs));
			}
		    logger.log(Level.INFO, "assetInfo list: {0}", assetInfoList.size());
	    } catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AssetException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(rs);
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "exited");
	    
	    return assetInfoList;
	}

	public List<AssetInfo> selectAssetInfo(int currentPage, int recordsPerPage) {
		logger.log(Level.INFO, "entered");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    List<AssetInfo> assetInfoList = new ArrayList<>();
	    try {
	    	conn = DataSourceListener.getAssetDS().getConnection();

			logger.log(Level.INFO, "selectLimit SQL: {0} ", selectLimit);
			
			int offset = (currentPage * recordsPerPage) - recordsPerPage;
			
			ps = conn.prepareStatement(selectLimit);
			ps.setInt(1, offset);
			ps.setInt(2, recordsPerPage);
			
			rs = ps.executeQuery();
			while (rs.next()) {
				assetInfoList.add(populateItems(rs));
			}
		    logger.log(Level.INFO, "assetInfo list: {0}", assetInfoList.size());
	    } catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AssetException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(rs);
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "exited");
	    
	    return assetInfoList;
	}

	public List<AssetInfo> filteredAssetInfo(FilterParam filter) {
		return filteredAssetInfo(filter, false);
	}

	public List<AssetInfo> filteredAssetInfo(FilterParam filter, boolean pagination) {
		logger.log(Level.INFO, "entered");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    List<AssetInfo> assetInfoList = new ArrayList<>();
	    try {
	    	if(filter != null) {
		    	conn = DataSourceListener.getAssetDS().getConnection();
				logger.log(Level.INFO, "Filter type: {0}", filter.getFilterType());

				String query = "";
				if("PRODUCT".equals(filter.getFilterType())) {
					query = pagination ? filterByProductLimit : filterByProduct;
				} else if ("SERIAL".equals(filter.getFilterType())) {
					query = pagination ? filterBySerialNumberLimit : filterBySerialNumber;
				} else if ("LOCATION".equals(filter.getFilterType())) {
					query = pagination ? filterByLocationLimit : filterByLocation;
				} else if ("ADDRESS".equals(filter.getFilterType())) {
					query = pagination ? filterByAddressLimit : filterByAddress;
				} else if ("TRACK".equals(filter.getFilterType())) {
					query = pagination ? filterByTrackLimit : filterByTrack;
				} else if ("OEM".equals(filter.getFilterType())) {
					query = pagination ? filterByOEMLimit : filterByOEM;
				} else if ("OPCO".equals(filter.getFilterType())) {
					query = pagination ? filterByOPCONameLimit : filterByOPCOName;
				} else if ("BUSINESS".equals(filter.getFilterType())) {
					query = pagination ? filterByBusinessSegmentLimit : filterByBusinessSegment;
				} else if ("DATE".equals(filter.getFilterType())) {
					query = pagination ? filterByExpiringDateLimit : filterByExpiringDate;
				} else if ("EXPIRED".equals(filter.getFilterType())) {
					query = pagination ? filterByExpiredDateLimit : filterByExpiredDate;
				} else {
					query = pagination ? filterByRenewalLimit : filterByRenewal;
				}
				ps = conn.prepareStatement(query);
				if ("DATE".equals(filter.getFilterType())) {
					ps.setObject(1, ValueConvertor.convertToLocalDate(filter.getStartDate(), AssetConstants.DEFAULT_DATEFORMAT));
					ps.setObject(2, ValueConvertor.convertToLocalDate(filter.getEndDate(), AssetConstants.DEFAULT_DATEFORMAT));
					if(pagination) {
						int offset = (filter.getCurrentPage() * filter.getRecordsPerPage()) - filter.getRecordsPerPage();
						ps.setInt(3, offset);
						ps.setInt(4, filter.getRecordsPerPage());
					}
				} else if (!"EXPIRED".equals(filter.getFilterType())){
					ps.setString(1, filter.getFilterValue());
					if(pagination) {
						int offset = (filter.getCurrentPage() * filter.getRecordsPerPage()) - filter.getRecordsPerPage();
						ps.setInt(2, offset);
						ps.setInt(3, filter.getRecordsPerPage());
					}
				}else {
					if(pagination) {
						int offset = (filter.getCurrentPage() * filter.getRecordsPerPage()) - filter.getRecordsPerPage();
						ps.setInt(1, offset);
						ps.setInt(2, filter.getRecordsPerPage());
					}
				}
				rs = ps.executeQuery();
				while (rs.next()) {
					assetInfoList.add(populateItems(rs));
				}
			    logger.log(Level.INFO, "filteredAssetInfo list: {0}", assetInfoList.size());
	    	} else {
				logger.log(Level.INFO, AssetConstants.ERROR_INVALID_INPUT);
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
	    logger.log(Level.INFO, "exited");
	    return assetInfoList;
	}

	public int getNumberOfRows(FilterParam filter) {
		logger.log(Level.INFO, "entered");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    int noOfRows = 0;
	    try {
	    	conn = DataSourceListener.getAssetDS().getConnection();
			String query = "";

			if(filter != null) {
				logger.log(Level.INFO, "Filter type: {0}", filter.getFilterType());

				if("PRODUCT".equals(filter.getFilterType())) {
					query = filterByProductCount;
				} else if ("SERIAL".equals(filter.getFilterType())) {
					query = filterBySerialNumberCount;
				} else if ("LOCATION".equals(filter.getFilterType())) {
					query = filterByLocationCount;
				} else if ("ADDRESS".equals(filter.getFilterType())) {
					query = filterByAddressCount;
				} else if ("TRACK".equals(filter.getFilterType())) {
					query = filterByTrackCount;
				} else if ("OEM".equals(filter.getFilterType())) {
					query = filterByOEMCount;
				} else if ("OPCO".equals(filter.getFilterType())) {
					query = filterByOPCONameCount;
				} else if ("BUSINESS".equals(filter.getFilterType())) {
					query = filterByBusinessSegmentCount;
				} else if ("DATE".equals(filter.getFilterType())) {
					query = filterByExpiringDateCount;
				} else if ("EXPIRED".equals(filter.getFilterType())) {
					query = filterByExpiredDateCount;
				} else {
					query = filterByRenewalCount;
				}
				ps = conn.prepareStatement(query);
				if ("DATE".equals(filter.getFilterType())) {
					ps.setObject(1, ValueConvertor.convertToLocalDate(filter.getStartDate(), AssetConstants.DEFAULT_DATEFORMAT));
					ps.setObject(2, ValueConvertor.convertToLocalDate(filter.getEndDate(), AssetConstants.DEFAULT_DATEFORMAT));
				} else if (!"EXPIRED".equals(filter.getFilterType())){
					ps.setString(1, filter.getFilterValue());
				}
			} else {
				query = selectCount;
				ps = conn.prepareStatement(query);
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
	    logger.log(Level.INFO, "exited");
	    return noOfRows;
	}

	public Map<String,Integer> getRenewalPeriodCount(List<String> intervals) {
		logger.log(Level.INFO, "entered");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    Map<String,Integer> result = new TreeMap<>();
	    try {
	    	conn = DataSourceListener.getAssetDS().getConnection();
	    	if(intervals != null) {
	    		for (String interval : intervals) {
			    	logger.log(Level.INFO, "Filter type: {0}", interval);
					ps = conn.prepareStatement(filterByRenewalCount);
					ps.setString(1, interval);
		
					rs = ps.executeQuery();
					if (rs.next()) {
						int noOfRows = rs.getInt(1);
						result.put(interval, noOfRows);
						logger.log(Level.INFO, "noOfRows: {0}", noOfRows);
					}
					DAOUtil.close(rs);
					DAOUtil.closePreparedStatement(ps);
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
	    logger.log(Level.INFO, "exited");
	    return result;
	}

	private void populatePreparedStatement(PreparedStatement ps, AssetInfo assetInfo) throws SQLException{
		ps.setString(1, assetInfo.getTrackName());
		ps.setString(2, assetInfo.getBusinessSegment());
		ps.setString(3, assetInfo.getOpcoName());
		ps.setString(4, assetInfo.getDeviceName());
		ps.setString(5, assetInfo.getOemName());
		ps.setString(6, assetInfo.getContractedThrough());
		ps.setString(7, assetInfo.getProductNumber());
		ps.setString(8, assetInfo.getProductDescription());
		ps.setInt(9, assetInfo.getQuantity());
		ps.setString(10, assetInfo.getContractNumber());
		ps.setString(11, assetInfo.getServiceLevel());
		ps.setString(12, assetInfo.getSerialNumber());
		ps.setString(13, assetInfo.getServiceLevelDescription());
		ps.setString(14, assetInfo.getSku());
		ps.setObject(15, ValueConvertor.convertToLocalDate(assetInfo.getSupportStartDate(), AssetConstants.DEFAULT_DATEFORMAT));
		ps.setObject(16, ValueConvertor.convertToLocalDate(assetInfo.getSupportEndDate(), AssetConstants.DEFAULT_DATEFORMAT));
		ps.setObject(17, ValueConvertor.convertToLocalDate(assetInfo.getEolDate(), AssetConstants.DEFAULT_DATEFORMAT));
		ps.setObject(18, ValueConvertor.convertToLocalDate(assetInfo.getPurchasedDate(), AssetConstants.DEFAULT_DATEFORMAT));
		ps.setString(19, assetInfo.getPurchasedVendor());
		ps.setObject(20, ValueConvertor.convertToLocalDate(assetInfo.getInstalledDate(), AssetConstants.DEFAULT_DATEFORMAT));
		ps.setString(21, assetInfo.getPurchasedCost());
		ps.setString(22, assetInfo.getDeployedLocation());
		ps.setString(23, assetInfo.getAddress1());
		ps.setString(24, assetInfo.getAddress2());
		ps.setString(25, assetInfo.getCity());
		ps.setString(26, assetInfo.getState());
		ps.setString(27, assetInfo.getCountry());
		ps.setString(28, assetInfo.getZipCode());
	}
	
	private AssetInfo populateItems(ResultSet rs) throws SQLException{
		AssetInfo asset = new AssetInfo();

		asset.setTrackName(rs.getString("TrackName"));
		asset.setBusinessSegment(rs.getString("BusinessSegment"));
		asset.setOpcoName(rs.getString("OPCOName"));
		asset.setDeviceName(rs.getString("DeviceName"));
		asset.setOemName(rs.getString("OEMName"));
		asset.setContractedThrough(rs.getString("ContractedThrough"));
		asset.setProductNumber(rs.getString("ProductNumber"));
		asset.setProductDescription(rs.getString("ProductDescription"));
		asset.setQuantity(rs.getInt("Quantity"));
		asset.setContractNumber(rs.getString("ContractNumber"));
		asset.setServiceLevel(rs.getString("ServiceLevel"));
		asset.setSerialNumber(rs.getString("SerialNumber"));
		asset.setServiceLevelDescription(rs.getString("ServiceLevelDescription"));
		asset.setSku(rs.getString("SKU"));
		asset.setSupportStartDate(rs.getString("SupportStartDate"));
		asset.setSupportEndDate(rs.getString("SupportEndDate"));
		asset.setEolDate(rs.getString("EOLDate"));
		asset.setPurchasedDate(rs.getString("PurchasedDate"));
		asset.setPurchasedVendor(rs.getString("PurchasedVendor"));
		asset.setInstalledDate(rs.getString("InstalledDate"));
		asset.setPurchasedCost(rs.getString("PurchasedCost"));
		asset.setDeployedLocation(rs.getString("DeployedLocation"));
		asset.setAddress1(rs.getString("Address1"));
		asset.setAddress2(rs.getString("Address2"));
		asset.setCity(rs.getString("City"));
		asset.setState(rs.getString("State"));
		asset.setZipCode(rs.getString("ZipCode"));
		asset.setCountry(rs.getString("Country"));
		asset.setCreated(rs.getString("Created"));
		asset.setLastUpdated(rs.getString("LastUpdated"));
		asset.setUpdatedBy(rs.getString("UpdatedBy"));
		
		return asset;
	}

	private boolean validateAssetInputs(AssetInfo asset) {
		boolean result = false;
	    if (Validator.isNotEmpty(asset.getTrackName()) || 
	    		Validator.isNotEmpty(asset.getBusinessSegment()) || 
	    		Validator.isNotEmpty(asset.getOpcoName()) || 
	    		Validator.isNotEmpty(asset.getDeviceName()) || 
	    		Validator.isNotEmpty(asset.getOemName()) || 
	    		Validator.isNotEmpty(asset.getProductNumber()) || 
	    		Validator.isNotEmpty(asset.getSerialNumber()) || 
	    		Validator.isNotEmpty(asset.getDeployedLocation()) || 
	    		Validator.isNotEmpty(asset.getAddress1()) || 
	    		Validator.isNotEmpty(asset.getSupportEndDate()) 
	    	) {
	    	result = true;
	    }
		return result;
	}
}
