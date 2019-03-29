package com.pfg.asset.dto;

import java.io.Serializable;

public class FilterParam implements Serializable{
	private static final long serialVersionUID = 1L;

	private String action;
	private String rowSelected;
	private String filterType;
	private String filterValue;
	private String startDate;
	private String endDate;
	private int currentPage;
	private int recordsPerPage;
	
	public FilterParam() {}

	public FilterParam(String filterType, String filterValue) {
		this.filterType = filterType;
		this.filterValue = filterValue;
	}

	public FilterParam(String filterType, String filterValue, int currentPage, int recordsPerPage) {
		this.filterType = filterType;
		this.filterValue = filterValue;
		this.currentPage = currentPage;
		this.recordsPerPage = recordsPerPage;
	}

	public FilterParam(String filterType, String startDate, String endDate) {
		this.filterType = filterType;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getFilterType() {
		return filterType;
	}
	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}

	public String getFilterValue() {
		return filterValue;
	}
	public void setFilterValue(String filterValue) {
		this.filterValue = filterValue;
	}

	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getRecordsPerPage() {
		return recordsPerPage;
	}
	public void setRecordsPerPage(int recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
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

	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}

	public String getRowSelected() {
		return rowSelected;
	}
	public void setRowSelected(String rowSelected) {
		this.rowSelected = rowSelected;
	}

	@Override
	public String toString() {
		return "FilterParam [filterType=" + filterType + ", filterValue=" + filterValue + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", currentPage=" + currentPage + ", recordsPerPage=" + recordsPerPage + "]";
	}
}
