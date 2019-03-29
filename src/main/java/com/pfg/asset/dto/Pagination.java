package com.pfg.asset.dto;

import java.io.Serializable;

public class Pagination implements Serializable{
	private static final long serialVersionUID = 1L;

	private int noOfPages;
	private int currentPage;
	private int recordsPerPage;
	private int totalRecords;
	private int visiblePages;

	public Pagination() {}

	public Pagination(int noOfPages, int currentPage, int recordsPerPage, int totalRecords, int visiblePages) {
		this.totalRecords = totalRecords;
		this.recordsPerPage = recordsPerPage;
		this.currentPage = currentPage;
		this.noOfPages = noOfPages;
		this.visiblePages = visiblePages;
	}

	public int getNoOfPages() {
		return noOfPages;
	}
	public void setNoOfPages(int noOfPages) {
		this.noOfPages = noOfPages;
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

	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getVisiblePages() {
		return visiblePages;
	}
	public void setVisiblePages(int visiblePages) {
		this.visiblePages = visiblePages;
	}

	@Override
	public String toString() {
		return "Pagination [noOfPages=" + noOfPages + ", currentPage=" + currentPage + ", recordsPerPage="
				+ recordsPerPage + ", totalRecords=" + totalRecords + ", visiblePages=" + visiblePages + "]";
	}
}
