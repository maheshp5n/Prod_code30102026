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

import com.pratikshat.exicom.model.request.RejectRmaRequest;

public class RmaRejectParser {
	
	public List<RejectRmaRequest> parseIn(MultipartFile file) throws Exception {
		List<RejectRmaRequest> lst = new ArrayList<RejectRmaRequest>();
		DataFormatter dataFormatter = new DataFormatter();
		if (file.isEmpty())
			throw new Exception("File not found");
		ByteArrayInputStream str = new ByteArrayInputStream(file.getBytes());

		Workbook wb = new XSSFWorkbook(str);
		Sheet sheet = wb.getSheetAt(0);
		Iterator<Row> rows = sheet.iterator();
		rows.next();// SKIPPING FIRST ROW
		while (rows.hasNext()) {

			Row row = rows.next();
			RejectRmaRequest req = new RejectRmaRequest();

			req.setRmaManualId(dataFormatter.formatCellValue(row.getCell(0)).trim());
			req.setRemarks(dataFormatter.formatCellValue(row.getCell(1)).trim());
			
			lst.add(req);
		}
		return lst;
	}

}
