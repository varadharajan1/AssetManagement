package com.pfg.asset.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.pfg.asset.dto.AssetResponse;
import com.pfg.asset.dto.FilterParam;

@Path("/filter")
public class AssetService {

	@POST
	@Path("/summary")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public AssetResponse filteredAsset(FilterParam filter) {
		AssetServiceImpl serviceImpl = new AssetServiceImpl();
		return serviceImpl.filteredAsset(filter);
	}

	@POST
	@Path("/type")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public AssetResponse distinctAssetColumn(FilterParam filter) {
		AssetServiceImpl serviceImpl = new AssetServiceImpl();
		return serviceImpl.distinctAssetColumn(filter);
	}

	@GET
	@Path("/serial")
	@Produces(MediaType.APPLICATION_JSON)
	public AssetResponse getSerialNumbers() {
		AssetServiceImpl serviceImpl = new AssetServiceImpl();
		return serviceImpl.getSerialNumbers();
	}

	@POST
	@Path("/pagination")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public AssetResponse getPaginationDetail(FilterParam filter) {
		AssetServiceImpl serviceImpl = new AssetServiceImpl();
		return serviceImpl.getPaginationDetail(filter);
	}
}
