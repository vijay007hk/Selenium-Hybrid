package com.selenium.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.selenium.xls.reader.XLSReader;

public class DriverScript {

	public Logger APP_LOGS;
	public XLSReader suiteXLS;
	public XLSReader currentTestSuiteXLS;
	public XLSReader currentTestCaseXLS;
	public int currentSuiteID;
	public String currentTestCase;
	public String currentTestSuite;
	public int currentTestCaseId;
	public int currentTestStepId;
	public String runmode="";
	private String currentTestCaseName;
	
	
	
	public static void main(String[] args) throws EncryptedDocumentException, InvalidFormatException, IOException {
		
        //System.out.println(System.getProperty("user.dir"));
		DriverScript test = new DriverScript();
        test.start();
        
	}
	public void start() throws EncryptedDocumentException, InvalidFormatException, IOException{
		//Initialise the app logs
		APP_LOGS =Logger.getLogger("devpinoyLogger");
		APP_LOGS.debug("Hello");
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+ "\\src\\com\\selenium\\config\\config.properties");
		FileInputStream fis2 = new FileInputStream(System.getProperty("user.dir")+ "\\src\\com\\selenium\\config\\OR.properties");
		Properties config = new Properties();
		Properties or = new Properties();
		config.load(fis);
		or.load(fis2);
		
		APP_LOGS.debug("Initialize Suite Xls");
		 suiteXLS = new XLSReader(System.getProperty("user.dir")+"\\src\\com\\selenium\\xls\\Suite.xls");
	    System.out.println(suiteXLS.getRowCount("TestSuite"));
	    runmode = suiteXLS.getCellData("TestSuite", "Runmode", 3);
	    System.out.println("Runmode is :"  + runmode);
	    
	    
	    for(int currentTestSuiteId=1; currentTestSuiteId<suiteXLS.getRowCount(Constants.TEST_SUITE_SHEET); currentTestSuiteId++){
	    	   	System.out.println(suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.TEST_SUITE_ID, currentTestSuiteId)+"--"+ suiteXLS.getCellData("TestSuite", "Runmode", currentTestSuiteId));
	    	   	currentTestSuite = suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.TEST_SUITE_ID, currentTestSuiteId); 
             
                if(suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.RUNMODE, currentTestSuiteId).equalsIgnoreCase("Y")){
                	System.out.println("Get current test suite whosse runmode is Y: "+currentTestSuite);
                	currentTestSuiteXLS = new XLSReader(System.getProperty("user.dir")+ "\\src\\com\\selenium\\xls\\"+currentTestSuite+".xls");
                  //iterate through all the testcases in the suite
                  for(currentTestCaseId=1; currentTestCaseId<currentTestSuiteXLS.getRowCount("Test Cases"); currentTestCaseId++ ){
                	  
                	  currentTestCaseName = currentTestSuiteXLS.getCellData(Constants.TEST_CASE_SHEET, Constants.TEST_CASE_ID, currentTestCaseId);
                	  
                	  if(currentTestSuiteXLS.getCellData(Constants.TEST_CASE_SHEET, Constants.RUNMODE, currentTestCaseId).equalsIgnoreCase("Y")){
                		  System.out.println("Get the Test Case whose runmode is Y: "+ currentTestCaseName);
                		  //Execute test steps for the test case whose runmode is Y
                		  for(currentTestStepId=1; currentTestStepId<currentTestSuiteXLS.getRowCount(Constants.TEST_STEP_SHEET); currentTestStepId++){
                			  	String keyword = currentTestSuiteXLS.getCellData(Constants.TEST_STEP_SHEET, Constants.KEYWORDS, currentTestStepId);
                			  System.out.println("Test steps : "+ currentTestSuiteXLS.getCellData(Constants.TEST_STEP_SHEET, Constants.KEYWORDS, currentTestStepId));
                			  if(keyword.equals("openBrowser"))
                				  Keywords.openBrowser("", config.getProperty("browser"));
                			  if(keyword.equals("navigate"))
                				  Keywords.navigate("", config.getProperty("url"));
                			  if(keyword.equals("verifyTitle"))
                				  Keywords.verifyTitle("", or.getProperty("windowtitle"));
                			  if(keyword.equals("clickLink")){
                				  Keywords.clickLink(or.getProperty("login"), "");
                				  System.out.println("Clicked on Login link");
                			  }
                			  if(keyword.equals("writeInput")){
                				  Keywords.writeInput(or.getProperty("email"), or.getProperty("emailid"));
                			  }
                			  if(keyword.equals("writeInput")){
                				  Keywords.writeInput(or.getProperty("password"), or.getProperty("pwd"));
                			  }
                			  
                 		  }
                	  }
                  }
                }
	    }
	    
	}

}
