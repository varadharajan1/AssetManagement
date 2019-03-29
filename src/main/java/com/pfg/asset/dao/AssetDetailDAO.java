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
import com.pfg.asset.dto.AssetDetail;
import com.pfg.asset.dto.FilterParam;
import com.pfg.asset.util.AssetConstants;
import com.pfg.asset.util.Validator;
import com.pfg.asset.util.ValueConvertor;

public class AssetDetailDAO {

	private static final Logger logger = Logger.getLogger(AssetDetailDAO.class.getName());
	
	private String insertAssetDetail = "INSERT INTO AssetDetails (TrackName, OEMName, ProductNumber, SKU, CustomerName, CustomerAddress1, ContractNumber, Quantity, SerialNumber, ServiceLevel, ServiceLevelDescription, StartDate, EndDate, UpdatedBy, Comments) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private String deleteAssetDetail = "DELETE FROM AssetDetails WHERE TrackName=? AND OEMName=? AND ProductNumber=? AND SKU=? AND CustomerName=? AND CustomerAddress1=? AND ContractNumber=? AND SerialNumber=? AND StartDate=?";
	private String updateAssetDetail = "UPDATE AssetDetails SET StartDate=?, EndDate=?, LastUpdated=CURRENT_DATE WHERE TrackName=? AND OEMName=? AND ProductNumber=? AND SKU=? AND CustomerName=? AND CustomerAddress1=? AND ContractNumber=? AND SerialNumber=? AND StartDate=?";
	private String selectAll   = "SELECT AD.TrackName, AD.OEMName, AD.ProductNumber, AD.SKU, AD.CustomerName, AD.CustomerAddress1, AD.ContractNumber, AD.Quantity, AD.SerialNumber, AD.ServiceLevel, AD.ServiceLevelDescription, AD.StartDate, AD.EndDate, AD.Comments, PI.ProductDescription, DI.CustomerAddress2, DI.City, DI.State, DI.ZipCode, DI.Country FROM AssetDetails AD, ProductInfo PI, DeploymentInfo DI WHERE AD.ProductNumber=PI.ProductNumber AND AD.SKU=PI.SKU AND AD.CustomerName=DI.CustomerName AND AD.CustomerAddress1=DI.CustomerAddress1 ORDER BY TrackName, OEMName, ProductNumber, SKU, CustomerName, CustomerAddress1, StartDate";
	private String selectLimit = "SELECT AD.TrackName, AD.OEMName, AD.ProductNumber, AD.SKU, AD.CustomerName, AD.CustomerAddress1, AD.ContractNumber, AD.Quantity, AD.SerialNumber, AD.ServiceLevel, AD.ServiceLevelDescription, AD.StartDate, AD.EndDate, AD.Comments, PI.ProductDescription, DI.CustomerAddress2, DI.City, DI.State, DI.ZipCode, DI.Country FROM AssetDetails AD, ProductInfo PI, DeploymentInfo DI WHERE AD.ProductNumber=PI.ProductNumber AND AD.SKU=PI.SKU AND AD.CustomerName=DI.CustomerName AND AD.CustomerAddress1=DI.CustomerAddress1 ORDER BY TrackName, OEMName, ProductNumber, SKU, CustomerName, CustomerAddress1, StartDate OFFSET ? LIMIT ?";
	private String selectCount = "SELECT count(*) FROM AssetDetails";

