package com.pfg.asset.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.pfg.asset.dao.DAOFactory;
import com.pfg.asset.dto.AssetResponse;
import com.pfg.asset.dto.FilterParam;
import com.pfg.asset.dto.ResponseData;
import com.pfg.asset.dto.ResponseDetails;
import com.pfg.asset.util.AssetConstants;
import com.pfg.asset.util.Validator;

public class AssetServiceImpl {
	private static final Logger logger = Logger.getLogger(AssetServiceImpl.class.getName());
	
	public AssetResponse distinctAssetColumn(FilterParam filter) {
		AssetResponse response = new AssetResponse();
		logger.log(Level.INFO, "distinctAssetColumn: invoked");
		try {
		    if(filter != null) {
				if (Validator.isEmpty(filter.getFilterType()) || Validator.isEmpty(filter.getFilterValue())) {
					return generateFailureResponse(generateFailureDetails("1", AssetConstants.ERROR_INVALID_INPUT));
				}
				List<String> assetColumnList = DAOFactory.getInstance().getAssetDashboardDAO().filterByColumn(filter.getFilterType(), filter.getFilterValue());
				
				String message = assetColumnList.size() + " row(s) retrieved successfully.";
				
				ResponseData data = new ResponseData();
				data.setFilteredColumnList(assetColumnList);
				response.setData(data);
				response.setDetails(generateSuccessDetails(message));
		    }else {
				logger.log(Level.INFO, AssetConstants.ERROR_INVALID_INPUT);
				return generateFailureResponse(generateFailureDetails("1", AssetConstants.ERROR_INVALID_INPUT));
		    }
		} catch (Exception e) {
			logger.log(Level.INFO, e.getMessage(), e);
			return generateFailureResponse(generateFailureDetails("128", e.getMessage()));
		}
		logger.log(Level.INFO, "distinctAssetColumn: ended");
		return response;
	}

	public AssetResponse getSerialNumbers() {
		AssetResponse response = new AssetResponse();
		try {
			logger.log(Level.INFO, "AssetServiceImpl: getSerialNumbers() invoked. ");
			
			List<String> serialNumberList = DAOFactory.getInstance().getAssetDashboardDAO().distinctColumns("SERIAL");

			String message = serialNumberList.size() + " row(s) retrieved successfully.";
			
			ResponseData data = new ResponseData();
			data.setSerialNumberList(serialNumberList);
			response.setData(data);
			response.setDetails(generateSuccessDetails(message));
			
			logger.log(Level.INFO, "AssetServiceImpl: getSerialNumbers(): {0}", serialNumberList.size());
		} catch (Exception e) {
			logger.log(Level.INFO, e.getMessage(), e);
			return generateFailureResponse(generateFailureDetails("128", e.getMessage()));
		}
		logger.log(Level.INFO, "AssetServiceImpl: getSerialNumbers: ended");
		return response;
	}

	private ResponseDetails generateFailureDetails(String problemTypes, String message) {
		ResponseDetails details = new ResponseDetails();
		details.setStatus(AssetConstants.FAILURESTATUS);
		if (problemTypes == null) {
			details.setProblemTypes("65536");
		} else {
			details.setProblemTypes(problemTypes);
		}
		details.setReturnCode("-1");
		details.setMessage(message);
		return details;
	}
	  
	private ResponseDetails generateSuccessDetails(String message) {
		ResponseDetails details = new ResponseDetails();
		details.setStatus(AssetConstants.SUCCESSSTATUS);
		details.setProblemTypes("0");
		details.setReturnCode("0");
		details.setMessage(message);
		return details;
	}
	  
	private AssetResponse generateFailureResponse(ResponseDetails details) {
		AssetResponse response = new AssetResponse();
		ResponseData data = new ResponseData();
		
		response.setData(data);
		response.setDetails(details);
		
		return response;
	}	
}
