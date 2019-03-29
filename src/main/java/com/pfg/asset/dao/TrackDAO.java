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
import com.pfg.asset.dto.TrackInfo;
import com.pfg.asset.util.AssetConstants;
import com.pfg.asset.util.Validator;

public class TrackDAO {

	private static final Logger logger = Logger.getLogger(TrackDAO.class.getName());
	
	private String insertTrack = "INSERT INTO TrackInfo (TrackName, TrackDescription) VALUES (?,?)";
	private String selectAll = "SELECT TrackName, TrackDescription FROM TrackInfo ORDER BY TrackName";
	private String selectLimit = "SELECT TrackName, TrackDescription FROM TrackInfo ORDER BY TrackName OFFSET ? LIMIT ?";
	private String selectCount = "SELECT count(*) FROM TrackInfo";
	private String deleteTrack = "DELETE FROM TrackInfo WHERE TrackName=?";
	private String updateTrack = "UPDATE TrackInfo SET LastUpdated=CURRENT_DATE WHERE TrackName=? AND TrackDescription=?";

	public int insertTrack(TrackInfo track) {
		logger.log(Level.INFO, "TrackDAO: insertTrack() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    int result = 0;
	    try {
		    if(track != null) {
			    if (Validator.isEmpty(track.getTrackName())) {
			    	throw new AssetException(AssetConstants.ERROR_INVALID_INPUT);
			    }
				conn = DataSourceListener.getAssetDS().getConnection();

				logger.log(Level.INFO, "TrackDAO: insert SQL: {0} ", insertTrack);
				
				ps = conn.prepareStatement(insertTrack);
				ps.setString(1, track.getTrackName());
				ps.setString(2, track.getTrackDescription());

				result = ps.executeUpdate();

				logger.log(Level.INFO, "TrackDAO: {0} row(s) inserted.", result);
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
	    logger.log(Level.INFO, "TrackDAO: insertTrack() ended.");
	    
	    return result;
	}

	public int deleteTrack(TrackInfo track) {
		logger.log(Level.INFO, "TrackDAO: deleteTrack() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    int result = 0;
	    try {
		    if(track != null) {
			    if (Validator.isEmpty(track.getTrackName())) {
			    	throw new AssetException(AssetConstants.ERROR_INVALID_INPUT);
			    }
				conn = DataSourceListener.getAssetDS().getConnection();

				logger.log(Level.INFO, "TrackDAO: delete SQL: {0} ", deleteTrack);
				
				ps = conn.prepareStatement(deleteTrack);
				ps.setString(1, track.getTrackName());

				result = ps.executeUpdate();

				logger.log(Level.INFO, "TrackDAO: {0} row(s) deleted.", result);
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
	    logger.log(Level.INFO, "TrackDAO: deleteTrack() ended.");
	    
	    return result;
	}

	public int batchUpdate(List<TrackInfo> tracks) {
		logger.log(Level.INFO, "TrackDAO: batchUpdate() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    int result = 0;
	    try {
		    if(tracks != null) {
				conn = DataSourceListener.getAssetDS().getConnection();
				conn.setAutoCommit(false);
				
				logger.log(Level.INFO, "TrackDAO: update SQL: {0} ", updateTrack);
				
				ps = conn.prepareStatement(updateTrack);
				for(TrackInfo track : tracks) {
					ps.setString(1, track.getTrackName());
					ps.setString(2, track.getTrackDescription());
					ps.addBatch();
				}
				int[] batchResult = ps.executeBatch();
				conn.commit();
				
				result = IntStream.of(batchResult).sum();
				
				logger.log(Level.INFO, "TrackDAO: {0} row(s) modified.", result);
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
	    logger.log(Level.INFO, "TrackDAO: batchUpdate() ended.");
	    
	    return result;
	}

	public int updateTrack(TrackInfo track) {
		logger.log(Level.INFO, "TrackDAO: updateTrack() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    int result = 0;
	    try {
		    if(track != null) {
			    if (Validator.isEmpty(track.getTrackName())) {
			    	throw new AssetException(AssetConstants.ERROR_INVALID_INPUT);
			    }
				conn = DataSourceListener.getAssetDS().getConnection();

				logger.log(Level.INFO, "TrackDAO: update SQL: {0} ", updateTrack);
				
				ps = conn.prepareStatement(updateTrack);
				ps.setString(1, track.getTrackName());
				ps.setString(2, track.getTrackDescription());

				result = ps.executeUpdate();

				logger.log(Level.INFO, "TrackDAO: {0} row(s) modified.", result);
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
	    logger.log(Level.INFO, "TrackDAO: updateTrack() ended.");
	    
	    return result;
	}

	public List<TrackInfo> selectAllTrackInfo() {
		logger.log(Level.INFO, "TrackDAO: selectAllTrackInfo() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    List<TrackInfo> trackList = new ArrayList<>();
	    try {
	    	conn = DataSourceListener.getAssetDS().getConnection();

			logger.log(Level.INFO, "TrackDAO: selectAll SQL: {0} ", selectAll);
			
			ps = conn.prepareStatement(selectAll);

			rs = ps.executeQuery();
			while (rs.next()) {
				trackList.add(populateItems(rs));
			}
		    logger.log(Level.INFO, "TrackDAO: track list: {0}", trackList.size());
	    } catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AssetException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(rs);
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "TrackDAO: selectAllTrackInfo() ended.");
	    
	    return trackList;
	}

	public List<TrackInfo> selectTrackInfo(int currentPage, int recordsPerPage) {
		logger.log(Level.INFO, "TrackDAO: selectTrackInfo() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    List<TrackInfo> trackList = new ArrayList<>();
	    try {
	    	conn = DataSourceListener.getAssetDS().getConnection();

			logger.log(Level.INFO, "TrackDAO: selectLimit SQL: {0} ", selectLimit);
			
			int offset = (currentPage * recordsPerPage) - recordsPerPage;
			
			ps = conn.prepareStatement(selectLimit);
			ps.setInt(1, offset);
			ps.setInt(2, recordsPerPage);
			
			rs = ps.executeQuery();
			while (rs.next()) {
				trackList.add(populateItems(rs));
			}
		    logger.log(Level.INFO, "TrackDAO: track list: {0}", trackList.size());
	    } catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AssetException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(rs);
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "TrackDAO: selectTrackInfo() ended.");
	    
	    return trackList;
	}

	public int getNumberOfRows() {
		logger.log(Level.INFO, "TrackDAO: getNumberOfRows() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    int noOfRows = 0;
	    try {
	    	conn = DataSourceListener.getAssetDS().getConnection();

			logger.log(Level.INFO, "TrackDAO: selectCount SQL: {0} ", selectCount);
			
			ps = conn.prepareStatement(selectCount);

			rs = ps.executeQuery();
			if (rs.next()) {
				noOfRows = rs.getInt(1);
			}
		    logger.log(Level.INFO, "TrackDAO: noOfRows: {0}", noOfRows);
	    } catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AssetException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(rs);
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "TrackDAO: getNumberOfRows() ended.");
	    
	    return noOfRows;
	}

	private TrackInfo populateItems(ResultSet rs) throws SQLException{
		TrackInfo track = new TrackInfo();

		track.setTrackName(rs.getString("TrackName"));
		track.setTrackDescription(rs.getString("TrackDescription"));
		
		return track;
	}
}
