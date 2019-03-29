package com.pfg.asset.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ValueConvertor {

	private static final Logger logger = Logger.getLogger(ValueConvertor.class.getName());
	private ValueConvertor() { }
	
	public static long convertToLong(String date, String format){
		long result = 0;
		logger.log(Level.INFO, "ValueConvertor: convertToLong: {0}", date );
		format = Validator.isNotEmpty(format) ? format : "MM/dd/yyyy";
		if (Validator.isNotEmpty(date)) {
			try {
				SimpleDateFormat newDateFormat = new SimpleDateFormat(format);
				Date myDate = newDateFormat.parse(date);
				
				newDateFormat.applyPattern("yyyyMMdd");
				String myDateString = newDateFormat.format(myDate);
				
				result = Long.parseLong(myDateString);
			} catch (ParseException e) {
				logger.log(Level.SEVERE, "ValueConvertor: convertToLong; error while converting the date: {0}", date );
				logger.log(Level.SEVERE, "ValueConvertor: {0}", e.getMessage());
			}
		}
		logger.log(Level.INFO, "ValueConvertor: convertToLong; result: {0}", result );
		return result;
	}
	
	public static LocalDate convertToLocalDate(String date, String format){
		LocalDate result = null;
		logger.log(Level.INFO, "ValueConvertor: convertToLocalDate: {0}", date );
		format = Validator.isNotEmpty(format) ? format : "MM/dd/yyyy";
		if (Validator.isNotEmpty(date)) {
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
				result = LocalDate.parse(date, formatter);
			} catch (Exception e) {
				logger.log(Level.SEVERE, "ValueConvertor: convertToLong; error while converting the date: {0}", date );
				logger.log(Level.SEVERE, "ValueConvertor: {0}", e.getMessage());
			}
		}
		logger.log(Level.INFO, "ValueConvertor: convertToLocalDate; result: {0}", result );
		return result;
	}

	public static String convertToDateString(long date, String format){
		String result = "";
		logger.log(Level.INFO, "ValueConvertor: convertToDateString: {0}", date );
		format = Validator.isNotEmpty(format) ? format : "MM/dd/yyyy";
		if (date > 0) {
			try {
				SimpleDateFormat newDateFormat = new SimpleDateFormat(format);
				Date myDate = newDateFormat.parse(""+date);
				
				result = newDateFormat.format(myDate);

			} catch (ParseException e) {
				logger.log(Level.SEVERE, "ValueConvertor: convertToDateString; error while converting the date: {0}", date );
				logger.log(Level.SEVERE, "ValueConvertor: {0}", e.getMessage());
			}
		}
		logger.log(Level.INFO, "ValueConvertor: convertToDateString: result: {0}", result );
		return result;
	}

	public static int convertToInt(String numbers){
		int result = 0;
		logger.log(Level.INFO, "ValueConvertor: convertToInt; numbers: {0}", numbers );
		if (Validator.isAllNumbers(numbers)) {
			result = Integer.parseInt(numbers);
		}
		logger.log(Level.INFO, "ValueConvertor: convertToInt; result: {0}", result );
		return result;
	}
    
	public static String[] stringTokanizer(String str, String delimiter){
		return Pattern.compile(delimiter).splitAsStream(str).toArray(String[]::new);
	}
	
	public static String javascriptUnescape(String escape) throws ScriptException{

		ScriptEngineManager factory = new ScriptEngineManager();
		ScriptEngine engine = factory.getEngineByName("JavaScript");
		return (String) engine.eval("unescape('" + escape + "')");
	}
	
	public static <K extends Comparable,V extends Comparable> Map<K,V> sortByValues(Map<K,V> map){
        List<Map.Entry<K,V>> entries = new LinkedList<Map.Entry<K,V>>(map.entrySet());
      
        Collections.sort(entries, new Comparator<Map.Entry<K,V>>() {
            @Override
            public int compare(Entry<K, V> o1, Entry<K, V> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
      
        Map<K,V> sortedMap = new LinkedHashMap<K,V>();
        for(Map.Entry<K,V> entry: entries){
            sortedMap.put(entry.getKey(), entry.getValue());
        }
      
        return sortedMap;
    }
}
