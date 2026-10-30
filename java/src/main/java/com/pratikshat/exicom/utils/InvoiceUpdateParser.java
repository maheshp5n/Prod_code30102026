package com.pratikshat.exicom.utils;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.pratikshat.exicom.model.request.InvoiceUpdateRequest;

public class InvoiceUpdateParser {
	public List<InvoiceUpdateRequest> parseIn(MultipartFile file) throws Exception{
		List<InvoiceUpdateRequest> lst=new ArrayList<InvoiceUpdateRequest>();
		DataFormatter dataFormatter=new DataFormatter();
		if(file.isEmpty())
			throw new Exception("File not found");
		ByteArrayInputStream str=new ByteArrayInputStream(file.getBytes());
		
		Workbook wb =new XSSFWorkbook(str);
		Sheet sheet=wb.getSheetAt(0);
		Iterator<Row> rows=sheet.iterator();
		rows.next();//SKIPPING FIRST ROW
		while(rows.hasNext()) {
			
			Row row =rows.next();
			
			InvoiceUpdateRequest req=new InvoiceUpdateRequest();
			
			req.setCaseNumber(dataFormatter.formatCellValue(row.getCell(0)).trim());
			
			req.setPONumber(dataFormatter.formatCellValue(row.getCell(1)).trim());
			try {
				req.setPODate(row.getCell(2).getDateCellValue());
			}catch(Exception ex) {
				
			}
			
			req.setInvoiceNumber(dataFormatter.formatCellValue(row.getCell(3)).trim());
			try {
				req.setInvoiceDate(row.getCell(4).getDateCellValue());
			}catch(Exception ex) {
				
			}
			
			req.setVendorInvoiceNumber(dataFormatter.formatCellValue(row.getCell(5)).trim());
			try {
				req.setVendorInvoiceDate(row.getCell(6).getDateCellValue());
			}catch(Exception ex) {
				
			}
			
			lst.add(req);
		}
		return lst;
	}
	
	
	
}
