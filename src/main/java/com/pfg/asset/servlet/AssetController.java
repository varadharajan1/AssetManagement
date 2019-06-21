package com.pfg.asset.servlet;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pfg.asset.dao.DAOFactory;
import com.pfg.asset.dto.AssetInfo;
import com.pfg.asset.util.AssetConstants;
import com.pfg.asset.util.Validator;

public class AssetController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(AssetController.class.getName());
    
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException  {
		logger.log(Level.INFO, "AssetController: doGet() entered " );
		response.setContentType("text/html");

		RequestDispatcher dispatcher = null;
		Gson gson = null;
		String message = "";
		String filename = "asset-entry.jsp";
		List<AssetInfo> assetSelectedList = null;

		try {
        	String action = request.getParameter("action");
			String filterType = request.getParameter("filterType");
			String filterValue = request.getParameter("filterValue");
			String renewal = request.getParameter("renewal");
			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");
        	String assetSelected = request.getParameter(AssetConstants.ROW_SELECTED);

        	if(Validator.isNotEmpty(assetSelected)) {
				gson = new Gson();
		        Type type = new TypeToken<List<AssetInfo>>() {}.getType();
		        assetSelectedList = gson.fromJson(("["+assetSelected+"]"), type);
		        if((assetSelectedList != null ) && assetSelectedList.size() > 1) {
			    	request.setAttribute(AssetConstants.MESSAGE_KEY, "Multiple Rows Selected");
					logger.log(Level.INFO, "Multiple Rows Selected");
		        }
				logger.log(Level.INFO, "assetSelected: {0}", assetSelected );
		    	request.setAttribute(AssetConstants.ROW_SELECTED, assetSelected);
		    	request.setAttribute("filterType", filterType);
		    	request.setAttribute("filterValue", filterValue);
		    	request.setAttribute("renewal", renewal);
		    	request.setAttribute("fromDate", fromDate);
		    	request.setAttribute("toDate", toDate);
			}

			if("insert".equalsIgnoreCase(action) || "update".equalsIgnoreCase(action)) {
				String trackName = request.getParameter("trackName");
				String businessSegment = request.getParameter("businessSegment");
				String opcoName = request.getParameter("opcoName");
				String deviceName = request.getParameter("deviceName");
				String oemName = request.getParameter("oemName");
				String productNumber = request.getParameter("productNumber");
				String productDescription = request.getParameter("productDescription");
				String sku = request.getParameter("sku");
				String deployedLocation = request.getParameter("deployedLocation");
				String deployedAddress1 = request.getParameter("deployedAddress1");
				String deployedAddress2 = request.getParameter("deployedAddress2");
				String deployedCity = request.getParameter("deployedCity");
				String deployedState = request.getParameter("deployedState");
				String deployedZipCode = request.getParameter("deployedZipCode");
				String deployedCountry = request.getParameter("deployedCountry");
				String contractNumber = request.getParameter("contractNumber");
				String contractedThrough = request.getParameter("contractedThrough");
				String quantity = request.getParameter("quantity");
				String serialNumber = request.getParameter("serialNumber");
				String serviceLevelDescription = request.getParameter("serviceLevelDescription");
				String serviceLevel = request.getParameter("serviceLevel");
				String startDate = request.getParameter("supportStartDate");
				String endDate = request.getParameter("supportEndDate");
				String eolDate = request.getParameter("eolDate");
				String purchasedDate = request.getParameter("purchasedDate");
				String purchasedVendor = request.getParameter("purchasedVendor");
				String installedDate = request.getParameter("installedDate");
				String purchasedCost = request.getParameter("purchasedCost");
				String updatedBy = request.getParameter("updatedBy");

				AssetInfo asset = new AssetInfo();
				asset.setTrackName(trackName);
				asset.setBusinessSegment(businessSegment);
				asset.setOpcoName(opcoName);
				asset.setDeviceName(deviceName);
				asset.setOemName(oemName);
				asset.setProductNumber(productNumber);
				asset.setProductDescription(productDescription);
				asset.setSku(sku);
				asset.setDeployedLocation(deployedLocation);
				asset.setAddress1(deployedAddress1);
				asset.setAddress2(deployedAddress2);
				asset.setCity(deployedCity);
				asset.setState(deployedState);
				asset.setZipCode(deployedZipCode);
				asset.setCountry(deployedCountry);
				asset.setContractNumber(contractNumber);
				asset.setContractedThrough(contractedThrough);
				asset.setQuantity(Validator.isAllNumbers(quantity) ? Integer.parseInt(quantity) : 0);
				asset.setSerialNumber(serialNumber);
				asset.setServiceLevel(serviceLevel);
				asset.setServiceLevelDescription(serviceLevelDescription);
				asset.setSupportStartDate(startDate);
				asset.setSupportEndDate(endDate);
				asset.setEolDate(eolDate);
				asset.setPurchasedDate(purchasedDate);
				asset.setPurchasedVendor(purchasedVendor);
				asset.setInstalledDate(installedDate);
				asset.setPurchasedCost(purchasedCost);
				//asset.setUpdatedBy(updatedBy);
				
				logger.log(Level.INFO, "asset: {0}", asset );

				if("update".equalsIgnoreCase(action)) {
			        if(assetSelectedList != null ) {
			        	logger.log(Level.INFO, "assetSelectedList.size: {0}", assetSelectedList.size() );
			        }
					int rows = DAOFactory.getInstance().getAssetInfoDAO().updateAssetDetails(assetSelectedList, asset);
					message = rows + " row(s) updated.";
			    	request.setAttribute(AssetConstants.MESSAGE_KEY, message);
					if(Validator.isNotEmpty(filterType)) {
						filename = "/filter?filterType="+ filterType +"&filterValue="+ filterValue +"&renewal="+ renewal +"&fromDate="+ fromDate +"&toDate="+ toDate;
					}else {
						filename = "/detail";
					}
				} else {
					logger.log(Level.INFO, "AssetController: doGet() assetDetail: {0}", asset);
					if(Validator.validate(asset)) {
						int rows = DAOFactory.getInstance().getAssetInfoDAO().insertAssetInfo(asset);
						message = rows + " row(s) inserted.";
					} else {
						message = AssetConstants.ERROR_REQUIRED_INPUT;
					}
			    	request.setAttribute(AssetConstants.MESSAGE_KEY, message);
					filename = "asset-entry.jsp";
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			message = e.getMessage();
	    	request.setAttribute(AssetConstants.MESSAGE_KEY, message);
		}
		logger.log(Level.INFO, "AssetController: doGet() redirect with {0}.", filename );
		dispatcher = request.getRequestDispatcher(filename);
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
