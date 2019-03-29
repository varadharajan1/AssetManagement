package com.pfg.asset.dto;

import java.io.Serializable;

public class ProductInfo implements Serializable{
	private static final long serialVersionUID = 1L;

	private String productNumber;
	private String productDescription;
	private String SKU;
	
	public ProductInfo() {}
	
	public ProductInfo(String productNumber, String desc, String sku) {
		this.productNumber = productNumber;
		this.productDescription = desc;
		this.SKU = sku;
	}
	
	public String getProductNumber() {
		return productNumber;
	}
	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public String getSKU() {
		return SKU;
	}
	public void setSKU(String sKU) {
		SKU = sKU;
	}

	@Override
	public String toString() {
		return "ProductInfo [productNumber=" + productNumber + ", productDescription=" + productDescription + ", SKU=" + SKU
				+ "]";
	}
}
