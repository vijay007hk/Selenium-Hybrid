package com.selenium.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.selenium.xls.reader.XLSReader;

public class DriverScript {

	public static void main(String[] args) throws EncryptedDocumentException, InvalidFormatException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		//System.out.println(System.getProperty("user.dir"));
		DriverScript test = new DriverScript();
		test.start();

	}
	public static Logger APP_LOGS;
	public XLSReader suiteXLS;
	public XLSReader currentTestSuiteXLS;
	public XLSReader currentTestCaseXLS;
	public int currentSuiteID;
	public String currentTestCase;
	public String currentTestSuite;
	public int currentTestCaseId;
	public int currentTestStepId;
	public String runmode="";
	public Method method[];
	private String currentTestCaseName;
	public Keywords keywords;
	public String currentKeyword = "";
	public String keyword_exec_result;
	public String username;
	public String password;
	public String currentObject;
	public String currentData;
	public String finalData = "";
	ArrayList<String> resultSet;
	public int currentTestDataId;
	
	public DriverScript(){
		keywords = new Keywords();
		method = keywords.getClass().getMethods();
	}

	public void executeKeywords() throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{

		String[] data;
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+ "\\src\\com\\selenium\\config\\config.properties");
		FileInputStream fis2 = new FileInputStream(System.getProperty("user.dir")+ "\\src\\com\\selenium\\config\\OR.properties");
		Properties config = new Properties();
		Properties or = new Properties();
		config.load(fis);
		or.load(fis2);

		for(currentTestStepId=1; currentTestStepId<currentTestSuiteXLS.getRowCount(Constants.TEST_STEP_SHEET); currentTestStepId++){

			System.out.println("Test steps : "+ currentTestSuiteXLS.getCellData(Constants.TEST_STEP_SHEET, Constants.KEYWORDS, currentTestStepId));
			if(currentTestCaseName.equals(currentTestSuiteXLS.getCellData(Constants.TEST_STEP_SHEET, Constants.TEST_CASE_ID, currentTestStepId))){
				currentKeyword = currentTestSuiteXLS.getCellData(Constants.TEST_STEP_SHEET, Constants.KEYWORDS, currentTestStepId);
				APP_LOGS.debug(currentKeyword);
				currentObject = currentTestSuiteXLS.getCellData(Constants.TEST_STEP_SHEET, Constants.OBJECT, currentTestStepId);
				currentData = currentTestSuiteXLS.getCellData(Constants.TEST_STEP_SHEET, Constants.DATA, currentTestStepId);
				String object="";
						
				if(!currentObject.isEmpty()){
					object = or.getProperty(currentObject);
				}
				else object = "";
								
				if(currentData.contains("config")){
					data = currentData.split("\\|");
					finalData = config.getProperty(data[1]);
				}else if(currentData.contains("OR")){
					data = currentData.split("\\|");
					finalData = or.getProperty(data[1]);
				}
				else {
					  
					//finalData = or.getProperty(currentData);
				      finalData = currentTestSuiteXLS.getCellData(currentTestCaseName, currentData, currentTestDataId);
				}
				
							                 
				 for(int i=0; i<method.length; i++){
				  if(method[i].getName().equals(currentKeyword)){
					  keyword_exec_result = (String)method[i].invoke(keywords,object,finalData);
					  APP_LOGS.debug(keyword_exec_result);
					  resultSet.add(keyword_exec_result);
				  }
			  }

			}
		
		}

	}
	public void start() throws EncryptedDocumentException, InvalidFormatException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		//Initialise the app logs
		
		APP_LOGS =Logger.getLogger("devpinoyLogger");
		APP_LOGS.debug("Hello");

		APP_LOGS.debug("Initialize Suite Xls");
		suiteXLS = new XLSReader(System.getProperty("user.dir")+"\\src\\com\\selenium\\xls\\Suite.xls");
		System.out.println(suiteXLS.getRowCount("TestSuite"));
		runmode = suiteXLS.getCellData("TestSuite", "Runmode", 3);
		System.out.println("Runmode is :"  + runmode);

		
		for(int currentTestSuiteId=1; currentTestSuiteId<suiteXLS.getRowCount(Constants.TEST_SUITE_SHEET); currentTestSuiteId++){
			System.out.println(suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.TEST_SUITE_ID, currentTestSuiteId)+"--"+ suiteXLS.getCellData("TestSuite", "Runmode", currentTestSuiteId));
			currentTestSuite = suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.TEST_SUITE_ID, currentTestSuiteId); 

			if(suiteXLS.getCellData(Constants.TEST_SUITE_SHEET, Constants.RUNMODE, currentTestSuiteId).trim().equalsIgnoreCase(Constants.RUNMODE_YES)){
				System.out.println("Get current test suite whosse runmode is Y: "+currentTestSuite);
				currentTestSuiteXLS = new XLSReader(System.getProperty("user.dir")+ "\\src\\com\\selenium\\xls\\"+currentTestSuite+".xls");
				//iterate through all the testcases in the suite
				for(currentTestCaseId=1; currentTestCaseId<currentTestSuiteXLS.getRowCount("Test Cases"); currentTestCaseId++ ){

					currentTestCaseName = currentTestSuiteXLS.getCellData(Constants.TEST_CASE_SHEET, Constants.TEST_CASE_ID, currentTestCaseId);
					resultSet = new ArrayList<String>();
					if(currentTestSuiteXLS.getCellData(Constants.TEST_CASE_SHEET, Constants.RUNMODE, currentTestCaseId).trim().equalsIgnoreCase(Constants.RUNMODE_YES)){
						System.out.println("Get the Test Case whose runmode is Y: "+ currentTestCaseName);
						//Execute test steps- keywords for the test case whose runmode is Y and as many times as there is data
						if(currentTestSuiteXLS.isSheetExist(currentTestCaseName)){
							System.out.println("TestData Row Count  :"+currentTestSuiteXLS.getRowCount(currentTestCaseName));
							for(currentTestDataId=1; currentTestDataId<currentTestSuiteXLS.getRowCount(currentTestCaseName); currentTestDataId++){
								
								if(currentTestSuiteXLS.getCellData(currentTestCaseName, Constants.RUNMODE, currentTestDataId).trim().equals(Constants.RUNMODE_YES))
									executeKeywords();
								    createXLSReport();
							}
						}
						else{
							
							executeKeywords();
							//createXLSReport();
						}
					}
				}
			}
		}

	}
    
	
	public void createXLSReport(){
		
		if(resultSet.isEmpty()){
			//skip
			currentTestSuiteXLS.setCellData(currentTestCaseName, Constants.RESULT, currentTestDataId, Constants.RESULT_SKIP);
		}else{
			for(int i=0; i<resultSet.size(); i++){
				if(!resultSet.get(i).equals(Constants.PASS)){
					currentTestSuiteXLS.setCellData(currentTestCaseName, Constants.RESULT, currentTestDataId, Constants.RESULT_FAIL);
				}
				
			}
		}
		currentTestSuiteXLS.setCellData(currentTestCaseName, Constants.RESULT, currentTestDataId, Constants.RESULT_PASS);
		System.out.println("LoginTest "+ " Cols count :" + currentTestSuiteXLS.getColumnCount("Test Steps"));
		System.out.println("col exists :" + currentTestSuiteXLS.isColumnExists("Test Steps", "Object"));
		currentTestSuiteXLS.addColumn("Test Steps", "Final Results");
		
	}
	
}
