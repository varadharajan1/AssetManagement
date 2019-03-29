package com.pfg.asset.dao;

public class DAOFactory {

	private static final DAOFactory factory = new DAOFactory();
	private ProductDAO productDAO;
	private TrackDAO trackDAO;
	private OEMDAO oemDAO;
	private CustomerDAO customerDAO;
	private AssetDetailDAO assetDetailDAO;
	private AssetDashboardDAO assetDashboardDAO;
	private AssetInfoDAO assetInfoDAO;
	private LoadAssetData assetDataDAO;
	private AssetConfigDAO assetConfigDAO;
  
	private DAOFactory() { }
	
	public static DAOFactory getInstance() {
		return factory;
	}

	public ProductDAO getProductDAO() {
		if(productDAO == null) {
			productDAO = new ProductDAO();
		}
		return productDAO;
	}

	public TrackDAO getTrackDAO() {
		if(trackDAO == null) {
			trackDAO = new TrackDAO();
		}
		return trackDAO;
	}

	public OEMDAO getOEMDAO() {
		if(oemDAO == null) {
			oemDAO = new OEMDAO();
		}
		return oemDAO;
	}

	public CustomerDAO getCustomerDAO() {
		if(customerDAO == null) {
			customerDAO = new CustomerDAO();
		}
		return customerDAO;
	}

	public AssetDetailDAO getAssetDetailDAO() {
		if(assetDetailDAO == null) {
			assetDetailDAO = new AssetDetailDAO();
		}
		return assetDetailDAO;
	}

	public LoadAssetData getAssetDataDAO() {
		if(assetDataDAO == null) {
			assetDataDAO = new LoadAssetData();
		}
		return assetDataDAO;
	}

	public AssetDashboardDAO getAssetDashboardDAO() {
		if(assetDashboardDAO == null) {
			assetDashboardDAO = new AssetDashboardDAO();
		}
		return assetDashboardDAO;
	}

	public AssetInfoDAO getAssetInfoDAO() {
		if(assetInfoDAO == null) {
			assetInfoDAO = new AssetInfoDAO();
		}
		return assetInfoDAO;
	}

	public AssetConfigDAO getAssetConfigDAO() {
		if(assetConfigDAO == null) {
			assetConfigDAO = new AssetConfigDAO();
		}
		return assetConfigDAO;
	}

}
