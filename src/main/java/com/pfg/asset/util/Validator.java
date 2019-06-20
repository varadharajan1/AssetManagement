package com.pfg.asset.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.pfg.asset.dto.AssetInfo;

public class Validator {

	private Validator() { }
	
	public static boolean validate(Object object) {
		boolean result = false;
		
		if (object instanceof AssetInfo) {
			AssetInfo asset = (AssetInfo)object;
			result = validateAssetInputs(asset);
		}else if (object instanceof AssetInfo) {
			result = false;
		}
		return result;
	}
	public static boolean isValidUserName(String string) {
		boolean result = false;
		Pattern p = Pattern.compile("^[a-zA-Z0-9_]+$");
		Matcher m = p.matcher(string);
		if (m.find()) {
			result = true;
		}
		return result;
	}

	public static boolean isValid(String input) {
		Pattern p = Pattern.compile("<|\"|>|;");
		Matcher m = p.matcher(input);
		return !m.find();
	}
  
	public static boolean isValidDate(String date, String format) {
		boolean retValue = false;
		if ((date == null) || (date.trim().length() == 0)) {
			return retValue;
		}
		format = Validator.isNotEmpty(format) ? format : "MM/dd/yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date testDate = null;
		try {
			testDate = sdf.parse(date);
		} catch (ParseException e) {
			return retValue;
		}
		if (!sdf.format(testDate).equals(date)) {
			retValue = false;
		} else {
			retValue = true;
		}
		return retValue;
	}

	public static boolean isValidString(String... fields) {
		boolean isValid = false;
		for (String string : fields) {
			Pattern pattern = Pattern.compile("^[a-zA-Z0-9_*\\-\\s]+$");
			Matcher matcher = pattern.matcher(string);
			if (matcher.find()) {
				isValid = true;
			} else {
				return false;
			}
		}
		return isValid;
	}

	public static boolean isAllLetters(String input) {
		boolean result = false;
		Pattern p = Pattern.compile("^[a-zA-Z]+$");
		Matcher m = p.matcher(input);
		if (m.find()) {
			result = true;
		}
		return result;
	}
  
	public static boolean isAllNumbers(String numbers) {
		boolean result = false;
		Pattern p = Pattern.compile("^\\d+$");
		Matcher m = p.matcher(numbers);
		if (m.find()) {
			result = true;
		}
		return result;
	}

	public static boolean isAlphaNumeric(String string) {
		boolean result = false;
		Pattern p = Pattern.compile("^[a-zA-Z0-9]+$");
		Matcher m = p.matcher(string);
		if (m.find()) {
			result = true;
		}
		return result;
	}
  
	public static boolean isNotEmpty(String string) {
		boolean retValue = false;
		if ((string != null) && (string.trim().length() > 0)) {
			retValue = true;
		}
		return retValue;
	}
  
	public static boolean isEmpty(String string) {
		boolean retValue = false;
		if ((string == null) || (string.trim().equals(""))) {
			retValue = true;
		}
		return retValue;
	}

	public static boolean validateAssetInputs(AssetInfo asset) {
		boolean result = false;
	    if (Validator.isNotEmpty(asset.getTrackName()) || 
	    		Validator.isNotEmpty(asset.getBusinessSegment()) || 
	    		Validator.isNotEmpty(asset.getOpcoName()) || 
	    		Validator.isNotEmpty(asset.getDeviceName()) || 
	    		Validator.isNotEmpty(asset.getOemName()) || 
	    		Validator.isNotEmpty(asset.getProductNumber()) || 
	    		Validator.isNotEmpty(asset.getSerialNumber()) || 
	    		Validator.isNotEmpty(asset.getDeployedLocation()) || 
	    		Validator.isNotEmpty(asset.getAddress1()) || 
	    		Validator.isNotEmpty(asset.getSupportEndDate()) 
	    	) {
	    	result = true;
	    }
		return result;
	}
}
