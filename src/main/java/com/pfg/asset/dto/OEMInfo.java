package com.pfg.asset.dto;

import java.io.Serializable;

public class OEMInfo implements Serializable{
	private static final long serialVersionUID = 1L;

	private String oemName;
	private String oemDescription;
	
	public OEMInfo() {}
	
	public OEMInfo(String oemName, String desc) {
		this.oemName = oemName;
		this.oemDescription = desc;
	}

	public String getOemName() {
		return oemName;
	}

	public void setOemName(String oemName) {
		this.oemName = oemName;
	}

	public String getOemDescription() {
		return oemDescription;
	}

	public void setOemDescription(String oemDescription) {
		this.oemDescription = oemDescription;
	}

	@Override
	public String toString() {
		return "OEMInfo [oemName=" + oemName + ", oemDescription=" + oemDescription + "]";
	}

}
