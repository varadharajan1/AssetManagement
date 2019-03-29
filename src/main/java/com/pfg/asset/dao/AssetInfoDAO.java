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

	private String insertAssetInfo = "INSERT INTO AssetInfo (TrackName, OPCOName, DeviceName, OEMName, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, StartDate, EndDate, DeployedLocation, DeployedAddress1, DeployedAddress2, DeployedCity, DeployedState, DeployedZipCode, DeployedCountry) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private String deleteAssetInfo = "DELETE FROM AssetInfo WHERE TrackName=? AND OPCOName=? AND DeviceName=? AND OEMName=? AND ProductNumber=? AND SerialNumber=? AND EndDate=?";
	private String updateAssetInfo = "UPDATE AssetInfo SET StartDate=?, EndDate=?, LastUpdated=CURRENT_DATE WHERE TrackName=? AND OPCOName=? AND DeviceName=? AND OEMName=? AND ProductNumber=? AND SerialNumber=? AND EndDate=?";
    
	private String selectAll = "SELECT TrackName, OPCOName, DeviceName, OEMName, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, StartDate, EndDate, DeployedLocation, DeployedAddress1, DeployedAddress2, DeployedCity, DeployedState, DeployedZipCode, DeployedCountry, Created, LastUpdated, UpdatedBy FROM AssetInfo ORDER BY StartDate, EndDate";
	private String selectLimit = "SELECT TrackName, OPCOName, DeviceName, OEMName, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, StartDate, EndDate, DeployedLocation, DeployedAddress1, DeployedAddress2, DeployedCity, DeployedState, DeployedZipCode, DeployedCountry, Created, LastUpdated, UpdatedBy FROM AssetInfo ORDER BY StartDate, EndDate OFFSET ? LIMIT ?";
	private String selectCount = "SELECT count(*) FROM AssetInfo";
	
	private String filterByRenewalLimit = "SELECT TrackName, OPCOName, DeviceName, OEMName, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, StartDate, EndDate, DeployedLocation, DeployedAddress1, DeployedAddress2, DeployedCity, DeployedState, DeployedZipCode, DeployedCountry, Created, LastUpdated, UpdatedBy FROM AssetInfo WHERE EndDate <= (CURRENT_DATE + ?::interval) ORDER BY StartDate, EndDate OFFSET ? LIMIT ?";
	private String filterByOEMLimit = "SELECT TrackName, OPCOName, DeviceName, OEMName, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, StartDate, EndDate, DeployedLocation, DeployedAddress1, DeployedAddress2, DeployedCity, DeployedState, DeployedZipCode, DeployedCountry, Created, LastUpdated, UpdatedBy FROM AssetInfo WHERE OEMName=? ORDER BY StartDate, EndDate OFFSET ? LIMIT ?";
	private String filterByTrackLimit = "SELECT TrackName, OPCOName, DeviceName, OEMName, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, StartDate, EndDate, DeployedLocation, DeployedAddress1, DeployedAddress2, DeployedCity, DeployedState, DeployedZipCode, DeployedCountry, Created, LastUpdated, UpdatedBy FROM AssetInfo WHERE TrackName=? ORDER BY StartDate, EndDate OFFSET ? LIMIT ?";
	private String filterByProductLimit = "SELECT TrackName, OPCOName, DeviceName, OEMName, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, StartDate, EndDate, DeployedLocation, DeployedAddress1, DeployedAddress2, DeployedCity, DeployedState, DeployedZipCode, DeployedCountry, Created, LastUpdated, UpdatedBy FROM AssetInfo WHERE ProductNumber=? ORDER BY StartDate, EndDate OFFSET ? LIMIT ?";
	private String filterBySerialNumberLimit = "SELECT TrackName, OPCOName, DeviceName, OEMName, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, StartDate, EndDate, DeployedLocation, DeployedAddress1, DeployedAddress2, DeployedCity, DeployedState, DeployedZipCode, DeployedCountry, Created, LastUpdated, UpdatedBy FROM AssetInfo WHERE SerialNumber=? ORDER BY StartDate, EndDate OFFSET ? LIMIT ?";
	private String filterByLocationLimit = "SELECT TrackName, OPCOName, DeviceName, OEMName, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, StartDate, EndDate, DeployedLocation, DeployedAddress1, DeployedAddress2, DeployedCity, DeployedState, DeployedZipCode, DeployedCountry, Created, LastUpdated, UpdatedBy FROM AssetInfo WHERE DeployedLocation=? ORDER BY StartDate, EndDate OFFSET ? LIMIT ?";
	private String filterByAddressLimit = "SELECT TrackName, OPCOName, DeviceName, OEMName, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, StartDate, EndDate, DeployedLocation, DeployedAddress1, DeployedAddress2, DeployedCity, DeployedState, DeployedZipCode, DeployedCountry, Created, LastUpdated, UpdatedBy FROM AssetInfo WHERE DeployedAddress1=? ORDER BY StartDate, EndDate OFFSET ? LIMIT ?";
	private String filterByExpiringDateLimit = "SELECT TrackName, OPCOName, DeviceName, OEMName, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, StartDate, EndDate, DeployedLocation, DeployedAddress1, DeployedAddress2, DeployedCity, DeployedState, DeployedZipCode, DeployedCountry, Created, LastUpdated, UpdatedBy FROM AssetInfo WHERE EndDate BETWEEN ? AND ? ORDER BY StartDate, EndDate OFFSET ? LIMIT ?";

	private String filterByRenewal = "SELECT TrackName, OPCOName, DeviceName, OEMName, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, StartDate, EndDate, DeployedLocation, DeployedAddress1, DeployedAddress2, DeployedCity, DeployedState, DeployedZipCode, DeployedCountry, Created, LastUpdated, UpdatedBy FROM AssetInfo WHERE EndDate <= (CURRENT_DATE + ?::interval) ORDER BY StartDate, EndDate";
	private String filterByOEM = "SELECT TrackName, OPCOName, DeviceName, OEMName, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, StartDate, EndDate, DeployedLocation, DeployedAddress1, DeployedAddress2, DeployedCity, DeployedState, DeployedZipCode, DeployedCountry, Created, LastUpdated, UpdatedBy FROM AssetInfo WHERE OEMName=? ORDER BY StartDate, EndDate";
	private String filterByTrack = "SELECT TrackName, OPCOName, DeviceName, OEMName, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, StartDate, EndDate, DeployedLocation, DeployedAddress1, DeployedAddress2, DeployedCity, DeployedState, DeployedZipCode, DeployedCountry, Created, LastUpdated, UpdatedBy FROM AssetInfo WHERE TrackName=? ORDER BY StartDate, EndDate";
	private String filterByProduct = "SELECT TrackName, OPCOName, DeviceName, OEMName, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, StartDate, EndDate, DeployedLocation, DeployedAddress1, DeployedAddress2, DeployedCity, DeployedState, DeployedZipCode, DeployedCountry, Created, LastUpdated, UpdatedBy FROM AssetInfo WHERE ProductNumber=? ORDER BY StartDate, EndDate";
	private String filterBySerialNumber = "SELECT TrackName, OPCOName, DeviceName, OEMName, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, StartDate, EndDate, DeployedLocation, DeployedAddress1, DeployedAddress2, DeployedCity, DeployedState, DeployedZipCode, DeployedCountry, Created, LastUpdated, UpdatedBy FROM AssetInfo WHERE SerialNumber=? ORDER BY StartDate, EndDate";
	private String filterByLocation = "SELECT TrackName, OPCOName, DeviceName, OEMName, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, StartDate, EndDate, DeployedLocation, DeployedAddress1, DeployedAddress2, DeployedCity, DeployedState, DeployedZipCode, DeployedCountry, Created, LastUpdated, UpdatedBy FROM AssetInfo WHERE DeployedLocation=? ORDER BY StartDate, EndDate";
	private String filterByAddress = "SELECT TrackName, OPCOName, DeviceName, OEMName, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, StartDate, EndDate, DeployedLocation, DeployedAddress1, DeployedAddress2, DeployedCity, DeployedState, DeployedZipCode, DeployedCountry, Created, LastUpdated, UpdatedBy FROM AssetInfo WHERE DeployedAddress1=? ORDER BY StartDate, EndDate";
	private String filterByExpiringDate = "SELECT TrackName, OPCOName, DeviceName, OEMName, ProductNumber, ProductDescription, Quantity, ContractNumber, ServiceLevel, SerialNumber, ServiceLevelDescription, SKU, StartDate, EndDate, DeployedLocation, DeployedAddress1, DeployedAddress2, DeployedCity, DeployedState, DeployedZipCode, DeployedCountry, Created, LastUpdated, UpdatedBy FROM AssetInfo WHERE EndDate BETWEEN ? AND ? ORDER BY StartDate, EndDate";

	private String filterByRenewalCount = "SELECT count(*) FROM AssetInfo WHERE EndDate <= (CURRENT_DATE + ?::interval)";
	private String filterByOEMCount = "SELECT count(*) FROM AssetInfo WHERE OEMName=?";
	private String filterByTrackCount = "SELECT count(*) FROM AssetInfo WHERE TrackName=?";
	private String filterByProductCount = "SELECT count(*) FROM AssetInfo WHERE ProductNumber=?";
	private String filterBySerialNumberCount = "SELECT count(*) FROM AssetInfo WHERE SerialNumber=?";
	private String filterByLocationCount = "SELECT count(*) FROM AssetInfo WHERE DeployedLocation=?";
	private String filterByAddressCount = "SELECT count(*) FROM AssetInfo WHERE DeployedAddress1=?";
	private String filterByExpiringDateCount = "SELECT count(*) FROM AssetInfo WHERE EndDate BETWEEN ? AND ?";

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
				ps.setString(1, assetInfo.getTrackName());
				ps.setString(2, assetInfo.getOpcoName());
				ps.setString(3, assetInfo.getDeviceName());
				ps.setString(4, assetInfo.getOemName());
				ps.setString(5, assetInfo.getProductNumber());
				ps.setString(6, assetInfo.getProductDescription());
				ps.setInt(7, assetInfo.getQuantity());
				ps.setString(8, assetInfo.getContractNumber());
				ps.setString(9, assetInfo.getServiceLevel());
				ps.setString(10, assetInfo.getSerialNumber());
				ps.setString(11, assetInfo.getServiceLevelDescription());
				ps.setString(12, assetInfo.getSku());
				ps.setObject(13, ValueConvertor.convertToLocalDate(assetInfo.getStartDate(), AssetConstants.DEFAULT_DATEFORMAT));
				ps.setObject(14, ValueConvertor.convertToLocalDate(assetInfo.getEndDate(), AssetConstants.DEFAULT_DATEFORMAT));
				ps.setString(15, assetInfo.getDeployedLocation());
				ps.setString(16, assetInfo.getDeployedAddress1());
				ps.setString(17, assetInfo.getDeployedAddress2());
				ps.setString(18, assetInfo.getDeployedCity());
				ps.setString(19, assetInfo.getDeployedState());
				ps.setString(20, assetInfo.getDeployedZipCode());
				ps.setString(21, assetInfo.getDeployedCountry());

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
					ps.setString(1, assetInfo.getTrackName());
					ps.setString(2, assetInfo.getOpcoName());
					ps.setString(3, assetInfo.getDeviceName());
					ps.setString(4, assetInfo.getOemName());
					ps.setString(5, assetInfo.getProductNumber());
					ps.setInt(6, assetInfo.getQuantity());
					ps.setString(7, assetInfo.getContractNumber());
					ps.setString(8, assetInfo.getProductDescription());
					ps.setString(9, assetInfo.getServiceLevel());
					ps.setString(10, assetInfo.getSerialNumber());
					ps.setString(11, assetInfo.getServiceLevelDescription());
					ps.setString(12, assetInfo.getSku());
					ps.setObject(13, ValueConvertor.convertToLocalDate(assetInfo.getStartDate(), AssetConstants.DEFAULT_DATEFORMAT));
					ps.setObject(14, ValueConvertor.convertToLocalDate(assetInfo.getEndDate(), AssetConstants.DEFAULT_DATEFORMAT));
					ps.setString(15, assetInfo.getDeployedLocation());
					ps.setString(16, assetInfo.getDeployedAddress1());
					ps.setString(17, assetInfo.getDeployedAddress2());
					ps.setString(18, assetInfo.getDeployedCity());
					ps.setString(19, assetInfo.getDeployedState());
					ps.setString(20, assetInfo.getDeployedZipCode());
					ps.setString(21, assetInfo.getDeployedCountry());
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
				ps.setString(2, assetInfo.getOpcoName());
				ps.setString(3, assetInfo.getDeviceName());
				ps.setString(4, assetInfo.getOemName());
				ps.setString(5, assetInfo.getProductNumber());
				ps.setString(6, assetInfo.getSerialNumber());
				ps.setObject(7, ValueConvertor.convertToLocalDate(assetInfo.getEndDate(), AssetConstants.DEFAULT_DATEFORMAT));

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
					ps.setString(2, assetInfo.getOpcoName());
					ps.setString(3, assetInfo.getDeviceName());
					ps.setString(4, assetInfo.getOemName());
					ps.setString(5, assetInfo.getProductNumber());
					ps.setString(6, assetInfo.getSerialNumber());
					ps.setObject(7, ValueConvertor.convertToLocalDate(assetInfo.getEndDate(), AssetConstants.DEFAULT_DATEFORMAT));
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

	public int batchUpdate(List<AssetInfo> assetInfoList, String startDate, String endDate) {
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
				for(AssetInfo assetInfo : assetInfoList) {
					ps.setObject(1, ValueConvertor.convertToLocalDate(startDate, AssetConstants.DEFAULT_DATEFORMAT));
					ps.setObject(2, ValueConvertor.convertToLocalDate(endDate, AssetConstants.DEFAULT_DATEFORMAT));
					ps.setString(3, assetInfo.getTrackName());
					ps.setString(4, assetInfo.getOpcoName());
					ps.setString(5, assetInfo.getDeviceName());
					ps.setString(6, assetInfo.getOemName());
					ps.setString(7, assetInfo.getProductNumber());
					ps.setString(8, assetInfo.getSerialNumber());
					ps.setObject(9, ValueConvertor.convertToLocalDate(assetInfo.getEndDate(), AssetConstants.DEFAULT_DATEFORMAT));
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

	public int updateAssetDetail(AssetInfo assetInfo, String startDate, String endDate) {
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

				logger.log(Level.INFO, "update SQL: {0} ", updateAssetInfo);
				
				ps = conn.prepareStatement(updateAssetInfo);
				ps.setObject(1, ValueConvertor.convertToLocalDate(startDate, AssetConstants.DEFAULT_DATEFORMAT));
				ps.setObject(2, ValueConvertor.convertToLocalDate(endDate, AssetConstants.DEFAULT_DATEFORMAT));
				ps.setString(3, assetInfo.getTrackName());
				ps.setString(4, assetInfo.getOpcoName());
				ps.setString(5, assetInfo.getDeviceName());
				ps.setString(6, assetInfo.getOemName());
				ps.setString(7, assetInfo.getProductNumber());
				ps.setString(8, assetInfo.getSerialNumber());
				ps.setObject(9, ValueConvertor.convertToLocalDate(assetInfo.getEndDate(), AssetConstants.DEFAULT_DATEFORMAT));

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
				} else if ("DATE".equals(filter.getFilterType())) {
					query = pagination ? filterByExpiringDateLimit : filterByExpiringDate;
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
				} else {
					ps.setString(1, filter.getFilterValue());
					if(pagination) {
						int offset = (filter.getCurrentPage() * filter.getRecordsPerPage()) - filter.getRecordsPerPage();
						ps.setInt(2, offset);
						ps.setInt(3, filter.getRecordsPerPage());
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
				} else if ("DATE".equals(filter.getFilterType())) {
					query = filterByExpiringDateCount;
				} else {
					query = filterByRenewalCount;
				}
				ps = conn.prepareStatement(query);
				if ("DATE".equals(filter.getFilterType())) {
					ps.setObject(1, ValueConvertor.convertToLocalDate(filter.getStartDate(), AssetConstants.DEFAULT_DATEFORMAT));
					ps.setObject(2, ValueConvertor.convertToLocalDate(filter.getEndDate(), AssetConstants.DEFAULT_DATEFORMAT));
				} else {
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

	private AssetInfo populateItems(ResultSet rs) throws SQLException{
		AssetInfo asset = new AssetInfo();

		asset.setTrackName(rs.getString("TrackName"));
		asset.setOpcoName(rs.getString("OPCOName"));
		asset.setDeviceName(rs.getString("DeviceName"));
		asset.setOemName(rs.getString("OEMName"));
		asset.setProductNumber(rs.getString("ProductNumber"));
		asset.setProductDescription(rs.getString("ProductDescription"));
		asset.setQuantity(rs.getInt("Quantity"));
		asset.setContractNumber(rs.getString("ContractNumber"));
		asset.setServiceLevel(rs.getString("ServiceLevel"));
		asset.setSerialNumber(rs.getString("SerialNumber"));
		asset.setServiceLevelDescription(rs.getString("ServiceLevelDescription"));
		asset.setSku(rs.getString("SKU"));
		asset.setStartDate(rs.getString("StartDate"));
		asset.setEndDate(rs.getString("EndDate"));
		asset.setDeployedLocation(rs.getString("DeployedLocation"));
		asset.setDeployedAddress1(rs.getString("DeployedAddress1"));
		asset.setDeployedAddress2(rs.getString("DeployedAddress2"));
		asset.setDeployedCity(rs.getString("DeployedCity"));
		asset.setDeployedState(rs.getString("DeployedState"));
		asset.setDeployedZipCode(rs.getString("DeployedZipCode"));
		asset.setDeployedCountry(rs.getString("DeployedCountry"));
		asset.setCreated(rs.getString("Created"));
		asset.setLastUpdated(rs.getString("LastUpdated"));
		asset.setUpdatedBy(rs.getString("UpdatedBy"));
		
		return asset;
	}

	private boolean validateAssetInputs(AssetInfo asset) {
		boolean result = false;
	    if (Validator.isNotEmpty(asset.getTrackName()) || 
	    		Validator.isNotEmpty(asset.getOpcoName()) || 
	    		Validator.isNotEmpty(asset.getOemName()) || 
	    		Validator.isNotEmpty(asset.getProductNumber()) || 
	    		Validator.isNotEmpty(asset.getSerialNumber()) || 
	    		Validator.isNotEmpty(asset.getSku()) || 
	    		Validator.isNotEmpty(asset.getDeployedLocation()) || 
	    		Validator.isNotEmpty(asset.getDeployedAddress1()) || 
	    		Validator.isNotEmpty(asset.getEndDate()) 
	    	) {
	    	result = true;
	    }
		return result;
	}
}
