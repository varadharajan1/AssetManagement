package com.pfg.asset.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.pfg.asset.dto.AssetResponse;
import com.pfg.asset.dto.ProductInfo;

@Path("/product")
public class ProductService {

	@POST
	@Path("/description")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public AssetResponse getProductDescription(ProductInfo productInfo) {
		ProductServiceImpl employeeServiceImpl = new ProductServiceImpl();
		return employeeServiceImpl.getProductDescription(productInfo);
	}

	@POST
	@Path("/skus")
	@Produces(MediaType.APPLICATION_JSON)
	public AssetResponse getProductSKUs(String productNumber) {
		ProductServiceImpl employeeServiceImpl = new ProductServiceImpl();
		return employeeServiceImpl.getProductSKUs(productNumber);
	}

	@GET
	@Path("/numbers")
	@Produces(MediaType.APPLICATION_JSON)
	public AssetResponse getProductNumbers() {
		ProductServiceImpl employeeServiceImpl = new ProductServiceImpl();
		return employeeServiceImpl.getProductNumbers();
	}
}
