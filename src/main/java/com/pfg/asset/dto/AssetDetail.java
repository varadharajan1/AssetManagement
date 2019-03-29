package com.pfg.asset.dto;

import java.io.Serializable;

import com.pfg.asset.util.Validator;

public class AssetDetail implements Serializable{
	private static final long serialVersionUID = 1L;

	private String trackName;
	private String oemName;
	
	private String productNumber;
	private String productDescription;
	private String SKU;

	private String customerName;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private int zipCode;
	private String country;
	
	private String contractNumber;
	private String quantity;
	private String serialNumber;
	private String serviceLevel;
	private String serviceLevelDescription;
	private String startDate;
	private String endDate;
	private String updatedBy;
	private String comments;

	public AssetDetail() {}

	public String getTrackName() {
		return trackName;
	}

	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}

	public String getOemName() {
		return oemName;
	}

	public void setOemName(String oemName) {
		this.oemName = oemName;
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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getServiceLevel() {
		return serviceLevel;
	}

	public void setServiceLevel(String serviceLevel) {
		this.serviceLevel = serviceLevel;
	}

	public String getServiceLevelDescription() {
		return serviceLevelDescription;
	}

	public void setServiceLevelDescription(String serviceLevelDescription) {
		this.serviceLevelDescription = serviceLevelDescription;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "AssetDetail [trackName=" + trackName + ", oemName=" + oemName + ", productNumber=" + productNumber
				+ ", productDescription=" + productDescription + ", SKU=" + SKU + ", customerName=" + customerName
				+ ", address1=" + address1 + ", address2=" + address2 + ", city=" + city + ", state=" + state
				+ ", zipCode=" + zipCode + ", country=" + country + ", contractNumber=" + contractNumber + ", quantity="
				+ quantity + ", serialNumber=" + serialNumber + ", serviceLevel=" + serviceLevel
				+ ", serviceLevelDescription=" + serviceLevelDescription + ", startDate=" + startDate + ", endDate="
				+ endDate + ", updatedBy=" + updatedBy + ", comments=" + comments + "]";
	}
	
	public String displayString(){
		StringBuilder result = new StringBuilder();
		
		if(Validator.isNotEmpty(trackName)) {
			result.append("trackName=" + trackName);
			result.append(", ");
		}
		if(Validator.isNotEmpty(oemName)) {
			result.append("oemName=" + oemName);
			result.append(", ");
		}
		if(Validator.isNotEmpty(productNumber)) {
			result.append("productNumber=" + productNumber);
			result.append(", ");
		}
		if(Validator.isNotEmpty(SKU)) {
			result.append("SKU=" + SKU);
			result.append(", ");
		}
		if(Validator.isNotEmpty(customerName)) {
			result.append("customerName=" + customerName);
			result.append(", ");
		}
		if(Validator.isNotEmpty(address1)) {
			result.append("address1=" + address1);
			result.append(", ");
		}
		if(Validator.isNotEmpty(contractNumber)) {
			result.append("contractNumber=" + contractNumber);
			result.append(", ");
		}
		if(Validator.isNotEmpty(quantity)) {
			result.append("quantity=" + quantity);
			result.append(", ");
		}
		if(Validator.isNotEmpty(serialNumber)) {
			result.append("serialNumber=" + serialNumber);
			result.append(", ");
		}
		if(Validator.isNotEmpty(startDate)) {
			result.append("startDate=" + startDate);
			result.append(", ");
		}
		if(Validator.isNotEmpty(endDate)) {
			result.append("endDate=" + endDate);
		} else {
			result.replace(result.lastIndexOf(", "), result.length(), "");
		}
		return "AssetDetail [" + result.toString() + "]";
	}
}
