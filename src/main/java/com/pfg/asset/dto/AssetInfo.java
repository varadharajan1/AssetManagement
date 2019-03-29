package com.pfg.asset.dto;

import java.io.Serializable;

public class AssetInfo implements Serializable{
	private static final long serialVersionUID = 1L;

	private String trackName;
	private String oemName;
	private String opcoName;
	private String deviceName;
	
	private String productNumber;
	private String productDescription;
	private int quantity;
	private String contractNumber;
	private String serviceLevel;
	private String serialNumber;
	private String serviceLevelDescription;
	private String sku;
	private String startDate;
	private String endDate;

	private String deployedLocation;
	private String deployedAddress1;
	private String deployedAddress2;
	private String deployedCity;
	private String deployedState;
	private String deployedZipCode;
	private String deployedCountry;
	
	private String created;
	private String lastUpdated;
	private String updatedBy;

	public String getTrackName() {
		return trackName;
	}
	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}

	public String getOpcoName() {
		return opcoName;
	}
	public void setOpcoName(String opcoName) {
		this.opcoName = opcoName;
	}
	
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
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

	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getContractNumber() {
		return contractNumber;
	}
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
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

	public String getDeployedLocation() {
		return deployedLocation;
	}
	public void setDeployedLocation(String deployedLocation) {
		this.deployedLocation = deployedLocation;
	}

	public String getDeployedAddress1() {
		return deployedAddress1;
	}
	public void setDeployedAddress1(String deployedAddress1) {
		this.deployedAddress1 = deployedAddress1;
	}

	public String getDeployedAddress2() {
		return deployedAddress2;
	}
	public void setDeployedAddress2(String deployedAddress2) {
		this.deployedAddress2 = deployedAddress2;
	}

	public String getDeployedCity() {
		return deployedCity;
	}
	public void setDeployedCity(String deployedCity) {
		this.deployedCity = deployedCity;
	}

	public String getDeployedState() {
		return deployedState;
	}
	public void setDeployedState(String deployedState) {
		this.deployedState = deployedState;
	}

	public String getDeployedZipCode() {
		return deployedZipCode;
	}
	public void setDeployedZipCode(String deployedZipCode) {
		this.deployedZipCode = deployedZipCode;
	}

	public String getDeployedCountry() {
		return deployedCountry;
	}
	public void setDeployedCountry(String deployedCountry) {
		this.deployedCountry = deployedCountry;
	}

	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}

	public String getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@Override
	public String toString() {
		return "AssetInfo [trackName=" + trackName + ", oemName=" + oemName + ", opcoName=" + opcoName + ", deviceName="
				+ deviceName + ", productNumber=" + productNumber + ", productDescription=" + productDescription
				+ ", quantity=" + quantity + ", contractNumber=" + contractNumber + ", serviceLevel=" + serviceLevel
				+ ", serialNumber=" + serialNumber + ", serviceLevelDescription=" + serviceLevelDescription + ", sku="
				+ sku + ", startDate=" + startDate + ", endDate=" + endDate + ", deployedLocation=" + deployedLocation
				+ ", deployedAddress1=" + deployedAddress1 + ", deployedAddress2=" + deployedAddress2
				+ ", deployedCity=" + deployedCity + ", deployedState=" + deployedState + ", deployedZipCode="
				+ deployedZipCode + ", deployedCountry=" + deployedCountry + ", created=" + created + ", lastUpdated="
				+ lastUpdated + ", updatedBy=" + updatedBy + "]";
	}
}
