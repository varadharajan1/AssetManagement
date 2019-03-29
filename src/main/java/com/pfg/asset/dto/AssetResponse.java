package com.pfg.asset.dto;

import java.io.Serializable;

public class AssetResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private ResponseData data = null;
	private ResponseDetails details = null;

	public ResponseData getData() {
		return data;
	}
	public void setData(ResponseData data) {
		this.data = data;
	}
	public ResponseDetails getDetails() {
		return details;
	}
	public void setDetails(ResponseDetails details) {
		this.details = details;
	}
	
}
