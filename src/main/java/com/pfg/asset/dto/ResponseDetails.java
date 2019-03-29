package com.pfg.asset.dto;

import java.io.Serializable;

public class ResponseDetails  implements Serializable {
	private static final long serialVersionUID = 1L;
	private String status = "";
	private String returnCode = "";
	private String message = "";
	private String problemTypes = "";

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getProblemTypes() {
		return problemTypes;
	}
	public void setProblemTypes(String problemTypes) {
		this.problemTypes = problemTypes;
	}
}
