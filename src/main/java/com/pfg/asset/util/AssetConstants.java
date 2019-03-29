package com.pfg.asset.util;

import java.util.Arrays;
import java.util.List;

public class AssetConstants {

	private AssetConstants() { }
	
	public static final int PAGINATION_COUNT = 5;
	public static final int DEFAULT_COUNT = 10;

	public static final String MESSAGE_KEY = "message";

	public static final String EMP_FORM = "empForm";
	public static final String UPDATE_EMP_FORM = "UPDATE";
	public static final String CREATE_EMP_FORM = "CREATE";

	public static final String ERROR_INVALID_INPUT = "Invalid Input";
	public static final String ERROR_INVALID_ID = "Invalid Employee Number";

	public static final String ERROR_INVALID_DATES = "Please provide the valid dates.";
	public static final String ERROR_REQUIRED_INPUT = "Please provide the values for all the required fields.";

	public static final String SUCCESSSTATUS = "Success";
	public static final String FAILURESTATUS = "Failure";
	
	public static final String DEFAULT_DATEFORMAT = "yyyy-MM-dd";
	
	public static final List<String> INTERVALS = Arrays.asList("3 months", "6 months", "9 months", "12 months");
	
	public static final String CRON_EXPRESSION = "0 * * * * ?";
	
	public enum FILTER { RENEWAL, PRODUCT, LOCATION, ADDRESS, SERIAL, DATE, TRACK, OEM } 
}
