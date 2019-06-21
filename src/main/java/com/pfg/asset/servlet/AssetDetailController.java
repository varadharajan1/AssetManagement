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
import com.pfg.asset.dto.AssetConfig;
import com.pfg.asset.dto.AssetInfo;
import com.pfg.asset.dto.Pagination;
import com.pfg.asset.util.AssetConstants;
import com.pfg.asset.util.Validator;

public class AssetDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(AssetDetailController.class.getName());
    
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException  {
		logger.log(Level.INFO, "doGet() entered " );
		response.setContentType("text/html");

		RequestDispatcher dispatcher = null;
		String message = "";
		int page = 1;
		int records = AssetConstants.DEFAULT_COUNT;
		int visiblePages = AssetConstants.PAGINATION_COUNT;

		try {
			String currentPage = request.getParameter("currentPage");
			String recordsPerPage = request.getParameter("recordsPerPage");

			AssetConfig assetConfig  = DAOFactory.getInstance().getAssetConfigDAO().selectAssetConfig();
			records =  assetConfig.getRecordsPerPage();
			
			if(Validator.isNotEmpty(currentPage) && Validator.isAllNumbers(currentPage)) {
				page = Integer.parseInt(currentPage);
			}
			if(Validator.isNotEmpty(recordsPerPage) && Validator.isAllNumbers(recordsPerPage)) {
				records = Integer.parseInt(recordsPerPage);
			}

			Gson gson = null;
			List<AssetInfo> assetInfoList = DAOFactory.getInstance().getAssetInfoDAO().selectAssetInfo(page, records);
			gson = new Gson();
	        Type type = new TypeToken<List<AssetInfo>>() {}.getType();
	        String json = gson.toJson(assetInfoList, type);

			request.setAttribute("dataSet", json);
			logger.log(Level.INFO, "assetInfoList.size: {0}", assetInfoList.size() );

			int noOfRows = DAOFactory.getInstance().getAssetInfoDAO().getNumberOfRows(null);

			int noOfPages = noOfRows / records;
			if ((noOfRows % records) > 0) {
			    noOfPages++;
			}			
			Pagination pagination = new Pagination();
			pagination.setNoOfPages(noOfPages);
			pagination.setCurrentPage(page);
			pagination.setRecordsPerPage(records);
			pagination.setTotalRecords(noOfRows);
			pagination.setVisiblePages(visiblePages);

			request.setAttribute("pagination", pagination);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			message = e.getMessage();
	    	request.setAttribute(AssetConstants.MESSAGE_KEY, message);
		}
		logger.log(Level.INFO, "redirect with {0}.", message );
		dispatcher = request.getRequestDispatcher("asset-info.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
