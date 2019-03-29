package com.pfg.asset.util;

import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.opencsv.CSVReader;

public class CSVReaderUtils {
	private static final Logger logger = Logger.getLogger(CSVReaderUtils.class.getName());

	private CSVReaderUtils() { }

	public static void importCSV(String csvFile) {
        csvFile = "C:\\Asset\\ProductInfo.csv";
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(csvFile));
            String[] line;
            while ((line = reader.readNext()) != null) {
            	if(Validator.isEmpty(line[0]) || Validator.isEmpty(line[2])) {
            		logger.log(Level.INFO, "CSVReaderUtils: importCSV: product name={0} / SKU={2} is null or empty", line);
            	}else {
            		logger.log(Level.INFO, "CSVReaderUtils: importCSV: Product [Name={0}, Description={1}, SKU={2}]", line);
            	}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public static void main(String[] args) {
        String csvFile = "C:\\Asset\\ProductInfo.csv";
		importCSV(csvFile);
	}
}
