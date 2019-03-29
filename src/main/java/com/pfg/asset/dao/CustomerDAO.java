package com.pfg.asset.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

import com.pfg.asset.core.AssetException;
import com.pfg.asset.core.DataSourceListener;
import com.pfg.asset.dto.CustomerInfo;
import com.pfg.asset.util.AssetConstants;
import com.pfg.asset.util.Validator;

public class CustomerDAO {

	private static final Logger logger = Logger.getLogger(CustomerDAO.class.getName());
	
	private String insertCustomer = "INSERT INTO DeploymentInfo (CustomerName, CustomerAddress1, CustomerAddress2, City, State, ZipCode, Country) VALUES (?,?,?,?,?,?,?)";
	private String selectAll = "SELECT CustomerName, CustomerAddress1, CustomerAddress2, City, State, ZipCode, Country FROM DeploymentInfo ORDER BY CustomerName, CustomerAddress1";
	private String selectLimit = "SELECT CustomerName, CustomerAddress1, CustomerAddress2, City, State, ZipCode, Country FROM DeploymentInfo ORDER BY CustomerName, CustomerAddress1 OFFSET ? LIMIT ?";
	private String selectCount = "SELECT count(*) FROM DeploymentInfo";
	private String deleteCustomer = "DELETE FROM DeploymentInfo WHERE CustomerName=? AND CustomerAddress1=?";
	private String updateCustomer = "UPDATE DeploymentInfo SET LastUpdated=CURRENT_DATE WHERE CustomerName=? AND CustomerAddress1=?";

	private String selectCustomerName = "SELECT DISTINCT CustomerName FROM DeploymentInfo ORDER BY CustomerName";
	private String selectCustomerAddress = "SELECT DISTINCT CustomerAddress1 FROM DeploymentInfo WHERE CustomerName=? ORDER BY CustomerAddress1";
	private String selectDistinctAddress = "SELECT DISTINCT CustomerAddress1 FROM DeploymentInfo ORDER BY CustomerAddress1";
	private String selectCustomer = "SELECT CustomerName, CustomerAddress1, CustomerAddress2, City, State, ZipCode, Country FROM DeploymentInfo WHERE CustomerName=? AND CustomerAddress1=? ORDER BY CustomerName, CustomerAddress1";
	
