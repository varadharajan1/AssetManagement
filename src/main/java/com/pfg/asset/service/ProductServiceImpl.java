package com.pfg.asset.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.pfg.asset.dao.DAOFactory;
import com.pfg.asset.dto.AssetResponse;
import com.pfg.asset.dto.ProductInfo;
import com.pfg.asset.dto.ResponseData;
import com.pfg.asset.dto.ResponseDetails;
import com.pfg.asset.util.AssetConstants;
import com.pfg.asset.util.Validator;

public class ProductServiceImpl {
	private static final Logger logger = Logger.getLogger(ProductServiceImpl.class.getName());
	
	public AssetResponse getProductDescription(ProductInfo product) {
		AssetResponse employeeResponse = new AssetResponse();
		logger.log(Level.INFO, "ProductServiceImpl: getProductDescription: invoked");
		try {
		    if(product != null) {
				if (Validator.isEmpty(product.getProductNumber()) || Validator.isEmpty(product.getSKU())) {
					return generateFailureResponse(generateFailureDetails("1", AssetConstants.ERROR_INVALID_INPUT));
				}
				String productDescription = DAOFactory.getInstance().getProductDAO().selectProductionDescription(product.getProductNumber(), product.getSKU());
				
				String message = "1 row(s) retrieved successfully.";
				
				ResponseData data = new ResponseData();
				data.setProductDescription(productDescription);
				employeeResponse.setData(data);
				employeeResponse.setDetails(generateSuccessDetails(message));
		    }else {
				logger.log(Level.INFO, AssetConstants.ERROR_INVALID_INPUT);
				return generateFailureResponse(generateFailureDetails("1", AssetConstants.ERROR_INVALID_INPUT));
		    }
		} catch (Exception e) {
			logger.log(Level.INFO, e.getMessage(), e);
			return generateFailureResponse(generateFailureDetails("128", e.getMessage()));
		}
		logger.log(Level.INFO, "ProductServiceImpl: getProductDescription: ended");
		return employeeResponse;
	}

	public AssetResponse getProductSKUs(String productNumber) {
		AssetResponse employeeResponse = new AssetResponse();
		try {
			logger.log(Level.INFO, "ProductServiceImpl: getProductSKUs() invoked. ");
			
			if (Validator.isEmpty(productNumber)) {
				return generateFailureResponse(generateFailureDetails("1", AssetConstants.ERROR_INVALID_INPUT));
			}
			logger.log(Level.INFO, "ProductServiceImpl: getProductSKUs() invoked for the productNumber: [{0}]", productNumber);

			List<String> productSKUList = DAOFactory.getInstance().getProductDAO().selectSKUs(productNumber);

			String message = productSKUList.size() + " row(s) retrieved successfully.";
			
			ResponseData data = new ResponseData();
			data.setProductSKUList(productSKUList);
			employeeResponse.setData(data);
			employeeResponse.setDetails(generateSuccessDetails(message));
			
			logger.log(Level.INFO, "ProductServiceImpl: getProductSKUs(): {0}", productSKUList.size());
		} catch (Exception e) {
			logger.log(Level.INFO, e.getMessage(), e);
			return generateFailureResponse(generateFailureDetails("128", e.getMessage()));
		}
		logger.log(Level.INFO, "ProductServiceImpl: getProductSKUs: ended");
		return employeeResponse;
	}

	public AssetResponse getProductNumbers() {
		AssetResponse employeeResponse = new AssetResponse();
		try {
			logger.log(Level.INFO, "ProductServiceImpl: getProductNumbers() invoked. ");
			
			List<String> productNameList = DAOFactory.getInstance().getProductDAO().selectProductNumbers();

			String message = productNameList.size() + " row(s) retrieved successfully.";
			
			ResponseData data = new ResponseData();
			data.setProductNameList(productNameList);
			employeeResponse.setData(data);
			employeeResponse.setDetails(generateSuccessDetails(message));
			
			logger.log(Level.INFO, "ProductServiceImpl: getProductNumbers(): {0}", productNameList.size());
		} catch (Exception e) {
			logger.log(Level.INFO, e.getMessage(), e);
			return generateFailureResponse(generateFailureDetails("128", e.getMessage()));
		}
		logger.log(Level.INFO, "ProductServiceImpl: getProductNumbers: ended");
		return employeeResponse;
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
		AssetResponse employeeResponse = new AssetResponse();
		ResponseData data = new ResponseData();
		
		employeeResponse.setData(data);
		employeeResponse.setDetails(details);
		
		return employeeResponse;
	}	
}
