package com.pfg.asset.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pfg.asset.dao.DAOFactory;
import com.pfg.asset.dto.AssetInfo;
import com.pfg.asset.dto.FilterParam;
import com.pfg.asset.util.CSVUtils;
import com.pfg.asset.util.Validator;

public class ExportData extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ExportData.class.getName());

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException  {
		logger.log(Level.INFO, "entered " );
		response.setContentType("application/csv");
		
		OutputStream outStream = null;
		List<AssetInfo> assetList = null;
		try {
			
			BufferedReader reader = request.getReader();
			Gson gson = new Gson();
			FilterParam filter = gson.fromJson(reader, FilterParam.class);
			
			logger.log(Level.INFO, "action: {0}", filter.getAction() );
			logger.log(Level.INFO, "RowSelected: {0}", filter.getRowSelected() );
			logger.log(Level.INFO, "filterType: {0}", filter.getFilterType() );
			logger.log(Level.INFO, "filterValue: {0}", filter.getFilterValue());
			logger.log(Level.INFO, "fromDate: {0}", filter.getStartDate() );
			logger.log(Level.INFO, "toDate: {0}", filter.getEndDate() );

			response.setHeader("Content-Disposition", "attachment;filename="+filter.getAction()+".csv");
			if("EXPORT_SELECTED".equalsIgnoreCase(filter.getAction())) {
				String assetSelected = filter.getRowSelected();
				logger.log(Level.INFO, "assetSelected: {0}", assetSelected );
				if(Validator.isNotEmpty(assetSelected)) {
					gson = new Gson();
			        Type type = new TypeToken<List<AssetInfo>>() {}.getType();
			        assetList = gson.fromJson(assetSelected, type);
				}
			}else{
				if(filter != null) {
					if(Validator.isEmpty(filter.getFilterType())) {
						assetList = DAOFactory.getInstance().getAssetInfoDAO().selectAllAssetInfo();
					}else{
						assetList = DAOFactory.getInstance().getAssetInfoDAO().filteredAssetInfo(filter, false);
					}
				}
			}
			String exportContent = CSVUtils.write(assetList);
			
			outStream = response.getOutputStream();
			outStream.write(exportContent.getBytes());

		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}finally {
			try {
				if(outStream != null) {
					outStream.flush();
					outStream.close();
				}
			} catch (Exception e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
