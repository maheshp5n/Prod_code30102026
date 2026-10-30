package com.pratikshat.exicom.utils;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.pratikshat.exicom.model.request.AmcUpdateRequest;

public class AmcUpdateParser {

	
	public List<AmcUpdateRequest> parseIn(MultipartFile file) throws Exception{
		List<AmcUpdateRequest> lst=new ArrayList<AmcUpdateRequest>();
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
			
			AmcUpdateRequest req=new AmcUpdateRequest();
			req.setAssetSerialNo(formatter.formatCellValue(row.getCell(0)).trim());
			req.setAssetPartCode(formatter.formatCellValue(row.getCell(1)).trim());
			req.setAmcPoNo(formatter.formatCellValue(row.getCell(2)).trim());
			try {
				req.setAmcPoDate(row.getCell(3).getDateCellValue());	
			}catch(Exception ex) {
				throw new Exception("AMC PO date is blank");
			}
			
			try {
				req.setAmcStartDate(row.getCell(4).getDateCellValue());
			}catch(Exception ex) {
				throw new Exception("AMC Start date is blank");
			}
			
			req.setAmcPeriod(formatter.formatCellValue(row.getCell(5)).trim());
			
			lst.add(req);
		}
		return lst;
	}
}
