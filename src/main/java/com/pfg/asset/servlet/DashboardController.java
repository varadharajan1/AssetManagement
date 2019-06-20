package com.pfg.asset.servlet;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pfg.asset.dao.DAOFactory;
import com.pfg.asset.util.AssetConstants;

public class DashboardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(DashboardController.class.getName());
    
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException  {
		logger.log(Level.INFO, "DashboardController: doGet() entered " );
		response.setContentType("text/html");

		RequestDispatcher dispatcher = null;
		String message = "";

		try {
			int totalNoOfAssets = DAOFactory.getInstance().getAssetDashboardDAO().getNumberOfRows("ASSET");
			int totalNoOfTracks = DAOFactory.getInstance().getAssetDashboardDAO().getNumberOfRows("TRACK");
			int totalNoOfOpcos = DAOFactory.getInstance().getAssetDashboardDAO().getNumberOfRows("OPCO");
			int totalNoOfBusiness = DAOFactory.getInstance().getAssetDashboardDAO().getNumberOfRows("BUSINESS");

			Map<String,Integer> renewalCounts = DAOFactory.getInstance().getAssetInfoDAO().getRenewalPeriodCount(AssetConstants.INTERVALS);
			
			int expiredAssets = DAOFactory.getInstance().getAssetDashboardDAO().getNumberOfRows("EXPIRED");
			renewalCounts.put("EXPIRED", expiredAssets);

			Map<String,Integer> oemNameCounts = DAOFactory.getInstance().getAssetDashboardDAO().getOEMNameCount();
			
			request.setAttribute("totalNoOfAssets", totalNoOfAssets);
			request.setAttribute("totalNoOfTracks", totalNoOfTracks);
			request.setAttribute("totalNoOfOpcos", totalNoOfOpcos);
			request.setAttribute("totalNoOfBusiness", totalNoOfBusiness);
			request.setAttribute("renewalCounts", renewalCounts);
			request.setAttribute("oemNameCounts", oemNameCounts);
			
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			message = e.getMessage();
	    	request.setAttribute(AssetConstants.MESSAGE_KEY, message);
		}
		logger.log(Level.INFO, "DashboardController: doGet() redirect with {0}.", message );
		dispatcher = request.getRequestDispatcher("dashboard.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
