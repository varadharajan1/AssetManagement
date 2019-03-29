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
import com.pfg.asset.dto.OEMInfo;
import com.pfg.asset.util.AssetConstants;
import com.pfg.asset.util.Validator;

public class OEMDAO {

	private static final Logger logger = Logger.getLogger(OEMDAO.class.getName());
	
	private String insertOEM = "INSERT INTO OEMInfo (OEMName, OEMDescription) VALUES (?,?)";
	private String selectAll = "SELECT OEMName, OEMDescription FROM OEMInfo ORDER BY OEMName";
	private String selectLimit = "SELECT OEMName, OEMDescription FROM OEMInfo ORDER BY OEMName OFFSET ? LIMIT ?";
	private String selectCount = "SELECT count(*) FROM OEMInfo";
	private String deleteOEM = "DELETE FROM OEMInfo WHERE OEMName=?";
	private String updateOEM = "UPDATE OEMInfo SET LastUpdated=CURRENT_DATE WHERE OEMName=? AND OEMDescription=?";

	public int insertOEM(OEMInfo oem) {
		logger.log(Level.INFO, "OEMDAO: insertOEM() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    int result = 0;
	    try {
		    if(oem != null) {
			    if (Validator.isEmpty(oem.getOemName())) {
			    	throw new AssetException(AssetConstants.ERROR_INVALID_INPUT);
			    }
				conn = DataSourceListener.getAssetDS().getConnection();

				logger.log(Level.INFO, "OEMDAO: insert SQL: {0} ", insertOEM);
				
				ps = conn.prepareStatement(insertOEM);
				ps.setString(1, oem.getOemName());
				ps.setString(2, oem.getOemDescription());

				result = ps.executeUpdate();

				logger.log(Level.INFO, "OEMDAO: {0} row(s) inserted.", result);
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
	    logger.log(Level.INFO, "OEMDAO: insertOEM() ended.");
	    
	    return result;
	}

	public int deleteOEM(OEMInfo oem) {
		logger.log(Level.INFO, "OEMDAO: deleteOEM() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    int result = 0;
	    try {
		    if(oem != null) {
			    if (Validator.isEmpty(oem.getOemName())) {
			    	throw new AssetException(AssetConstants.ERROR_INVALID_INPUT);
			    }
				conn = DataSourceListener.getAssetDS().getConnection();

				logger.log(Level.INFO, "OEMDAO: delete SQL: {0} ", deleteOEM);
				
				ps = conn.prepareStatement(deleteOEM);
				ps.setString(1, oem.getOemName());

				result = ps.executeUpdate();

				logger.log(Level.INFO, "OEMDAO: {0} row(s) deleted.", result);
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
	    logger.log(Level.INFO, "OEMDAO: deleteOEM() ended.");
	    
	    return result;
	}

	public int batchUpdate(List<OEMInfo> oems) {
		logger.log(Level.INFO, "OEMDAO: batchUpdate() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    int result = 0;
	    try {
		    if(oems != null) {
				conn = DataSourceListener.getAssetDS().getConnection();
				conn.setAutoCommit(false);
				
				logger.log(Level.INFO, "OEMDAO: update SQL: {0} ", updateOEM);
				
				ps = conn.prepareStatement(updateOEM);
				for(OEMInfo oem : oems) {
					ps.setString(1, oem.getOemName());
					ps.setString(2, oem.getOemDescription());
					ps.addBatch();
				}
				int[] batchResult = ps.executeBatch();
				conn.commit();
				
				result = IntStream.of(batchResult).sum();
				
				logger.log(Level.INFO, "OEMDAO: {0} row(s) modified.", result);
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
	    logger.log(Level.INFO, "OEMDAO: batchUpdate() ended.");
	    
	    return result;
	}

	public int updateOEM(OEMInfo oem) {
		logger.log(Level.INFO, "OEMDAO: updateOEM() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    int result = 0;
	    try {
		    if(oem != null) {
			    if (Validator.isEmpty(oem.getOemName())) {
			    	throw new AssetException(AssetConstants.ERROR_INVALID_INPUT);
			    }
				conn = DataSourceListener.getAssetDS().getConnection();

				logger.log(Level.INFO, "OEMDAO: update SQL: {0} ", updateOEM);
				
				ps = conn.prepareStatement(updateOEM);
				ps.setString(1, oem.getOemName());
				ps.setString(2, oem.getOemDescription());

				result = ps.executeUpdate();

				logger.log(Level.INFO, "OEMDAO: {0} row(s) modified.", result);
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
	    logger.log(Level.INFO, "OEMDAO: updateOEM() ended.");
	    
	    return result;
	}

	public List<OEMInfo> selectAllOEMInfo() {
		logger.log(Level.INFO, "OEMDAO: selectAllOEMInfo() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    List<OEMInfo> oemList = new ArrayList<>();
	    try {
	    	conn = DataSourceListener.getAssetDS().getConnection();

			logger.log(Level.INFO, "OEMDAO: selectAll SQL: {0} ", selectAll);
			
			ps = conn.prepareStatement(selectAll);

			rs = ps.executeQuery();
			while (rs.next()) {
				oemList.add(populateItems(rs));
			}
		    logger.log(Level.INFO, "OEMDAO: oem list: {0}", oemList.size());
	    } catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AssetException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(rs);
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "OEMDAO: selectAllOEMInfo() ended.");
	    
	    return oemList;
	}

	public List<OEMInfo> selectOEMInfo(int currentPage, int recordsPerPage) {
		logger.log(Level.INFO, "OEMDAO: selectOEMInfo() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    List<OEMInfo> oemList = new ArrayList<>();
	    try {
	    	conn = DataSourceListener.getAssetDS().getConnection();

			logger.log(Level.INFO, "OEMDAO: selectLimit SQL: {0} ", selectLimit);
			
			int offset = (currentPage * recordsPerPage) - recordsPerPage;
			
			ps = conn.prepareStatement(selectLimit);
			ps.setInt(1, offset);
			ps.setInt(2, recordsPerPage);
			
			rs = ps.executeQuery();
			while (rs.next()) {
				oemList.add(populateItems(rs));
			}
		    logger.log(Level.INFO, "OEMDAO: oem list: {0}", oemList.size());
	    } catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AssetException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(rs);
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "OEMDAO: selectOEMInfo() ended.");
	    
	    return oemList;
	}

	public int getNumberOfRows() {
		logger.log(Level.INFO, "OEMDAO: getNumberOfRows() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    int noOfRows = 0;
	    try {
	    	conn = DataSourceListener.getAssetDS().getConnection();

			logger.log(Level.INFO, "OEMDAO: selectCount SQL: {0} ", selectCount);
			
			ps = conn.prepareStatement(selectCount);

			rs = ps.executeQuery();
			if (rs.next()) {
				noOfRows = rs.getInt(1);
			}
		    logger.log(Level.INFO, "OEMDAO: noOfRows: {0}", noOfRows);
	    } catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AssetException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(rs);
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "OEMDAO: getNumberOfRows() ended.");
	    
	    return noOfRows;
	}

	private OEMInfo populateItems(ResultSet rs) throws SQLException{
		OEMInfo oem = new OEMInfo();

		oem.setOemName(rs.getString("OEMName"));
		oem.setOemDescription(rs.getString("OEMDescription"));
		
		return oem;
	}
}
