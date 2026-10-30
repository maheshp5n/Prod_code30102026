package com.pratikshat.exicom.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class BtCustomerInventoryFileParser {
	public List<Map<String, Object>> parseIn(File file) throws Exception {

		List<Map<String, Object>> lst = new ArrayList<Map<String, Object>>();
		DataFormatter formatter = new DataFormatter();
		if (!file.exists())
			throw new Exception("File not found");
		Workbook wb = null;
		try {
			wb = new XSSFWorkbook(new FileInputStream(file));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (wb == null) {
			try {
				wb = new HSSFWorkbook(new FileInputStream(file));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		if (wb == null)
			throw new Exception("Wrong file format");
		Sheet sheet = wb.getSheetAt(0);
		Iterator<Row> rows = sheet.iterator();
		rows.next();// SKIPPING FIRST ROW

		while (rows.hasNext()) {
			Row row = rows.next();
			Map<String, Object> req = new HashMap<String, Object>();
			req.put("assetsSerialNumber", formatter.formatCellValue(row.getCell(0)));
			req.put("partCode", formatter.formatCellValue(row.getCell(1)));
			req.put("customerInventoryStage", formatter.formatCellValue(row.getCell(2)));
			req.put("customerInventoryStatus", formatter.formatCellValue(row.getCell(3)));
			req.put("dummayWarehouseName", formatter.formatCellValue(row.getCell(4)));
			req.put("contactName", formatter.formatCellValue(row.getCell(5)));
			req.put("contactNumber", formatter.formatCellValue(row.getCell(6)));
			req.put("accountSapNumber", formatter.formatCellValue(row.getCell(7)));
			req.put("challanNumber", formatter.formatCellValue(row.getCell(8)));
			req.put("receiveChallanDate", formatter.formatCellValue(row.getCell(9)));
			req.put("circle", formatter.formatCellValue(row.getCell(10)));
			req.put("dummayLocationName", formatter.formatCellValue(row.getCell(11)));
			req.put("customerLrNumber", formatter.formatCellValue(row.getCell(12)));
			req.put("customerCurierPartnarName", formatter.formatCellValue(row.getCell(13)));
			req.put("customerPlant", formatter.formatCellValue(row.getCell(14)));
			try {
				req.put("receiveDate", row.getCell(15).getDateCellValue());
			} catch (Exception ex) {
				
			}
			// req.put("receiveDate", formatter.formatCellValue(row.getCell(15)));
			req.put("pacingCondation", formatter.formatCellValue(row.getCell(16)));
			req.put("physicalDamage", formatter.formatCellValue(row.getCell(17)));
			req.put("remarks", formatter.formatCellValue(row.getCell(18)));

			req.put("rmaManualId", formatter.formatCellValue(row.getCell(19)));

			// req.put("accountName", formatter.formatCellValue(row.getCell(3)));

			// req.put("lotNumber", formatter.formatCellValue(row.getCell(6)));
			// req.put("trackingNumber", formatter.formatCellValue(row.getCell(7)));
			// req.put("docketNumber", formatter.formatCellValue(row.getCell(8)));
			// req.put("docketDate", formatter.formatCellValue(row.getCell(9)));

			// req.put("brcNumber", formatter.formatCellValue(row.getCell(12)));

			lst.add(req);
		}

		return lst;
	}

	public List<Map<String, Object>> parseOut(File file) throws Exception {

		List<Map<String, Object>> lst = new ArrayList<Map<String, Object>>();
		DataFormatter formatter = new DataFormatter();
		if (!file.exists())
			throw new Exception("File not found");
		Workbook wb = null;
		try {
			wb = new XSSFWorkbook(new FileInputStream(file));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (wb == null) {
			try {
				wb = new HSSFWorkbook(new FileInputStream(file));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		if (wb == null)
			throw new Exception("Wrong file format");
		Sheet sheet = wb.getSheetAt(0);
		Iterator<Row> rows = sheet.iterator();
		rows.next();// SKIPPING FIRST ROW

		while (rows.hasNext()) {
			Row row = rows.next();
			Map<String, Object> req = new HashMap<String, Object>();
			req.put("manualid", formatter.formatCellValue(row.getCell(0)));
			req.put("actualOutDate", formatter.formatCellValue(row.getCell(1)));
			req.put("outLrDocNumber", formatter.formatCellValue(row.getCell(2)));
			req.put("outCourierPartner", formatter.formatCellValue(row.getCell(3)));
			req.put("outdeleveryChallanNumber", formatter.formatCellValue(row.getCell(4)));
			req.put("outGrnNumber", formatter.formatCellValue(row.getCell(5)));

			// req.put("outGrnDate", formatter.formatCellValue(row.getCell(6)));
			if(row.getCell(6)!=null)
			req.put("outGrnDate", row.getCell(6).getDateCellValue());
			else
				req.put("outGrnDate", formatter.formatCellValue(row.getCell(6)));
			// req.put("outState", formatter.formatCellValue(row.getCell(7)));

			// req.put("loatNumber", formatter.formatCellValue(row.getCell(8)));

			req.put("trackingNumber", formatter.formatCellValue(row.getCell(7)));
			lst.add(req);
		}

		return lst;
	}
}
