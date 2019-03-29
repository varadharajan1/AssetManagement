package com.pfg.asset.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.pfg.asset.dto.AssetResponse;
import com.pfg.asset.dto.CustomerInfo;

@Path("/customer")
public class CustomerService {

	@POST
	@Path("/info")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public AssetResponse getCustomerInfo(CustomerInfo customerInfo) {
		CustomerServiceImpl serviceImpl = new CustomerServiceImpl();
		return serviceImpl.getCustomerInfo(customerInfo);
	}

	@POST
	@Path("/address")
	@Produces(MediaType.APPLICATION_JSON)
	public AssetResponse getCustomerAddress(String customerName) {
		CustomerServiceImpl serviceImpl = new CustomerServiceImpl();
		return serviceImpl.getCustomerAddress(customerName);
	}

	@POST
	@Path("/distinctAddress")
	@Produces(MediaType.APPLICATION_JSON)
	public AssetResponse getDistinctAddress() {
		CustomerServiceImpl serviceImpl = new CustomerServiceImpl();
		return serviceImpl.getDistinctAddress();
	}

	@GET
	@Path("/names")
	@Produces(MediaType.APPLICATION_JSON)
	public AssetResponse getCustomerNames() {
		CustomerServiceImpl serviceImpl = new CustomerServiceImpl();
		return serviceImpl.getCustomerNames();
	}
}
