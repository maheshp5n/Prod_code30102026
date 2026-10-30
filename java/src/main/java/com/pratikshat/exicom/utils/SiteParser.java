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

import com.pratikshat.exicom.model.request.SiteBulkRequest;

public class SiteParser {
	public List<SiteBulkRequest> parseIn(MultipartFile file) throws Exception {
		List<SiteBulkRequest> lst = new ArrayList<SiteBulkRequest>();
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
			SiteBulkRequest req = new SiteBulkRequest();

			req.setSiteName(dataFormatter.formatCellValue(row.getCell(0)).trim());
			req.setSiteId(dataFormatter.formatCellValue(row.getCell(1)).trim());
			req.setInfraId(dataFormatter.formatCellValue(row.getCell(2)).trim());
			req.setSiteAddress(dataFormatter.formatCellValue(row.getCell(3)).trim());
			req.setSiteDescription(dataFormatter.formatCellValue(row.getCell(4)).trim());
			req.setCircle(dataFormatter.formatCellValue(row.getCell(5)).trim());
			req.setSsa(dataFormatter.formatCellValue(row.getCell(6)).trim());
			req.setClusterDistrict(dataFormatter.formatCellValue(row.getCell(7)).trim());
			req.setAccount(dataFormatter.formatCellValue(row.getCell(8)).trim());
			req.setCustomer(dataFormatter.formatCellValue(row.getCell(9)).trim());
			req.setServiceCenter(dataFormatter.formatCellValue(row.getCell(10)).trim());
			req.setServiceResource(dataFormatter.formatCellValue(row.getCell(11)).trim());
			req.setIndusId(dataFormatter.formatCellValue(row.getCell(12)).trim());
			
			lst.add(req);
		}
		return lst;
	}
}
