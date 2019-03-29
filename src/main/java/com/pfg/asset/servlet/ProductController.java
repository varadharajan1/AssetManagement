package com.pfg.asset.servlet;

import java.io.IOException;
import java.lang.reflect.Type;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pfg.asset.dao.DAOFactory;
import com.pfg.asset.dto.ProductInfo;
import com.pfg.asset.util.AssetConstants;
import com.pfg.asset.util.Validator;
import com.pfg.asset.util.ValueConvertor;

public class ProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(ProductController.class.getName());
    
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException  {
		logger.log(Level.INFO, "ProductController: doGet() entered " );
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
				String productNumber = request.getParameter("productNumber");
				String productDescription = request.getParameter("productDescription");
				String sku = request.getParameter("sku");
				
				ProductInfo product = new ProductInfo(productNumber, productDescription, sku);
				int rows = DAOFactory.getInstance().getProductDAO().insertProduct(product);
				message = rows + " row(s) inserted.";
		    	request.setAttribute(AssetConstants.MESSAGE_KEY, message);
			} else if("update".equalsIgnoreCase(action) || "delete".equalsIgnoreCase(action)) {
				String productSelected = request.getParameter("rowSelected");
				logger.log(Level.INFO, "ProductController: doGet() productSelected: {0}", productSelected );
				
				List<ProductInfo> productInfoList = new ArrayList<>();
				
				if(Validator.isNotEmpty(productSelected)) {
					String products[] = ValueConvertor.stringTokanizer(productSelected, ",");
					for(String item : products) {
						logger.log(Level.INFO, "ProductController: doGet() item: {0}", item );
						logger.log(Level.INFO, "ProductController: doGet() decode: {0}", URLDecoder.decode(item, "UTF-8") );
						if(Validator.isNotEmpty(item)) {
							String items[] = ValueConvertor.stringTokanizer(URLDecoder.decode(item,"UTF-8"), "~");
							logger.log(Level.INFO, "ProductController: doGet() Product[{0},{1},{2}] ", items);
							
							ProductInfo product = new ProductInfo();
							for(int i=0; i < items.length; i++){
								if(i==0) {
									product.setProductNumber(items[i]);
								}else if (i==1) {
									product.setProductDescription(items[i]);
								}else if (i==2) {
									product.setSKU(items[i]);
								}
							}
							productInfoList.add(product);
						}
					}
					if("update".equalsIgnoreCase(action)) {
						int rows = DAOFactory.getInstance().getProductDAO().batchUpdate(productInfoList);
						message = rows + " row(s) modified.";
				    	request.setAttribute(AssetConstants.MESSAGE_KEY, message);
					}
				}
			}
			List<ProductInfo> productList = DAOFactory.getInstance().getProductDAO().selectProductInfo(page, records);
			request.setAttribute("productList", productList);
			logger.log(Level.INFO, "ProductController: doGet() productList.size: {0}", productList.size() );

			int noOfRows = DAOFactory.getInstance().getProductDAO().getNumberOfRows();
			
			int noOfPages = noOfRows / records;
			if ((noOfRows % records) > 0) {
			    noOfPages++;
			}			
			request.setAttribute("noOfPages", noOfPages);
			request.setAttribute("currentPage", page);
			request.setAttribute("recordsPerPage", records);
			request.setAttribute("totalRecords", noOfRows);
			logger.log(Level.INFO, "ProductController: doGet() noOfPages: {0}", noOfPages );
			logger.log(Level.INFO, "ProductController: doGet() currentPage: {0}", page );
			logger.log(Level.INFO, "ProductController: doGet() noOfRows: {0}", noOfRows );
			
		} catch (Exception e) {
			logger.log(Level.INFO, e.getMessage(), e);
			message = e.getMessage();
	    	request.setAttribute(AssetConstants.MESSAGE_KEY, message);
		}
		logger.log(Level.INFO, "ProductController: doGet() redirect with {0}.", message );
		dispatcher = request.getRequestDispatcher("product.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException  {
		logger.log(Level.INFO, "ProductController: doGet() entered " );
		response.setContentType("text/html");

		RequestDispatcher dispatcher = null;
		String message = "";
		
		try {
			String page = request.getParameter("action");
			
			if("insert".equalsIgnoreCase(page)) {
				String productNumber = request.getParameter("productNumber");
				String productDescription = request.getParameter("productDescription");
				String sku = request.getParameter("sku");
				
				ProductInfo product = new ProductInfo(productNumber, productDescription, sku);
				
				int rows = DAOFactory.getInstance().getProductDAO().insertProduct(product);
				message = rows + " row(s) inserted.";
				
		    	request.setAttribute(AssetConstants.MESSAGE_KEY, message);
			}
	    	
			List<ProductInfo> productList = DAOFactory.getInstance().getProductDAO().selectAllProductInfo();
			Gson gson = new Gson();
	        Type type = new TypeToken<List<ProductInfo>>() {}.getType();
	        String json = gson.toJson(productList, type);

			request.setAttribute("dataSet", json);

			logger.log(Level.INFO, "ProductController: doGet() productList.size: {0}", productList.size() );
			
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			message = e.getMessage();
	    	request.setAttribute(AssetConstants.MESSAGE_KEY, message);
		}
		logger.log(Level.INFO, "ProductController: doGet() redirect with {0}.", message );
		dispatcher = request.getRequestDispatcher("product.jsp");
		dispatcher.forward(request, response);
	}

}
