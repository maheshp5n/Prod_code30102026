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

import com.pratikshat.exicom.model.request.BtCaseBulkRequest;

public class BtCaseParser {
	public List<BtCaseBulkRequest> parseIn(MultipartFile file) throws Exception {
		List<BtCaseBulkRequest> lst = new ArrayList<BtCaseBulkRequest>();
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
			BtCaseBulkRequest req = new BtCaseBulkRequest();

			req.setAssetSerialNumber(dataFormatter.formatCellValue(row.getCell(0)).trim());
			req.setAssetPartCode(dataFormatter.formatCellValue(row.getCell(1)).trim());
			req.setSiteId(dataFormatter.formatCellValue(row.getCell(2)).trim());
			
			req.setCallerPhoneNo(dataFormatter.formatCellValue(row.getCell(3)).trim());

			req.setCaseTypeName(dataFormatter.formatCellValue(row.getCell(4)).trim());

			req.setCaseReasonName(dataFormatter.formatCellValue(row.getCell(5)).trim());

			req.setStatusName(dataFormatter.formatCellValue(row.getCell(6)).trim());

			req.setStageName(dataFormatter.formatCellValue(row.getCell(7)).trim());

			req.setCaseOriginName(dataFormatter.formatCellValue(row.getCell(8)).trim());

			req.setPriorityName(dataFormatter.formatCellValue(row.getCell(9)).trim());

			req.setPendingResponsibility(dataFormatter.formatCellValue(row.getCell(10)).trim());

			req.setProblemsReported(dataFormatter.formatCellValue(row.getCell(11)).trim());
			
			req.setNumberOfSMR(dataFormatter.formatCellValue(row.getCell(12)).trim());
			
			req.setOtherProblemReported(dataFormatter.formatCellValue(row.getCell(13)).trim());
			
			req.setRemark(dataFormatter.formatCellValue(row.getCell(14)).trim());
			req.setProblemMaster(dataFormatter.formatCellValue(row.getCell(15)).trim());
			req.setRJILTT01No(dataFormatter.formatCellValue(row.getCell(16)).trim());
			

			lst.add(req);
		}
		return lst;
	}
}
