package com.pfg.asset.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
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
import com.pfg.asset.util.AssetConstants;
import com.pfg.asset.util.Validator;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, maxFileSize = 1024 * 1024 * 30, maxRequestSize = 1024 * 1024 * 50)
public class ImportData extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String UPLOAD_DIR = "uploadedFiles";
	
	private static final Logger logger = Logger.getLogger(ImportData.class.getName());
    
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException  {
		logger.log(Level.INFO, "ImportData: doGet() entered " );
		response.setContentType("text/html");

		InputStream filecontent = null;
		RequestDispatcher dispatcher = null;
		String message = "";
		String fileName = "";
		String tableName = request.getParameter("tableName");
		logger.log(Level.INFO, "tableName: {0}", tableName);
		
		try {
			Part filePart = request.getPart("importFileName");

			if(filePart != null && Validator.isNotEmpty(tableName)) {
				fileName = getFileName(filePart);
				logger.log(Level.INFO, "File {0} has been uploaded", fileName);
	
				filecontent = filePart.getInputStream();
			    Reader reader = new InputStreamReader(filecontent);
	
				long rows = DAOFactory.getInstance().getAssetDataDAO().importData(tableName, reader);
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
			List<String> tableNameList = DAOFactory.getInstance().getAssetDataDAO().getTableNames();
	    	request.setAttribute("tableNameList", tableNameList);
		}
		logger.log(Level.INFO, "ImportData: doGet() redirect with {0}.", message );
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
