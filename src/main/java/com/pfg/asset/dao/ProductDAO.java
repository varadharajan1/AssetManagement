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
import com.pfg.asset.dto.ProductInfo;
import com.pfg.asset.util.AssetConstants;
import com.pfg.asset.util.Validator;

public class ProductDAO {

	private static final Logger logger = Logger.getLogger(ProductDAO.class.getName());
	
	private String insertProduct = "INSERT INTO ProductInfo (ProductNumber, ProductDescription, SKU) VALUES (?,?,?)";
	private String selectAll = "SELECT ProductNumber, ProductDescription, SKU FROM ProductInfo ORDER BY ProductNumber";
	private String selectLimit = "SELECT ProductNumber, ProductDescription, SKU FROM ProductInfo ORDER BY ProductNumber OFFSET ? LIMIT ?";
	private String selectCount = "SELECT count(*) FROM ProductInfo";
	private String deleteProduct = "DELETE FROM ProductInfo WHERE ProductNumber=? AND SKU=?";
	private String updateProduct = "UPDATE ProductInfo SET LastUpdated=CURRENT_DATE WHERE ProductNumber=? AND ProductDescription=? AND SKU=?";

	private String selectProductNumber = "SELECT DISTINCT ProductNumber FROM ProductInfo ORDER BY ProductNumber";
	private String selectProductSKU = "SELECT DISTINCT SKU FROM ProductInfo WHERE ProductNumber=? ORDER BY SKU";
	private String selectProductDescription = "SELECT DISTINCT ProductDescription FROM ProductInfo WHERE ProductNumber=? AND SKU=?";

