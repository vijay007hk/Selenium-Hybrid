package com.selenium.xls.reader;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;



public class XLSReader {

	public static String workbooks = "Suite.xls";
	public static String sheets = "";
	public static String rows = "";
	public static String col = "";
	public static String cells = "";
	static int rownum = 0;
	//
	public static String filename = System.getProperty("user.dir")+"\\src\\com\\selenium\\xls";
	public String path;
	public FileInputStream fis = null;
	public FileOutputStream fout = null;
	private HSSFWorkbook workbook = null;
	private HSSFSheet sheet = null;
	private HSSFRow row = null;
	private HSSFCell cell = null;
	private HSSFRow activerow= null;
	
	
	public XLSReader(String path){
		this.path = path;
		try{
			fis = new FileInputStream(path);
			workbook = new HSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			fis.close();
			
		}catch (Exception e){
			e.printStackTrace();
		}
		
		
	}
	public static void main(String[] args) throws IOException, EncryptedDocumentException, InvalidFormatException  {
	
	//	InputStream isr = new FileInputStream(System.getProperty("user.dir")+"\\src\\com\\selenium\\xls\\"+workbook);
	//	Workbook wb = WorkbookFactory.create(isr);
	//	Sheet sheet =  wb.getSheetAt(0);
	//	Row rw =  sheet.getRow(rownum);
	//	Column coll = new Column();
	//	for(int i=0; i<=rw.getSheet().getLastRowNum(); i++){
	//		for(int j=0; j<rw.getLastCellNum(); j++){
	//					System.out.print(rw.getCell(j)+"  ");
						
		//	}
			//			rw = sheet.getRow(rownum++);
				//		System.out.println();
		//}
		//for(int j=0; j<rw.getLastCellNum(); j++){
			//System.out.println(coll.getColumn());
		//}
		//System.out.println("Row count is :"+getRowCount("CheckItemsSuite.xls", "LoginTest"));
		//System.out.println("Column count is :"+getRowCount("CheckItemsSuite.xls", "LoginTest"));

	}
	
	
	//Returns the row count
	public int getRowCount(String sheetname){
	    int index = workbook.getSheetIndex(sheetname);
	    
	    if(index == -1)
	    	return 0;
	    else{
	    	sheet=workbook.getSheetAt(index);
	    	int rowcount = sheet.getLastRowNum()+1;
	    	return rowcount;
	    }
		
	}
	
	
	public static int getColumnCount(String workbook, String sheetname) throws EncryptedDocumentException, InvalidFormatException, IOException{
		int colcount = -1;
		InputStream isr = new FileInputStream(System.getProperty("usr.dir")+"\\src\\com\\selenium\\xls\\"+workbook);
		Workbook wb = WorkbookFactory.create(isr);
		Sheet sheet = wb.getSheet(sheetname);
		Row rws = sheet.getRow(0);
		colcount =  rws.getLastCellNum();
		return colcount;
	}
	
	//Returns Cell value
	@SuppressWarnings({ "deprecation" })
	public String getCellData(String sheetname, String colname, int rownum){
		if(rownum<=0)
		return "";
		
		int index = workbook.getSheetIndex(sheetname);
		int colnum = -1;
		if(index==-1)
			return "";
		
		sheet = workbook.getSheetAt(index);
		row = sheet.getRow(0);
		activerow = sheet.getRow(rownum);
		for(int i=0; i<row.getLastCellNum(); i++){
			if(row.getCell(i).getStringCellValue().trim().equals(colname))
				colnum = i;
			if(colnum>0)break;
				
		}
		if(colnum==-1)
			return "";

		if(activerow.getCell(colnum).getCellType()==
				(Cell.CELL_TYPE_STRING))
			return activerow.getCell(colnum).getStringCellValue();
		else if(activerow.getCell(colnum).getCellType()==(Cell.CELL_TYPE_NUMERIC)){
			String cellText = String.valueOf(cell.getNumericCellValue());
			return cellText;
		}
		
			return "";		
	
	}
	public boolean isSheetExist(String currentTestCaseName) {
		int numofsheets = workbook.getNumberOfSheets();
		String sheet = currentTestCaseName ;  
		for(int i=0;i<numofsheets; i++){
			if(sheet.equals(workbook.getSheetAt(i).getSheetName().trim()))
				return true;
		}
		
		
		return false;
	}
}
