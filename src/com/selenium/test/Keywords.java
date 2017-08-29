package com.selenium.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Keyboard;
import com.selenium.test.DriverScript;

@SuppressWarnings("unused")
public class Keywords {
	
	static WebDriver driver;
	//TakeScreenShot scr = (TakeScreenShot)
	public  static String openBrowser(String object, String data){
		 if(data.equals("Firefox")){
			 System.setProperty("webdriver.gecko.driver", "C:\\geckodriver\\geckodriver.exe");
			 driver=new FirefoxDriver();
			 driver.manage().timeouts().implicitlyWait(20L, TimeUnit.SECONDS);
		 }
		return Constants.PASS;
	}
	public static String navigate(String object, String data){
		try{
	    driver.navigate().to(data);
	    return Constants.PASS;
		}catch(Exception e){
		   return Constants.RESULT_FAIL+ " Unable to naviagate to url";
		}	
	    
   }
	public static String verifyTitle(String object, String data){
	    if(driver.getTitle().equals(data)){
		System.out.println("Title is as Expected!");
		
		return Constants.PASS;
	    }
	    else return Constants.RESULT_FAIL + " Title is not as expected!";
	}
	
	public static String clickLink(String object, String data){
		try{
		driver.findElement(By.xpath(object)).click();;
		return Constants.PASS;
		}catch(Exception e){
			return Constants.RESULT_FAIL + " Unable to click on link";
		}
	}
	public static String writeInput(String object, String data){
		try{
		driver.findElement(By.xpath(object)).sendKeys(data);
		return Constants.PASS;
		}catch(Exception e){
			return Constants.RESULT_FAIL + " Unable to write in input.";
		}
		
	}
	public static String clickButton(String object, String data) throws InterruptedException{
		try{
		driver.findElement(By.xpath(object)).click();
		Thread.sleep(5000L);
		driver.quit();
		return Constants.PASS;
		}catch(Exception e){
			return Constants.RESULT_FAIL + " Unable to click button.";
		}
	}
	public static String clickLinkText(String object, String data){
		
		//driver.quit();
		return Constants.PASS;
	}
    
	/*********************** APPLICATION SPECIFIC KEYWORDS 
	 * @throws IOException ********************/
	
	public static String validateLogin(String object, String data) throws IOException{
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+ "\\src\\com\\selenium\\config\\config.properties");
		FileInputStream fis2 = new FileInputStream(System.getProperty("user.dir")+ "\\src\\com\\selenium\\config\\OR.properties");
		Properties config = new Properties();
		Properties or = new Properties();
		config.load(fis);
		or.load(fis2);
		object = or.getProperty("invalid_pssword_email");
		try{
			String ivalidemailpass =  driver.findElement(By.xpath(object)).getText();
			String missingPass = driver.findElement(By.xpath(or.getProperty("blank_password"))).getText();
			String missingEmail = driver.findElement(By.xpath(or.getProperty("blank_email"))).getText();
			  DriverScript.correctData = DriverScript.currentTestSuiteXLS.getCellData(DriverScript.currentTestCaseName, Constants.CORRECTDATA, DriverScript.currentTestDataId );
			  if(!DriverScript.correctData.equals(Constants.CORRECTDATA_YES)){
				  String errMsg = DriverScript.currentTestSuiteXLS.getCellData(DriverScript.currentTestCaseName, Constants.ERRORMSG, DriverScript.currentTestDataId);
				  if(errMsg.trim().equals(ivalidemailpass.trim()) || errMsg.trim().equals(missingPass.trim()) || errMsg.trim().equals(missingEmail.trim())){
					  System.out.println("Error message is as expected :"+ errMsg);
					  return Constants.PASS;
				  }
				  
				  else return Constants.RESULT_FAIL;
			  }
		}catch(Exception e){
			return Constants.RESULT_FAIL;
		}
		return "";
		
	}
}
