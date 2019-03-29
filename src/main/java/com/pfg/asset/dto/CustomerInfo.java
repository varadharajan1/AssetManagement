package com.pfg.asset.dto;

import java.io.Serializable;

public class CustomerInfo implements Serializable{
	private static final long serialVersionUID = 1L;

	private String customerName;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private int zipCode;
	private String country;
	
	public CustomerInfo() {}
	
	public CustomerInfo(String customerName, String address1, String address2, String city, String state, int zip, String country) {
		this.customerName = customerName;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.state = state;
		this.zipCode = zip;
		this.country = country;
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

	@Override
	public String toString() {
		return "CustomerInfo [customerName=" + customerName + ", address1=" + address1 + ", address2=" + address2
				+ ", city=" + city + ", state=" + state + ", zipCode=" + zipCode + ", country=" + country + "]";
	}
}
