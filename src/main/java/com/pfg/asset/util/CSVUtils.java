package com.pfg.asset.util;

import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.text.StringEscapeUtils;

import com.pfg.asset.dto.AssetInfo;

public class CSVUtils {

	private static final Logger logger = Logger.getLogger(CSVUtils.class.getName());
    private static final String DEFAULT_SEPARATOR = ",";
    private static final String NEWLINE_SEPARATOR = "\n";
	private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("com.pfg.asset.nls.asset_management");

	private CSVUtils() {}
	
    public static String write(List<AssetInfo> assetList) {
		logger.log(Level.INFO, "entered" );
    	
		StringBuilder sb = new StringBuilder();
    	try {
        	sb.append(resourceBundle.getString("label.asset.detail.trackName")).append(DEFAULT_SEPARATOR);
        	sb.append(resourceBundle.getString("label.asset.detail.opcoName")).append(DEFAULT_SEPARATOR);
        	sb.append(resourceBundle.getString("label.asset.detail.deviceName")).append(DEFAULT_SEPARATOR);
        	sb.append(resourceBundle.getString("label.asset.detail.oemName")).append(DEFAULT_SEPARATOR);
        	sb.append(resourceBundle.getString("label.asset.detail.productNumber")).append(DEFAULT_SEPARATOR);
        	sb.append(resourceBundle.getString("label.asset.detail.productDescription")).append(DEFAULT_SEPARATOR);
        	sb.append(resourceBundle.getString("label.asset.detail.quantity")).append(DEFAULT_SEPARATOR);
        	sb.append(resourceBundle.getString("label.asset.detail.contractNumber")).append(DEFAULT_SEPARATOR);
        	sb.append(resourceBundle.getString("label.asset.detail.serviceLevel")).append(DEFAULT_SEPARATOR);
        	sb.append(resourceBundle.getString("label.asset.detail.serialNumber")).append(DEFAULT_SEPARATOR);
        	sb.append(resourceBundle.getString("label.asset.detail.serviceLevelDescription")).append(DEFAULT_SEPARATOR);
        	sb.append(resourceBundle.getString("label.asset.detail.sku")).append(DEFAULT_SEPARATOR);
        	sb.append(resourceBundle.getString("label.asset.detail.startDate")).append(DEFAULT_SEPARATOR);
        	sb.append(resourceBundle.getString("label.asset.detail.endDate")).append(DEFAULT_SEPARATOR);
        	sb.append(resourceBundle.getString("label.asset.detail.deployedLocation")).append(DEFAULT_SEPARATOR);
        	sb.append(resourceBundle.getString("label.asset.detail.deployedAddress1")).append(DEFAULT_SEPARATOR);
        	sb.append(resourceBundle.getString("label.asset.detail.deployedAddress2")).append(DEFAULT_SEPARATOR);
        	sb.append(resourceBundle.getString("label.asset.detail.deployedCity")).append(DEFAULT_SEPARATOR);
        	sb.append(resourceBundle.getString("label.asset.detail.deployedState")).append(DEFAULT_SEPARATOR);
        	sb.append(resourceBundle.getString("label.asset.detail.deployedZipCode")).append(DEFAULT_SEPARATOR);
        	sb.append(resourceBundle.getString("label.asset.detail.deployedCountry"));
            sb.append(NEWLINE_SEPARATOR);
            
            for (AssetInfo assetInfo: assetList) {
            	sb.append(StringEscapeUtils.escapeCsv(assetInfo.getTrackName())).append(DEFAULT_SEPARATOR);
            	sb.append(StringEscapeUtils.escapeCsv(assetInfo.getOpcoName())).append(DEFAULT_SEPARATOR);
            	sb.append(StringEscapeUtils.escapeCsv(assetInfo.getDeviceName())).append(DEFAULT_SEPARATOR);
            	sb.append(StringEscapeUtils.escapeCsv(assetInfo.getOemName())).append(DEFAULT_SEPARATOR);
            	sb.append(StringEscapeUtils.escapeCsv(assetInfo.getProductNumber())).append(DEFAULT_SEPARATOR);
            	sb.append(StringEscapeUtils.escapeCsv(assetInfo.getProductDescription())).append(DEFAULT_SEPARATOR);
            	sb.append(assetInfo.getQuantity()).append(DEFAULT_SEPARATOR);
            	sb.append(StringEscapeUtils.escapeCsv(assetInfo.getContractNumber())).append(DEFAULT_SEPARATOR);
            	sb.append(StringEscapeUtils.escapeCsv(assetInfo.getServiceLevel())).append(DEFAULT_SEPARATOR);
            	sb.append(StringEscapeUtils.escapeCsv(assetInfo.getSerialNumber())).append(DEFAULT_SEPARATOR);
            	sb.append(StringEscapeUtils.escapeCsv(assetInfo.getServiceLevelDescription())).append(DEFAULT_SEPARATOR);
            	sb.append(StringEscapeUtils.escapeCsv(assetInfo.getSku())).append(DEFAULT_SEPARATOR);
            	sb.append(StringEscapeUtils.escapeCsv(assetInfo.getStartDate())).append(DEFAULT_SEPARATOR);
            	sb.append(StringEscapeUtils.escapeCsv(assetInfo.getEndDate())).append(DEFAULT_SEPARATOR);
            	sb.append(StringEscapeUtils.escapeCsv(assetInfo.getDeployedLocation())).append(DEFAULT_SEPARATOR);
            	sb.append(StringEscapeUtils.escapeCsv(assetInfo.getDeployedAddress1())).append(DEFAULT_SEPARATOR);
            	sb.append(StringEscapeUtils.escapeCsv(assetInfo.getDeployedAddress2())).append(DEFAULT_SEPARATOR);
            	sb.append(StringEscapeUtils.escapeCsv(assetInfo.getDeployedCity())).append(DEFAULT_SEPARATOR);
            	sb.append(StringEscapeUtils.escapeCsv(assetInfo.getDeployedState())).append(DEFAULT_SEPARATOR);
            	sb.append(StringEscapeUtils.escapeCsv(assetInfo.getDeployedZipCode())).append(DEFAULT_SEPARATOR);
            	sb.append(StringEscapeUtils.escapeCsv(assetInfo.getDeployedCountry()));
                sb.append(NEWLINE_SEPARATOR);
            }
        } catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
        }
		logger.log(Level.INFO, "exited" );
		return sb.toString();
    }
}