	public int insertProduct(ProductInfo product) {
		logger.log(Level.INFO, "ProductDAO: insertProduct() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    int result = 0;
	    try {
		    if(product != null) {
			    if (Validator.isEmpty(product.getProductNumber()) || Validator.isEmpty(product.getSKU())) {
			    	throw new AssetException(AssetConstants.ERROR_INVALID_INPUT);
			    }
				conn = DataSourceListener.getAssetDS().getConnection();

				logger.log(Level.INFO, "ProductDAO: insert SQL: {0} ", insertProduct);
				
				ps = conn.prepareStatement(insertProduct);
				ps.setString(1, product.getProductNumber());
				ps.setString(2, product.getProductDescription());
				ps.setString(3, product.getSKU());

				result = ps.executeUpdate();

				logger.log(Level.INFO, "ProductDAO: {0} row(s) inserted.", result);
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
	    logger.log(Level.INFO, "ProductDAO: insertProduct() ended.");
	    
	    return result;
	}

	public int deleteProduct(ProductInfo product) {
		logger.log(Level.INFO, "ProductDAO: deleteProduct() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    int result = 0;
	    try {
		    if(product != null) {
			    if (Validator.isEmpty(product.getProductNumber()) || Validator.isEmpty(product.getSKU())) {
			    	throw new AssetException(AssetConstants.ERROR_INVALID_INPUT);
			    }
				conn = DataSourceListener.getAssetDS().getConnection();

				logger.log(Level.INFO, "ProductDAO: delete SQL: {0} ", deleteProduct);
				
				ps = conn.prepareStatement(deleteProduct);
				ps.setString(1, product.getProductNumber());
				ps.setString(2, product.getSKU());

				result = ps.executeUpdate();

				logger.log(Level.INFO, "ProductDAO: {0} row(s) deleted.", result);
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
	    logger.log(Level.INFO, "ProductDAO: deleteProduct() ended.");
	    
	    return result;
	}

	public int batchUpdate(List<ProductInfo> products) {
		logger.log(Level.INFO, "ProductDAO: batchUpdate() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    int result = 0;
	    try {
		    if(products != null) {
				conn = DataSourceListener.getAssetDS().getConnection();
				conn.setAutoCommit(false);
				
				logger.log(Level.INFO, "ProductDAO: update SQL: {0} ", updateProduct);
				
				ps = conn.prepareStatement(updateProduct);
				for(ProductInfo product : products) {
					ps.setString(1, product.getProductNumber());
					ps.setString(2, product.getProductDescription());
					ps.setString(3, product.getSKU());
					ps.addBatch();
				}
				int[] batchResult = ps.executeBatch();
				conn.commit();
				
				result = IntStream.of(batchResult).sum();
				
				logger.log(Level.INFO, "ProductDAO: {0} row(s) modified.", result);
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
	    logger.log(Level.INFO, "ProductDAO: batchUpdate() ended.");
	    
	    return result;
	}

	public int updateProduct(ProductInfo product) {
		logger.log(Level.INFO, "ProductDAO: updateProduct() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    int result = 0;
	    try {
		    if(product != null) {
			    if (Validator.isEmpty(product.getProductNumber()) || Validator.isEmpty(product.getSKU())) {
			    	throw new AssetException(AssetConstants.ERROR_INVALID_INPUT);
			    }
				conn = DataSourceListener.getAssetDS().getConnection();

				logger.log(Level.INFO, "ProductDAO: update SQL: {0} ", updateProduct);
				
				ps = conn.prepareStatement(updateProduct);
				ps.setString(1, product.getProductNumber());
				ps.setString(2, product.getProductDescription());
				ps.setString(3, product.getSKU());

				result = ps.executeUpdate();

				logger.log(Level.INFO, "ProductDAO: {0} row(s) modified.", result);
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
	    logger.log(Level.INFO, "ProductDAO: updateProduct() ended.");
	    
	    return result;
	}

	public List<String> selectProductNumbers() {
		logger.log(Level.INFO, "ProductDAO: selectProductNumbers() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    List<String> productList = new ArrayList<>();
	    try {
	    	conn = DataSourceListener.getAssetDS().getConnection();

			logger.log(Level.INFO, "ProductDAO: selectProductNumber SQL: {0} ", selectProductNumber);
			
			ps = conn.prepareStatement(selectProductNumber);

			rs = ps.executeQuery();
			while (rs.next()) {
				productList.add(rs.getString("ProductNumber"));
			}
		    logger.log(Level.INFO, "ProductDAO: productNumber list: {0}", productList.size());
	    } catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AssetException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(rs);
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "ProductDAO: selectProductNumbers() ended.");
	    
	    return productList;
	}

	public List<String> selectSKUs(String productNumber) {
		logger.log(Level.INFO, "ProductDAO: selectProductSKUs() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    List<String> productList = new ArrayList<>();
	    try {
	    	conn = DataSourceListener.getAssetDS().getConnection();

			logger.log(Level.INFO, "ProductDAO: selectProductSKU SQL: {0} ", selectProductSKU);
			
			ps = conn.prepareStatement(selectProductSKU);
			ps.setString(1, productNumber);

			rs = ps.executeQuery();
			while (rs.next()) {
				productList.add(rs.getString("SKU"));
			}
		    logger.log(Level.INFO, "ProductDAO: productNumber list: {0}", productList.size());
	    } catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AssetException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(rs);
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "ProductDAO: selectProductSKUs() ended.");
	    
	    return productList;
	}

	public String selectProductionDescription(String productNumber, String sku) {
		logger.log(Level.INFO, "ProductDAO: selectProductionDescription() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    String productDescription = "";
	    try {
	    	conn = DataSourceListener.getAssetDS().getConnection();

			logger.log(Level.INFO, "ProductDAO: selectProductDescription SQL: {0} ", selectProductDescription);
			
			ps = conn.prepareStatement(selectProductDescription);
			ps.setString(1, productNumber);
			ps.setString(2, sku);

			rs = ps.executeQuery();
			while (rs.next()) {
				productDescription = rs.getString("ProductDescription");
			}
		    logger.log(Level.INFO, "ProductDAO: productDescription: {0}", productDescription);
	    } catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AssetException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(rs);
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "ProductDAO: selectProductionDescription() ended.");
	    
	    return productDescription;
	}

	public List<ProductInfo> selectAllProductInfo() {
		logger.log(Level.INFO, "ProductDAO: selectAllProductInfo() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    List<ProductInfo> productList = new ArrayList<>();
	    try {
	    	conn = DataSourceListener.getAssetDS().getConnection();

			logger.log(Level.INFO, "ProductDAO: selectAll SQL: {0} ", selectAll);
			
			ps = conn.prepareStatement(selectAll);

			rs = ps.executeQuery();
			while (rs.next()) {
				productList.add(populateItems(rs));
			}
		    logger.log(Level.INFO, "ProductDAO: product list: {0}", productList.size());
	    } catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AssetException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(rs);
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "ProductDAO: selectAllProductInfo() ended.");
	    
	    return productList;
	}

	public List<ProductInfo> selectProductInfo(int currentPage, int recordsPerPage) {
		logger.log(Level.INFO, "ProductDAO: selectProductInfo() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    List<ProductInfo> productList = new ArrayList<>();
	    try {
	    	conn = DataSourceListener.getAssetDS().getConnection();

			logger.log(Level.INFO, "ProductDAO: selectLimit SQL: {0} ", selectLimit);
			
			int offset = (currentPage * recordsPerPage) - recordsPerPage;
			
			ps = conn.prepareStatement(selectLimit);
			ps.setInt(1, offset);
			ps.setInt(2, recordsPerPage);
			
			rs = ps.executeQuery();
			while (rs.next()) {
				productList.add(populateItems(rs));
			}
		    logger.log(Level.INFO, "ProductDAO: product list: {0}", productList.size());
	    } catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AssetException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(rs);
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "ProductDAO: selectProductInfo() ended.");
	    
	    return productList;
	}

	public int getNumberOfRows() {
		logger.log(Level.INFO, "ProductDAO: getNumberOfRows() executed.");
		
		Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    int noOfRows = 0;
	    try {
	    	conn = DataSourceListener.getAssetDS().getConnection();

			logger.log(Level.INFO, "ProductDAO: selectCount SQL: {0} ", selectCount);
			
			ps = conn.prepareStatement(selectCount);

			rs = ps.executeQuery();
			if (rs.next()) {
				noOfRows = rs.getInt(1);
			}
		    logger.log(Level.INFO, "ProductDAO: noOfRows: {0}", noOfRows);
	    } catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AssetException(e.getMessage());
	    }
	    finally {
			DAOUtil.close(rs);
			DAOUtil.closePreparedStatement(ps);
			DAOUtil.close(conn);
	    }
	    logger.log(Level.INFO, "ProductDAO: getNumberOfRows() ended.");
	    
	    return noOfRows;
	}

	private ProductInfo populateItems(ResultSet rs) throws SQLException{
		ProductInfo product = new ProductInfo();

		product.setProductNumber(rs.getString("ProductNumber"));
		product.setProductDescription(rs.getString("ProductDescription"));
		product.setSKU(rs.getString("SKU"));
		
		return product;
	}
}
