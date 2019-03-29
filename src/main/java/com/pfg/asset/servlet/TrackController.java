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
import com.pfg.asset.dto.TrackInfo;
import com.pfg.asset.util.AssetConstants;
import com.pfg.asset.util.Validator;
import com.pfg.asset.util.ValueConvertor;

public class TrackController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(TrackController.class.getName());
    
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException  {
		logger.log(Level.INFO, "TrackController: doGet() entered " );
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
				String trackName = request.getParameter("trackName");
				String trackDescription = request.getParameter("trackDescription");
				
				TrackInfo track = new TrackInfo(trackName, trackDescription);
				
				int rows = DAOFactory.getInstance().getTrackDAO().insertTrack(track);
				message = rows + " row(s) inserted.";
				
		    	request.setAttribute(AssetConstants.MESSAGE_KEY, message);
			} else if("update".equalsIgnoreCase(action) || "delete".equalsIgnoreCase(action)) {
				String trackSelected = request.getParameter("rowSelected");
				logger.log(Level.INFO, "TrackController: doGet() trackSelected: {0}", trackSelected );
				
				List<TrackInfo> trackInfoList = new ArrayList<>();
				
				if(Validator.isNotEmpty(trackSelected)) {
					String tracks[] = ValueConvertor.stringTokanizer(trackSelected, ",");
					for(String item : tracks) {
						logger.log(Level.INFO, "TrackController: doGet() item: {0}", item );
						logger.log(Level.INFO, "TrackController: doGet() decode: {0}", URLDecoder.decode(item, "UTF-8") );
						if(Validator.isNotEmpty(item)) {
							String items[] = ValueConvertor.stringTokanizer(URLDecoder.decode(item,"UTF-8"), "~");
							logger.log(Level.INFO, "TrackController: doGet() Track[{0},{1}] ", items);
							
							TrackInfo track = new TrackInfo();
							for(int i=0; i < items.length; i++){
								if(i==0) {
									track.setTrackName(items[i]);
								}else if (i==1) {
									track.setTrackDescription(items[i]);
								}
							}
							trackInfoList.add(track);
						}
					}
					if("update".equalsIgnoreCase(action)) {
						int rows = DAOFactory.getInstance().getTrackDAO().batchUpdate(trackInfoList);
						message = rows + " row(s) modified.";
				    	request.setAttribute(AssetConstants.MESSAGE_KEY, message);
					}
				}
			}
			List<TrackInfo> trackList = DAOFactory.getInstance().getTrackDAO().selectTrackInfo(page, records);
			request.setAttribute("trackList", trackList);
			logger.log(Level.INFO, "TrackController: doGet() trackList.size: {0}", trackList.size() );

			int noOfRows = DAOFactory.getInstance().getTrackDAO().getNumberOfRows();
			
			int noOfPages = noOfRows / records;
			if ((noOfRows % records) > 0) {
			    noOfPages++;
			}			
			request.setAttribute("noOfPages", noOfPages);
			request.setAttribute("currentPage", page);
			request.setAttribute("recordsPerPage", records);
			request.setAttribute("totalRecords", noOfRows);
			logger.log(Level.INFO, "TrackController: doGet() noOfPages: {0}", noOfPages );
			logger.log(Level.INFO, "TrackController: doGet() currentPage: {0}", page );
			logger.log(Level.INFO, "TrackController: doGet() noOfRows: {0}", noOfRows );
			
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			message = e.getMessage();
	    	request.setAttribute(AssetConstants.MESSAGE_KEY, message);
		}
		logger.log(Level.INFO, "TrackController: doGet() redirect with {0}.", message );
		dispatcher = request.getRequestDispatcher("track.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
