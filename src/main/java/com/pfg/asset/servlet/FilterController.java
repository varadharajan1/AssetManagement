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
import com.pfg.asset.dto.FilterParam;
import com.pfg.asset.dto.Pagination;
import com.pfg.asset.util.AssetConstants;
import com.pfg.asset.util.Validator;

public class FilterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(FilterController.class.getName());
    
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
			String action = request.getParameter("action");
			String currentPage = request.getParameter("currentPage");
			String recordsPerPage = request.getParameter("recordsPerPage");
			String filterType = request.getParameter("filterType");
			String filterValue = request.getParameter("filterValue");
			String renewal = request.getParameter("renewal");
			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");
			
			logger.log(Level.INFO, "filterType: {0}", filterType );
			logger.log(Level.INFO, "filterValue: {0}", filterValue );
			logger.log(Level.INFO, "fromDate: {0}", fromDate );
			logger.log(Level.INFO, "toDate: {0}", toDate );
			logger.log(Level.INFO, "renewal: {0}", renewal );

			AssetConfig assetConfig  = DAOFactory.getInstance().getAssetConfigDAO().selectAssetConfig();
			records =  assetConfig.getRecordsPerPage();
			
			if(Validator.isNotEmpty(currentPage) && Validator.isAllNumbers(currentPage)) {
				page = Integer.parseInt(currentPage);
			}
			if(Validator.isNotEmpty(recordsPerPage) && Validator.isAllNumbers(recordsPerPage)) {
				records = Integer.parseInt(recordsPerPage);
			}
			if(Validator.isEmpty(filterType)) {
				filterType = AssetConstants.FILTER.RENEWAL.toString();
			}
			if(Validator.isEmpty(filterValue)) {
				if(Validator.isEmpty(renewal)) {
					if(Validator.isEmpty(fromDate) && Validator.isEmpty(toDate)) {
						filterValue = AssetConstants.INTERVALS.get(0);
					}
				} else {
					filterValue = renewal;
				}
			}
			Gson gson = null;
			FilterParam filter = new FilterParam();
			filter.setFilterType(filterType);
			filter.setFilterValue(filterValue);
			filter.setCurrentPage(page);
			filter.setRecordsPerPage(records);
			filter.setStartDate(fromDate);
			filter.setEndDate(toDate);
			
			List<AssetInfo> filteredList = DAOFactory.getInstance().getAssetInfoDAO().filteredAssetInfo(filter,true);
			gson = new Gson();
	        Type type = new TypeToken<List<AssetInfo>>() {}.getType();
	        String json = gson.toJson(filteredList, type);

			request.setAttribute("dataSet", json);
			logger.log(Level.INFO, "filteredList.size: {0}", filteredList.size() );

			int noOfRows = DAOFactory.getInstance().getAssetInfoDAO().getNumberOfRows(filter);

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

			request.setAttribute("filterParam", filter);
			request.setAttribute("pagination", pagination);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			message = e.getMessage();
	    	request.setAttribute(AssetConstants.MESSAGE_KEY, message);
		}
		logger.log(Level.INFO, "redirect with {0}.", message );
		dispatcher = request.getRequestDispatcher("filter.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
