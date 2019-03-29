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
import com.pfg.asset.dto.CustomerInfo;
import com.pfg.asset.util.AssetConstants;
import com.pfg.asset.util.Validator;
import com.pfg.asset.util.ValueConvertor;

public class DeploymentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(DeploymentController.class.getName());
    
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException  {
		logger.log(Level.INFO, "DeploymentController: doGet() entered " );
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
				String customerName = request.getParameter("customerName");
				String address1 = request.getParameter("address1");
				String address2 = request.getParameter("address2");
				String city = request.getParameter("city");
				String zipCode = request.getParameter("zipCode");
				String state = request.getParameter("state");
				String country = request.getParameter("country");
				
				int zip = (Validator.isNotEmpty(zipCode) && Validator.isAllNumbers(zipCode)) ? Integer.parseInt(zipCode): 0;
				CustomerInfo customer = new CustomerInfo(customerName, address1, address2, city, state, zip, country);
				
				int rows = DAOFactory.getInstance().getCustomerDAO().insertCustomer(customer);
				message = rows + " row(s) inserted.";
		    	request.setAttribute(AssetConstants.MESSAGE_KEY, message);
			} else if("update".equalsIgnoreCase(action) || "delete".equalsIgnoreCase(action)) {
				String customerSelected = request.getParameter("rowSelected");
				logger.log(Level.INFO, "DeploymentController: doGet() customerSelected: {0}", customerSelected );
				
				List<CustomerInfo> customerInfoList = new ArrayList<>();
				if(Validator.isNotEmpty(customerSelected)) {
					String[] customers = ValueConvertor.stringTokanizer(customerSelected, ",");
					for(String item : customers) {
						logger.log(Level.INFO, "DeploymentController: doGet() item: {0}", item );
						logger.log(Level.INFO, "DeploymentController: doGet() decode: {0}", URLDecoder.decode(item, "UTF-8") );
						if(Validator.isNotEmpty(item)) {
							String[] items = ValueConvertor.stringTokanizer(URLDecoder.decode(item,"UTF-8"), "~");
							logger.log(Level.INFO, "DeploymentController: doGet() Customer[{0},{1}] ", items);
							
							CustomerInfo customer = new CustomerInfo();
							for(int i=0; i < items.length; i++){
								if(i==0) {
									customer.setCustomerName(items[i]);
								}else if (i==1) {
									customer.setAddress1(items[i]);
								}else if (i==2) {
									customer.setAddress2(items[i]);
								}else if (i==3) {
									customer.setCity(items[i]);
								}else if (i==4) {
									customer.setState(items[i]);
								}else if (i==5) {
									int zip = (Validator.isNotEmpty(items[i]) && Validator.isAllNumbers(items[i])) ? Integer.parseInt(items[i]): 0;
									customer.setZipCode(zip);
								}else if (i==6) {
									customer.setCountry(items[i]);
								}
							}
							customerInfoList.add(customer);
						}
					}
					if("update".equalsIgnoreCase(action)) {
						int rows = DAOFactory.getInstance().getCustomerDAO().batchUpdate(customerInfoList);
						message = rows + " row(s) modified.";
				    	request.setAttribute(AssetConstants.MESSAGE_KEY, message);
					}
				}
			}

			List<CustomerInfo> customerList = DAOFactory.getInstance().getCustomerDAO().selectCustomerInfo(page, records);
			request.setAttribute("customerList", customerList);
			logger.log(Level.INFO, "DeploymentController: doGet() customerList.size: {0}", customerList.size() );

			int noOfRows = DAOFactory.getInstance().getCustomerDAO().getNumberOfRows();
			
			int noOfPages = noOfRows / records;
			if ((noOfRows % records) > 0) {
			    noOfPages++;
			}			
			request.setAttribute("noOfPages", noOfPages);
			request.setAttribute("currentPage", page);
			request.setAttribute("recordsPerPage", records);
			request.setAttribute("totalRecords", noOfRows);
			logger.log(Level.INFO, "DeploymentController: doGet() noOfPages: {0}", noOfPages );
			logger.log(Level.INFO, "DeploymentController: doGet() currentPage: {0}", page );
			logger.log(Level.INFO, "DeploymentController: doGet() noOfRows: {0}", noOfRows );
			
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			message = e.getMessage();
	    	request.setAttribute(AssetConstants.MESSAGE_KEY, message);
		}
		logger.log(Level.INFO, "DeploymentController: doGet() redirect with {0}.", message );
		dispatcher = request.getRequestDispatcher("deployment.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
