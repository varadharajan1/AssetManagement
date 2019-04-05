package com.pfg.asset.dao;

public class DAOFactory {

	private static final DAOFactory factory = new DAOFactory();
	private AssetDashboardDAO assetDashboardDAO;
	private AssetInfoDAO assetInfoDAO;
	private AssetConfigDAO assetConfigDAO;
  
	private DAOFactory() { }
	
	public static DAOFactory getInstance() {
		return factory;
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
