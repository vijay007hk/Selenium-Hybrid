package com.selenium.test;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.selenium.xls.reader.XLSReader;

public class DriverScript {

	public Logger APP_LOGS;
	public XLSReader suiteXLS;
	public int currentSuiteID;
	public String runmode="";
	public static void main(String[] args) throws EncryptedDocumentException, InvalidFormatException, IOException {
		
        //System.out.println(System.getProperty("user.dir"));
		DriverScript test = new DriverScript();
        test.start();
        
	}
	public void start() throws EncryptedDocumentException, InvalidFormatException, IOException{
		//Initialise the app logs
		APP_LOGS =Logger.getLogger("devpinoyLogger");
		APP_LOGS.debug("Hello");
		
		
		APP_LOGS.debug("Initialize Suite Xls");
		 suiteXLS = new XLSReader(System.getProperty("user.dir")+"\\src\\com\\selenium\\xls\\Suite.xls");
	    System.out.println(suiteXLS.getRowCount("TestSuite"));
	    for(int currentTestSuiteId=1; currentTestSuiteId<=suiteXLS.getRowCount("TestSuite"); currentTestSuiteId++){
	    	runmode = suiteXLS.getCellData("TestSuite", "Runmode", 2);
	    	
	    	
	    }
	    System.out.println("Runmode is :"  + runmode);
	}

}
