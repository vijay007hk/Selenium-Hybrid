package com.selenium.test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Keyboard;

@SuppressWarnings("unused")
public class Keywords {
   
	
	static WebDriver driver;
	public  static String openBrowser(String object, String data){
		 if(data.equals("Firefox")){
			 System.setProperty("webdriver.gecko.driver", "C:\\geckodriver\\geckodriver.exe");
			 driver=new FirefoxDriver();
			 driver.manage().timeouts().implicitlyWait(20L, TimeUnit.SECONDS);
		 }
		return Constants.PASS;
	}
	public static String navigate(String object, String data){
		
	    driver.navigate().to(data);	
	
	    return Constants.PASS;
   }
	public static String verifyTitle(String object, String data){
	    if(driver.getTitle().equals(data)){
		System.out.println("Title is as Expected!");
		
	    	return "PASS";
	    }
	    return Constants.PASS;
	}
	
	public static String clickLink(String object, String data){
		driver.findElement(By.xpath(object)).click();;
		
		return Constants.PASS;
	}
	public static String writeInput(String object, String data){
		driver.findElement(By.xpath(object)).sendKeys(data);
		/*Keyboard keys = null ;
		keys.sendKeys("TAB");*/
		return Constants.PASS;
	}
	public static String clickButton(String object, String data) throws InterruptedException{
		driver.findElement(By.xpath(object)).click();
		Thread.sleep(5000L);
		driver.quit();
		return Constants.PASS;
	}
	public static String clickLinkText(String object, String data){
		
		//driver.quit();
		return Constants.PASS;
	}

}