	public int insertCustomer(CustomerInfo customer) {
		logger.log(Level.INFO, "CustomerDAO: insertCustomer() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    int result = 0;
	    try {
		    if(customer != null) {
			    if (Validator.isEmpty(customer.getCustomerName()) || Validator.isEmpty(customer.getAddress1())) {
			    	throw new AssetException(AssetConstants.ERROR_INVALID_INPUT);
			    }
				conn = DataSourceListener.getAssetDS().getConnection();

				logger.log(Level.INFO, "CustomerDAO: insert SQL: {0} ", insertCustomer);
				
				ps = conn.prepareStatement(insertCustomer);
				ps.setString(1, customer.getCustomerName());
				ps.setString(2, customer.getAddress1());
				ps.setString(3, customer.getAddress2());
				ps.setString(4, customer.getCity());
				ps.setString(5, customer.getState());
				ps.setInt(6, customer.getZipCode());
				ps.setString(7, customer.getCountry());

				result = ps.executeUpdate();

				logger.log(Level.INFO, "CustomerDAO: {0} row(s) inserted.", result);
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
	    logger.log(Level.INFO, "CustomerDAO: insertCustomer() ended.");
	    
	    return result;
	}

	public int deleteCustomer(CustomerInfo customer) {
		logger.log(Level.INFO, "CustomerDAO: deleteCustomer() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    int result = 0;
	    try {
		    if(customer != null) {
			    if (Validator.isEmpty(customer.getCustomerName()) || Validator.isEmpty(customer.getAddress1())) {
			    	throw new AssetException(AssetConstants.ERROR_INVALID_INPUT);
			    }
				conn = DataSourceListener.getAssetDS().getConnection();

				logger.log(Level.INFO, "CustomerDAO: delete SQL: {0} ", deleteCustomer);
				
				ps = conn.prepareStatement(deleteCustomer);
				ps.setString(1, customer.getCustomerName());
				ps.setString(2, customer.getAddress1());

				result = ps.executeUpdate();

				logger.log(Level.INFO, "CustomerDAO: {0} row(s) deleted.", result);
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
	    logger.log(Level.INFO, "CustomerDAO: deleteCustomer() ended.");
	    
	    return result;
	}

	public int batchDelete(List<CustomerInfo> customers) {
		logger.log(Level.INFO, "CustomerDAO: batchDelete() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    int result = 0;
	    try {
		    if(customers != null) {
				conn = DataSourceListener.getAssetDS().getConnection();
				conn.setAutoCommit(false);
				
				logger.log(Level.INFO, "CustomerDAO: delete SQL: {0} ", deleteCustomer);
				
				ps = conn.prepareStatement(deleteCustomer);
				for(CustomerInfo customer : customers) {
					ps.setString(1, customer.getCustomerName());
					ps.setString(2, customer.getAddress1());
					ps.addBatch();
				}
				int[] batchResult = ps.executeBatch();
				conn.commit();
				
				result = IntStream.of(batchResult).sum();
				
				logger.log(Level.INFO, "CustomerDAO: {0} row(s) deleted.", result);
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
	    logger.log(Level.INFO, "CustomerDAO: batchDelete() ended.");
	    
	    return result;
	}

	public int batchUpdate(List<CustomerInfo> customers) {
		logger.log(Level.INFO, "CustomerDAO: batchUpdate() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    int result = 0;
	    try {
		    if(customers != null) {
				conn = DataSourceListener.getAssetDS().getConnection();
				conn.setAutoCommit(false);
				
				logger.log(Level.INFO, "CustomerDAO: update SQL: {0} ", updateCustomer);
				
				ps = conn.prepareStatement(updateCustomer);
				for(CustomerInfo customer : customers) {
					ps.setString(1, customer.getCustomerName());
					ps.setString(2, customer.getAddress1());
					ps.addBatch();
				}
				int[] batchResult = ps.executeBatch();
				conn.commit();
				
				result = IntStream.of(batchResult).sum();
				
				logger.log(Level.INFO, "CustomerDAO: {0} row(s) modified.", result);
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
	    logger.log(Level.INFO, "CustomerDAO: batchUpdate() ended.");
	    
	    return result;
	}

	public int updateCustomer(CustomerInfo customer) {
		logger.log(Level.INFO, "CustomerDAO: updateCustomer() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    int result = 0;
	    try {
		    if(customer != null) {
			    if (Validator.isEmpty(customer.getCustomerName()) || Validator.isEmpty(customer.getAddress1())) {
			    	throw new AssetException(AssetConstants.ERROR_INVALID_INPUT);
			    }
				conn = DataSourceListener.getAssetDS().getConnection();

				logger.log(Level.INFO, "CustomerDAO: update SQL: {0} ", updateCustomer);
				
				ps = conn.prepareStatement(updateCustomer);
				ps.setString(1, customer.getCustomerName());
				ps.setString(2, customer.getAddress1());

				result = ps.executeUpdate();

				logger.log(Level.INFO, "CustomerDAO: {0} row(s) modified.", result);
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
	    logger.log(Level.INFO, "CustomerDAO: updateCustomer() ended.");
	    
	    return result;
	}

	public List<CustomerInfo> selectAllCustomerInfo() {
		logger.log(Level.INFO, "CustomerDAO: selectAllCustomerInfo() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    List<CustomerInfo> customerList = new ArrayList<>();
	    try {
	    	conn = DataSourceListener.getAssetDS().getConnection();

			logger.log(Level.INFO, "CustomerDAO: selectAll SQL: {0} ", selectAll);
			
			ps = conn.prepareStatement(selectAll);

			rs = ps.executeQuery();
			while (rs.next()) {
				customerList.add(populateItems(rs));
			}
		    logger.log(Level.INFO, "CustomerDAO: customer list: {0}", customerList.size());
	    } catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AssetException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(rs);
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "CustomerDAO: selectAllCustomerInfo() ended.");
	    
	    return customerList;
	}

	public List<String> selectCustomerNames() {
		logger.log(Level.INFO, "CustomerDAO: selectCustomerNames() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    List<String> customerList = new ArrayList<>();
	    try {
	    	conn = DataSourceListener.getAssetDS().getConnection();

			logger.log(Level.INFO, "CustomerDAO: selectCustomerName SQL: {0} ", selectCustomerName);
			
			ps = conn.prepareStatement(selectCustomerName);

			rs = ps.executeQuery();
			while (rs.next()) {
				customerList.add(rs.getString("CustomerName"));
			}
		    logger.log(Level.INFO, "CustomerDAO: customerName list: {0}", customerList.size());
	    } catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AssetException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(rs);
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "CustomerDAO: selectCustomerNames() ended.");
	    
	    return customerList;
	}

	public List<String> selectCustomerAddress(String customerName) {
		logger.log(Level.INFO, "CustomerDAO: selectCustomerAddress() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    List<String> customerList = new ArrayList<>();
	    try {
	    	conn = DataSourceListener.getAssetDS().getConnection();

			logger.log(Level.INFO, "CustomerDAO: selectCustomerAddress SQL: {0} ", selectCustomerAddress);
			
			ps = conn.prepareStatement(selectCustomerAddress);
			ps.setString(1, customerName);
			
			rs = ps.executeQuery();
			while (rs.next()) {
				customerList.add(rs.getString("CustomerAddress1"));
			}
		    logger.log(Level.INFO, "CustomerDAO: customerName list: {0}", customerList.size());
	    } catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AssetException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(rs);
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "CustomerDAO: selectCustomerNames() ended.");
	    
	    return customerList;
	}

	public List<String> selectDistinctAddress() {
		logger.log(Level.INFO, "CustomerDAO: selectDistinctAddress() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    List<String> customerList = new ArrayList<>();
	    try {
	    	conn = DataSourceListener.getAssetDS().getConnection();

			logger.log(Level.INFO, "CustomerDAO: selectDistinctAddress SQL: {0} ", selectDistinctAddress);
			
			ps = conn.prepareStatement(selectDistinctAddress);
			
			rs = ps.executeQuery();
			while (rs.next()) {
				customerList.add(rs.getString("CustomerAddress1"));
			}
		    logger.log(Level.INFO, "CustomerDAO: customerAddress1 list: {0}", customerList.size());
	    } catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AssetException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(rs);
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "CustomerDAO: selectDistinctAddress() ended.");
	    
	    return customerList;
	}

	public CustomerInfo selectCustomerInfo(String customerName, String address1) {
		logger.log(Level.INFO, "CustomerDAO: selectCustomerInfo() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    CustomerInfo customer = new CustomerInfo();
	    try {
	    	conn = DataSourceListener.getAssetDS().getConnection();

			logger.log(Level.INFO, "CustomerDAO: selectCustomer SQL: {0} ", selectCustomer);
			
			ps = conn.prepareStatement(selectCustomer);
			ps.setString(1, customerName);
			ps.setString(2, address1);
			
			rs = ps.executeQuery();
			while (rs.next()) {
				customer = populateItems(rs);
			}
		    logger.log(Level.INFO, "CustomerDAO: customer : {0}", customer);
	    } catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AssetException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(rs);
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "CustomerDAO: selectCustomerInfo() ended.");
	    
	    return customer;
	}

	public List<CustomerInfo> selectCustomerInfo(int currentPage, int recordsPerPage) {
		logger.log(Level.INFO, "CustomerDAO: selectCustomerInfo() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    List<CustomerInfo> customerList = new ArrayList<>();
	    try {
	    	conn = DataSourceListener.getAssetDS().getConnection();

			logger.log(Level.INFO, "CustomerDAO: selectLimit SQL: {0} ", selectLimit);
			
			int offset = (currentPage * recordsPerPage) - recordsPerPage;
			
			ps = conn.prepareStatement(selectLimit);
			ps.setInt(1, offset);
			ps.setInt(2, recordsPerPage);
			
			rs = ps.executeQuery();
			while (rs.next()) {
				customerList.add(populateItems(rs));
			}
		    logger.log(Level.INFO, "CustomerDAO: customer list: {0}", customerList.size());
	    } catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AssetException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(rs);
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "CustomerDAO: selectCustomerInfo() ended.");
	    
	    return customerList;
	}

	public int getNumberOfRows() {
		logger.log(Level.INFO, "CustomerDAO: getNumberOfRows() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    int noOfRows = 0;
	    try {
	    	conn = DataSourceListener.getAssetDS().getConnection();

			logger.log(Level.INFO, "CustomerDAO: selectCount SQL: {0} ", selectCount);
			
			ps = conn.prepareStatement(selectCount);

			rs = ps.executeQuery();
			if (rs.next()) {
				noOfRows = rs.getInt(1);
			}
		    logger.log(Level.INFO, "CustomerDAO: noOfRows: {0}", noOfRows);
	    } catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AssetException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(rs);
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "CustomerDAO: getNumberOfRows() ended.");
	    
	    return noOfRows;
	}

	private CustomerInfo populateItems(ResultSet rs) throws SQLException{
		CustomerInfo customer = new CustomerInfo();

		customer.setCustomerName(rs.getString("CustomerName"));
		customer.setAddress1(rs.getString("CustomerAddress1"));
		customer.setAddress2(rs.getString("CustomerAddress2"));
		customer.setCity(rs.getString("City"));
		customer.setState(rs.getString("State"));
		customer.setZipCode(rs.getInt("ZipCode"));
		customer.setCountry(rs.getString("Country"));
		
		return customer;
	}
}
