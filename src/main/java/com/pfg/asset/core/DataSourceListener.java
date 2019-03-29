package com.pfg.asset.core;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import com.pfg.asset.util.AssetCronTrigger;

public class DataSourceListener implements ServletContextListener {

	private static final Logger logger = Logger.getLogger(DataSourceListener.class.getName());
	@Resource(name="jdbc/assetDS")
	private static DataSource assetDS;
	
	private AssetCronTrigger cronTrigger = null;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	    logger.log(Level.INFO, "releasing the assetDS datasource.");
	    assetDS = null;
		try {
			cronTrigger.unSchedule();
		}catch(Exception e) {
			logger.log(Level.SEVERE, "AssetCronTrigger failed while deleting the Job" );
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AssetException(e.getMessage());
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.log(Level.INFO, "DataSourceListener initialized.");
		if(assetDS != null) {
			try {
				cronTrigger = new AssetCronTrigger();
				cronTrigger.schedule();
			}catch(Exception e) {
				logger.log(Level.SEVERE, "AssetCronTrigger failed while scheduling the trigger" );
				logger.log(Level.SEVERE, e.getMessage(), e);
				throw new AssetException(e.getMessage());
			}
		}
	}

	public static DataSource getAssetDS() {
		return assetDS;
	}
}
