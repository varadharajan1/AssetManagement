package com.pfg.asset.servlet;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		String message = "";
		try {
			String action = request.getParameter("action");
			if("insert".equalsIgnoreCase(action)) {
				String trackName = request.getParameter("trackName");
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
				String quantity = request.getParameter("quantity");
				String serialNumber = request.getParameter("serialNumber");
				String serviceLevelDescription = request.getParameter("serviceLevelDescription");
				String serviceLevel = request.getParameter("serviceLevel");
				String startDate = request.getParameter("startDate");
				String endDate = request.getParameter("endDate");
				String updatedBy = request.getParameter("updatedBy");

				AssetInfo asset = new AssetInfo();
				asset.setTrackName(trackName);
				asset.setOpcoName(opcoName);
				asset.setDeviceName(deviceName);
				asset.setOemName(oemName);
				asset.setProductNumber(productNumber);
				asset.setProductDescription(productDescription);
				asset.setSku(sku);
				asset.setDeployedLocation(deployedLocation);
				asset.setDeployedAddress1(deployedAddress1);
				asset.setDeployedAddress2(deployedAddress2);
				asset.setDeployedCity(deployedCity);
				asset.setDeployedState(deployedState);
				asset.setDeployedZipCode(deployedZipCode);
				asset.setDeployedCountry(deployedCountry);
				asset.setContractNumber(contractNumber);
				asset.setQuantity(Validator.isAllNumbers(quantity) ? Integer.parseInt(quantity) : 0);
				asset.setSerialNumber(serialNumber);
				asset.setServiceLevel(serviceLevel);
				asset.setServiceLevelDescription(serviceLevelDescription);
				asset.setStartDate(startDate);
				asset.setEndDate(endDate);
				//asset.setUpdatedBy(updatedBy);
				
				logger.log(Level.INFO, "AssetController: doGet() assetDetail: {0}", asset);
				if(Validator.validate(asset)) {
					int rows = DAOFactory.getInstance().getAssetInfoDAO().insertAssetInfo(asset);
					message = rows + " row(s) inserted.";
				} else {
					message = AssetConstants.ERROR_REQUIRED_INPUT;
				}
		    	request.setAttribute(AssetConstants.MESSAGE_KEY, message);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			message = e.getMessage();
	    	request.setAttribute(AssetConstants.MESSAGE_KEY, message);
		}
		logger.log(Level.INFO, "AssetController: doGet() redirect with {0}.", message );
		dispatcher = request.getRequestDispatcher("asset-entry.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
