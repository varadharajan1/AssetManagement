package com.pfg.asset.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.pfg.asset.dao.DAOFactory;
import com.pfg.asset.dto.AssetConfig;
import com.pfg.asset.util.AssetConstants;
import com.pfg.asset.util.Validator;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, maxFileSize = 1024 * 1024 * 30, maxRequestSize = 1024 * 1024 * 50)
public class SettingsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(SettingsController.class.getName());
    
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException  {
		logger.log(Level.INFO, "SettingsController: doGet() entered " );
		response.setContentType("text/html");

		RequestDispatcher dispatcher = null;
		InputStream filecontent = null;
		String message = "";
		String fileName = "";

		try {
			String tableName = request.getParameter("tableName");
			logger.log(Level.INFO, "tableName: {0}", tableName);
			String action = request.getParameter("action");
			logger.log(Level.INFO, "action: {0}", action);
			if("update".equalsIgnoreCase(action)) {
				String recordsPerPage = request.getParameter("recordsPerPage");
				String filterType = request.getParameter("filterType");
				String filterValue = request.getParameter("filterValue");
				int records = AssetConstants.DEFAULT_COUNT;
				
				if(Validator.isNotEmpty(recordsPerPage) && Validator.isAllNumbers(recordsPerPage)) {
					records = Integer.parseInt(recordsPerPage);
				}
				if(Validator.isEmpty(filterType)) {
					filterType = AssetConstants.FILTER.RENEWAL.toString();
				}
				if(Validator.isEmpty(filterValue)) {
					filterValue = AssetConstants.INTERVALS.get(0);
				}
				logger.log(Level.INFO, "filterType: {0}", filterType);
				logger.log(Level.INFO, "filterValue: {0}", filterValue);
				
			}else if ("import".equalsIgnoreCase(action)) {
				try {
					Part filePart = request.getPart("importFileName");

					if(filePart != null && Validator.isNotEmpty(tableName)) {
						fileName = getFileName(filePart);
						logger.log(Level.INFO, "File {0} has been uploaded", fileName);
			
						filecontent = filePart.getInputStream();
					    Reader reader = new InputStreamReader(filecontent);
			
						long rows = DAOFactory.getInstance().getAssetConfigDAO().importData(tableName, reader);
						message = rows + " row(s) inserted.";

						request.setAttribute(AssetConstants.MESSAGE_KEY, message);
					} else {
						message = "File: "+ fileName +" OR table: "+ tableName+" is not available for process";
						request.setAttribute(AssetConstants.MESSAGE_KEY, message);
					}
				} catch (Exception fne) {
					message = "Problems during file upload. Error: "+ fne.getMessage();
					logger.log(Level.SEVERE, message);
			    	request.setAttribute(AssetConstants.MESSAGE_KEY, message);
				} finally {
					if (filecontent != null) {
						filecontent.close();
					}
				}
			}
			
			AssetConfig assetConfig  = DAOFactory.getInstance().getAssetConfigDAO().selectAssetConfig();
			List<String> tableNameList = DAOFactory.getInstance().getAssetConfigDAO().getTableNames();

			Map<String,String> cronExpressions = new HashMap<>();
			cronExpressions.put("0 * * ? * *","Every Minute");
			cronExpressions.put("0 0 0 * * ?","Every day at midnight");
			cronExpressions.put("0 0 8 * * ?","Every day at 8 AM");
			cronExpressions.put("0 0 12 * * ?","Every day at noon");
			cronExpressions.put("0 0 12 * * MON","Every Monday at noon");
			cronExpressions.put("0 0 12 * * TUE","Every Tuesday at noon");
			cronExpressions.put("0 0 12 1 * ?","Every month on the 1st, at noon");
			cronExpressions.put("0 0 12 2 * ?","Every month on the 2nd, at noon");
			cronExpressions.put("0 0 12 15 * ?","Every month on the 15th, at noon");
			cronExpressions.put("0 0 12 2L * ?","Every month on the last Monday, at noon");

			request.setAttribute("cronExpressions", cronExpressions);
			request.setAttribute("assetConfig", assetConfig);
			request.setAttribute("tableNameList", tableNameList);
			
		} catch (Exception fne) {
			message = "Error: "+ fne.getMessage();
			logger.log(Level.SEVERE, message);
	    	request.setAttribute(AssetConstants.MESSAGE_KEY, message);
		}
		logger.log(Level.INFO, "SettingsController: doGet() redirect with {0}.", message );
		dispatcher = request.getRequestDispatcher("settings.jsp");
		dispatcher.forward(request, response);
	}
	
	private String getFileName(Part part) {
		String fileName = "";
		String contentDisposition = part.getHeader("content-disposition");
		if(contentDisposition != null) {
			String[] items = contentDisposition.split(";");
			for (String item : items) {
				if (item.trim().startsWith("filename")) {
					fileName = item.substring(item.indexOf("=") + 2, item.length() - 1);
					//fileName = item.substring(item.indexOf('=') + 1).trim().replace("\"", "");
				}
			}
		}
		return fileName;
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