	private String filterByRenewal = "SELECT TrackName, OEMName, ProductNumber, SKU, CustomerName, CustomerAddress1, ContractNumber, Quantity, SerialNumber, StartDate, EndDate FROM AssetDetails WHERE EndDate <= (CURRENT_DATE + ?::interval)";
	private String filterByProduct = "SELECT TrackName, OEMName, ProductNumber, SKU, CustomerName, CustomerAddress1, ContractNumber, Quantity, SerialNumber, StartDate, EndDate FROM AssetDetails WHERE ProductNumber=?";
	private String filterBySerialNumber = "SELECT TrackName, OEMName, ProductNumber, SKU, CustomerName, CustomerAddress1, ContractNumber, Quantity, SerialNumber, StartDate, EndDate FROM AssetDetails WHERE SerialNumber=?";
	private String filterByCustomer = "SELECT TrackName, OEMName, ProductNumber, SKU, CustomerName, CustomerAddress1, ContractNumber, Quantity, SerialNumber, StartDate, EndDate FROM AssetDetails WHERE CustomerName=?";
	private String filterByLocation = "SELECT TrackName, OEMName, ProductNumber, SKU, CustomerName, CustomerAddress1, ContractNumber, Quantity, SerialNumber, StartDate, EndDate FROM AssetDetails WHERE CustomerAddress1=?";

	private String filterByRenewalLimit = "SELECT TrackName, OEMName, ProductNumber, SKU, CustomerName, CustomerAddress1, ContractNumber, Quantity, SerialNumber, StartDate, EndDate FROM AssetDetails WHERE EndDate <= (CURRENT_DATE + ?::interval) OFFSET ? LIMIT ?";
	private String filterByProductLimit = "SELECT TrackName, OEMName, ProductNumber, SKU, CustomerName, CustomerAddress1, ContractNumber, Quantity, SerialNumber, StartDate, EndDate FROM AssetDetails WHERE ProductNumber=? OFFSET ? LIMIT ?";
	private String filterBySerialNumberLimit = "SELECT TrackName, OEMName, ProductNumber, SKU, CustomerName, CustomerAddress1, ContractNumber, Quantity, SerialNumber, StartDate, EndDate FROM AssetDetails WHERE SerialNumber=? OFFSET ? LIMIT ?";
	private String filterByCustomerLimit = "SELECT TrackName, OEMName, ProductNumber, SKU, CustomerName, CustomerAddress1, ContractNumber, Quantity, SerialNumber, StartDate, EndDate FROM AssetDetails WHERE CustomerName=? OFFSET ? LIMIT ?";
	private String filterByLocationLimit = "SELECT TrackName, OEMName, ProductNumber, SKU, CustomerName, CustomerAddress1, ContractNumber, Quantity, SerialNumber, StartDate, EndDate FROM AssetDetails WHERE CustomerAddress1=? OFFSET ? LIMIT ?";

	private String filterByRenewalCount = "SELECT count(*) FROM AssetDetails WHERE EndDate <= (CURRENT_DATE + ?::interval)";
	private String filterByProductCount = "SELECT count(*) FROM AssetDetails WHERE ProductNumber=?";
	private String filterBySerialNumberCount = "SELECT count(*) FROM AssetDetails WHERE SerialNumber=?";
	private String filterByCustomerCount = "SELECT count(*) FROM AssetDetails WHERE CustomerName=?";
	private String filterByLocationCount = "SELECT count(*) FROM AssetDetails WHERE CustomerAddress1=?";

