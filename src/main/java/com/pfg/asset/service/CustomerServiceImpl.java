package com.pfg.asset.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.pfg.asset.dao.DAOFactory;
import com.pfg.asset.dto.AssetResponse;
import com.pfg.asset.dto.CustomerInfo;
import com.pfg.asset.dto.ResponseData;
import com.pfg.asset.dto.ResponseDetails;
import com.pfg.asset.util.AssetConstants;
import com.pfg.asset.util.Validator;

public class CustomerServiceImpl {
	private static final Logger logger = Logger.getLogger(CustomerServiceImpl.class.getName());
	
	public AssetResponse getCustomerInfo(CustomerInfo customer) {
		AssetResponse employeeResponse = new AssetResponse();
		logger.log(Level.INFO, "CustomerServiceImpl: getCustomerInfo: invoked");
		try {
		    if(customer != null) {
				if (Validator.isEmpty(customer.getCustomerName()) || Validator.isEmpty(customer.getAddress1())) {
					return generateFailureResponse(generateFailureDetails("1", AssetConstants.ERROR_INVALID_INPUT));
				}
				CustomerInfo customerInfo = DAOFactory.getInstance().getCustomerDAO().selectCustomerInfo(customer.getCustomerName(), customer.getAddress1());
				
				String message = "1 row(s) retrieved successfully.";
				
				ResponseData data = new ResponseData();
				data.setCustomerInfo(customerInfo);
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
		logger.log(Level.INFO, "CustomerServiceImpl: getCustomerInfo: ended");
		return employeeResponse;
	}

	public AssetResponse getCustomerAddress(String customerName) {
		AssetResponse response = new AssetResponse();
		try {
			logger.log(Level.INFO, "CustomerServiceImpl: getCustomerAddress() invoked. ");
			
			if (Validator.isEmpty(customerName)) {
				return generateFailureResponse(generateFailureDetails("1", AssetConstants.ERROR_INVALID_INPUT));
			}
			logger.log(Level.INFO, "CustomerServiceImpl: getCustomerAddress() invoked for the customerName: [{0}]", customerName);

			List<String> customerAddressList = DAOFactory.getInstance().getCustomerDAO().selectCustomerAddress(customerName);

			String message = customerAddressList.size() + " row(s) retrieved successfully.";
			
			ResponseData data = new ResponseData();
			data.setCustomerAddress(customerAddressList);
			response.setData(data);
			response.setDetails(generateSuccessDetails(message));
			
			logger.log(Level.INFO, "CustomerServiceImpl: getCustomerAddress(): {0}", customerAddressList.size());
		} catch (Exception e) {
			logger.log(Level.INFO, e.getMessage(), e);
			return generateFailureResponse(generateFailureDetails("128", e.getMessage()));
		}
		logger.log(Level.INFO, "CustomerServiceImpl: getCustomerAddress: ended");
		return response;
	}

	public AssetResponse getDistinctAddress() {
		AssetResponse response = new AssetResponse();
		try {
			logger.log(Level.INFO, "CustomerServiceImpl: getDistinctAddress() invoked. ");
			
			List<String> customerAddressList = DAOFactory.getInstance().getCustomerDAO().selectDistinctAddress();

			String message = customerAddressList.size() + " row(s) retrieved successfully.";
			
			ResponseData data = new ResponseData();
			data.setCustomerAddress(customerAddressList);
			response.setData(data);
			response.setDetails(generateSuccessDetails(message));
			
			logger.log(Level.INFO, "CustomerServiceImpl: getDistinctAddress(): {0}", customerAddressList.size());
		} catch (Exception e) {
			logger.log(Level.INFO, e.getMessage(), e);
			return generateFailureResponse(generateFailureDetails("128", e.getMessage()));
		}
		logger.log(Level.INFO, "CustomerServiceImpl: getDistinctAddress: ended");
		return response;
	}

	public AssetResponse getCustomerNames() {
		AssetResponse response = new AssetResponse();
		try {
			logger.log(Level.INFO, "CustomerServiceImpl: getCustomerNames() invoked. ");
			
			List<String> customerNameList = DAOFactory.getInstance().getCustomerDAO().selectCustomerNames();

			String message = customerNameList.size() + " row(s) retrieved successfully.";
			
			ResponseData data = new ResponseData();
			data.setCustomerNameList(customerNameList);
			response.setData(data);
			response.setDetails(generateSuccessDetails(message));
			
			logger.log(Level.INFO, "CustomerServiceImpl: getCustomerNames(): {0}", customerNameList.size());
		} catch (Exception e) {
			logger.log(Level.INFO, e.getMessage(), e);
			return generateFailureResponse(generateFailureDetails("128", e.getMessage()));
		}
		logger.log(Level.INFO, "CustomerServiceImpl: getCustomerNames: ended");
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
