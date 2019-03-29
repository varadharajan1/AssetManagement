package com.pfg.asset.servlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pfg.asset.dao.DAOFactory;
import com.pfg.asset.dto.OEMInfo;
import com.pfg.asset.util.AssetConstants;
import com.pfg.asset.util.Validator;
import com.pfg.asset.util.ValueConvertor;

public class OEMController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(OEMController.class.getName());
    
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException  {
		logger.log(Level.INFO, "OEMController: doGet() entered " );
		response.setContentType("text/html");

		RequestDispatcher dispatcher = null;
		String message = "";
		int page = 1;
		int records = AssetConstants.DEFAULT_COUNT;

		try {
			String currentPage = request.getParameter("currentPage");
			String recordsPerPage = request.getParameter("recordsPerPage");
			
			if(Validator.isNotEmpty(currentPage) && Validator.isAllNumbers(currentPage)) {
				page = Integer.parseInt(currentPage);
			}
			if(Validator.isNotEmpty(recordsPerPage) && Validator.isAllNumbers(recordsPerPage)) {
				records = Integer.parseInt(recordsPerPage);
			}
		
			String action = request.getParameter("action");

			if("insert".equalsIgnoreCase(action)) {
				String oemName = request.getParameter("oemName");
				String oemDescription = request.getParameter("oemDescription");
				
				OEMInfo oem = new OEMInfo(oemName, oemDescription);
				int rows = DAOFactory.getInstance().getOEMDAO().insertOEM(oem);
				message = rows + " row(s) inserted.";
		    	request.setAttribute(AssetConstants.MESSAGE_KEY, message);
			} else if("update".equalsIgnoreCase(action) || "delete".equalsIgnoreCase(action)) {
				String oemSelected = request.getParameter("rowSelected");
				logger.log(Level.INFO, "OEMController: doGet() oemSelected: {0}", oemSelected );
				
				List<OEMInfo> oemInfoList = new ArrayList<>();
				if(Validator.isNotEmpty(oemSelected)) {
					String oems[] = ValueConvertor.stringTokanizer(oemSelected, ",");
					for(String item : oems) {
						logger.log(Level.INFO, "OEMController: doGet() item: {0}", item );
						logger.log(Level.INFO, "OEMController: doGet() decode: {0}", URLDecoder.decode(item, "UTF-8") );
						if(Validator.isNotEmpty(item)) {
							String items[] = ValueConvertor.stringTokanizer(URLDecoder.decode(item,"UTF-8"), "~");
							logger.log(Level.INFO, "OEMController: doGet() OEM[{0},{1}] ", items);
							
							OEMInfo oem = new OEMInfo();
							for(int i=0; i < items.length; i++){
								if(i==0) {
									oem.setOemName(items[i]);
								}else if (i==1) {
									oem.setOemDescription(items[i]);
								}
							}
							oemInfoList.add(oem);
						}
					}
					if("update".equalsIgnoreCase(action)) {
						int rows = DAOFactory.getInstance().getOEMDAO().batchUpdate(oemInfoList);
						message = rows + " row(s) modified.";
				    	request.setAttribute(AssetConstants.MESSAGE_KEY, message);
					}
				}
			}

			List<OEMInfo> oemList = DAOFactory.getInstance().getOEMDAO().selectOEMInfo(page, records);
			request.setAttribute("oemList", oemList);
			logger.log(Level.INFO, "OEMController: doGet() oemList.size: {0}", oemList.size() );

			int noOfRows = DAOFactory.getInstance().getOEMDAO().getNumberOfRows();
			
			int noOfPages = noOfRows / records;
			if ((noOfRows % records) > 0) {
			    noOfPages++;
			}			
			request.setAttribute("noOfPages", noOfPages);
			request.setAttribute("currentPage", page);
			request.setAttribute("recordsPerPage", records);
			request.setAttribute("totalRecords", noOfRows);
			logger.log(Level.INFO, "OEMController: doGet() noOfPages: {0}", noOfPages );
			logger.log(Level.INFO, "OEMController: doGet() currentPage: {0}", page );
			logger.log(Level.INFO, "OEMController: doGet() noOfRows: {0}", noOfRows );
			
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			message = e.getMessage();
	    	request.setAttribute(AssetConstants.MESSAGE_KEY, message);
		}
		logger.log(Level.INFO, "OEMController: doGet() redirect with {0}.", message );
		dispatcher = request.getRequestDispatcher("oem.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