	public int insertAssetDetail(AssetDetail assetDetail) {
		logger.log(Level.INFO, "AssetDetailDAO: insertAssetDetail() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    int result = 0;
	    try {
			logger.log(Level.INFO, "AssetDetailDAO: assetDetail: {0} ", assetDetail);
		    if(assetDetail != null) {
			    if (!validateAssetInputs(assetDetail)) {
			    	throw new AssetException(AssetConstants.ERROR_INVALID_INPUT);
			    }
				conn = DataSourceListener.getAssetDS().getConnection();

				logger.log(Level.INFO, "AssetDetailDAO: insert SQL: {0} ", insertAssetDetail);
				
				ps = conn.prepareStatement(insertAssetDetail);
				ps.setString(1, assetDetail.getTrackName());
				ps.setString(2, assetDetail.getOemName());
				ps.setString(3, assetDetail.getProductNumber());
				ps.setString(4, assetDetail.getSKU());
				ps.setString(5, assetDetail.getCustomerName());
				ps.setString(6, assetDetail.getAddress1());
				ps.setString(7, assetDetail.getContractNumber());
				ps.setString(8, assetDetail.getQuantity());
				ps.setString(9, assetDetail.getSerialNumber());
				ps.setString(10, assetDetail.getServiceLevel());
				ps.setString(11, assetDetail.getServiceLevelDescription());
				ps.setObject(12, ValueConvertor.convertToLocalDate(assetDetail.getStartDate(), AssetConstants.DEFAULT_DATEFORMAT));
				ps.setObject(13, ValueConvertor.convertToLocalDate(assetDetail.getEndDate(), AssetConstants.DEFAULT_DATEFORMAT));
				ps.setString(14, assetDetail.getUpdatedBy());
				ps.setString(15, assetDetail.getComments());

				result = ps.executeUpdate();

				logger.log(Level.INFO, "AssetDetailDAO: {0} row(s) inserted.", result);
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
	    logger.log(Level.INFO, "AssetDetailDAO: insertAssetDetail() ended.");
	    
	    return result;
	}

	public int batchInsert(List<AssetDetail> assetDetails) {
		logger.log(Level.INFO, "AssetDetailDAO: batchInsert() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    int result = 0;
	    try {
		    if(assetDetails != null) {
				conn = DataSourceListener.getAssetDS().getConnection();
				conn.setAutoCommit(false);
				
				logger.log(Level.INFO, "AssetDetailDAO: insert SQL: {0} ", insertAssetDetail);
				
				ps = conn.prepareStatement(insertAssetDetail);
				for(AssetDetail assetDetail : assetDetails) {
				    if (!validateAssetInputs(assetDetail)) {
						logger.log(Level.INFO, AssetConstants.ERROR_INVALID_INPUT);
				    	continue;
				    }
					ps.setString(1, assetDetail.getTrackName());
					ps.setString(2, assetDetail.getOemName());
					ps.setString(3, assetDetail.getProductNumber());
					ps.setString(4, assetDetail.getSKU());
					ps.setString(5, assetDetail.getCustomerName());
					ps.setString(6, assetDetail.getAddress1());
					ps.setString(7, assetDetail.getContractNumber());
					ps.setString(8, assetDetail.getQuantity());
					ps.setString(9, assetDetail.getSerialNumber());
					ps.setString(10, assetDetail.getServiceLevel());
					ps.setString(11, assetDetail.getServiceLevelDescription());
					ps.setObject(12, ValueConvertor.convertToLocalDate(assetDetail.getStartDate(), AssetConstants.DEFAULT_DATEFORMAT));
					ps.setObject(13, ValueConvertor.convertToLocalDate(assetDetail.getEndDate(), AssetConstants.DEFAULT_DATEFORMAT));
					ps.setString(14, assetDetail.getUpdatedBy());
					ps.setString(15, assetDetail.getComments());
					ps.addBatch();
				}
				int[] batchResult = ps.executeBatch();
				conn.commit();
				
				result = IntStream.of(batchResult).sum();
				
				logger.log(Level.INFO, "AssetDetailDAO: {0} row(s) inserted.", result);
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
	    logger.log(Level.INFO, "AssetDetailDAO: batchInsert() ended.");
	    
	    return result;
	}

	public int deleteAssetDetail(AssetDetail assetDetail) {
		logger.log(Level.INFO, "AssetDetailDAO: deleteAssetDetail() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    int result = 0;
	    try {
		    if(assetDetail != null) {
			    if (Validator.isEmpty(assetDetail.getCustomerName())) {
			    	throw new AssetException(AssetConstants.ERROR_INVALID_INPUT);
			    }
				conn = DataSourceListener.getAssetDS().getConnection();

				logger.log(Level.INFO, "AssetDetailDAO: delete SQL: {0} ", deleteAssetDetail);
				
				ps = conn.prepareStatement(deleteAssetDetail);
				ps.setString(1, assetDetail.getTrackName());
				ps.setString(2, assetDetail.getOemName());
				ps.setString(3, assetDetail.getProductNumber());
				ps.setString(4, assetDetail.getSKU());
				ps.setString(5, assetDetail.getCustomerName());
				ps.setString(6, assetDetail.getAddress1());
				ps.setString(7, assetDetail.getContractNumber());
				ps.setString(8, assetDetail.getSerialNumber());
				ps.setObject(9, ValueConvertor.convertToLocalDate(assetDetail.getStartDate(), AssetConstants.DEFAULT_DATEFORMAT));

				result = ps.executeUpdate();

				logger.log(Level.INFO, "AssetDetailDAO: {0} row(s) deleted.", result);
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
	    logger.log(Level.INFO, "AssetDetailDAO: deleteAssetDetail() ended.");
	    
	    return result;
	}

	public int batchDelete(List<AssetDetail> assetDetails) {
		logger.log(Level.INFO, "AssetDetailDAO: batchDelete() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    int result = 0;
	    try {
		    if(assetDetails != null) {
				conn = DataSourceListener.getAssetDS().getConnection();
				conn.setAutoCommit(false);
				
				logger.log(Level.INFO, "AssetDetailDAO: delete SQL: {0} ", deleteAssetDetail);
				
				ps = conn.prepareStatement(deleteAssetDetail);
				for(AssetDetail assetDetail : assetDetails) {
					ps.setString(1, assetDetail.getTrackName());
					ps.setString(2, assetDetail.getOemName());
					ps.setString(3, assetDetail.getProductNumber());
					ps.setString(4, assetDetail.getSKU());
					ps.setString(5, assetDetail.getCustomerName());
					ps.setString(6, assetDetail.getAddress1());
					ps.setString(7, assetDetail.getContractNumber());
					ps.setString(8, assetDetail.getSerialNumber());
					ps.setObject(9, ValueConvertor.convertToLocalDate(assetDetail.getStartDate(), AssetConstants.DEFAULT_DATEFORMAT));
					ps.addBatch();
				}
				int[] batchResult = ps.executeBatch();
				conn.commit();
				
				result = IntStream.of(batchResult).sum();
				
				logger.log(Level.INFO, "AssetDetailDAO: {0} row(s) deleted.", result);
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
	    logger.log(Level.INFO, "AssetDetailDAO: batchDelete() ended.");
	    
	    return result;
	}

	public int batchUpdate(List<AssetDetail> assetDetails, String startDate, String endDate) {
		logger.log(Level.INFO, "AssetDetailDAO: batchUpdate() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    int result = 0;
	    try {
		    if(assetDetails != null) {
				conn = DataSourceListener.getAssetDS().getConnection();
				conn.setAutoCommit(false);
				
				logger.log(Level.INFO, "AssetDetailDAO: update SQL: {0} ", updateAssetDetail);
				
				ps = conn.prepareStatement(updateAssetDetail);
				for(AssetDetail assetDetail : assetDetails) {
					ps.setObject(1, ValueConvertor.convertToLocalDate(startDate, AssetConstants.DEFAULT_DATEFORMAT));
					ps.setObject(2, ValueConvertor.convertToLocalDate(endDate, AssetConstants.DEFAULT_DATEFORMAT));
					ps.setString(3, assetDetail.getTrackName());
					ps.setString(4, assetDetail.getOemName());
					ps.setString(5, assetDetail.getProductNumber());
					ps.setString(6, assetDetail.getSKU());
					ps.setString(7, assetDetail.getCustomerName());
					ps.setString(8, assetDetail.getAddress1());
					ps.setString(9, assetDetail.getContractNumber());
					ps.setString(10, assetDetail.getSerialNumber());
					ps.setObject(11, ValueConvertor.convertToLocalDate(assetDetail.getStartDate(), AssetConstants.DEFAULT_DATEFORMAT));
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
	    logger.log(Level.INFO, "AssetDetailDAO: batchUpdate() ended.");
	    
	    return result;
	}

	public int updateAssetDetail(AssetDetail assetDetail, String startDate, String endDate) {
		logger.log(Level.INFO, "AssetDetailDAO: updateAssetDetail() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    int result = 0;
	    try {
		    if(assetDetail != null) {
			    if (Validator.isEmpty(assetDetail.getCustomerName())) {
			    	throw new AssetException(AssetConstants.ERROR_INVALID_INPUT);
			    }
				conn = DataSourceListener.getAssetDS().getConnection();

				logger.log(Level.INFO, "AssetDetailDAO: update SQL: {0} ", updateAssetDetail);
				
				ps = conn.prepareStatement(updateAssetDetail);
				ps.setObject(1, ValueConvertor.convertToLocalDate(startDate, AssetConstants.DEFAULT_DATEFORMAT));
				ps.setObject(2, ValueConvertor.convertToLocalDate(endDate, AssetConstants.DEFAULT_DATEFORMAT));
				ps.setString(3, assetDetail.getTrackName());
				ps.setString(4, assetDetail.getOemName());
				ps.setString(5, assetDetail.getProductNumber());
				ps.setString(6, assetDetail.getSKU());
				ps.setString(7, assetDetail.getCustomerName());
				ps.setString(8, assetDetail.getAddress1());
				ps.setString(9, assetDetail.getContractNumber());
				ps.setString(10, assetDetail.getSerialNumber());
				ps.setObject(11, ValueConvertor.convertToLocalDate(assetDetail.getStartDate(), AssetConstants.DEFAULT_DATEFORMAT));

				result = ps.executeUpdate();

				logger.log(Level.INFO, "AssetDetailDAO: {0} row(s) modified.", result);
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
	    logger.log(Level.INFO, "AssetDetailDAO: updateAssetDetail() ended.");
	    
	    return result;
	}

	public List<AssetDetail> selectAllAssetDetail() {
		logger.log(Level.INFO, "AssetDetailDAO: selectAllAssetDetail() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    List<AssetDetail> assetDetailList = new ArrayList<>();
	    try {
	    	conn = DataSourceListener.getAssetDS().getConnection();

			logger.log(Level.INFO, "AssetDetailDAO: selectAll SQL: {0} ", selectAll);
			
			ps = conn.prepareStatement(selectAll);

			rs = ps.executeQuery();
			while (rs.next()) {
				assetDetailList.add(populateItems(rs));
			}
		    logger.log(Level.INFO, "AssetDetailDAO: assetDetail list: {0}", assetDetailList.size());
	    } catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AssetException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(rs);
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "AssetDetailDAO: selectAllAssetDetail() ended.");
	    
	    return assetDetailList;
	}

	public List<AssetDetail> selectAssetDetail(int currentPage, int recordsPerPage) {
		logger.log(Level.INFO, "AssetDetailDAO: selectAssetDetail() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    List<AssetDetail> assetDetailList = new ArrayList<>();
	    try {
	    	conn = DataSourceListener.getAssetDS().getConnection();

			logger.log(Level.INFO, "AssetDetailDAO: selectLimit SQL: {0} ", selectLimit);
			
			int offset = (currentPage * recordsPerPage) - recordsPerPage;
			
			ps = conn.prepareStatement(selectLimit);
			ps.setInt(1, offset);
			ps.setInt(2, recordsPerPage);
			
			rs = ps.executeQuery();
			while (rs.next()) {
				assetDetailList.add(populateItems(rs));
			}
		    logger.log(Level.INFO, "AssetDetailDAO: assetDetail list: {0}", assetDetailList.size());
	    } catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AssetException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(rs);
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "AssetDetailDAO: selectAssetDetail() ended.");
	    
	    return assetDetailList;
	}

	public List<AssetDetail> filteredAssetDetail(FilterParam filter) {
		return filteredAssetDetail(filter, false);
	}

	public List<AssetDetail> filteredAssetDetail(FilterParam filter, boolean pagination) {
		logger.log(Level.INFO, "AssetDetailDAO: filteredAssetDetail() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    List<AssetDetail> assetDetailList = new ArrayList<>();
	    try {
	    	if(filter != null) {
		    	conn = DataSourceListener.getAssetDS().getConnection();
				logger.log(Level.INFO, "Filter type: {0}", filter.getFilterType());

				String query = "";
				if("PRODUCT".equals(filter.getFilterType())) {
					query = pagination ? filterByProductLimit : filterByProduct;
				} else if ("SERIAL".equals(filter.getFilterType())) {
					query = pagination ? filterBySerialNumberLimit : filterBySerialNumber;
				} else if ("CUSTOMER".equals(filter.getFilterType())) {
					query = pagination ? filterByCustomerLimit : filterByCustomer;
				} else if ("ADDRESS".equals(filter.getFilterType())) {
					query = pagination ? filterByLocationLimit : filterByLocation;
				} else {
					query = pagination ? filterByRenewalLimit : filterByRenewal;
				}
				ps = conn.prepareStatement(query);
				ps.setString(1, filter.getFilterValue());

				if(pagination) {
					int offset = (filter.getCurrentPage() * filter.getRecordsPerPage()) - filter.getRecordsPerPage();
					ps.setInt(2, offset);
					ps.setInt(3, filter.getRecordsPerPage());
				}
				rs = ps.executeQuery();
				while (rs.next()) {
					assetDetailList.add(populatePrimaryItems(rs));
				}
			    logger.log(Level.INFO, "AssetDetailDAO: filteredAssetDetail list: {0}", assetDetailList.size());
	    	}else {
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
	    logger.log(Level.INFO, "AssetDetailDAO: selectAssetDetail() ended.");
	    return assetDetailList;
	}

	public int getNumberOfRows(FilterParam filter) {
		logger.log(Level.INFO, "AssetDetailDAO: getNumberOfRows() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    int noOfRows = 0;
	    try {
	    	conn = DataSourceListener.getAssetDS().getConnection();
			logger.log(Level.INFO, "Filter type: {0}", filter.getFilterType());

			if("PRODUCT".equals(filter.getFilterType())) {
				ps = conn.prepareStatement(filterByProductCount);
			} else if ("SERIAL".equals(filter.getFilterType())) {
				ps = conn.prepareStatement(filterBySerialNumberCount);
			} else if ("CUSTOMER".equals(filter.getFilterType())) {
				ps = conn.prepareStatement(filterByCustomerCount);
			} else if ("ADDRESS".equals(filter.getFilterType())) {
				ps = conn.prepareStatement(filterByLocationCount);
			} else {
				ps = conn.prepareStatement(filterByRenewalCount);
			}
			ps.setString(1, filter.getFilterValue());

			rs = ps.executeQuery();
			if (rs.next()) {
				noOfRows = rs.getInt(1);
			}
		    logger.log(Level.INFO, "AssetDetailDAO: noOfRows: {0}", noOfRows);
	    } catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AssetException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(rs);
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "AssetDetailDAO: getNumberOfRows() ended.");
	    
	    return noOfRows;
	}

	public Map<String,Integer> getRenewalPeriodCount(List<String> intervals) {
		logger.log(Level.INFO, "AssetDetailDAO: getRenewalPeriodCount() executed.");
		
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
						logger.log(Level.INFO, "AssetDetailDAO: noOfRows: {0}", noOfRows);
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
	    logger.log(Level.INFO, "AssetDetailDAO: getRenewalPeriodCount() ended.");
	    
	    return result;
	}

	public int getNumberOfRows() {
		logger.log(Level.INFO, "AssetDetailDAO: getNumberOfRows() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    int noOfRows = 0;
	    try {
	    	conn = DataSourceListener.getAssetDS().getConnection();

			logger.log(Level.INFO, "AssetDetailDAO: selectCount SQL: {0} ", selectCount);
			
			ps = conn.prepareStatement(selectCount);

			rs = ps.executeQuery();
			if (rs.next()) {
				noOfRows = rs.getInt(1);
			}
		    logger.log(Level.INFO, "AssetDetailDAO: noOfRows: {0}", noOfRows);
	    } catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AssetException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(rs);
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "AssetDetailDAO: getNumberOfRows() ended.");
	    
	    return noOfRows;
	}

	private AssetDetail populateItems(ResultSet rs) throws SQLException{
		AssetDetail assetDetail = new AssetDetail();

		assetDetail.setTrackName(rs.getString("TrackName"));
		assetDetail.setOemName(rs.getString("OEMName"));
		assetDetail.setProductNumber(rs.getString("ProductNumber"));
		assetDetail.setProductDescription(rs.getString("ProductDescription"));
		assetDetail.setSKU(rs.getString("SKU"));
		assetDetail.setCustomerName(rs.getString("CustomerName"));
		assetDetail.setAddress1(rs.getString("CustomerAddress1"));
		assetDetail.setAddress2(rs.getString("CustomerAddress2"));
		assetDetail.setCity(rs.getString("City"));
		assetDetail.setState(rs.getString("State"));
		assetDetail.setZipCode(rs.getInt("ZipCode"));
		assetDetail.setCountry(rs.getString("Country"));
		assetDetail.setContractNumber(rs.getString("ContractNumber"));
		assetDetail.setQuantity(rs.getString("Quantity"));
		assetDetail.setSerialNumber(rs.getString("SerialNumber"));
		assetDetail.setServiceLevel(rs.getString("ServiceLevel"));
		assetDetail.setServiceLevelDescription(rs.getString("ServiceLevelDescription"));
		assetDetail.setStartDate(rs.getString("StartDate"));
		assetDetail.setEndDate(rs.getString("EndDate"));
		//assetDetail.setUpdatedBy(rs.getString("UpdatedBy"));
		assetDetail.setComments(rs.getString("Comments"));
		
		return assetDetail;
	}

	private AssetDetail populatePrimaryItems(ResultSet rs) throws SQLException{
		AssetDetail assetDetail = new AssetDetail();

		assetDetail.setTrackName(rs.getString("TrackName"));
		assetDetail.setOemName(rs.getString("OEMName"));
		assetDetail.setProductNumber(rs.getString("ProductNumber"));
		assetDetail.setSKU(rs.getString("SKU"));
		assetDetail.setCustomerName(rs.getString("CustomerName"));
		assetDetail.setAddress1(rs.getString("CustomerAddress1"));
		assetDetail.setContractNumber(rs.getString("ContractNumber"));
		assetDetail.setQuantity(rs.getString("Quantity"));
		assetDetail.setSerialNumber(rs.getString("SerialNumber"));
		assetDetail.setStartDate(rs.getString("StartDate"));
		assetDetail.setEndDate(rs.getString("EndDate"));
		
		return assetDetail;
	}

	private boolean validateAssetInputs(AssetDetail asset) {
		boolean result = false;
	    if (Validator.isNotEmpty(asset.getTrackName()) || 
	    		Validator.isNotEmpty(asset.getOemName()) || 
	    		Validator.isNotEmpty(asset.getProductNumber()) || 
	    		Validator.isNotEmpty(asset.getSKU()) || 
	    		Validator.isNotEmpty(asset.getCustomerName()) || 
	    		Validator.isNotEmpty(asset.getAddress1()) || 
	    		Validator.isNotEmpty(asset.getContractNumber()) || 
	    		Validator.isNotEmpty(asset.getSerialNumber()) || 
	    		Validator.isNotEmpty(asset.getStartDate()) 
	    	) {
	    	result = true;
	    }
		return result;
	}
}
