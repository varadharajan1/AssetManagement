package com.pfg.asset.dto;

import java.io.Serializable;
import java.util.List;

public class ResponseData implements Serializable{
	private static final long serialVersionUID = 1L;

	private List<String> productNameList;
	private List<String> productSKUList;
	private String productDescription;

	private List<String> filteredColumnList;
	private List<String> serialNumberList;

	private List<String> customerNameList;
	private List<String> customerAddress;

	private Pagination pagination;

	public List<String> getProductNameList() {
		return productNameList;
	}
	public void setProductNameList(List<String> productNameList) {
		this.productNameList = productNameList;
	}
	public List<String> getProductSKUList() {
		return productSKUList;
	}
	public void setProductSKUList(List<String> productSKUList) {
		this.productSKUList = productSKUList;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public List<String> getCustomerNameList() {
		return customerNameList;
	}
	public void setCustomerNameList(List<String> customerNameList) {
		this.customerNameList = customerNameList;
	}
	public List<String> getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(List<String> customerAddress) {
		this.customerAddress = customerAddress;
	}
	public List<String> getFilteredColumnList() {
		return filteredColumnList;
	}
	public void setFilteredColumnList(List<String> filteredColumnList) {
		this.filteredColumnList = filteredColumnList;
	}
	public List<String> getSerialNumberList() {
		return serialNumberList;
	}
	public void setSerialNumberList(List<String> serialNumberList) {
		this.serialNumberList = serialNumberList;
	}
	public Pagination getPagination() {
		return pagination;
	}
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
}
