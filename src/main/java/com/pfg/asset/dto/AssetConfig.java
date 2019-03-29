package com.pfg.asset.dto;

import java.io.Serializable;

public class AssetConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	private String smtpHost;
	private String smtpPort;
	private String smtpUsername;
	private String smtpPassword;
	private String smtpSender;
	private String smtpRecipient;
	private String smtpReplyTo;
	private int recordsPerPage;
	private int paginationCount;
	private String cronExpression;
	private String renewalPeriod;
	private String filterType;
	private String filterValue;

	public String getSmtpHost() {
		return smtpHost;
	}
	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}
	public String getSmtpPort() {
		return smtpPort;
	}
	public void setSmtpPort(String smtpPort) {
		this.smtpPort = smtpPort;
	}
	public String getSmtpSender() {
		return smtpSender;
	}
	public void setSmtpSender(String smtpSender) {
		this.smtpSender = smtpSender;
	}
	public String getSmtpRecipient() {
		return smtpRecipient;
	}
	public void setSmtpRecipient(String smtpRecipient) {
		this.smtpRecipient = smtpRecipient;
	}
	public String getSmtpReplyTo() {
		return smtpReplyTo;
	}
	public void setSmtpReplyTo(String smtpReplyTo) {
		this.smtpReplyTo = smtpReplyTo;
	}
	public int getRecordsPerPage() {
		return recordsPerPage;
	}
	public void setRecordsPerPage(int recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}
	public int getPaginationCount() {
		return paginationCount;
	}
	public void setPaginationCount(int paginationCount) {
		this.paginationCount = paginationCount;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
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
	public String getSmtpUsername() {
		return smtpUsername;
	}
	public void setSmtpUsername(String smtpUsername) {
		this.smtpUsername = smtpUsername;
	}
	public String getSmtpPassword() {
		return smtpPassword;
	}
	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}
	public String getRenewalPeriod() {
		return renewalPeriod;
	}
	public void setRenewalPeriod(String renewalPeriod) {
		this.renewalPeriod = renewalPeriod;
	}

	@Override
	public String toString() {
		return "AssetConfig [smtpHost=" + smtpHost + ", smtpPort=" + smtpPort + ", smtpUsername=" + smtpUsername
				+ ", smtpPassword=" + smtpPassword + ", smtpSender=" + smtpSender + ", smtpRecipient=" + smtpRecipient
				+ ", smtpReplyTo=" + smtpReplyTo + ", recordsPerPage=" + recordsPerPage + ", paginationCount="
				+ paginationCount + ", cronExpression=" + cronExpression + ", renewalPeriod=" + renewalPeriod
				+ ", filterType=" + filterType + ", filterValue=" + filterValue + "]";
	}
}
