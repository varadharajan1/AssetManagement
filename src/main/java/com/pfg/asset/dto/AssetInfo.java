package com.pfg.asset.dto;

import java.io.Serializable;

public class AssetInfo implements Serializable{
	private static final long serialVersionUID = 1L;

	private String trackName;
	private String businessSegment;
	private String oemName;
	private String opcoName;
	private String deviceName;
	private String contractedThrough;
	
	private String productNumber;
	private String productDescription;
	private int quantity;
	private String contractNumber;
	private String serviceLevel;
	private String serialNumber;
	private String serviceLevelDescription;
	private String sku;
	private String supportStartDate;
	private String supportEndDate;

	private String eolDate;
	private String purchasedDate;
	private String purchasedVendor;
	private String installedDate;
	private String purchasedCost;
	
	private String deployedLocation;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String zipCode;
	private String country;
	
	private String created;
	private String lastUpdated;
	private String updatedBy;

	public String getTrackName() {
		return trackName;
	}
	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}
	public String getBusinessSegment() {
		return businessSegment;
	}
	public void setBusinessSegment(String businessSegment) {
		this.businessSegment = businessSegment;
	}
	public String getOemName() {
		return oemName;
	}
	public void setOemName(String oemName) {
		this.oemName = oemName;
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
	public String getContractedThrough() {
		return contractedThrough;
	}
	public void setContractedThrough(String contractedThrough) {
		this.contractedThrough = contractedThrough;
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
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getContractNumber() {
		return contractNumber;
	}
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	public String getServiceLevel() {
		return serviceLevel;
	}
	public void setServiceLevel(String serviceLevel) {
		this.serviceLevel = serviceLevel;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getServiceLevelDescription() {
		return serviceLevelDescription;
	}
	public void setServiceLevelDescription(String serviceLevelDescription) {
		this.serviceLevelDescription = serviceLevelDescription;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getSupportStartDate() {
		return supportStartDate;
	}
	public void setSupportStartDate(String supportStartDate) {
		this.supportStartDate = supportStartDate;
	}
	public String getSupportEndDate() {
		return supportEndDate;
	}
	public void setSupportEndDate(String supportEndDate) {
		this.supportEndDate = supportEndDate;
	}
	public String getEolDate() {
		return eolDate;
	}
	public void setEolDate(String eolDate) {
		this.eolDate = eolDate;
	}
	public String getPurchasedDate() {
		return purchasedDate;
	}
	public void setPurchasedDate(String purchasedDate) {
		this.purchasedDate = purchasedDate;
	}
	public String getPurchasedVendor() {
		return purchasedVendor;
	}
	public void setPurchasedVendor(String purchasedVendor) {
		this.purchasedVendor = purchasedVendor;
	}
	public String getInstalledDate() {
		return installedDate;
	}
	public void setInstalledDate(String installedDate) {
		this.installedDate = installedDate;
	}
	public String getPurchasedCost() {
		return purchasedCost;
	}
	public void setPurchasedCost(String purchasedCost) {
		this.purchasedCost = purchasedCost;
	}
	public String getDeployedLocation() {
		return deployedLocation;
	}
	public void setDeployedLocation(String deployedLocation) {
		this.deployedLocation = deployedLocation;
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
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
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
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Override
	public String toString() {
		return "AssetInfo [trackName=" + trackName + ", businessSegment=" + businessSegment + ", oemName=" + oemName
				+ ", opcoName=" + opcoName + ", deviceName=" + deviceName + ", contractedThrough=" + contractedThrough
				+ ", productNumber=" + productNumber + ", productDescription=" + productDescription + ", quantity="
				+ quantity + ", contractNumber=" + contractNumber + ", serviceLevel=" + serviceLevel + ", serialNumber="
				+ serialNumber + ", serviceLevelDescription=" + serviceLevelDescription + ", sku=" + sku
				+ ", supportStartDate=" + supportStartDate + ", supportEndDate=" + supportEndDate + ", eolDate="
				+ eolDate + ", purchasedDate=" + purchasedDate + ", purchasedVendor=" + purchasedVendor
				+ ", installedDate=" + installedDate + ", purchasedCost=" + purchasedCost + ", deployedLocation="
				+ deployedLocation + ", address1=" + address1 + ", address2=" + address2 + ", city=" + city + ", state="
				+ state + ", zipCode=" + zipCode + ", country=" + country + ", created=" + created + ", lastUpdated="
				+ lastUpdated + ", updatedBy=" + updatedBy + "]";
	}

}
