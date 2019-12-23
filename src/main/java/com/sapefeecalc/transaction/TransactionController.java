package com.sapefeecalc.transaction;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TransactionController {

	private static final String INPUT_FILE_PATH = "E:\\Mydocs\\Sapient\\InputDatafile.xlsx";
	private static final String OUTPUT_FILE_PATH = "E:\\Mydocs\\Sapient\\OutputDatafile.xlsx";
	
	public static List<TransactionBean> readExcelSheet(){
		List<TransactionBean> transactionList = new ArrayList<TransactionBean>();
        FileInputStream fileInput = null;
        try {
            fileInput = new FileInputStream(INPUT_FILE_PATH);
            XSSFWorkbook workbook = new XSSFWorkbook(fileInput);
            int numberOfSheets = workbook.getNumberOfSheets();
            for (int i = 0; i < numberOfSheets; i++) {
            	XSSFSheet sheet = workbook.getSheetAt(i);
            	Iterator rowIterator = sheet.iterator();
                while (rowIterator.hasNext()) {
                	TransactionBean transactionBean = new TransactionBean();
                    Row row = (Row) rowIterator.next();
                    if(row.getRowNum()==0){
                        continue; 
                    }
                    Iterator cellIterator = row.cellIterator();
                    while (cellIterator.hasNext()) {
                        Cell cell = (Cell) cellIterator.next();
                        if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
                        	if (cell.getColumnIndex() == 0) {
                        		transactionBean.setTransactionId(cell.getStringCellValue());
                            }
                            else if (cell.getColumnIndex() == 1) {
                            	transactionBean.setClientId(cell.getStringCellValue());
                            }
                            else if (cell.getColumnIndex() == 2) {
                            	transactionBean.setSecurityId(cell.getStringCellValue());
                            }
                            else if (cell.getColumnIndex() == 3) {
                            	transactionBean.setTransactionType(cell.getStringCellValue());
                            }
                            else if (cell.getColumnIndex() == 6) {
                            	transactionBean.setPriorityFlag(cell.getStringCellValue());
                            }
                        } else if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
                        	
                        	if (cell.getColumnIndex() == 4) {
                        		
                            	transactionBean.setTransactionDate(cell.getDateCellValue());
                            } 
                        	else if (cell.getColumnIndex() == 5) {
                            	transactionBean.setMarketValue(cell.getNumericCellValue());
                            }
                        } 
                    }
                    transactionList.add(transactionBean);
                }
            }
            fileInput.close();
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transactionList;
	}
	
	public static List<TransactionProcessingBean> processingFee(){
		
		List<TransactionBean> transactionList = new ArrayList<TransactionBean>();
		SimpleDateFormat sdfSource = new SimpleDateFormat("MM/dd/yy");
		try{
		TransactionBean tb1 = new TransactionBean("SAPEXTXN1", "GS", "ICICI", 
				"BUY", sdfSource.parse("11/23/2013"), 101.9, "Y");
		TransactionBean tb2 = new TransactionBean("SAPEXTXN2", "AS", "REL", 
				"SELL", sdfSource.parse("11/20/2013"), 121.9, "N");
		TransactionBean tb3 = new TransactionBean("SAPEXTXN3", "AS", "REL", 
				"DEPOSIT", sdfSource.parse("11/20/2013"), 120.0, "Y");
		TransactionBean tb4 = new TransactionBean("SAPEXTXN4", "HJ", "RELIND", 
				"WITHDRAW", sdfSource.parse("11/30/2013"), 230, "N");
		TransactionBean tb5 = new TransactionBean("SAPEXTXN5", "GS", "ICICI", 
				"SELL", sdfSource.parse("11/23/2013"), 330.0, "Y");
		System.out.println(tb1.equals(tb5));
		transactionList.add(tb1); transactionList.add(tb2); transactionList.add(tb3);
		transactionList.add(tb4);transactionList.add(tb5);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		List<TransactionProcessingBean> transProsList = new ArrayList<TransactionProcessingBean>();
		List<TransactionBean> intradayTx=new ArrayList<TransactionBean>();
		List<TransactionBean> tempIntradayTx=new ArrayList<TransactionBean>();
		for(TransactionBean getTxn : transactionList){
			if(tempIntradayTx.contains(getTxn)){
				intradayTx.add(getTxn);
			}else{
				tempIntradayTx.add(getTxn);
			}
		}
		System.out.println(intradayTx);
		for(TransactionBean gettransaction : transactionList){
			
			TransactionProcessingBean txProcBean = new TransactionProcessingBean();
			
			if(checkIntradayTransactions(intradayTx,gettransaction )){
				txProcBean.setProcessingFee(IntradayTransaction(gettransaction));				
			}else{
				txProcBean.setProcessingFee(normalTransaction(gettransaction));
			}
			txProcBean.setClientId(gettransaction.getClientId());
			txProcBean.setTransactionType(gettransaction.getTransactionType());
			txProcBean.setTransactionDate(gettransaction.getTransactionDate());
			txProcBean.setPriority(gettransaction.getPriorityFlag());
			
			transProsList.add(txProcBean);
		}
		
		return transProsList;
		
	}
	
	public static double normalTransaction(TransactionBean txBean){
		double amount = 0;
		if(txBean.getPriorityFlag().equalsIgnoreCase("Y")){
			amount = txBean.getMarketValue() + 500;
			
		}else{
			if(txBean.getTransactionType().equalsIgnoreCase("SELL") || 
					txBean.getTransactionType().equalsIgnoreCase("WITHDRAW")){
				
				amount = txBean.getMarketValue() + 100;
				
			}else if(txBean.getTransactionType().equalsIgnoreCase("BUY") || 
					txBean.getTransactionType().equalsIgnoreCase("DEPOSIT")){
				
				amount = txBean.getMarketValue() + 50;
				
			}
		}
		
		return amount;
		
	}
	
	public static boolean checkIntradayTransactions(List<TransactionBean> txBean ,TransactionBean checkTnx){
		
		boolean checkIntradayTransactions = false;
		
		if(txBean.contains(checkTnx)){
			checkIntradayTransactions = true;
		}
		return checkIntradayTransactions;
		
	}
	
	public static double IntradayTransaction(TransactionBean txBean){
		
		double amount = 0;
		
		if(txBean.getTransactionType().equalsIgnoreCase("SELL") || 
				txBean.getTransactionType().equalsIgnoreCase("BUY")){
			
			amount = txBean.getMarketValue() + 10;
			
		} else{
			
			amount = normalTransaction(txBean);
		}
		
		return amount;
		
	}
	
	
	public static void writeToExcel(List<TransactionProcessingBean> txproList){
		 
		XSSFWorkbook workbook = new XSSFWorkbook();
 
        XSSFSheet transactionProcessingSheet = workbook.createSheet("Sample_Output");
        
        Cell cell = null;
        
        Row header = transactionProcessingSheet.createRow(0);
        header.createCell(0).setCellValue("Client Id");
        header.createCell(1).setCellValue("Transaction Type");
        header.createCell(2).setCellValue("Transaction Date");
        header.createCell(3).setCellValue("Priority");
        header.createCell(3).setCellValue("Processing Fee");
        
        
        int rowIndex = 1;
        for(TransactionProcessingBean getTransaction : txproList){
            Row row = transactionProcessingSheet.createRow(rowIndex++);
            int cellIndex = 0;
            row.createCell(cellIndex++).setCellValue(getTransaction.getClientId());
            row.createCell(cellIndex++).setCellValue(getTransaction.getTransactionType());
            
            CellStyle cellStyle = workbook.createCellStyle();
            CreationHelper createHelper = workbook.getCreationHelper();
            
            cellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("m/d/yy"));
            cell = row.createCell(2);
            cell.setCellValue(getTransaction.getTransactionDate());
            cell.setCellStyle(cellStyle);
            
            row.createCell(cellIndex++).setCellStyle(cellStyle);
            row.createCell(cellIndex++).setCellValue(getTransaction.getPriority());
            row.createCell(cellIndex++).setCellValue(getTransaction.getProcessingFee());

        }
        try {
            FileOutputStream fos = new FileOutputStream(OUTPUT_FILE_PATH);
            workbook.write(fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
    }
	
	public static void main(String[] args) {
		
		List<TransactionProcessingBean> transactionList = processingFee();
		
		Comparator<TransactionProcessingBean> groupByComparator = Comparator.comparing(TransactionProcessingBean::getClientId)
				.thenComparing(TransactionProcessingBean::getTransactionType).thenComparing(TransactionProcessingBean::getTransactionDate)
				.thenComparing(TransactionProcessingBean::getPriority);
		
		transactionList.sort(groupByComparator);
		 
        System.out.println(transactionList);
        
        writeToExcel(transactionList);
 
		
	}
}
