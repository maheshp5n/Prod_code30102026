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

import com.pratikshat.exicom.model.request.PODUpdateRequest;

public class PODParser {
	public List<PODUpdateRequest> parseIn(MultipartFile file) throws Exception{
		List<PODUpdateRequest> lst=new ArrayList<PODUpdateRequest>();
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
			
			PODUpdateRequest req=new PODUpdateRequest();
			
			req.setCaseNumber(dataFormatter.formatCellValue(row.getCell(0)).trim());
			
			req.setPodNumber(dataFormatter.formatCellValue(row.getCell(1)).trim());
			try {
				req.setPodDate(row.getCell(2).getDateCellValue());
			}catch(Exception ex) {
				
			}
			
			req.setPodShippingMode(dataFormatter.formatCellValue(row.getCell(3)).trim());
			
			
//			req.setPodShippingToName(dataFormatter.formatCellValue(row.getCell(4)).trim());
//			req.setPodShippingTrackingNumber(dataFormatter.formatCellValue(row.getCell(5)).trim());
//			try {
//				req.setPodShippingExpectedDate(row.getCell(6).getDateCellValue());
//			}catch(Exception ex) {
//				
//			}
			
			lst.add(req);
		}
		return lst;
	}
}
