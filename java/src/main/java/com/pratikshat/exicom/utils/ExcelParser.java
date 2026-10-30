package com.pratikshat.exicom.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelParser {

	public List<Map<String, Object>> parseFile(InputStream in, int sheetNumber) throws Exception {
		List<Map<String, Object>> lst = new ArrayList<Map<String, Object>>();
		DataFormatter formatter = new DataFormatter();
		Workbook wb = null;
		try {
			wb = new XSSFWorkbook(in);
		} catch (Exception ex) {

		}
		if (wb == null) {
			try {
				wb = new HSSFWorkbook(in);
			} catch (Exception ex) {
			}
		}
		if (wb == null)
			throw new Exception("Wrong file format");
		Sheet sheet = wb.getSheetAt(sheetNumber);
		Iterator<Row> rows = sheet.iterator();
		Row firsRow = rows.next();
		List<String> headers = new ArrayList<String>();
		FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
		Iterator<Cell> headerCell = firsRow.iterator();
		while (headerCell.hasNext()) {
			Cell cl = headerCell.next();
			String hValue = cl.getStringCellValue();
			headers.add(hValue);
		}
		while (rows.hasNext()) {
			Iterator<Cell> cells = rows.next().iterator();
			Map<String, Object> data = new HashMap<String, Object>();
			int cellNumber = 0;
			while (cells.hasNext()) {

				Cell cell = cells.next();

				// if(CellType.NUMERIC==cell.getCellTypeEnum()) {
				// try {
				// data.put(headers.get(cellNumber), cell.getDateCellValue());
				// }catch(Exception ex) {
				// data.put(headers.get(cellNumber), cell.getNumericCellValue());
				// }
				// }
				// else {
				// data.put(headers.get(cellNumber),formatter.formatCellValue(cell));
				// }

				data.put(headers.get(cellNumber), formatter.formatCellValue(cell));
				cellNumber++;
			}
			lst.add(data);
		}

		return lst;
	}

	public ByteArrayOutputStream genrateFile(List<Map<String, Object>> data) throws Exception {
		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet();
		Row headerRow = sheet.createRow(0);
		Set<String> keySet = data.get(0).keySet();
		int cellCount = 0;
		for (String key : keySet) {
			headerRow.createCell(cellCount++).setCellValue(key);
		}
		int rowNumber = 1;
		for (Map<String, Object> d : data) {
			Row drow = sheet.createRow(rowNumber++);
			int innercell = 0;
			for (String key : keySet) {
				System.out.println("Key name ==>  " + key + ", Value ==>  " + d.get(key));
				drow.createCell(innercell++).setCellValue(d.get(key) == null ? "" : d.get(key).toString());
			}
		}
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		wb.write(outStream);

		return outStream;
	}

	public static List<Map<String, Object>> parseDctRepairBulkConsumedd(File file) throws Exception {
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
			req.put("consumedProductPartcode", formatter.formatCellValue(row.getCell(0)));
			req.put("consumedQuantity", formatter.formatCellValue(row.getCell(1)));
			req.put("remarks", formatter.formatCellValue(row.getCell(2)));
			lst.add(req);
		}

		return lst;
	}

}
