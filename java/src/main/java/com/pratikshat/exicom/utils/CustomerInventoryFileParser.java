package com.pratikshat.exicom.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.pratikshat.exicom.model.request.CustomerInventoryOutWardRequest;
import com.pratikshat.exicom.model.request.CustomerInventoryRequest;

public class CustomerInventoryFileParser {
	public List<CustomerInventoryRequest> parseIn(File file) throws Exception{
		List<CustomerInventoryRequest> lst=new ArrayList<CustomerInventoryRequest>();
		DataFormatter formatter=new DataFormatter();
		if(!file.exists())
			throw new Exception("File not found");
		//ByteArrayInputStream str=new ByteArrayInputStream(new FileInputStream(file));
		Workbook wb=null;
		try {
			wb=new XSSFWorkbook(new FileInputStream(file));
		}catch(Exception ex) {
			
		}
		if(wb==null) {
			try {
				wb=new HSSFWorkbook(new FileInputStream(file));	
			}catch(Exception ex) {
				
			}
		}
		if(wb==null)
			throw new Exception("Wrong file format");		
		Sheet sheet=wb.getSheetAt(0);
		Iterator<Row> rows=sheet.iterator();
		rows.next();//SKIPPING FIRST ROW
		while(rows.hasNext()) {
			Row row =rows.next();
			CustomerInventoryRequest req=new CustomerInventoryRequest();
			req.setAccountSapNumber(formatter.formatCellValue(row.getCell(0)));
			req.setCircle(formatter.formatCellValue(row.getCell(1)));
			req.setCustomerChallanDate(row.getCell(2).getDateCellValue());
			req.setCustomerChallanNumber(formatter.formatCellValue(row.getCell(3)));
			req.setCustomerDocketNumber(formatter.formatCellValue(row.getCell(4)));
			req.setProductPartCode(formatter.formatCellValue(row.getCell(5)));
			req.setProductSerialNumber(formatter.formatCellValue(row.getCell(6)));
			req.setReceiveDate(row.getCell(7).getDateCellValue());
			req.setRedmrks(formatter.formatCellValue(row.getCell(8)));
			req.setWarrantyStatus(formatter.formatCellValue(row.getCell(9)));
			req.setLotNo(formatter.formatCellValue(row.getCell(10)));
			lst.add(req);
		}
		return lst;
	}
	public List<CustomerInventoryOutWardRequest> parseOut(File file) throws Exception{
		List<CustomerInventoryOutWardRequest> lst=new ArrayList<CustomerInventoryOutWardRequest>();
		List<Map<String,Object>> mList=new ExcelParser().parseFile(new FileInputStream(file),0);
		System.out.println(mList);
		for(Map<String,Object> d:mList) {
			CustomerInventoryOutWardRequest out=new CustomerInventoryOutWardRequest();
			out.setDispatchChallanNumber(d.get("Dispatch Challan Number")!=null?d.get("Dispatch Challan Number").toString():"");
			out.setDispatchDate(new Date());
			out.setDispatchDocketNumber(d.get("Dispatch Docket Number")!=null?d.get("Dispatch Docket Number").toString():"");
			out.setProductPartcode(d.get("Product Part Code")!=null?d.get("Product Part Code").toString():"");
			out.setProductSerialNumber(d.get("Product Serial Number")!=null?d.get("Product Serial Number").toString():"");
			out.setLotNo(d.get("Lot Number")!=null?d.get("Lot Number").toString():"");
			out.setRemark(d.get("Remarks")!=null?d.get("Remarks").toString():"");
			lst.add(out);
		}
		
		return lst;
	}
	
	
}
