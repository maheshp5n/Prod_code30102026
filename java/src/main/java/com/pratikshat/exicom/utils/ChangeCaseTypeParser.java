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

import com.pratikshat.exicom.model.request.ChangeCaseTypeRequest;

public class ChangeCaseTypeParser {
	public List<ChangeCaseTypeRequest> parseIn(MultipartFile file) throws Exception{
		List<ChangeCaseTypeRequest> lst=new ArrayList<ChangeCaseTypeRequest>();
		DataFormatter formatter=new DataFormatter();
		if(file.isEmpty())
			throw new Exception("File not found");
		ByteArrayInputStream str=new ByteArrayInputStream(file.getBytes());
		
		Workbook wb =new XSSFWorkbook(str);
		Sheet sheet=wb.getSheetAt(0);
		Iterator<Row> rows=sheet.iterator();
		rows.next();//SKIPPING FIRST ROW
		while(rows.hasNext()) {
			
			Row row =rows.next();
			
			ChangeCaseTypeRequest req=new ChangeCaseTypeRequest();
			req.setCaseManualId(formatter.formatCellValue(row.getCell(0)).trim());
			req.setCaseType(formatter.formatCellValue(row.getCell(1)).trim());
			req.setCaseReason(formatter.formatCellValue(row.getCell(2)).trim());
			req.setPoNo(formatter.formatCellValue(row.getCell(3)).trim());
			try {
				req.setPoDate(row.getCell(4).getDateCellValue());
			}catch(Exception ex) {
				
			}
			
			lst.add(req);
		}
		return lst;
	}
}
